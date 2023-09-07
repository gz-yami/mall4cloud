package com.mall4j.cloud.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单下的每个店铺
 *
 * @author FrozenWatermelon
 */
public class OrderShopVO implements Serializable {

    @Schema(description = "店铺id" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Long shopId;

    @Schema(description = "店铺名称" , requiredMode = Schema.RequiredMode.REQUIRED)
    private String shopName;

    @Schema(description = "商品总值" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Long total;

    @Schema(description = "商品总数" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer totalNum;


    @Schema(description = "地址Dto" , requiredMode = Schema.RequiredMode.REQUIRED)
    private OrderAddrVO orderAddr;

    @Schema(description = "产品信息" , requiredMode = Schema.RequiredMode.REQUIRED)
    private List<OrderItemVO> orderItems;

    @Schema(description = "订单创建时间" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Date createTime;

    @Schema(description = "订单付款时间")
    private Date payTime;

    @Schema(description = "订单发货时间")
    private Date deliveryTime;

    @Schema(description = "订单完成时间")
    private Date finallyTime;

    @Schema(description = "订单取消时间")
    private Date cancelTime;

    @Schema(description = "订单更新时间")
    private Date updateTime;

    @Schema(description = "配送类型 3：无需快递" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer deliveryType;

    @Schema(description = "订单状态" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer status;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public List<OrderItemVO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemVO> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderAddrVO getOrderAddr() {
        return orderAddr;
    }

    public void setOrderAddr(OrderAddrVO orderAddr) {
        this.orderAddr = orderAddr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getFinallyTime() {
        return finallyTime;
    }

    public void setFinallyTime(Date finallyTime) {
        this.finallyTime = finallyTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    @Override
    public String toString() {
        return "OrderShopVO{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", total=" + total +
                ", totalNum=" + totalNum +
                ", orderAddr=" + orderAddr +
                ", orderItems=" + orderItems +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", deliveryTime=" + deliveryTime +
                ", finallyTime=" + finallyTime +
                ", cancelTime=" + cancelTime +
                ", updateTime=" + updateTime +
                ", deliveryType=" + deliveryType +
                ", status=" + status +
                '}';
    }
}
