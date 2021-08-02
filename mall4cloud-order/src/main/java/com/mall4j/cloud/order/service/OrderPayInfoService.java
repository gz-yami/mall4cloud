package com.mall4j.cloud.order.service;

import com.mall4j.cloud.order.model.OrderPayInfo;

/**
 * 订单支付记录
 *
 * @author FrozenWatermelon
 * @date 2020-12-04 11:27:35
 */
public interface OrderPayInfoService {

	/**
	 * 保存订单支付记录
	 * @param orderPayInfo 订单支付记录
	 */
	void save(OrderPayInfo orderPayInfo);

	/**
	 * 更新订单支付记录
	 * @param orderPayInfo 订单支付记录
	 */
	void update(OrderPayInfo orderPayInfo);

	/**
	 * 根据订单支付记录id删除订单支付记录
	 * @param payId
	 */
	void deleteById(Long payId);
}
