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
import * as cartCount from './cart-count.js'
import { AppType, PayType } from './constant.js'
import Big from 'big.js'

const wxs = number()

const util = {
  /*
   * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
   *
   * https://www.mall4j.com/
   *
   * 未经允许，不可做商业用途！
   *
   * 版权所有，侵权必究！
   */
  formatTime: (date) => {
    const year = date.getFullYear()
    const month = date.getMonth() + 1
    const day = date.getDate()
    const hour = date.getHours()
    const minute = date.getMinutes()
    const second = date.getSeconds()
    return [year, month, day].map(util.formatNumber()).join('/') + ' ' + [hour, minute, second].map(util.formatNumber()).join(':')
  },

  formatNumber: n => {
    n = n.toString()
    return n[1] ? n : '0' + n
  },

  formatHtml: content => {
    if (!content) {
      return ''
    }
    content = content.replace(/<p/gi, '<p style="max-width:100% !important;word-wrap:break-word;word-break:break-word;" ')
    content = content.replace(/<ul/gi, '<ul style="list-style-type: none" ')
    content = content.replace(/<img/gi, '<img style="width: auto; max-width:100% !important;height:auto !important;margin:0;" ')
    content = content.replace(/style="/gi, 'style="max-width:100% !important;table-layout:fixed;word-wrap:break-word; word-break: break-word;')
    content = content.replace(/<table/gi, '<table style="table-layout:fixed; word-wrap:break-word; word-break:break-word;" ')
    content = content.replace(/<td/gi, '<td cellspacing="0" cellpadding="0" border="0" style="display:block;vertical-align:top;margin: 0px; padding: 0px; border: 0px;outline-width:0px;"')
    content = content.replace(/width=/gi, 'sss=')
    content = content.replace(/height=/gi, 'sss=')
    content = content.replace(/ \/>/gi, ' style="max-width:100% !important;height:auto !important;margin:0;display:block;" />')
    content = content.replace(/\n/gi, '<br>')
    content = content.replace(/\r\n/gi, '<br>')
    return content
  },

  endOfStartTime: (startTime, endTime) => {
    let result = {
      day: '00',
      hou: '00',
      min: '00',
      sec: '00',
      signs: 0
    }

    if (endTime - startTime > 0) {
      const time = (endTime - startTime) / 1000 // 获取天、时、分、秒
      const day = parseInt(time / (60 * 60 * 24))
      const hou = parseInt(time % (60 * 60 * 24) / 3600)
      const min = parseInt(time % (60 * 60 * 24) % 3600 / 60)
      const sec = parseInt(time % (60 * 60 * 24) % 3600 % 60)
      result = {
        day: `${util.timeFormat(day)}`,
        hou: `${util.timeFormat(hou)}`,
        min: `${util.timeFormat(min)}`,
        sec: `${util.timeFormat(sec)}`,
        signs: 1
      }
    }

    return result
  },

  // 小于10的格式化函数
  timeFormat: times => {
    return times < 10 ? '0' + times : times
  },

  dateToTimestamp: dateStr => {
    if (!dateStr) {
      return ''
    }

    const newDataStr = dateStr.replace(/\.|-/g, '/')
    const date = new Date(newDataStr)
    return date.getTime()
  },

  // 检查是否授权
  checkAuthInfo: fn => {
    uni.hideLoading()
    // eslint-disable-next-line no-undef
    const pages = getCurrentPages()
    uni.setStorageSync('cloudRouteUrlAfterLogin', pages.length ? pages[pages.length - 1].$page.fullPath : '/pages/index/index')
    if (uni.getStorageSync('cloudToken')) {
      fn()
      return
    }
    if (pages[0] && pages[pages.length - 1].route === 'pages/cart/cart') {
      return
    }
    uni.navigateTo({
      url: '/package-user/pages/user-login/user-login'
    })
  },

  // 函数参数必须是字符串，因为二代身份证号码是十八位，而在javascript中，十八位的数值会超出计算范围，造成不精确的结果，导致最后两位和计算的值不一致，从而该函数出现错误。
  // 详情查看javascript的数值范围
  checkIDCard: (idcode) => {
    // 加权因子
    const weightFactor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
    // 校验码
    const checkCode = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2']

    const code = idcode + ''
    const last = idcode[17]// 最后一位

    const seventeen = code.substring(0, 17)

    // ISO 7064:1983.MOD 11-2
    // 判断最后一位校验码是否正确
    const arr = seventeen.split('')
    const len = arr.length
    let num = 0
    for (let i = 0; i < len; i++) {
      num = num + arr[i] * weightFactor[i]
    }

    // 获取余数
    const resisue = num % 11
    const lastNo = checkCode[resisue]

    // 格式的正则
    // 正则思路
    /*
    第一位不可能是0
    第二位到第六位可以是0-9
    第七位到第十位是年份，所以七八位为19或者20
    十一位和十二位是月份，这两位是01-12之间的数值
    十三位和十四位是日期，是从01-31之间的数值
    十五，十六，十七都是数字0-9
    十八位可能是数字0-9，也可能是X
    */
    const idcardPatter = /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X])$/

    // 判断格式是否正确
    const format = idcardPatter.test(idcode)

    // 返回验证结果，校验码和格式同时正确才算是合法的身份证号码
    return !!(last === lastNo && format)
  },

  /**
   * 手机号正则校验
   */
  checkPhoneNumber: (phoneNumber) => {
    // var regexp = /^[1]([3-9])[0-9]{9}$/
    const regexp = /^[1][0-9]{10}$/
    return regexp.test(phoneNumber)
  },

  /**
   * 邮箱正则校验
   */
  checkEmail: (email) => {
    const regexp = /^(\w+((-\w+)|(\.\w+))*)\+\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$ /
    return regexp.test(email)
  },

  /**
   * 用户名正则校验：①4-16位字母、数字或下划线； ②不允许纯数字
   * @param {String} userName
   */
  checkUserName: (userName) => {
    const regexp = /^(?!\d+$)[a-zA-Z0-9_]{4,16}$/
    return regexp.test(userName)
  },

  /**
   * 登录密码校验: 由字母加数字或符号至少两种以上字符组成6-20位半角字符，区分大小写
   * @param {String} password
   */
  checkPassword: (password) => {
    // var regexp = /^(?!\d+$)(?![a-zA-Z]+$)[0-9A-Za-z\W]{6,20}$/
    // return regexp.test(password)
    return password.length >= 6 && password.length <= 16
  },

  /**
   * 包含符号、空格
   * @param {String} value
   * @returns
   */
  containsSymbols: (value) => {
    const regexp = /[`~!@#$%^&*()_\-+=<>?:"{}|,./;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘’，。、 ]/
    return regexp.test(value)
  },

  /**
   * 匹配两侧空格，匹配到就返回true
   * @param {String} str
   * @returns {Boolean}
   */
  noSpacesBothSides: (str) => {
    const reg = /(^\s)|(\s$)/g
    return reg.test(str)
  },

  /**
   * 获取链接上的参数
   */
  getUrlKey: (name) => {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || ['', ''])[1]
      .replace(/\+/g, '%20')) || null
  },

  /**
   * 记录页面点击
   * @param {Number} visitType 访问类型 1:页面访问  2:加购  3:收藏  4:提交订单  5:支付成功  6:申请退款
   * @param {Object} params 参数 pid:页面id bid:业务id  n:加购商品的数量  sid:店铺id
   */
  tapLog: (visitType, { pid, bid, sid, n }) => {
    const flowAnalysisLogDtoUni = uni.getStorageSync('cloudFlowAnalysisLogDtoUni') || {}
    flowAnalysisLogDtoUni.n = n
    flowAnalysisLogDtoUni.bid = bid
    flowAnalysisLogDtoUni.sid = sid
    // 处理生成订单页面的pid
    if (pid) {
      let step = uni.getStorageSync('cloudStep') * 1
      step += 1
      flowAnalysisLogDtoUni.s = step
      uni.setStorageSync('cloudStep', step)
      flowAnalysisLogDtoUni.pid = pid
    }
    visitType = visitType || 1
    http.saveLog(visitType, flowAnalysisLogDtoUni)
  },

  /**
   * 获取唯一值
   */
  getUuid: () => {
    const s = []
    const hexDigits = '0123456789abcdef'
    for (let i = 0; i < 36; i++) {
      s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
    }
    s[14] = '4' // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = '-'

    return s.join('')
  },

  /**
   * 时间戳与当前时间比较，是否为同一天
   */
  checkSameDay: (timestamp) => {
    const nt = new Date(new Date().getTime())
    const ot = new Date(timestamp)
    return ot.getFullYear() + ot.getMonth() + ot.getDate() === nt.getFullYear() + nt.getMonth() + nt.getDate()
  },

  /**
   * 函数节流
   * @param fn
   * @param wait
   * @returns {Function}
   * @constructor
   */
  throttle: (fn, wait) => {
    let timer = null
    return function () {
      const context = util
      const args = arguments
      if (!timer) {
        timer = setTimeout(function () {
          fn.apply(context, args)
          timer = null
        }, wait)
      }
    }
  },

  /**
   * 时间戳转化为年 月 日 时 分 秒
   * number: 传入时间戳
   * format：返回格式，支持自定义，但参数必须与formateArr里保持一致
   */
  tsToDate: (number, format) => {
    const formateArr = ['Y', 'M', 'D', 'h', 'm', 's']
    const returnArr = []

    const date = new Date(number)
    returnArr.push(date.getFullYear())
    returnArr.push(date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1)
    returnArr.push(date.getDate() < 10 ? '0' + date.getDate() : date.getDate())

    returnArr.push(date.getHours() < 10 ? '0' + date.getHours() : date.getHours())
    returnArr.push(date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes())
    returnArr.push(date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds())

    for (const i in returnArr) {
      format = format.replace(formateArr[i], returnArr[i])
    }
    return format
  },

  /**
   * 进行相隔时间判断
   *
   * true 删除显示时间
   * false 保留显示时间
   */
  timeBeApart: (uppTime, preTime) => {
    if (!uppTime) {
      return false
    }
    const dateDiff = preTime - uppTime// 时间差的毫秒数
    const dayDiff = Math.floor(dateDiff / (24 * 3600 * 1000))// 计算出相差天数
    const leave1 = dateDiff % (24 * 3600 * 1000) // 计算天数后剩余的毫秒数
    const hours = Math.floor(leave1 / (3600 * 1000))// 计算出小时数
    // 计算相差分钟数
    const leave2 = leave1 % (3600 * 1000) // 计算小时数后剩余的毫秒数
    const minutes = Math.floor(leave2 / (60 * 1000))// 计算相差分钟数

    return !(dayDiff >= 1 || hours >= 1 || minutes > 4)
  },

  /**
   * 移除购物车Tabbar的数字
   */
  removeTabBadge: () => {
    uni.removeTabBarBadge({
      index: 3
    })
  },

  /**
   * 精度运算
   * @param {*} a 第一个计算数(例如:被除数/被减数)
   * @param {*} b 第二个计算数(例如:除数/减数)
   * @param {*} countMode 计算方式(0:加 1:减 2:乘 3:除)
   */
  accuracyCount: (a, b, countMode) => {
    let finalResult = 0
    if (countMode === 0) {
      finalResult = new Big(a).plus(b).valueOf()
    } else if (countMode === 1) {
      finalResult = new Big(a).minus(b).valueOf()
    } else if (countMode === 2) {
      finalResult = new Big(a).times(b).valueOf()
    } else if (countMode === 3) {
      finalResult = new Big(a).div(b).valueOf()
    } else {
      return
    }
    return finalResult
  },

  /**
   * 倒计时计算
   * @param {*} seconds 在几秒后失效
   */
  countDown: (seconds) => {
    if (!seconds) {
      return ''
    }
    // 计算日期
    const day = parseInt(seconds / (60 * 60 * 24))
    const hour = parseInt((seconds % (60 * 60 * 24)) / 3600)
    const min = parseInt(seconds % (60 * 60 * 24) % 3600 / 60)
    const sec = parseInt(seconds % (60 * 60 * 24) % 3600 % 60)
    return {
      day: `${util.timeFormat(day)}`,
      hour: `${util.timeFormat(hour)}`,
      min: `${util.timeFormat(min)}`,
      sec: `${util.timeFormat(sec)}`
    }
  },

  /**
   * 登录成功
   * @param {Object} loginRes 登录成功返回的数据
   * @param {Boolean} isRefreshToken 该次登录是否为刷新token;
   */
  loginSuccess: async function (loginRes, isRefreshToken) {
    uni.setStorageSync('cloudToken', loginRes.accessToken)
    uni.setStorageSync('cloudLoginResult', loginRes) // 保存整个登录数据
    const expiresTimeStamp = loginRes.expiresIn * 1000 / 2 + new Date().getTime()
    // 缓存token的过期时间
    uni.setStorageSync('cloudExpiresTimeStamp', expiresTimeStamp)
    // 请求用户信息
    const userInfo = await this.getUserInfo()
    uni.setStorageSync('cloudUserDetails', userInfo)

    // 还原全局 正在登录状态
    util.resetLoginSts()
    // 请求购物车数量
    cartCount.getCartCount()

    // 登录成功后，更新通联支付配置
    util.getAllinpayConfig()

    // 若为刷新token的登录，则不需要跳转到上一页面
    if (isRefreshToken) {
      // console.log('刷新token登录成功，不需要跳转到上一页面')
      return
    }
    const routeUrlAfterLogin = uni.getStorageSync('cloudRouteUrlAfterLogin')
    const pages = getCurrentPages()
    if (pages[pages.length - 1].route === 'pages/detail/detail' || pages[pages.length - 1].route === 'package-activities/pages/group-detail/group-detail') {
      // 如果当前页面是商品详情页或拼团详情页，则不跳转其它页面
      return
    }
    const prevPage = pages[pages.length - 2]
    if (!prevPage) {
      // 当前页面为首页则不跳转
      if (pages[pages.length - 1].route === 'pages/index/index') {
        return
      }

      uni.switchTab({
        url: '/pages/index/index'
      })
      return
    }
    // 判断上一页面是否为tabbar页面 (首页和分类页无需登录接口)
    const isTabbar = prevPage.route === 'pages/user/user' || prevPage.route === 'pages/basket/basket'
    if (prevPage.route === 'package-activities/pages/live-room/live-room' || prevPage.route === 'package-activities/pages/wx-player/wx-player') {
      uni.setStorageSync('cloudLiveRoomReload', true)
    }
    if (isTabbar) {
      uni.switchTab({
        url: '/' + prevPage.route
      })
    } else {
      // 非tabbar页面
      let backDelata = 0
      pages.forEach((page, index) => {
        if (page.$page.fullPath === routeUrlAfterLogin) {
          backDelata = pages.length - index - 1
        }
      })
      if (backDelata) {
        uni.navigateBack({
          delta: backDelata
        })
      } else {
        uni.switchTab({
          url: '/pages/index/index'
        })
      }
    }
  },

  getUserInfo: () => {
    // eslint-disable-next-line no-unused-vars
    return new Promise((resolve, reject) => {
      http.request({
        url: '/mall4cloud_user/user/ma/user_detail_info',
        method: 'GET',
        dontTrunLogin: true
      }).then((res) => {
        resolve(res)
      })
    })
  },

  /**
   * 刷新token
   */
  refreshToken: () => {
    const refreshToken = uni.getStorageSync('cloudLoginResult').refreshToken
    const expiresTimeStamp = uni.getStorageSync('cloudExpiresTimeStamp')
    if (refreshToken && expiresTimeStamp && expiresTimeStamp < new Date().getTime()) {
      getApp().globalData.isLanding = true
      const params = {
        url: '/mall4cloud_auth/ua/token/refresh',
        method: 'POST',
        isRefreshing: true,
        data: {
          refreshToken
        }
      }
      http.request(params).then(res => {
        util.resetLoginSts()
        util.loginSuccess(res, true)
      }).catch(() => {
        uni.hideLoading()
        util.resetLoginSts()
        // 清除refreshToken 过期时间
        uni.removeStorageSync('cloudExpiresTimeStamp')
      })
    }
  },

  /**
   * 获取并更新通联支付配置信息
   */
  getAllinpayConfig: () => {
    const params = {
      url: '/mall4cloud_admin/ua/app/sys_config/pay_settlement_type',
      method: 'GET'
    }
    http.request(params).then(res => {
      if (res) {
        const paySettlementType = JSON.parse(res).paySettlementType
        uni.setStorageSync('cloudPaySettlementType', paySettlementType)
        if (paySettlementType === 1) {
          // 通联支付已开启，则获取用户是否创建通联会员
          util.getAllinpayMember()
        }
      }
    })
  },

  /**
   * 获取用户是否创建通联会员
   */
  getAllinpayMember: () => {
    const params = {
      url: '/mall4cloud_payment/allinpay/member/get_user_info',
      method: 'GET',
      withoutPrompting: true
    }
    http.request(params).catch(res => {
      if (res.msg === '用户不存在。') {
        util.createAllinpayMember()
      }
    })
  },

  /**
   * 创建通联会员
   */
  createAllinpayMember: () => {
    const params = {
      url: '/mall4cloud_payment/allinpay/member/create_member',
      method: 'POST'
    }
    http.request(params)
  },

  /**
   * 防抖
   * @param fn
   * @param t
   * @returns {Function}
   */
  debounce: (fn, t) => {
    const delay = t || 300
    let timer
    return function () {
      const args = arguments
      if (timer) {
        clearTimeout(timer)
      }
      const callNow = !timer
      timer = setTimeout(() => {
        timer = null
      }, delay)
      if (callNow) fn.apply(util, args)
    }
  },

  /**
   * 微信环境登录
   */
  weChatLogin: () => {
    const appType = uni.getStorageSync('cloudAppType')
    if (appType !== AppType.MP && appType !== AppType.MINI) {
      return
    }

    if (appType === AppType.MINI) {
      wx.login({
        success: res => {
          util.loginByCode(res.code) // 小程序-先请求微信小程序登录接口
        }
      })
      return
    }

    if (appType === AppType.MP) {
      const tempuid = uni.getStorageSync('cloudTempUidCloud')
      const token = uni.getStorageSync('cloudToken')
      if (!tempuid && !token) {
        // alert('公众号网页登录location.href:' + location.href)
        const code = util.getUrlKey('code')
        if (!code) {
          http.mpAuthLogin(import.meta.env.VITE_APP_DOMAIN_ADDRESS, true)
          return
        }
        // alert('H5-微信环境 code:' + code)
        util.loginByCode(code) // 先请求微信公众号登录接口
      }
    }
  },

  /**
   * 通过微信返回的code登录
   * @param {String} code 请求微信返回的 code
   */
  loginByCode: (code) => {
    const params = {
      login: true,
      url: uni.getStorageSync('cloudAppType') === AppType.MINI ? '/mall4cloud_auth/ua/social/ma' : '/mall4cloud_auth/ua/social/mp',
      method: 'POST',
      data: code
    }
    http.request(params).then(res => {
      uni.setStorageSync('cloudTempUidCloud', res.tempUid)
      if (res.tokenInfo) {
        util.loginSuccess(res.tokenInfo)
      }
    }).catch(errRes => {
      if (errRes.code === 'A04001') {
        // 还原全局 正在登录状态
        util.resetLoginSts()
        uni.setStorageSync('cloudTempUidCloud', errRes.data) // token或tempUid
      }
    })
  },

  /**
   * 还原登录状态
   */
  resetLoginSts: () => {
    getApp().globalData.isLanding = false
    while (getApp().globalData.requestQueue.length) {
      http.request(getApp().globalData.requestQueue.pop())
      getApp().globalData.currentReqCounts--
    }
  },

  /**
   * 设置默认支付类型
   * @param {String} payTypeStr aliPay支付宝   wechatPay微信支付   balancePay余额支付
   * @return {Number} payType 支付类型
   */
  setDefaultPayType: (payTypeStr) => {
    const isWechat = uni.getStorageSync('cloudAppType') === AppType.MP || uni.getStorageSync('cloudAppType') === AppType.MINI
    // payTypeStr 默认：支付宝
    payTypeStr = payTypeStr || 'aliPay'
    let payType
    if (payTypeStr === 'balancePay') {
      payType = PayType.BALANCEPAY
    } else {
      const paySettlementType = uni.getStorageSync('cloudPaySettlementType')
      // #ifdef H5
      payType = payTypeStr === 'aliPay' ? PayType.ALIPAY_H5 : isWechat ? PayType.WECHATPAY_MP : PayType.WECHATPAY_H5
      // #endif
      // #ifdef APP-PLUS
      if (paySettlementType === 1) {
        payType = payTypeStr === 'aliPay' ? PayType.ALIPAY_H5 : PayType.WECHATPAY_APP
        return payType
      }
      payType = payTypeStr === 'aliPay' ? PayType.ALIPAY_APP : PayType.WECHATPAY_APP
      // #endif
      // #ifdef MP-WEIXIN
      payType = PayType.WECHATPAY
      // #endif
    }
    return payType
  },

  /**
   * minIo 上传文件
   * @param imgItem
   * @param {Object} fn 上传成功回调
   */
  mioIoUpload: (imgItem, fn) => {
    const params = {
      url: '/mall4cloud_biz/ua/oss/upload_minio',
      filePath: imgItem,
      name: 'file',
      callBack: res => {
        const data = JSON.parse(res).data
        const resourcesVal = '/' + data.dir + data.fileName
        fn(resourcesVal)
      }
    }
    http.upload(params)
  },

  /**
   * 根据地址返回省市区
   * @param {String} address 地址
   * @param {String} name 地名
   * @return {Array} 省市区的信息
   */
  area: async (address, name) => {
    if (!address) return
    const areaInfoArr = []
    let province, city, area // 名字
    let provinceIndex, cityIndex, areaIndex // 所处的索引
    let provinceId, cityId, areaId // areaId
    // 特殊的区域
    if (name.includes('苏滁现代产业园')) {
      province = '安徽省'
      city = '苏滁市'
      area = '苏滁现代产业园'
    } else if (name.includes('保定白沟新城')) {
      province = '河北省'
      city = '保定市'
      area = '保定白沟新城'
    } else if (name.includes('中沙群岛')) {
      province = '海南省'
      city = '三沙市'
      area = '中沙群岛的岛礁及其海域'
    } else {
      const provSuffix = ['省', '自治区', '黑龙江', '北京市', '天津市', '上海市']
      const citySuffix = ['市', '地区', '自治州', '盟']
      const areaSuffix = ['区', '市', '县', '群岛']
      let aIndex = 0
      const common = (arr) => {
        aIndex = 0
        for (let i = 0; i < arr.length; i++) {
          const index = address.indexOf(arr[i])
          if (index !== -1) {
            aIndex = index + arr[i].length
            break
          }
        }
      }
      common(provSuffix)
      if (aIndex === 0) return
      province = address.substring(0, aIndex)
      address = address.substring(aIndex)
      // 直辖市
      if (province === '北京市' || province === '天津市' || province === '上海市') {
        for (const itAddr of ['北京市', '天津市', '上海市']) {
          const inx = address.indexOf(itAddr)
          if (inx !== -1) {
            city = itAddr
            address = address.substring(itAddr.length)
            break
          }
        }
        common(areaSuffix)
        if (aIndex === 0) return
        area = address.substring(0, aIndex)
        city = area.includes('县') ? '县' : '市辖区'
      } else {
        common(citySuffix)
        if (aIndex === 0) return
        city = address.substring(0, aIndex)
        address = address.substring(aIndex)
        common(areaSuffix)
        if (aIndex === 0) return
        area = address.substring(0, aIndex)
      }
    }
    const getListByPid = (pid) => {
      return new Promise((resolve) => {
        const params = {
          url: '/mall4cloud_order/area/list_by_pid',
          method: 'GET',
          data: {
            pid
          }
        }
        http.request(params).then((res) => {
          resolve(res)
        })
      })
    }
    // 省
    // console.log(uni.getStorageSync('cloudProvArray'))
    if (!uni.getStorageSync('cloudProvArray')) {
      const params = {
        url: '/mall4cloud_order/area/list',
        method: 'GET',
        data: {}
      }
      const res = await http.request(params)
      if (res) {
        uni.setStorageSync('cloudProvArray', res)
      }
    }
    if (uni.getStorageSync('cloudProvArray')) {
      const res1 = uni.getStorageSync('cloudProvArray')
      for (let i = 0; i < res1.length; i++) {
        if (province === res1[i].areaName) {
          provinceIndex = i
          provinceId = res1[i].areaId
          break
        }
      }
    } else {
      return []
    }
    // 市
    const res2 = await getListByPid(provinceId)
    for (let i = 0; i < res2.length; i++) {
      if (city === res2[i].areaName) {
        cityIndex = i
        cityId = res2[i].areaId
        break
      }
    }
    // 区
    const res3 = await getListByPid(cityId)
    for (let i = 0; i < res3.length; i++) {
      if (area === res3[i].areaName) {
        areaIndex = i
        areaId = res3[i].areaId
        break
      }
    }
    areaInfoArr.push({
      name: province,
      index: provinceIndex,
      areaId: provinceId
    })
    areaInfoArr.push({
      name: city,
      index: cityIndex,
      areaId: cityId
    })
    areaInfoArr.push({
      name: area,
      index: areaIndex,
      areaId
    })
    return areaInfoArr
  },

  /**
   * 获取网站配置
   */
  getWebConfig: () => {
    const params = {
      url: '/mall4cloud_admin/ua/web_config/get_activity',
      method: 'GET',
      data: {
        webConfigType: 'H5_WEBSITE_CONFIG'
      }
    }
    return new Promise((resolve) => {
      http.request(params).then((res) => {
        if (res) {
          if (res.loginLogoImg) {
            res.loginLogoImg = res.loginLogoImg.indexOf('http') > -1 ? res.loginLogoImg : import.meta.env.VITE_APP_RESOURCES_URL + res.loginLogoImg
          } else {
            res.loginLogoImg = new URL('@/static/logo.png', import.meta.url).href
          }
          res.titleContentCn = res.titleContentCn || '蓝海商城'
          res.titleContentEn = res.titleContentEn || 'Mall4j'
          uni.setStorageSync('cloudUniWebConfigData', res)
          uni.setStorageSync('cloudUniLoginLogoImg', res.loginLogoImg)
        } else {
          uni.removeStorageSync('cloudUniWebConfigData')
        }
        resolve()
      })
    })
  },

  /**
   * 回到首页
   */
  toHomePage: () => {
    const appType = uni.getStorageSync('cloudAppType')
    if (appType === 2 || appType === 4) {
      uni.switchTab({
        url: '/'
      })
    } else {
      uni.switchTab({
        url: '/pages/index/index'
      })
    }
  },

  /**
   * 微信小程序跳转通联小程序
   */
  toAllinPay: (params) => {
    wx.navigateToMiniProgram({
      appId: 'wxc46c6d2eed27ca0a', // 通联小程序 appId
      path: 'pages/merchantAddress/merchantAddress', // 通联小程序页面路径
      extraData: { targetUrl: params.targetUrl }, // 商户拼接的请求报文
      envVersion: 'release', // develop 开发版 trial 体验版 release 正式版
      // 打开成功
      success: params.success,
      // 打开失败
      fail: params.fail,
      // 打开完成(成功或失败都会执行)
      complete: params.complete
    })
  },

  /**
   * 百度地图经纬度转为腾讯地图/高德地图经纬度
   * @param {Number} longitude
   * @param {Number} latitude
   * @returns
   */
  bdMapToTxMap: (longitude, latitude) => {
    // eslint-disable-next-line no-loss-of-precision
    const pi = (3.14159265358979324 * 3000.0) / 180.0
    const x = longitude - 0.0065
    const y = latitude - 0.006
    const z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi)
    const theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi)
    const lngs = z * Math.cos(theta)
    const lats = z * Math.sin(theta)
    return {
      lng: lngs,
      lat: lats
    }
  },

  /**
   * 文件地址校验
   * @param fileUrl 获取到的文件路径
   */
  checkFileUrl: (fileUrl) => {
    // 防止 fileUrl 为null时 indexOf() 方法失效报错
    const url = fileUrl || ''
    const baseUrl = import.meta.env.VITE_APP_RESOURCES_URL
    // 判断 fileUrl 中是否已存在基础路径
    const check = url.indexOf(baseUrl) !== -1
    if (check || !fileUrl) {
      return url
    } else {
      return baseUrl + url
    }
  },

  // 获取用户授权
  getUserAuth: (callBack) => {
    const system = uni.getSystemInfoSync()
    if (system.platform === 'android') {
      // 查询是否已经授权了定位权限
      // eslint-disable-next-line no-undef
      plus.android.requestPermissions(
        ['android.permission.ACCESS_FINE_LOCATION'],
        // 成功回调
        function (resultObj) {
          let result = 0
          for (let i = 0; i < resultObj.granted.length; i++) {
            result = 1
          }
          for (let i = 0; i < resultObj.deniedPresent.length; i++) {
            result = 0
          }
          for (let i = 0; i < resultObj.deniedAlways.length; i++) {
            // 永久拒绝权限，需要到设置页面打开
            result = -1
          }
          if (result === 1) {
            // 授权成功执行回调
            callBack()
          } else if (result === 0) {
            // 本次拒绝了权限，提示用户
            uni.showToast({
              title: $t('index.authorityErrorTip1'),
              icon: 'none'
            })
          } else if (result === -1) {
            uni.showModal({
              title: $t('index.tips'),
              content: $t('index.authorityErrorTip2'),
              showCancel: true, // 是否显示取消按钮
              cancelText: $t('index.cancel'),
              confirmText: $t('index.confirm'),
              success: (res) => {
                if (res.confirm) {
                  // 点击确定
                  // 若所需权限被拒绝,则打开APP设置界面,可以在APP设置界面打开相应权限
                  // eslint-disable-next-line no-undef
                  const Intent = plus.android.importClass('android.content.Intent')
                  // eslint-disable-next-line no-undef
                  const Settings = plus.android.importClass('android.provider.Settings')
                  // eslint-disable-next-line no-undef
                  const Uri = plus.android.importClass('android.net.Uri')
                  // eslint-disable-next-line no-undef
                  const mainActivity = plus.android.runtimeMainActivity()
                  const intent = new Intent()
                  intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                  const uri = Uri.fromParts('package', mainActivity.getPackageName(), null)
                  intent.setData(uri)
                  mainActivity.startActivity(intent)
                }
              }
            })
          }
        },
        // 失败回调
        function (error) {
          uni.showToast({
            title: $t('index.authorityError') + error.code + ' = ' + error.message,
            icon: 'none'
          })
        }
      )
    } else {
      callBack()
    }
  },

  // 生成海报
  getPoster: (pic, price, prodName, code) => {
    return {
      css: {
        width: '600rpx',
        height: '850rpx'

      },
      views: [{
        type: 'view',
        css: {
          width: '600rpx',
          height: '850rpx',
          position: 'fixed',
          backgroundColor: '#fff',
          borderRadius: '24rpx',
          padding: '30rpx',
          boxSizing: 'border-box'
        },
        views: [
          {
            type: 'image',
            // 这个是默认图片，路径错误会导致小程序生成失败
            src: '/static/images/def.png',
            css: {
              width: '540rpx',
              height: '540rpx',
              display: 'block',
              margin: '0 auto'
            }
          },
          {
            type: 'image',
            src: util.checkFileUrl(pic),
            css: {
              width: '540rpx',
              height: '540rpx',
              display: 'block',
              position: 'absolute',
              top: '-30rpx',
              left: '-30rpx'
            }
          },
          {
            type: 'view',
            css: {
              height: '200rpx',
              width: '540rpx',
              marginTop: '50rpx'
            },
            views: [
              {
                type: 'view',
                css: {
                  width: '360rpx',
                  height: '190rpx',
                  borderRight: '1px solid #E9E9EA'
                },
                views: [{
                  type: 'view',
                  css: {
                  },
                  views: [
                    {
                      type: 'text',
                      text: '￥',
                      css: {
                        fontSize: '28rpx',
                        color: '#f81a1a',
                        verticalAlign: 'bottom'
                      }
                    },
                    {
                      type: 'text',
                      text: wxs.parsePrice(price)[0] + '.',
                      css: {
                        fontSize: '40rpx',
                        verticalAlign: 'bottom',
                        lineHeight: '40rpx',
                        color: '#f81a1a'

                      }
                    },
                    {
                      type: 'text',
                      text: wxs.parsePrice(price)[1],
                      css: {
                        fontSize: '28rpx',
                        verticalAlign: 'bottom',
                        color: '#f81a1a'
                      }
                    }
                  ]
                }, {
                  type: 'text',
                  text: prodName,
                  css: {
                    marginTop: '10rpx',
                    fontSize: '28rpx',
                    color: '#333333',
                    display: '-webkit-box',
                    boxOrient: 'vertical',
                    lineClamp: 4,
                    overflow: 'hidden',
                    textOverflow: 'ellipsis'
                  }
                }]
              },
              {
                type: 'view',
                css: {
                  width: '150rpx',
                  height: '150rpx',
                  position: 'absolute',
                  right: '0rpx',
                  bottom: '30rpx'
                },
                views: [{
                  type: 'image',
                  src: code,
                  css: {
                    width: '150rpx',
                    height: '150rpx'
                  }
                }]
              },
              {
                type: 'text',
                text: '长按识别查看',
                css: {
                  fontSize: '20rpx',
                  lineHeight: '30rpx',
                  textAlign: 'center',
                  width: '150rpx',
                  height: '30rpx',
                  position: 'absolute',
                  right: '0rpx',
                  bottom: '0rpx'
                }
              }
            ]
          }]
      }]
    }
  },

  /**
   * 小程序处理base64小程序码
   * @param {*} base64data
   */
  base64src: (base64data) => {
    const fsm = uni.getFileSystemManager()
    const FILE_BASE_NAME = 'tmp_base64src' + Math.random() * 1000000000000
    return new Promise((resolve, reject) => {
      const format = 'jpg'
      const bodyData = base64data
      if (!format) {
        reject(new Error('ERROR_BASE64SRC_PARSE'))
      }
      const filePath = `${wx.env.USER_DATA_PATH}/${FILE_BASE_NAME}.${format}`
      const buffer = uni.base64ToArrayBuffer(bodyData)
      fsm.writeFile({
        filePath,
        data: buffer,
        encoding: 'binary',
        success () {
          resolve(filePath)
        },
        fail () {
          reject(new Error('ERROR_BASE64SRC_WRITE'))
        }
      })
    })
  },
  /***
   * 获取用户交易单号
   * @param {String/Number} orderNumber 订单编号
   */
  getTransactionId: (orderNumber) => {
    return new Promise(resolve => {
      http.request({
        url: `/mall4cloud_order/my_order/check_trade_managed/${orderNumber}`,
        method: 'GET'
      }).then(res => {
        // res.orderStatus 订单状态枚举:(1)待发货，(2) 已发货，(3)确认收货;(4)交易完成(5) 已退款
        // res.bizPayNo 支付单号
        // 接口返回订单号且状态为(2) 已发货时，需要在小程序端确认收货，否则可以正常收货
        if (res.bizPayNo && res.orderStatus === 2) {
          resolve(res.bizPayNo)
        } else {
          resolve(false)
        }
      })
    })
  },
  /**
   * 订单确认收货处理
   * @param {String/Number} orderNumber 订单编号
   */
  orderReceiptCheck: async (orderNumber) => {
    const transactionId = await util.getTransactionId(orderNumber)
    if (transactionId) {
      // #ifndef MP-WEIXIN
      uni.showToast({
        title: '请前往小程序端确认收货！',
        icon: 'none'
      })
      // #endif
      // #ifdef MP-WEIXIN
      // 调用微信确认收货组件
      if (wx.openBusinessView) {
        // 缓存当前操作的订单号
        uni.setStorageSync('cloudConfirmOrderNumber', orderNumber)
        wx.openBusinessView({
          businessType: 'weappOrderConfirm',
          extraData: {
            transaction_id: transactionId,
            merchant_id: '',
            merchant_trade_no: orderNumber
          },
          success: () => {
            // res.status： "success":用户确认收货成功; "fail":用户确认收货失败; "cancel":用户取消
          },
          fail: (failMsg) => {
            // eslint-disable-next-line no-console
            console.log('唤起确认收货组件失败：', failMsg)
          }
        })
      } else {
        // 引导用户升级微信版本
        uni.showToast({
          title: '微信版本过低，请升级后重试',
          icon: 'none'
        })
      }
      // #endif
    }
    return Boolean(transactionId)
  },

  /**
   * 订单确认收货
   */
  confirmReceipt: orderNumber => {
    http.request({
      url: `/mall4cloud_order/my_order/receipt/${orderNumber}`,
      method: 'PUT'
    }).then(() => {
      uni.removeStorageSync('cloudConfirmOrderNumber')
    })
  },
  gotoLiveRoom (type, roomId, liveUserId) {
    util.videoInteract()
    let pageUrl = '/package-activities/pages/live-room/live-room'
    // #ifdef MP-WEIXIN
    if (!(getApp().globalData.useLivePlayer || getApp().globalData.livePlayType === 3)) {
      pageUrl = '/package-activities/pages/wx-player/wx-player'
    }
    // #endif
    uni.navigateTo({
      url: `${pageUrl}?liveType=${type}&roomId=${roomId}&liveUserId=${liveUserId}`
    })
  },
  videoInteract () {
    // #ifdef H5
    if (getApp().globalData.$isVideoInteract) return
    const audioObj = uni.createInnerAudioContext()
    audioObj.play()
    audioObj.stop()
    audioObj.destroy()
    getApp().globalData.$isVideoInteract = true
    // #endif
  },
  awsUpload (ossList, files) {
    return new Promise((resolve) => {
      ossList.forEach((item, index) => {
        // #ifdef APP-PLUS
        util.getArrayBuffer(files[index].path).then((buffer) => {
          const params = {
            uploadUrl: item.preSignUrl,
            method: 'put',
            data: buffer
          }
          http.request(params).then(() => {
            uni.hideLoading()
            resolve()
          })
        })
        // #endif

        // #ifdef H5
        uni.request({
          url: files[index].path,
          method: 'GET',
          responseType: 'arraybuffer',
          success: function (requestRes) {
            const params = {
              uploadUrl: item.preSignUrl,
              method: 'put',
              data: requestRes.data
            }
            http.request(params).then(() => {
              uni.hideLoading()
              resolve()
            })
          }
        })
        // #endif
        // #ifdef MP-WEIXIN
        uni.getFileSystemManager().readFile({
          filePath: files[0].path,
          success: function (requestRes) {
            const params = {
              uploadUrl: item.preSignUrl,
              method: 'put',
              data: requestRes.data
            }
            http.request(params).then(() => {
              uni.hideLoading()
              resolve()
            })
          }
        })
        // #endif
      })
    })
  },
  getArrayBuffer: (filePath) => {
    return new Promise((resolve) => {
      plus.io.resolveLocalFileSystemURL(filePath, async function (entry) {
        entry.file(function (file) { // 读取文件
          const reader = new plus.io.FileReader()
          reader.readAsDataURL(file) // 以URL格式读取文件
          reader.onload = function () {
            const base64 = this.result.split(',')[1] // 获取base64字符串
            const buffer = uni.base64ToArrayBuffer(base64) // 转换为arrayBuffer格式
            resolve(buffer)
          }
        })
      })
    })
  }
}

export default util
