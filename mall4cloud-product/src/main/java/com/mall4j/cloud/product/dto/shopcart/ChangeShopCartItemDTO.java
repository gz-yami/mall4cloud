package com.mall4j.cloud.product.dto.shopcart;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author FrozenWatermelon
 * @date 2020-11-20 15:47:32
 */
public class ChangeShopCartItemDTO {

    @ApiModelProperty(value = "购物车ID", required = true)
    private Long shopCartItemId;

    @NotNull(message = "商品ID不能为空")
    @ApiModelProperty(value = "商品ID", required = true)
    private Long spuId;

    @ApiModelProperty(value = "旧的skuId 如果传过来说明在变更sku", required = true)
    private Long oldSkuId;

    @NotNull(message = "skuId不能为空")
    @ApiModelProperty(value = "skuId", required = true)
    private Long skuId;

    @ApiModelProperty(value = "店铺ID，前端不用传该字段")
    private Long shopId;

    @NotNull(message = "商品个数不能为空")
    @ApiModelProperty(value = "商品个数", required = true)
    private Integer count;

    @ApiModelProperty(value = "商品是否勾选 true：勾选 ")
    private Boolean isCheck;

    public Long getShopCartItemId() {
        return shopCartItemId;
    }

    public void setShopCartItemId(Long shopCartItemId) {
        this.shopCartItemId = shopCartItemId;
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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public Long getOldSkuId() {
        return oldSkuId;
    }

    public void setOldSkuId(Long oldSkuId) {
        this.oldSkuId = oldSkuId;
    }

    @Override
    public String toString() {
        return "ChangeShopCartItemDTO{" +
                "shopCartItemId=" + shopCartItemId +
                ", spuId=" + spuId +
                ", oldSkuId=" + oldSkuId +
                ", skuId=" + skuId +
                ", shopId=" + shopId +
                ", count=" + count +
                ", isCheck=" + isCheck +
                '}';
    }
}
