package com.mall4j.cloud.product.model;

import com.mall4j.cloud.common.model.BaseModel;

import java.io.Serializable;

/**
 * 库存信息
 *
 * @author FrozenWatermelon
 * @date 2020-11-11 13:49:06
 */
public class SkuStock extends BaseModel implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 库存id
	 */
	private Long stockId;

	/**
	 * SKU ID
	 */
	private Long skuId;

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

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
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
		return "SkuStock{" +
				"stockId=" + stockId +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				",skuId=" + skuId +
				",actualStock=" + actualStock +
				",lockStock=" + lockStock +
				",stock=" + stock +
				'}';
	}
}
