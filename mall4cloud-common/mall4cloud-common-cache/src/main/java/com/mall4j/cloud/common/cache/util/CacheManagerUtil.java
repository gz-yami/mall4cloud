package com.mall4j.cloud.common.cache.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * 缓存管理工具类
 * 用于操作缓存的增删改查
 */
@Component
public class CacheManagerUtil {

	private final CacheManager cacheManager;

	@Autowired
	public CacheManagerUtil(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * 获取缓存中指定key的值
	 * @param cacheName 缓存名称
	 * @param key 缓存键
	 * @return 缓存值
	 */
	@SuppressWarnings({ "unchecked" })
	public <T> T getCache(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			return null;
		}
		Cache.ValueWrapper valueWrapper = cache.get(key);
		if (valueWrapper == null) {
			return null;
		}
		return (T) valueWrapper.get();
	}

	/**
	 * 向缓存中添加指定key-value对
	 * @param cacheName 缓存名称
	 * @param key 缓存键
	 * @param value 缓存值
	 */
	public void putCache(String cacheName, String key, Object value) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			cache.put(key, value);
		}
	}

	/**
	 * 从缓存中删除指定key的值
	 * @param cacheName 缓存名称
	 * @param key 缓存键
	 */
	public void evictCache(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			cache.evict(key);
		}
	}

}
