package com.mall4j.cloud.rbac.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 菜单id和权限id列表
 * @author FrozenWatermelon
 * @date 2020/9/18
 */
public class MenuWithPermissionIdDTO {

    @ApiModelProperty("菜单id")
    private Long menuId;

    @ApiModelProperty("菜单下的权限id列表")
    private List<Long> permissionIds;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    @Override
    public String toString() {
        return "MenuWithPermissionIdDTO{" +
                "menuId=" + menuId +
                ", permissionIds=" + permissionIds +
                '}';
    }
}
