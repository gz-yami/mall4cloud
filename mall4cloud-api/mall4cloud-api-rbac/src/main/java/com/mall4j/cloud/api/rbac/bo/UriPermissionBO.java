package com.mall4j.cloud.api.rbac.bo;

/**
 * uri权限bo
 *
 * @author FrozenWatermelon
 * @date 2020/9/3
 */
public class UriPermissionBO {

	/**
	 * 请求方法 1.GET 2.POST 3.PUT 4.DELETE
	 */
	private Integer method;

	/**
	 * uri
	 */
	private String uri;

	/**
	 * permission
	 */
	private String permission;

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "UriPermissionBO{" + "method=" + method + ", uri='" + uri + '\'' + ", permission='" + permission + '\''
				+ '}';
	}

}
