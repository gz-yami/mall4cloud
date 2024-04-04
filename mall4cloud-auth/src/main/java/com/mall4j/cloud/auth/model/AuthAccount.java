package com.mall4j.cloud.auth.model;

import com.mall4j.cloud.common.database.annotations.DistributedId;
import com.mall4j.cloud.common.model.BaseModel;
import lombok.Data;
/**
 * 统一账户信息
 *
 * @author FrozenWatermelon
 * @date 2020/07/02
 */
@Data
public class AuthAccount extends BaseModel {

	/**
	 * 全平台用户唯一id
	 */
	@DistributedId("mall4cloud-auth-account")
	private Long uid;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 创建ip
	 */
	private String createIp;

	/**
	 * 状态 1:启用 0:禁用 -1:删除
	 */
	private Integer status;

	/**
	 * 系统类型见SysTypeEnum 0.普通用户系统 1.商家端
	 */
	private Integer sysType;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 所属租户
	 */
	private Long tenantId;

	/**
	 * 是否是管理员
	 */
	private Integer isAdmin;

	@Override
	public String toString() {
		return "AuthAccount{" +
				"uid=" + uid +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", createIp='" + createIp + '\'' +
				", status=" + status +
				", sysType=" + sysType +
				", userId=" + userId +
				", tenantId=" + tenantId +
				", isAdmin=" + isAdmin +
				'}';
	}

}
