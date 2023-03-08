package com.mall4j.cloud.rbac.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 菜单资源简易VO
 *
 * @author FrozenWatermelon
 * @date 2020-09-17 16:35:01
 */
public class MenuPermissionSimpleVO {
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单资源用户id" )
    private Long menuPermissionId;

    @Schema(description = "资源关联菜单" )
    private Long menuId;

    @Schema(description = "资源名称" )
    private String name;

	public Long getMenuPermissionId() {
		return menuPermissionId;
	}

	public void setMenuPermissionId(Long menuPermissionId) {
		this.menuPermissionId = menuPermissionId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "MenuPermissionVO{" +
				"menuPermissionId=" + menuPermissionId +
				",menuId=" + menuId +
				",name=" + name +
				'}';
	}
}
