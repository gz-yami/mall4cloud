package com.mall4j.cloud.order.bo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author FrozenWatermelon
 * @date 2021/2/4
 */
public class SubmitOrderPayAmountInfoBO {


    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "总共需要支付金额")
    private Long totalFee;

    @ApiModelProperty(value = "订单地址id")
    private Long orderAddrId;

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOrderAddrId() {
        return orderAddrId;
    }

    public void setOrderAddrId(Long orderAddrId) {
        this.orderAddrId = orderAddrId;
    }

    @Override
    public String toString() {
        return "SubmitOrderPayAmountInfoBO{" +
                "createTime=" + createTime +
                ", totalFee=" + totalFee +
                ", orderAddrId=" + orderAddrId +
                '}';
    }
}
