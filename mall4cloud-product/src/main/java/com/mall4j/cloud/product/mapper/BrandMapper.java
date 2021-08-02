package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.product.dto.BrandDTO;
import com.mall4j.cloud.product.model.Brand;
import com.mall4j.cloud.api.product.vo.BrandVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface BrandMapper {

	/**
	 * 获取品牌信息列表
	 *
	 * @param brandDTO 品牌信息
	 * @return 品牌信息列表
	 */
	List<BrandVO> list(@Param("brandDTO") BrandDTO brandDTO);

	/**
	 * 根据品牌信息id获取品牌信息
	 *
	 * @param brandId 品牌信息id
	 * @return 品牌信息
	 */
	BrandVO getByBrandId(@Param("brandId") Long brandId);

	/**
	 * 保存品牌信息
	 *
	 * @param brand 品牌信息
	 */
	void save(@Param("brand") Brand brand);

	/**
	 * 更新品牌信息
	 *
	 * @param brand 品牌信息
	 */
	void update(@Param("brand") Brand brand);

	/**
	 * 根据品牌信息id删除品牌信息
	 *
	 * @param brandId
	 */
	void deleteById(@Param("brandId") Long brandId);

	/**
	 * 获取品牌在商品中使用的数量
	 *
	 * @param brandId
	 * @return 使用该品牌的商品数量
	 */
	int getUseNum(@Param("brandId") Long brandId);

	/**
	 * 根据分类id，获取品牌数据
	 *
	 * @param categoryId
	 * @return
	 */
    List<BrandVO> getBrandByCategoryId(@Param("categoryId") Long categoryId);

	/**
	 * 更新品牌状态（启用或禁用）
	 *
	 * @param brandDTO
	 */
	void updateBrandStatus(@Param("brand") BrandDTO brandDTO);

	/**
	 * 根据分类id，获取品牌列表(分类中的推荐品牌)
	 *
	 * @param categoryId
	 * @return
	 */
    List<BrandVO> listByCategory(@Param("categoryId") Long categoryId);

	/**
	 * 获取置顶品牌列表
	 * @return
	 */
	List<BrandVO> topBrandList();

}
