package com.mall4j.cloud.common.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 多个店铺订单合并在一起的合并类
 * "/confirm" 使用
 * @author FrozenWatermelon
 */
public class ShopCartOrderMergerVO {

    @Schema(description = "商品总值" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Long total;

    @Schema(description = "商品总数" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer totalCount;

    @Schema(description = "配送类型 ：无需快递" )
    private Integer dvyType;

    @Schema(description = "过滤掉的商品项" , requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ShopCartItemVO> filterShopItems;

    @Schema(description = "每个店铺的订单信息" , requiredMode = Schema.RequiredMode.REQUIRED)
    List<ShopCartOrderVO> shopCartOrders;

    @Schema(description = "用户地址" )
    private UserAddrVO userAddr;


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getDvyType() {
        return dvyType;
    }

    public void setDvyType(Integer dvyType) {
        this.dvyType = dvyType;
    }

    public List<ShopCartOrderVO> getShopCartOrders() {
        return shopCartOrders;
    }

    public void setShopCartOrders(List<ShopCartOrderVO> shopCartOrders) {
        this.shopCartOrders = shopCartOrders;
    }

    public List<ShopCartItemVO> getFilterShopItems() {
        return filterShopItems;
    }

    public void setFilterShopItems(List<ShopCartItemVO> filterShopItems) {
        this.filterShopItems = filterShopItems;
    }

    public UserAddrVO getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(UserAddrVO userAddr) {
        this.userAddr = userAddr;
    }

    @Override
    public String toString() {
        return "ShopCartOrderMergerVO{" +
                "total=" + total +
                ", totalCount=" + totalCount +
                ", dvyType=" + dvyType +
                ", filterShopItems=" + filterShopItems +
                ", shopCartOrders=" + shopCartOrders +
                ", userAddr=" + userAddr +
                '}';
    }
}
