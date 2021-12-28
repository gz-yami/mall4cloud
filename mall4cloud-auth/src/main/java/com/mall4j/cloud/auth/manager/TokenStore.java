package com.mall4j.cloud.auth.manager;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.AES;
import com.mall4j.cloud.common.cache.constant.CacheNames;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.security.bo.TokenInfoBO;
import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.auth.constant.SysTypeEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.api.auth.vo.TokenInfoVO;
import com.mall4j.cloud.common.util.PrincipalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * token管理 1. 登陆返回token 2. 刷新token 3. 清除用户过去token 4. 校验token
 *
 * @author FrozenWatermelon
 * @date 2020/7/2
 */
@Component
@RefreshScope
public class TokenStore {

	private static final Logger logger = LoggerFactory.getLogger(TokenStore.class);

	private final RedisTemplate<Object, Object> redisTemplate;

	private final RedisSerializer<Object> redisSerializer;

	private final StringRedisTemplate stringRedisTemplate;

	public TokenStore(RedisTemplate<Object, Object> redisTemplate, RedisSerializer<Object> redisSerializer,
			StringRedisTemplate stringRedisTemplate) {
		this.redisTemplate = redisTemplate;
		this.redisSerializer = redisSerializer;
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 将用户的部分信息存储在token中，并返回token信息
	 * @param userInfoInToken 用户在token中的信息
	 * @return token信息
	 */
	public TokenInfoBO storeAccessToken(UserInfoInTokenBO userInfoInToken) {
		TokenInfoBO tokenInfoBO = new TokenInfoBO();
		String accessToken = IdUtil.simpleUUID();
		String refreshToken = IdUtil.simpleUUID();

		tokenInfoBO.setUserInfoInToken(userInfoInToken);
		tokenInfoBO.setExpiresIn(getExpiresIn(userInfoInToken.getSysType()));

		String uidToAccessKeyStr = getUidToAccessKey(getApprovalKey(userInfoInToken));
		String accessKeyStr = getAccessKey(accessToken);
		String refreshToAccessKeyStr = getRefreshToAccessKey(refreshToken);

		// 一个用户会登陆很多次，每次登陆的token都会存在 uid_to_access里面
		// 但是每次保存都会更新这个key的时间，而key里面的token有可能会过期，过期就要移除掉
		List<String> existsAccessTokens = new ArrayList<>();
		// 新的token数据
		existsAccessTokens.add(accessToken + StrUtil.COLON + refreshToken);

		Long size = redisTemplate.opsForSet().size(uidToAccessKeyStr);
		if (size != null && size != 0) {
			List<String> tokenInfoBoList = stringRedisTemplate.opsForSet().pop(uidToAccessKeyStr, size);
			if (tokenInfoBoList != null) {
				for (String accessTokenWithRefreshToken : tokenInfoBoList) {
					String[] accessTokenWithRefreshTokenArr = accessTokenWithRefreshToken.split(StrUtil.COLON);
					String accessTokenData = accessTokenWithRefreshTokenArr[0];
					if (BooleanUtil.isTrue(stringRedisTemplate.hasKey(getAccessKey(accessTokenData)))) {
						existsAccessTokens.add(accessTokenWithRefreshToken);
					}
				}
			}
		}

		redisTemplate.executePipelined((RedisCallback<Object>) connection -> {

			long expiresIn = tokenInfoBO.getExpiresIn();

			byte[] uidKey = uidToAccessKeyStr.getBytes(StandardCharsets.UTF_8);
			byte[] refreshKey = refreshToAccessKeyStr.getBytes(StandardCharsets.UTF_8);
			byte[] accessKey = accessKeyStr.getBytes(StandardCharsets.UTF_8);

			for (String existsAccessToken : existsAccessTokens) {
				connection.sAdd(uidKey, existsAccessToken.getBytes(StandardCharsets.UTF_8));
			}

			// 通过uid + sysType 保存access_token，当需要禁用用户的时候，可以根据uid + sysType 禁用用户
			connection.expire(uidKey, expiresIn);

			// 通过refresh_token获取用户的access_token从而刷新token
			connection.setEx(refreshKey, expiresIn, accessToken.getBytes(StandardCharsets.UTF_8));

			// 通过access_token保存用户的租户id，用户id，uid
			connection.setEx(accessKey, expiresIn, Objects.requireNonNull(redisSerializer.serialize(userInfoInToken)));

			return null;
		});

		// 返回给前端是加密的token
		tokenInfoBO.setAccessToken(encryptToken(accessToken,userInfoInToken.getSysType()));
		tokenInfoBO.setRefreshToken(encryptToken(refreshToken,userInfoInToken.getSysType()));

		return tokenInfoBO;
	}

	private int getExpiresIn(int sysType) {
		// 3600秒
		int expiresIn = 3600;

		// 普通用户token过期时间 1小时
		if (Objects.equals(sysType, SysTypeEnum.ORDINARY.value())) {
			expiresIn = expiresIn * 24 * 30;
		}
		// 系统管理员的token过期时间 2小时
		if (Objects.equals(sysType, SysTypeEnum.MULTISHOP.value()) || Objects.equals(sysType, SysTypeEnum.PLATFORM.value())) {
			expiresIn = expiresIn * 24 * 30;
		}
		return expiresIn;
	}

	/**
	 * 根据accessToken 获取用户信息
	 * @param accessToken accessToken
	 * @param needDecrypt 是否需要解密
	 * @return 用户信息
	 */
	public ServerResponseEntity<UserInfoInTokenBO> getUserInfoByAccessToken(String accessToken, boolean needDecrypt) {
		if (StrUtil.isBlank(accessToken)) {
			return ServerResponseEntity.showFailMsg("accessToken is blank");
		}
		String realAccessToken;
		if (needDecrypt) {
			ServerResponseEntity<String> decryptTokenEntity = decryptToken(accessToken);
			if (!decryptTokenEntity.isSuccess()) {
				return ServerResponseEntity.transform(decryptTokenEntity);
			}
			realAccessToken = decryptTokenEntity.getData();
		}
		else {
			realAccessToken = accessToken;
		}
		UserInfoInTokenBO userInfoInTokenBO = (UserInfoInTokenBO) redisTemplate.opsForValue()
				.get(getAccessKey(realAccessToken));

		if (userInfoInTokenBO == null) {
			return ServerResponseEntity.showFailMsg("accessToken 已过期");
		}
		return ServerResponseEntity.success(userInfoInTokenBO);
	}

	/**
	 * 刷新token，并返回新的token
	 * @param refreshToken
	 * @return
	 */
	public ServerResponseEntity<TokenInfoBO> refreshToken(String refreshToken) {
		if (StrUtil.isBlank(refreshToken)) {
			return ServerResponseEntity.showFailMsg("refreshToken is blank");
		}
		ServerResponseEntity<String> decryptTokenEntity = decryptToken(refreshToken);
		if (!decryptTokenEntity.isSuccess()) {
			return ServerResponseEntity.transform(decryptTokenEntity);
		}
		String realRefreshToken = decryptTokenEntity.getData();
		String accessToken = stringRedisTemplate.opsForValue().get(getRefreshToAccessKey(realRefreshToken));

		if (StrUtil.isBlank(accessToken)) {
			return ServerResponseEntity.showFailMsg("refreshToken 已过期");
		}
		ServerResponseEntity<UserInfoInTokenBO> userInfoByAccessTokenEntity = getUserInfoByAccessToken(accessToken,
				false);

		if (!userInfoByAccessTokenEntity.isSuccess()) {
			return ServerResponseEntity.showFailMsg("refreshToken 已过期");
		}

		UserInfoInTokenBO userInfoInTokenBO = userInfoByAccessTokenEntity.getData();
		// 删除旧的refresh_token
		stringRedisTemplate.delete(getRefreshToAccessKey(realRefreshToken));
		// 删除旧的access_token
		stringRedisTemplate.delete(getAccessKey(accessToken));
		// 保存一份新的token
		TokenInfoBO tokenInfoBO = storeAccessToken(userInfoInTokenBO);

		return ServerResponseEntity.success(tokenInfoBO);
	}

	/**
	 * 删除全部的token
	 */
	public void deleteAllToken(String appId, Long uid) {
		String uidKey = getUidToAccessKey(getApprovalKey(appId, uid));
		Long size = redisTemplate.opsForSet().size(uidKey);
		if (size == null || size == 0) {
			return;
		}
		List<String> tokenInfoBoList = stringRedisTemplate.opsForSet().pop(uidKey, size);

		if (CollUtil.isEmpty(tokenInfoBoList)) {
			return;
		}

		for (String accessTokenWithRefreshToken : tokenInfoBoList) {
			String[] accessTokenWithRefreshTokenArr = accessTokenWithRefreshToken.split(StrUtil.COLON);
			String accessToken = accessTokenWithRefreshTokenArr[0];
			String refreshToken = accessTokenWithRefreshTokenArr[1];
			redisTemplate.delete(getRefreshToAccessKey(refreshToken));
			redisTemplate.delete(getAccessKey(accessToken));
		}
		redisTemplate.delete(uidKey);

	}

	private static String getApprovalKey(UserInfoInTokenBO userInfoInToken) {
		return getApprovalKey(userInfoInToken.getSysType().toString(), userInfoInToken.getUid());
	}

	private static String getApprovalKey(String appId, Long uid) {
		return uid == null?  appId : appId + StrUtil.COLON + uid;
	}

	private String encryptToken(String accessToken,Integer sysType) {
		return Base64.encode(accessToken + System.currentTimeMillis() + sysType);
	}

	private ServerResponseEntity<String> decryptToken(String data) {
		String decryptStr;
		String decryptToken;
		try {
			decryptStr = Base64.decodeStr(data);
			decryptToken = decryptStr.substring(0,32);
			// 创建token的时间，token使用时效性，防止攻击者通过一堆的尝试找到aes的密码，虽然aes是目前几乎最好的加密算法
			long createTokenTime = Long.parseLong(decryptStr.substring(32,45));
			// 系统类型
			int sysType = Integer.parseInt(decryptStr.substring(45));
			// token的过期时间
			int expiresIn = getExpiresIn(sysType);
			long second = 1000L;
			if (System.currentTimeMillis() - createTokenTime > expiresIn * second) {
				return ServerResponseEntity.showFailMsg("token 格式有误");
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			return ServerResponseEntity.showFailMsg("token 格式有误");
		}

		// 防止解密后的token是脚本，从而对redis进行攻击，uuid只能是数字和小写字母
		if (!PrincipalUtil.isSimpleChar(decryptToken)) {
			return ServerResponseEntity.showFailMsg("token 格式有误");
		}
		return ServerResponseEntity.success(decryptToken);
	}

	public String getAccessKey(String accessToken) {
		return CacheNames.ACCESS + accessToken;
	}

	public String getUidToAccessKey(String approvalKey) {
		return CacheNames.UID_TO_ACCESS + approvalKey;
	}

	public String getRefreshToAccessKey(String refreshToken) {
		return CacheNames.REFRESH_TO_ACCESS + refreshToken;
	}

	public TokenInfoVO storeAndGetVo(UserInfoInTokenBO userInfoInToken) {
		TokenInfoBO tokenInfoBO = storeAccessToken(userInfoInToken);

		TokenInfoVO tokenInfoVO = new TokenInfoVO();
		tokenInfoVO.setAccessToken(tokenInfoBO.getAccessToken());
		tokenInfoVO.setRefreshToken(tokenInfoBO.getRefreshToken());
		tokenInfoVO.setExpiresIn(tokenInfoBO.getExpiresIn());
		return tokenInfoVO;
	}

	public void updateUserInfoByUidAndAppId(Long uid, String appId, UserInfoInTokenBO userInfoInTokenBO) {
		if (userInfoInTokenBO == null) {
			return;
		}
		String uidKey = getUidToAccessKey(getApprovalKey(appId, uid));
		Set<String> tokenInfoBoList = stringRedisTemplate.opsForSet().members(uidKey);
		if (tokenInfoBoList == null || tokenInfoBoList.size() == 0) {
			throw new Mall4cloudException(ResponseEnum.UNAUTHORIZED);
		}
		for (String accessTokenWithRefreshToken : tokenInfoBoList) {
			String[] accessTokenWithRefreshTokenArr = accessTokenWithRefreshToken.split(StrUtil.COLON);
			String accessKey = this.getAccessKey(accessTokenWithRefreshTokenArr[0]);
			UserInfoInTokenBO oldUserInfoInTokenBO = (UserInfoInTokenBO) redisTemplate.opsForValue().get(accessKey);
			if (oldUserInfoInTokenBO == null) {
				continue;
			}
			BeanUtils.copyProperties(userInfoInTokenBO, oldUserInfoInTokenBO);
			redisTemplate.opsForValue().set(accessKey, Objects.requireNonNull(userInfoInTokenBO),getExpiresIn(userInfoInTokenBO.getSysType()), TimeUnit.SECONDS);
		}
	}
}
