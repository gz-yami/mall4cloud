package com.mall4j.cloud.api.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author FrozenWatermelon
 * @date 2020/12/8
 */
public class SpuAndSkuVO {

    @Schema(description = "spu信息" )
    private SpuVO spu;

    @Schema(description = "sku信息" )
    private SkuVO sku;

    public SpuVO getSpu() {
        return spu;
    }

    public void setSpu(SpuVO spu) {
        this.spu = spu;
    }

    public SkuVO getSku() {
        return sku;
    }

    public void setSku(SkuVO sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "SpuAndSkuVO{" +
                "spu=" + spu +
                ", sku=" + sku +
                '}';
    }
}
