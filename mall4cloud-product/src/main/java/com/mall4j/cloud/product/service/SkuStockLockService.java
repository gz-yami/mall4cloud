package com.mall4j.cloud.product.service;

import com.mall4j.cloud.api.product.dto.SkuStockLockDTO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.product.model.SkuStockLock;

import java.util.List;

/**
 * 库存锁定信息
 *
 * @author FrozenWatermelon
 * @date 2020-12-22 16:12:10
 */
public interface SkuStockLockService {

	/**
	 * 分页获取库存锁定信息列表
	 * @param pageDTO 分页参数
	 * @return 库存锁定信息列表分页数据
	 */
	PageVO<SkuStockLock> page(PageDTO pageDTO);

	/**
	 * 根据库存锁定信息id获取库存锁定信息
	 *
	 * @param id 库存锁定信息id
	 * @return 库存锁定信息
	 */
	SkuStockLock getById(Long id);

	/**
	 * 保存库存锁定信息
	 * @param skuStockLock 库存锁定信息
	 */
	void save(SkuStockLock skuStockLock);

	/**
	 * 更新库存锁定信息
	 * @param skuStockLock 库存锁定信息
	 */
	void update(SkuStockLock skuStockLock);

	/**
	 * 根据库存锁定信息id删除库存锁定信息
	 * @param id 库存锁定信息id
	 */
	void deleteById(Long id);

	/**
	 * 锁定库存
	 * @param skuStockLocksParam 参数
	 * @return 是否成功
	 */
	ServerResponseEntity<Void> lock(List<SkuStockLockDTO> skuStockLocksParam);

	/**
	 * 根据订单号进行库存解锁
	 * @param orderIds 订单ids
	 */
    void unlockStock(List<Long> orderIds);

	/**
	 * 正式锁定库存，标记为使用状态
	 * @param orderIds 订单ids
	 */
    void markerStockUse(List<Long> orderIds);
}
