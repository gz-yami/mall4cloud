package com.mall4j.cloud.order.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

/**
 * 订单支付记录VO
 *
 * @author FrozenWatermelon
 * @date 2020-12-04 11:27:35
 */
public class OrderPayInfoVO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "支付单号" )
    private Long payId;

    @Schema(description = "用户id" )
    private Long userId;

    @Schema(description = "外部订单流水号" )
    private String bizPayNo;

    @Schema(description = "系统类型 见SysTypeEnum" )
    private Integer sysType;

    @Schema(description = "支付状态" )
    private Integer payStatus;

    @Schema(description = "支付金额" )
    private Long payAmount;

    @Schema(description = "版本号" )
    private Integer version;

    @Schema(description = "回调内容" )
    private String callbackContent;

    @Schema(description = "回调时间" )
    private Date callbackTime;

    @Schema(description = "确认时间" )
    private Date confirmTime;

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBizPayNo() {
		return bizPayNo;
	}

	public void setBizPayNo(String bizPayNo) {
		this.bizPayNo = bizPayNo;
	}

	public Integer getSysType() {
		return sysType;
	}

	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCallbackContent() {
		return callbackContent;
	}

	public void setCallbackContent(String callbackContent) {
		this.callbackContent = callbackContent;
	}

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	@Override
	public String toString() {
		return "OrderPayInfoVO{" +
				"payId=" + payId +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				",userId=" + userId +
				",bizPayNo=" + bizPayNo +
				",sysType=" + sysType +
				",payStatus=" + payStatus +
				",payAmount=" + payAmount +
				",version=" + version +
				",callbackContent=" + callbackContent +
				",callbackTime=" + callbackTime +
				",confirmTime=" + confirmTime +
				'}';
	}
}
