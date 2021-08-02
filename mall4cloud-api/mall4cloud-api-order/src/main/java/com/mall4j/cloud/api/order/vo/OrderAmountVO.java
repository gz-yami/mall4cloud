package com.mall4j.cloud.api.order.vo;

/**
 * @author FrozenWatermelon
 * @date 2020/12/25
 */
public class OrderAmountVO {

    /**
     * 支付金额
     */
    private Long payAmount;

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    @Override
    public String toString() {
        return "OrderAmountVO{" +
                "payAmount=" + payAmount +
                '}';
    }
}
