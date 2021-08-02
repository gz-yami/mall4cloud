package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.product.model.SpuSkuAttrValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品sku销售属性关联信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface SpuSkuAttrValueMapper {

	/**
	 * 保存商品sku销售属性关联信息
	 *
	 * @param spuSkuAttrValue 商品sku销售属性关联信息
	 */
	void save(@Param("spuSkuAttrValue") SpuSkuAttrValue spuSkuAttrValue);

	/**
	 * 更新商品sku销售属性关联信息
	 *
	 * @param spuSkuAttrValue 商品sku销售属性关联信息
	 */
	void update(@Param("spuSkuAttrValue") SpuSkuAttrValue spuSkuAttrValue);

	/**
	 * 批量更新商品sku销售属性关联信息
	 *
	 * @param spuSkuAttrValues 商品sku销售属性关联信息
	 */
	void updateBatch(@Param("spuSkuAttrValues") List<SpuSkuAttrValue> spuSkuAttrValues);

	/**
	 * 根据商品sku销售属性关联信息id删除商品sku销售属性关联信息
	 *
	 * @param spuSkuAttrId
	 */
	void deleteById(@Param("spuSkuAttrId") Long spuSkuAttrId);

	/**
	 * 批量保存sku规格信息
	 *
	 * @param spuSkuAttrValues attrList
	 */
	void saveBatch(@Param("spuSkuAttrValues") List<SpuSkuAttrValue> spuSkuAttrValues);

	/**
	 * 修改商品规格信息
	 *
	 * @param spuId
	 */
	void updateBySpuId(@Param("spuId") Long spuId);

	/**
	 * 根据skuId列表，改变销售属性状态
	 * @param skuIds
	 * @param status
	 */
    void changeStatusBySkuId(@Param("skuIds") List<Long> skuIds, @Param("status") Integer status);
}
