import request from '@/utils/request'

// 获取平台所有的分类信息
export function categoryPage(pageParam) {
  return request({
    url: '/mall4cloud_product/admin/category/platform_categories',
    method: 'get',
    params: {}
  })
}

// 获取店铺所有的分类信息
export function shopCategoryPage(pageParam) {
  return request({
    url: '/mall4cloud_product/admin/category/shop_categories',
    method: 'get',
    params: {}
  })
}

// 获取店铺中的销售属性
export function getShopAttrs() {
  return request({
    url: '/mall4cloud_product/admin/attr/get_shop_attrs',
    method: 'get',
    params: {}
  })
}

export function get(categoryId) {
  return request({
    url: '/mall4cloud_product/admin/category',
    method: 'get',
    params: {
      categoryId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_product/admin/category',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_product/admin/category',
    method: 'put',
    data
  })
}

export function deleteById(categoryId) {
  return request({
    url: '/mall4cloud_product/admin/category',
    method: 'delete',
    params: {
      categoryId
    }
  })
}

export function enableOrDisable(data) {
  return request({
    url: '/mall4cloud_product/admin/category/category_enable_or_disable',
    method: 'put',
    data
  })
}
