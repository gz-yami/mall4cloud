<template>
  <view class="payment">
    <view class="pay-number">
      <view class="price">
        <view class="symbol">￥</view>
        <view class="big">{{ wxs.parsePrice(totalFee)[0] }}</view>
        <view class="symbol">.{{ wxs.parsePrice(totalFee)[1] }}</view>
      </view>
      <view class="time">
        <view class="text">剩余时间</view>
        <view class="num">{{ hou }}</view>
        <view class="colon">:</view>
        <view class="num">{{ min }}</view>
        <view class="colon">:</view>
        <view class="num">{{ sec }}</view>
      </view>
    </view>
    <radio-group class="ways">
      <label class="item">
        <view class="pay-name">
          <view class="img">
            <image src="/static/images/balancePay.png" />
          </view>
          <view class="name">模拟支付</view>
        </view>
        <radio color="#fc1b35" :checked="true" />
      </label>
      <label class="item">
        <view class="pay-name">
          <view class="img">
            <image src="/static/images/alipay.png" />
          </view>
          <view class="name">支付宝</view>
        </view>
        <radio color="#fc1b35" disabled />
      </label>

      <label class="item">
        <view class="pay-name">
          <view class="img">
            <image src="/static/images/wxpay.png" />
          </view>
          <view class="name">微信支付</view>
        </view>
        <radio color="#fc1b35" disabled />
      </label>

    </radio-group>

    <view class="sure-pay" @tap="surePay">确定付款</view>
  </view>
</template>
<script module="wxs" lang="wxs" src="../../wxs/index.wxs"></script>

<script>
const http = require('../../utils/http.js')
import Wechat from '../../utils/wechat.js'
import { PayType } from '../../utils/constant'

export default {
  data() {
    return {
      isWechat: false, // 是否为微信环境
      payType: PayType.ALIPAY_H5,
      PayTypeConstant: PayType,
      appType: uni.getStorageSync('appType'),
      totalFee: 0,
      totalScore: 0,

      orderIds: '', // 订单id
      dvyType: 1, // 配送类型 1:快递 2:自提 3：无需快递 4:同城配送

      timer: '', // 定时器名称
      endTime: '', // 订单过期时间
      countDown: false, // 倒计时变量
      hou: '', // 时
      min: '', // 分
      sec: '' // 秒
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    // #ifdef H5
    this.isWechat = Wechat.isWechat()
    // #endif
    // #ifdef APP-PLUS
    this.isWechat = false
    this.payType = PayType.ALIPAY_APP
    // #endif
    // #ifdef MP-WEIXIN
    this.isWechat = false
    this.payType = PayType.WECHATPAY
    // #endif
    if (options.orderIds) {
      this.orderIds = options.orderIds
    }
    if (options.isPurePoints) {
      this.isPurePoints = options.orderIds
    }
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.getOrderPaymentInfo()
  },

  methods: {
    /**
     * 获取订单支付信息
     */
    getOrderPaymentInfo() {
      const params = {
        url: '/mall4cloud_order/a/order/order_pay_info',
        method: 'GET',
        data: {
          orderIds: this.orderIds
        },
        callBack: res => {
          this.totalFee = res.totalFee
          this.totalScore = res.totalScore
          this.endTime = res.endTime
          // 生成倒计时
          this.getCountDown()
        }
      }
      http.request(params)
    },

    /**
     * 生成支付倒计时
     */
    getCountDown() {
      const nowTime = new Date().getTime() // 现在时间（时间戳）
      const endTime = new Date(this.endTime.replace(/\-/g, '/')).getTime() // 结束时间（时间戳）
      const time = (endTime - nowTime) / 1000 // 距离结束的毫秒数
      // 获取时、分、秒
      let hou = parseInt(time % (60 * 60 * 24) / 3600)
      let min = parseInt(time % (60 * 60 * 24) % 3600 / 60)
      let sec = parseInt(time % (60 * 60 * 24) % 3600 % 60)
      hou = this.timeFormin(hou)
      min = this.timeFormin(min)
      sec = this.timeFormin(sec)
      this.hou = this.timeFormat(hou)
      this.min = this.timeFormat(min)
      this.sec = this.timeFormat(sec)
      // 每1000ms刷新一次
      if (time > 0) {
        this.countDown = true
        this.timer = setTimeout(this.getCountDown, 1000)
      } else {
        this.countDown = false
        uni.navigateTo({
          url: '/pages/order/order'
        })
        // this.$Router.push('/pages/orderList/orderList')
      }
    },

    // 小于10的格式化函数（2变成02）
    timeFormat(num) {
      return num < 10 ? '0' + num : num
    },
    // 小于0的格式化函数（不会出现负数）
    timeFormin(num) {
      return num < 0 ? 0 : num
    },
    // 确认支付
    surePay() {
      uni.showModal({
          title: '提示',
          content: '确定是否付款',
          showCancel: true,//是否显示取消按钮
          cancelText: '取消',
          confirmText: '确定',
          success: (res) => {
            if (res.confirm) {
              this.orderPay()
            } else if (res.cancel) {
              uni.showToast({
                title: this.i18n.cancelBalancePay,
                icon: 'none',
                duration: 1500
              });
            }
          },
        })
      // uni.navigateTo({
      //   url: '/pages/payment-result/payment-result'
      // })
    },
    orderPay() {
      let orderIdList = this.orderIds.split(',')
      const params = {
        url: '/mall4cloud_payment/pay/order',
        method: 'POST',
        data: {
          orderIds: orderIdList
        },
        callBack: res => {
          uni.redirectTo({
            url: `/pages/payment-result/payment-result?sts=1&orderIds=${this.orderIds}`
          })
        }
      }
      http.request(params)
    }
  }
}
</script>

<style>
@import "./payment.css";
</style>
