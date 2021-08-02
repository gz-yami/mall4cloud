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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/09/02
 */
@RequestMapping(value = "/menu_permission")
@RestController
@Api(tags = "权限接口")
public class MenuPermissionController {

	@Autowired
	private MenuPermissionService menuPermissionService;

	@Autowired
	private MapperFacade mapperFacade;

	@GetMapping("/list_by_menu")
	@ApiOperation(value = "获取菜单资源列表", notes = "分页获取菜单资源列表")
	public ServerResponseEntity<List<MenuPermissionVO>> listByMenuId(Long menuId) {
		List<MenuPermissionVO> menuPermissionVOList = menuPermissionService.listByMenuId(menuId);
		return ServerResponseEntity.success(menuPermissionVOList);
	}

	@GetMapping
	@ApiOperation(value = "获取菜单资源", notes = "根据menuPermissionId获取菜单资源")
	public ServerResponseEntity<MenuPermissionVO> getByMenuPermissionId(@RequestParam Long menuPermissionId) {
		return ServerResponseEntity.success(menuPermissionService.getByMenuPermissionId(menuPermissionId));
	}

	@PostMapping
	@ApiOperation(value = "保存菜单资源", notes = "保存菜单资源")
	public ServerResponseEntity<Void> save(@Valid @RequestBody MenuPermissionDTO menuPermissionDTO) {
		MenuPermission menuPermission = mapperFacade.map(menuPermissionDTO, MenuPermission.class);
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		menuPermission.setMenuPermissionId(null);
		menuPermission.setBizType(userInfoInTokenBO.getSysType());
		return menuPermissionService.save(menuPermission);
	}

	@PutMapping
	@ApiOperation(value = "更新菜单资源", notes = "更新菜单资源")
	public ServerResponseEntity<Void> update(@Valid @RequestBody MenuPermissionDTO menuPermissionDTO) {
		MenuPermission menuPermission = mapperFacade.map(menuPermissionDTO, MenuPermission.class);
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		menuPermission.setBizType(userInfoInTokenBO.getSysType());
		return menuPermissionService.update(menuPermission);
	}

	@DeleteMapping
	@ApiOperation(value = "删除菜单资源", notes = "根据菜单资源id删除菜单资源")
	public ServerResponseEntity<Void> delete(@RequestParam Long menuPermissionId) {
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		menuPermissionService.deleteById(menuPermissionId,userInfoInTokenBO.getSysType());
		return ServerResponseEntity.success();
	}

	@GetMapping(value = "/list")
	@ApiOperation(value = "获取当前用户拥有的权限", notes = "当前用户所拥有的所有权限，精确到按钮，实际上element admin里面的roles就可以理解成权限")
	public ServerResponseEntity<List<String>> permissions() {
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		return ServerResponseEntity.success(menuPermissionService.listUserPermissions(userInfoInTokenBO.getUserId(),
				userInfoInTokenBO.getSysType(), BooleanUtil.isTrue(userInfoInTokenBO.getIsAdmin())));
	}

	@GetMapping(value = "/page")
	@ApiOperation(value = "获取当前用户拥有的权限", notes = "当前用户所拥有的所有权限，精确到按钮，实际上element admin里面的roles就可以理解成权限")
	public ServerResponseEntity<PageVO<MenuPermissionVO>> pagePermissions(PageDTO pageDTO) {
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		PageVO<MenuPermissionVO> permissionPage = menuPermissionService.page(pageDTO, userInfoInTokenBO.getSysType());
		return ServerResponseEntity.success(permissionPage);
	}

}
