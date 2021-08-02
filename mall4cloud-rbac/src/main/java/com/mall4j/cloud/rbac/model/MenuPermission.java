package com.mall4j.cloud.rbac.model;

import java.io.Serializable;

import com.mall4j.cloud.common.model.BaseModel;
/**
 * 菜单资源
 *
 * @author FrozenWatermelon
 * @date 2020-09-15 16:36:50
 */
public class MenuPermission extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 菜单资源用户id
     */
    private Long menuPermissionId;

    /**
     * 资源关联菜单
     */
    private Long menuId;

    /**
     * 业务类型 0平台菜单 1 店铺菜单
     */
    private Integer bizType;

    /**
     * 权限对应的编码
     */
    private String permission;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源对应服务器路径
     */
    private String uri;

    /**
     * 请求方法 1.GET 2.POST 3.PUT 4.DELETE
     */
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

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
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

	@Override
	public String toString() {
		return "MenuPermissionVO{" +
				"menuPermissionId=" + menuPermissionId +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				",menuId=" + menuId +
				",bizType=" + bizType +
				",permission=" + permission +
				",name=" + name +
				",uri=" + uri +
				",method=" + method +
				'}';
	}
}
