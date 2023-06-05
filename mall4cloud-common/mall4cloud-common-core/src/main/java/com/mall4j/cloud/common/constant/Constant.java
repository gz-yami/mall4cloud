package com.mall4j.cloud.common.constant;

/**
 * 常量
 * @author FrozenWatermelon
 */
public class Constant {

	/** 超级管理员ID */
	public static final int SUPER_ADMIN_ID = 1;

	/**
	 * 自营店id
	 */
	public static final long MAIN_SHOP = 1L;

	/**
	 * 如果把平台的数据也保存在店铺里面，如分类，热搜之类的，保存的店铺id
	 */
	public static final long PLATFORM_SHOP_ID = 0L;

	/**
	 * 商城缺失sku属性时的字符描述
	 */
	public static final String SKU_PREFIX = "规格:";
	public static final String DEFAULT_SKU = "规格";

	/** 系统菜单最大id */
	public static final int SYS_MENU_MAX_ID = 30;

	/**
	 * 查询订单成功状态
	 */
	public static final String SUCCESS = "SUCCESS";

	/**
	 * 一级分类id
	 */
	public static final Long CATEGORY_ID = 0L;

	/**
	 * 分类间隔
	 */
	public static final String CATEGORY_INTERVAL = "-";

	/**
	 * 店铺收藏返回的商品数量
	 */
	public static final Integer SPU_SIZE_FIVE = 5;

	/**
	 * 订单如果未支付，30分钟后取消
	 */
	public static final int ORDER_CANCEL_TIME = 30;

	/**
	 * 如果因为网络原因，导致无法清楚订单是否已经下单成功，那么处理的时间应该要比订单取消时间晚一点
	 */
	public static final int ORDER_CANCEL_WAIT_TIME = 10 + ORDER_CANCEL_TIME;

	/**
	 * 默认金额
	 */
	public static final Long DEFAULT_AMOUNT = 0L;

	/**
	 * 字符串最大长度限制
	 */
	public static final Integer MAX_FIELD_LIMIT = 500;

	/**
	 * 句号（英文符号）
	 */
	public static final String PERIOD = ".";

	/**
	 * 下划线
	 */
	public static final String UNDERLINE = "_";

	/**
	 * 逗号
	 */
	public static final String COMMA = ",";
}
