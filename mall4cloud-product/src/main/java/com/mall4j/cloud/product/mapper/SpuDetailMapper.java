package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.product.model.SpuDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 商品详情信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface SpuDetailMapper {

	/**
	 * 保存商品详情信息
	 * @param spuDetail 商品详情信息
	 */
	void save(@Param("spuDetail") SpuDetail spuDetail);

	/**
	 * 更新商品详情信息
	 * @param spuDetail 商品详情信息
	 */
	void update(@Param("spuDetail") SpuDetail spuDetail);

	/**
	 * 根据商品详情信息id删除商品详情信息
	 * @param spuId
	 */
	void deleteById(@Param("spuId") Long spuId);

}
