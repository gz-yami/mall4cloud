package com.mall4j.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 库存信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SkuStockDTO {
    private static final long serialVersionUID = 1L;

    @Schema(description = "库存id" )
    private Long stockId;

    @Schema(description = "SKU ID" )
    private Long skuId;

    @Schema(description = "库存" )
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "StockDTO{" +
				"stockId=" + stockId +
				",skuId=" + skuId +
				",stock=" + stock +
				'}';
	}
}
