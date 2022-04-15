import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_multishop/admin/notice/page',
    method: 'get',
    params: pageParam
  })
}

export function get(id) {
  return request({
    url: '/mall4cloud_multishop/admin/notice',
    method: 'get',
    params: {
      id
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_multishop/admin/notice',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_multishop/admin/notice',
    method: 'put',
    data
  })
}

export function deleteById(id) {
  return request({
    url: '/mall4cloud_multishop/admin/notice',
    method: 'delete',
    params: {
      id
    }
  })
}
