package com.mall4j.cloud.common.exception;

import com.mall4j.cloud.common.response.ResponseEnum;

/**
 * @author FrozenWatermelon
 * @date 2020/7/11
 */
public class Mall4cloudException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Object object;

	private ResponseEnum responseEnum;

	public Mall4cloudException(String msg) {
		super(msg);
	}

	public Mall4cloudException(String msg, Object object) {
		super(msg);
		this.object = object;
	}

	public Mall4cloudException(String msg, Throwable cause) {
		super(msg, cause);
	}


	public Mall4cloudException(ResponseEnum responseEnum) {
		super(responseEnum.getMsg());
		this.responseEnum = responseEnum;
	}

	public Mall4cloudException(ResponseEnum responseEnum, Object object) {
		super(responseEnum.getMsg());
		this.responseEnum = responseEnum;
		this.object = object;
	}


	public Object getObject() {
		return object;
	}

	public ResponseEnum getResponseEnum() {
		return responseEnum;
	}

}
