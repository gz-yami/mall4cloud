package com.mall4j.cloud.auth.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 更新密码
 *
 * @author FrozenWatermelon
 * @date 2020/09/21
 */
public class UpdatePasswordDTO {

	@NotBlank(message = "oldPassword NotBlank")
	@ApiModelProperty(value = "旧密码", required = true)
	private String oldPassword;

	@NotNull(message = "newPassword NotNull")
	@ApiModelProperty(value = "新密码", required = true)
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "UpdatePasswordDTO{" +
				"oldPassword='" + oldPassword + '\'' +
				", newPassword='" + newPassword + '\'' +
				'}';
	}
}
