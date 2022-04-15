import request from '@/utils/request'

export function get(key) {
  return request({
    url: '/mall4cloud_platform/sys_config/info/' + key,
    method: 'get',
    params: ''
  })
}
export function save(data) {
  return request({
    url: '/mall4cloud_platform/sys_config/save/',
    method: 'post',
    data
  })
}
