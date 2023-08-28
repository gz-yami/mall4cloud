package com.mall4j.cloud.api.rbac.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author FrozenWatermelon
 * @date 2020/9/2
 */
public class CheckPermissionDTO {

	/**
	 * 用户id
	 */
	@NotNull(message = "userId not null")
	private Long userId;

	/**
	 * 系统类型
	 */
	@NotNull(message = "sysType not null")
	private Integer sysType;

	/**
	 * uri
	 */
	@NotBlank(message = "uri not blank")
	private String uri;

	/**
	 * 是否是管理员
	 */
	@NotNull(message = "isAdmin not null")
	private Integer isAdmin;

	/**
	 * 请求方法 1.GET 2.POST 3.PUT 4.DELETE
	 */
	private Integer method;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getSysType() {
		return sysType;
	}

	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "CheckPermissionDTO{" + "userId=" + userId + ", sysType=" + sysType + ", uri='" + uri + '\''
				+ ", isAdmin=" + isAdmin + ", method=" + method + '}';
	}

}
