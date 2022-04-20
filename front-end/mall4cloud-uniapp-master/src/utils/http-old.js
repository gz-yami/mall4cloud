var config = require('./config.js') // 统一的网络请求方法
var util = require('./util.js')
import {
  AppType
} from './constant.js'

function request(params, isGetTonken) {
  if (Object.prototype.toString.call(params.data) == '[object Array]') {
    params.data = JSON.stringify(params.data)
  } else if (Object.prototype.toString.call(params.data) == '[object Number]') {
    params.data = params.data + ''
  }
  var needToken = false
  if (params.url.indexOf('/p/') == 0 || params.url.indexOf('/user/registerOrBindUser') == 0) {
    needToken = true
  }
  uni.request({
    url: (params.domain ? params.domain : config.domain) + params.url,
    // 接口请求地址
    data: params.data,
    header: {
      'Authorization': !needToken ? undefined : uni.getStorageSync('token') || uni.getStorageSync('tempToken'),
      'locale': uni.getStorageSync('lang') || 'zh_CN'
    },
    method: params.method == undefined ? 'POST' : params.method,
    dataType: 'json',
    responseType: params.responseType == undefined ? 'text' : params.responseType,
    success: function(res) {
      if (res.statusCode == 200) {
        // 如果有定义了params.callBack，则调用 params.callBack(res.data)
        if (params.callBack) {
          params.callBack(res.data)
        }
      } else if (res.statusCode == 500) {
        uni.hideLoading()
        setTimeout(() => {
          uni.showToast({
            title: '服务器出了点小差~',
            icon: 'none'
          })
        }, 1)
      } else if (res.statusCode == 401) {
        uni.removeStorageSync('loginResult')
        uni.removeStorageSync('token')
        uni.removeStorageSync('hadBindUser')
        // #ifdef H5
        const ua = navigator.userAgent.toLowerCase()
        if (ua.search(/MicroMessenger/i) > -1) uni.setStorageSync('appType', AppType.MP)
        // #endif
        uni.hideLoading()
        if (!params.dontTrunLogin) {
          if (uni.getStorageSync('hadLogin')) {
            uni.showModal({
              title: '提示',
              content: '登录已过期，请重新登录',
              success: res => {
                if (res.confirm) {
                  // 跳转登录页面
                  var url = ''
                  // #ifdef H5 || MP-WEIXIN
                  if (uni.getStorageSync('appType') == AppType.MP || uni.getStorageSync('appType') == AppType.MINI) {
                    url = '/pages/login/login'
                  } else {
                    url = '/pages/accountLogin/accountLogin'
                  }
                  // #endif

                  // #ifdef APP-PLUS
                  url = '/pages/accountLogin/accountLogin'
                  // #endif
                  uni.navigateTo({
                    url: url
                  })
                } else {
                  uni.switchTab({
                    url: '/pages/index/index'
                  })
                }
              }
            })
          } else {
            // 跳转登录页面
            // #ifdef H5
            uni.navigateTo({
              url: uni.getStorageSync('appType') == AppType.MP ? '/pages/login/login' : '/pages/accountLogin/accountLogin'
            })
            // #endif

            // #ifdef MP-WEIXIN
            uni.navigateTo({
              url: '/pages/login/login'
            })
            // #endif
          }
        }
      } else if (res.statusCode == 400 && !params.errCallBack) {
        uni.hideLoading()
        setTimeout(() => {
          uni.showToast({
            title: res.data,
            icon: 'none'
          })
        }, 1)
      } else {
        // 如果有定义了params.errCallBack，则调用 params.errCallBack(res.data)
        if (params.errCallBack) {
          params.errCallBack(res)
        }
        uni.hideLoading()
      }
    },
    fail: function() {
      uni.hideLoading()
      setTimeout(() => {
        uni.showToast({
          title: '服务器出了点小差~',
          icon: 'none'
        })
      }, 1)
    }
  })
}

/**
 * 上传文件统一接口
 */
function upload(params) {
  uni.uploadFile({
    url: config.domain + params.url,
    filePath: params.filePath,
    name: params.name,
    header: {
      'Authorization': params.login ? undefined : uni.getStorageSync('token')
    },
    dataType: 'json',
    responseType: params.responseType == undefined ? 'json' : params.responseType,
    success: function(res) {
      if (res.statusCode == 200) {
        // 如果有定义了params.callBack，则调用 params.callBack(res.data)
        if (params.callBack) {
          params.callBack(res.data)
        }
      } else {
        uni.showToast({
          title: '服务器出了点小差~',
          icon: 'none'
        })
      }
    },
    fail: function() {
      uni.hideLoading()
    }
  })
}

