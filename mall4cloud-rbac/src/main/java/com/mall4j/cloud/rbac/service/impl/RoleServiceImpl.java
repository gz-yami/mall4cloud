package com.mall4j.cloud.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.rbac.mapper.RoleMenuMapper;
import com.mall4j.cloud.rbac.mapper.UserRoleMapper;
import com.mall4j.cloud.rbac.model.Role;
import com.mall4j.cloud.rbac.mapper.RoleMapper;
import com.mall4j.cloud.rbac.model.RoleMenu;
import com.mall4j.cloud.rbac.service.RoleService;
import com.mall4j.cloud.rbac.vo.RoleVO;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author FrozenWatermelon
 * @date 2020-09-17 19:15:44
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public PageVO<RoleVO> page(PageDTO pageDTO, Integer sysType, Long tenantId) {
        return PageUtil.doPage(pageDTO, () -> roleMapper.list(sysType, tenantId));
    }

    @Override
    public List<RoleVO> list(Integer sysType, Long tenantId) {
        return roleMapper.list(sysType, tenantId);
    }

    @Override
    public RoleVO getByRoleId(Long roleId) {
        RoleVO role = roleMapper.getByRoleId(roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.getByRoleId(roleId);
        role.setMenuIds(roleMenus.stream().filter(roleMenu -> roleMenu.getMenuId() != null).map(RoleMenu::getMenuId).collect(Collectors.toList()));
        role.setMenuPermissionIds(roleMenus.stream().filter(roleMenu -> roleMenu.getMenuPermissionId() != null).map(RoleMenu::getMenuPermissionId).collect(Collectors.toList()));
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Role role, List<Long> menuIds, List<Long> menuPermissionIds) {
        roleMapper.save(role);
        insertMenuAndPermission(role.getRoleId(), menuIds, menuPermissionIds);
    }


    @Override
    public void update(Role role, List<Long> menuIds, List<Long> menuPermissionIds) {
        roleMapper.update(role);
        roleMenuMapper.deleteByRoleId(role.getRoleId());
        insertMenuAndPermission(role.getRoleId(), menuIds, menuPermissionIds);
    }

    private void insertMenuAndPermission(Long roleId, List<Long> menuIds, List<Long> menuPermissionIds) {
        if (CollectionUtil.isNotEmpty(menuIds)) {
            List<RoleMenu> roleMenus = menuIds.stream().map(menuId -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuMapper.insertBatch(roleMenus);
        }

        if (CollectionUtil.isNotEmpty(menuPermissionIds)) {
            List<RoleMenu> roleMenus = menuPermissionIds.stream().map(menuPermissionId -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuPermissionId(menuPermissionId);
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuMapper.insertBatch(roleMenus);
        }
    }

    @Override
    public void deleteById(Long roleId, Integer sysType) {
        roleMapper.deleteById(roleId,sysType);
        roleMenuMapper.deleteByRoleId(roleId);
        userRoleMapper.deleteByRoleId(roleId);
    }

    @Override
    public Integer getBizType(Long roleId) {
        return roleMapper.getBizType(roleId);
    }
}
