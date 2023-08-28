package com.mall4j.cloud.api.rbac.dto;

import jakarta.validation.constraints.NotNull;

/**
 * @author FrozenWatermelon
 * @date 2020/11/27
 */
public class ClearUserPermissionsCacheDTO {

    /**
     * 用户id
     */
    @NotNull(message = "userId not null")
    private Long userId;

    /**
     * 系统类型
     */
    @NotNull(message = "sysType not null")
    private Integer sysType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSysType() {
        return sysType;
    }

    public void setSysType(Integer sysType) {
        this.sysType = sysType;
    }

    @Override
    public String toString() {
        return "ClearUserPermissionsCacheDTO{" +
                "userId=" + userId +
                ", sysType=" + sysType +
                '}';
    }
}
