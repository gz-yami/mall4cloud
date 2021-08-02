package com.mall4j.cloud.payment.service;

import com.mall4j.cloud.payment.bo.PayInfoBO;
import com.mall4j.cloud.payment.bo.PayInfoResultBO;
import com.mall4j.cloud.payment.dto.PayInfoDTO;
import com.mall4j.cloud.payment.model.PayInfo;

import java.util.List;

/**
 * 订单支付记录
 *
 * @author FrozenWatermelon
 * @date 2020-12-25 09:50:59
 */
public interface PayInfoService {

    /**
     * 创建支付订单，返回给前端，前端唤起应用进行支付
     * @param userId 用户id
     * @param payParam 支付参数
     * @return 前端唤起支付需要的参数
     */
    PayInfoBO pay(Long userId, PayInfoDTO payParam);

    /**
     * 根据订单支付记录id获取订单支付记录
     *
     * @param payId 订单支付记录id
     * @return 订单支付记录
     */
    PayInfo getByPayId(Long payId);

    /**
     * 标记为支付成功
     * @param payInfoResult 支付信息
     * @param orderIds 订单ids
     */
    void paySuccess(PayInfoResultBO payInfoResult, List<Long> orderIds);

    /**
     * 根据支付订单号获取订单支付状态
     * @param orderIds 订单号ids
     * @return 支付状态
     */
    Integer getPayStatusByOrderIds(String orderIds);

    /**
     * 查询订单是否已经支付
     * @param orderIds 订单id
     * @param userId 用户id
     * @return 是否已经支付
     */
    Integer isPay(String orderIds, Long userId);
}
