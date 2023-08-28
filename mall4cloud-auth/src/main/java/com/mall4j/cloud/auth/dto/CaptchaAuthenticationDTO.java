package com.mall4j.cloud.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 验证码登陆
 *
 * @author FrozenWatermelon
 * @date 2020/7/1
 */
public class CaptchaAuthenticationDTO extends AuthenticationDTO {

	@Schema(description = "验证码" , requiredMode = Schema.RequiredMode.REQUIRED)
	private String captchaVerification;

	public String getCaptchaVerification() {
		return captchaVerification;
	}

	public void setCaptchaVerification(String captchaVerification) {
		this.captchaVerification = captchaVerification;
	}


	@Override
	public String toString() {
		return "CaptchaAuthenticationDTO{" + "captchaVerification='" + captchaVerification + '\'' + "} "
				+ super.toString();
	}

}
