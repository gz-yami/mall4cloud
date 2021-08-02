package com.mall4j.cloud.common.rocketmq.config;

/**
 * nameserver用;分割
 * 同步消息，如果两次
 * @author FrozenWatermelon
 * @date 2021/3/25
 */
public class RocketMqConstant {

    // 延迟消息 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h (1-18)

    /**
     * 取消订单时间，实际上30分钟
     */
    public static final int CANCEL_ORDER_DELAY_LEVEL = 16;

    /**
     * 默认发送消息超时时间
     */
    public static final long TIMEOUT = 3000;


    /**
     * 库存解锁topic
     */
    public static final String STOCK_UNLOCK_TOPIC = "stock-unlock-topic";

    /**
     * 订单取消
     */
    public static final String ORDER_CANCEL_TOPIC = "order-cancel-topic";

    /**
     * 订单支付成功
     */
    public static final String ORDER_NOTIFY_TOPIC = "order-notify-topic";

    /**
     * 订单支付成功 通知库存服务
     */
    public static final String ORDER_NOTIFY_STOCK_TOPIC = "order-notify-stock-topic";
    /**
     * 商品搜索topic
     */
    public static final String CANAL_TOPIC = "canal-topic";



}
