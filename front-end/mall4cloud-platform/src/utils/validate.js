/*
* Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
*
* https://www.mall4j.com/
*
* 未经允许，不可做商业用途！
*
* 版权所有，侵权必究！
*/
/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * 以1开头，后面跟10位数
 */
export const MOBILE_REGEXP = /1[0-9]{10}/

/**
 * 1. 用户名不能为纯数字
 * 2. 由字母、数字或下划线 4-16位组成
 */
export const USER_NAME_REGEXP = /^(?!\d+$)[a-zA-Z0-9_]{4,16}$/

/**
 * 1. 密码不能为纯数字
 * 2. 由字母、数字或下划线 4-16位组成
 */
export const PASSWORD_REGEXP = /^(?!\d+$)([a-zA-Z0-9_]{4,16})/

/**
 * 邮箱正则
 */
export const EMAIL_REGEXP = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/

/**
 * 统一社会信用代码（15/18/20位，宽松匹配）
 */
export const UNIFIED_SOCIAL_CREDIT_CODE = /^(([0-9A-Za-z]{15})|([0-9A-Za-z]{18})|([0-9A-Za-z]{20}))$/

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername (str) {
  return isEmail(str) || isMobile(str) || isUserName(str)
}

/**
 * 是否是手机号
 * @param value 输入值
 * @return 匹配结果
 */
export function isMobile (value) {
  return isMatching(MOBILE_REGEXP, value)
}

/**
 * 是否是用户名
 * @param value 输入值
 * @return 匹配结果
 */
export function isUserName (value) {
  return isMatching(USER_NAME_REGEXP, value)
}

/**
 * 是否是邮箱
 * @param value 输入值
 * @return 匹配结果
 */
export function isEmail (value) {
  return isMatching(EMAIL_REGEXP, value)
}

/**
 * 统一社会信用代码
 * @param value 输入值
 * @returns 匹配结果
 */
export function isCreditCode (value) {
  return isMatching(UNIFIED_SOCIAL_CREDIT_CODE, value)
}

export function isMatching (regexp, value) {
  if (!value) {
    return false
  }
  return regexp.test(value)
}

/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validURL (url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validLowerCase (str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUpperCase (str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validAlphabets (str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail (email) {
  const reg = /^(([^<>()[]\\.,;:\s@"]+(\.[^<>()[]\\.,;:\s@"]+)*)|(".+"))@(([[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString (str) {
  return typeof str === 'string' || str instanceof String
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray (arg) {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]'
  }
  return Array.isArray(arg)
}

/**
 * 密码 6-16位
 * @param {Array} password
 * @returns {Boolean}
 */
export function validPassword (password) {
  return password.length >= 6 && password.length <= 16
}

/**
 * 判断是否全为空格 只要有一个其他字符返回false
 * @param {String} str
 * @returns {Boolean}
 */
export function validNoEmptySpace (str) {
  const reg = /^\s+$/g
  return reg.test(str)
}

/**
 * 判断富文本是否为全空格
 * @param {String} str
 * @returns
 */
export function isHtmlNull (str) {
  const html = str.replace(/<(?!img).*?>/g, '').replace(/&nbsp;/gi, '').replace(/(\n)/g, '')
  if (html === '') return true
  const regu = '^[ ]+$'
  const re = new RegExp(regu)
  return re.test(html)
}

/**
 * 匹配两侧空格，匹配到就返回true
 * @param {String} str
 * @returns {Boolean}
 */
export function noSpacesBothSides (str) {
  const reg = /(^\s)|(\s$)/g
  return reg.test(str)
}

/**
 * 判断身份证是否合法
 * @param {str} idcode 函数参数必须是字符串，因为二代身份证号码是十八位，而在javascript中，十八位的数值会超出计算范围，造成不精确的结果，导致最后两位和计算的值不一致，从而该函数出现错误。
 * 详情查看javascript的数值范围
 * @returns {Boolean}
 */
export function checkIDCard (idcode) {
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
}
