package com.mall4j.cloud.rbac.service.impl;

import com.mall4j.cloud.api.rbac.bo.UriPermissionBO;
import com.mall4j.cloud.common.cache.constant.CacheNames;
import com.mall4j.cloud.common.cache.util.CacheManagerUtil;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.rbac.mapper.MenuPermissionMapper;
import com.mall4j.cloud.rbac.model.MenuPermission;
import com.mall4j.cloud.rbac.service.MenuPermissionService;
import com.mall4j.cloud.rbac.vo.MenuPermissionVO;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 菜单资源
 *
 * @author FrozenWatermelon
 * @date 2020-09-14 16:27:55
 */
@Service
public class MenuPermissionServiceImpl implements MenuPermissionService {

	@Autowired
	private MenuPermissionMapper menuPermissionMapper;

	@Autowired
	private CacheManagerUtil cacheManagerUtil;

	@Override
	public PageVO<MenuPermissionVO> page(PageDTO pageDTO,Integer sysType) {
		return PageUtil.doPage(pageDTO, () -> menuPermissionMapper.list(sysType));
	}

	@Override
	public MenuPermissionVO getByMenuPermissionId(Long menuPermissionId) {
		return menuPermissionMapper.getByMenuPermissionId(menuPermissionId);
	}

	@Override
	@CacheEvict(cacheNames = CacheNames.URI_PERMISSION_KEY, key = "#menuPermission.bizType")
	public ServerResponseEntity<Void> save(MenuPermission menuPermission) {

		MenuPermission dbMenuPermission = menuPermissionMapper.getByPermission(menuPermission.getPermission(), AuthUserContext.get().getSysType());
		if (dbMenuPermission != null) {
			return ServerResponseEntity.showFailMsg("权限编码已存在，请勿重复添加");
		}
		menuPermissionMapper.save(menuPermission);
		return ServerResponseEntity.success();
	}

	@Override
	@CacheEvict(cacheNames = CacheNames.URI_PERMISSION_KEY, key = "#menuPermission.bizType")
	public ServerResponseEntity<Void> update(MenuPermission menuPermission) {
		MenuPermission dbMenuPermission = menuPermissionMapper.getByPermission(menuPermission.getPermission(),AuthUserContext.get().getSysType());
		if (dbMenuPermission != null && !Objects.equals(menuPermission.getMenuPermissionId(), dbMenuPermission.getMenuPermissionId())) {
			return ServerResponseEntity.showFailMsg("权限编码已存在，请勿重复添加");
		}
		menuPermissionMapper.update(menuPermission);
		return ServerResponseEntity.success();
	}

	@Override
	@CacheEvict(cacheNames = CacheNames.URI_PERMISSION_KEY, key = "#sysType")
	public void deleteById(Long menuPermissionId, Integer sysType) {
		menuPermissionMapper.deleteById(menuPermissionId,sysType);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = CacheNames.USER_PERMISSIONS_KEY, key = "#sysType + ':' + #userId"),
			@CacheEvict(cacheNames = CacheNames.MENU_ID_LIST_KEY, key = "#userId")
	})
	public void clearUserPermissionsCache(Long userId, Integer sysType) {
	}

	@Override
	public List<String> listUserPermissions(Long userId, Integer sysType, boolean isAdmin) {
		MenuPermissionServiceImpl menuPermissionService = (MenuPermissionServiceImpl)AopContext.currentProxy();
		List<String> permsList;

		// 系统管理员，拥有最高权限
		if (isAdmin) {
			permsList = menuPermissionService.listAllPermission(sysType);
		}
		else {
			permsList = menuPermissionService.listPermissionByUserIdAndSysType(userId, sysType);
		}
		return permsList;
	}

	@Override
	@Cacheable(cacheNames = CacheNames.URI_PERMISSION_KEY, key = "#sysType")
	public List<UriPermissionBO> listUriPermissionInfo(Integer sysType) {
		return menuPermissionMapper.listUriPermissionInfo(sysType);
	}

    @Override
    public List<MenuPermissionVO> listByMenuId(Long menuId) {
        return  menuPermissionMapper.listByMenuId(menuId);
    }

    /**
	 * 获取某个类型用户的所有权限列表（有缓存）
	 * @param sysType 系统类型
	 * @return 权限列表
	 */
	@Cacheable(cacheNames = CacheNames.PERMISSIONS_KEY, key = "#sysType")
	public List<String> listAllPermission(Integer sysType) {
		return menuPermissionMapper.listAllPermissionBySysType(sysType);
	}

	/**
	 * 获取某个类型用户的所有权限列表（有缓存）
	 * @param sysType 系统类型
	 * @return 权限列表
	 */
	@Cacheable(cacheNames = CacheNames.USER_PERMISSIONS_KEY, key = "#sysType + ':' + #userId")
	public List<String> listPermissionByUserIdAndSysType(Long userId, Integer sysType) {
		return menuPermissionMapper.listPermissionByUserIdAndSysType(userId, sysType);
	}

}
