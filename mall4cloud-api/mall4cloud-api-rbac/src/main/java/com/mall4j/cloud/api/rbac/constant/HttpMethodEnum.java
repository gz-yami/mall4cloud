package com.mall4j.cloud.api.rbac.constant;

import java.util.Objects;

/**
 * 请求方法 1.GET 2.POST 3.PUT 4.DELETE http方法枚举
 * @author FrozenWatermelon
 * @date 2020/9/3
 */
public enum HttpMethodEnum {

	/**
	 * GET
	 */
	GET(1, "GET"),

	/**
	 * POST
	 */
	POST(2, "POST"),

	/**
	 * PUT
	 */
	PUT(3, "PUT"),

	/**
	 * DELETE
	 */
	DELETE(4, "DELETE"),;

	private final Integer value;

	private final String method;

	public Integer value() {
		return value;
	}

	public String method() {
		return this.method;
	}

	HttpMethodEnum(Integer value, String method) {
		this.value = value;
		this.method = method;
	}

	public static HttpMethodEnum valueOf(Integer value) {
		for (HttpMethodEnum httpMethodEnum : values()) {
			if (Objects.equals(httpMethodEnum.value(), value)) {
				return httpMethodEnum;
			}
		}
		return null;
	}

}
