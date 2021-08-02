package com.mall4j.cloud.payment.constant;

/**
 * 支付回调类型
 * @author FrozenWatermelon
 */
public enum BackType {

    /**
     * api
     */
    API(0),
    /**
     * 商家端
     */
    SHOP(1),

    /**
     * 平台端
     */
    PLATFORM(2)
    ;

    private Integer num;

    public Integer value() {
        return num;
    }

    BackType(Integer num) {
        this.num = num;
    }

    public static BackType instance(Integer value) {
        BackType[] enums = values();
        for (BackType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
