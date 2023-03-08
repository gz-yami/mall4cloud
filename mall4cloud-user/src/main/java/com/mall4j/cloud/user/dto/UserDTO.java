package com.mall4j.cloud.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

/**
 * 用户表DTO
 *
 * @author YXF
 * @date 2020-12-08 11:18:04
 */
public class UserDTO{
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID" )
    private Long userId;

    @Schema(description = "用户昵称" )
    private String nickName;

    @Schema(description = "头像图片路径" )
    private String pic;

    @Schema(description = "状态 1 正常 0 无效" )
    private Integer status;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"userId=" + userId +
				",nickName=" + nickName +
				",pic=" + pic +
				",status=" + status +
				'}';
	}
}
