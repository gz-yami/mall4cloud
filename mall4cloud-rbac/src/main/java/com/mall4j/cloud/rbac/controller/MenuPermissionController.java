package com.mall4j.cloud.rbac.controller;

import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.common.util.BooleanUtil;
import com.mall4j.cloud.rbac.dto.MenuPermissionDTO;
import com.mall4j.cloud.rbac.model.MenuPermission;
import com.mall4j.cloud.rbac.service.MenuPermissionService;
import com.mall4j.cloud.rbac.vo.MenuPermissionVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/09/02
 */
@RequestMapping(value = "/menu_permission")
@RestController
@Tag(name = "权限接口")
public class MenuPermissionController {

	@Autowired
	private MenuPermissionService menuPermissionService;


	@GetMapping("/list_by_menu")
	@Operation(summary = "获取菜单资源列表" , description = "分页获取菜单资源列表")
	public ServerResponseEntity<List<MenuPermissionVO>> listByMenuId(Long menuId) {
		List<MenuPermissionVO> menuPermissionVOList = menuPermissionService.listByMenuId(menuId);
		return ServerResponseEntity.success(menuPermissionVOList);
	}

	@GetMapping
	@Operation(summary = "获取菜单资源" , description = "根据menuPermissionId获取菜单资源")
	public ServerResponseEntity<MenuPermissionVO> getByMenuPermissionId(@RequestParam Long menuPermissionId) {
		return ServerResponseEntity.success(menuPermissionService.getByMenuPermissionId(menuPermissionId));
	}

	@PostMapping
	@Operation(summary = "保存菜单资源" , description = "保存菜单资源")
	public ServerResponseEntity<Void> save(@Valid @RequestBody MenuPermissionDTO menuPermissionDTO) {
		MenuPermission menuPermission = BeanUtil.map(menuPermissionDTO, MenuPermission.class);
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		menuPermission.setMenuPermissionId(null);
		menuPermission.setBizType(userInfoInTokenBO.getSysType());
		return menuPermissionService.save(menuPermission);
	}

	@PutMapping
	@Operation(summary = "更新菜单资源" , description = "更新菜单资源")
	public ServerResponseEntity<Void> update(@Valid @RequestBody MenuPermissionDTO menuPermissionDTO) {
		MenuPermission menuPermission = BeanUtil.map(menuPermissionDTO, MenuPermission.class);
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		menuPermission.setBizType(userInfoInTokenBO.getSysType());
		return menuPermissionService.update(menuPermission);
	}

	@DeleteMapping
	@Operation(summary = "删除菜单资源" , description = "根据菜单资源id删除菜单资源")
	public ServerResponseEntity<Void> delete(@RequestParam Long menuPermissionId) {
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		menuPermissionService.deleteById(menuPermissionId,userInfoInTokenBO.getSysType());
		return ServerResponseEntity.success();
	}

	@GetMapping(value = "/list")
	@Operation(summary = "获取当前用户拥有的权限" , description = "当前用户所拥有的所有权限，精确到按钮，实际上element admin里面的roles就可以理解成权限")
	public ServerResponseEntity<List<String>> permissions() {
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		return ServerResponseEntity.success(menuPermissionService.listUserPermissions(userInfoInTokenBO.getUserId(),
				userInfoInTokenBO.getSysType(), BooleanUtil.isTrue(userInfoInTokenBO.getIsAdmin())));
	}

	@GetMapping(value = "/page")
	@Operation(summary = "获取当前用户拥有的权限" , description = "当前用户所拥有的所有权限，精确到按钮，实际上element admin里面的roles就可以理解成权限")
	public ServerResponseEntity<PageVO<MenuPermissionVO>> pagePermissions(PageDTO pageDTO) {
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		PageVO<MenuPermissionVO> permissionPage = menuPermissionService.page(pageDTO, userInfoInTokenBO.getSysType());
		return ServerResponseEntity.success(permissionPage);
	}

}
