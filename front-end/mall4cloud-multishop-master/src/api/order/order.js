import request from '@/utils/request'

export function page(pageParam) {
  return request({
    url: '/mall4cloud_order/m/order/page',
    method: 'get',
    params: pageParam
  })
}

export function changeAmount(data) {
  return request({
    url: '/mall4cloud_order/m/order/change_amount',
    method: 'put',
    data
  })
}

export function orderInfo(orderId) {
  return request({
    url: '/mall4cloud_order/m/order/order_info/' + orderId,
    method: 'get'
  })
}

// 原/order/delivery/getOrderItemUnDelivery
export function getOrderItemAndAddress(orderId) {
  return request({
    url: '/mall4cloud_order/m/order/order_item_and_address/' + orderId,
    method: 'get'
  })
}

//
export function delivery(data) {
  return request({
    url: '/mall4cloud_order/m/order/delivery',
    method: 'post',
    data
  })
}
