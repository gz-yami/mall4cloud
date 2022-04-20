var http = require('./http.js')
import {
  AppType
} from '../utils/constant.js'
const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()
  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

const formatHtml = content => {
  if (!content) {
    return ''
  }
  content = content.replace(/\<p/gi, '<p style="max-width:100% !important;word-wrap:break-word;word-break:break-word;" ')
  content = content.replace(/\<img/gi, '<img style="width:100% !important;height:auto !important;margin:0;display:flex;" ')
  content = content.replace(/style="/gi, 'style="max-width:100% !important;table-layout:fixed;word-wrap:break-word;word-break:break-word;')
  content = content.replace(/\<table/gi, '<table style="table-layout:fixed;word-wrap:break-word;word-break:break-word;" ')
  content = content.replace(/\<td/gi, '<td cellspacing="0" cellpadding="0" border="0" style="display:block;vertical-align:top;margin: 0px; padding: 0px; border: 0px;outline-width:0px;"')
  content = content.replace(/width=/gi, 'sss=')
  content = content.replace(/height=/gi, 'sss=')
  content = content.replace(/ \/\>/gi, ' style="max-width:100% !important;height:auto !important;margin:0;display:block;" \/\>')
  return content
}

const endOfStartTime = (startTime, endTime) => {
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
      day: `${timeFormat(day)}`,
      hou: `${timeFormat(hou)}`,
      min: `${timeFormat(min)}`,
      sec: `${timeFormat(sec)}`,
      signs: 1
    }
  }

  return result
} // 小于10的格式化函数

const timeFormat = times => {
  return times < 10 ? '0' + times : times
}

const dateToTimestamp = dateStr => {
  if (!dateStr) {
    return ''
  }

  const newDataStr = dateStr.replace(/\.|\-/g, '/')
  const date = new Date(newDataStr)
  const timestamp = date.getTime()
  return timestamp
}

// 检查是否授权
const checkAuthInfo = fn => {
  uni.hideLoading()
  if (uni.getStorageSync('loginResult').userId) {
    fn()
  } else { // 没有userId
    // #ifdef H5 || MP-WEIXIN
    if (uni.getStorageSync('appType') == AppType.MP || uni.getStorageSync('appType') == AppType.MINI) {
      uni.navigateTo({
        url: '/pages/login/login'
      })
    } else {
      uni.navigateTo({
        url: '/pages/accountLogin/accountLogin'
      })
    }
    // #endif
    // #ifdef APP-PLUS
    uni.navigateTo({
      url: '/pages/accountLogin/accountLogin'
    })
    // #endif
  }
}

// 函数参数必须是字符串，因为二代身份证号码是十八位，而在javascript中，十八位的数值会超出计算范围，造成不精确的结果，导致最后两位和计算的值不一致，从而该函数出现错误。
// 详情查看javascript的数值范围
function checkIDCard(idcode) {
  // 加权因子
  var weight_factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
  // 校验码
  var check_code = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2']

  var code = idcode + ''
  var last = idcode[17]// 最后一位

  var seventeen = code.substring(0, 17)

  // ISO 7064:1983.MOD 11-2
  // 判断最后一位校验码是否正确
  var arr = seventeen.split('')
  var len = arr.length
  var num = 0
  for (var i = 0; i < len; i++) {
    num = num + arr[i] * weight_factor[i]
  }

  // 获取余数
  var resisue = num % 11
  var last_no = check_code[resisue]

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
  var idcard_patter = /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X])$/

  // 判断格式是否正确
  var format = idcard_patter.test(idcode)

  // 返回验证结果，校验码和格式同时正确才算是合法的身份证号码
  return !!(last === last_no && format)
}

/**
 * 手机号正则校验
 */
const checkPhoneNumber = (phoneNumber) => {
  var regexp = /^[1]([3-9])[0-9]{9}$/
  return regexp.test(phoneNumber)
}
/**
 * 邮箱正则校验
 */
const checkEmail = (email) => {
  var regexp = /^(\w+((-\w+)|(\.\w+))*)\+\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$ /
  return regexp.test(email)
}

/**
 * 用户名正则校验
 */
const checkUserName = (userName) => {
  var regexp = /^([a-zA-Z0-9_]{4,16})$/
  return regexp.test(userName)
}
/**
 * 获取链接上的参数
 */
const getUrlKey = (name) => {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || ['', ''])[1]
    .replace(/\+/g, '%20')) || null
}

/**
 * 处理参数
 * @param {obj}  to: 目标页面路由信息
 */
