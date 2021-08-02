package com.mall4j.cloud.platform.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author lhd
 * @date 2020/9/8
 */
public class SysUserDTO {

    @ApiModelProperty("平台用户id")
    private Long sysUserId;

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty("昵称")
    private String nickName;

    @NotBlank(message = "头像不能为空")
    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("员工编号")
    private String code;

    @ApiModelProperty("联系方式")
    private String phoneNum;

    @ApiModelProperty("角色id列表")
    private List<Long> roleIds;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "SysUserDTO{" +
                "sysUserId=" + sysUserId +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", code='" + code + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", roleIds=" + roleIds +
                '}';
    }
}
