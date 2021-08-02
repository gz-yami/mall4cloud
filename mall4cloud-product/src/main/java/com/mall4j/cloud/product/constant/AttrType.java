package com.mall4j.cloud.product.constant;

/**
 * 属性类型
 * @author yxf
 * @date 2020/11/20
 */
public enum AttrType {

	/**
	 * 销售属性
	 */
	SALES(0),

	/**
	 * 基本属性
	 */
	BASIC(1)
	;

	private final Integer value;

	public Integer value() {
		return value;
	}

	AttrType(Integer value) {
		this.value = value;
	}

}
