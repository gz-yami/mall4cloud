package com.mall4j.cloud.payment.bo;

/**
 * @author FrozenWatermelon
 * @date 2020/12/25
 */
public class PayInfoBO {

    /**
     * 支付信息，如商品名称
     */
    private String body;

    /**
     * 支付单号
     */
    private Long payId;

    /**
     * 付款金额
     */
    private Long payAmount;

    /**
     * api回调域名
     */
    private String apiNoticeUrl;

    /**
     * 支付完成会跳地址
     */
    private String returnUrl;

    /**
     * 第三方用户id
     */
    private String bizUserId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public String getApiNoticeUrl() {
        return apiNoticeUrl;
    }

    public void setApiNoticeUrl(String apiNoticeUrl) {
        this.apiNoticeUrl = apiNoticeUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    @Override
    public String toString() {
        return "PayInfoBO{" +
                "body='" + body + '\'' +
                ", payId='" + payId + '\'' +
                ", payAmount=" + payAmount +
                ", apiNoticeUrl='" + apiNoticeUrl + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", bizUserId='" + bizUserId + '\'' +
                '}';
    }
}
