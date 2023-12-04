/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

// 数据分析.js
if (!uni.getStorageSync('cloudSystemType')) {
  let systemType
  // #ifdef H5
  systemType = 2
  // #endif
  // #ifdef MP-WEIXIN
  systemType = 3
  // #endif
  // #ifdef APP-PLUS
  systemType = uni.getSystemInfoSync().platform === 'android' ? 4 : 5
  // #endif
  uni.setStorageSync('cloudSystemType', systemType)
}

const flow = {
  /**
   * 处理页面信息
   * @param {*} to 目标路由信息
   */
  getPageInfo: (to) => {
    const path = to.url
    const query = to.query
    const pageInfo = {}
    if (path === 'pages/index/index' || path === '*' || path === '' || path === '/') {
      // console.log('首页')
      pageInfo.pageId = 1
    } else if (path === 'pages/detail/detail') {
      // console.log('商品详情页')
      pageInfo.pageId = 2
      pageInfo.bid = query.spuId
    } else if (path === 'pages/category/category') {
      // console.log('分类')
      pageInfo.pageId = 3
    } else if (path === 'package-activities/pages/discount-list/discount-list') {
      // pageInfo.pageId = 7
      // console.log('满减')
    } else if (path === 'pages/cart/cart') {
      // console.log('购物车')
      pageInfo.pageId = 201
    } else if (path === 'package-user/pages/order-detail/order-detail') {
      // console.log('订单详情')
      pageInfo.pageId = 202
    } else if (path === 'package-user/pages/order/order') {
      // console.log('订单列表')
      pageInfo.pageId = 203
      pageInfo.bid = query.orderIds
    } else if (path === 'package-activities/pages/payment/payment') {
      // console.log('支付页面')
      pageInfo.pageId = 204
    } else if (path === 'pages/payment-result/payment-result') {
      // console.log('支付成功页面')
      pageInfo.pageId = 205
      pageInfo.bid = query.orderIds
      // 206  生成订单结算页面
    } else if (path === 'package-refund/pages/refund-detail/refund-detail') {
      // console.log('退款详情页')
      pageInfo.pageId = 207
      // 208 生成退款结算页面
    } else if (path === 'pages/my/my') {
      // console.log('个人中心')
      pageInfo.pageId = 301
    } else if (path === 'package-activities/pages/member-center/member-center') {
      // console.log('会员中心')
      pageInfo.pageId = 302
    }
    return pageInfo
  },
  init: function (to) {
    // 初始化数据
    if (!uni.getStorageSync('cloudInitLog')) {
      uni.setStorageSync('cloudInitLog', 'cloudInitLog')
      uni.setStorageSync('cloudStep', 0)
    }
    let step = uni.getStorageSync('cloudStep') * 1
    // 获取页面信息
    const pageInfo = this.getPageInfo(to)
    // oid:  uuid
    // st: 系统类型 1:pc  2:h5  3:小程序 4:安卓  5:ios
    // pid: 页面id
    // ve: 访问事件 1:页面访问 2:加购 3:收藏 4:提交订单 5:支付成功  6:申请退款
    // s: 用户操作步骤数，初始为1，跳转页面后+1，同页面刷新不变
    // sid: 店铺id
    // bid: 业务id
    // n: 仅用于加购商品的商品数量
    // id、st、pid、ve、s 在商城的所有页面都是必传
    // 商品详情页面：sId、bId必传(bId是商品id)
    // 提交订单页面、支付成功页面：bId为订单id
    const uuId = uni.getStorageSync('cloudOid')
    const flowAnalysisLogDtoUni = {
      oid: uuId,
      st: uni.getStorageSync('cloudSystemType'),
      pid: pageInfo.pageId,
      ve: 1,
      bid: pageInfo.bid,
      sid: pageInfo.sid,
      s: step
    }
    uni.setStorageSync('cloudFlowAnalysisLogDtoUni', flowAnalysisLogDtoUni)
    if (pageInfo.pageId) {
      step += 1
      flowAnalysisLogDtoUni.s = step
      uni.setStorageSync('cloudStep', step)
      uni.setStorageSync('cloudFlowAnalysisLogDtoUni', flowAnalysisLogDtoUni)
      uni.setStorageSync('cloudFlowDteail', to.url === 'pages/detail/detail')
      // 单独处理几个支付成功
      const paySuccess = [
        'pages/payment-result/payment-result',
        'package-user/pages/order/order'
      ]
      const visitType = paySuccess.includes(to.url) && pageInfo.bid ? 5 : 1
      // 商品详情页需传店铺id，获取接口返回的店铺id后再提交埋点
      if (uuId && to.url !== 'pages/detail/detail') {
        http.saveLog(visitType, flowAnalysisLogDtoUni)
      }
      // 页面埋点，当uuId不存在将请求放入队列中，等待uuid生成后再提交
      if (!uuId) {
        getApp().globalData.flowRequestQueue.push({
          ve: visitType,
          flowData: flowAnalysisLogDtoUni
        })
        if (!uni.getStorageSync('cloudRequestOidUni')) {
          // 获取用户操作id信息
          http.getOid()
        }
      }
    }
  }
}
export default flow
