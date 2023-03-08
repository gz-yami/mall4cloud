package com.mall4j.cloud.api.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * spu信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SpuVO extends BaseVO {
	private static final long serialVersionUID = 1L;

	@Schema(description = "spu id" )
	private Long spuId;

	@Schema(description = "品牌ID" )
	private Long brandId;

	@Schema(description = "分类ID" )
	private Long categoryId;

	@Schema(description = "店铺分类ID" )
	private Long shopCategoryId;

	@Schema(description = "店铺id" )
	private Long shopId;

	@Schema(description = "spu名称" )
	private String name;

	@Schema(description = "卖点" )
	private String sellingPoint;

	@Schema(description = "商品介绍主图" )
	@JsonSerialize(using = ImgJsonSerializer.class)
	private String mainImgUrl;

	@Schema(description = "商品介绍主图 多个图片逗号分隔" )
	@JsonSerialize(using = ImgJsonSerializer.class)
	private String imgUrls;

	@Schema(description = "售价，整数方式保存" )
	private Long priceFee;

	@Schema(description = "市场价，整数方式保存" )
	private Long marketPriceFee;

	@Schema(description = "状态 1:enable, 0:disable, -1:deleted" )
	private Integer status;

	@Schema(description = "sku是否含有图片 0无 1有" )
	private Integer hasSkuImg;

	@Schema(description = "商品详情" )
	private String detail;

	@Schema(description = "总库存" )
	private Integer totalStock;

	@Schema(description = "规格属性" )
	private List<SpuAttrValueVO> spuAttrValues;

	@Schema(description = "sku列表" )
	private List<SkuVO> skus;

	@Schema(description = "序号" )
	private Integer seq;

	@Schema(description = "品牌信息" )
	private BrandVO brand;

	@Schema(description = "商品销量" )
	private Integer saleNum;

	@Schema(description = "店铺名称" )
	private String shopName;

	@Schema(description = "分类信息" )
	private CategoryVO category;

	@Schema(description = "店铺分类信息" )
	private CategoryVO shopCategory;

	@Schema(description = "分组商品关联id" )
	private Long referenceId;

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public String getMainImgUrl() {
		return mainImgUrl;
	}

	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}

	public List<SkuVO> getSkus() {
		return skus;
	}

	public void setSkus(List<SkuVO> skus) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSellingPoint() {
		return sellingPoint;
	}

	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}

	public Integer getHasSkuImg() {
		return hasSkuImg;
	}

	public void setHasSkuImg(Integer hasSkuImg) {
		this.hasSkuImg = hasSkuImg;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public BrandVO getBrand() {
		return brand;
	}

	public void setBrand(BrandVO brand) {
		this.brand = brand;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public CategoryVO getCategory() {
		return category;
	}

	public void setCategory(CategoryVO category) {
		this.category = category;
	}

	public CategoryVO getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(CategoryVO shopCategory) {
		this.shopCategory = shopCategory;
	}

	@Override
	public String toString() {
		return "SpuVO{" +
				"spuId=" + spuId +
				", brandId=" + brandId +
				", categoryId=" + categoryId +
				", shopCategoryId=" + shopCategoryId +
				", shopId=" + shopId +
				", name='" + name + '\'' +
				", sellingPoint='" + sellingPoint + '\'' +
				", mainImgUrl='" + mainImgUrl + '\'' +
				", imgUrls='" + imgUrls + '\'' +
				", priceFee=" + priceFee +
				", marketPriceFee=" + marketPriceFee +
				", status=" + status +
				", hasSkuImg=" + hasSkuImg +
				", detail='" + detail + '\'' +
				", totalStock=" + totalStock +
				", spuAttrValues=" + spuAttrValues +
				", skus=" + skus +
				", seq=" + seq +
				", brand=" + brand +
				", saleNum=" + saleNum +
				", shopName='" + shopName + '\'' +
				", category=" + category +
				", shopCategory=" + shopCategory +
				", referenceId=" + referenceId +
				'}';
	}
}
