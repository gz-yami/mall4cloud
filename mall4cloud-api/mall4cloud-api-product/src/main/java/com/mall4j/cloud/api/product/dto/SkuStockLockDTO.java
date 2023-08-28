package com.mall4j.cloud.api.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * @author FrozenWatermelon
 * @date 2020/12/22
 */
public class SkuStockLockDTO {

    @NotNull(message = "产品ID不能为空")
    @Schema(description = "产品ID" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Long spuId;

    @NotNull(message = "skuId不能为空")
    @Schema(description = "skuId" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Long skuId;

    @NotNull(message = "orderId不能为空")
    @Schema(description = "orderId" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;

    @NotNull(message = "商品数量不能为空")
    @Min(value = 1,message = "商品数量不能为空")
    @Schema(description = "商品数量" ,requiredMode = Schema.RequiredMode.REQUIRED)
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
