package com.mall4j.cloud.rbac.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * 角色DTO
 *
 * @author FrozenWatermelon
 * @date 2020-09-17 19:15:44
 */
public class RoleDTO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色id" )
    private Long roleId;

    @Schema(description = "角色名称" )
    private String roleName;

    @Schema(description = "备注" )
    private String remark;

	@Schema(description = "菜单id列表" )
	private List<Long> menuIds;

	@Schema(description = "菜单资源id列表" )
	private List<Long> menuPermissionIds;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Long> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}

	public List<Long> getMenuPermissionIds() {
		return menuPermissionIds;
	}

	public void setMenuPermissionIds(List<Long> menuPermissionIds) {
		this.menuPermissionIds = menuPermissionIds;
	}

	@Override
	public String toString() {
		return "RoleDTO{" +
				"roleId=" + roleId +
				", roleName='" + roleName + '\'' +
				", remark='" + remark + '\'' +
				", menuIds=" + menuIds +
				", menuPermissionIds=" + menuPermissionIds +
				'}';
	}
}
