import request from '@/utils/request'

export function shopUserInfo() {
  return request({
    url: '/mall4cloud_platform/sys_user/info',
    method: 'get'
  })
}

export function page(pageParam) {
  return request({
    url: '/mall4cloud_platform/sys_user/page',
    method: 'get',
    params: pageParam
  })
}

export function get(sysUserId) {
  return request({
    url: '/mall4cloud_platform/sys_user',
    method: 'get',
    params: {
      sysUserId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_platform/sys_user',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_platform/sys_user',
    method: 'put',
    data
  })
}

export function deleteById(sysUserId) {
  return request({
    url: '/mall4cloud_platform/sys_user',
    method: 'delete',
    params: {
      sysUserId
    }
  })
}
