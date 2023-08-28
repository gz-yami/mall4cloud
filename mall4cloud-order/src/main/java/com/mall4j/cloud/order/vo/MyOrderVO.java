package com.mall4j.cloud.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

/**
 * 我的订单
 *
 * @author FrozenWatermelon
 */
@Schema(description = "我的订单")
public class MyOrderVO {

	@Schema(description = "订单项" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private List<MyOrderItemVO> orderItems;

	@Schema(description = "订单号" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private Long orderId;

	@Schema(description = "总价" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private Long actualTotal;

	@Schema(description = "订单状态" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer status;

	@Schema(description = "配送类型 3：无需快递" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer deliveryType;

	@Schema(description = "店铺名称" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private String shopName;

	@Schema(description = "店铺id" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private Long shopId;

	@Schema(description = "订单创建时间" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private Date createTime;

	@Schema(description = "商品总数" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer allCount;

	public List<MyOrderItemVO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<MyOrderItemVO> orderItems) {
		this.orderItems = orderItems;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(Long actualTotal) {
		this.actualTotal = actualTotal;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Integer getAllCount() {
		return allCount;
	}

	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
	}

	@Override
	public String toString() {
		return "MyOrderVO{" +
				"orderItems=" + orderItems +
				", orderId=" + orderId +
				", actualTotal=" + actualTotal +
				", status=" + status +
				", deliveryType=" + deliveryType +
				", shopName='" + shopName + '\'' +
				", shopId=" + shopId +
				", createTime=" + createTime +
				", allCount=" + allCount +
				'}';
	}
}
