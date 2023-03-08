package com.mall4j.cloud.product.vo.app;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.api.product.vo.SpuAttrValueVO;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * spu信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SpuAppVO {
	private static final long serialVersionUID = 1L;

	@Schema(description = "spu id" )
	private Long spuId;

	@Schema(description = "店铺id" )
	private Long shopId;

	@Schema(description = "spu名称" )
	private String name;

	@Schema(description = "卖点" )
	private String sellingPoint;

	@JsonSerialize(using = ImgJsonSerializer.class)
	@Schema(description = "商品介绍主图" )
	private String mainImgUrl;

	@JsonSerialize(using = ImgJsonSerializer.class)
	@Schema(description = "商品介绍主图 多个图片逗号分隔" )
	private String imgUrls;

	@Schema(description = "售价，整数方式保存" )
	private Long priceFee;

	@Schema(description = "市场价，整数方式保存" )
	private Long marketPriceFee;

	@Schema(description = "商品详情" )
	private String detail;

	@Schema(description = "总库存" )
	private Integer totalStock;

	@Schema(description = "规格属性" )
	private List<SpuAttrValueVO> spuAttrValues;

	@Schema(description = "sku列表" )
	private List<SkuAppVO> skus;

	@Schema(description = "商品销量" )
	private Integer saleNum;

	public String getMainImgUrl() {
		return mainImgUrl;
	}

	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}

	public List<SkuAppVO> getSkus() {
		return skus;
	}

	public void setSkus(List<SkuAppVO> skus) {
		this.skus = skus;
	}

	public List<SpuAttrValueVO> getSpuAttrValues() {
		return spuAttrValues;
	}

	public void setSpuAttrValues(List<SpuAttrValueVO> spuAttrValues) {
		this.spuAttrValues = spuAttrValues;
	}

	public Integer getTotalStock() {
		return totalStock;
	}

	public void setTotalStock(Integer totalStock) {
		this.totalStock = totalStock;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
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
				", name='" + name + '\'' +
				", sellingPoint='" + sellingPoint + '\'' +
				", mainImgUrl='" + mainImgUrl + '\'' +
				", imgUrls='" + imgUrls + '\'' +
				", priceFee=" + priceFee +
				", marketPriceFee=" + marketPriceFee +
				", detail='" + detail + '\'' +
				", totalStock=" + totalStock +
				", spuAttrValues=" + spuAttrValues +
				", skus=" + skus +
				", saleNum=" + saleNum +
				'}';
	}
}
