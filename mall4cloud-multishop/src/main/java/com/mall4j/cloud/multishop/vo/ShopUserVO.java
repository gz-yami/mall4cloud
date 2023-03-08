package com.mall4j.cloud.multishop.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/9/2
 */
public class ShopUserVO {

	/**
	 * shopUserId
	 */
	@Schema(description = "店铺用户id" )
	private Long shopUserId;

	/**
	 * 昵称
	 */
	@Schema(description = "昵称" )
	private String nickName;

	/**
	 * 员工编号
	 */
	@Schema(description = "员工编号" )
	private String code;

	/**
	 * 联系方式
	 */
	@Schema(description = "联系方式" )
	private String phoneNum;

	@Schema(description = "是否已经有账号了" )
	private Integer hasAccount;

	@Schema(description = "店铺id" )
	private Long shopId;

	@Schema(description = "角色id列表" )
	private List<Long> roleIds;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Long getShopUserId() {
		return shopUserId;
	}

	public void setShopUserId(Long shopUserId) {
		this.shopUserId = shopUserId;
	}

	public Integer getHasAccount() {
		return hasAccount;
	}

	public void setHasAccount(Integer hasAccount) {
		this.hasAccount = hasAccount;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Override
	public String toString() {
		return "ShopUserVO{" +
				"shopUserId=" + shopUserId +
				", nickName='" + nickName + '\'' +
				", code='" + code + '\'' +
				", phoneNum='" + phoneNum + '\'' +
				", hasAccount=" + hasAccount +
				", shopId=" + shopId +
				", roleIds=" + roleIds +
				'}';
	}
}
