package com.mall4j.cloud.product.service;

import java.util.List;

/**
 * 品牌分类关联信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface CategoryBrandService {

	/**
	 * 根据品牌id删除品牌分类关联信息
	 * @param brandId
	 */
	void deleteByBrandId(Long brandId);

	/**
	 * 保存品牌信息
	 * @param brandId
	 * @param categoryIds
	 */
    void saveByCategoryIds(Long brandId, List<Long> categoryIds);

	/**
	 * 更新品牌信息
	 * @param brandId
	 * @param categoryIds
	 */
	void updateByCategoryIds(Long brandId, List<Long> categoryIds);

	/**
	 * 根据品牌id或者关联的分类列表
	 * @param brandId
	 * @return
	 */
	List<Long> getCategoryIdBrandId(Long brandId);
}
