import request from '@/utils/request'

// 审核列表
export function page(pageParam) {
  return request({
    url: '/mall4cloud_multishop/platform/shop_auditing/page',
    method: 'GET',
    params: pageParam
  })
}

// 管理列表
export function managePage(pageParam) {
  return request({
    url: '/mall4cloud_multishop/platform/shop_detail/page',
    method: 'GET',
    params: pageParam
  })
}

// 根据店铺id查询详情信息
export function getDetail(shopId) {
  return request({
    url: `/mall4cloud_multishop/platform/shop_detail/info`,
    method: 'GET',
    params: {
      shopId
    }
  })
}

// 校验店铺名
export function examineShopName(param) {
  return request({
    url: '/mall4cloud_multishop/platform/shop_detail/check_shop_name',
    method: 'GET',
    params: param
  })
}

// 创建店铺
export function creat(data) {
  return request({
    url: '/mall4cloud_multishop/platform/shop_detail',
    method: 'POST',
    data: data
  })
}

// 更新店铺
export function update(data) {
  return request({
    url: '/mall4cloud_multishop/platform/shop_detail/update_shop',
    method: 'PUT',
    data: data
  })
}

// 管理-店铺详情
export function getManageDetail(shopId) {
  return request({
    url: '/mall4cloud_multishop/platform/shop_detail/info',
    method: 'GET',
    params: {
      shopId: shopId
    }
  })
}

// 保存账号密码
export function savaAccountAndPassword(data) {
  return request({
    url: '/mall4cloud_multishop/platform/shop_detail/save_username_and_password',
    method: 'POST',
    data: data
  })
}