const getPageInfo = (to) => {
  var path = to.path
  var query = to.query
  var pageInfo = {}
  if (path == '/pages/index/index' || path == '') {
    pageInfo.pageId = 1
  } else if (path == '/pages/prod/prod' && !query.seckillId && !query.groupActivityId) {
    pageInfo.pageId = query.bannerEnter == '1' ? 2 : 3
    pageInfo.bizType = 0
    pageInfo.bizData = query.prodid
  } else if (path == '/pages/snapUpDetail/snapUpDetail' && query.seckillId) {
    pageInfo.pageId = 4
    pageInfo.bizType = 2
    pageInfo.bizData = query.seckillId
  } else if (path == '/pages/prod/prod' && query.groupActivityId) {
    pageInfo.pageId = 5
    pageInfo.bizType = 1
    pageInfo.bizData = query.prodid
  } else if (path == '/pages/category/category') {
    pageInfo.pageId = 6
  } else if (path == '/pages/specialDiscount/specialDiscount') {
    pageInfo.pageId = 7
  } else if (path == '/pages/basket/basket') {
    pageInfo.pageId = 8
  } else if (path == '/pages/order-detail/order-detail') {
    pageInfo.pageId = 9
  } else if (path == '/pages/payWay/payWay') {
    pageInfo.pageId = 10
    pageInfo.bizData = query.orderIds
  } else if (path == '/pages/pay-result/pay-result') {
    pageInfo.pageId = 11
  }	else if (path == '/pages/user/user') {
    pageInfo.pageId = 12
  } else if (path == '/pages/orderList/orderList') {
    pageInfo.pageId = 13
  }
  return pageInfo
}

/**
 * 记录页面点击次数
 */
const tapLog = (visitType, orderIds) => {
  // 更新会话时间
  uni.setStorageSync('sessionTimeStamp', new Date().getTime())
  var flowAnalysisLogDto = uni.getStorageSync('flowAnalysisLogDto')

  if (visitType) {
    flowAnalysisLogDto.visitType = visitType
  } else {
    visitType = 3
  }
  // 支付页面单独处理
  if (orderIds) {
    flowAnalysisLogDto.pageId = 10
    flowAnalysisLogDto.orderIds = orderIds
  }
  http.saveLog(flowAnalysisLogDto, visitType)
}

/**
 * 获取唯一值
 */
const getUuid = () => {
  var s = []
  var hexDigits = '0123456789abcdef'
  for (var i = 0; i < 36; i++) {
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
  }
  s[14] = '4' // bits 12-15 of the time_hi_and_version field to 0010
  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
  s[8] = s[13] = s[18] = s[23] = '-'

  var uuid = s.join('')
  return uuid
}

/**
 *	时间戳与当前时间比较，是否为同一天
 */
const checkSameDay = (timestamp) => {
  var nt = new Date(new Date().getTime())
  var ot = new Date(timestamp)
  return ot.getFullYear() + ot.getMonth() + ot.getDate() == nt.getFullYear() + nt.getMonth() + nt.getDate()
}

/**
 * 函数节流
 * @param fn
 * @param wait
 * @returns {Function}
 * @constructor
 */
const throttle = (fn, wait) => {
  var timer = null
  return function() {
    var context = this
    var args = arguments
    if (!timer) {
      timer = setTimeout(function() {
        fn.apply(context, args)
        timer = null
      }, wait)
    }
  }
}

/**
 * 时间戳转化为年 月 日 时 分 秒
 * number: 传入时间戳
 * format：返回格式，支持自定义，但参数必须与formateArr里保持一致
*/
const tsToDate = (number, format) => {
  var formateArr = ['Y', 'M', 'D', 'h', 'm', 's']
  var returnArr = []

  var date = new Date(number)
  returnArr.push(date.getFullYear())
  returnArr.push(date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1)
  returnArr.push(date.getDate() < 10 ? '0' + date.getDate() : date.getDate())

  returnArr.push(date.getHours() < 10 ? '0' + date.getHours() : date.getHours())
  returnArr.push(date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes())
  returnArr.push(date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds())

  for (var i in returnArr) {
    format = format.replace(formateArr[i], returnArr[i])
  }
  return format
}

/**
 * 移除购物车Tabbar的数字
 */
const removeTabBadge = () => {
  let pl = ''
  // #ifdef MP-WEIXIN
  pl = 'mp'
  // #endif
  uni.removeTabBarBadge({
    index: pl == 'mp' ? 3 : 2
  })
}

module.exports = {
  getUrlKey: getUrlKey,
  dateToTimestamp: dateToTimestamp,
  formatTime: formatTime,
  formatHtml: formatHtml,
  endOfStartTime: endOfStartTime,
  checkAuthInfo: checkAuthInfo,
  checkPhoneNumber: checkPhoneNumber,
  checkEmail: checkEmail,
  checkUserName: checkUserName,
  throttle: throttle,
  getPageInfo: getPageInfo,
  tapLog: tapLog,
  getUuid: getUuid,
  checkSameDay: checkSameDay,
  tsToDate: tsToDate,
  checkIDCard: checkIDCard,
  removeTabBadge: removeTabBadge
}
