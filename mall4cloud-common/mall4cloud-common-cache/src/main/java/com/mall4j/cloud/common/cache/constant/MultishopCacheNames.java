package com.mall4j.cloud.common.cache.constant;

/**
 * @author FrozenWatermelon
 * @date 2020/11/23
 */
public interface MultishopCacheNames {

    /**
     * 前缀
     */
    String MULTISHOP_PREFIX = "mall4cloud_multishop:";

    /**
     * 店铺分类列表缓存key
     */
    String SHOP_DETAIL_ID_KEY = MULTISHOP_PREFIX + "shop_detail:getById:";

    /**
     * 店铺分类列表缓存key
     */
    String INDEX_IMG_KEY = MULTISHOP_PREFIX + "index_img";

    /**
     * 店铺分类列表缓存key
     */
    String NOTICES_KEY = MULTISHOP_PREFIX + "notices";


    String MULTISHOP_SIMPLE_INFO_KEY = MULTISHOP_PREFIX + "simple_info";


    String HOT_SEARCH_LIST_KEY = MULTISHOP_PREFIX + "hot_search_list";

}
