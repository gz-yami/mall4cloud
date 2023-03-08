package com.mall4j.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 库存锁定信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-12-22 16:12:10
 */
public class SkuStockLockDTO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "id" )
    private Long id;

    @Schema(description = "商品id" )
    private Long spuId;

    @Schema(description = "sku id" )
    private Long skuId;

    @Schema(description = "订单id" )
    private Long orderId;

    @Schema(description = "锁定库存数量" )
    private Integer count;

    @Schema(description = "状态-1已解锁 0待确定 1已锁定" )
    private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SkuStockLockDTO{" +
				"id=" + id +
				",spuId=" + spuId +
				",skuId=" + skuId +
				",orderId=" + orderId +
				",count=" + count +
				",status=" + status +
				'}';
	}
}
