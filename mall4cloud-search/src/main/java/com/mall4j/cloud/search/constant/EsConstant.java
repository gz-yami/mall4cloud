package com.mall4j.cloud.search.constant;

/**
 * 配置缓存名字
 *
 * @author lhd
 * @date 2020/12/30
 */
public interface EsConstant {

    /**
     * 商品
     */
    String SHOP = "shop";
    String SPU_ID = "spuId";
    String SPU_NAME = "spuName";
    String SPU_STATUS = "spuStatus";
    String TAGS = "tags";
    String TAG_ID = TAGS + ".tagId";
    String SALE_NUM = "saleNum";
    String PRICE_FEE = "priceFee";
    String HAS_STOCK = "hasStock";
    String SELLING_POINT = "sellingPoint";

    /**
     * 店铺
     */
    String SHOP_ID = "shopId";
    String SHOP_NAME = "shopName";
    String SHOP_TYPE = "shopType";
    String SHOP_COUPON = "shop_coupon";

    /**
     * 属性
     */
    String ATTRS = "attrs";
    String ATTR_IDS = "attrIds";
    String ATTR_VALUE_IDS = "attrValueIds";
    String ATTR_ATTR_VALUE_ID = ATTRS + ".attrValueId";
    String ATTR_ATTR_NAME = ATTRS + ".attrName";
    String ATTR_ATTR_VALUE_NAME = ATTRS + ".attrValueName";
    String ATTR_ATTR_ID = ATTRS + ".attrId";

    /**
     * 品牌
     */
    String BRANDS = "brands";
    String BRAND_ID = "brandId";
    String BRAND_IMG = "brandImg";
    String BRAND_NAME = "brandName";

    /**
     * 分类
     */
    String MAIN_IMG_URL = "mainImgUrl";
    String CATEGORIES = "categories";
    String CATEGORY_ID = "categoryId";
    String CATEGORY_NAME = "categoryName";
    String SHOP_CATEGORIES = "shopCategories";
    String SHOP_CATEGORY_ID = "secondaryCategoryId";
    String SHOP_CATEGORY_NAME = "secondaryCategoryName";
    String PRIMARY_CATEGORY_ID = "primaryCategoryId";
    String SHOP_SECONDARY_CATEGORY_ID = "shopSecondaryCategoryId";
    String SHOP_PRIMARY_CATEGORY_ID = "shopPrimaryCategoryId";

    String TOP_HITS_DATA = "top_hits_data";
    String CONNECTION_SYMBOLS = ",";

    /**
     * 指定返回字段
     */
    String[] FETCH_SOURCE = {"spuId","spuName","sellingPoint","shopId","priceFee","marketPriceFee","hasStock","saleNum","mainImgUrl","stock","seq","spuStatus"};

}
