<template>
  <view class="pay-result">
    <view class="pay-det">
      <view class="img">
        <img
          class="pay-img"
          :src="sts==1?'/static/images/hook.png':'/static/images/payment-close.png'"
          alt=""
        >
      </view>
      <view class="error-text">
        {{ sts==1?'支付成功，感谢您的购买':'支付失败，请重新支付' }}
      </view>
    </view>
    <view class="pay-bottom">
      <view
        class="btn"
        @tap="toIndex"
      >
        返回首页
      </view>
      <view
        class="btn back"
        @tap="toOrderDet"
      >
        查看订单
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue'

const Data = reactive({
  orderIds: '',
  sts: 0 // 订单支付状态:  0失败; 1成功
})
const { sts } = toRefs(Data)

onLoad((options) => {
  Data.orderIds = options.orderIds
  Data.sts = Number(options.sts)
})

onBackPress(() => {
  uni.redirectTo({
    url: '/pages/index/index'
  })
  return true
})

// 去首页
const toIndex = () => {
  uni.switchTab({
    url: '/pages/index/index'
  })
}

// 去订单详情
const toOrderDet = () => {
  if (Data.sts === 1) {
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
</script>

<style lang="scss" scoped>
@use "payment-result";
@use "../../popup";
</style>
