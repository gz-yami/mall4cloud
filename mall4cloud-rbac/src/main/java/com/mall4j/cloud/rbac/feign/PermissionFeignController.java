package com.mall4j.cloud.rbac.feign;

import com.mall4j.cloud.api.rbac.bo.UriPermissionBO;
import com.mall4j.cloud.api.rbac.dto.ClearUserPermissionsCacheDTO;
import com.mall4j.cloud.api.rbac.feign.PermissionFeignClient;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.util.BooleanUtil;
import com.mall4j.cloud.rbac.service.MenuPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author FrozenWatermelon
 * @date 2020/7/15
 */
@RestController
public class PermissionFeignController implements PermissionFeignClient {

	private static final Logger logger = LoggerFactory.getLogger(PermissionFeignController.class);

	@Autowired
	private MenuPermissionService menuPermissionService;

	@Override
	public ServerResponseEntity<Boolean> checkPermission(@RequestParam("userId") Long userId, @RequestParam("sysType") Integer sysType, @RequestParam("uri") String uri, @RequestParam("isAdmin") Integer isAdmin, @RequestParam("method") Integer method) {
		// 目前该用户拥有的权限
		List<String> userPermissions = menuPermissionService.listUserPermissions(userId,
				sysType, BooleanUtil.isTrue(isAdmin));

		// uri,方法对应的权限 map
		List<UriPermissionBO> uriPermissions = menuPermissionService.listUriPermissionInfo(sysType);

		AntPathMatcher pathMatcher = new AntPathMatcher();

		// 看看该uri对应需要什么权限
		for (UriPermissionBO uriPermission : uriPermissions) {
			// uri + 请求方式匹配
			if (pathMatcher.match(uriPermission.getUri(), uri) && Objects.equals(uriPermission.getMethod(), method)) {
				// uri 用户有这个权限
				if (userPermissions.contains(uriPermission.getPermission())) {
					return ServerResponseEntity.success(Boolean.TRUE);
				}
				else {
					return ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED);
				}
			}
		}

		// 如果uri 没有匹配到，则说明uri不需要权限，直接校验成功
		return ServerResponseEntity.success(Boolean.TRUE);
	}

	@Override
	public ServerResponseEntity<Void> clearUserPermissionsCache(ClearUserPermissionsCacheDTO clearUserPermissionsCacheDTO) {
		menuPermissionService.clearUserPermissionsCache(clearUserPermissionsCacheDTO.getUserId(), clearUserPermissionsCacheDTO.getSysType());
		return ServerResponseEntity.success();
	}

}
