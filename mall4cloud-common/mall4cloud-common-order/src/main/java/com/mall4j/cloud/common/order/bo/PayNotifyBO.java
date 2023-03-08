package com.mall4j.cloud.common.order.bo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 订单支付成功通知
 * @author FrozenWatermelon
 * @date 2020/12/8
 */
public class PayNotifyBO {

    private List<Long> orderIds;

    public PayNotifyBO(){

    }

    public PayNotifyBO(List<Long> orderIds) {
        this.orderIds = orderIds;
    }

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Long> orderIds) {
        this.orderIds = orderIds;
    }

    @Override
    public String toString() {
        return "PayNotifyBO{" +
                "orderIds=" + orderIds +
                '}';
    }
}
