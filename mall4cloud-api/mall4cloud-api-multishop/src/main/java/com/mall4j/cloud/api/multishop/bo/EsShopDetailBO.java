package com.mall4j.cloud.api.multishop.bo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 店铺详情VO
 *
 * @author FrozenWatermelon
 * @date 2020-11-23 16:24:29
 */
public class EsShopDetailBO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺id" )
    private Long shopId;

    @Schema(description = "店铺类型1自营店 2普通店" )
    private Integer type;

    @Schema(description = "店铺名称" )
    private String shopName;

    @Schema(description = "店铺logo" )
    private String shopLogo;

    @Schema(description = "店铺状态(-1:未开通 0: 停业中 1:营业中)" )
    private Integer shopStatus;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public Integer getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(Integer shopStatus) {
		this.shopStatus = shopStatus;
	}

	@Override
	public String toString() {
		return "EsShopDetailBO{" +
				"shopId=" + shopId +
				", type=" + type +
				", shopName='" + shopName + '\'' +
				", shopLogo='" + shopLogo + '\'' +
				", shopStatus=" + shopStatus +
				'}';
	}
}
