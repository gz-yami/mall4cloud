package com.mall4j.cloud.common.xss;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * xss 攻击过滤
 *
 * @author FrozenWatermelon
 */
public class XssWrapper extends HttpServletRequestWrapper {

	/**
	 * Constructs a request object wrapping the given request.
	 * @param request The request to wrap
	 * @throws IllegalArgumentException if the request is null
	 */
	public XssWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 对数组参数进行特殊字符过滤
	 */
	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXss(values[i]);
		}
		return encodedValues;
	}

	/**
	 * 对参数中特殊字符进行过滤
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (StrUtil.isBlank(value)) {
			return value;
		}
		return cleanXss(value);
	}

	/**
	 * 获取attribute,特殊字符过滤
	 */
	@Override
	public Object getAttribute(String name) {
		Object value = super.getAttribute(name);
		if (value instanceof String && StrUtil.isNotBlank((String) value)) {
			return cleanXss((String) value);
		}
		return value;
	}

	/**
	 * 对请求头部进行特殊字符过滤
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (StrUtil.isBlank(value)) {
			return value;
		}
		return cleanXss(value);
	}

	private String cleanXss(String value) {
		return XssUtil.clean(value);
	}

}
