package com.mall4j.cloud.api.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * token信息，该信息用户返回给前端，前端请求携带accessToken进行用户校验
 *
 * @author FrozenWatermelon
 * @date 2020/7/2
 */
public class TokenInfoVO {

	@Schema(description = "accessToken" )
	private String accessToken;

	@Schema(description = "refreshToken" )
	private String refreshToken;

	@Schema(description = "在多少秒后过期" )
	private Integer expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "TokenInfoVO{" + "accessToken='" + accessToken + '\'' + ", refreshToken='" + refreshToken + '\''
				+ ", expiresIn=" + expiresIn + '}';
	}

}
