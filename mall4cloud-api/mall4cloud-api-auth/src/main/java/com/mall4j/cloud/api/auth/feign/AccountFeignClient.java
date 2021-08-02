package com.mall4j.cloud.api.auth.feign;

import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.auth.constant.SysTypeEnum;
import com.mall4j.cloud.api.auth.dto.AuthAccountDTO;
import com.mall4j.cloud.api.auth.vo.AuthAccountVO;
import com.mall4j.cloud.api.auth.vo.TokenInfoVO;
import com.mall4j.cloud.common.feign.FeignInsideAuthConfig;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author FrozenWatermelon
 * @date 2020/9/22
 */
@FeignClient(value = "mall4cloud-auth",contextId ="account")
public interface AccountFeignClient {

	/**
	 * 保存统一账户
	 * @param authAccountDTO 账户信息
	 * @return Long uid
	 */
	@PostMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/account")
	ServerResponseEntity<Long> save(@RequestBody AuthAccountDTO authAccountDTO);

	/**
	 * 更新统一账户
	 * @param authAccountDTO 账户信息
	 * @return void
	 */
	@PutMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/account")
	ServerResponseEntity<Void> update(@RequestBody AuthAccountDTO authAccountDTO);

	/**
	 * 更新账户状态
	 * @param authAccountDTO 账户信息
	 * @return void
	 */
	@PutMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/account/status")
	ServerResponseEntity<Void> updateAuthAccountStatus(@RequestBody AuthAccountDTO authAccountDTO);

	/**
	 * 根据用户id和系统类型删除用户
	 * @param userId 用户id
	 * @return void
	 */
	@DeleteMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/account/deleteByUserIdAndSysType")
	ServerResponseEntity<Void> deleteByUserIdAndSysType(@RequestParam("userId")Long userId);

	/**
	 * 根据用户id和系统类型获取用户信息
	 * @param userId 用户id
	 * @param sysType 系统类型
	 * @return void
	 */
	@GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/account/getByUserIdAndSysType")
	ServerResponseEntity<AuthAccountVO> getByUserIdAndSysType(@RequestParam("userId") Long userId,@RequestParam("sysType") Integer sysType);

	/**
	 * 保存用户信息，生成token，返回前端
	 * @param userInfoInTokenBO 账户信息 和社交账号信息
	 * @return uid
	 */
	@PostMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/storeTokenAndGetVo")
	ServerResponseEntity<TokenInfoVO> storeTokenAndGetVo(@RequestBody UserInfoInTokenBO userInfoInTokenBO);

	/**
	 * 根据用户名和系统类型获取用户信息
	 * @param username
	 * @param sysType
	 * @return
	 */
	@PostMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/getByUsernameAndSysType")
	ServerResponseEntity<AuthAccountVO> getByUsernameAndSysType(@RequestParam("userName") String username, @RequestParam("sysType") SysTypeEnum sysType);

	/**
	 * 根据用户id与用户类型更新用户信息
	 * @param userInfoInTokenBO 新的用户信息
	 * @param userId 用户id
	 * @param sysType 用户类型
	 * @return
	 */
	@PutMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/accout/updateTenantIdByUserIdAndSysType")
	ServerResponseEntity<Void> updateUserInfoByUserIdAndSysType(@RequestBody UserInfoInTokenBO userInfoInTokenBO, @RequestParam("userId") Long userId, @RequestParam("sysType") Integer sysType);

	/**
	 * 根据租户id查询商家信息
	 * @param tenantId
	 * @return
	 */
	@GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/account/getMerchantInfoByTenantId")
	ServerResponseEntity<AuthAccountVO> getMerchantInfoByTenantId(@RequestParam("tenantId") Long tenantId);
}
