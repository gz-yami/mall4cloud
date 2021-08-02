package com.mall4j.cloud.api.vo.search;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2021/2/5
 */
public class EsOrderVO {

    @ApiModelProperty(value = "订单项",required=true)
    private List<EsOrderItemVO> orderItems;

    @ApiModelProperty(value = "订单号",required=true)
    private Long orderId;

    @ApiModelProperty(value = "总价",required=true)
    private Long actualTotal;

    @ApiModelProperty(value = "订单状态",required=true)
    private Integer status;

    @ApiModelProperty(value = "配送类型 3：无需快递",required=true)
    private Integer deliveryType;

    @ApiModelProperty(value = "店铺名称",required=true)
    private String shopName;

    @ApiModelProperty(value = "店铺id",required=true)
    private Long shopId;

    @ApiModelProperty(value = "订单创建时间",required=true)
    private Date createTime;

    @ApiModelProperty(value = "商品总数",required=true)
    private Integer allCount;

    @ApiModelProperty(value = "收货人姓名")
    private String consignee;

    @ApiModelProperty(value = "收货人手机号")
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
