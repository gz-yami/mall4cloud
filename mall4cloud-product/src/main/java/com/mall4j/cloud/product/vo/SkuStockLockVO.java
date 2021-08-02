package com.mall4j.cloud.product.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * 库存锁定信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-12-22 16:12:10
 */
public class SkuStockLockVO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("商品id")
    private Long spuId;

    @ApiModelProperty("sku id")
    private Long skuId;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("锁定库存数量")
    private Integer count;

    @ApiModelProperty("状态-1已解锁 0待确定 1已锁定")
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
		return "SkuStockLockVO{" +
				"id=" + id +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				",spuId=" + spuId +
				",skuId=" + skuId +
				",orderId=" + orderId +
				",count=" + count +
				",status=" + status +
				'}';
	}
}
