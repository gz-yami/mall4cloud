package com.mall4j.cloud.platform.mapper;

import com.mall4j.cloud.platform.model.SysConfig;
import com.mall4j.cloud.platform.vo.SysConfigVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统配置信息表
 *
 * @author lhd
 * @date 2020-12-23 16:27:57
 */
public interface SysConfigMapper {

	/**
	 * 获取系统配置信息表列表
	 * @return 系统配置信息表列表
	 */
	List<SysConfigVO> list();

	/**
	 * 根据系统配置信息表id获取系统配置信息表
	 *
	 * @param id 系统配置信息表id
	 * @return 系统配置信息表
	 */
	SysConfigVO getById(@Param("id") Long id);

	/**
	 * 保存系统配置信息表
	 * @param sysConfig 系统配置信息表
	 */
	void save(@Param("sysConfig") SysConfig sysConfig);

	/**
	 * 更新系统配置信息表
	 * @param sysConfig 系统配置信息表
	 */
	void update(@Param("sysConfig") SysConfig sysConfig);

	/**
	 * 根据系统配置key获取对应数量
	 * @param paramKey key
	 * @return count
	 */
    int countByKey(@Param("paramKey") String paramKey);


	/**
	 * 根据key，查询系统配置信息
	 * @param key key
	 * @return SysConfig
	 */
	SysConfig queryByKey(@Param("key") String key);
}
