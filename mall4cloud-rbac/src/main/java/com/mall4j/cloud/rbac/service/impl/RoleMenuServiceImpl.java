package com.mall4j.cloud.rbac.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.mall4j.cloud.rbac.mapper.RoleMenuMapper;
import com.mall4j.cloud.rbac.service.RoleMenuService;

/**
 * @author FrozenWatermelon
 * @date 2020/6/23
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

	@Resource
	private RoleMenuMapper roleMenuMapper;

}
