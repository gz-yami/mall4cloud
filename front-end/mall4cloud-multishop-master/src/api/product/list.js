import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_search/m/search/page',
    method: 'get',
    params: pageParam
  })
}

export function get(spuId) {
  return request({
    url: '/mall4cloud_product/admin/spu',
    method: 'get',
    params: {
      spuId
    }
  })
}

export function save(data) {
  return request({
    url: '/mall4cloud_product/admin/spu',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/mall4cloud_product/admin/spu',
    method: 'put',
    data
  })
}

export function deleteById(spuId) {
  return request({
    url: '/mall4cloud_product/admin/spu',
    method: 'delete',
    params: {
      spuId
    }
  })
}

// 商品上下架
export function updateProdStatus(data) {
  return request({
    url: '/mall4cloud_product/admin/spu/prod_status',
    method: 'put',
    data
  })
}

// 修改spu（名称、价格、库存、序号）信息
export function updatePartProdStatus(data) {
  return request({
    url: '/mall4cloud_product/admin/spu/update_spu_data',
    method: 'put',
    data
  })
}

export function examineProdOnShelfApply(data) {
  return request({
    url: '/mall4cloud_product/admin/product_offline_handle_event/spu_audit',
    method: 'POST',
    data
  })
}

// 获取最新下线活动详情
export function getOfflineDetailById(prodId) {
  return request({
    url: `/mall4cloud_product/admin/product_offline_handle_event/get_offline_handle_event_by_prodId/${prodId}`,
    method: 'GET'
  })
}

// 申请上线
export function rereapplyOnlineById(data) {
  return request({
    url: '/mall4cloud_product/admin/product_offline_handle_event/audit_apply',
    method: 'POST',
    data
  })
}
