var config = require('./config.js') // 统一的网络请求方法
import {
  AppType
} from './constant.js'

function request(params) {
  const url = params.uploadUrl ? params.uploadUrl : (params.domain ? params.domain : config.domain) + params.url
  let head = []
  head = {
    'Authorization': uni.getStorageSync('token'),
    'locale': uni.getStorageSync('lang') || 'zh_CN'
  }
  if (params.header) {
    console.log('ContentType：', params.header)
    head['Content-Type'] = params.header.ContentType
    head['Accept'] = 'application/json'
  }
  uni.request({
    url: url,
    // 接口请求地址
    data: params.data,
    header: head,
    method: params.method === undefined ? 'POST' : params.method,
    dataType: 'json',
    responseType: params.responseType === undefined ? 'text' : params.responseType,
    success: function(res) {
      if (params.uploadUrl && params.header && res.statusCode === 200) {
        if (params.callBack) {
          params.callBack(res.data)
        }
        return
      }
      if (res.statusCode !== 200) {
        uni.showToast({
          title: '服务器出了点小差~',
          icon: 'none'
        })
        return
      }
      var responseData = res.data
      if (responseData.code === '00000') {
        if (params.callBack) {
          params.callBack(responseData.data)
        }
        return
      }
      // A00001 用于直接显示提示用户的错误，内容由输入内容决定
      // A00003 无法读取获取请求参数
      // A00005 服务器出了点小差
      if (responseData.code === 'A00001' || responseData.code === 'A00003') {
        uni.showToast({
          title: responseData.msg || 'Error',
          icon: 'none'
        })
        if (params.errCallBack) {
          params.errCallBack(responseData)
        }
        return
      }

      if (responseData.code === 'A00005') {
        uni.showToast({
          title: '服务器出了点小差~',
          icon: 'none'
        })
        if (params.errCallBack) {
          params.errCallBack(responseData)
        }
        return
      }

      // A00002 方法参数没有校验，内容由输入内容决定
      if (responseData.code === 'A00002') {
        uni.showToast({
          title: responseData.msg || 'Error',
          icon: 'none'
        })
        if (params.errCallBack) {
          params.errCallBack(responseData)
        }
        return
      }

      // A00002 方法参数没有校验，内容由输入内容决定
      if (responseData.code === 'A00006') {
        if (responseData.data && responseData.data.length) {
          responseData.data.forEach(errorMsg => {
            uni.showToast({
              title: errorMsg || 'Error',
              icon: 'none'
            })
          })
        } else {
          uni.showToast({
            title: responseData.msg || 'Error',
            icon: 'none'
          })
        }
        if (params.errCallBack) {
          params.errCallBack(responseData)
        }
        return
      }

      // A00004 未授权
      if (responseData.code === 'A00004') {
        uni.removeStorageSync('token')
        // #ifdef H5
        const ua = navigator.userAgent.toLowerCase()
        if (ua.search(/MicroMessenger/i) > -1) uni.setStorageSync('appType', AppType.MP)
        // #endif
        uni.hideLoading()
        // to re-login
        uni.showModal({
          title: '提示',
          content: '登录已过期，请重新登录',
          success: modalRes => {
            if (modalRes.confirm) {
              // 跳转登录页面
              let url = url = '/pages/login/login'

              uni.navigateTo({
                url: url
              })
            } else if (modalRes.cancel) {
              uni.switchTab({
                url: '/pages/index/index'
              })
            }
          }
        })
        if (params.errCallBack) {
          params.errCallBack(responseData)
        }
        return
      }
      if (params.errCallBack) {
        params.errCallBack(responseData)
      }
    },
    fail: function() {
      uni.hideLoading()
      setTimeout(() => {
        uni.showToast({
          title: '服务器打了个盹~',
          icon: 'none'
        })
      }, 1)
    }
  })
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
 * 上传图片
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
    responseType: params.responseType === undefined ? 'json' : params.responseType,
    success: (res) => {
      // 如果有定义了params.callBack，则调用 params.callBack(res.data)
      if (params.callBack) {
        params.callBack(res.data)
      }
    },
    fail: (err) => {
      console.log(err)
      uni.hideLoading()
    }
  })
}

exports.request = request
exports.mpAuthLogin = mpAuthLogin
exports.upload = upload
