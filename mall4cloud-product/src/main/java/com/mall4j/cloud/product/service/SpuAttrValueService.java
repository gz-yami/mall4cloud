package com.mall4j.cloud.product.service;

import com.mall4j.cloud.product.model.SpuAttrValue;
import com.mall4j.cloud.api.product.vo.SpuAttrValueVO;

import java.util.List;

/**
 * 商品规格属性关联信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface SpuAttrValueService {

	/**
	 * 更新商品规格属性关联信息
	 *
	 * @param spuId         id
	 * @param spuAttrValues 商品属性信息
	 * @param spuAttrValuesDb 缓存中的商品属性信息
	 */
	void update(Long spuId, List<SpuAttrValue> spuAttrValues, List<SpuAttrValueVO> spuAttrValuesDb);

	/**
	 * 批量保存
	 *
	 * @param spuId         id
	 * @param spuAttrValues 规格属性信息
	 */
	void saveBatch(Long spuId, List<SpuAttrValue> spuAttrValues);

	/**
	 * 根据商品id删除spuId
	 *
	 * @param spuId id
	 */
	void deleteBySpuId(Long spuId);

	/**
	 * 根据属性和分类id列表删除商品属性关联信息, 并发送消息到队列
	 * @param attrId
	 * @param attrValueId
	 * @param categoryIds
	 */
    void deleteByAttIdAndCategoryIds(Long attrId, List<Long> attrValueId,  List<Long> categoryIds);

	/**
	 * 根据spuId获取商品属性列表
	 * @param spuId
	 * @return
	 */
	List<SpuAttrValueVO> getSpuAttrsBySpuId(Long spuId);

	/**
	 * 批量更新商品基本属性
	 * @param spuAttrValues
	 */
	void batchUpdateSpuAttrValue(List<SpuAttrValue> spuAttrValues);
}
