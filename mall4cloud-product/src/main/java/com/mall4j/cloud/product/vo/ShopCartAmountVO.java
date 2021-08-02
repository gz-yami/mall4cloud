package com.mall4j.cloud.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author FrozenWatermelon
 */
@ApiModel("购物车合计")
public class ShopCartAmountVO {

    @ApiModelProperty("总额")
    private Long totalMoney;

    @ApiModelProperty("总计")
    private Long finalMoney;

    @ApiModelProperty("减额")
    private Long subtractMoney;

    @ApiModelProperty("商品数量")
    private Integer count;

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

    public Long getSubtractMoney() {
        return subtractMoney;
    }

    public void setSubtractMoney(Long subtractMoney) {
        this.subtractMoney = subtractMoney;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ShopCartAmountVO{" +
                "totalMoney=" + totalMoney +
                ", finalMoney=" + finalMoney +
                ", subtractMoney=" + subtractMoney +
                ", count=" + count +
                '}';
    }
}
