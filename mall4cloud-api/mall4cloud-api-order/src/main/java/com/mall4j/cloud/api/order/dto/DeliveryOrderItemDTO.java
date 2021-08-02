package com.mall4j.cloud.api.order.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 物流订单项信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-12-07 15:10:00
 */
public class DeliveryOrderItemDTO{

    @ApiModelProperty("id")
    private Long orderItemId;

    @ApiModelProperty("商品图片")
    private String pic;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("发货改变的数量")
    private Integer changeNum;

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getSpuName() {
		return spuName;
	}

	public void setSpuName(String spuName) {
		this.spuName = spuName;
	}

	public Integer getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(Integer changeNum) {
		this.changeNum = changeNum;
	}

	@Override
	public String toString() {
		return "DeliveryOrderItemDTO{" +
				"orderItemId=" + orderItemId +
				", pic='" + pic + '\'' +
				", spuName='" + spuName + '\'' +
				", changeNum=" + changeNum +
				'}';
	}
}
