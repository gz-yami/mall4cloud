package com.mall4j.cloud.product.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 商品详情信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public class SpuDetailVO extends BaseVO {
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
		return "SpuDetailVO{" +
				"spuId=" + spuId +
				",createTime=" + createTime +
				",updateTime=" + updateTime +
				",detail=" + detail +
				'}';
	}
}
