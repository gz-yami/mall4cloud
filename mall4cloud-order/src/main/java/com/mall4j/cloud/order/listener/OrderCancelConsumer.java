package com.mall4j.cloud.order.listener;

import com.mall4j.cloud.common.rocketmq.config.RocketMqConstant;
import com.mall4j.cloud.order.service.OrderService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author FrozenWatermelon
 */
@Component
@RocketMQMessageListener(topic = RocketMqConstant.ORDER_CANCEL_TOPIC,consumerGroup = RocketMqConstant.ORDER_CANCEL_TOPIC)
public class OrderCancelConsumer implements RocketMQListener<List<Long>> {

    @Autowired
    private OrderService orderService;

    /**
     * 订单取消状态修改后再进行其他服务
     */
    @Override
    public void onMessage(List<Long> orderIds) {
        // 如果订单未支付的话，将订单设为取消状态
        orderService.cancelOrderAndGetCancelOrderIds(orderIds);
    }
}
