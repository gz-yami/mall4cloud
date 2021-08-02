package com.mall4j.cloud.order.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 提交订单的支付信息
 * @author FrozenWatermelon
 * @date 2021/2/4
 */
public class SubmitOrderPayInfoVO {

    @ApiModelProperty(value = "商品名称")
    private List<String> spuNameList;

    @ApiModelProperty(value = "收货人姓名")
    private String consignee;

    @ApiModelProperty(value = "收货地址")
    private String userAddr;

    @ApiModelProperty(value = "收货人手机号")
    private String mobile;

    @ApiModelProperty(value = "订单过期时间")
    private Date endTime;

    @ApiModelProperty(value = "总共需要支付金额")
    private Long totalFee;

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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public List<String> getSpuNameList() {
        return spuNameList;
    }

    public void setSpuNameList(List<String> spuNameList) {
        this.spuNameList = spuNameList;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    @Override
    public String toString() {
        return "SubmitOrderPayInfoVO{" +
                "spuNameList=" + spuNameList +
                ", consignee='" + consignee + '\'' +
                ", userAddr='" + userAddr + '\'' +
                ", mobile='" + mobile + '\'' +
                ", endTime=" + endTime +
                ", totalFee=" + totalFee +
                '}';
    }
}
