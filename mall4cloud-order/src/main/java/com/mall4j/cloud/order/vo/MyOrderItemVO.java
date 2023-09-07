package com.mall4j.cloud.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author FrozenWatermelon
 */
@Schema(description = "我的订单-订单项")
public class MyOrderItemVO {

    @Schema(description = "商品图片" , requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

    @Schema(description = "商品名称" , requiredMode = Schema.RequiredMode.REQUIRED)
    private String spuName;

    @Schema(description = "订单号" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orderId;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }
    @Override
    public String toString() {
        return "MyOrderItemVO{" +
                "pic='" + pic + '\'' +
                ", spuName='" + spuName + '\'' +
                ", orderId=" + orderId +
                ", count=" + count +
                ", price=" + price +
                ", skuId=" + skuId +
                ", skuName='" + skuName + '\'' +
                ", orderItemId=" + orderItemId +
                ", spuId=" + spuId +
                '}';
    }
}
