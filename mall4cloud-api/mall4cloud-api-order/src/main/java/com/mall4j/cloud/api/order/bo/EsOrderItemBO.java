package com.mall4j.cloud.api.order.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class EsOrderItemBO {
    @Schema(description = "商品图片" , requiredMode = Schema.RequiredMode.REQUIRED)
    private String pic;

    @Schema(description = "商品名称" , requiredMode = Schema.RequiredMode.REQUIRED)
    private String spuName;

    @Schema(description = "商品数量" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer count;

    @Schema(description = "商品价格" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Long price;

    @Schema(description = "skuId" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Long skuId;

    @Schema(description = "skuName" , requiredMode = Schema.RequiredMode.REQUIRED)
    private String skuName;

    @Schema(description = "订单项id" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderItemId;

    @Schema(description = "商品id" , requiredMode = Schema.RequiredMode.REQUIRED)
    private Long spuId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 单个orderItem的配送类型  ：无需快递
     */
    private Integer deliveryType;

    /**
     * 加入购物车时间
     */
    private Date shopCartTime;

    /**
     * 商品总金额
     */
    private Long spuTotalAmount;

    @Override
    public String toString() {
        return "EsOrderItemBO{" +
                "pic='" + pic + '\'' +
                ", spuName='" + spuName + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", skuId=" + skuId +
                ", skuName='" + skuName + '\'' +
                ", orderItemId=" + orderItemId +
                ", spuId=" + spuId +
                ", shopId=" + shopId +
                ", userId=" + userId +
                ", deliveryType=" + deliveryType +
                ", shopCartTime=" + shopCartTime +
                ", spuTotalAmount=" + spuTotalAmount +
                '}';
    }
}
