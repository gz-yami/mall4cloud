package com.mall4j.cloud.api.product.constant;

/**
 * 等级
 * @author yxf
 * @date 2020/11/20
 */
public enum CategoryLevel {

	/**
	 * 第一级
	 */
	First(0),

	/**
	 * 第二级
	 */
	SECOND(1),

	/**
	 * 第三级
	 */
	THIRD(2)
	;

	private final Integer value;

	public Integer value() {
		return value;
	}

	CategoryLevel(Integer value) {
		this.value = value;
	}

}
