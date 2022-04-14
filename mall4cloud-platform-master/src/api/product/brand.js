import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_product/platform/brand/page',
    method: 'get',
    params: pageParam
  })
}

export function get(brandId) {
  return request({
    url: '/mall4cloud_product/platform/brand',
    method: 'get',
    params: {
      brandId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_product/platform/brand',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_product/platform/brand',
    method: 'put',
    data
  })
}

export function deleteById(brandId) {
  return request({
    url: '/mall4cloud_product/platform/brand',
    method: 'delete',
    params: {
      brandId
    }
  })
}

// 更新品牌状态（启用或禁用）
export function enableOrDisable(data) {
  return request({
    url: '/mall4cloud_product/platform/brand/update_brand_status',
    method: 'put',
    data
  })
}
