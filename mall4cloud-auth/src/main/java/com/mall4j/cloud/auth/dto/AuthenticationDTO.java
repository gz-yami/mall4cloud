package com.mall4j.cloud.auth.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用于登陆传递账号密码
 *
 * @author FrozenWatermelon
 * @date 2020/7/1
 */
public class AuthenticationDTO {

	/**
	 * 用户名
	 */
	@NotBlank(message = "principal不能为空")
	@ApiModelProperty(value = "用户名", required = true)
	protected String principal;

	/**
	 * 密码
	 */
	@NotBlank(message = "credentials不能为空")
	@ApiModelProperty(value = "一般用作密码", required = true)
	protected String credentials;

	/**
	 * sysType 参考SysTypeEnum
	 */
	@NotNull(message = "sysType不能为空")
	@ApiModelProperty(value = "系统类型 0.普通用户系统 1.商家端", required = true)
	protected Integer sysType;

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public Integer getSysType() {
		return sysType;
	}

	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}

	@Override
	public String toString() {
		return "AuthenticationDTO{" +
				"principal='" + principal + '\'' +
				", credentials='" + credentials + '\'' +
				", sysType=" + sysType +
				'}';
	}
}
