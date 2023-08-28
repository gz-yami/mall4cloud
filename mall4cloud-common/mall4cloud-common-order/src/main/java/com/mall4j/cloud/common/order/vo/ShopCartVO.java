package com.mall4j.cloud.common.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 购物车VO
 *
 * @author FrozenWatermelon
 * @date 2020-11-20 15:47:32
 */
public class ShopCartVO {

	@Schema(description = "店铺ID" , requiredMode = Schema.RequiredMode.REQUIRED)
	private Long shopId;

	@Schema(description = "店铺名称" , requiredMode = Schema.RequiredMode.REQUIRED)
	private String shopName;

	@Schema(description = "店铺类型1自营店 2普通店" )
	private Integer shopType;

	@Schema(description = "购物车商品信息" )
	private List<ShopCartItemVO> shopCartItem;

	@Schema(description = "商品总值" , requiredMode = Schema.RequiredMode.REQUIRED)
	private Long total;
	@Schema(description = "数量" , requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer totalCount;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

	public List<ShopCartItemVO> getshopCartItem() {
		return shopCartItem;
	}

	public void setshopCartItem(List<ShopCartItemVO> shopCartItem) {
		this.shopCartItem = shopCartItem;
	}

	@Override
	public String toString() {
		return "ShopCartVO{" +
				"shopId=" + shopId +
				", shopName='" + shopName + '\'' +
				", shopType=" + shopType +
				", shopCartItem=" + shopCartItem +
				", total=" + total +
				", totalCount=" + totalCount +
				'}';
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
