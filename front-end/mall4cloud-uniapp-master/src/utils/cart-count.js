import http from './http'

// 获取购物车数字
function getCartCount() {
  const params = {
    url: '/mall4cloud_product/a/shop_cart/prod_count',
    method: 'GET',
    data: {},
    callBack: res => {
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
    }
  }
  if (uni.getStorageSync('token')) {
    http.request(params)
  }
}

exports.getCartCount = getCartCount
