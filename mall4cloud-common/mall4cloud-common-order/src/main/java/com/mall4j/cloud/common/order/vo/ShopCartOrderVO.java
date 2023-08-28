package com.mall4j.cloud.common.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

/**
 * 单个店铺的订单信息
 * @author FrozenWatermelon
 */
public class ShopCartOrderVO implements Serializable{

    @Schema(description = "店铺id" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Long shopId;

    @Schema(description = "店铺名称" , requiredMode = Schema.RequiredMode.REQUIRED)
    private String shopName;

    @Schema(description = "商品总值" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Long total;

    @Schema(description = "购物车商品" , requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ShopCartItemVO> shopCartItemVO;

    @Schema(description = "商品总数" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer totalCount;




    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<ShopCartItemVO> getShopCartItemVO() {
        return shopCartItemVO;
    }

    public void setShopCartItemVO(List<ShopCartItemVO> shopCartItemVO) {
        this.shopCartItemVO = shopCartItemVO;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }


    @Override
    public String toString() {
        return "ShopCartOrderVO{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", total=" + total +
                ", shopCartItemVO=" + shopCartItemVO +
                ", totalCount=" + totalCount +
                '}';
    }
}
