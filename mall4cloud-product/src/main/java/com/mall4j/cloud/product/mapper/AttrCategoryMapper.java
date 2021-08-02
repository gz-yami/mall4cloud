package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.api.product.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性与属性分组关联信息
 *
 * @author YXF
 * @date 2020-11-23 16:20:01
 */
public interface AttrCategoryMapper {

	/**
	 * 根据属性id，获取属性关联的分类id列表
	 *
	 * @param attrId 属性id
	 * @return 分类id列表
	 */
	List<Long> getCategoryIdsByAttrId(@Param("attrId") Long attrId);

	/**
	 * 批量保存属性与属性分组关联信息
	 *
	 * @param attrId      属性id
	 * @param categoryIds 分类id列表
	 */
	void saveBatch(@Param("attrId") Long attrId, @Param("categoryIds") List<Long> categoryIds);

	/**
	 * 批量删除属性与分类关联信息
	 *
	 * @param attrId      属性与分类关联id列表
	 * @param categoryIds 属性与分类关联id列表
	 */
	void deleteBatch(@Param("attrId") Long attrId, @Param("categoryIds") List<Long> categoryIds);

	/**
	 * 据属性Id，获取属性关联的分类列表信息
	 *
	 * @param attrId
	 * @return
	 */
    List<CategoryVO> listByAttrId(@Param("attrId") Long attrId);
}
