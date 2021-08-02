package com.mall4j.cloud.common.response;

/**
 * @author FrozenWatermelon
 * @date 2020/7/9
 */
public enum ResponseEnum {

	/**
	 * ok
	 */
	OK("00000", "ok"),

	/**
	 * 用于直接显示提示用户的错误，内容由输入内容决定
	 */
	SHOW_FAIL("A00001", ""),

	/**
	 * 方法参数没有校验，内容由输入内容决定
	 */
	METHOD_ARGUMENT_NOT_VALID("A00002", ""),

	/**
	 * 无法读取获取请求参数
	 */
	HTTP_MESSAGE_NOT_READABLE("A00003", "请求参数格式有误"),

	/**
	 * 未授权
	 */
	UNAUTHORIZED("A00004", "Unauthorized"),

	/**
	 * 服务器出了点小差
	 */
	EXCEPTION("A00005", "服务器出了点小差"),

	/**
	 * 数据异常
	 */
	DATA_ERROR("A00007", "数据异常，请刷新后重新操作"),

	/**
	 * 一些需要登录的接口，而实际上因为前端无法知道token是否已过期，导致token已失效时，
	 * 应该返回一个状态码，告诉前端token已经失效了，及时清理
	 */
	CLEAN_TOKEN("A00008", "clean token"),

	/**
	 * 刷新token已过期
	 */
	REFRESH_TOKEN_EXIST("A00009", "refresh token exist"),

	/**
	 * 数据不完整
	 */
	DATA_INCOMPLETE("A00010", "数据不完整"),

	/**
	 * 01开头代表商品
	 */
	SPU_NOT_EXIST("A01000", "spu not exist"),

	/**
	 * 02开头代表购物车
	 */
	SHOP_CART_NOT_EXIST("A02000", "shop cart not exist"),

	/**
	 * 03开头代表订单
	 */
	ORDER_NOT_EXIST("A03000", "order not exist"),

	/**
	 * 请勿重复提交订单，
	 * 1.当前端遇到该异常时，说明前端防多次点击没做好
	 * 2.提示用户 订单已发生改变，请勿重复下单
	 */
	REPEAT_ORDER("A03002", "please don't repeat order"),

	/**
	 * 订单已过期，当前端看到该状态码的时候，提示订单信息已过期，请重新确认后提交，此时用户点击确定，前端刷新页面。
	 */
	ORDER_EXPIRED("A03003", "order expired"),

	/**
	 * 订单已支付，无法取消订单
	 */
	ORDER_PAYED("A03007", "order payed"),

	/**
	 * 订单未发货，无法确认收货
	 */
	ORDER_NO_DELIVERY("A03008", "order no delivery"),

	/**
	 * 库存不足，body会具体返回那个skuid的库存不足
	 */
	NOT_STOCK("A03010", "not stock"),

	/**
	 * 订单未完成或未关闭，无法删除订单
	 */
	ORDER_NOT_FINISH_OR_CLOSE("A03011", "order not finish or close"),

	/**
	 * 订单未支付
	 */
	ORDER_NOT_PAYED("A03012", "order not payed"),

	/**
	 * 订单已失败
	 */
	ORDER_HAS_FAILED("A03013", "order has failed"),

	/**
	 * 没有查询权限
	 */
	REFUND_NOT_PERMISSION("A03024", "refund not permission"),

	/**
	 * 撤销失败 当前状态不允许此操作
	 */
	REFUND_STATUS_CHECK("A03034", "refund status check"),

	/**
	 * 04 开头代表注册登录之类的异常状态
	 * 社交账号未绑定，当前端看到该异常时，应该在合适的时间（比如在购买的时候跳）根据社交账号的类型，跳转到绑定系统账号的页面
	 */
	SOCIAL_ACCOUNT_NOT_BIND("A04001", "social account not bind"),

	/**
	 * 有些时候第三方系统授权之后，会有个临时的key，比如小程序的session_key
	 * 这个异常代表session_key过期，前端遇到这个问题的时候，应该再次调用社交登录的接口，刷新session_key
	 */
	BIZ_TEMP_SESSION_KEY_EXPIRE("A04002", "biz temp session key expire"),

	/**
	 * 账号未注册，前端看到这个状态码，弹出选择框，提示用户账号未注册，是否进入注册页面，用户选择是，进入注册页面
	 */
	ACCOUNT_NOT_REGISTER("A04003", "account not register");
    private final String code;

	private final String msg;

	public String value() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	ResponseEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResponseEnum{" + "code='" + code + '\'' + ", msg='" + msg + '\'' + "} " + super.toString();
	}

}
