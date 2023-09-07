package com.mall4j.cloud.api.vo.search;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2021/2/5
 */
public class EsOrderVO {

    @Schema(description = "订单项" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private List<EsOrderItemVO> orderItems;

    @Schema(description = "订单号" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;

    @Schema(description = "总价" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Long actualTotal;

    @Schema(description = "订单状态" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer status;

    @Schema(description = "配送类型 3：无需快递" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer deliveryType;

    @Schema(description = "店铺名称" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private String shopName;

    @Schema(description = "店铺id" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Long shopId;

    @Schema(description = "订单创建时间" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Date createTime;

    @Schema(description = "商品总数" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer allCount;

    @Schema(description = "收货人姓名" )
    private String consignee;

    @Schema(description = "收货人手机号" )
    private String mobile;

    /**
     * 用户订单地址Id
     */
    private Long orderAddrId;

    /**
     * 总值
     */
    private Long total;

    /**
     * 订单关闭原因 1-超时未支付4-买家取消 15-已通过货到付款交易
     */
    private Integer closeType;

    /**
     * 付款时间
     */
    private Date payTime;

    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 完成时间
     */
    private Date finallyTime;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 是否已支付，1.已支付0.未支付
     */
    private Integer isPayed;

    /**
     * 用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除
     */
    private Integer deleteStatus;

    public Long getOrderAddrId() {
        return orderAddrId;
    }

    public void setOrderAddrId(Long orderAddrId) {
        this.orderAddrId = orderAddrId;
    }

    public List<EsOrderItemVO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<EsOrderItemVO> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getCloseType() {
        return closeType;
    }

    public void setCloseType(Integer closeType) {
        this.closeType = closeType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
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

    public Integer getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(Integer isPayed) {
        this.isPayed = isPayed;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "EsOrderVO{" +
                "orderItems=" + orderItems +
                ", orderId=" + orderId +
                ", actualTotal=" + actualTotal +
                ", status=" + status +
                ", deliveryType=" + deliveryType +
                ", shopName='" + shopName + '\'' +
                ", shopId=" + shopId +
                ", createTime=" + createTime +
                ", allCount=" + allCount +
                ", consignee='" + consignee + '\'' +
                ", mobile='" + mobile + '\'' +
                ", orderAddrId=" + orderAddrId +
                ", total=" + total +
                ", closeType=" + closeType +
                ", payTime=" + payTime +
                ", deliveryTime=" + deliveryTime +
                ", finallyTime=" + finallyTime +
                ", cancelTime=" + cancelTime +
                ", isPayed=" + isPayed +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
