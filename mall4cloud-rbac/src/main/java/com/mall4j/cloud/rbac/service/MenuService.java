package com.mall4j.cloud.rbac.service;

import com.mall4j.cloud.rbac.model.Menu;

import java.util.List;

import com.mall4j.cloud.rbac.vo.MenuSimpleVO;
import com.mall4j.cloud.rbac.vo.MenuVO;

/**
 * 菜单管理
 *
 * @author FrozenWatermelon
 * @date 2020-09-14 16:27:55
 */
public interface MenuService {

	/**
	 * 根据菜单管理id获取菜单管理
	 *
	 * @param menuId 菜单管理id
	 * @return 菜单管理
	 */
	MenuVO getByMenuId(Long menuId);

	/**
	 * 保存菜单管理
	 * @param menu 菜单管理
	 */
	void save(Menu menu);

	/**
	 * 更新菜单管理
	 * @param menu 菜单管理
	 */
	void update(Menu menu);

	/**
	 * 根据菜单管理id删除菜单管理
	 * @param menuId 菜单id
	 * @param sysType 系统类型
	 */
	void deleteById(Long menuId, Integer sysType);

	/**
	 * 根据系统类型获取该系统的菜单列表
	 * @param sysType 系统类型
	 * @return 菜单列表
	 */
	List<Menu> listBySysType(Integer sysType);

	/**
	 * 根据系统类型获取该系统的菜单列表 + 菜单下的权限列表
	 * @param sysType 系统类型
	 * @return 菜单列表 + 菜单下的权限列表
	 */
	List<MenuSimpleVO> listWithPermissions(Integer sysType);

	/**
	 * 获取当前用户可见的菜单ids
	 *  @param userId 用户id
	 * @return 菜单列表
	 */
	List<Long> listMenuIds(Long userId);
}
