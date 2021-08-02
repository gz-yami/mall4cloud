package com.mall4j.cloud.platform.service;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.platform.model.SysUser;
import com.mall4j.cloud.platform.vo.SysUserSimpleVO;
import com.mall4j.cloud.platform.vo.SysUserVO;

import java.util.List;

/**
 * @author lhd
 * @date 2020/12/22
 */
public interface SysUserService {

	/**
	 * 根据用户id获取当前登陆的商家用户信息
	 * @param userId 用户id
	 * @return 商家用户信息
	 */
	SysUserSimpleVO getSimpleByUserId(Long userId);

	/**
	 * 分页获取平台用户列表
	 * @param pageDTO 分页参数
	 * @param shopId 平台id
	 * @param nickName 昵称
	 * @return 平台用户列表
	 */
	PageVO<SysUserVO> pageByShopId(PageDTO pageDTO, Long shopId, String nickName);

	/**
	 * 根据用户id获取商家用户信息
	 *
	 * @param userId 用户id
	 * @return 商家用户信息
	 */
	SysUserVO getByUserId(Long userId);

	/**
	 * 保存平台用户信息
	 * @param sysUser 平台用户id
	 * @param roleIds 角色id列表
	 */
	void save(SysUser sysUser, List<Long> roleIds);

	/**
	 * 更新平台用户信息
	 * @param sysUser 平台用户id
	 * @param roleIds 角色id列表
	 */
	void update(SysUser sysUser,List<Long> roleIds);

	/**
	 * 根据平台用户id删除平台用户信息
	 * @param sysUserId 平台用户id
	 */
	void deleteById(Long sysUserId);

}
