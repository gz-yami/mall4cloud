package com.mall4j.cloud.product.listener;

import com.mall4j.cloud.common.rocketmq.config.RocketMqConstant;
import com.mall4j.cloud.product.service.SkuStockLockService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 解锁库存的监听
 * @author FrozenWatermelon
 */
@Component
@RocketMQMessageListener(topic = RocketMqConstant.STOCK_UNLOCK_TOPIC,consumerGroup = RocketMqConstant.STOCK_UNLOCK_TOPIC)
public class StockUnlockConsumer implements RocketMQListener<List<Long>> {

    @Autowired
    private SkuStockLockService skuStockLockService;

    /**
     * 1、库存锁定一定时间后，如果订单支付未支付，则解锁库存（有可能库存锁定成功，订单因为异常回滚导致订单未创建）
     * 2、取消订单，直接解锁库存
     */
    @Override
    public void onMessage(List<Long> orderIds) {
        skuStockLockService.unlockStock(orderIds);
    }
}
