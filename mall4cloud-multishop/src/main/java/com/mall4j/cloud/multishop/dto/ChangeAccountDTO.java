package com.mall4j.cloud.multishop.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author FrozenWatermelon
 * @date 2020/9/22
 */
public class ChangeAccountDTO {

    @NotNull(message = "userId not null")
    @Schema(description = "用户id" )
    private Long userId;

    @NotBlank(message = "username not blank")
    @Schema(description = "用户名" )
    private String username;

    @NotBlank(message = "password not blank")
    @Schema(description = "密码" )
    private String password;

    @NotNull(message = "status not null")
    @Schema(description = "状态 1启用 0禁用" )
    private Integer status;

    @Schema(description = "邮箱" )
    private String email;

    @Schema(description = "手机号" )
    private String phone;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ChangeAccountDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
