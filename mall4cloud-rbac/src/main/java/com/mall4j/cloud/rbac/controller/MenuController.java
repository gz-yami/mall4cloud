package com.mall4j.cloud.rbac.controller;

import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.common.util.BooleanUtil;
import com.mall4j.cloud.rbac.dto.MenuDTO;
import com.mall4j.cloud.rbac.model.Menu;
import com.mall4j.cloud.rbac.service.MenuService;
import com.mall4j.cloud.rbac.vo.MenuSimpleVO;
import com.mall4j.cloud.rbac.vo.MenuVO;
import com.mall4j.cloud.rbac.vo.RouteMetaVO;
import com.mall4j.cloud.rbac.vo.RouteVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author FrozenWatermelon
 * @date 2020/08/06
 */
@RequestMapping(value = "/menu")
@RestController
@Tag(name = "菜单接口")
public class MenuController {

	@Autowired
	private MenuService menuService;


	@GetMapping(value = "/route")
	@Operation(summary = "路由菜单" , description = "获取当前登陆用户可用的路由菜单列表")
	public ServerResponseEntity<List<RouteVO>> route(Integer sysType) {
		sysType = Objects.isNull(sysType) ? AuthUserContext.get().getSysType(): sysType;
		List<Menu> menus = menuService.listBySysType(sysType);

		List<RouteVO> routes = new ArrayList<>(menus.size());

		for (Menu menu : menus) {
			RouteVO route = new RouteVO();
			route.setAlwaysShow(BooleanUtil.isTrue(menu.getAlwaysShow()));
			route.setComponent(menu.getComponent());
			route.setHidden(BooleanUtil.isTrue(menu.getHidden()));
			route.setName(menu.getName());
			route.setPath(menu.getPath());
			route.setRedirect(menu.getRedirect());
			route.setId(menu.getMenuId());
			route.setParentId(menu.getParentId());
			route.setSeq(menu.getSeq());

			RouteMetaVO meta = new RouteMetaVO();
			meta.setActiveMenu(menu.getActiveMenu());
			meta.setAffix(BooleanUtil.isTrue(menu.getAffix()));
			meta.setBreadcrumb(BooleanUtil.isTrue(menu.getBreadcrumb()));
			meta.setIcon(menu.getIcon());
			meta.setNoCache(BooleanUtil.isTrue(menu.getNoCache()));
			meta.setTitle(menu.getTitle());
			// 对于前端来说角色就是权限
			meta.setRoles(Collections.singletonList(menu.getPermission()));

			route.setMeta(meta);
			routes.add(route);
		}
		return ServerResponseEntity.success(routes);
	}

	@GetMapping
	@Operation(summary = "获取菜单管理" , description = "根据menuId获取菜单管理")
	public ServerResponseEntity<MenuVO> getByMenuId(@RequestParam Long menuId) {
		return ServerResponseEntity.success(menuService.getByMenuId(menuId));
	}

	@PostMapping
	@Operation(summary = "保存菜单管理" , description = "保存菜单管理")
	public ServerResponseEntity<Void> save(@Valid @RequestBody MenuDTO menuDTO) {
		Menu menu = checkAndGenerate(menuDTO);
		menu.setMenuId(null);
		menuService.save(menu);
		return ServerResponseEntity.success();
	}

	private Menu checkAndGenerate(@RequestBody @Valid MenuDTO menuDTO) {
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		if(!Objects.equals(userInfoInTokenBO.getTenantId(),0L)){
			throw new Mall4cloudException("无权限操作！");
		}
		Menu menu = BeanUtil.map(menuDTO, Menu.class);
		menu.setBizType(menuDTO.getSysType());
		if(Objects.isNull(menuDTO.getSysType())){
			menu.setBizType(AuthUserContext.get().getSysType());
		}
		return menu;
	}

	@PutMapping
	@Operation(summary = "更新菜单管理" , description = "更新菜单管理")
	public ServerResponseEntity<Void> update(@Valid @RequestBody MenuDTO menuDTO) {
		Menu menu = checkAndGenerate(menuDTO);
		menuService.update(menu);
		return ServerResponseEntity.success();
	}

	@DeleteMapping
	@Operation(summary = "删除菜单管理" , description = "根据菜单管理id删除菜单管理")
	public ServerResponseEntity<Void> delete(@RequestParam("menuId") Long menuId,@RequestParam("sysType") Integer sysType) {
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		if(!Objects.equals(userInfoInTokenBO.getTenantId(),0L)){
			throw new Mall4cloudException("无权限操作！");
		}
		sysType = Objects.isNull(sysType) ? userInfoInTokenBO.getSysType():sysType;
		menuService.deleteById(menuId,sysType);
		return ServerResponseEntity.success();
	}

	@GetMapping(value = "/list_with_permissions")
	@Operation(summary = "菜单列表和按钮列表" , description = "根据系统类型获取该系统的菜单列表 + 菜单下的权限列表")
	public ServerResponseEntity<List<MenuSimpleVO>> listWithPermissions() {
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		List<MenuSimpleVO> menuList = menuService.listWithPermissions(userInfoInTokenBO.getSysType());
		return ServerResponseEntity.success(menuList);
	}

	@GetMapping(value = "/list_menu_ids")
	@Operation(summary = "获取当前用户可见的菜单ids" , description = "获取当前用户可见的菜单id")
	public ServerResponseEntity<List<Long>> listMenuIds() {
		UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
		List<Long> menuList = menuService.listMenuIds(userInfoInTokenBO.getUserId());
		return ServerResponseEntity.success(menuList);
	}
}
