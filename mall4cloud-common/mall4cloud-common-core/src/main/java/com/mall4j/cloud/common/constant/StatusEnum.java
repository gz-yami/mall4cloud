package com.mall4j.cloud.common.constant;

/**
 * 等级
 * @author yxf
 * @date 2020/11/20
 */
public enum StatusEnum {

	/**
	 * 删除 (逻辑删除)
	 */
	DELETE(-1),

	/**
	 * 禁用/过期/下架
	 */
	DISABLE(0),

	/**
	 * 启用/未过期/上架
	 */
	ENABLE(1),

	/**
	 * 违规下架
	 */
	OFFLINE(2),

	/**
	 * 等待审核
	 */
	WAIT_AUDIT(3)
	;

	private final Integer value;

	public Integer value() {
		return value;
	}

	StatusEnum(Integer value) {
		this.value = value;
	}

	public static Boolean offlineStatus (Integer value) {
		StatusEnum[] enums = values();
		for (StatusEnum statusEnum : enums) {
			if (statusEnum.value().equals(value)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}
