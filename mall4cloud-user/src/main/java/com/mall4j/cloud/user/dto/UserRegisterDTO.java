package com.mall4j.cloud.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

/**
 * @author lhd
 * @date 2020/12/30
 */
@Schema(description = "用户注册信息")
public class UserRegisterDTO {

	@NotBlank
	@Schema(description = "密码" )
	private String password;

	@Schema(description = "头像" )
	private String img;

	@Schema(description = "昵称" )
	private String nickName;

	@NotBlank
	@Schema(description = "用户名" )
	private String userName;

	@Schema(description = "当账户未绑定时，临时的uid" )
	private String tempUid;

	@Schema(description = "用户id" )
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
