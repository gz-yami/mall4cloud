package com.mall4j.cloud.search.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author lth
 * @Date 2021/6/21 10:38
 */
public class SpuAdminVO {
    @Schema(description = "商品id" )
    private Long spuId;

    @Schema(description = "商品名称" )
    private String spuName;

    @Schema(description = "商品介绍主图" )
    private String mainImgUrl;

    @Schema(description = "店铺id" )
    private Long shopId;

    @Schema(description = "店铺名称" )
    private String shopName;

    @Schema(description = "商品售价" )
    private Long priceFee;

    @Schema(description = "市场价，整数方式保存" )
    private Long marketPriceFee;

    @Schema(description = "销量" )
    private Integer saleNum;

    @Schema(description = "状态" )
    private Integer spuStatus;

    @Schema(description = "库存" )
    private Integer stock;

    @Schema(description = "序号" )
    private Integer seq;

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

    public String getMainImgUrl() {
        return mainImgUrl;
    }

    public void setMainImgUrl(String mainImgUrl) {
        this.mainImgUrl = mainImgUrl;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Integer getSpuStatus() {
        return spuStatus;
    }

    public void setSpuStatus(Integer spuStatus) {
        this.spuStatus = spuStatus;
    }

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

    @Override
    public String toString() {
        return "SpuAdminVO{" +
                "spuId=" + spuId +
                ", spuName='" + spuName + '\'' +
                ", mainImgUrl='" + mainImgUrl + '\'' +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", priceFee=" + priceFee +
                ", marketPriceFee=" + marketPriceFee +
                ", saleNum=" + saleNum +
                ", spuStatus=" + spuStatus +
                ", stock=" + stock +
                ", seq=" + seq +
                '}';
    }
}
