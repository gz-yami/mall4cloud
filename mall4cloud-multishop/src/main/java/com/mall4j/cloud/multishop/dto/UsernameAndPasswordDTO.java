package com.mall4j.cloud.multishop.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author lhd
 * @date 2020/12/30
 */
@Schema(description = "用户名和密码参数")
public class UsernameAndPasswordDTO {

    @NotBlank(message="用户名不能为空")
    @Size(max = 30)
    @Schema(description = "用户名" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message="密码不能为空")
    @Size(max = 64)
    @Schema(description = "密码" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "店铺id" )
    private Long shopId;

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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "UsernameAndPasswordDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", shopId='" + shopId +
                '}';
    }
}
