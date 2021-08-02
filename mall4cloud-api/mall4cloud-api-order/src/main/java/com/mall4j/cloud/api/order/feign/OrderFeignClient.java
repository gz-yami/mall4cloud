package com.mall4j.cloud.api.order.feign;

import com.mall4j.cloud.api.order.bo.EsOrderBO;
import com.mall4j.cloud.api.order.bo.OrderStatusBO;
import com.mall4j.cloud.api.order.bo.OrderSimpleAmountInfoBO;
import com.mall4j.cloud.api.order.vo.OrderAmountVO;
import com.mall4j.cloud.common.feign.FeignInsideAuthConfig;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/11/23
 */
@FeignClient(value = "mall4cloud-order",contextId = "order")
public interface OrderFeignClient {


    /**
     * 如果订单没有被取消的话，获取订单金额，否之会获取失败
     *
     * @param orderIds 订单id列表
     * @return 订单金额
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/ordgetOrdersAmountAndIfNoCanceler")
    ServerResponseEntity<OrderAmountVO> getOrdersAmountAndIfNoCancel(@RequestParam("orderIds") List<Long> orderIds);

    /**
     * 获取订单状态，如果订单状态不存在，则说明订单没有创建
     *
     * @param orderIds 订单id列表
     * @return 订单状态
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/getOrdersStatus")
    ServerResponseEntity<List<OrderStatusBO>> getOrdersStatus(@RequestParam("orderIds") List<Long> orderIds);

    /**
     * 获取订单中的金额信息
     *
     * @param orderIds 订单id列表
     * @return 订单中的金额信息
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/getOrdersSimpleAmountInfo")
    ServerResponseEntity<List<OrderSimpleAmountInfoBO>> getOrdersSimpleAmountInfo(@RequestParam("orderIds") List<Long> orderIds);


    /**
     * 获取订单需要保存到es中的数据
     *
     * @param orderId 订单id
     * @return es中的数据
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/getEsOrder")
    ServerResponseEntity<EsOrderBO> getEsOrder(@RequestParam("orderId")Long orderId);

    /**
     * 支付时更新订单状态
     * @param orderIds 订单id列表
     * @return null
     */
    @GetMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/updateOrderState")
    ServerResponseEntity<Void> updateOrderState(@RequestParam("orderIds") List<Long> orderIds);
}
