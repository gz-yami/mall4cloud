import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_rbac/menu/page',
    method: 'get',
    params: pageParam
  })
}

export function get(menuId) {
  return request({
    url: '/mall4cloud_rbac/menu',
    method: 'get',
    params: {
      menuId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_rbac/menu',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_rbac/menu',
    method: 'put',
    data
  })
}

export function deleteById(menuId) {
  return request({
    url: '/mall4cloud_rbac/menu',
    method: 'delete',
    params: {
      menuId
    }
  })
}

export function menuList() {
  return request({
    url: '/mall4cloud_rbac/menu/route',
    method: 'get'
  })
}

export function listWithPermissions() {
  return request({
    url: '/mall4cloud_rbac/menu/list_with_permissions',
    method: 'get'
  })
}

export function listMenuIds() {
  return request({
    url: '/mall4cloud_rbac/menu/list_menu_ids',
    method: 'get'
  })
}
