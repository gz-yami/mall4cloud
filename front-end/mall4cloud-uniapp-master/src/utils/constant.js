const PayType = {
  SCOREPAY: 0, // 积分支付
  WECHATPAY: 1, // 小程序支付
  ALIPAY: 2, // 支付宝支付
  WECHATPAY_SWEEP_CODE: 3, // 微信扫码支付
  WECHATPAY_H5: 4, // 微信H5支付
  WECHATPAY_MP: 5, // 微信公众号支付
  ALIPAY_H5: 6, // 支付宝H5支付
  ALIPAY_APP: 7, // 支付宝APP支付
  WECHATPAY_APP: 8, // 微信APP支付
  BALANCEPAY: 9 // 余额支付
}

const AppType = {
  MINI: 1, // 微信小程序
  MP: 2, // 微信公众号
  PC: 3, // pc
  H5: 4, // h5
  ANDROID: 5, // 安卓
  IOS: 6, // 苹果
  ALI: 7, // 支付宝H5
  ALIMINI: 8 // 支付宝小程序
}

module.exports = {
  PayType,
  AppType
}
