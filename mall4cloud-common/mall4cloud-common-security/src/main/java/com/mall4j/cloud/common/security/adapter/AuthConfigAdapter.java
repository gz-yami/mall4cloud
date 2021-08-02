package com.mall4j.cloud.common.security.adapter;

import java.util.List;

/**
 * 实现该接口之后，修改需要授权登陆的路径，不需要授权登陆的路径
 *
 * @author FrozenWatermelon
 * @date 2020/7/4
 */
public interface AuthConfigAdapter {

	/**
	 * 需要授权登陆的路径
	 * @return 需要授权登陆的路径列表
	 */
	List<String> pathPatterns();

	/**
	 * 不需要授权登陆的路径
	 * @param paths
	 * @return 不需要授权登陆的路径列表
	 */
	List<String> excludePathPatterns(String... paths);

}
