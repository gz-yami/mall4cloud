package com.mall4j.cloud.multishop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author lhd
 * @date 2020/12/30
 */
@ApiModel(value= "用户名和密码参数")
public class UsernameAndPasswordDTO {

    @NotBlank(message="用户名不能为空")
    @Size(max = 30)
    @ApiModelProperty(value = "用户名",required=true)
    private String username;

    @NotBlank(message="密码不能为空")
    @Size(max = 64)
    @ApiModelProperty(value = "密码",required=true)
    private String password;

    @ApiModelProperty(value = "店铺id")
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
