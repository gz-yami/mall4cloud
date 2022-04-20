<template>
  <view class="search-page">
    <!-- 搜索栏 -->
    <view class="search-top">
      <view class="search-box">
        <view class="icon">
          <image src="/static/images/search.png" />
        </view>
        <input placeholder="输入商品名或店铺名" class="input" :value="searchKeyword" @input="getSearchContent" @confirm="toSearchListPage">
      </view>
      <view class="search-cancel" @tap="backPreviousPage">取消</view>
    </view>

    <!-- 搜索历史 -->
    <view v-if="recentSearchHistory && recentSearchHistory.length > 0" class="search-con">
      <view class="search-tit">
        <view class="text">搜索历史</view>
        <view class="del" @tap="clearSearchHistory">
          <image src="/static/images/del.png" />
        </view>
      </view>
      <view class="search-history">
        <block v-for="(item, index) in recentSearchHistory" :key="index">
          <view class="item" :data-keyword="item" @tap="clickKeywordSearch">{{ item }}</view>
        </block>
      </view>
    </view>

    <!-- 热门搜索 -->
    <view v-if="hotSearchList && hotSearchList.length > 0" class="search-con">
      <view class="search-tit">
        <view class="text">
          热门搜索
          <view class="hot-icon">
            <image src="/static/images/search-hot.png" />
          </view>
        </view>
      </view>
      <view class="search-hot">
        <block v-for="(item, index) in hotSearchList" :key="index">
          <view class="item" :data-keyword="item.content" @tap="clickKeywordSearch">{{ item.content }}</view>
        </block>
      </view>
    </view>
  </view>
</template>

<script>
const http = require('../../utils/http.js')
export default {
  data() {
    return {
      searchKeyword: '', // 关键词
      // 请求的参数
      pageQuery: {
        shopId: 0
      },
      recentSearchHistory: [], // 搜索历史列表
      hotSearchList: [] // 热门搜索列表
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.searchKeyword = options.keyword
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.getHotSearchList()
    this.getRecentSearchHistory()
  },

  methods: {
    /**
     * 获取热搜列表
     */
    getHotSearchList() {
      const params = {
        url: '/mall4cloud_multishop/ua/app/hot_search/list',
        method: 'GET',
        data: this.pageQuery,
        callBack: res => {
          this.hotSearchList = res
        }
      }
      http.request(params)
    },

    /**
     * 搜索店铺/商品(跳转到搜索列表页)
     */
    toSearchListPage() {
      // 判断搜索框是否为空
      if (!this.searchKeyword || !this.searchKeyword.trim()) {
        uni.showToast({
          title: '请输入关键字',
          icon: 'none'
        })
        return
      }
      let recentSearchHistory = uni.getStorageSync('recentSearchHistory') || []
      // 过滤掉重复的搜索关键词
      recentSearchHistory = recentSearchHistory.filter(item => item !== this.searchKeyword)
      recentSearchHistory.unshift(this.searchKeyword)
      if (recentSearchHistory.length > 10) {
        recentSearchHistory.pop()
      }
      // 将搜索内容放到缓存中
      uni.setStorageSync('recentSearchHistory', recentSearchHistory)
      // 跳转到搜索列表页
      uni.redirectTo({
        url: '/pages/search-list/search-list?keyword=' + this.searchKeyword
      })
    },

    /**
     * 获取搜索历史
     */
    getRecentSearchHistory() {
      this.recentSearchHistory = uni.getStorageSync('recentSearchHistory')
    },

    /**
     * 清空搜索历史
     */
    clearSearchHistory() {
      uni.removeStorageSync('recentSearchHistory')
      this.getRecentSearchHistory()
    },

    /**
     * 点击历史搜索/热门搜索关键词跳转到搜索列表页
     */
    clickKeywordSearch(e) {
      this.searchKeyword = e.currentTarget.dataset.keyword
      this.toSearchListPage()
    },

    /**
     * 获取搜索框字符串
     */
    getSearchContent(e) {
      this.searchKeyword = e.detail.value
    },

    /**
     * 返回上一页
     */
    backPreviousPage() {
      // #ifndef H5
      uni.navigateBack()
      // #endif

      // #ifdef H5
      history.back()
      // #endif
    }
  }
}
</script>
<style>
@import "./search-page.css";
</style>
