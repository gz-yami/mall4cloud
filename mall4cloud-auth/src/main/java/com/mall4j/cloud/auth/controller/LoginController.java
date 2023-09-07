package com.mall4j.cloud.auth.controller;
import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.rbac.dto.ClearUserPermissionsCacheDTO;
import com.mall4j.cloud.api.rbac.feign.PermissionFeignClient;
import com.mall4j.cloud.auth.dto.AuthenticationDTO;
import com.mall4j.cloud.auth.manager.TokenStore;
import com.mall4j.cloud.auth.service.AuthAccountService;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.api.auth.vo.TokenInfoVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author FrozenWatermelon
 * @date 2020/6/30
 */
@RestController
@Tag(name = "登录")
public class LoginController {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private AuthAccountService authAccountService;

	@Autowired
	private PermissionFeignClient permissionFeignClient;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/ua/login")
	@Operation(summary = "账号密码" , description = "通过账号登录，还要携带用户的类型，也就是用户所在的系统")
	public ServerResponseEntity<TokenInfoVO> login(
			@Valid @RequestBody AuthenticationDTO authenticationDTO) {

		// 这边获取了用户的用户信息，那么根据sessionid对应一个user的原则，我应该要把这个东西存起来，然后校验，那么存到哪里呢？
		// redis，redis有天然的自动过期的机制，有key value的形式
		ServerResponseEntity<UserInfoInTokenBO> userInfoInTokenResponse = authAccountService
				.getUserInfoInTokenByInputUserNameAndPassword(authenticationDTO.getPrincipal(),
						authenticationDTO.getCredentials(), authenticationDTO.getSysType());


		if (!userInfoInTokenResponse.isSuccess()) {
			return ServerResponseEntity.transform(userInfoInTokenResponse);
		}

		UserInfoInTokenBO data = userInfoInTokenResponse.getData();

		ClearUserPermissionsCacheDTO clearUserPermissionsCacheDTO = new ClearUserPermissionsCacheDTO();
		clearUserPermissionsCacheDTO.setSysType(data.getSysType());
		clearUserPermissionsCacheDTO.setUserId(data.getUserId());
		// 将以前的权限清理了,以免权限有缓存
		ServerResponseEntity<Void> clearResponseEntity = permissionFeignClient.clearUserPermissionsCache(clearUserPermissionsCacheDTO);

		if (!clearResponseEntity.isSuccess()) {
			return ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED);
		}

		// 保存token，返回token数据给前端，这里是最重要的
		return ServerResponseEntity.success(tokenStore.storeAndGetVo(data));
	}

	@PostMapping("/login_out")
	@Operation(summary = "退出登陆" , description = "点击退出登陆，清除token，清除菜单缓存")
	public ServerResponseEntity<TokenInfoVO> loginOut() {
		UserInfoInTokenBO userInfoInToken = AuthUserContext.get();
		ClearUserPermissionsCacheDTO clearUserPermissionsCacheDTO = new ClearUserPermissionsCacheDTO();
		clearUserPermissionsCacheDTO.setSysType(userInfoInToken.getSysType());
		clearUserPermissionsCacheDTO.setUserId(userInfoInToken.getUserId());
		// 将以前的权限清理了,以免权限有缓存
		permissionFeignClient.clearUserPermissionsCache(clearUserPermissionsCacheDTO);
		// 删除该用户在该系统的token
		tokenStore.deleteAllToken(userInfoInToken.getSysType().toString(), userInfoInToken.getUid());
		return ServerResponseEntity.success();
	}

}
