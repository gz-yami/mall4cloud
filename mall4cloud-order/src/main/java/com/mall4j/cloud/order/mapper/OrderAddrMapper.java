package com.mall4j.cloud.order.mapper;

import com.mall4j.cloud.order.model.OrderAddr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户订单配送地址
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 14:13:50
 */
public interface OrderAddrMapper {

	/**
	 * 获取用户订单配送地址列表
	 * @return 用户订单配送地址列表
	 */
	List<OrderAddr> list();

	/**
	 * 根据用户订单配送地址id获取用户订单配送地址
	 *
	 * @param orderAddrId 用户订单配送地址id
	 * @return 用户订单配送地址
	 */
	OrderAddr getByOrderAddrId(@Param("orderAddrId") Long orderAddrId);

	/**
	 * 保存用户订单配送地址
	 * @param orderAddr 用户订单配送地址
	 */
	void save(@Param("orderAddr") OrderAddr orderAddr);

	/**
	 * 更新用户订单配送地址
	 * @param orderAddr 用户订单配送地址
	 */
	void update(@Param("orderAddr") OrderAddr orderAddr);

	/**
	 * 根据用户订单配送地址id删除用户订单配送地址
	 * @param orderAddrId
	 */
	void deleteById(@Param("orderAddrId") Long orderAddrId);
}
