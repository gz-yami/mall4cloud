package com.mall4j.cloud.common.cache.constant;

/**
 * @author FrozenWatermelon
 * @date 2020/11/23
 */
public interface ProductCacheNames {

    /**
     * 前缀
     */
    String PRODUCT_PREFIX = "mall4cloud_product:";

    /**
     * 根据skuid获取sku的缓存key
     */
    String SKU_BY_ID_KEY = PRODUCT_PREFIX + "sku:by_id:";

    /**
     * 用户端分类列表缓存key
     */
    String CATEGORY_LIST = PRODUCT_PREFIX + "category_list:";

    /**
     * 根据店铺id和上级id，获取分类列表缓存key
     */
    String CATEGORY_LIST_BY_PARENT_ID_AND_SHOP_ID = PRODUCT_PREFIX + "category_list:shopIdAndParentId:";

    /**
     * 根据店铺id和上级id，获取分类列表缓存key
     */
    String CATEGORY_LIST_ALL_OF_SHOP = PRODUCT_PREFIX + "category_list_all_of_shop:shopId:";

    /**
     * 分类下的属性列表缓存key
     */
    String ATTRS_BY_CATEGORY_KEY = PRODUCT_PREFIX + "attrs_by_category:list:";

    /**
     * 分类下的品牌列表缓存key
     */
    String BRAND_LIST_BY_CATEGORY = PRODUCT_PREFIX + "brand_list_by_category:id:";

    /**
     * 购物车商品数量
     */
    String SHOP_CART_ITEM_COUNT = "shop_cart:count:";

    /**
     * spu信息缓存key
     */
    String SPU_KEY = PRODUCT_PREFIX + "spu:";

    /**
     * spu扩展信息缓存key
     */
    String SPU_EXTENSION_KEY = PRODUCT_PREFIX + "spu_extension:";

    /**
     * sku列表信息缓存key
     */
    String SKU_LIST_KEY = PRODUCT_PREFIX + "sku_list:";

    /**
     * 商品详情sku列表信息缓存key
     */
    String SKU_OF_SPU_DETAIL_KEY = PRODUCT_PREFIX + "sku_of_spu_detail_list:";

    /**
     * 置顶品牌列表信息缓存key
     */
    String BRAND_TOP = PRODUCT_PREFIX + "brand_top:list";


}
