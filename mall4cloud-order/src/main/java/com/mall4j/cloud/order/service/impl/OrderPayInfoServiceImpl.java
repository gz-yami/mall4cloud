package com.mall4j.cloud.order.service.impl;

import com.mall4j.cloud.order.model.OrderPayInfo;
import com.mall4j.cloud.order.mapper.OrderPayInfoMapper;
import com.mall4j.cloud.order.service.OrderPayInfoService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单支付记录
 *
 * @author FrozenWatermelon
 * @date 2020-12-04 11:27:35
 */
@Service
public class OrderPayInfoServiceImpl implements OrderPayInfoService {

    @Autowired
    private OrderPayInfoMapper orderPayInfoMapper;

    @Override
    public void save(OrderPayInfo orderPayInfo) {
        orderPayInfoMapper.save(orderPayInfo);
    }

    @Override
    public void update(OrderPayInfo orderPayInfo) {
        orderPayInfoMapper.update(orderPayInfo);
    }

    @Override
    public void deleteById(Long payId) {
        orderPayInfoMapper.deleteById(payId);
    }
}
