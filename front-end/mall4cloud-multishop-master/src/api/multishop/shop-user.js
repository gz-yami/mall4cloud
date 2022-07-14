import request from '@/utils/request'

export function shopUserInfo() {
  return request({
    url: '/mall4cloud_multishop/m/shop_user/info',
    method: 'get'
  })
}

export function page(pageParam) {
  return request({
    url: '/mall4cloud_multishop/m/shop_user/page',
    method: 'get',
    params: pageParam
  })
}

export function get(shopUserId) {
  return request({
    url: '/mall4cloud_multishop/m/shop_user',
    method: 'get',
    params: {
      shopUserId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_multishop/m/shop_user',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_multishop/m/shop_user',
    method: 'put',
    data
  })
}

export function deleteById(shopUserId) {
  return request({
    url: '/mall4cloud_multishop/m/shop_user',
    method: 'delete',
    params: {
      shopUserId
    }
  })
}
