package com.mall4j.cloud.order.bo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * @author FrozenWatermelon
 * @date 2021/2/4
 */
public class SubmitOrderPayAmountInfoBO {


    @Schema(description = "创建时间" )
    private Date createTime;

    @Schema(description = "总共需要支付金额" )
    private Long totalFee;

    @Schema(description = "订单地址id" )
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
