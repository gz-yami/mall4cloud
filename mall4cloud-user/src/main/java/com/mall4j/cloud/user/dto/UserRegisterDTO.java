package com.mall4j.cloud.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author lhd
 * @date 2020/12/30
 */
@ApiModel(value= "用户注册信息")
public class UserRegisterDTO {

	@NotBlank
	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "头像")
	private String img;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@NotBlank
	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "当账户未绑定时，临时的uid")
	private String tempUid;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTempUid() {
		return tempUid;
	}

	public void setTempUid(String tempUid) {
		this.tempUid = tempUid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserRegisterDTO{" +
				"password='" + password + '\'' +
				", img='" + img + '\'' +
				", nickName='" + nickName + '\'' +
				", userName='" + userName + '\'' +
				", tempUid='" + tempUid + '\'' +
				", userId=" + userId +
				'}';
	}
}
