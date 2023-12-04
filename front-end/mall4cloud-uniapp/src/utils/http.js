/* eslint-disable no-console */
// 全局请求封装
let loadingTimer, isShowLoad
const http = {
  // eslint-disable-next-line max-lines-per-function
  request: (params) => {
    // #ifdef APP-PLUS
    let networkType = ''
    uni.getNetworkType({
      success: (res) => {
        networkType = res.networkType
      }
    })
    if (networkType === 'none' && !uni.getStorageSync('cloudWatchingNetworkFlag')) {
      // 开启网络监听
      uni.onNetworkStatusChange(networkCallback)
      return
    } else {
      // 关闭网络监听
      uni.offNetworkStatusChange(networkCallback)
    }
    // #endif
    // eslint-disable-next-line max-lines-per-function
    return new Promise((resolve, reject) => {
      const globalData = getApp().globalData
      // 默认全局 loading
      // 如需自定义请求loading, 将 params.selfLoading 设置为 true;  (e.g 提交秒杀订单)
      if (globalData.currentReqCounts === 0 && !params.selfLoading) {
        clearTimeout(loadingTimer)
        loadingTimer = setTimeout(() => {
          isShowLoad = true
          uni.showLoading()
        }, 500)
      }
      globalData.currentReqCounts++
      const url = params.uploadUrl ? params.uploadUrl : (params.domain ? params.domain : import.meta.env.VITE_APP_BASE_API) + params.url
      let head = []
      params.uploadUrl ? head = {
        locale: uni.getStorageSync('cloudLang') || 'zh_CN'
      } : head = {
        Authorization: uni.getStorageSync('cloudToken'),
        locale: uni.getStorageSync('cloudLang') || 'zh_CN'
      }
      if (params.header) {
        head['Content-Type'] = params.header.ContentType
      }
      // 如果在进行刷新token, 将其他请求存储在队列中
      if (!params.isRefreshing && getApp().globalData.isLanding) {
        globalData.requestQueue.push(params)
        return
      }
      if (Object.prototype.toString.call(params.data) === '[object Array]') {
        params.data = JSON.stringify(params.data)
      } else if (Object.prototype.toString.call(params.data) === '[object Number]') {
        params.data = params.data + ''
      }
      uni.request({
        url,
        // 接口请求地址
        data: params.data,
        header: head,
        method: params.method === undefined ? 'POST' : params.method,
        dataType: 'json',
        responseType: params.responseType === undefined ? 'text' : params.responseType,
        success: function (res) {
          // 隐藏loading加载，放在最前面，防止真机小程序showToast显示时间太短问题
          http.hideLoad(globalData)
          if (params.uploadUrl && params.header && res.statusCode === 200) {
            return resolve(res.data)
          }
          if (res.statusCode === 404) {
            uni.showToast({
              title: '无法找到请求地址',
              icon: 'none'
            })
            return
          }
          if (res.statusCode !== 200) {
            uni.showToast({
              title: '服务器出了点小差~',
              icon: 'none'
            })
            return
          }

          const responseData = res.data
          // 仅返回结果
          if (!responseData.success && !responseData.code && !responseData.data) {
            return resolve(responseData)
          }
          if (responseData.code === '00000') {
            return resolve(responseData.data)
          }
          // A00001 用于直接显示提示用户的错误，内容由输入内容决定
          // A00003 无法读取获取请求参数
          // A00005 服务器出了点小差
          // A00011 敏感词报错
          if (responseData.code === 'A00001' || responseData.code === 'A00003' || responseData.code === 'A00011') {
            if (!params.withoutPrompting) {
              // withoutPrompting参数为true时，不显示错误提示
              uni.showToast({
                title: responseData.msg || 'Error',
                icon: 'none'
              })
            }
            return reject(responseData)
          }

          if (responseData.code === 'A00005') {
            console.error('============== 请求异常 ==============')
            console.log('接口: ', params.url)
            console.log('异常信息: ', responseData)
            console.error('============== 请求异常 ==============')
            uni.showToast({
              title: '服务器出了点小差~',
              icon: 'none'
            })
            return reject(responseData)
          }
          if (responseData.code === 'A03020') {
            return reject(responseData)
          }
          // A00002 方法参数没有校验，内容由输入内容决定
          if (responseData.code === 'A00002') {
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
            return reject(responseData)
          }

          // A00004 未授权
          if (responseData.code === 'A00004') {
            const pages = getCurrentPages()
            if (pages.length > 0) uni.setStorageSync('cloudRouteUrlAfterLogin', pages[pages.length - 1].$page.fullPath)
            uni.removeStorageSync('cloudToken')
            uni.removeStorageSync('cloudToken')
            uni.removeStorageSync('cloudLoginResult')
            uni.removeStorageSync('cloudExpiresTimeStamp')
            uni.removeStorageSync('cloudDeliveryAddrInfo')
            // #ifdef H5
            const ua = navigator.userAgent.toLowerCase()
            if (ua.search(/MicroMessenger/i) > -1) {
              uni.setStorageSync('cloudAppType', AppType.MP)
            }
            // #endif
            if (!params.dontShowLogin) {
              // to re-login
              uni.showModal({
                title: '提示',
                content: '登录已过期，请重新登录',
                success: modalRes => {
                  if (modalRes.confirm) {
                    // 跳转登录页面
                    uni.navigateTo({
                      url: '/pages/login/login'
                    })
                  } else if (modalRes.cancel) {
                    uni.switchTab({
                      url: '/pages/index/index'
                    })
                  }
                }
              })
            }
          }

          // A00008 token过期
          if (responseData.code === 'A00008') {
            uni.removeStorageSync('cloudToken')
          }
          return reject(responseData)
        },
        fail: (err) => {
          // 隐藏loading加载，放在最前面，防止真机小程序showToast显示时间太短问题
          http.hideLoad(globalData)
          setTimeout(() => {
            uni.showToast({
              title: '服务器打了个盹~',
              icon: 'none'
            })
          }, 1)
          return reject(err)
        }
      })
    })
  },

  hideLoad: (globalData) => {
    globalData.currentReqCounts--
    if (globalData.currentReqCounts === 0) {
      clearTimeout(loadingTimer)
      isShowLoad && uni.hideLoading()
      isShowLoad = false
    }
  },

  mpAuthLogin: (page, needCode) => {
    // 在微信环境打开,请求公众号网页登陆
    let redirectUrl = null

    if (!page || page === import.meta.env.VITE_APP_DOMAIN_ADDRESS) {
      redirectUrl = window.location.href
    } else {
      redirectUrl = import.meta.env.VITE_APP_DOMAIN_ADDRESS + page
    }
    const scope = 'snsapi_userinfo'
    window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + import.meta.env.VITE_APP_MP_APPID +
      '&redirect_uri=' +
      encodeURIComponent(redirectUrl) + '&response_type=code&scope=' + scope + '&state=' + (needCode ? 'needCode' : 'unNeedCode') +
      '#wechat_redirect'
  },

  // 通过接口获取用户操作id信息
  getOid: () => {
    uni.setStorageSync('cloudRequestOidUni', true)
    http.request({
      url: '/mall4cloud_flow/ma/flow/getId',
      method: 'GET'
    }).then(res => {
      uni.removeStorageSync('cloudRequestOidUni')
      uni.setStorageSync('cloudOid', res)
      while (getApp().globalData.flowRequestQueue.length) {
        const { ve, flowData } = getApp().globalData.flowRequestQueue.shift()
        flowData.oid = res
        http.saveLog(ve, flowData)
      }
    }).catch(() => {
      uni.removeStorageSync('cloudRequestOidUni')
      // eslint-disable-next-line no-console
      console.error('获取oid失败')
    })
  },

  /**
   * 提交数据分析
   * @param {*} visitType 访问类型
   * @param {*} flowData 提交的数据内容
   */
  saveLog: (visitType, flowData) => {
    const flowAnalysisLogDtoUni = Object.assign(flowData)
    flowAnalysisLogDtoUni.ve = visitType
    http.request({
      url: '/mall4cloud_flow/ma/flow',
      method: 'POST',
      data: flowAnalysisLogDtoUni
    })
  },

  /**
   * 上传图片
   */
  upload: (params) => {
    uni.uploadFile({
      url: import.meta.env.VITE_APP_BASE_API + params.url,
      filePath: params.filePath,
      name: params.name,
      header: {
        Authorization: params.login ? undefined : uni.getStorageSync('cloudToken')
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
}

// 网络监听回调事件
const networkCallback = function (res) {
  uni.setStorageSync('cloudWatchingNetworkFlag', 1)
  if (res.isConnected) {
    uni.removeStorageSync('cloudWatchingNetworkFlag')
    uni.reLaunch({
      url: '/pages/index/index'
    })
  }
}

export default http
