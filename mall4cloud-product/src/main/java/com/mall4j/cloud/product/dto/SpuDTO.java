package com.mall4j.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * spu信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SpuDTO{
	private static final long serialVersionUID = 1L;

	@Schema(description = "spuId" )
	private Long spuId;

	@Schema(description = "品牌ID" )
	private Long brandId;

	@NotNull(message = "分类不能为空")
	@Schema(description = "分类ID" )
	private Long categoryId;

	@NotNull(message = "店铺分类不能为空")
	@Schema(description = "店铺分类ID" )
	private Long shopCategoryId;

	@NotNull(message = "商品名称不能为空")
	@Schema(description = "spu名称" )
	private String name;

	@Schema(description = "卖点" )
	private String sellingPoint;

	@NotNull(message = "商品轮播图不能为空")
	@Schema(description = "商品介绍主图 多个图片逗号分隔" )
	private String imgUrls;

	@NotNull(message = "商品主图不能为空")
	@Schema(description = "商品主图" )
	private String mainImgUrl;

	@Schema(description = "市场价" )
	private Long marketPriceFee;

	@NotNull(message = "售价不能为空")
	@Schema(description = "售价" )
	private Long priceFee;

	@Schema(description = "状态 1:enable, 0:disable, -1:deleted" )
	private Integer status;

	@Schema(description = "商品属性值列表" )
	private List<SpuAttrValueDTO> spuAttrValues;

	@NotEmpty(message = "sku信息不能为空")
	@Schema(description = "商品规格列表" )
	private List<SkuDTO> skuList;

	@Schema(description = "商品详情" )
	private String detail;

	@NotNull(message = "总库存不能为空")
	@Schema(description = "总库存" )
	private Integer totalStock;

	@Schema(description = "更新时，变化的库存" )
	private Integer changeStock;

	@Schema(description = "商品视频" )
	private String video;

	@Schema(description = "sku是否含有图片 0无 1有" )
	private Integer hasSkuImg;

	@Schema(description = "分组id" )
	private Long tagId;

	@Schema(description = "序号" )
	private Integer seq;

	@Schema(description = "spuId列表(商品上下架：批量操作时，用此参数)(批量处理参数)" )
	private List<Long> spuIds;

	@Schema(description = "店铺id" )
	private Long shopId;

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public List<SpuAttrValueDTO> getSpuAttrValues() {
		return spuAttrValues;
	}

	public void setSpuAttrValues(List<SpuAttrValueDTO> spuAttrValues) {
		this.spuAttrValues = spuAttrValues;
	}

	public List<SkuDTO> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<SkuDTO> skuList) {
		this.skuList = skuList;
	}

	public Integer getTotalStock() {
		return totalStock;
	}

	public void setTotalStock(Integer totalStock) {
		this.totalStock = totalStock;
	}

	public Integer getChangeStock() {
		return changeStock;
	}

	public void setChangeStock(Integer changeStock) {
		this.changeStock = changeStock;
	}

	public String getSellingPoint() {
		return sellingPoint;
	}

	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
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

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getMarketPriceFee() {
		return marketPriceFee;
	}

	public void setMarketPriceFee(Long marketPriceFee) {
		this.marketPriceFee = marketPriceFee;
	}

	public Long getPriceFee() {
		return priceFee;
	}

	public void setPriceFee(Long priceFee) {
		this.priceFee = priceFee;
	}

	public String getMainImgUrl() {
		return mainImgUrl;
	}

	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}

	public Integer getHasSkuImg() {
		return hasSkuImg;
	}

	public void setHasSkuImg(Integer hasSkuImg) {
		this.hasSkuImg = hasSkuImg;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public List<Long> getSpuIds() {
		return spuIds;
	}

	public void setSpuIds(List<Long> spuIds) {
		this.spuIds = spuIds;
	}

	@Override
	public String toString() {
		return "SpuDTO{" +
				"spuId=" + spuId +
				", brandId=" + brandId +
				", categoryId=" + categoryId +
				", shopCategoryId=" + shopCategoryId +
				", name='" + name + '\'' +
				", sellingPoint='" + sellingPoint + '\'' +
				", imgUrls='" + imgUrls + '\'' +
				", mainImgUrl='" + mainImgUrl + '\'' +
				", marketPriceFee=" + marketPriceFee +
				", priceFee=" + priceFee +
				", status=" + status +
				", spuAttrValues=" + spuAttrValues +
				", skuList=" + skuList +
				", detail='" + detail + '\'' +
				", totalStock=" + totalStock +
				", changeStock=" + changeStock +
				", video='" + video + '\'' +
				", hasSkuImg=" + hasSkuImg +
				", tagId=" + tagId +
				", seq=" + seq +
				", spuIds=" + spuIds +
				", shopId=" + shopId +
				'}';
	}
}
