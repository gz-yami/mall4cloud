package com.mall4j.cloud.common.util;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

/**
 * 正则表达式工具
 *
 * @author FrozenWatermelon
 */
public class PrincipalUtil {

	/**
	 * 以1开头，后面跟10位数
	 */
	public static final String MOBILE_REGEXP = "1[0-9]{10}";

	/**
	 * 1. 用户名不能为纯数字 2. 由数字字母下划线 4-16位组成
	 */
	public static final String USER_NAME_REGEXP = "(?!\\d+$)([a-zA-Z0-9_]{4,16})";

	/**
	 * 字段名，数字字母下划线
	 */
	public static final String FIELD_REGEXP = "([a-zA-Z0-9_]+)";

	/**
	 * 由简单的字母数字拼接而成的字符串 不含有下划线，大写字母
	 */
	public static final String SIMPLE_CHAR_REGEXP = "([a-z0-9]+)";

	/**
	 * 邮箱正则
	 */
	public static final String EMAIL_REGEXP = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

	/**
	 * http协议正则
	 */
	public static final String HTTP_PROTOCOL_REGEXP = "^((http[s]{0,1})://)";



	/**
	 * 是否是手机号
	 * @param value 输入值
	 * @return 匹配结果
	 */
	public static boolean isMobile(String value) {
		return isMatching(MOBILE_REGEXP, value);
	}

	/**
	 * 是否是用户名
	 * @param value 输入值
	 * @return 匹配结果
	 */
	public static boolean isUserName(String value) {
		return isMatching(USER_NAME_REGEXP, value);
	}

	/**
	 * 是否符合字段规则
	 * @param value 输入值
	 * @return 匹配结果
	 */
	public static boolean isField(String value) {
		return !isMatching(FIELD_REGEXP, value);
	}

	/**
	 * 是否是邮箱
	 * @param value 输入值
	 * @return 匹配结果
	 */
	public static boolean isEmail(String value) {
		return isMatching(EMAIL_REGEXP, value);
	}

	/**
	 * 是否是由简单的字母数字拼接而成的字符串
	 * @param value 输入值
	 * @return 匹配结果
	 */
	public static boolean isSimpleChar(String value) {
		return isMatching(SIMPLE_CHAR_REGEXP, value);
	}

	/**
	 * 是否是HTTP协议
	 * @param value 输入值
	 * @return 匹配结果
	 */
	public static boolean isHttpProtocol(String value) {
		return isFind(HTTP_PROTOCOL_REGEXP, value);
	}

	public static boolean isMatching(String regexp, String value) {
		if (StrUtil.isBlank(value)) {
			return false;
		}
		return Pattern.matches(regexp, value);
	}

	public static boolean isFind(String regexp, String value) {
		if (StrUtil.isBlank(value)) {
			return false;
		}
		Pattern pattern= Pattern.compile(regexp);
		return pattern.matcher(value).find();
	}
}
