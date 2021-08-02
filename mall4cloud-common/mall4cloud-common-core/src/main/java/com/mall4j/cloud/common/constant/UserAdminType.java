package com.mall4j.cloud.common.constant;


/**
 * @author lhd
 * @date 2020/12/30
 */
public enum UserAdminType {
    /**
     * 管理员
     */
    ADMIN(1),
    /**
     * 用户
     */
    USER(0),
    ;

    private Integer value;

    public Integer value() {
        return value;
    }

    public Integer getValue() {
        return value;
    }


    UserAdminType(Integer value) {
        this.value = value;
    }

    public static UserAdminType instance(Integer value) {
        UserAdminType[] enums = values();
        for (UserAdminType statusEnum : enums) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
