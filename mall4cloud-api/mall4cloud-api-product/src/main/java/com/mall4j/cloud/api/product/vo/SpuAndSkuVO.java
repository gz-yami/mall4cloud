package com.mall4j.cloud.api.product.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author FrozenWatermelon
 * @date 2020/12/8
 */
public class SpuAndSkuVO {

    @ApiModelProperty("spu信息")
    private SpuVO spu;

    @ApiModelProperty("sku信息")
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
