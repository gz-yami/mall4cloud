package com.mall4j.cloud.auth.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 验证码登陆
 *
 * @author FrozenWatermelon
 * @date 2020/7/1
 */
public class CaptchaAuthenticationDTO extends AuthenticationDTO {

	@ApiModelProperty(value = "验证码", required = true)
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
