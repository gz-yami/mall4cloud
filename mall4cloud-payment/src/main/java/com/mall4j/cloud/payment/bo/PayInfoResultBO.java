package com.mall4j.cloud.payment.bo;

/**
 * 支付后返回的一些基础数据
 * @author FrozenWatermelon
 * @date 2020/12/25
 */
public class PayInfoResultBO {

	/**
	 * 商城支付单号
	 */
	private Long payId;

	/**
	 * 第三方订单流水号
	 */
	private String bizPayNo;

	/**
	 * 是否支付成功
	 */
	private Integer isPaySuccess;

	/**
	 * 支付成功的标记
	 */
	private String successString;

	/**
	 * 支付金额
	 */
	private Long payAmount;

	/**
	 * 回调内容
	 */
	private String callbackContent;

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public String getBizPayNo() {
		return bizPayNo;
	}

	public void setBizPayNo(String bizPayNo) {
		this.bizPayNo = bizPayNo;
	}

	public Integer getIsPaySuccess() {
		return isPaySuccess;
	}

	public void setIsPaySuccess(Integer isPaySuccess) {
		this.isPaySuccess = isPaySuccess;
	}

	public String getSuccessString() {
		return successString;
	}

	public void setSuccessString(String successString) {
		this.successString = successString;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public String getCallbackContent() {
		return callbackContent;
	}

	public void setCallbackContent(String callbackContent) {
		this.callbackContent = callbackContent;
	}

	@Override
	public String toString() {
		return "PayInfoResultBO{" +
				"payId=" + payId +
				", bizPayNo='" + bizPayNo + '\'' +
				", isPaySuccess=" + isPaySuccess +
				", successString='" + successString + '\'' +
				", payAmount=" + payAmount +
				", callbackContent='" + callbackContent + '\'' +
				'}';
	}
}
