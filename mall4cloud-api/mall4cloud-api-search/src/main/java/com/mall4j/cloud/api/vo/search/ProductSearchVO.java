package com.mall4j.cloud.api.vo.search;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/11/16
 */
public class ProductSearchVO {


    @ApiModelProperty(value = "店铺信息")
    private ShopInfoSearchVO shopInfo;

    @ApiModelProperty(value = "规格属性列表")
    private List<AttrSearchVO> attrs;

    @ApiModelProperty(value = "品牌列表信息")
    private List<BrandSearchVO> brands;

    @ApiModelProperty(value = "spu列表信息")
    private List<SpuSearchVO> spus;

    @ApiModelProperty(value = "分类列表信息")
    private List<CategorySearchVO> categorys;

    public ShopInfoSearchVO getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfoSearchVO shopInfo) {
        this.shopInfo = shopInfo;
    }

    public List<AttrSearchVO> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<AttrSearchVO> attrs) {
        this.attrs = attrs;
    }

    public List<BrandSearchVO> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandSearchVO> brands) {
        this.brands = brands;
    }

    public List<SpuSearchVO> getSpus() {
        return spus;
    }

    public void setSpus(List<SpuSearchVO> spus) {
        this.spus = spus;
    }

    public List<CategorySearchVO> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<CategorySearchVO> categorys) {
        this.categorys = categorys;
    }

    @Override
    public String toString() {
        return "ProductSearchVO{" +
                "shopInfo=" + shopInfo +
                ", attrs=" + attrs +
                ", brands=" + brands +
                ", spus=" + spus +
                '}';
    }
}
