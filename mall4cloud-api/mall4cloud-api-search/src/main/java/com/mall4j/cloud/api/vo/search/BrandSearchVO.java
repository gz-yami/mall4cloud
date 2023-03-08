package com.mall4j.cloud.api.vo.search;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author FrozenWatermelon
 * @date 2020/11/17
 */
public class BrandSearchVO {

    @Schema(description = "品牌名称" )
    private String brandName;

    @Schema(description = "品牌id" )
    private Long brandId;

    @Schema(description = "品牌图片" )
    private String brandImg;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandImg() {
        return brandImg;
    }

    public void setBrandImg(String brandImg) {
        this.brandImg = brandImg;
    }

    @Override
    public String toString() {
        return "BrandVO{" +
                "brandName='" + brandName + '\'' +
                ", brandId=" + brandId +
                ", brandImg='" + brandImg + '\'' +
                '}';
    }
}
