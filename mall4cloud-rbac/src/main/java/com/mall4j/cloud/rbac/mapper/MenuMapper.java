package com.mall4j.cloud.rbac.mapper;

import com.mall4j.cloud.rbac.model.Menu;
import com.mall4j.cloud.rbac.vo.MenuSimpleVO;
import com.mall4j.cloud.rbac.vo.MenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author FrozenWatermelon
 */
public interface MenuMapper {

	/**
	 * 根据菜单管理id获取菜单管理
	 *
	 * @param menuId 菜单管理id
	 * @return 菜单管理
	 */
	MenuVO getByMenuId(@Param("menuId") Long menuId);

	/**
	 * 保存菜单管理
	 *
	 * @param menu 菜单管理
	 */
	void save(@Param("menu") Menu menu);

	/**
	 * 更新菜单管理
	 *
	 * @param menu 菜单管理
	 */
	void update(@Param("menu") Menu menu);

	/**
	 * 根据菜单管理id删除菜单管理
	 *
	 * @param menuId
	 * @param sysType
	 */
	void deleteById(@Param("menuId") Long menuId, @Param("sysType") Integer sysType);

	/**
	 * 根据系统类型获取该系统的菜单列表
	 *
	 * @param sysType 系统类型
	 * @return 菜单列表
	 */
	List<Menu> listBySysType(@Param("sysType") Integer sysType);

	/**
	 * 根据系统类型获取该系统的菜单列表，只有id和名字
	 *
	 * @param sysType sysType 系统类型
	 * @return 简易菜单信息列表
	 */
	List<MenuSimpleVO> listSimpleMenuBySytType(Integer sysType);

	/**
	 * 根据系统类型获取该系统的菜单列表 + 菜单下的权限列表
	 *
	 * @param sysType 系统类型
	 * @return 菜单列表 + 菜单下的权限列表
	 */
	List<MenuSimpleVO> listWithPermissions(Integer sysType);

	/**
	 * 获取当前用户可见的菜单ids
	 *
	 * @param userId  用户id
	 * @return 菜单列表
	 */
	List<Long> listMenuIds(@Param("userId") Long userId);
}
