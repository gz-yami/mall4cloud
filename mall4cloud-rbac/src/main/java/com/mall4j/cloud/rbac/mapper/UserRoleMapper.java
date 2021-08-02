package com.mall4j.cloud.rbac.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/6/24
 */
public interface UserRoleMapper {

    /**
     * 根据用户id删除用户与角色关系
     * @param userId
     */
    void deleteByUserId(Long userId);

    /**
     * 根据用户id 批量添加用户角色关系
     * @param userId
     * @param roleIdList
     */
    void insertUserAndUserRole(@Param("userId") Long userId, @Param("roleIdList") List<Long> roleIdList);

    /**
     * 根据用户id 获取用户角色关系
     * @param userId 用户id
     * @return 角色id列表
     */
    List<Long> getRoleIds(Long userId);

    /**
     * 根据角色id 删除用户角色关系
     * @param roleId 用户id
     */
    void deleteByRoleId(Long roleId);
}
