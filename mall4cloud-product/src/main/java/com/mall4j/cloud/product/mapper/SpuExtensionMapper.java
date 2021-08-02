package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.product.bo.SkuWithStockBO;
import com.mall4j.cloud.product.model.SpuExtension;
import com.mall4j.cloud.product.vo.SpuExtensionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author FrozenWatermelon
 * @date 2020-11-11 13:49:06
 */
public interface SpuExtensionMapper {

	/**
	 * 获取列表
	 *
	 * @return 列表
	 */
	List<SpuExtensionVO> list();

	/**
	 * 根据id获取
	 *
	 * @param spuExtendId id
	 * @return
	 */
	SpuExtensionVO getBySpuExtendId(@Param("spuExtendId") Long spuExtendId);

	/**
	 * 保存
	 *
	 * @param spuExtension
	 */
	void save(@Param("spuExtension") SpuExtension spuExtension);

	/**
	 * 根据id删除
	 *
	 * @param spuId id
	 */
	void deleteById(@Param("spuId") Long spuId);

	/**
	 * 更新库存
	 *
	 * @param spuId 商品id
	 * @param count 商品数量
	 */
    void updateStock(@Param("spuId") Long spuId, @Param("count") Integer count);

	/**
	 * 通过订单减少库存
	 *
	 * @param spuId 商品id
	 * @param count 数量
	 * @return
	 */
	int reduceStockByOrder(@Param("spuId") Long spuId, @Param("count") Integer count);

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

	/**
	 * 获取spu扩展信息
	 * @param spuId
	 * @return
	 */
    SpuExtension getBySpuId(Long spuId);
}
