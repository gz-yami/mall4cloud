package com.mall4j.cloud.product.dto.shopcart;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author FrozenWatermelon
 * @date 2021-02-03 15:47:32
 */
public class CheckShopCartItemDTO {

    @NotNull
    @ApiModelProperty(value = "购物车ID", required = true)
    private Long shopCartItemId;

    @NotNull
    @ApiModelProperty(value = "商品是否勾选 1:勾选 0:未勾选")
    private Integer isChecked;

    public Long getShopCartItemId() {
        return shopCartItemId;
    }

    public void setShopCartItemId(Long shopCartItemId) {
        this.shopCartItemId = shopCartItemId;
    }

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "CheckShopCartItemDTO{" +
                "shopCartItemId=" + shopCartItemId +
                ", isChecked=" + isChecked +
                '}';
    }
}
