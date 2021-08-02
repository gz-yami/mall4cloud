package com.mall4j.cloud.rbac.service;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.rbac.model.Role;
import com.mall4j.cloud.rbac.vo.RoleVO;

import java.util.List;

/**
 * 角色
 *
 * @author FrozenWatermelon
 * @date 2020-09-17 19:15:44
 */
public interface RoleService {

	/**
	 * 分页获取角色列表
	 * @param pageDTO 分页参数
	 * @param sysType
     * @param tenantId
	 * @return 角色列表分页数据
	 */
	PageVO<RoleVO> page(PageDTO pageDTO, Integer sysType, Long tenantId);

	/**
	 * 分页获取角色列表
	 * @param sysType 系统类型
	 * @param tenantId 租户id
	 * @return 角色列表分页数据
	 */
	List<RoleVO> list(Integer sysType, Long tenantId);

	/**
	 * 根据角色id获取角色
	 *
	 * @param roleId 角色id
	 * @return 角色
	 */
	RoleVO getByRoleId(Long roleId);

	/**
	 * 保存角色
     * @param role 角色
     * @param menuIds 菜单id列表
	 * @param menuPermissionIds 权限id列表
     */
	void save(Role role, List<Long> menuIds, List<Long> menuPermissionIds);

	/**
	 * 更新角色
	 * @param role 角色
	 * @param menuIds 菜单id列表
	 * @param menuPermissionIds 权限id列表
	 */
	void update(Role role, List<Long> menuIds, List<Long> menuPermissionIds);

	/**
	 * 根据角色id删除角色
     * @param roleId
     * @param sysType
     */
	void deleteById(Long roleId, Integer sysType);

	/**
	 * 根据角色id获取该角色所在系统
	 * @param roleId 角色id
	 * @return sysType
	 */
	Integer getBizType(Long roleId);

}
