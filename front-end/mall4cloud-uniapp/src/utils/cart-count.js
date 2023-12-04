/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

import http from './http'

// 获取购物车数字
function getCartCount () {
  if (!uni.getStorageSync('cloudToken')) {
    uni.removeTabBarBadge({
      index: 3
    })
    return
  }
  const params = {
    url: '/mall4cloud_product/a/shop_cart/prod_count',
    method: 'GET',
    data: {}
  }
  http.request(params).then(res => {
    if (res) {
      uni.setTabBarBadge({
        index: 2,
        text: res + ''
      })
    } else {
      uni.removeTabBarBadge({
        index: 2
      })
    }
  })
}

export { getCartCount }
