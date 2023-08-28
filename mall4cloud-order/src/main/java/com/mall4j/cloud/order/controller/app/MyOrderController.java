package com.mall4j.cloud.order.controller.app;

import com.mall4j.cloud.api.feign.SearchOrderFeignClient;
import com.mall4j.cloud.api.order.constant.OrderStatus;
import com.mall4j.cloud.api.vo.EsPageVO;
import com.mall4j.cloud.api.vo.search.EsOrderVO;
import com.mall4j.cloud.common.dto.OrderSearchDTO;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.order.model.Order;
import com.mall4j.cloud.order.model.OrderAddr;
import com.mall4j.cloud.order.model.OrderItem;
import com.mall4j.cloud.order.service.*;
import com.mall4j.cloud.order.service.OrderAddrService;
import com.mall4j.cloud.order.service.OrderItemService;
import com.mall4j.cloud.order.service.OrderService;
import com.mall4j.cloud.order.vo.OrderAddrVO;
import com.mall4j.cloud.order.vo.OrderCountVO;
import com.mall4j.cloud.order.vo.OrderItemVO;
import com.mall4j.cloud.order.vo.OrderShopVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 我的订单
 *
 * @author FrozenWatermelon
 */
@RestController
@RequestMapping("/p/myOrder")
@Tag(name = "app-我的订单接口")
public class MyOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private SearchOrderFeignClient searchOrderFeignClient;
    @Autowired
    private OrderAddrService orderAddrService;


    /**
     * 订单详情信息接口
     */
    @GetMapping("/order_detail")
    @Operation(summary = "订单详情信息" , description = "根据订单号获取订单详情信息")
    @Parameter(name = "orderId", description = "订单号" , required = true)
    public ServerResponseEntity<OrderShopVO> orderDetail(@RequestParam(value = "orderId") Long orderId) {
        Long userId = AuthUserContext.get().getUserId();
        OrderShopVO orderShopDto = new OrderShopVO();
        Order order = orderService.getOrderByOrderIdAndUserId(orderId, userId);
        OrderAddr orderAddr = orderAddrService.getByOrderAddrId(order.getOrderAddrId());
        List<OrderItem> orderItems = orderItemService.listOrderItemsByOrderId(orderId);
        orderShopDto.setShopId(order.getShopId());
        orderShopDto.setDeliveryType(order.getDeliveryType());
        orderShopDto.setShopName(order.getShopName());
        orderShopDto.setCreateTime(order.getCreateTime());
        orderShopDto.setStatus(order.getStatus());
        orderShopDto.setOrderAddr(BeanUtil.map(orderAddr, OrderAddrVO.class));
        // 付款时间
        orderShopDto.setPayTime(order.getPayTime());
        // 发货时间
        orderShopDto.setDeliveryTime(order.getDeliveryTime());
        // 完成时间
        orderShopDto.setFinallyTime(order.getFinallyTime());
        // 取消时间
        orderShopDto.setCancelTime(order.getCancelTime());
        // 更新时间
        orderShopDto.setUpdateTime(order.getUpdateTime());
        orderShopDto.setOrderItems(BeanUtil.mapAsList(orderItems, OrderItemVO.class));
        orderShopDto.setTotal(order.getTotal());
        orderShopDto.setTotalNum(order.getAllCount());

        return ServerResponseEntity.success(orderShopDto);
    }

    @GetMapping("/order_count")
    @Operation(summary = "计算各个订单数量" , description = "根据订单状态计算各个订单数量")
    public ServerResponseEntity<OrderCountVO> orderCount() {
        Long userId = AuthUserContext.get().getUserId();
        OrderCountVO orderCount = orderService.countNumberOfStatus(userId);
        return ServerResponseEntity.success(orderCount);
    }

    /**
     * 分页获取
     */
    @GetMapping("/search_order")
    @Operation(summary = "订单列表信息查询" , description = "根据订单编号或者订单中商品名称搜索")
    public ServerResponseEntity<EsPageVO<EsOrderVO>> searchOrder(OrderSearchDTO orderSearchDTO) {
        Long userId = AuthUserContext.get().getUserId();
        orderSearchDTO.setUserId(userId);
        return searchOrderFeignClient.getOrderPage(orderSearchDTO);
    }

    /**
     * 取消订单
     */
    @PutMapping("/cancel/{orderId}")
    @Operation(summary = "根据订单号取消订单" , description = "根据订单号取消订单")
    @Parameter(name = "orderId", description = "订单号" , required = true)
    public ServerResponseEntity<String> cancel(@PathVariable("orderId") Long orderId) {
        Long userId = AuthUserContext.get().getUserId();
        Order order = orderService.getOrderByOrderIdAndUserId(orderId, userId);
        if (!Objects.equals(order.getStatus(), OrderStatus.UNPAY.value())) {
            // 订单已支付，无法取消订单
            return ServerResponseEntity.fail(ResponseEnum.ORDER_PAYED);
        }
        // 如果订单未支付的话，将订单设为取消状态
        orderService.cancelOrderAndGetCancelOrderIds(Collections.singletonList(order.getOrderId()));
        return ServerResponseEntity.success();
    }


    /**
     * 确认收货
     */
    @PutMapping("/receipt/{orderId}")
    @Operation(summary = "根据订单号确认收货" , description = "根据订单号确认收货")
    public ServerResponseEntity<String> receipt(@PathVariable("orderId") Long orderId) {
        Long userId = AuthUserContext.get().getUserId();
        Order order = orderService.getOrderByOrderIdAndUserId(orderId, userId);
        if (!Objects.equals(order.getStatus(), OrderStatus.CONSIGNMENT.value())) {
            // 订单未发货，无法确认收货
            return ServerResponseEntity.fail(ResponseEnum.ORDER_NO_DELIVERY);
        }
        List<OrderItem> orderItems = orderItemService.listOrderItemsByOrderId(orderId);
        order.setOrderItems(orderItems);
        // 确认收货
        orderService.receiptOrder(order.getOrderId());
        return ServerResponseEntity.success();
    }

    /**
     * 删除订单
     */
    @DeleteMapping("/{orderId}")
    @Operation(summary = "根据订单号删除订单" , description = "根据订单号删除订单")
    @Parameter(name = "orderId", description = "订单号" , required = true)
    public ServerResponseEntity<String> delete(@PathVariable("orderId") Long orderId) {
        Long userId = AuthUserContext.get().getUserId();
        Order order = orderService.getOrderByOrderIdAndUserId(orderId, userId);
        if (!Objects.equals(order.getStatus(), OrderStatus.SUCCESS.value()) && !Objects.equals(order.getStatus(), OrderStatus.CLOSE.value()) ) {
            // 订单未完成或未关闭，无法删除订单
            return ServerResponseEntity.fail(ResponseEnum.ORDER_NOT_FINISH_OR_CLOSE);
        }
        // 删除订单
        orderService.deleteOrder(order.getOrderId());
        return ServerResponseEntity.success();
    }
}
