package com.mall4j.cloud.multishop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 更新密码
 *
 * @author FrozenWatermelon
 * @date 2020/09/21
 */
public class UpdateShopPasswordDTO {

	@NotBlank(message = "confirmPsw NotBlank")
	@Schema(description = "确认密码" , requiredMode = Schema.RequiredMode.REQUIRED)
	private String confirmPsw;

	@NotNull(message = "password NotNull")
	@Schema(description = "新密码" , requiredMode = Schema.RequiredMode.REQUIRED)
	private String password;

	@NotNull(message = "shopId NotNull")
	@Schema(description = "店铺id" , requiredMode = Schema.RequiredMode.REQUIRED)
	private Long shopId;

	public String getConfirmPsw() {
		return confirmPsw;
	}

	public void setConfirmPsw(String confirmPsw) {
		this.confirmPsw = confirmPsw;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Override
	public String toString() {
		return "UpdateShopPasswordDTO{" +
				"confirmPsw='" + confirmPsw + '\'' +
				", password='" + password + '\'' +
				", shopId=" + shopId +
				'}';
	}
}
