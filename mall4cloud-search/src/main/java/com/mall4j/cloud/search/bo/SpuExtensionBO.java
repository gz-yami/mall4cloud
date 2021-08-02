package com.mall4j.cloud.search.bo;

import cn.throwx.canal.gule.annotation.CanalModel;
import cn.throwx.canal.gule.common.FieldNamingPolicy;

import java.util.Date;

/**
 * 商品拓展信息
 *
 * @author FrozenWatermelon
 */
@CanalModel(database = "mall4cloud_product", table = "spu_extension", fieldNamingPolicy = FieldNamingPolicy.LOWER_UNDERSCORE)
public class SpuExtensionBO {
    /**
     * 商品扩展信息表id
     */
    private Long spuExtendId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 销量
     */
    private Integer saleNum;

    /**
     * 实际库存
     */
    private Integer actualStock;

    /**
     * 锁定库存
     */
    private Integer lockStock;

    /**
     * 可售卖库存
     */
    private Integer stock;

    public Long getSpuExtendId() {
        return spuExtendId;
    }

    public void setSpuExtendId(Long spuExtendId) {
        this.spuExtendId = spuExtendId;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Integer getActualStock() {
        return actualStock;
    }

    public void setActualStock(Integer actualStock) {
        this.actualStock = actualStock;
    }

    public Integer getLockStock() {
        return lockStock;
    }

    public void setLockStock(Integer lockStock) {
        this.lockStock = lockStock;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SpuExtensionBO{" +
                "spuExtendId=" + spuExtendId +
                ",createTime=" + createTime +
                ",updateTime=" + updateTime +
                ",spuId=" + spuId +
                ",saleNum=" + saleNum +
                ",actualStock=" + actualStock +
                ",lockStock=" + lockStock +
                ",stock=" + stock +
                '}';
    }
}
