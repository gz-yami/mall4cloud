package com.mall4j.cloud.api.product.bo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;

import java.util.Date;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/11/12
 */
public class EsProductBO {

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 卖点
     */
    private String sellingPoint;

    /**
     * 商品售价
     */
    private Long priceFee;

    /**
     * 市场价，整数方式保存
     */
    private Long marketPriceFee;

    /**
     * 商品介绍主图
     */
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String mainImgUrl;

    /**
     * 店铺名称 搜索华为的时候，可以把华为的旗舰店搜索出来
     */
    private String shopName;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺logo
     */
    private String shopImg;

    /**
     * 店铺类型1自营店 2普通店
     */
    private Integer shopType;

    /**
     * 商品状态
     */
    private Integer spuStatus;

    /**
     * 是否有库存
     */
    private Boolean hasStock;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 销量
     */
    private Integer saleNum;

    /**
     * 商品创建时间
     */
    private Date createTime;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 商品序号
     */
    private Integer seq;

    /**
     * 品牌图片
     */
    private String brandImg;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 商家一级分类id
     */
    private Long shopPrimaryCategoryId;

    /**
     * 商家一级分类名称
     */
    private String shopPrimaryCategoryName;

    /**
     * 商家二级分类id
     */
    private Long shopSecondaryCategoryId;

    /**
     * 商家二级分类名称
     */
    private String shopSecondaryCategoryName;

    /**
     * 平台一级分类id
     */
    private Long primaryCategoryId;

    /**
     * 平台一级分类名称
     */
    private String primaryCategoryName;

    /**
     * 平台二级分类id
     */
    private Long secondaryCategoryId;

    /**
     * 平台二级分类名称
     */
    private String secondaryCategoryName;

    /**
     * 商品用于搜索的规格属性
     */
    private List<EsAttrBO> attrs;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public Long getPriceFee() {
        return priceFee;
    }

    public void setPriceFee(Long priceFee) {
        this.priceFee = priceFee;
    }

    public Long getMarketPriceFee() {
        return marketPriceFee;
    }

    public void setMarketPriceFee(Long marketPriceFee) {
        this.marketPriceFee = marketPriceFee;
    }

    public String getMainImgUrl() {
        return mainImgUrl;
    }

    public void setMainImgUrl(String mainImgUrl) {
        this.mainImgUrl = mainImgUrl;
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

    public Integer getSpuStatus() {
        return spuStatus;
    }

    public void setSpuStatus(Integer spuStatus) {
        this.spuStatus = spuStatus;
    }

    public Boolean getHasStock() {
        return hasStock;
    }

    public void setHasStock(Boolean hasStock) {
        this.hasStock = hasStock;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandImg() {
        return brandImg;
    }

    public void setBrandImg(String brandImg) {
        this.brandImg = brandImg;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getShopPrimaryCategoryId() {
        return shopPrimaryCategoryId;
    }

    public void setShopPrimaryCategoryId(Long shopPrimaryCategoryId) {
        this.shopPrimaryCategoryId = shopPrimaryCategoryId;
    }

    public String getShopPrimaryCategoryName() {
        return shopPrimaryCategoryName;
    }

    public void setShopPrimaryCategoryName(String shopPrimaryCategoryName) {
        this.shopPrimaryCategoryName = shopPrimaryCategoryName;
    }

    public Long getShopSecondaryCategoryId() {
        return shopSecondaryCategoryId;
    }

    public void setShopSecondaryCategoryId(Long shopSecondaryCategoryId) {
        this.shopSecondaryCategoryId = shopSecondaryCategoryId;
    }

    public String getShopSecondaryCategoryName() {
        return shopSecondaryCategoryName;
    }

    public void setShopSecondaryCategoryName(String shopSecondaryCategoryName) {
        this.shopSecondaryCategoryName = shopSecondaryCategoryName;
    }

    public List<EsAttrBO> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<EsAttrBO> attrs) {
        this.attrs = attrs;
    }

    public String getSellingPoint() {
        return sellingPoint;
    }

    public void setSellingPoint(String sellingPoint) {
        this.sellingPoint = sellingPoint;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public Long getPrimaryCategoryId() {
        return primaryCategoryId;
    }

    public void setPrimaryCategoryId(Long primaryCategoryId) {
        this.primaryCategoryId = primaryCategoryId;
    }

    public String getPrimaryCategoryName() {
        return primaryCategoryName;
    }

    public void setPrimaryCategoryName(String primaryCategoryName) {
        this.primaryCategoryName = primaryCategoryName;
    }

    public Long getSecondaryCategoryId() {
        return secondaryCategoryId;
    }

    public void setSecondaryCategoryId(Long secondaryCategoryId) {
        this.secondaryCategoryId = secondaryCategoryId;
    }

    public String getSecondaryCategoryName() {
        return secondaryCategoryName;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public void setSecondaryCategoryName(String secondaryCategoryName) {
        this.secondaryCategoryName = secondaryCategoryName;
    }

    @Override
    public String toString() {
        return "EsProductBO{" +
                "spuId=" + spuId +
                ", spuName='" + spuName + '\'' +
                ", sellingPoint='" + sellingPoint + '\'' +
                ", priceFee=" + priceFee +
                ", marketPriceFee=" + marketPriceFee +
                ", mainImgUrl='" + mainImgUrl + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopId=" + shopId +
                ", shopImg='" + shopImg + '\'' +
                ", shopType=" + shopType +
                ", spuStatus=" + spuStatus +
                ", hasStock=" + hasStock +
                ", stock=" + stock +
                ", saleNum=" + saleNum +
                ", createTime=" + createTime +
                ", brandName='" + brandName + '\'' +
                ", brandId=" + brandId +
                ", seq=" + seq +
                ", brandImg='" + brandImg + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", shopPrimaryCategoryId=" + shopPrimaryCategoryId +
                ", shopPrimaryCategoryName='" + shopPrimaryCategoryName + '\'' +
                ", shopSecondaryCategoryId=" + shopSecondaryCategoryId +
                ", shopSecondaryCategoryName='" + shopSecondaryCategoryName + '\'' +
                ", primaryCategoryId=" + primaryCategoryId +
                ", primaryCategoryName='" + primaryCategoryName + '\'' +
                ", secondaryCategoryId=" + secondaryCategoryId +
                ", secondaryCategoryName='" + secondaryCategoryName + '\'' +
                ", attrs=" + attrs +
                '}';
    }
}
