package com.mall4j.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 商品详情信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SpuDetailDTO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品id" )
    private Long spuId;

    @Schema(description = "商品详情" )
    private String detail;

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "SpuDetailDTO{" +
				"spuId=" + spuId +
				",detail=" + detail +
				'}';
	}
}
