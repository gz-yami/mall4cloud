package com.mall4j.cloud.rbac.model;

import com.mall4j.cloud.common.model.BaseModel;

/**
 * 角色与菜单对应关系
 *
 * @author FrozenWatermelon
 * @date 2020/6/24
 */
public class RoleMenu extends BaseModel {

	/**
	 * 关联id
	 */
	private Long id;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 菜单ID
	 */
	private Long menuId;

	/**
	 * 菜单资源用户id
	 */
	private Long menuPermissionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getMenuPermissionId() {
		return menuPermissionId;
	}

	public void setMenuPermissionId(Long menuPermissionId) {
		this.menuPermissionId = menuPermissionId;
	}

	@Override
	public String toString() {
		return "RoleMenu{" +
				"id=" + id +
				", roleId=" + roleId +
				", menuId=" + menuId +
				", menuPermissionId=" + menuPermissionId +
				"} " + super.toString();
	}
}
