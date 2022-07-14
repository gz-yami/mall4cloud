import request from '@/utils/request'

// 获取平台品牌信息列表
export function page(pageParam) {
  return request({
    url: '/mall4cloud_product/platform/brand/page',
    method: 'get',
    params: pageParam
  })
}

// 根据分类，获取品牌列表（发布商品请求品牌）
export function getBrandByCategoryId(pageParam) {
  return request({
    url: '/mall4cloud_product/admin/brand/get_brand_by_category_id',
    method: 'get',
    params: pageParam
  })
}

