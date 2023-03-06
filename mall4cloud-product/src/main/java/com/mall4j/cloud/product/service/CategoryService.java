package com.mall4j.cloud.product.service;

import com.mall4j.cloud.product.dto.CategoryDTO;
import com.mall4j.cloud.product.model.Category;
import com.mall4j.cloud.api.product.vo.CategoryVO;

import java.util.List;

/**
 * 分类信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface CategoryService {

	/**
	 * 根据分类信息id获取分类信息
	 *
	 * @param categoryId 分类信息id
	 * @return 分类信息
	 */
	CategoryVO getById(Long categoryId);

	/**
	 * 保存分类信息
	 * @param category 分类信息
	 */
	void save(Category category);

	/**
	 * 更新分类信息
	 * @param category 分类信息
	 */
	void update(Category category);

	/**
	 * 根据分类信息id删除分类信息
	 * @param categoryId
	 */
	void deleteById(Long categoryId);

	/**
	 * 获取分类列表(未删除的分类--启用、未启用状态的分类)
	 * @param shopId 店铺id 必填
	 * @return
	 */
	List<CategoryVO> list(Long shopId);

	/**
	 * 获取分类id列表
	 * @param shopId
	 * @param parentId
	 * @return
	 */
	List<Long> listCategoryId(Long shopId, Long parentId);

	/**
	 * 根据shopId 和 categoruId 清除分类缓存
	 * @param shopId
	 * @param parentId
	 */
	void removeCategoryCache(Long shopId, Long parentId);

	/**
	 *  获取分类的pathName集合
	 * @param categorys 分类集合
	 */
	void getPathNames(List<CategoryVO> categorys);

	/**
	 * 根据分类id获取分类、pathName信息
	 * @param categoryId
	 * @return
	 */
    CategoryVO getPathNameByCategoryId(Long categoryId);

	/**
	 * 获取分类列表 (仅返回启用状态的分类)
	 * @param parentId 上级分类id
	 * @param shopId 店铺id
	 * @return
	 */
	List<CategoryVO> categoryList(Long shopId, Long parentId);

	/**
	 * 根据店铺id和上级id，获取分类列表
	 * @param parentId 上级分类id
	 * @param shopId 店铺id
	 * @return
	 */
	List<CategoryVO> listByShopIdAndParenId(Long shopId, Long parentId);

	/**
	 * 获取分类列表 (仅获取启用状态的分类)
	 * @param shopId
	 * @return
	 */
	List<CategoryVO> shopCategoryList(Long shopId);

	/**
	 * 获取子分类
	 * @param categoryId
	 * @return
	 */
	List<Category> getChildCategory(Long categoryId);

	/**
	 * 批量更新状态
	 * @param updateList
	 * @param status
	 */
	void updateBatchOfStatus(List<Long> updateList, Integer status);
}
