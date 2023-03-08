package com.mall4j.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * sku信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SkuDTO{
	private static final long serialVersionUID = 1L;

	@Schema(description = "属性id" )
	private Long skuId;

	@Schema(description = "SPU id" )
	private Long spuId;

	@Schema(description = "多个销售属性值id逗号分隔" )
	private String attrs;

	@Schema(description = "sku名称" )
	private String skuName;

	@Schema(description = "banner图片" )
	private String imgUrl;

	@Schema(description = "售价，整数方式保存" )
	private Long priceFee;

	@Schema(description = "市场价，整数方式保存" )
	private Long marketPriceFee;

	@Schema(description = "状态 1:enable, 0:disable, -1:deleted" )
	private Integer status;

	@Schema(description = "库存" )
	private Integer stock;

	@Schema(description = "更新时，变化的库存" )
	private Integer changeStock;

	@Schema(description = "商品编码" )
	private String partyCode;

	@Schema(description = "商品条形码" )
	private String modelId;

	@Schema(description = "商品重量" )
	private Double weight;

	@Schema(description = "商品体积" )
	private Double volume;

	@Schema(description = "规格列表" )
	private List<SpuSkuAttrValueDTO> spuSkuAttrValues;

	public String getPartyCode() {
		return partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public List<SpuSkuAttrValueDTO> getSpuSkuAttrValues() {
		return spuSkuAttrValues;
	}

	public void setSpuSkuAttrValues(List<SpuSkuAttrValueDTO> spuSkuAttrValues) {
		this.spuSkuAttrValues = spuSkuAttrValues;
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

	public Integer getChangeStock() {
		return changeStock;
	}

	public void setChangeStock(Integer changeStock) {
		this.changeStock = changeStock;
	}

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

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	@Override
	public String toString() {
		return "SkuDTO{" +
				"skuId=" + skuId +
				", spuId=" + spuId +
				", attrs='" + attrs + '\'' +
				", skuName='" + skuName + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", priceFee=" + priceFee +
				", marketPriceFee=" + marketPriceFee +
				", status=" + status +
				", stock=" + stock +
				", partyCode='" + partyCode + '\'' +
				", modelId='" + modelId + '\'' +
				", weight=" + weight +
				", volume=" + volume +
				", spuSkuAttrValues=" + spuSkuAttrValues +
				'}';
	}
}
