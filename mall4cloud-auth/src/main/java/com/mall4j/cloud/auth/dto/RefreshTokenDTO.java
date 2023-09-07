package com.mall4j.cloud.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

/**
 * 刷新token
 *
 * @author FrozenWatermelon
 * @date 2020/7/1
 */
public class RefreshTokenDTO {

	/**
	 * refreshToken
	 */
	@NotBlank(message = "refreshToken不能为空")
	@Schema(description = "refreshToken" , requiredMode = Schema.RequiredMode.REQUIRED)
	private String refreshToken;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "RefreshTokenDTO{" + "refreshToken='" + refreshToken + '\'' + '}';
	}

}
