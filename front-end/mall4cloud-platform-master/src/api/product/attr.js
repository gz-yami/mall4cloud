import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_product/admin/attr/page',
    method: 'get',
    params: pageParam
  })
}

export function get(attrId) {
  return request({
    url: '/mall4cloud_product/admin/attr',
    method: 'get',
    params: {
      attrId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_product/admin/attr',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_product/admin/attr',
    method: 'put',
    data
  })
}

export function deleteById(attrId) {
  return request({
    url: '/mall4cloud_product/admin/attr',
    method: 'delete',
    params: {
      attrId
    }
  })
}
