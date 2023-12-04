/*
* Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
*
* https://www.mall4j.com/
*
* 未经允许，不可做商业用途！
*
* 版权所有，侵权必究！
*/
import Cookies from 'vue-cookies'

const TokenKey = 'Luck-platform-Token'

export function getToken () {
  return Cookies.get(TokenKey)
}

export function setToken (token) {
  return Cookies.set(TokenKey, token, { sameSite: 'Lax' })
}

export function removeToken () {
  return Cookies.remove(TokenKey)
}
