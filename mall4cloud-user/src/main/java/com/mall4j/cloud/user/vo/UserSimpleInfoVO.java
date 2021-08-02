package com.mall4j.cloud.user.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author FrozenWatermelon
 * @date 2021/2/25
 */
public class UserSimpleInfoVO {

    @ApiModelProperty(value = "用户昵称",required=true)
    private String nickName;

    @ApiModelProperty(value = "用户头像",required=true)
    private String pic;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "UserCenterInfoVO{" +
                "nickName='" + nickName + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
