package com.mall4j.cloud.payment.mapper;

import com.mall4j.cloud.payment.model.PayInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 订单支付记录
 *
 * @author FrozenWatermelon
 * @date 2020-12-25 09:50:59
 */
public interface PayInfoMapper {

	/**
	 * 根据订单支付记录id获取订单支付记录
	 *
	 * @param payId 订单支付记录id
	 * @return 订单支付记录
	 */
	PayInfo getByPayId(@Param("payId") Long payId);

	/**
	 * 保存订单支付记录
	 *
	 * @param payInfo 订单支付记录
	 */
	void save(@Param("payInfo") PayInfo payInfo);

	/**
	 * 更新订单支付记录
	 *
	 * @param payInfo 订单支付记录
	 */
	void update(@Param("payInfo") PayInfo payInfo);

	/**
	 * 根据订单支付记录id删除订单支付记录
	 *
	 * @param payId
	 */
	void deleteById(@Param("payId") Long payId);

	/**
	 * 根据支付订单号获取订单支付状态
	 *
	 * @param orderIds 订单号ids
	 * @return 支付状态
	 */
	Integer getPayStatusByOrderIds(@Param("orderIds") String orderIds);

	/**
	 * 查询订单是否已经支付
	 * @param orderIds 订单id
	 * @param userId 用户id
	 * @return 是否已经支付
	 */
	Integer isPay(@Param("orderIds") String orderIds, @Param("userId") Long userId);
}
