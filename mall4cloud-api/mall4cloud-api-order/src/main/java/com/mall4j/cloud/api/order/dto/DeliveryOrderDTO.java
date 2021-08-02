package com.mall4j.cloud.api.order.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 订单快递信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-12-07 15:10:00
 */
public class DeliveryOrderDTO{

	@ApiModelProperty(value = "deliveryOrderId")
	private Long deliveryOrderId;

	@NotNull(message="订单号不能为空")
	@ApiModelProperty(value = "订单号",required=true)
	private Long orderId;

	@NotNull(message="发货方式不能为空")
	@ApiModelProperty(value = "发货方式",required=true)
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
