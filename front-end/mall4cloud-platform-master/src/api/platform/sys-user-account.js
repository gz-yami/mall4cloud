import request from '@/utils/request'

export function get(userId) {
  return request({
    url: '/mall4cloud_platform/sys_user/account',
    method: 'get',
    params: {
      userId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_platform/sys_user/account',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_platform/sys_user/account',
    method: 'put',
    data
  })
}

