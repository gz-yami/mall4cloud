package com.mall4j.cloud.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author FrozenWatermelon
 */
@ApiModel("我的订单数量")
public class OrderCountVO {

    @ApiModelProperty(value = "所有订单数量")
    private Integer allCount;

    @ApiModelProperty(value = "待付款")
    private Integer unPay;

    @ApiModelProperty(value = "待发货")
    private Integer payed;

    @ApiModelProperty(value = "待收货")
    private Integer consignment;

    @ApiModelProperty(value = "已完成")
    private Integer success;

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getUnPay() {
        return unPay;
    }

    public void setUnPay(Integer unPay) {
        this.unPay = unPay;
    }

    public Integer getPayed() {
        return payed;
    }

    public void setPayed(Integer payed) {
        this.payed = payed;
    }

    public Integer getConsignment() {
        return consignment;
    }

    public void setConsignment(Integer consignment) {
        this.consignment = consignment;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "OrderCountVO{" +
                "allCount=" + allCount +
                ", unPay=" + unPay +
                ", payed=" + payed +
                ", consignment=" + consignment +
                ", success=" + success +
                '}';
    }
}
