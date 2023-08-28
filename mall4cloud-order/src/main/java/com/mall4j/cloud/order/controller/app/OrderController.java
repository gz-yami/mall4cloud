package com.mall4j.cloud.order.controller.app;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.api.product.manager.ShopCartAdapter;
import com.mall4j.cloud.api.user.feign.UserAddrFeignClient;
import com.mall4j.cloud.common.cache.constant.CacheNames;
import com.mall4j.cloud.common.cache.constant.OrderCacheNames;
import com.mall4j.cloud.common.cache.util.CacheManagerUtil;
import com.mall4j.cloud.common.cache.util.RedisUtil;
import com.mall4j.cloud.common.constant.Constant;
import com.mall4j.cloud.common.order.vo.*;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.order.bo.SubmitOrderPayAmountInfoBO;
import com.mall4j.cloud.order.dto.app.OrderDTO;
import com.mall4j.cloud.order.model.OrderAddr;
import com.mall4j.cloud.order.service.OrderAddrService;
import com.mall4j.cloud.order.service.OrderItemService;
import com.mall4j.cloud.order.service.OrderService;
import com.mall4j.cloud.order.vo.SubmitOrderPayInfoVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;

/**
 * 订单信息
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 14:13:50
 */
@RestController("appOrderController")
@RequestMapping("/a/order")
@Tag(name = "app-订单信息")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopCartAdapter shopCartAdapter;

    @Autowired
    private CacheManagerUtil cacheManagerUtil;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderAddrService orderAddrService;

    @Autowired
    private UserAddrFeignClient userAddrFeignClient;

    /**
     * 生成订单
     */
    @PostMapping("/confirm")
    @Operation(summary = "结算，生成订单信息" , description = "传入下单所需要的参数进行下单")
    public ServerResponseEntity<ShopCartOrderMergerVO> confirm(@Valid @RequestBody OrderDTO orderParam){
        Long userId = AuthUserContext.get().getUserId();
        // 将要返回给前端的完整的订单信息
        ShopCartOrderMergerVO shopCartOrderMerger = new ShopCartOrderMergerVO();
        shopCartOrderMerger.setDvyType(orderParam.getDvyType());
        ServerResponseEntity<UserAddrVO> addrFeign = userAddrFeignClient.getUserAddrByAddrId(orderParam.getAddrId());
        if (addrFeign.isSuccess()){
            shopCartOrderMerger.setUserAddr(addrFeign.getData());
        }
        ServerResponseEntity<List<ShopCartItemVO>> shopCartItemResponse = shopCartAdapter.getShopCartItems(orderParam.getShopCartItem());
        if (!shopCartItemResponse.isSuccess()) {
            return ServerResponseEntity.transform(shopCartItemResponse);
        }
        List<ShopCartItemVO> shopCartItems = shopCartItemResponse.getData();
        // 购物车
        List<ShopCartVO> shopCarts = shopCartAdapter.conversionShopCart(shopCartItems);
        // 重算一遍订单金额
        recalculateAmountWhenFinishingCalculateShop(shopCartOrderMerger, shopCarts);
        // 防止重复提交
        RedisUtil.STRING_REDIS_TEMPLATE.opsForValue().set(OrderCacheNames.ORDER_CONFIRM_UUID_KEY + CacheNames.UNION + userId, String.valueOf(userId));
        // 保存订单计算结果缓存，省得重新计算并且用户确认的订单金额与提交的一致
        cacheManagerUtil.putCache(OrderCacheNames.ORDER_CONFIRM_KEY,String.valueOf(userId),shopCartOrderMerger);
        return ServerResponseEntity.success(shopCartOrderMerger);
    }

    /**
     * 这里有提交订单的代码
     * 购物车/立即购买  提交订单,根据店铺拆单
     */
    @PostMapping("/submit")
    @Operation(summary = "提交订单，返回支付流水号" , description = "根据传入的参数判断是否为购物车提交订单，同时对购物车进行删除，用户开始进行支付")
    public ServerResponseEntity<List<Long>> submitOrders() {
        Long userId = AuthUserContext.get().getUserId();
        ShopCartOrderMergerVO mergerOrder = cacheManagerUtil.getCache(OrderCacheNames.ORDER_CONFIRM_KEY, String.valueOf(userId));
        // 看看订单有没有过期
        if (mergerOrder == null) {
            return ServerResponseEntity.fail(ResponseEnum.ORDER_EXPIRED);
        }
        // 防止重复提交
        boolean cad = RedisUtil.cad(OrderCacheNames.ORDER_CONFIRM_UUID_KEY + CacheNames.UNION + userId, String.valueOf(userId));
        if (!cad) {
            return ServerResponseEntity.fail(ResponseEnum.REPEAT_ORDER);
        }
        List<Long> orderIds = orderService.submit(userId,mergerOrder);
        return ServerResponseEntity.success(orderIds);
    }


    @GetMapping("/order_pay_info")
    @Operation(summary = "获取订单支付信息" , description = "获取订单支付的商品/地址信息")
    @Parameter(name = "orderIds", description = "订单流水号" , required = true)
    public ServerResponseEntity<SubmitOrderPayInfoVO> getOrderPayInfoByOrderNumber(@RequestParam("orderIds") String orderIds) {
        long[] orderIdList = StrUtil.splitToLong(orderIds, ",");
        List<String> spuNameList = orderItemService.getSpuNameListByOrderIds(orderIdList);
        //获取订单信息
        SubmitOrderPayAmountInfoBO submitOrderPayAmountInfo = orderService.getSubmitOrderPayAmountInfo(orderIdList);
        if (Objects.isNull(submitOrderPayAmountInfo) || Objects.isNull(submitOrderPayAmountInfo.getCreateTime()) ) {
            return ServerResponseEntity.fail(ResponseEnum.ORDER_NOT_EXIST);
        }
        Date endTime =  DateUtil.offsetMinute(submitOrderPayAmountInfo.getCreateTime(), Constant.ORDER_CANCEL_TIME);
        SubmitOrderPayInfoVO orderPayInfoParam = new SubmitOrderPayInfoVO();
        orderPayInfoParam.setSpuNameList(spuNameList);
        orderPayInfoParam.setEndTime(endTime);
        orderPayInfoParam.setTotalFee(submitOrderPayAmountInfo.getTotalFee());
        // 地址
        if (Objects.nonNull(submitOrderPayAmountInfo.getOrderAddrId())) {
            OrderAddr orderAddr = orderAddrService.getByOrderAddrId(submitOrderPayAmountInfo.getOrderAddrId());
            //写入商品名、收货地址/电话
            String addr = orderAddr.getProvince() + orderAddr.getCity() + orderAddr.getArea() + orderAddr.getAddr();
            orderPayInfoParam.setUserAddr(addr);
            orderPayInfoParam.setConsignee(orderAddr.getConsignee());
            orderPayInfoParam.setMobile(orderAddr.getMobile());
        }
        return ServerResponseEntity.success(orderPayInfoParam);
    }

    /**
     * 重算一遍订单金额
     */
    private void recalculateAmountWhenFinishingCalculateShop(ShopCartOrderMergerVO shopCartOrderMerger, List<ShopCartVO> shopCarts) {
        // 所有店铺的订单信息
        List<ShopCartOrderVO> shopCartOrders = new ArrayList<>();
        long total = 0;
        int totalCount = 0;
        // 所有店铺所有的商品item
        for (ShopCartVO shopCart : shopCarts) {
            // 每个店铺的订单信息
            ShopCartOrderVO shopCartOrder = new ShopCartOrderVO();
            shopCartOrder.setShopId(shopCart.getShopId());
            shopCartOrder.setShopName(shopCart.getShopName());
            total += shopCart.getTotal();
            totalCount += shopCart.getTotalCount();
            shopCartOrder.setTotal(shopCart.getTotal());
            shopCartOrder.setTotalCount(shopCart.getTotalCount());
            shopCartOrder.setShopCartItemVO(shopCart.getshopCartItem());
            shopCartOrders.add(shopCartOrder);
        }
        shopCartOrderMerger.setTotal(total);
        shopCartOrderMerger.setTotalCount(totalCount);
        shopCartOrderMerger.setShopCartOrders(shopCartOrders);
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
