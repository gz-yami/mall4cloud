import request from '@/utils/request'

export function transportPage() {
  return request({
    url: 'mall4cloud_delivery/m/transport/list',
    method: 'get'
  })
}

export function transportGet(attrId) {
  return request({
    url: '/mall4cloud_delivery/m/transport',
    method: 'get',
    params: {
      attrId
    }
  })
}

export function transportSave(data) {
  return request({
    url: '/mall4cloud_delivery/m/transport',
    method: 'post',
    data
  })
}

export function transportUpdate(data) {
  return request({
    url: '/mall4cloud_delivery/m/transport',
    method: 'put',
    data
  })
}

export function deleteTransportById(attrId) {
  return request({
    url: '/mall4cloud_delivery/m/transport',
    method: 'delete',
    params: {
      attrId
    }
  })
}
