package com.mall4j.cloud.api.product.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/11/12
 */
public class SpuUpdateDTO implements Serializable {

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类id列表
     */
    private List<Long> categoryIds;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类等级
     */
    private Integer categoryLevel;

    /**
     * 商品状态
     */
    private Integer status;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺名称 搜索华为的时候，可以把华为的旗舰店搜索出来
     */
    private String shopName;

    /**
     * 店铺logo
     */
    private String shopImg;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 品牌图片
     */
    private String brandImg;

    /**
     * 品牌名称
     */
    private String brandName;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandImg() {
        return brandImg;
    }

    public void setBrandImg(String brandImg) {
        this.brandImg = brandImg;
    }

    @Override
    public String toString() {
        return "SpuUpdateDTO{" +
                "categoryId=" + categoryId +
                ", categoryIds=" + categoryIds +
                ", categoryName='" + categoryName + '\'' +
                ", categoryLevel=" + categoryLevel +
                ", status=" + status +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", shopImg='" + shopImg + '\'' +
                ", brandId=" + brandId +
                ", brandImg='" + brandImg + '\'' +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
