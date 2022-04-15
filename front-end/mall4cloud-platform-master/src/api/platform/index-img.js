import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_multishop/admin/index_img/page',
    method: 'get',
    params: pageParam
  })
}

export function get(imgId) {
  return request({
    url: '/mall4cloud_multishop/admin/index_img',
    method: 'get',
    params: {
      imgId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_multishop/admin/index_img',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_multishop/admin/index_img',
    method: 'put',
    data
  })
}

export function deleteById(imgId) {
  return request({
    url: '/mall4cloud_multishop/admin/index_img',
    method: 'delete',
    params: {
      imgId
    }
  })
}
