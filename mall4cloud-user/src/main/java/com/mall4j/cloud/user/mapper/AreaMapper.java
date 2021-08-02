package com.mall4j.cloud.user.mapper;

import com.mall4j.cloud.api.user.vo.AreaVO;
import com.mall4j.cloud.user.model.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 省市区地区信息
 *
 * @author YXF
 * @date 2020-11-25 15:10:06
 */
public interface AreaMapper {

	/**
	 * 获取省市区地区信息列表
	 *
	 * @return 省市区地区信息列表
	 */
	List<AreaVO> list();

	/**
	 * 根据省市区地区信息id获取省市区地区信息
	 *
	 * @param areaId 省市区地区信息id
	 * @return 省市区地区信息
	 */
	AreaVO getByAreaId(@Param("areaId") Long areaId);

	/**
	 * 保存省市区地区信息
	 *
	 * @param area 省市区地区信息
	 */
	void save(@Param("area") Area area);

	/**
	 * 更新省市区地区信息
	 *
	 * @param area 省市区地区信息
	 */
	void update(@Param("area") Area area);

	/**
	 * 根据省市区地区信息id删除省市区地区信息
	 *
	 * @param areaId
	 */
	void deleteById(@Param("areaId") Long areaId);

	/**
	 * 获取该地址id下的下级地址数量
	 *
	 * @param areaId
	 * @return
	 */
	int countByAreaId(@Param("areaId") Long areaId);

	/**
	 * 根据上级分类id获取下级地址列表
	 *
	 * @param pid
	 * @return
	 */
	List<AreaVO> listByPid(@Param("pid") Long pid);

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
