import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_rbac/menu_permission/page',
    method: 'get',
    params: pageParam
  })
}

export function get(menuPermissionId) {
  return request({
    url: '/mall4cloud_rbac/menu_permission',
    method: 'get',
    params: {
      menuPermissionId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_rbac/menu_permission',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_rbac/menu_permission',
    method: 'put',
    data
  })
}

export function deleteById(menuPermissionId) {
  return request({
    url: '/mall4cloud_rbac/menu_permission',
    method: 'delete',
    params: {
      menuPermissionId
    }
  })
}

export function menuPermissionsList() {
  return request({
    url: '/mall4cloud_rbac/menu_permission/list',
    method: 'get'
  })
}
