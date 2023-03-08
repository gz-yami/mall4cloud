package com.mall4j.cloud.payment.controller;

import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.payment.bo.PayInfoResultBO;
import com.mall4j.cloud.payment.model.PayInfo;
import com.mall4j.cloud.payment.service.PayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FrozenWatermelon
 */
@Hidden
@RestController
@RequestMapping("/notice/pay")
public class PayNoticeController {

    @Autowired
    private PayInfoService payInfoService;

    /**
     * 支付异步回调
     */
    @RequestMapping("/order")
    public ResponseEntity<String> submit(Long payId) {
        PayInfo payInfo = payInfoService.getByPayId(payId);
        String[] orderIdStrArr = payInfo.getOrderIds().split(StrUtil.COMMA);
        List<Long> orderIdList = new ArrayList<>();
        for (String s : orderIdStrArr) {
            orderIdList.add(Long.valueOf(s));
        }
        PayInfoResultBO payInfoResult = new PayInfoResultBO();
        payInfoResult.setPayId(payId);
        payInfoResult.setBizPayNo(payInfo.getBizPayNo());
        payInfoResult.setCallbackContent(payInfo.getCallbackContent());
        // 支付成功
        payInfoService.paySuccess(payInfoResult,orderIdList);
        return ResponseEntity.ok("");
    }
}
