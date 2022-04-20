<template>
  <view class="pay-result">
    <view class="pay-det">
      <view class="img">
        <img class="pay-img" :src="sts==1?'/static/images/hook.png':'/static/images/payment-close.png'" alt="">
      </view>
      <view class="error-text">{{ sts==1?'支付成功，感谢您的购买':'支付失败，请重新支付' }}</view>
    </view>
    <view class="pay-bottom">
      <view class="btn" @tap="toIndex">返回首页</view>
      <view class="btn back" @tap="toOrderDet">查看订单</view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      orderIds: '',
      sts: 0 // 订单支付状态:  0失败; 1成功
    }
  },
  onLoad(options) {
    console.log(options)
    this.orderIds = options.orderIds
    this.sts = Number(options.sts)
  },
  onBackPress(event) {
    uni.redirectTo({
      url: '/pages/index/index'
    })
    return true
  },
  methods: {

    // 去首页
    toIndex() {
      uni.switchTab({
        url: '/pages/index/index'
      })
    },

    // 去订单详情
    toOrderDet() {
      if (this.sts === 1) {
        // 支付成功 跳转到待发货订单orderStatus: 2
        uni.redirectTo({
          url: '/pages/order/order?orderStatus=2'
        })
      } else {
        // 支付失败 跳转到待发货订单orderStatus: 1
        uni.redirectTo({
          url: '/pages/order/order?orderStatus=1'
        })
      }
    }
  }
}
</script>

<style>
@import "./payment-result.css";
@import "./../../popup.css";
</style>
