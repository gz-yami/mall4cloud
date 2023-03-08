package com.mall4j.cloud.rbac.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 菜单资源VO
 *
 * @author FrozenWatermelon
 * @date 2020-09-15 16:35:01
 */
public class MenuPermissionVO {
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单资源用户id" )
    private Long menuPermissionId;

    @Schema(description = "资源关联菜单" )
    private Long menuId;

	@Schema(description = "菜单标题" )
	private String menuTitle;

    @Schema(description = "权限对应的编码" )
    private String permission;

    @Schema(description = "资源名称" )
    private String name;

    @Schema(description = "资源对应服务器路径" )
    private String uri;

    @Schema(description = "请求方法 1.GET 2.POST 3.PUT 4.DELETE" )
    private Integer method;

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

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	@Override
	public String toString() {
		return "MenuPermissionVO{" +
				"menuPermissionId=" + menuPermissionId +
				", menuId=" + menuId +
				", menuTitle='" + menuTitle + '\'' +
				", permission='" + permission + '\'' +
				", name='" + name + '\'' +
				", uri='" + uri + '\'' +
				", method=" + method +
				'}';
	}
}
