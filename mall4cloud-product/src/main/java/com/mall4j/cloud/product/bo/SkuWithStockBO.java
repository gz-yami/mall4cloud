package com.mall4j.cloud.product.bo;

/**
 * @author FrozenWatermelon
 * @date 2020/12/30
 */
public class SkuWithStockBO {

    private Long id;

    private Long skuId;

    private Long spuId;

    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "SkuWithStockBO{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", spuId=" + spuId +
                ", count=" + count +
                '}';
    }
}
