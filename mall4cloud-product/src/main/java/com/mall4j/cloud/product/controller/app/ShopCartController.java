package com.mall4j.cloud.product.controller.app;

import cn.hutool.core.collection.CollectionUtil;
import com.mall4j.cloud.api.product.manager.ShopCartAdapter;
import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.common.order.vo.ShopCartVO;
import com.mall4j.cloud.common.order.vo.ShopCartWithAmountVO;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.product.dto.shopcart.ChangeShopCartItemDTO;
import com.mall4j.cloud.product.dto.shopcart.CheckShopCartItemDTO;
import com.mall4j.cloud.product.model.ShopCartItem;
import com.mall4j.cloud.product.service.ShopCartService;
import com.mall4j.cloud.product.service.SkuService;
import com.mall4j.cloud.product.service.SpuService;
import com.mall4j.cloud.api.product.vo.SkuVO;
import com.mall4j.cloud.api.product.vo.SpuVO;
import com.mall4j.cloud.common.order.vo.ShopCartItemVO;
import com.mall4j.cloud.product.vo.ShopCartAmountVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.mall4j.cloud.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 购物车
 *
 * @author FrozenWatermelon
 * @date 2020-11-20 15:47:32
 */
@RestController
@RequestMapping("/a/shop_cart")
@Tag(name = "app-购物车")
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;



    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ShopCartAdapter shopCartAdapter;

    /**
     * 获取用户购物车信息
     *
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "获取用户购物车信息" , description = "获取用户购物车信息")
    public ServerResponseEntity<ShopCartWithAmountVO> info() {
        // 拿到购物车的所有item
        List<ShopCartItemVO> shopCartItems = shopCartService.getShopCartItems();
        List<ShopCartVO> shopCarts = shopCartAdapter.conversionShopCart(shopCartItems);
        ShopCartWithAmountVO shopCartWithAmountVO = new ShopCartWithAmountVO();
        long total = 0L;
        for (ShopCartItemVO shopCartItem : shopCartItems) {
            if (Objects.equals(shopCartItem.getIsChecked(),1)){
                total += shopCartItem.getTotalAmount();
            }
        }
        shopCartWithAmountVO.setShopCarts(shopCarts);
        shopCartWithAmountVO.setTotalMoney(total);
        return ServerResponseEntity.success(shopCartWithAmountVO);
    }

    /**
     * 获取用户购物车信息
     *
     * @return
     */
    @GetMapping("/amount_info")
    @Operation(summary = "获取用户购物车金额信息" , description = "获取用户购物车金额信息")
    public ServerResponseEntity<ShopCartAmountVO> amountInfo() {
        // 拿到购物车的所有item
        List<ShopCartItemVO> shopCartItems = shopCartService.getShopCartItems();
        List<ShopCartVO> shopCarts = shopCartAdapter.conversionShopCart(shopCartItems);
        ShopCartWithAmountVO shopCartWithAmountVO = new ShopCartWithAmountVO();
        shopCartWithAmountVO.setShopCarts(shopCarts);

        return ServerResponseEntity.success(BeanUtil.map(shopCartWithAmountVO, ShopCartAmountVO.class));
    }



    @DeleteMapping("/delete_item")
    @Operation(summary = "删除用户购物车物品" , description = "通过购物车id删除用户购物车物品")
    public ServerResponseEntity<Void> deleteItem(@RequestBody List<Long> shopCartItemIds) {
        Long userId = AuthUserContext.get().getUserId();
        shopCartService.deleteShopCartItemsByShopCartItemIds(userId,shopCartItemIds);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delete_all")
    @Operation(summary = "清空用户购物车所有物品" , description = "清空用户购物车所有物品")
    public ServerResponseEntity<String> deleteAll() {
        Long userId = AuthUserContext.get().getUserId();
        shopCartService.deleteAllShopCartItems(userId);
        // 删除成功
        return ServerResponseEntity.success();
    }

    @PostMapping("/check_items")
    @Operation(summary = "", description = "")
    public ServerResponseEntity<Void> checkItems(@Valid @RequestBody List<CheckShopCartItemDTO> params) {
        if(CollectionUtil.isEmpty(params)) {
            return ServerResponseEntity.success();
        }
        Long userId = AuthUserContext.get().getUserId();
        shopCartService.checkShopCartItems(userId,params);
        return ServerResponseEntity.success();
    }


    @PostMapping("/change_item")
    @Operation(summary = "添加、修改用户购物车物品", description = "通过商品id(prodId)、skuId、店铺Id(shopId),添加/修改用户购物车商品，并传入改变的商品个数(count)，" +
            "当count为正值时，增加商品数量，当count为负值时，将减去商品的数量，当最终count值小于0时，会将商品从购物车里面删除")
    public ServerResponseEntity<Void> addItem(@Valid @RequestBody ChangeShopCartItemDTO param) {

        // 不用校验库存是否充足！！！
        Long userId = AuthUserContext.get().getUserId();
        List<ShopCartItemVO> shopCartItems = shopCartService.getShopCartItems();

        SpuVO spu = spuService.getBySpuId(param.getSpuId());
        SkuVO sku = skuService.getSkuBySkuId(param.getSkuId());


        // 当商品状态不正常时，不能添加到购物车
        if (Objects.isNull(spu) || Objects.isNull(sku) || !Objects.equals(spu.getStatus(), StatusEnum.ENABLE.value()) || !Objects.equals(sku.getStatus(), StatusEnum.ENABLE.value()) || !Objects.equals(sku.getSpuId(),spu.getSpuId())) {
            // 当返回商品不存在时，前端应该将商品从购物车界面移除
            return ServerResponseEntity.fail(ResponseEnum.SPU_NOT_EXIST);
        }
        // 保存shopId，不要让前端传过来
        param.setShopId(spu.getShopId());


        Integer oldCount = 0;
        Long oldShopCartItemId = null;
        for (ShopCartItemVO shopCartItemVo : shopCartItems) {
            if (Objects.equals(param.getSkuId(), shopCartItemVo.getSkuId())) {
                // 旧数量
                oldCount = shopCartItemVo.getCount();
                oldShopCartItemId = shopCartItemVo.getCartItemId();
                ShopCartItem shopCartItem = new ShopCartItem();
                shopCartItem.setUserId(userId);
                shopCartItem.setCount(param.getCount() + shopCartItemVo.getCount());
                shopCartItem.setIsChecked(shopCartItemVo.getIsChecked());
                shopCartItem.setCartItemId(shopCartItemVo.getCartItemId());
                // 如果有个旧的sku，就说明是在切换sku
                if (Objects.nonNull(param.getOldSkuId())) {
                    continue;
                }
                // 防止购物车变成负数，从购物车删除
                if (shopCartItem.getCount() <= 0) {
                    shopCartService.deleteShopCartItemsByShopCartItemIds(userId,Collections.singletonList(shopCartItem.getCartItemId()));
                    return ServerResponseEntity.success();
                }
                shopCartService.updateShopCartItem(userId,shopCartItem);
                return ServerResponseEntity.success();
            }
        }

        if (Objects.nonNull(param.getOldSkuId())) {
            for (ShopCartItemVO oldShopCartItem : shopCartItems) {
                // 旧sku
                if (Objects.equals(param.getOldSkuId(), oldShopCartItem.getSkuId())) {
                    ShopCartItem shopCartItem = new ShopCartItem();
                    shopCartItem.setUserId(userId);
                    shopCartItem.setCartItemId(oldShopCartItem.getCartItemId());
                    // 如果以前就存在这个商品，还要把以前的商品数量累加
                    shopCartItem.setCount(param.getCount() + oldCount);
                    shopCartItem.setSkuId(param.getSkuId());

                    if (oldShopCartItemId != null) {
                        // 删除旧的购物项
                        shopCartService.deleteShopCartItemsByShopCartItemIds(userId,Collections.singletonList(oldShopCartItemId));
                    }
                    // 更新购物车
                    shopCartService.updateShopCartItem(userId,shopCartItem);
                    return ServerResponseEntity.success();
                }
            }
        }

        // 所有都正常时
        shopCartService.addShopCartItem(userId,param,sku.getPriceFee());
        // 添加成功
        return ServerResponseEntity.success();
    }

    @GetMapping("/prod_count")
    @Operation(summary = "获取购物车商品数量" , description = "获取购物车商品数量")
    public ServerResponseEntity<Integer> prodCount() {
        return ServerResponseEntity.success(shopCartService.getShopCartItemCount(AuthUserContext.get().getUserId()));
    }

    @GetMapping("/expiry_prod_list")
    @Operation(summary = "获取购物车失效商品信息" , description = "获取购物车失效商品列表")
    public ServerResponseEntity<List<ShopCartItemVO>> expiryProdList() {
        return ServerResponseEntity.success(shopCartService.getShopCartExpiryItems());
    }

}
