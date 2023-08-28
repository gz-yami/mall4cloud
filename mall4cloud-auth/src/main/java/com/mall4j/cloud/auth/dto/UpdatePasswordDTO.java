package com.mall4j.cloud.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 更新密码
 *
 * @author FrozenWatermelon
 * @date 2020/09/21
 */
public class UpdatePasswordDTO {

	@NotBlank(message = "oldPassword NotBlank")
	@Schema(description = "旧密码" , requiredMode = Schema.RequiredMode.REQUIRED)
	private String oldPassword;

	@NotNull(message = "newPassword NotNull")
	@Schema(description = "新密码" , requiredMode = Schema.RequiredMode.REQUIRED)
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
