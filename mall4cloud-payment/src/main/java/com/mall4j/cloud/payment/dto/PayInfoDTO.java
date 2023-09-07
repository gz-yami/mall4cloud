package com.mall4j.cloud.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * 订单支付记录DTO
 *
 * @author FrozenWatermelon
 * @date 2020-12-25 09:50:59
 */
public class PayInfoDTO{

	@NotEmpty(message = "订单号不能为空")
	@Schema(description = "订单号" , requiredMode = Schema.RequiredMode.REQUIRED)
	private List<Long> orderIds;

	@Schema(description = "支付完成回跳地址" , requiredMode = Schema.RequiredMode.REQUIRED)
	private String returnUrl;

	public List<Long> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<Long> orderIds) {
		this.orderIds = orderIds;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	@Override
	public String toString() {
		return "PayInfoDTO{" +
				"orderIds=" + orderIds +
				", returnUrl='" + returnUrl + '\'' +
				'}';
	}
}
