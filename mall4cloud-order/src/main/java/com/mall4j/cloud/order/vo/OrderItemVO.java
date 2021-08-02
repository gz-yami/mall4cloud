package com.mall4j.cloud.order.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * 订单项VO
 *
 * @author FrozenWatermelon
 * @date 2020-12-04 11:27:35
 */
public class OrderItemVO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单项ID")
    private Long orderItemId;

    @ApiModelProperty("店铺id")
    private Long shopId;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("产品ID")
    private Long spuId;

    @ApiModelProperty("产品SkuID")
    private Long skuId;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("购物车产品个数")
    private Integer count;

    @ApiModelProperty("产品名称")
    private String spuName;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("产品主图片路径")
    private String pic;

    @ApiModelProperty("单个orderItem的配送类型 3：无需快递")
    private Integer deliveryType;

    @ApiModelProperty("加入购物车时间")
    private Date shopCartTime;

    @ApiModelProperty("产品价格")
    private Long price;

    @ApiModelProperty("商品总金额")
    private Long spuTotalAmount;

	@ApiModelProperty("发货改变的数量")
	private Integer changeNum;

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getSpuName() {
		return spuName;
	}

	public void setSpuName(String spuName) {
		this.spuName = spuName;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Date getShopCartTime() {
		return shopCartTime;
	}

	public void setShopCartTime(Date shopCartTime) {
		this.shopCartTime = shopCartTime;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getSpuTotalAmount() {
		return spuTotalAmount;
	}

	public void setSpuTotalAmount(Long spuTotalAmount) {
		this.spuTotalAmount = spuTotalAmount;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Integer getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(Integer changeNum) {
		this.changeNum = changeNum;
	}

	@Override
	public String toString() {
		return "OrderItemVO{" +
				"orderItemId=" + orderItemId +
				", shopId=" + shopId +
				", orderId=" + orderId +
				", spuId=" + spuId +
				", skuId=" + skuId +
				", userId=" + userId +
				", count=" + count +
				", spuName='" + spuName + '\'' +
				", skuName='" + skuName + '\'' +
				", pic='" + pic + '\'' +
				", deliveryType=" + deliveryType +
				", shopCartTime=" + shopCartTime +
				", price=" + price +
				", spuTotalAmount=" + spuTotalAmount +
				", changeNum=" + changeNum +
				'}';
	}
}
