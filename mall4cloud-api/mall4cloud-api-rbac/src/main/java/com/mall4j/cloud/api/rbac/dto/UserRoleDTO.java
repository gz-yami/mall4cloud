package com.mall4j.cloud.api.rbac.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/11/27
 */
public class UserRoleDTO {

    /**
     * 用户id
     */
    @NotNull(message = "userId not null")
    private Long userId;


    /**
     * 角色id列表
     */
    @NotEmpty(message = "userId not null")
    private List<Long> roleIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "UserRoleDTO{" +
                "userId=" + userId +
                ", roleIds=" + roleIds +
                '}';
    }
}
