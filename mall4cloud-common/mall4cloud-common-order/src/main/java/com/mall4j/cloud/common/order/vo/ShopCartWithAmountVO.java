package com.mall4j.cloud.common.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/12/14
 */
public class ShopCartWithAmountVO {

    @Schema(description = "总额" )
    private Long totalMoney;

    @Schema(description = "总计" )
    private Long finalMoney;

    @Schema(description = "商品数量" )
    private Integer count;

    @Schema(description = "多个店铺的购物车信息" )
    private List<ShopCartVO> shopCarts;

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getFinalMoney() {
        return finalMoney;
    }

    public void setFinalMoney(Long finalMoney) {
        this.finalMoney = finalMoney;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ShopCartVO> getShopCarts() {
        return shopCarts;
    }

    public void setShopCarts(List<ShopCartVO> shopCarts) {
        this.shopCarts = shopCarts;
    }

    @Override
    public String toString() {
        return "ShopCartWithAmountVO{" +
                "totalMoney=" + totalMoney +
                ", finalMoney=" + finalMoney +
                ", count=" + count +
                ", shopCarts=" + shopCarts +
                '}';
    }
}
