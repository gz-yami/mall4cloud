package com.mall4j.cloud.search.constant;

import java.util.Objects;

/**
 * es当中的商品排序
 * @author FrozenWatermelon
 * @date 2020/11/17
 */
public enum EsProductSortEnum {

    /**
     * 新品(创建时间)
     */
    CREATE_TIME(1, "createTime", Boolean.TRUE),

    /**
     * 销量倒序
     */
    SALE_NUM_DESC(2, "saleNum", Boolean.FALSE),

    /**
     * 销量正序
     */
    SALE_NUM_ASC(3, "saleNum", Boolean.TRUE),

    /**
     * 商品价格倒序
     */
    PRICE_DESC(4, "priceFee", Boolean.FALSE),

    /**
     * 商品价格正序
     */
    PRICE_ASC(5, "priceFee", Boolean.TRUE),

    /**
     * 分组排序
     */
    SPU_TAG(6, "tags.seq", null)
    ;

    private final Integer value;

    private final String sort;

    private final Boolean order;

    public Integer value() {
        return value;
    }

    EsProductSortEnum(Integer value,String sort,Boolean order) {
        this.value = value;
        this.sort = sort;
        this.order = order;
    }

    public String sort() {
        return sort;
    }

    public Boolean order() {
        return order;
    }

    public static Boolean isAsc(EsProductSortEnum esProductSortEnum) {
        if (Objects.equals(esProductSortEnum.order(), Boolean.TRUE)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    public static Boolean isDesc(EsProductSortEnum esProductSortEnum) {
        if (Objects.equals(esProductSortEnum.order(), Boolean.FALSE)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
