package com.mall4j.cloud.api.product.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

/**
 * sku信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SkuVO extends BaseVO {
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

	@Schema(description = "商品编码" )
	private String partyCode;

	@Schema(description = "商品条形码" )
	private String modelId;

	@Schema(description = "商品重量" )
	private BigDecimal weight;

	@Schema(description = "商品体积" )
	private BigDecimal volume;

	@Schema(description = "当前sku规格列表" )
	private List<SpuSkuAttrValueVO> spuSkuAttrValues;

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

	public List<SpuSkuAttrValueVO> getSpuSkuAttrValues() {
		return spuSkuAttrValues;
	}

	public void setSpuSkuAttrValues(List<SpuSkuAttrValueVO> spuSkuAttrValues) {
		this.spuSkuAttrValues = spuSkuAttrValues;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Long getSkuId() {
		return skuId;
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
		return "SkuVO{" +
				"skuId=" + skuId +
				", spuId=" + spuId +
				", attrs='" + attrs + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", priceFee=" + priceFee +
				", marketPriceFee=" + marketPriceFee +
				", status=" + status +
				", stock=" + stock +
				", spuSkuAttrValues=" + spuSkuAttrValues +
				", partyCode='" + partyCode + '\'' +
				", modelId='" + modelId + '\'' +
				", weight=" + weight +
				", volume=" + volume +
				'}';
	}
}
