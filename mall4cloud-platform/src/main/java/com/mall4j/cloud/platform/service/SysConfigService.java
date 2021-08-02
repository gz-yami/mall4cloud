package com.mall4j.cloud.platform.service;

import com.mall4j.cloud.platform.model.SysConfig;

/**
 * 系统配置信息表
 *
 * @author lhd
 * @date 2020-12-23 16:27:57
 */
public interface SysConfigService {
	/**
	 * 根据key，更新value
	 * @param key 参数key
	 * @param value 参数value
	 */
	void updateValueByKey(String key, String value);

	/**
	 * 删除配置信息
	 * @param ids 配置项id列表
	 */
	void deleteBatch(Long[] ids);

	/**
	 * 根据key，获取配置的value值
	 * @param key 参数key
	 * @return value
	 */
	String getValue(String key);

    /**
     * 根据key获取value
     * @param key key
	 * @param clazz 泛型
	 * @return 对象
     */
	<T> T  getSysConfigObject(String key, Class<T> clazz);

    /**
     * 删除配置
     * @param key key
     */
	void removeSysConfig(String key);

    /**
     * 保存or修改配置
	 * @param sysConfig sysConfig
     */
	void saveOrUpdateSysConfig(SysConfig sysConfig);

    /**
     * 根据key获取配置对象
     * @param key key
	 * @return 配置信息
     */
	SysConfig getByKey(String key);
}
