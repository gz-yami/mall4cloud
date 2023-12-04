<template>
  <view class="page-my">
    <!-- 用户资料 -->
    <view class="user-box">
      <view
        class="img"
        @tap="toUserSettings"
      >
        <image :src="userInfo.pic || '/static/images/head-sculpture.png'" />
      </view>
      <view
        v-if="isNotLogged"
        class="text-box"
        @tap="toLogin"
      >
        <view class="name">
          立即登录
        </view>
      </view>
      <view
        v-if="!isNotLogged"
        class="text-box"
        @tap="toUserSettings"
      >
        <view class="name">
          {{ userInfo.nickName }}
        </view>
      </view>
    </view>

    <!-- 我的订单 -->
    <view class="my-con my-orders">
      <view
        class="tit"
        @tap="toMyOder(0)"
      >
        <view class="text">
          我的订单
        </view>
        <view class="more text-arrow">
          全部
        </view>
      </view>
      <view class="con-box">
        <view
          class="item"
          @tap="toMyOder(1)"
        >
          <view class="img">
            <image src="/static/images/my-paid.png" />
            <view
              v-if="orderCountInfo.unPay"
              class="mark"
            >
              {{ orderCountInfo.unPay }}
            </view>
          </view>
          <view class="text">
            待付款
          </view>
        </view>
        <view
          class="item"
          @tap="toMyOder(2)"
        >
          <view class="img">
            <image src="/static/images/my-delivered.png" />
            <view
              v-if="orderCountInfo.payed"
              class="mark"
            >
              {{ orderCountInfo.payed }}
            </view>
          </view>
          <view class="text">
            待发货
          </view>
        </view>
        <view
          class="item w70"
          @tap="toMyOder(3)"
        >
          <view class="img">
            <image src="/static/images/my-receiving.png" />
            <view
              v-if="orderCountInfo.consignment"
              class="mark"
            >
              {{ orderCountInfo.consignment }}
            </view>
          </view>
          <view class="text">
            待收货
          </view>
        </view>
        <view
          class="item"
          @tap="notOpen('退换货')"
        >
          <view class="img">
            <image src="/static/images/my-refund.png" />
            <!-- <view class="mark">{{ orderCountInfo.consignment }}</view> -->
          </view>
          <view class="text">
            退换货
          </view>
        </view>
      </view>
    </view>

    <!-- 我的工具 -->
    <view class="my-con my-tools">
      <view class="tit">
        <view class="text">
          我的工具
        </view>
      </view>
      <view class="con-box">
        <view
          class="item"
          @tap="notOpen('会员功能')"
        >
          <view class="img">
            <image src="/static/images/my-member.png" />
          </view>
          <view class="text">
            会员中心
          </view>
        </view>
        <view
          class="item"
          @tap="notOpen('分销')"
        >
          <view class="img">
            <image src="/static/images/my-share.png" />
          </view>
          <view class="text">
            分销中心
          </view>
        </view>
        <view
          class="item"
          @tap="notOpen('领券中心')"
        >
          <view class="img">
            <image src="/static/images/my-coupon.png" />
          </view>
          <view class="text">
            领券中心
          </view>
        </view>
        <view
          class="item"
          @tap="notOpen('我的收藏')"
        >
          <view class="img">
            <image src="/static/images/my-collection.png" />
          </view>
          <view class="text">
            我的收藏
          </view>
        </view>
        <view
          class="item"
          @tap="toFreeShop"
        >
          <view class="img">
            <image src="/static/images/my-shop.png" />
          </view>
          <view class="text">
            免费开店
          </view>
        </view>
        <view
          class="item"
          @tap="toAddressList"
        >
          <view class="img">
            <image src="/static/images/my-address.png" />
          </view>
          <view class="text">
            地址管理
          </view>
        </view>
        <view
          class="item"
          @tap="notOpen('我的足迹')"
        >
          <view class="img">
            <image src="/static/images/my-history.png" />
          </view>
          <view class="text">
            我的足迹
          </view>
        </view>
        <view
          class="item"
          @tap="toUserSettings"
        >
          <view class="img">
            <image src="/static/images/my-set.png" />
          </view>
          <view class="text">
            系统设置
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue'
import * as cartCount from '@/utils/cart-count.js'

const Data = reactive({
  isNotLogged: true, // 未登录
  userInfo: '',
  orderCountInfo: '',
  simpleInfo: ''
})
const { isNotLogged, userInfo, orderCountInfo } = toRefs(Data)

onShow(() => {
  // 排除 tempToken 影响，真实登陆后再请求数据
  if (uni.getStorageSync('cloudToken')) {
    // 查询用户信息
    queryUserInfo()
    // 加载订单数据
    loadOrderCountFun()
    // 获取用户头像昵称
    getSimpleInfo()
    Data.isNotLogged = false
  } else {
    Data.simpleInfo = {}
    Data.orderCountInfo = {}
    Data.userInfo = {}
    Data.isNotLogged = true
  }
  cartCount.getCartCount()
})

/**
     * 获取用户昵称头像
     */
const getSimpleInfo = () => {
  const params = {
    url: '/mall4cloud_user/a/user/simple_info',
    method: 'GET',
    data: {}
  }
  http.request(params).then((res) => {
    Data.simpleInfo = res
  })
}

/**
     * 计算各个订单数量
     */
const loadOrderCountFun = () => {
  const params = {
    url: '/mall4cloud_order/p/myOrder/order_count',
    method: 'GET',
    data: {}
  }
  http.request(params).then(res => {
    Data.orderCountInfo = res
  })
}
/**
     * 获取用户信息
     */
const queryUserInfo = () => {
  const params = {
    url: '/mall4cloud_user/a/user/ma/user_detail_info',
    method: 'GET',
    data: {}

  }
  http.request(params).then(res => {
    Data.userInfo = res
  })
}

// 去登陆
const toLogin = () => {
  uni.navigateTo({
    url: '/pages/login/login'
  })
}

// 去用户设置
const toUserSettings = () => {
  uni.navigateTo({
    url: '/pages/user-settings/user-settings'
  })
}

// 去我的订单
const toMyOder = (sts) => {
  uni.navigateTo({
    url: '/pages/order/order?orderStatus=' + sts
  })
}

// 去地址管理
const toAddressList = () => {
  uni.navigateTo({
    url: '/pages/address-list/address-list'
  })
}

// 去免费开店
const toFreeShop = () => {
  if (uni.getStorageSync('cloudToken')) {
    uni.navigateTo({
      url: '/pages/free-shop/free-shop'
    })
  } else {
    uni.showToast({
      title: '请登录账号',
      duration: 2000,
      icon: 'error'
    })
    setTimeout(() => {
      uni.navigateTo({
        url: '/'
      })
    }, 2000)
  }
}

const notOpen = (title) => {
  uni.showModal({
    title: '提示',
    content: `${title}暂未开源`,
    showCancel: false

  })
}
</script>

<style lang="scss" scoped>
@use "my";
</style>
