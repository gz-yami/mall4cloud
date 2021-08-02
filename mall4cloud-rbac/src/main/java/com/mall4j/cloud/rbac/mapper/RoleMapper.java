package com.mall4j.cloud.rbac.mapper;

import com.mall4j.cloud.rbac.model.Role;
import com.mall4j.cloud.rbac.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色
 *
 * @author FrozenWatermelon
 * @date 2020-09-17 19:15:44
 */
public interface RoleMapper {

	/**
	 * 获取角色列表
	 *
	 * @param sysType
	 * @param tenantId
	 * @return 角色列表
	 */
	List<RoleVO> list(@Param("sysType") Integer sysType, @Param("tenantId") Long tenantId);

	/**
	 * 根据角色id获取角色
	 *
	 * @param roleId 角色id
	 * @return 角色
	 */
	RoleVO getByRoleId(@Param("roleId") Long roleId);

	/**
	 * 保存角色
	 *
	 * @param role 角色
	 */
	void save(@Param("role") Role role);

	/**
	 * 更新角色
	 *
	 * @param role 角色
	 */
	void update(@Param("role") Role role);

	/**
	 * 根据角色id删除角色
	 *
	 * @param roleId  角色id
	 * @param sysType 系统类型
	 */
	void deleteById(@Param("roleId") Long roleId, @Param("sysType") Integer sysType);

	/**
	 * 根据角色id获取该角色所在系统
	 *
	 * @param roleId 角色id
	 * @return sysType
	 */
    Integer getBizType(@Param("roleId") Long roleId);
}
