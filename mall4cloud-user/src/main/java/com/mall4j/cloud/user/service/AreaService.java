package com.mall4j.cloud.user.service;

import com.mall4j.cloud.api.user.vo.AreaVO;
import com.mall4j.cloud.user.model.Area;

import java.util.List;

/**
 * 省市区地区信息
 *
 * @author YXF
 * @date 2020-11-25 14:48:52
 */
public interface AreaService {

	/**
	 * 获取地址列表
	 * @return
	 */
	List<AreaVO> list();

	/**
	 * 根据省市区地区信息id获取省市区地区信息
	 *
	 * @param areaId 省市区地区信息id
	 * @return 省市区地区信息
	 */
	AreaVO getByAreaId(Long areaId);

	/**
	 * 保存省市区地区信息
	 * @param area 省市区地区信息
	 */
	void save(Area area);

	/**
	 * 更新省市区地区信息
	 * @param area 省市区地区信息
	 */
	void update(Area area);

	/**
	 * 根据省市区地区信息id删除省市区地区信息
	 * @param areaId
	 */
	void deleteById(Long areaId);

	/**
	 * 根据上级地址id，获取地址列表
	 * @param pid
	 * @return
	 */
	List<AreaVO> listByPid(Long pid);

	/**
	 * 清除地址的所有缓存数据
	 * @param pid
	 */
	void removeAllCache(Long pid);

	/**
	 * 根据上级分类id，清除缓存
	 * @param pid
	 */
	void removeAreaCacheByParentId(Long pid);
	/**
	 * 获取省市区三级结构完整的集合
	 *
	 * @return 省市区三级结构完整的集合
	 */
	List<AreaVO> getAreaListInfo();

	/**
	 * 获取可用的省市区列表
	 * @return
	 */
    List<AreaVO> listAreaOfEnable();
}
