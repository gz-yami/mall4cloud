/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

import http from './http.js'
// #ifdef H5
import './jwx/index.js'
// #endif

export default {
  // 判断是否在微信中
  isWechat () {
    const ua = window.navigator.userAgent.toLowerCase()
    return /micromessenger/.test(ua)
  },

  // #ifdef H5
  /**
   *
   * @param {*} cb 微信签名后回调: 即要调用的api及处理函数
   * @param {*} isPay 是否为调用支付api
   * @param {*} dontShowLogin 详情页取消登录弹窗
   */
  getWxConfig (cb, isPay, dontShowLogin) {
    let url = ''
    uni.getSystemInfo({
      success: function (res) {
        url = res.platform === 'ios' && isPay ? uni.getStorageSync('cloudIosUrl') : window.location.href // 客户端平台，值域为：ios、android
      }
    })
    const params = {
      url: '/mall4cloud_auth/wx/jsapi/createJsapiSignature',
      method: 'POST',
      data: url,
      dontShowLogin
    }
    http.request(params).then(data => {
      // 下面要发起微信支付了
      wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: import.meta.env.VITE_APP_MP_APPID, // 必填，公众号的唯一标识
        timestamp: data.timestamp, // 必填，生成签名的时间戳
        nonceStr: data.nonceStr, // 必填，生成签名的随机串
        signature: data.signature, // 必填，签名，见附录1
        jsApiList: ['chooseWXPay', 'updateAppMessageShareData', 'onMenuShareAppMessage', 'scanQRCode', 'getLocation', 'updateTimelineShareData', 'onMenuShareTimeline'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
      })
      if (cb) {
        cb()
      }
    })
  },
  // #endif

  /**
   * 唤起微信支付
   * @param {Object} paydata 支付需要的参数
   * @param {Object} cb 成功回调
   * @param {Object} errorCb 失败回调
   */
  callWexinPay (paydata, cb, errorCb) {
    // 获取后台传入的数据
    const timestamp = paydata.timeStamp
    const nonceStr = paydata.nonceStr
    const packages = paydata.packageValue
    const paySign = paydata.paySign
    const signType = paydata.signType
    const desc = paydata.desc

    this.getWxConfig(() => {
      wx.ready(function () {
        wx.chooseWXPay({
          timestamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
          nonceStr, // 支付签名随机串，不长于 32 位
          package: packages, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
          signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
          paySign, // 支付签名
          desc, // 描述
          success: function (res) {
            // 支付成功后的回调函数
            cb(res)
          },
          fail: function (res) {
            // 失败回调函数
            errorCb(res)
          }
        })
      })
      wx.error(function () { })
    }, 'isPay')
  },
  /**
   * 唤起微信分享
   * @param {Object} sharedata 分享需要的参数
   * @param {Object} cb 成功回调
   * @param {Object} errorCb 失败回调
   */
  callWexinShare (sharedata, cb, errorCb) {
    this.getWxConfig(() => {
      wx.ready(function () {
        wx.updateAppMessageShareData({
          title: sharedata.title, // 分享标题
          link: sharedata.link, // 分享链接
          imgUrl: sharedata.imgUrl, // 分享图标
          desc: sharedata.desc, // 分享描述
          success: function (res) {
            // 支付成功后的回调函数
            cb(res)
          },
          cancel: function (cancelMsg) {
            errorCb(cancelMsg)
          }
        })
        wx.updateTimelineShareData({
          title: sharedata.title, // 分享标题
          link: sharedata.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号 JS 安全域名一致
          imgUrl: sharedata.imgUrl, // 分享图标
          success: function () {
            // 设置成功
          }
        })
        try {
          // 分享给朋友  pc端兼容处理
          wx.onMenuShareAppMessage({
            title: sharedata.title, // 分享标题
            link: sharedata.link, // 分享链接
            imgUrl: sharedata.imgUrl, // 分享图标
            desc: sharedata.desc // 分享描述
          })
          wx.onMenuShareTimeline({
            title: sharedata.title, // 分享标题
            link: sharedata.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号 JS 安全域名一致
            imgUrl: sharedata.imgUrl // 分享图标
          })
        } catch (error) {

        }
      })
      wx.error(function () { })
    }, undefined, sharedata.dontShowLogin)
  },
  /**
   * scanQRCode-扫码
   * @param {Object} paydata 支付需要的参数
   * @param {Object} cb 成功回调
   * @param {Object} errorCb 失败回调
   */
  scanQRCode (cb, errorCb) {
    this.getWxConfig(() => {
      wx.ready(function () {
        wx.scanQRCode({
          needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
          scanType: ['qrCode', 'barCode'], // 可以指定扫二维码还是一维码，默认二者都有
          success: function (res) {
            const result = res.resultStr // 当needResult 为 1 时，扫码返回的结果
            cb(result)
          },
          fail: function (res) {
            // 失败回调函数
            errorCb(res)
          }
        })
      })
      wx.error(function () { })
    })
  },

  /**
   * getLocation-获取地理位置信息
   * @param {Object} cb 成功回调
   * @param {Object} errorCb 失败回调
   */
  getLocation (cb, errorCb) {
    this.getWxConfig(() => {
      wx.ready(function () {
        wx.getLocation({
          type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
          success: function (res) {
            // return latitude = res.latitude, // 纬度，浮点数，范围为90 ~ -90
            // longitude = res.longitude // 经度，浮点数，范围为180 ~ -180
            cb(res)
          },
          fail: function (res) {
            // 失败回调函数
            errorCb(res)
          }
        })
      })
      wx.error(function () { })
    })
  }
}
