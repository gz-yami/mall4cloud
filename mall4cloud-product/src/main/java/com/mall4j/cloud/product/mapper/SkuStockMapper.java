package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.product.bo.SkuWithStockBO;
import com.mall4j.cloud.product.model.SkuStock;
import com.mall4j.cloud.product.vo.SkuStockVO;
import com.mall4j.cloud.api.product.vo.SkuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface SkuStockMapper {

	/**
	 * 保存库存信息
	 *
	 * @param skuStock 库存信息
	 */
	void save(@Param("skuStock") SkuStock skuStock);

	/**
	 * 更新库存信息
	 *
	 * @param skuStock 库存信息
	 */
	void update(@Param("skuStock") SkuStock skuStock);

	/**
	 * 根据库存信息id删除库存信息
	 *
	 * @param stockId
	 */
	void deleteById(@Param("stockId") Long stockId);

	/**
	 * 批量保存
	 *
	 * @param skuStocks 库存信息集合
	 */
	void saveBatch(@Param("skuStocks") List<SkuStock> skuStocks);

	/**
	 * 根据skuIds删除库存
	 *
	 * @param skuIds ids
	 */
	void deleteBySkuIds(@Param("skuIds") List<Long> skuIds);

	/**
	 * 根据列表中的库存数量，增加sku的库存
	 *
	 * @param skuStocks 修改信息
	 */
	void updateStock(@Param("skuStocks") List<SkuStock> skuStocks);

	/**
	 * 通过sku集合获取库存信息
	 *
	 * @param skuVOList
	 * @return 库存信息
	 */
	List<SkuStockVO> listBySkuList(@Param("skuVOList") List<SkuVO> skuVOList);

	/**
	 * 通过订单减少库存
	 *
	 * @param skuId 商品id
	 * @param count 数量
	 * @return
	 */
    int reduceStockByOrder(@Param("skuId") Long skuId, @Param("count") Integer count);

	/**
	 * 通过订单添加库存
	 *
	 * @param skuWithStocks 库存信息
	 */
    void addStockByOrder(@Param("skuWithStocks") List<SkuWithStockBO> skuWithStocks);

	/**
	 * 通过订单减少实际库存
	 *
	 * @param skuWithStocks 库存信息
	 */
    void reduceActualStockByOrder(@Param("skuWithStocks") List<SkuWithStockBO> skuWithStocks);

	/**
	 * 通过已经取消的订单减少实际库存
	 *
	 * @param skuWithStocks 库存信息
	 */
	void reduceActualStockByCancelOrder(@Param("skuWithStocks") List<SkuWithStockBO> skuWithStocks);

}
