package com.mall4j.cloud.order.config;

import com.mall4j.cloud.common.cache.adapter.CacheTtlAdapter;
import com.mall4j.cloud.common.cache.bo.CacheNameWithTtlBO;
import com.mall4j.cloud.common.cache.constant.OrderCacheNames;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/12/18
 */
@Component
public class OrderCacheTtlAdapter implements CacheTtlAdapter {
    @Override
    public List<CacheNameWithTtlBO> listCacheNameWithTtl() {
        List<CacheNameWithTtlBO> cacheNameWithTtls = new ArrayList<>();
        // 确认订单缓存30分钟
        cacheNameWithTtls.add(new CacheNameWithTtlBO(OrderCacheNames.ORDER_CONFIRM_UUID_KEY, 60 * 30));
        cacheNameWithTtls.add(new CacheNameWithTtlBO(OrderCacheNames.ORDER_CONFIRM_KEY, 60 * 30));
        return cacheNameWithTtls;
    }
}
