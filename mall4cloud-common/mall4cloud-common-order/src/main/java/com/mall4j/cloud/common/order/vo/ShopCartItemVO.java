package com.mall4j.cloud.common.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author FrozenWatermelon
 * @date 2020-11-20 15:47:32
 */
public class ShopCartItemVO implements Serializable {

    @ApiModelProperty(value = "加入购物车时间", required = true)
    private Date createTime;

    @ApiModelProperty(value = "购物车ID", required = true)
    private Long cartItemId;

    @ApiModelProperty("店铺ID")
    private Long shopId;

    @ApiModelProperty("产品ID")
    private Long spuId;

    @ApiModelProperty("SkuID")
    private Long skuId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("购物车产品个数")
    private Integer count;

    @ApiModelProperty("是否已经勾选")
    private Integer isChecked;

    @ApiModelProperty("售价，加入购物车时的商品价格")
    private Long priceFee;

    @ApiModelProperty("当前商品价格")
    private Long skuPriceFee;

    @ApiModelProperty("当前总价格(商品价格 * 数量)")
    private Long totalPriceFee;

    @ApiModelProperty("当前总价格(商品价格 * 数量)(带小数)")
    private Long totalPrice;

    @ApiModelProperty("商品重量")
    private BigDecimal weight;

    @ApiModelProperty("商品体积")
    private BigDecimal volume;

    @ApiModelProperty("商品图片")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String imgUrl;

    @ApiModelProperty(value = "总金额", required = true)
    private Long totalAmount;

    @ApiModelProperty("sku规格信息")
    private String skuName;

    @ApiModelProperty("spu名称")
    private String spuName;

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getPriceFee() {
        return priceFee;
    }

    public void setPriceFee(Long priceFee) {
        this.priceFee = priceFee;
    }

    public Long getSkuPriceFee() {
        return skuPriceFee;
    }

    public void setSkuPriceFee(Long skuPriceFee) {
        this.skuPriceFee = skuPriceFee;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTotalPriceFee() {
        return totalPriceFee;
    }

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    public void setTotalPriceFee(Long totalPriceFee) {
        this.totalPriceFee = totalPriceFee;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ShopCartItemVO{" +
                "createTime=" + createTime +
                ", cartItemId=" + cartItemId +
                ", shopId=" + shopId +
                ", spuId=" + spuId +
                ", skuId=" + skuId +
                ", userId=" + userId +
                ", count=" + count +
                ", isChecked=" + isChecked +
                ", priceFee=" + priceFee +
                ", skuPriceFee=" + skuPriceFee +
                ", totalPriceFee=" + totalPriceFee +
                ", totalPrice=" + totalPrice +
                ", weight=" + weight +
                ", volume=" + volume +
                ", imgUrl='" + imgUrl + '\'' +
                ", totalAmount=" + totalAmount +
                ", skuName='" + skuName + '\'' +
                ", spuName='" + spuName + '\'' +
                '}';
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
