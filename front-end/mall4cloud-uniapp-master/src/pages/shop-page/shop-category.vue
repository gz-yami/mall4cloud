<template>
  <view class="shop-page">
    <view class="shop-category">
      <!-- 头部 -->
      <view class="header">
        <view class="bg">
          <image src="/static/img/banner3.png" />
        </view>
        <view class="bg-mask" />
        <view class="shop-info">
          <view class="logo">
            <image :src="shopInfo.shopLogo" />
          </view>
          <view class="text-box">
            <view class="name">{{ shopInfo.shopName }}</view>
            <view class="focus-box">
              <view v-if="shopInfo.type" class="self">自营</view>
            </view>
          </view>
        </view>
      </view>

      <!-- 分类 -->
      <view class="category">
        <!-- 内容区 -->
        <!-- 内容区 -->
        <view class="main">
          <!-- 左侧内容 -->
          <view class="first-menu">
            <block v-for="(firstCategory, index) in firstCategoryList" :key="index">
              <view class="item" :class="{active:selectedIndex===index}" :data-index="index" @tap="switchCategory">{{ firstCategory.name }}</view>
            </block>
          </view>
          <!-- 右侧内容 -->
          <view class="subclass">
            <view v-if="firstCategoryList.length" class="adv-img">
              <image :src="firstCategoryList[selectedIndex].imgUrl" />
            </view>
            <block v-for="(secondCategory, index) in secondCategoryList" :key="index">
              <view class="sub-con">
                <!-- <view class="second-menu">{{ secondCategory.name }}</view> -->
                <view class="third-menu">
                  <view class="item" :data-categoryid="secondCategory.categoryId" :data-name="secondCategory.name" @tap="toSearchListPage">
                    <view class="img">
                      <image :src="secondCategory.imgUrl" />
                    </view>
                    <view class="text">{{ secondCategory.name }}</view>
                  </view>
                </view>
              </view>
            </block>
          </view>
        </view>
      </view>
      <!-- 店铺tabbar -->
      <view class="shop-tabbar">
        <view class="item" @tap="goShopIndex">
          <view class="icon">
            <!-- <image src="/static/images/shop-index-r.png" /> -->
            <image src="/static/images/shop-index.png" />
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
        <view class="item active" @tap="goShopCategory">
          <view class="icon">
            <image src="/static/images/shop-category-r.png" />
            <!-- <image src="/static/images/shop-category.png" /> -->
          </view>
          <view class="text">分类</view>
        </view>
        <!-- <view class="item">
          <view class="icon">
            <image src="/static/images/detail-service.png" />
          </view>
          <view class="text">客服</view>
        </view> -->
      </view>
    </view>
  </view>
</template>

<script>
const http = require('../../utils/http.js')
export default {
  data() {
    return {
      shopId: 0,
      shopInfo: {},
      firstCategoryList: [], // 一级分类内容(一级分类下的categories为二级分类)
      secondCategoryList: [], // 二级分类内容(二级分类下的categories为三级分类)
      selectedIndex: 0 // 选中的分类项(默认为第一个分类)
    }
  },
  onLoad(options) {
    console.log(options)
    this.shopId = options.shopId
    this.shopInfo = uni.getStorageSync('shopInfo')
    this.getCategoryList()
  },
  methods: {

    // 收藏店铺
    collectShop() {
      this.isCollect = !this.isCollect
    },

    /**
     * 获取分类(直接得到1、2、3级的分类)
     */
    getCategoryList() {
      uni.showLoading()
      const params = {
        url: '/mall4cloud_product/ua/category/shop_category_list',
        method: 'GET',
        data: {
          shopId: this.shopId,
          parentId: 0
        },
        callBack: res => {
          if (!res) {
            uni.hideLoading()
            return
          }
          this.firstCategoryList = res
          // 默认选择: 第一个一级分类
          this.secondCategoryList = this.firstCategoryList[0].categories
          uni.hideLoading()
        }
      }
      http.request(params)
    },
    /**
     * 跳转到商品列表页
     */
    toSearchListPage(e) {
      const categoryId = parseInt(e.currentTarget.dataset.categoryid)
      uni.navigateTo({
        url: `/pages/shop-page/shop-prods?shopSecondaryCategoryId=${categoryId}&shopId=${this.shopId}`
      })
    },
    /**
     * 切换分类，改变二级分类内容
     */
    switchCategory(e) {
      const index = parseInt(e.currentTarget.dataset.index)
      this.selectedIndex = index
      this.secondCategoryList = this.firstCategoryList[index].categories
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
@import "./../category/category.css";
</style>
