package com.mall4j.cloud.product.vo.app;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * sku信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SkuAppVO{

	@Schema(description = "属性id" )
	private Long skuId;

	@Schema(description = "SPU id" )
	private Long spuId;

	@Schema(description = "sku名称" )
	private String skuName;

	@JsonSerialize(using = ImgJsonSerializer.class)
	@Schema(description = "banner图片" )
	private String imgUrl;

	@Schema(description = "售价，整数方式保存" )
	private Long priceFee;

	@Schema(description = "市场价，整数方式保存" )
	private Long marketPriceFee;

	@Schema(description = "库存" )
	private Integer stock;

	@Schema(description = "属性" )
	private String properties;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "SkuAppVO{" +
				"skuId=" + skuId +
				", spuId=" + spuId +
				", skuName='" + skuName + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", priceFee=" + priceFee +
				", marketPriceFee=" + marketPriceFee +
				", stock=" + stock +
				", properties='" + properties + '\'' +
				'}';
	}
}
