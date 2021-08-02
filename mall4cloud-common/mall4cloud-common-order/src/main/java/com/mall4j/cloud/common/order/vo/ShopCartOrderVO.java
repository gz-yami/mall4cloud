package com.mall4j.cloud.common.order.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 单个店铺的订单信息
 * @author FrozenWatermelon
 */
public class ShopCartOrderVO implements Serializable{

    @ApiModelProperty(value = "店铺id", required = true)
    private Long shopId;

    @ApiModelProperty(value = "店铺名称", required = true)
    private String shopName;

    @ApiModelProperty(value = "商品总值", required = true)
    private Long total;

    @ApiModelProperty(value = "购物车商品", required = true)
    private List<ShopCartItemVO> shopCartItemVO;

    @ApiModelProperty(value = "商品总数", required = true)
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
