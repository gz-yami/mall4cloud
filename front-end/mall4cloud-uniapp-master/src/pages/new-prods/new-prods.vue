<template>
  <view class="new-prods">
    <!-- banner -->
    <view class="banner">
      <image src="/static/img/banner2.png" />
    </view>

    <!-- 新品分类 -->
    <view class="new-category">
      <scroll-view scroll-x="true" class="category-box" :scroll-left="categoryScrollLeft" scroll-with-animation @scroll="scroll">
        <block v-for="(item, index) in categoryList" :key="index">
          <view class="category-item" :class="{active:selectedIndex===index}" :data-index="index" :data-categoryid="item.categoryId" @tap="switchCategory">{{ item.name }}</view>
        </block>
      </scroll-view>
    </view>

    <!-- 产品列表 -->
    <view class="prods">
      <block v-for="(item, index) in prodList" :key="index">
        <view class="item" @tap="toDetail(item.spuId)">
          <view class="img">
            <image :src="item.mainImgUrl" />
          </view>
          <view class="text-box">
            <view class="name">{{ item.spuName }}</view>
            <view class="price-box">
              <view class="price">
                <view class="symbol">￥</view>
                <view class="big">{{ wxs.parsePrice(item.priceFee)[0] }}</view>
                <view class="symbol">.{{ wxs.parsePrice(item.priceFee)[1] }}</view>
              </view>
            </view>
          </view>
        </view>
      </block>
    </view>
    <!-- 无商品显示 -->
    <view v-if="prodList.length===0" class="empty">
      <view class="img">
        <image src="/static/empty-img/not-found.png" />
      </view>
      <view class="text">没有找到对应商品，看看别的吧</view>
    </view>

    <view v-if="isLoadAll && prodList.length>0" class="nomore">没有更多了，看看别的吧</view>
  </view>
</template>
<script module="wxs" lang="wxs" src="../../wxs/index.wxs"></script>

<script>
const http = require('../../utils/http.js')

export default {
  data() {
    return {
      // 查询的参数
      pageQuery: {
        pageSize: 10, // 每页展示的个数
        pageNum: 1, // 页数
        sort: 1 // 新品 1:新品
      },
      // 分类商品列表返回的参数
      searchListData: {
        list: [],
        total: 0, // 总数
        pages: 0 // 总页数
      },
      selectedIndex: 0, // 选中项
      selectedCategoryId: null,
      categoryList: [], // 分类列表
      prodList: [], // 商品列表
      isLoadAll: false,

      // 分类滚动栏横向滚动距离
      categoryScrollLeft: 0,
      // 分类滚动的总宽度
      contentScrollW: 0,
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getSearchList()
    this.getPlatformCategoryList()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (this.pageQuery.pageNum < this.searchListData.pages) {
      this.pageQuery.pageNum = this.pageQuery.pageNum + 1
      this.getSearchList()
    } else {
      this.isLoadAll = true
    }
  },

  methods: {
    // 去商品详情页
    toDetail(supId) {
      uni.navigateTo({
        url: '/pages/detail/detail?spuId=' + supId
      })
    },
    /**
     * 获取平台一级分类
     */
    getPlatformCategoryList() {
      uni.showLoading()
      const params = {
        url: '/mall4cloud_product/ua/category/category_list',
        method: 'GET',
        data: {
          shopId: 0,
          parentId: 0
        },
        callBack: res => {
          this.categoryList = res
          uni.hideLoading()
          this.selectedCategoryId = res[0].categoryId
          this.getSearchList()
          this.getCategoryScrollWidth()
        }
      }
      http.request(params)
    },
    /**
     * 获取分类栏 scroll-view 滚动相关宽度
     */
    getCategoryScrollWidth() {
      const query = uni.createSelectorQuery().in(this)
      query.select('.category-box').boundingClientRect(data => {
        // scroll-view 的宽度
        this.contentScrollW = data.width
      }).exec()
      // 后面优化下这个地方
      setTimeout(() => {
        query.selectAll('.category-item').boundingClientRect(data => {
          let dataLen = data.length;
          for (let i = 0; i < dataLen; i++) {
            // scroll-view 各个分类项目的距离左边栏的距离
            this.categoryList[i].left = data[i].left
            // scroll-view 各个分类项目的宽度(看样式那里原来写死了20%，我改成自动宽度)
            this.categoryList[i].width = data[i].width
          }
        }).exec()
      }, 1)
     },
    scroll(e) {
      setTimeout(()=>{
        this.categoryScrollLeft = e.target.scrollLeft
      })
    },
    /**
     * 获取商品列表
     */
    getSearchList() {
      uni.showLoading()
      this.isLoadAll = false
      this.pageQuery.primaryCategoryId = this.selectedCategoryId
      const params = {
        url: '/mall4cloud_search/ua/search/simple_page',
        method: 'GET',
        data: this.pageQuery,
        callBack: res => {
          this.searchListData = res
          let list = []
          if (this.pageQuery.pageNum === 1) {
            list = res.list[0].spus
          } else {
            list = this.prodList
            list = list.concat(res.list[0].spus)
          }
          if (this.pageQuery.pageNum === this.searchListData.pages) {
            this.isLoadAll = true
          }
          this.prodList = list
          uni.hideLoading()
        }
      }
      http.request(params)
    },

    /**
     * 切换分类
     */
    switchCategory(e) {
      const index = parseInt(e.currentTarget.dataset.index)
      this.selectedCategoryId = parseInt(e.currentTarget.dataset.categoryid)
      if (this.selectedIndex === index) {
        return
      }
      this.selectedIndex = index
      this.pageQuery.pageNum = 1
      // scorll-view 实现居中显示的滚动位置(偏移值): 当前点击子元素距离左边栏的距离 - scroll-view 宽度的一半  + 当前点击子元素一半的宽度 - 父盒子边距(如有)
      this.categoryScrollLeft = this.categoryList[index].left - this.contentScrollW / 2 + this.categoryList[index].width / 2 - 15
      this.getSearchList()
    },

    // 跳转每日上新
    toDailyUpdate() {
      uni.navigateTo({
        url: '/pages/daily-update/daily-update'
      })
    }

  }
}
</script>

<style>
@import "./new-prods.css";
</style>
