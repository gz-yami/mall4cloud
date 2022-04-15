import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_delivery/m/transport/page',
    method: 'get',
    params: pageParam
  })
}

export function get(transportId) {
  return request({
    url: '/mall4cloud_delivery/m/transport',
    method: 'get',
    params: {
      transportId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_delivery/m/transport',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_delivery/m/transport',
    method: 'put',
    data
  })
}

export function deleteById(transportId) {
  return request({
    url: '/mall4cloud_delivery/m/transport',
    method: 'delete',
    params: {
      transportId
    }
  })
}

export function listArea() {
  return request({
    url: '/mall4cloud_delivery/m/area/list',
    method: 'get',
    params: {}
  })
}

export function listAreaInfo() {
  return request({
    url: '/mall4cloud_delivery/m/area/areaListInfo',
    method: 'get',
    params: {}
  })
}
