package com.mall4j.cloud.api.order.bo;

/**
 * @author FrozenWatermelon
 * @date 2020/12/30
 */
public class OrderSimpleAmountInfoBO {

    private Long orderId;

    private Long shopId;

    /**
     * 实际总值
     */
    private Long actualTotal;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单关闭原因
     */
    private Integer closeType;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(Long actualTotal) {
        this.actualTotal = actualTotal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCloseType() {
        return closeType;
    }

    public void setCloseType(Integer closeType) {
        this.closeType = closeType;
    }

    @Override
    public String toString() {
        return "OrderSimpleAmountInfoBO{" +
                "orderId=" + orderId +
                ", shopId=" + shopId +
                ", actualTotal=" + actualTotal +
                ", status=" + status +
                ", closeType=" + closeType +
                '}';
    }
}
