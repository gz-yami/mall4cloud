package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.product.dto.SkuDTO;
import com.mall4j.cloud.product.model.Sku;
import com.mall4j.cloud.api.product.vo.SkuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface SkuMapper {

	/**
	 * 保存sku信息
	 *
	 * @param sku sku信息
	 */
	void save(@Param("sku") Sku sku);

	/**
	 * 更新sku信息
	 *
	 * @param sku sku信息
	 */
	void update(@Param("sku") Sku sku);

	/**
	 * 根据sku信息id删除sku信息
	 *
	 * @param skuId
	 */
	void deleteById(@Param("skuId") Long skuId);

	/**
	 * 根据spuId获取sku信息
	 *
	 * @param spuId id
	 * @return 返回sku信息
	 */
	List<SkuVO> listBySpuId(@Param("spuId") Long spuId);

	/**
	 * 批量保存
	 *
	 * @param skuList
	 */
	void saveBatch(@Param("skuList") List<SkuDTO> skuList);

	/**
	 * 根据spuId删除sku信息
	 *
	 * @param spuId spuId
	 */
	void updateBySpuId(Long spuId);

	/**
	 * 批量修改
	 *
	 * @param skus 修改后的信息
	 */
	void updateBatch(@Param("skus") List<Sku> skus);

	/**
	 * 获取商品详细信息
	 *
	 * @param spuId
	 * @return
	 */
	List<SkuVO> listBySpuIdAndExtendInfo(@Param("spuId") Long spuId);

	/**
	 * 根据skuid获取sku信息
	 *
	 * @param skuId
	 * @return
	 */
	SkuVO getSkuBySkuId(@Param("skuId") Long skuId);

	/**
	 * 获取商品的sku列表（仅获取启用状态）
	 *
	 * @param spuId
	 * @return
	 */
	List<SkuVO> getSkuBySpuId(@Param("spuId") Long spuId);
}
