import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_multishop/admin/hot_search/page',
    method: 'get',
    params: pageParam
  })
}

export function get(hotSearchId) {
  return request({
    url: '/mall4cloud_multishop/admin/hot_search',
    method: 'get',
    params: {
      hotSearchId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_multishop/admin/hot_search',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_multishop/admin/hot_search',
    method: 'put',
    data
  })
}

export function deleteById(hotSearchId) {
  return request({
    url: '/mall4cloud_multishop/admin/hot_search',
    method: 'delete',
    params: {
      hotSearchId
    }
  })
}
