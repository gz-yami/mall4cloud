<template>
  <view class="shop-page">
    <view class="shop-index">
      <!-- 头部 -->
      <view class="header">
        <view class="bg">
          <image v-if="shopInfo.mobileBackgroundPic" :src="shopInfo.mobileBackgroundPic" />
          <image v-else src="/static/img/banner3.png" />
        </view>
      </view>
      <!-- 店铺信息 -->
      <view class="shop-info">
        <view class="logo">
          <image :src="shopInfo.shopLogo" />
        </view>
        <view class="text-box">
          <view class="name">{{ shopInfo.shopName }}</view>
          <view class="focus-box">
            <view v-if="shopInfo.type === 1" class="self">自营</view>
          </view>
        </view>
      </view>
      <view class="shop-des">{{ shopInfo.intro }}</view>

      <!-- 广告 -->
      <view class="shop-adv" />

      <!-- 店铺热销 -->
      <view class="shop-prods">
        <view class="shop-tit">
          <view class="text">店铺热销</view>
        </view>
        <view class="prods">
          <block v-for="(item,index) in prodList" :key="index">
            <view class="item" @tap="detail(item.spuId)">
              <view class="img">
                <image :src="item.mainImgUrl" />
              </view>
              <view class="text-box">
                <view class="name">{{ item.spuName }}</view>
                <!-- <view class="sku">夏季换新 银色 256g</view> -->
                <view class="price-box">
                  <view class="price">
                    <view class="symbol">￥</view>
                    <view class="big">{{ item.priceFee | price }}</view>
                  </view>
                </view>
              </view></view>
          </block>
        </view>
        <view class="nomore">没有更多了，看看别的吧</view>
      </view>

      <!-- 店铺tabbar -->
      <view class="shop-tabbar">
        <view class="item active" @tap="goShopIndex">
          <view class="icon">
            <image src="/static/images/shop-index-r.png" />
            <!-- <image src="/static/images/shop-index.png" /> -->
          </view>
          <view class="text">首页</view>
        </view>
        <view class="item" @tap="goShopProds">
          <view class="icon">
            <!-- <image src="/static/images/shop-prods-r.png" /> -->
            <image src="/static/images/shop-prods.png" />
          </view>
          <view class="text">商品</view>
        </view>
        <view class="item" @tap="goShopCategory">
          <view class="icon">
            <!-- <image src="/static/images/shop-category-r.png" /> -->
            <image src="/static/images/shop-category.png" />
          </view>
          <view class="text">分类</view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
const http = require('../../utils/http.js')
export default {
  filters: {
    price(value) {
      return (value / 100).toFixed(2)
    }
  },
  data() {
    return {
      isCollect: false, // 是否收藏
      shopId: 1,
      shopInfo: {},
      pageNum: 1,
      pageSize: 8,
      total: 1,
      pages: 1,
      prodList: []
    }
  },
  onReachBottom() {
    if (this.pageNum < this.pages) {
      this.pageNum++
      this.getProd()
    }
  },
  onLoad(options) {
    this.shopId = options.shopId
    this.getShopInfo()
    this.getProd()
  },
  methods: {

    // 收藏店铺
    collectShop() {
      this.isCollect = !this.isCollect
    },
    // 去商品详情
    detail(spuId) {
      uni.navigateTo({
        url: '/pages/detail/detail?spuId=' + spuId
      })
    },
    /**
     * 获取店铺信息
     */
    getShopInfo() {
      const params = {
        url: '/mall4cloud_multishop/ua/shop_detail/head_info',
        method: 'GET',
        data: {
          shopId: this.shopId
        },
        callBack: res => {
          this.shopInfo = res
          uni.setStorageSync('shopInfo', res)
        },
        errCallBack: errMsg => {
          console.log(errMsg)
        }
      }
      http.request(params)
    },
    /**
     * 获取商品列表
     */
    getProd() {
      const params = {
        url: '/mall4cloud_search/ua/search/simple_page',
        method: 'GET',
        data: {
          shopId: this.shopId,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        },
        callBack: res => {
          if (this.pageNum !== 1) {
            this.prodList = this.prodList.concat(res.list[0].spus)
          } else {
            this.prodList = res.list[0].spus
          }
          this.total = res.total
          this.pages = res.pages
        },
        errCallBack: errMsg => {
          console.log(errMsg)
        }
      }
      http.request(params)
    },
    // 切换tabbar
    goShopIndex() {
      uni.navigateTo({
        url: `/pages/shop-page/shop-index?shopId=${this.shopId}`
      })
    },
    goShopProds() {
      uni.navigateTo({
        url: `/pages/shop-page/shop-prods?shopId=${this.shopId}`
      })
    },
    goShopCategory() {
      uni.navigateTo({
        url: `/pages/shop-page/shop-category?shopId=${this.shopId}`
      })
    }
  }
}
</script>

<style>
@import "./shop-page.css";
</style>
