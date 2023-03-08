package com.mall4j.cloud.rbac.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * 菜单管理VO
 *
 * @author FrozenWatermelon
 * @date 2020-09-15 16:35:01
 */
public class MenuSimpleVO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单id" )
    private Long menuId;

    @Schema(description = "父菜单ID，一级菜单为0" )
    private Long parentId;

    @Schema(description = "设置该路由在侧边栏和面包屑中展示的名字" )
    private String title;

	@Schema(description = "菜单权限列表" )
	private List<MenuPermissionSimpleVO> menuPermissions;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MenuPermissionSimpleVO> getMenuPermissions() {
		return menuPermissions;
	}

	public void setMenuPermissions(List<MenuPermissionSimpleVO> menuPermissions) {
		this.menuPermissions = menuPermissions;
	}

	@Override
	public String toString() {
		return "MenuSimpleVO{" +
				"menuId=" + menuId +
				", parentId=" + parentId +
				", title='" + title + '\'' +
				", menuPermissions=" + menuPermissions +
				'}';
	}
}
