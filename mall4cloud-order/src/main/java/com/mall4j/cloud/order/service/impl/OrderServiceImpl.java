package com.mall4j.cloud.order.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mall4j.cloud.api.leaf.feign.SegmentFeignClient;
import com.mall4j.cloud.api.order.bo.EsOrderBO;
import com.mall4j.cloud.api.order.bo.OrderSimpleAmountInfoBO;
import com.mall4j.cloud.api.order.bo.OrderStatusBO;
import com.mall4j.cloud.api.order.constant.DeliveryType;
import com.mall4j.cloud.api.order.constant.OrderStatus;
import com.mall4j.cloud.api.order.dto.DeliveryOrderDTO;
import com.mall4j.cloud.api.order.vo.OrderAmountVO;
import com.mall4j.cloud.api.product.dto.SkuStockLockDTO;
import com.mall4j.cloud.api.product.feign.ShopCartFeignClient;
import com.mall4j.cloud.api.product.feign.SkuStockLockFeignClient;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.order.vo.ShopCartItemVO;
import com.mall4j.cloud.common.order.vo.ShopCartOrderMergerVO;
import com.mall4j.cloud.common.order.vo.ShopCartOrderVO;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.rocketmq.config.RocketMqConstant;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.order.bo.SubmitOrderPayAmountInfoBO;
import com.mall4j.cloud.order.mapper.OrderMapper;
import com.mall4j.cloud.order.model.Order;
import com.mall4j.cloud.order.model.OrderAddr;
import com.mall4j.cloud.order.model.OrderItem;
import com.mall4j.cloud.order.service.OrderAddrService;
import com.mall4j.cloud.order.service.OrderItemService;
import com.mall4j.cloud.order.service.OrderService;
import com.mall4j.cloud.order.vo.OrderCountVO;
import com.mall4j.cloud.order.vo.OrderVO;
import io.seata.spring.annotation.GlobalTransactional;
import com.mall4j.cloud.common.util.BeanUtil;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 订单信息
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 14:13:50
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private SegmentFeignClient segmentFeignClient;
    @Autowired
    private SkuStockLockFeignClient skuStockLockFeignClient;

    @Autowired
    private ShopCartFeignClient shopCartFeignClient;
    @Autowired
    private RocketMQTemplate stockMqTemplate;
    @Autowired
    private RocketMQTemplate orderCancelTemplate;
    @Autowired
    private OrderAddrService orderAddrService;

    @Override
    public void update(Order order) {
        orderMapper.update(order);
    }

    @Override
    public void deleteById(Long orderId) {
        orderMapper.deleteById(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> submit(Long userId, ShopCartOrderMergerVO mergerOrder) {
        List<Order> orders = saveOrder(userId, mergerOrder);
        List<Long> orderIds = new ArrayList<>();
        List<SkuStockLockDTO> skuStockLocks = new ArrayList<>();
        for (Order order : orders) {
            orderIds.add(order.getOrderId());
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                skuStockLocks.add(new SkuStockLockDTO(orderItem.getSpuId(), orderItem.getSkuId(), orderItem.getOrderId(), orderItem.getCount()));
            }
        }
        // 锁定库存
        ServerResponseEntity<Void> lockStockResponse = skuStockLockFeignClient.lock(skuStockLocks);
        // 锁定不成，抛异常，让回滚订单
        if (!lockStockResponse.isSuccess()) {
            throw new Mall4cloudException(lockStockResponse.getMsg());
        }
        // 发送消息，如果三十分钟后没有支付，则取消订单
        SendStatus sendStatus = orderCancelTemplate.syncSend(RocketMqConstant.ORDER_CANCEL_TOPIC, new GenericMessage<>(orderIds), RocketMqConstant.TIMEOUT, RocketMqConstant.CANCEL_ORDER_DELAY_LEVEL).getSendStatus();
        if (!Objects.equals(sendStatus,SendStatus.SEND_OK)) {
            // 消息发不出去就抛异常，发的出去无所谓
            throw new Mall4cloudException(ResponseEnum.EXCEPTION);
        }
        return orderIds;
    }


    @Override
    public List<OrderStatusBO> getOrdersStatus(List<Long> orderIds) {
        List<OrderStatusBO> orderStatusList = orderMapper.getOrdersStatus(orderIds);
        for (Long orderId : orderIds) {
            boolean hasOrderId = false;
            for (OrderStatusBO orderStatusBO : orderStatusList) {
                if (Objects.equals(orderStatusBO.getOrderId(), orderId)) {
                    hasOrderId = true;
                }
            }
            if (!hasOrderId) {
                OrderStatusBO orderStatusBO = new OrderStatusBO();
                orderStatusBO.setOrderId(orderId);
                orderStatusList.add(orderStatusBO);
            }
        }
        return orderStatusList;
    }

    @Override
    public OrderAmountVO getOrdersActualAmount(List<Long> orderIds) {
        return orderMapper.getOrdersActualAmount(orderIds);
    }

    @Override
    public void updateByToPaySuccess(List<Long> orderIds) {
        orderMapper.updateByToPaySuccess(orderIds);
    }

    @Override
    public List<OrderSimpleAmountInfoBO> getOrdersSimpleAmountInfo(List<Long> orderIds) {
        return orderMapper.getOrdersSimpleAmountInfo(orderIds);
    }

    /**
     * 取消订单和mq日志要同时落地，所以用分布式事务
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrderAndGetCancelOrderIds(List<Long> orderIds) {
        List<OrderStatusBO> ordersStatus = orderMapper.getOrdersStatus(orderIds);
        List<Long> cancelOrderIds = new ArrayList<>();
        for (OrderStatusBO orderStatusBO : ordersStatus) {
            if (orderStatusBO.getStatus() != null || !Objects.equals(orderStatusBO.getStatus(), OrderStatus.CLOSE.value()))  {
                cancelOrderIds.add(orderStatusBO.getOrderId());
            }
        }
        if (CollectionUtil.isEmpty(cancelOrderIds)) {
            return;
        }
        orderMapper.cancelOrders(cancelOrderIds);
        // 解锁库存状态
        SendStatus stockSendStatus = stockMqTemplate.syncSend(RocketMqConstant.STOCK_UNLOCK_TOPIC, new GenericMessage<>(orderIds)).getSendStatus();
        if (!Objects.equals(stockSendStatus,SendStatus.SEND_OK)) {
            // 消息发不出去就抛异常，发的出去无所谓
            throw new Mall4cloudException(ResponseEnum.EXCEPTION);
        }
    }

    @Override
    public Order getOrderByOrderIdAndUserId(Long orderId, Long userId) {
        Order order = orderMapper.getOrderByOrderIdAndUserId(orderId, userId);
        if (order == null) {
            // 订单不存在
            throw new Mall4cloudException(ResponseEnum.ORDER_NOT_EXIST);
        }
        return order;
    }

    @Override
    public OrderVO getOrderByOrderId(Long orderId) {
        Order order = orderMapper.getOrderByOrderIdAndShopId(orderId, AuthUserContext.get().getTenantId());
        if (order == null) {
            // 订单不存在
            throw new Mall4cloudException(ResponseEnum.ORDER_NOT_EXIST);
        }
        return BeanUtil.map(order, OrderVO.class);
    }

    /**
     * 确认收货订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int receiptOrder(Long orderId) {
        return orderMapper.receiptOrder(orderId);
    }


    @Override
    public void deleteOrder(Long orderId) {
        orderMapper.deleteOrder(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional(rollbackFor = Exception.class)
    public void delivery(DeliveryOrderDTO deliveryOrderParam) {
        //修改为发货状态
        Date date = new Date();
        Order order = new Order();
        order.setOrderId(deliveryOrderParam.getOrderId());
        order.setStatus(OrderStatus.CONSIGNMENT.value());
        order.setUpdateTime(date);
        order.setDeliveryTime(date);
        //无需物流
        order.setDeliveryType(DeliveryType.NOT_DELIVERY.value());
        orderMapper.update(order);

    }

    @Override
    public SubmitOrderPayAmountInfoBO getSubmitOrderPayAmountInfo(long[] orderIdList) {
        return orderMapper.getSubmitOrderPayAmountInfo(orderIdList);
    }

    @Override
    public EsOrderBO getEsOrder(Long orderId) {
        return orderMapper.getEsOrder(orderId);
    }


    public List<Order> saveOrder(Long userId, ShopCartOrderMergerVO mergerOrder) {
        OrderAddr orderAddr = BeanUtil.map(mergerOrder.getUserAddr(), OrderAddr.class);
        // 地址信息
        if (Objects.isNull(orderAddr)) {
            // 请填写收货地址
            throw new Mall4cloudException("请填写收货地址");
        }
        // 保存收货地址
        orderAddrService.save(orderAddr);
        // 订单商品参数
        List<ShopCartOrderVO> shopCartOrders = mergerOrder.getShopCartOrders();
        List<Order> orders = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        List<Long> shopCartItemIds = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(shopCartOrders)) {
            // 每个店铺生成一个订单
            for (ShopCartOrderVO shopCartOrderDto : shopCartOrders) {
                Order order = getOrder(userId, mergerOrder.getDvyType(), shopCartOrderDto);
                for (ShopCartItemVO shopCartItemVO : shopCartOrderDto.getShopCartItemVO()) {
                    OrderItem orderItem = getOrderItem(order, shopCartItemVO);
                    orderItems.add(orderItem);
                    shopCartItemIds.add(shopCartItemVO.getCartItemId());
                }
                order.setOrderItems(orderItems);
                order.setOrderAddrId(orderAddr.getOrderAddrId());
                orders.add(order);
            }
        }
        orderMapper.saveBatch(orders);
        orderItemService.saveBatch(orderItems);
        // 清空购物车
        shopCartFeignClient.deleteItem(shopCartItemIds);
        return orders;
    }

    private OrderItem getOrderItem(Order order, ShopCartItemVO shopCartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getOrderId());
        orderItem.setShopId(shopCartItem.getShopId());
        orderItem.setSkuId(shopCartItem.getSkuId());
        orderItem.setSpuId(shopCartItem.getSpuId());
        orderItem.setSkuName(shopCartItem.getSkuName());
        orderItem.setCount(shopCartItem.getCount());
        orderItem.setSpuName(shopCartItem.getSpuName());
        orderItem.setPic(shopCartItem.getImgUrl());
        orderItem.setPrice(shopCartItem.getSkuPriceFee());
        orderItem.setUserId(order.getUserId());
        orderItem.setSpuTotalAmount(shopCartItem.getTotalAmount());
        orderItem.setShopCartTime(shopCartItem.getCreateTime());
        // 订单项支付金额
        orderItem.setSpuTotalAmount(shopCartItem.getTotalAmount());
        return orderItem;
    }

    private Order getOrder(Long userId, Integer dvyType, ShopCartOrderVO shopCartOrderDto) {
        ServerResponseEntity<Long> segmentIdResponse = segmentFeignClient.getSegmentId(Order.DISTRIBUTED_ID_KEY);
        if (!segmentIdResponse.isSuccess()) {
            throw new Mall4cloudException("获取订单id失败");
        }
        // 订单信息
        Order order = new Order();
        order.setOrderId(segmentIdResponse.getData());
        order.setShopId(shopCartOrderDto.getShopId());
        order.setShopName(shopCartOrderDto.getShopName());

        // 用户id
        order.setUserId(userId);
        // 商品总额
        order.setTotal(shopCartOrderDto.getTotal());
        order.setStatus(OrderStatus.UNPAY.value());
        order.setIsPayed(0);
        order.setDeleteStatus(0);
        order.setAllCount(shopCartOrderDto.getTotalCount());
        order.setDeliveryType(DeliveryType.NOT_DELIVERY.value());
        return order;
    }

    @Override
    public Order getOrderAndOrderItemData(Long orderId, Long shopId) {
        return orderMapper.getOrderAndOrderItemData(orderId, shopId);
    }

    @Override
    public OrderCountVO countNumberOfStatus(Long userId) {
        return orderMapper.countNumberOfStatus(userId);
    }
}
