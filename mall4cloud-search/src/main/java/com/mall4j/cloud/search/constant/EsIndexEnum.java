package com.mall4j.cloud.search.constant;

/**
 * es当中的index
 * @author FrozenWatermelon
 * @date 2020/11/12
 */
public enum EsIndexEnum {

    /**
     * 商品
     */
    PRODUCT("product"),

    /**
     * 订单
     */
    ORDER("order"),

    ;

    private final String value;

    public String value() {
        return value;
    }

    EsIndexEnum(String value) {
        this.value = value;
    }
}
