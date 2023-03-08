package com.mall4j.cloud.api.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author FrozenWatermelon
 * @date 2020/9/22
 */
public class AuthAccountVO {
    /**
     * 全平台用户唯一id
     */
    private Long uid;

    @Schema(description = "用户id" )
    private Long userId;

    @Schema(description = "用户名" )
    private String username;

    @Schema(description = "状态 1:启用 0:禁用 -1:删除" )
    private Integer status;

    @Schema(description = "创建ip" )
    private String createIp;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AuthAccountVO{" +
                "uid=" + uid +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", status=" + status +
                ", createIp='" + createIp + '\'' +
                '}';
    }
}
