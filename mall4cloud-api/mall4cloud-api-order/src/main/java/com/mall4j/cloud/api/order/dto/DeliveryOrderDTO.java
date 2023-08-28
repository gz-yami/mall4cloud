package com.mall4j.cloud.api.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 订单快递信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-12-07 15:10:00
 */
public class DeliveryOrderDTO{

	@Schema(description = "deliveryOrderId" )
	private Long deliveryOrderId;

	@NotNull(message="订单号不能为空")
	@Schema(description = "订单号" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private Long orderId;

	@NotNull(message="发货方式不能为空")
	@Schema(description = "发货方式" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer deliveryType;

	private List<DeliveryOrderItemDTO> selectOrderItems;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public List<DeliveryOrderItemDTO> getSelectOrderItems() {
		return selectOrderItems;
	}

	public void setSelectOrderItems(List<DeliveryOrderItemDTO> selectOrderItems) {
		this.selectOrderItems = selectOrderItems;
	}

	public Long getDeliveryOrderId() {
		return deliveryOrderId;
	}

	public void setDeliveryOrderId(Long deliveryOrderId) {
		this.deliveryOrderId = deliveryOrderId;
	}

	@Override
	public String toString() {
		return "DeliveryOrderDTO{" +
				"deliveryOrderId='" + deliveryOrderId + '\'' +
				"orderNumber='" + orderId + '\'' +
				", deliveryType=" + deliveryType +
				", selectOrderItems=" + selectOrderItems +
				'}';
	}
}
