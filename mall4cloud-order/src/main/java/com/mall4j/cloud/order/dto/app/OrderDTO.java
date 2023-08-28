package com.mall4j.cloud.order.dto.app;

import com.mall4j.cloud.api.product.dto.ShopCartItemDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

/**
 * 订单参数
 *
 * @author FrozenWatermelon
 * @date 2020-12-04 11:27:35
 */
public class OrderDTO {

    @Schema(description = "立即购买时提交的商品项,如果该值为空，则说明是从购物车进入，如果该值不为空则说明为立即购买" )
    private ShopCartItemDTO shopCartItem;

    @NotNull(message = "配送类型不能为空")
    @Schema(description = "配送类型3：无需快递" )
    private Integer dvyType;

    @Schema(description = "地址ID，0为默认地址" )
    @NotNull(message = "地址不能为空")
    private Long addrId;

    public Integer getDvyType() {
        return dvyType;
    }

    public void setDvyType(Integer dvyType) {
        this.dvyType = dvyType;
    }

    public ShopCartItemDTO getShopCartItem() {
        return shopCartItem;
    }

    public void setShopCartItem(ShopCartItemDTO shopCartItem) {
        this.shopCartItem = shopCartItem;
    }

    public Long getAddrId() {
        return addrId;
    }

    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "shopCartItem=" + shopCartItem +
                ", dvyType=" + dvyType +
                ", addrId=" + addrId +
                '}';
    }
}
