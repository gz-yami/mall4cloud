package com.mall4j.cloud.api.product.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author FrozenWatermelon
 * @date 2020/12/22
 */
public class SkuStockLockDTO {

    @NotNull(message = "产品ID不能为空")
    @ApiModelProperty(value = "产品ID",required=true)
    private Long spuId;

    @NotNull(message = "skuId不能为空")
    @ApiModelProperty(value = "skuId",required=true)
    private Long skuId;

    @NotNull(message = "orderId不能为空")
    @ApiModelProperty(value = "orderId",required=true)
    private Long orderId;

    @NotNull(message = "商品数量不能为空")
    @Min(value = 1,message = "商品数量不能为空")
    @ApiModelProperty(value = "商品数量",required=true)
    private Integer count;

    public SkuStockLockDTO() {
    }

    public SkuStockLockDTO(Long spuId, Long skuId, Long orderId, Integer count) {
        this.spuId = spuId;
        this.skuId = skuId;
        this.orderId = orderId;
        this.count = count;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "SkuStockLockDTO{" +
                "spuId=" + spuId +
                ", skuId=" + skuId +
                ", orderId=" + orderId +
                ", count=" + count +
                '}';
    }
}
