<template>
  <view class="payment">
    <view class="pay-number">
      <view class="price">
        <view class="symbol">
          ￥
        </view>
        <view class="big">
          {{ wxs.parsePrice(totalFee)[0] }}
        </view>
        <view class="symbol">
          .{{ wxs.parsePrice(totalFee)[1] }}
        </view>
      </view>
      <view class="time">
        <view class="text">
          剩余时间
        </view>
        <view class="num">
          {{ hou }}
        </view>
        <view class="colon">
          :
        </view>
        <view class="num">
          {{ min }}
        </view>
        <view class="colon">
          :
        </view>
        <view class="num">
          {{ sec }}
        </view>
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
        <radio
          color="#fc1b35"
          :checked="true"
        />
      </label>
      <label class="item">
        <view class="pay-name">
          <view class="img">
            <image src="/static/images/alipay.png" />
          </view>
          <view class="name">支付宝</view>
        </view>
        <radio
          color="#fc1b35"
          disabled
        />
      </label>

      <label class="item">
        <view class="pay-name">
          <view class="img">
            <image src="/static/images/wxpay.png" />
          </view>
          <view class="name">微信支付</view>
        </view>
        <radio
          color="#fc1b35"
          disabled
        />
      </label>
    </radio-group>

    <view
      class="sure-pay"
      @tap="surePay"
    >
      确定付款
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue'
import Wechat from '../../utils/wechat.js'

const wxs = number()

const Data = reactive({
  isWechat: false, // 是否为微信环境
  payType: PayType.ALIPAY_H5,
  PayTypeConstant: PayType,
  appType: uni.getStorageSync('cloudAppType'),
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
})
const { totalFee, hou, min, sec } = toRefs(Data)

onLoad((options) => {
  // #ifdef H5
  Data.isWechat = Wechat.isWechat()
  // #endif
  // #ifdef APP-PLUS
  Data.isWechat = false
  Data.payType = PayType.ALIPAY_APP
  // #endif
  // #ifdef MP-WEIXIN
  Data.isWechat = false
  Data.payType = PayType.WECHATPAY
  // #endif
  if (options.orderIds) {
    Data.orderIds = options.orderIds
  }
  if (options.isPurePoints) {
    Data.isPurePoints = options.orderIds
  }
})

onShow(() => {
  getOrderPaymentInfo()
})

/**
     * 获取订单支付信息
     */
const getOrderPaymentInfo = () => {
  const params = {
    url: '/mall4cloud_order/a/order/order_pay_info',
    method: 'GET',
    data: {
      orderIds: Data.orderIds
    }
  }
  http.request(params).then((res) => {
    Data.totalFee = res.totalFee
    Data.totalScore = res.totalScore
    Data.endTime = res.endTime
    // 生成倒计时
    getCountDown()
  })
}

/**
     * 生成支付倒计时
     */
const getCountDown = () => {
  const nowTime = new Date().getTime() // 现在时间（时间戳）
  const endTime = new Date(Data.endTime.replace(/-/g, '/')).getTime() // 结束时间（时间戳）
  const time = (endTime - nowTime) / 1000 // 距离结束的毫秒数
  // 获取时、分、秒
  let hou = parseInt(time % (60 * 60 * 24) / 3600)
  let min = parseInt(time % (60 * 60 * 24) % 3600 / 60)
  let sec = parseInt(time % (60 * 60 * 24) % 3600 % 60)
  hou = timeFormin(hou)
  min = timeFormin(min)
  sec = timeFormin(sec)
  Data.hou = timeFormat(hou)
  Data.min = timeFormat(min)
  Data.sec = timeFormat(sec)
  // 每1000ms刷新一次
  if (time > 0) {
    Data.countDown = true
    Data.timer = setTimeout(Data.getCountDown, 1000)
  } else {
    Data.countDown = false
    uni.navigateTo({
      url: '/pages/order/order'
    })
    // this.$Router.push('/pages/orderList/orderList')
  }
}

// 小于10的格式化函数（2变成02）
const timeFormat = (num) => {
  return num < 10 ? '0' + num : num
}

// 小于0的格式化函数（不会出现负数）
const timeFormin = (num) => {
  return num < 0 ? 0 : num
}

// 确认支付
const surePay = () => {
  uni.showModal({
    title: '提示',
    content: '确定是否付款',
    showCancel: true, // 是否显示取消按钮
    cancelText: '取消',
    confirmText: '确定',
    success: (res) => {
      if (res.confirm) {
        orderPay()
      } else if (res.cancel) {
        uni.showToast({
          title: $t('cancelBalancePay'),
          icon: 'none',
          duration: 1500
        })
      }
    }
  })
  // uni.navigateTo({
  //   url: '/pages/payment-result/payment-result'
  // })
}

const orderPay = () => {
  const orderIdList = Data.orderIds.split(',')
  const params = {
    url: '/mall4cloud_payment/pay/order',
    method: 'POST',
    data: {
      orderIds: orderIdList
    }
  }
  http.request(params).then(() => {
    uni.redirectTo({
      url: `/pages/payment-result/payment-result?sts=1&orderIds=${Data.orderIds}`
    })
  })
}
</script>

<style lang="scss" scoped>
@use "payment";
</style>
