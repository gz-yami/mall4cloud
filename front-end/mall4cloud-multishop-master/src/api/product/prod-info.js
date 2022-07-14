import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_product/m/spu/page',
    method: 'get',
    params: pageParam
  })
}

export function getProdInfoPage(spuId) {
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

export function getAttrsByCategoryId(param) {
  return request({
    url: '/mall4cloud_product/admin/attr/get_attrs_by_category_id',
    method: 'get',
    params: param
  })
}
