package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.product.model.Category;
import com.mall4j.cloud.api.product.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 分类信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface CategoryMapper {

	/**
	 * 根据分类信息id获取分类信息
	 *
	 * @param categoryId 分类信息id
	 * @return 分类信息
	 */
	CategoryVO getById(@Param("categoryId") Long categoryId);

	/**
	 * 保存分类信息
	 *
	 * @param category 分类信息
	 */
	void save(@Param("category") Category category);

	/**
	 * 更新分类信息
	 *
	 * @param category 分类信息
	 * @return 更新的数量
	 */
	int update(@Param("category") Category category);

	/**
	 * 根据分类信息id删除分类信息
	 *
	 * @param categoryId
	 */
	void deleteById(@Param("categoryId") Long categoryId);

	/**
	 * 获取分类被关联的数量
	 *
	 * @param categoryId
	 * @return
	 */
	int getCategoryUseNum(@Param("categoryId") Long categoryId);

	/**
	 * 获取分类列表(未删除的分类--启用、未启用状态， 用于分类管理)
	 *
	 * @param shopId 店铺id 必填
	 * @return
	 */
	List<CategoryVO> list(@Param("shopId") Long shopId);


	/**
	 * 获取分类列表(仅启用状态的分类)
	 *
	 * @param parentId 不填默认
	 * @param shopId   店铺id 必填
	 * @return
	 */
	List<CategoryVO> listByShopIdAndParenId(@Param("shopId") Long shopId, @Param("parentId") Long parentId);

	/**
	 * 根据分类id 获取分类下的子分类
	 *
	 * @param categoryId
	 * @return
	 */
    List<Category> getChildCategory(@Param("categoryId") Long categoryId);

	/**
	 * 批量更新分类状态（启用、禁用）
	 *
	 * @param categoryIds
	 * @param status
	 * @return
	 */
	int updateBatchOfStatus(@Param("categoryIds") List<Long> categoryIds, @Param("status") Integer status);

	/**
	 * 查询分类名是否存在
	 *
	 * @param category
	 * @return
	 */
	int existCategoryName(@Param("category") Category category);

	/**
	 * 根据分类id列表，获取分类列表
	 *
	 * @param categoryIds
	 * @return
	 */
    List<Category> getListByCategoryIds(@Param("categoryIds") Set<Long> categoryIds);

	/**
	 * 获取分类id列表
	 * @param shopId 店铺id
	 * @param parentId
	 * @return
	 */
	List<Long> listCategoryId(@Param("shopId") Long shopId, @Param("parentId") Long parentId);

	/**
	 * 获取分类及子分类的数据
	 *
	 * @param shopId
	 * @param parentId
	 * @return
	 */
    List<CategoryVO> getCategoryAndChildCatogory(@Param("shopId") Long shopId, @Param("parentId") Long parentId);
}
