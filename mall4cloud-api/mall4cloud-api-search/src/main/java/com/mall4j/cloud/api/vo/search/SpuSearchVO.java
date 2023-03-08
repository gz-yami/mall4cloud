package com.mall4j.cloud.api.vo.search;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author FrozenWatermelon
 * @date 2020/11/17
 */
public class SpuSearchVO {

    @Schema(description = "商品id" )
    private Long spuId;

    @Schema(description = "商品名称" )
    private String spuName;

    @Schema(description = "卖点" )
    private String sellingPoint;

    @Schema(description = "店铺id" )
    private Long shopId;

    @Schema(description = "商品售价" )
    private Long priceFee;

    @Schema(description = "市场价，整数方式保存" )
    private Long marketPriceFee;

    @Schema(description = "是否有库存" )
    private Boolean hasStock;

    @Schema(description = "销量" )
    private Integer saleNum;

    @Schema(description = "商品介绍主图" )
    private String mainImgUrl;

    public String getMainImgUrl() {
        return mainImgUrl;
    }

    public void setMainImgUrl(String mainImgUrl) {
        this.mainImgUrl = mainImgUrl;
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

    public String getSellingPoint() {
        return sellingPoint;
    }

    public void setSellingPoint(String sellingPoint) {
        this.sellingPoint = sellingPoint;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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

    @Override
    public String toString() {
        return "SpuVO{" +
                "spuId=" + spuId +
                ", spuName='" + spuName + '\'' +
                ", sellingPoint='" + sellingPoint + '\'' +
                ", priceFee=" + priceFee +
                ", marketPriceFee=" + marketPriceFee +
                ", hasStock=" + hasStock +
                ", saleNum=" + saleNum +
                ", mainImgUrl='" + mainImgUrl + '\'' +
                '}';
    }
}