/**
 * 微信公众号登录
 * @param {Object} fn  登录成功回调
 * @param {String} code  微信授权返回的code, 用于登录
 */
var mpLogin = function(fn, code) {
  // 发送 res.code 到后台换取 openId, sessionKey, unionId
  request({
    login: true,
    url: '/appLogin',
    data: {
      principal: code,
      appType: AppType.MP // 登录类型
    },
    callBack: result => {
      loginSuccess(result, fn)
    }
  }, true)
}

var getToken = function(fn) {

}

/**
 * 登录成功后执行
 * @param {Object} result  登录成功返回的数据
 * @param {Object} fn		登录成功后的回调
 */
function loginSuccess(result, fn) {
  // 保存登陆信息
  uni.setStorageSync('loginResult', result)
  // 保存成功登录标识,token过期判断
  uni.setStorageSync('hadLogin', true)
  // 没有获取到用户昵称，说明服务器没有保存用户的昵称，也就是用户授权的信息并没有传到服务器
  // if (!result.pic) {
  // 	updateUserInfo();
  // }
  if (!result.enabled) {
    uni.showModal({
      showCancel: false,
      title: '提示',
      content: '您已被禁用，不能购买，请联系客服'
    })
    uni.setStorageSync('token', '')
  } else {
    uni.setStorageSync('token', 'bearer' + result.access_token) // 把token存入缓存，请求接口数据时要用
  }
  if (result.userId) {
    uni.setStorageSync('hadBindUser', true)
    getCartCount()
  }
  // var globalData = getApp().globalData;
  // globalData.isLanding = false;
  // while (globalData.requestQueue.length) {
  // 	request(globalData.requestQueue.pop());
  // }

  if (fn) {
    fn()
  }
}

// 更新用户头像昵称
function updateUserInfo() {
  uni.getUserInfo({
    success: res => {
      var userInfo = JSON.parse(res.rawData)
      request({
        url: '/p/user/setUserInfo',
        method: 'PUT',
        data: {
          avatarUrl: userInfo.avatarUrl,
          nickName: userInfo.nickName
        }
      })
    }
  })
}

/**
 * 获取购物车商品数量
 */
function getCartCount() {
  if (!uni.getStorageSync('token')) {
    util.removeTabBadge()
    return
  }
  var params = {
    url: '/p/shopCart/prodCount',
    method: 'GET',
    dontTrunLogin: true,
    data: {},
    callBack: function(res) {
      if (res > 0) {
        let pl = ''
        // #ifdef MP-WEIXIN
        pl = 'mp'
        // #endif
        uni.setTabBarBadge({
          index: pl == 'mp' ? 3 : 2,
          text: res > 99 ? '99+' : res + ''
        })
        getApp().globalData.totalCartCount = res
      } else {
        util.removeTabBadge()
        getApp().globalData.totalCartCount = 0
      }
    }
  }
  request(params)
}

function mpAuthLogin(page, needCode) {
  // 在微信环境打开,请求公众号网页登陆
  var redirectUrl = null

  if (!page) {
    redirectUrl = window.location.href
  } else {
    var {
      protocol,
      host
    } = window.location
    redirectUrl = `${protocol}//${host}` + page
  }
  var scope = 'snsapi_userinfo'
  window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + config.mpAppId +
		'&redirect_uri=' +
		encodeURIComponent(redirectUrl) + '&response_type=code&scope=' + scope + '&state=' + (needCode ? 'needCode'
    : 'unNeedCode') +
		'#wechat_redirect'
}

/**
 * 用户操作记录
 */
var saveLog = function(flowData, visitType) {
  var flowAnalysisLogDto = Object.assign(flowData)
  flowAnalysisLogDto.visitType = visitType
  var params = {
    url: '/flowAnalysisLog',
    method: 'POST',
    data: flowAnalysisLogDto,
    callBack: () => {
      // console.log(params.data)
    }
  }
  request(params)
}

exports.getToken = getToken
exports.mpLogin = mpLogin
exports.request = request
exports.getCartCount = getCartCount
exports.updateUserInfo = updateUserInfo
exports.upload = upload
exports.mpAuthLogin = mpAuthLogin
exports.loginSuccess = loginSuccess
exports.saveLog = saveLog
