package com.mall4j.cloud.platform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author lhd
 * @date 2020/9/2
 */
public class SysUserVO {

	/**
	 * sysUserId
	 */
	@Schema(description = "平台用户id" )
	private Long sysUserId;

	/**
	 * 昵称
	 */
	@Schema(description = "昵称" )
	private String nickName;

	/**
	 * 头像
	 */
	@Schema(description = "头像" )
	@JsonSerialize(using = ImgJsonSerializer.class)
	private String avatar;

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

	@Schema(description = "平台id" )
	private Long shopId;

	@Schema(description = "角色id列表" )
	private List<Long> roleIds;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public Long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
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
		return "SysUserVO{" +
				"sysUserId=" + sysUserId +
				", nickName='" + nickName + '\'' +
				", avatar='" + avatar + '\'' +
				", code='" + code + '\'' +
				", phoneNum='" + phoneNum + '\'' +
				", hasAccount=" + hasAccount +
				", shopId=" + shopId +
				", roleIds=" + roleIds +
				'}';
	}
}
