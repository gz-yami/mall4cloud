import request from '@/utils/request'

export function list() {
  return request({
    url: '/mall4cloud_delivery/m/delivery_company/list',
    method: 'get'
  })
}

