package com.mall4j.cloud.auth.feign;

import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.auth.constant.SysTypeEnum;
import com.mall4j.cloud.api.auth.dto.AuthAccountDTO;
import com.mall4j.cloud.api.auth.feign.AccountFeignClient;
import com.mall4j.cloud.api.auth.vo.AuthAccountVO;
import com.mall4j.cloud.api.leaf.feign.SegmentFeignClient;
import com.mall4j.cloud.auth.manager.TokenStore;
import com.mall4j.cloud.auth.mapper.AuthAccountMapper;
import com.mall4j.cloud.auth.model.AuthAccount;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.common.security.bo.AuthAccountInVerifyBO;
import com.mall4j.cloud.common.security.constant.InputUserNameEnum;
import com.mall4j.cloud.api.auth.vo.TokenInfoVO;
import com.mall4j.cloud.common.util.PrincipalUtil;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 账户Feign控制器
 * 处理账户相关的Feign请求
 */
@RestController
public class AccountFeignController implements AccountFeignClient {

    @Autowired
    private AuthAccountMapper authAccountMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private SegmentFeignClient segmentFeignClient;

    /**
     * 保存账户信息
     * @param authAccountDTO 账户DTO对象
     * @return 保存成功后返回账户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseEntity<Long> save(AuthAccountDTO authAccountDTO) {
        // 获取账户ID段
        ServerResponseEntity<Long> segmentIdResponse = segmentFeignClient.getSegmentId("mall4cloud-auth-account");
        if (!segmentIdResponse.isSuccess()) {
            throw new Mall4cloudException(ResponseEnum.EXCEPTION);
        }

        // 验证账户信息
        ServerResponseEntity<AuthAccount> verify = verify(authAccountDTO);
        if (!verify.isSuccess()) {
            return ServerResponseEntity.transform(verify);
        }
        AuthAccount data = verify.getData();
        data.setUid(segmentIdResponse.getData());
        authAccountMapper.save(data);

        return ServerResponseEntity.success(data.getUid());
    }

    /**
     * 更新账户信息
     * @param authAccountDTO 账户DTO对象
     * @return 更新成功返回null
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseEntity<Void> update(AuthAccountDTO authAccountDTO) {
        // 验证账户信息
        ServerResponseEntity<AuthAccount> verify = verify(authAccountDTO);
        if (!verify.isSuccess()) {
            return ServerResponseEntity.transform(verify);
        }
        authAccountMapper.updateAccountInfo(verify.getData());
        return ServerResponseEntity.success();
    }

    /**
     * 更新账户状态
     * @param authAccountDTO 账户DTO对象
     * @return 更新成功返回null
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseEntity<Void> updateAuthAccountStatus(AuthAccountDTO authAccountDTO) {
        if (Objects.isNull(authAccountDTO.getStatus())) {
            throw new Mall4cloudException(ResponseEnum.EXCEPTION);
        }
        AuthAccount authAccount = BeanUtil.map(authAccountDTO, AuthAccount.class);
        authAccountMapper.updateAccountInfo(authAccount);
        return ServerResponseEntity.success();
    }

    /**
     * 根据用户ID和系统类型删除账户
     * @param userId 用户ID
     * @return 删除成功返回null
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseEntity<Void> deleteByUserIdAndSysType(Long userId) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        authAccountMapper.deleteByUserIdAndSysType(userId, userInfoInTokenBO.getSysType());
        return ServerResponseEntity.success();
    }

    /**
     * 根据用户ID和系统类型获取账户信息
     * @param userId 用户ID
     * @param sysType 系统类型
     * @return 获取成功返回账户信息
     */
    @Override
    public ServerResponseEntity<AuthAccountVO> getByUserIdAndSysType(Long userId,Integer sysType) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        AuthAccount authAccount = authAccountMapper.getByUserIdAndType(userId, userInfoInTokenBO.getSysType());
        return ServerResponseEntity.success(BeanUtil.map(authAccount, AuthAccountVO.class));
    }

    /**
     * 存储令牌并获取令牌信息
     * @param userInfoInTokenBO 用户令牌信息
     * @return 存储成功后返回令牌信息
     */
    @Override
    public ServerResponseEntity<TokenInfoVO> storeTokenAndGetVo(UserInfoInTokenBO userInfoInTokenBO) {
        return ServerResponseEntity.success(tokenStore.storeAndGetVo(userInfoInTokenBO));
    }

    /**
     * 根据用户名和系统类型获取账户信息
     * @param username 用户名
     * @param sysType 系统类型
     * @return 获取成功返回账户信息
     */
    @Override
    public ServerResponseEntity<AuthAccountVO> getByUsernameAndSysType(String username, SysTypeEnum sysType) {
        return ServerResponseEntity.success(authAccountMapper.getByUsernameAndSysType(username, sysType.value()));
    }

    /**
     * 验证账户信息
     * @param authAccountDTO 账户DTO对象
     * @return 验证成功返回账户信息
     */
    private ServerResponseEntity<AuthAccount> verify(AuthAccountDTO authAccountDTO) {

        // 验证用户名格式
        if (!PrincipalUtil.isUserName(authAccountDTO.getUsername())) {
            return ServerResponseEntity.showFailMsg("用户名格式不正确");
        }

        // 验证用户名是否已存在
        AuthAccountInVerifyBO userNameBo = authAccountMapper.getAuthAccountInVerifyByInputUserName(InputUserNameEnum.USERNAME.value(), authAccountDTO.getUsername(), authAccountDTO.getSysType());
        if (userNameBo != null && !Objects.equals(userNameBo.getUserId(), authAccountDTO.getUserId())) {
            return ServerResponseEntity.showFailMsg("用户名已存在，请更换用户名再次尝试");
        }

        AuthAccount authAccount = BeanUtil.map(authAccountDTO, AuthAccount.class);

        // 加密密码
        if (StrUtil.isNotBlank(authAccount.getPassword())) {
            authAccount.setPassword(passwordEncoder.encode(authAccount.getPassword()));
        }

        return ServerResponseEntity.success(authAccount);
    }

    /**
     * 根据用户ID和系统类型更新用户信息
     * @param userInfoInTokenBO 用户令牌信息
     * @param userId 用户ID
     * @param sysType 系统类型
     * @return 更新成功返回null
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseEntity<Void> updateUserInfoByUserIdAndSysType(UserInfoInTokenBO userInfoInTokenBO, Long userId, Integer sysType) {
        AuthAccount byUserIdAndType = authAccountMapper.getByUserIdAndType(userId, sysType);
        userInfoInTokenBO.setUid(byUserIdAndType.getUid());
        tokenStore.updateUserInfoByUidAndAppId(byUserIdAndType.getUid(), sysType.toString(), userInfoInTokenBO);
        AuthAccount authAccount = BeanUtil.map(userInfoInTokenBO, AuthAccount.class);
        int res = authAccountMapper.updateUserInfoByUserId(authAccount, userId, sysType);
        if (res != 1) {
            throw new Mall4cloudException("用户信息错误，更新失败");
        }
        return ServerResponseEntity.success();
    }

    /**
     * 根据租户ID获取商户信息
     * @param tenantId 租户ID
     * @return 获取成功返回商户信息
     */
    @Override
    public ServerResponseEntity<AuthAccountVO> getMerchantInfoByTenantId(Long tenantId) {
        AuthAccountVO authAccountVO = authAccountMapper.getMerchantInfoByTenantId(tenantId);
        return ServerResponseEntity.success(authAccountVO);
    }

}
