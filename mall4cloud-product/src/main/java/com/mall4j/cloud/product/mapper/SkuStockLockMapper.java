package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.product.bo.SkuWithStockBO;
import com.mall4j.cloud.product.model.SkuStockLock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存锁定信息
 *
 * @author FrozenWatermelon
 * @date 2020-12-22 16:12:10
 */
public interface SkuStockLockMapper {

	/**
	 * 获取库存锁定信息列表
	 *
	 * @return 库存锁定信息列表
	 */
	List<SkuStockLock> list();

	/**
	 * 根据库存锁定信息id获取库存锁定信息
	 *
	 * @param id 库存锁定信息id
	 * @return 库存锁定信息
	 */
	SkuStockLock getById(@Param("id") Long id);

	/**
	 * 保存库存锁定信息
	 *
	 * @param skuStockLock 库存锁定信息
	 */
	void save(@Param("skuStockLock") SkuStockLock skuStockLock);

	/**
	 * 更新库存锁定信息
	 *
	 * @param skuStockLock 库存锁定信息
	 */
	void update(@Param("skuStockLock") SkuStockLock skuStockLock);

	/**
	 * 根据库存锁定信息id删除库存锁定信息
	 *
	 * @param id
	 */
	void deleteById(@Param("id") Long id);

	/**
	 * 批量保存库存锁定信息
	 *
	 * @param skuStockLocks 库存锁定信息
	 */
	void saveBatch(@Param("skuStockLocks") List<SkuStockLock> skuStockLocks);

	/**
	 * 根据订单号获取锁定的库存
	 *
	 * @param orderIds 订单号
	 * @return 锁定的库存信息
	 */
	List<SkuWithStockBO> listByOrderIds(@Param("orderIds") List<Long> orderIds);

	/**
	 * 将锁定状态标记为已解锁
	 *
	 * @param lockIds ids
	 */
	void unLockByIds(@Param("lockIds") List<Long> lockIds);

	/**
	 * 正式锁定库存，标记为使用状态
	 *
	 * @param orderIds 锁定库存的订单
	 */
    void markerStockUse(@Param("orderIds") List<Long> orderIds);

	/**
	 * 根据订单号获取已取消锁定的库存
	 *
	 * @param orderIds 订单号
	 * @return 锁定的库存信息
	 */
	List<SkuWithStockBO> listUnLockByOrderIds(@Param("orderIds") List<Long> orderIds);
}
