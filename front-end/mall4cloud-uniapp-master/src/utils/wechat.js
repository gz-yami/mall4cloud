var http = require('./http.js')
var config = require('./config.js')
// #ifdef H5
var wx = require('./jwx/index.js')
// #endif

export default {
  // 判断是否在微信中
  isWechat() {
    var ua = window.navigator.userAgent.toLowerCase()
    if (ua.match(/micromessenger/i) == 'micromessenger') {
      // console.log('是微信客户端')
      return true
    } else {
      // console.log('不是微信客户端')
      return false
    }
  },

  // #ifdef H5
  getWxConfig(cb) {
    var os = ''
    var url = ''
    uni.getSystemInfo({
      success: function(res) {
        os = res.platform // 客户端平台，值域为：ios、android
      }
    })
    if (os == 'ios') { // ios
      url = uni.getStorageSync('iosUrl')
    } else if (os == 'android') { // 安卓
      url = window.location.href
    }
    var params = {
      url: '/p/wx/jsapi/createJsapiSignature',
      method: 'POST',
      data: url,
      callBack: data => {
        // 下面要发起微信支付了
        wx.config({
          debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
          appId: config.mpAppId, // 必填，公众号的唯一标识
          timestamp: data.timestamp, // 必填，生成签名的时间戳
          nonceStr: data.nonceStr, // 必填，生成签名的随机串
          signature: data.signature, // 必填，签名，见附录1
          jsApiList: ['chooseWXPay', 'updateAppMessageShareData', 'onMenuShareAppMessage', 'scanQRCode', 'getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        })
        if (cb) {
          cb()
        }
      }
    }
    http.request(params)
  },
  // #endif

  /**
	 * 唤起微信支付
	 * @param {Object} paydata 支付需要的参数
	 * @param {Object} cb 成功回调
	 * @param {Object} errorCb 失败回调
	 */
  callWexinPay(paydata, cb, errorCb) {
    // 获取后台传入的数据
    const appId = paydata.appId
    const timestamp = paydata.timeStamp
    const nonceStr = paydata.nonceStr
    const packages = paydata.packageValue
    const paySign = paydata.paySign
    const signType = paydata.signType
    const desc = paydata.desc

    this.getWxConfig(() => {
      wx.ready(function() {
        console.log(paydata)
        wx.chooseWXPay({
          timestamp: timestamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
          nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
          package: packages, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
          signType: signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
          paySign: paySign, // 支付签名
          desc: desc, // 描述
          success: function(res) {
            // 支付成功后的回调函数
            cb(res)
          },
          fail: function(res) {
            // 失败回调函数
            errorCb(res)
          }
        })
      })
      wx.error(function(res) {

      })
    })
  },
  /**
	 * 唤起微信分享
	 * @param {Object} sharedata 分享需要的参数
	 * @param {Object} cb 成功回调
	 * @param {Object} errorCb 失败回调
	 */
  callWexinShare(sharedata, cb, errorCb) {
    this.getWxConfig(() => {
      wx.ready(function() {
        console.log(JSON.stringify(sharedata))
        wx.updateAppMessageShareData({
          title: sharedata.title, // 分享标题
          link: sharedata.link, // 分享链接
          imgUrl: sharedata.imgUrl, // 分享图标
          desc: sharedata.desc, // 分享描述
          success: function(res) {
            // 支付成功后的回调函数
            cb(res)
          },
          cancel: function(cancelMsg) {
            errorCb(cancelMsg)
          }
        })
      })
      wx.error(function(res) {})
    })
  },
  /**
	 * scanQRCode-扫码
	 * @param {Object} paydata 支付需要的参数
	 * @param {Object} cb 成功回调
	 * @param {Object} errorCb 失败回调
	 */
  scanQRCode(cb, errorCb) {
    this.getWxConfig(() => {
      wx.ready(function() {
        wx.scanQRCode({
          needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
          scanType: ['qrCode', 'barCode'], // 可以指定扫二维码还是一维码，默认二者都有
          success: function(res) {
            var result = res.resultStr // 当needResult 为 1 时，扫码返回的结果
            cb(result)
          },
          fail: function(res) {
            // 失败回调函数
            errorCb(res)
          }
        })
      })
      wx.error(function(res) {})
    })
  },

  /**
	 * getLocation-获取地理位置信息
	 * @param {Object} cb 成功回调
	 * @param {Object} errorCb 失败回调
	 */
  getLocation(cb, errorCb) {
    this.getWxConfig(() => {
      wx.ready(function() {
        wx.getLocation({
          type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
          success: function(res) {
            // return latitude = res.latitude, // 纬度，浮点数，范围为90 ~ -90
            // 	longitude = res.longitude // 经度，浮点数，范围为180 ~ -180
            cb(res)
          },
          fail: function(res) {
            // 失败回调函数
            errorCb(res)
          }
        })
      })
      wx.error(function(res) {})
    })
  }
}
