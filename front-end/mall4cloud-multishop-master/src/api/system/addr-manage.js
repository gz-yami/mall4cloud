import request from '@/utils/request'

export function page() {
  return request({
    url: '/mall4cloud_delivery/m/area/list',
    method: 'get'
  })
}

export function get(areaId) {
  return request({
    url: '/mall4cloud_delivery/m/area',
    method: 'get',
    params: {
      areaId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_delivery/m/area',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_delivery/m/area',
    method: 'put',
    data
  })
}

export function deleteById(areaId) {
  return request({
    url: '/mall4cloud_delivery/m/area',
    method: 'delete',
    params: {
      areaId
    }
  })
}
