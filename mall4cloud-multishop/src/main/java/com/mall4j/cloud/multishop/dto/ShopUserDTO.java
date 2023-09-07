package com.mall4j.cloud.multishop.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/9/8
 */
public class ShopUserDTO {

    @Schema(description = "店铺用户id" )
    private Long shopUserId;

    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称" )
    private String nickName;

    @Schema(description = "员工编号" )
    private String code;

    @Schema(description = "联系方式" )
    private String phoneNum;

    @Schema(description = "角色id列表" )
    private List<Long> roleIds;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Long getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(Long shopUserId) {
        this.shopUserId = shopUserId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "ShopUserDTO{" +
                "shopUserId=" + shopUserId +
                ", nickName='" + nickName + '\'' +
                ", code='" + code + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", roleIds=" + roleIds +
                '}';
    }
}
