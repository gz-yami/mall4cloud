package com.mall4j.cloud.order.dto.multishop;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 订单参数
 *
 * @author FrozenWatermelon
 * @date 2020-12-04 11:27:35
 */
public class OrderAdminDTO {

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @NotNull(message = "配送类型不能为空")
    @ApiModelProperty(value = "配送类型 3：无需快递")
    private Integer dvyType;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getDvyType() {
        return dvyType;
    }

    public void setDvyType(Integer dvyType) {
        this.dvyType = dvyType;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                ", orderId=" + orderId +
                ", shopId=" + shopId +
                ", dvyType=" + dvyType +
                '}';
    }
}
