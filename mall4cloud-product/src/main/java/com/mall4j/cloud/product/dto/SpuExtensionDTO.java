package com.mall4j.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * DTO
 *
 * @author FrozenWatermelon
 * @date 2020-11-11 13:49:06
 */
public class SpuExtensionDTO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品扩展信息表id" )
    private Long spuExtendId;

    @Schema(description = "创建时间" )
    private Date createTime;

    @Schema(description = "更新时间" )
    private Date updateTime;

    @Schema(description = "商品id" )
    private Long spuId;

    @Schema(description = "销量" )
    private Integer saleNum;

    @Schema(description = "实际库存" )
    private Integer actualStock;

    @Schema(description = "锁定库存" )
    private Integer lockStock;

    @Schema(description = "可售卖库存" )
    private Integer stock;

	public Long getSpuExtendId() {
		return spuExtendId;
	}

	public void setSpuExtendId(Long spuExtendId) {
		this.spuExtendId = spuExtendId;
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

	@Override
	public String toString() {
		return "SpuExtensionVO{" +
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
