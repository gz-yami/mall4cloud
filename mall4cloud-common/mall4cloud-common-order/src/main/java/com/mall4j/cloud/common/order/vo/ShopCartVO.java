package com.mall4j.cloud.common.order.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 购物车VO
 *
 * @author FrozenWatermelon
 * @date 2020-11-20 15:47:32
 */
public class ShopCartVO {

	@ApiModelProperty(value = "店铺ID", required = true)
	private Long shopId;

	@ApiModelProperty(value = "店铺名称", required = true)
	private String shopName;

	@ApiModelProperty("店铺类型1自营店 2普通店")
	private Integer shopType;

	@ApiModelProperty("购物车商品信息")
	private List<ShopCartItemVO> shopCartItem;

	@ApiModelProperty(value = "商品总值", required = true)
	private Long total;
	@ApiModelProperty(value = "数量", required = true)
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
