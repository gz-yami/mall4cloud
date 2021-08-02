package com.mall4j.cloud.order.mapper;

import com.mall4j.cloud.api.order.bo.EsOrderBO;
import com.mall4j.cloud.api.order.bo.OrderSimpleAmountInfoBO;
import com.mall4j.cloud.api.order.bo.OrderStatusBO;
import com.mall4j.cloud.api.order.vo.OrderAmountVO;
import com.mall4j.cloud.order.bo.SubmitOrderPayAmountInfoBO;
import com.mall4j.cloud.order.model.Order;
import com.mall4j.cloud.order.vo.OrderCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单信息
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 14:13:50
 */
public interface OrderMapper {

	/**
	 * 更新订单信息
	 *
	 * @param order 订单信息
	 */
	void update(@Param("order") Order order);

	/**
	 * 根据订单信息id删除订单信息
	 *
	 * @param orderId
	 */
	void deleteById(@Param("orderId") Long orderId);

	/**
	 * 批量保存订单数据
	 *
	 * @param orders
	 */
	void saveBatch(@Param("orders") List<Order> orders);

	/**
	 * 查询订单状态
	 *
	 * @param orderIds 多个订单的订单id
	 * @return 订单状态列表
	 */
	List<OrderStatusBO> getOrdersStatus(@Param("orderIds") List<Long> orderIds);

	/**
	 * 计算订单实际金额
	 *
	 * @param orderIds 多个订单的订单id
	 * @return 订单实际金额总和
	 */
	OrderAmountVO getOrdersActualAmount(@Param("orderIds") List<Long> orderIds);

	/**
	 * 将订单改为已支付状态
	 *
	 * @param orderIds 订单ids
	 */
	void updateByToPaySuccess(@Param("orderIds") List<Long> orderIds);

	/**
	 * 获取订单中的金额信息
	 *
	 * @param orderIds 多个订单的订单id
	 * @return 获取订单中的金额信息
	 */
	List<OrderSimpleAmountInfoBO> getOrdersSimpleAmountInfo(@Param("orderIds") List<Long> orderIds);

	/**
	 * 取消订单
	 *
	 * @param orderIds 订单ids
	 */
	void cancelOrders(@Param("orderIds") List<Long> orderIds);

	/**
	 * 根据订单号和用户id获取订单
	 *
	 * @param orderId orderId
	 * @param userId  userId
	 * @return Order
	 */
	Order getOrderByOrderIdAndUserId(@Param("orderId") Long orderId, @Param("userId") Long userId);

	/**
	 * 确认收货
	 * @param orderId
	 * @return
	 */
	int receiptOrder(@Param("orderId") Long orderId);


	/**
	 * 根据订单号删除订单
	 *
	 * @param orderId 订单号
	 */
	void deleteOrder(@Param("orderId") Long orderId);

	/**
	 * 根据订单号和店铺id获取订单
	 *
	 * @param orderId orderId
	 * @param shopId  shopId
	 * @return Order
	 */
    Order getOrderByOrderIdAndShopId(@Param("orderId") Long orderId, @Param("shopId") Long shopId);

	/**
	 * 获取订单和订单项的数据
	 *
	 * @param orderId
	 * @param shopId
	 * @return
	 */
	Order getOrderAndOrderItemData(@Param("orderId") Long orderId, @Param("shopId") Long shopId);

	/**
	 * 根据订单id列表获取订单金额信息
	 *
	 * @param orderIdList 订单id列表
	 * @return
	 */
    SubmitOrderPayAmountInfoBO getSubmitOrderPayAmountInfo(@Param("orderIdList") long[] orderIdList);

	/**
	 * 获取订单需要保存到es中的数据
	 *
	 * @param orderId 订单id
	 * @return
	 */
    EsOrderBO getEsOrder(@Param("orderId") Long orderId);

	/**
	 * 计算每个订单状态的状态数量
	 *
	 * @param userId 用户id
	 * @return
	 */
    OrderCountVO countNumberOfStatus(@Param("userId") Long userId);
}
