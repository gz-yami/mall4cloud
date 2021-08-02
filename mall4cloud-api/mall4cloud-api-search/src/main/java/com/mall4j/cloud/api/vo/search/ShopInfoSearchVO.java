package com.mall4j.cloud.api.vo.search;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author FrozenWatermelon
 * @date 2020/11/17
 */
public class ShopInfoSearchVO {

    @ApiModelProperty(value = "店铺名称 搜索华为的时候，可以把华为的旗舰店搜索出来")
    private String shopName;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "店铺logo")
    @JsonSerialize(using =ImgJsonSerializer.class)
    private String shopLogo;

    @ApiModelProperty("店铺类型1自营店 2普通店")
    private Integer type;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ShopInfoSearchVO{" +
                "shopName='" + shopName + '\'' +
                ", shopId=" + shopId +
                ", type=" + type +
                ", shopLogo='" + shopLogo + '\'' +
                '}';
    }
}
