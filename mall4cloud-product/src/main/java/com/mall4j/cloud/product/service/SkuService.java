package com.mall4j.cloud.product.service;

import com.mall4j.cloud.api.product.vo.SkuVO;
import com.mall4j.cloud.product.dto.SkuDTO;
import com.mall4j.cloud.product.dto.SpuDTO;
import com.mall4j.cloud.product.vo.app.SkuAppVO;

import java.util.List;

/**
 * sku信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface SkuService {

	/**
	 * 保存sku信息
	 * @param spu 商品id
	 * @param skuList sku信息
	 */
	void save(Long spu, List<SkuDTO> skuList);

	/**
	 * 更新sku信息
	 * @param spuId spuId
	 * @param skuList sku列表信息
	 */
	void update(Long spuId, List<SkuDTO> skuList);

	/**
	 * 根据sku信息id删除sku信息
	 * @param skuId
	 */
	void deleteById(Long skuId);

	/**
	 *  根据商品id获取商品中的sku列表（将会被缓存起来）
	 * @param spuId id
	 * @return 返回sku信息
	 */
	List<SkuVO> listBySpuId(Long spuId);

	/**
	 * 清除sku缓存
	 * @param spuId 商品id
	 * @param skuIds 商品skuId列表
	 */
	void removeSkuCacheBySpuIdOrSkuIds(Long spuId, List<Long> skuIds);


	/**
	 * 根据spuId删除sku信息
	 * @param spuId 商品id
	 */
	void deleteBySpuId(Long spuId);

	/**
	 * 获取sku的所有信息
	 * @param spuId 商品id
	 * @return sku信息
	 */
	List<SkuVO> listBySpuIdAndExtendInfo(Long spuId);

	/**
	 * 根据skuId获取sku信息
	 * @param skuId skuId
	 * @return sku信息
	 */
    SkuVO getSkuBySkuId(Long skuId);

	/**
	 * 更新sku金额或者库存信息
	 * @param spuDTO
	 */
	void updateAmountOrStock(SpuDTO spuDTO);

	/**
	 * 获取商品的sku列表（仅获取启用状态）
	 * @param spuId
	 * @return
	 */
	List<SkuAppVO> getSkuBySpuId(Long spuId);

}
