package com.mall4j.cloud.rbac.model;

import com.mall4j.cloud.common.model.BaseModel;

/**
 * 用户与角色对应关系
 *
 * @author FrozenWatermelon
 * @date 2020/6/24
 */
public class UserRole extends BaseModel {

	/**
	 * 关联id
	 */
	private Long id;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 角色ID
	 */
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserRole{" + "id=" + id + ", userId=" + userId + ", roleId=" + roleId + "} " + super.toString();
	}

}
