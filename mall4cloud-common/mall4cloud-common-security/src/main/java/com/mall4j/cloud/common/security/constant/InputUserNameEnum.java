package com.mall4j.cloud.common.security.constant;

/**
 * 输入的用户名类型枚举 1.username 2.mobile 3.email
 * @author FrozenWatermelon
 * @date 2020/7/3
 */
public enum InputUserNameEnum {

	/**
	 * 用户名
	 */
	USERNAME(1),

	/**
	 * 手机号
	 */
	PHONE(2),

	/**
	 * 邮箱
	 */
	EMAIL(3),;

	private final Integer value;

	public Integer value() {
		return value;
	}

	InputUserNameEnum(Integer value) {
		this.value = value;
	}

}
