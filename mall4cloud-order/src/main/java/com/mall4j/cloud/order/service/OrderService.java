package com.mall4j.cloud.order.service;

import com.mall4j.cloud.api.order.bo.EsOrderBO;
import com.mall4j.cloud.api.order.bo.OrderSimpleAmountInfoBO;
import com.mall4j.cloud.api.order.bo.OrderStatusBO;
import com.mall4j.cloud.api.order.dto.DeliveryOrderDTO;
import com.mall4j.cloud.api.order.vo.OrderAmountVO;
import com.mall4j.cloud.common.order.vo.ShopCartOrderMergerVO;
import com.mall4j.cloud.order.bo.SubmitOrderPayAmountInfoBO;
import com.mall4j.cloud.order.model.Order;
import com.mall4j.cloud.order.vo.OrderCountVO;
import com.mall4j.cloud.order.vo.OrderVO;

import java.util.List;

/**
 * 订单信息
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 14:13:50
 */
public interface OrderService {

	/**
	 * 更新订单信息
	 * @param order 订单信息
	 */
	void update(Order order);

	/**
	 * 根据订单信息id删除订单信息
	 * @param orderId
	 */
	void deleteById(Long orderId);

	/**
	 * 提交订单
	 * @param userId
	 * @param mergerOrder
	 * @return
	 */
    List<Long> submit(Long userId, ShopCartOrderMergerVO mergerOrder);

	/**
	 * 查询订单状态
	 * @param orderIds 多个订单的订单id
	 * @return 订单状态列表
	 */
	List<OrderStatusBO> getOrdersStatus(List<Long> orderIds);

	/**
	 * 计算订单实际金额
	 * @param orderIds 多个订单的订单id
	 * @return 订单实际金额总和
	 */
	OrderAmountVO getOrdersActualAmount(List<Long> orderIds);

	/**
	 * 将订单改为已支付状态
	 * @param orderIds 订单ids
	 */
    void updateByToPaySuccess(List<Long> orderIds);

	/**
	 * 获取订单中的金额信息，不包含退款
	 * @param orderIds 多个订单的订单id
	 * @return 订单商家id列表
	 */
	List<OrderSimpleAmountInfoBO> getOrdersSimpleAmountInfo(List<Long> orderIds);

	/**
	 * 取消订单
	 * @param orderId 订单id
	 */
	void cancelOrderAndGetCancelOrderIds(List<Long> orderId);

	/**
	 * 根据订单号和用户id获取订单
	 * @param orderId orderId
	 * @param userId userId
	 * @return Order
	 */
	Order getOrderByOrderIdAndUserId(Long orderId, Long userId);

	/**
	 * 根据订单号和店铺id获取订单
	 * @param orderId orderId
	 * @return Order
	 */
	OrderVO getOrderByOrderId(Long orderId);
	/**
	 * 更新订单
	 * @param orderId 订单号
	 * @return
	 */
	int receiptOrder(Long orderId);

	/**
	 * 根据订单号删除订单
	 * @param orderId 订单号
	 */
	void deleteOrder(Long orderId);

	/**
	 * 订单物流发货
	 * @param deliveryOrderParam 发货参数
	 */
    void delivery(DeliveryOrderDTO deliveryOrderParam);

	/**
	 * 根据订单id列表获取订单金额信息
	 * @param orderIdList 订单id列表
	 * @return
	 */
    SubmitOrderPayAmountInfoBO getSubmitOrderPayAmountInfo(long[] orderIdList);

	/**
	 * 获取订单需要保存到es中的数据
	 * @param orderId 订单id
	 * @return
	 */
    EsOrderBO getEsOrder(Long orderId);

	/**
	 * 获取订单和订单项信息
	 * @param orderId 订单id
	 * @param shopId  店铺id
	 * @return
	 */
	Order getOrderAndOrderItemData(Long orderId,Long shopId);

	/**
	 * 计算每个订单状态的状态数量
	 * @param userId 用户id
	 * @return
	 */
	OrderCountVO countNumberOfStatus(Long userId);
}
