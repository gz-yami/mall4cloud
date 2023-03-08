package com.mall4j.cloud.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

/**
 * @author FrozenWatermelon
 * @date 2020/11/16
 */
public class ProductSearchDTO {

    @Schema(description = "页面传递过来的全文匹配关键字" )
    private String keyword;

    @Schema(description = "品牌id,可以多选" )
    private String brandIds;

    @Schema(description = "商家一级分类id" )
    private Long shopPrimaryCategoryId;

    @Schema(description = "商家二级分类id" )
    private Long shopSecondaryCategoryId;

    @Schema(description = "平台一级分类id" )
    private Long primaryCategoryId;

    @Schema(description = "平台三级分类id" )
    private Long categoryId;

    @Schema(description = "排序：1新品,2销量倒序,3销量正序,4商品价格倒序,5商品价格正序,6评论(暂无评论)" )
    private Integer sort;

    @Schema(description = "自营店 1：自营店 2：非自营店" )
    private Integer selfShop;

    @Schema(description = "是否显示有货" )
    private Integer hasStock;

    @Schema(description = "价格区间查询-最低价" )
    private Long minPrice;

    @Schema(description = "价格区间查询-最高价" )
    private Long maxPrice;

    @Schema(description = "店铺id" )
    private Long shopId;

    @Schema(description = "属性值ids(属性之间用^拼接；属性于属性值id用_拼接；多个属性值id间用,拼接)" )
    private String attrIds;

    @Schema(description = "商品状态" )
    private Integer spuStatus;

    @Schema(description = "属性值ids(多个id间使用 , 分隔)" )
    private String attrValueIds;

    @Schema(description = "spuId列表" )
    private List<Long> spuIds;

    @Schema(description = "销量区间查询-最低销量" )
    private Long minSaleNum;

    @Schema(description = "销量区间查询-最高销量" )
    private Long maxSaleNum;

    @Schema(description = "商品编码列表（逗号分隔）" )
    private String partyCodes;

    @Schema(description = "商品条形码列表（逗号分隔）" )
    private String modelIds;

    @Schema(description = "0.全部  1.销售中  2.已售罄  3.已下架" )
    private Integer dataType;

    @Schema(description = "当前页" )
    private Integer pageNum;

    @Schema(description = "每页大小" )
    private Integer pageSize;

    /**
     * 对应SearchTypeEnum
     * 搜索类型 1：用户端搜索  2：店铺spu列表 3.平台spu管理列表
     */
    private Integer searchType;

    /**
     * 搜索属性信息
     */
    private Map<String, List<String>> attrMap;

    public Map<String, List<String>> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(Map<String, List<String>> attrMap) {
        this.attrMap = attrMap;
    }

    public String getAttrIds() {
        return attrIds;
    }

    public void setAttrIds(String attrIds) {
        this.attrIds = attrIds;
    }

    public Long getMinSaleNum() {
        return minSaleNum;
    }

    public void setMinSaleNum(Long minSaleNum) {
        this.minSaleNum = minSaleNum;
    }

    public Long getMaxSaleNum() {
        return maxSaleNum;
    }

    public void setMaxSaleNum(Long maxSaleNum) {
        this.maxSaleNum = maxSaleNum;
    }

    public String getPartyCodes() {
        return partyCodes;
    }

    public void setPartyCodes(String partyCodes) {
        this.partyCodes = partyCodes;
    }

    public String getModelIds() {
        return modelIds;
    }

    public void setModelIds(String modelIds) {
        this.modelIds = modelIds;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public Long getShopPrimaryCategoryId() {
        return shopPrimaryCategoryId;
    }

    public void setShopPrimaryCategoryId(Long shopPrimaryCategoryId) {
        this.shopPrimaryCategoryId = shopPrimaryCategoryId;
    }

    public Long getShopSecondaryCategoryId() {
        return shopSecondaryCategoryId;
    }

    public void setShopSecondaryCategoryId(Long shopSecondaryCategoryId) {
        this.shopSecondaryCategoryId = shopSecondaryCategoryId;
    }

    public Long getPrimaryCategoryId() {
        return primaryCategoryId;
    }

    public void setPrimaryCategoryId(Long primaryCategoryId) {
        this.primaryCategoryId = primaryCategoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSelfShop() {
        return selfShop;
    }

    public void setSelfShop(Integer selfShop) {
        this.selfShop = selfShop;
    }

    public Integer getHasStock() {
        return hasStock;
    }

    public void setHasStock(Integer hasStock) {
        this.hasStock = hasStock;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public Long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Long maxPrice) {
        this.maxPrice = maxPrice;
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

    public String getAttrValueIds() {
        return attrValueIds;
    }

    public void setAttrValueIds(String attrValueIds) {
        this.attrValueIds = attrValueIds;
    }

    public List<Long> getSpuIds() {
        return spuIds;
    }

    public void setSpuIds(List<Long> spuIds) {
        this.spuIds = spuIds;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        return "ProductSearchDTO{" +
                "keyword='" + keyword + '\'' +
                ", brandIds='" + brandIds + '\'' +
                ", shopPrimaryCategoryId=" + shopPrimaryCategoryId +
                ", shopSecondaryCategoryId=" + shopSecondaryCategoryId +
                ", primaryCategoryId=" + primaryCategoryId +
                ", categoryId=" + categoryId +
                ", sort=" + sort +
                ", selfShop=" + selfShop +
                ", hasStock=" + hasStock +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", shopId=" + shopId +
                ", attrIds='" + attrIds + '\'' +
                ", spuStatus=" + spuStatus +
                ", attrValueIds='" + attrValueIds + '\'' +
                ", spuIds=" + spuIds +
                ", minSaleNum=" + minSaleNum +
                ", maxSaleNum=" + maxSaleNum +
                ", partyCodes='" + partyCodes + '\'' +
                ", modelIds='" + modelIds + '\'' +
                ", dataType=" + dataType +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", searchType=" + searchType +
                ", attrMap=" + attrMap +
                '}';
    }
}
