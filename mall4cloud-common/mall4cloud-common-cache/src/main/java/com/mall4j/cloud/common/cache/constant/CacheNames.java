package com.mall4j.cloud.common.cache.constant;

/**
 * 缓存名字
 *
 * @author FrozenWatermelon
 * @date 2020/7/9
 */
public interface CacheNames extends RbacCacheNames,OauthCacheNames,ProductCacheNames,MultishopCacheNames,PlatformCacheNames,BizCacheNames, UserCacheNames {
    /**
     *
     * 参考CacheKeyPrefix
     * cacheNames 与 key 之间的默认连接字符
     */
    String UNION = "::";

    /**
     * key内部的连接字符（自定义）
     */
    String UNION_KEY = ":";

}
