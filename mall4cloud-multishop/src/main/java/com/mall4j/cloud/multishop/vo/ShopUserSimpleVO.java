package com.mall4j.cloud.multishop.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author FrozenWatermelon
 * @date 2020/9/2
 */
public class ShopUserSimpleVO {

	/**
	 * 昵称
	 */
	@ApiModelProperty("昵称")
	private String nickName;

	/**
	 * 头像
	 */
	@ApiModelProperty("头像")
	@JsonSerialize(using = ImgJsonSerializer.class)
	private String avatar;

	private Integer isAdmin;

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

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "ShopUserSimpleVO{" +
				"nickName='" + nickName + '\'' +
				", avatar='" + avatar + '\'' +
				", isAdmin=" + isAdmin +
				'}';
	}

}
