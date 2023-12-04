<template>
  <view class="page-shop-prods">
    <view class="shop-prods">
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
            <view class="name">
              {{ shopInfo.shopName }}
            </view>
            <view class="focus-box">
              <view
                v-if="shopInfo.type"
                class="self"
              >
                自营
              </view>
            </view>
          </view>
        </view>
        <view class="sortbar">
          <view class="item active">
            默认
          </view>
          <view
            class="list-style"
            @tap="changeListStyle"
          >
            <image
              v-if="isLineProds"
              src="/static/images/list-row-w.png"
            />
            <image
              v-else
              src="/static/images/list-line-w.png"
            />
          </view>
        </view>
      </view>

      <!-- 商品列表 -->
      <view
        v-if="prodList.length"
        class="prods-box"
        :class="{ 'add-bg': !isLineProds }"
      >
        <view :class="[isLineProds ? 'line-prods' : 'prods']">
          <block
            v-for="(item, index) in prodList"
            :key="index"
          >
            <view
              class="item"
              @tap="detail(item.spuId)"
            >
              <view class="img">
                <image :src="item.mainImgUrl" />
              </view>
              <view class="text-box">
                <view class="name">
                  {{ item.spuName }}
                </view>
                <view class="price-box">
                  <view class="price">
                    <view class="symbol">
                      ￥
                    </view>
                    <view class="big">
                      {{ price(item.priceFee) }}
                    </view>
                  </view>
                </view>
              </view>
            </view>
          </block>
        </view>
        <view class="nomore">
          没有更多了，看看别的吧
        </view>
      </view>
      <view
        v-else
        class="nomore"
      >
        暂无商品，看看别的吧
      </view>

      <!-- 店铺tabbar -->
      <view class="shop-tabbar">
        <view
          class="item"
          @tap="goShopIndex"
        >
          <view class="icon">
            <!-- <image src="/static/images/shop-index-r.png" /> -->
            <image src="/static/images/shop-index.png" />
          </view>
          <view class="text">
            首页
          </view>
        </view>
        <view
          class="item active"
          @tap="goShopProds"
        >
          <view class="icon">
            <image src="/static/images/shop-prods-r.png" />
            <!-- <image src="/static/images/shop-prods.png" /> -->
          </view>
          <view class="text">
            商品
          </view>
        </view>
        <view
          class="item"
          @tap="goShopCategory"
        >
          <view class="icon">
            <!-- <image src="/static/images/shop-category-r.png" /> -->
            <image src="/static/images/shop-category.png" />
          </view>
          <view class="text">
            分类
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue'

const Data = reactive({
  shopId: 0,
  shopInfo: {},
  shopSecondaryCategoryId: '',
  keyword: '',
  pageNum: 1,
  pageSize: 8,
  total: 1,
  pages: 1,
  prodList: [],
  isLineProds: true // 列表格式，默认横排展示
})
const { shopInfo, prodList, isLineProds } = toRefs(Data)

onReachBottom(() => {
  if (Data.pageNum < Data.pages) {
    Data.pageNum++
    getProd()
  }
})

onLoad((options) => {
  Data.shopId = options.shopId
  Data.shopSecondaryCategoryId = options.shopSecondaryCategoryId
  Data.keyword = options.keyword
  Data.shopInfo = uni.getStorageSync('cloudShopInfo')
  getProd()
})

const price = (value) => {
  return (value / 100).toFixed(2)
}

// 去商品详情
const detail = (spuId) => {
  uni.navigateTo({
    url: '/pages/detail/detail?spuId=' + spuId
  })
}

/**
 * 获取商品列表
 */
const getProd = () => {
  const params = {
    url: '/mall4cloud_search/ua/search/simple_page',
    method: 'GET',
    data: {
      shopId: Data.shopId,
      pageNum: Data.pageNum,
      pageSize: Data.pageSize,
      shopSecondaryCategoryId: Data.shopSecondaryCategoryId
    }

  }
  http.request(params).then(res => {
    if (Data.pageNum !== 1) {
      Data.prodList = Data.prodList.concat(res.list[0].spus)
    } else {
      Data.prodList = res.list[0].spus
    }
    Data.total = res.total
    Data.pages = res.pages
  })
}

// 切换tabbar
const goShopIndex = () => {
  uni.navigateTo({
    url: `/pages/shop-index/shop-index?shopId=${Data.shopId}`
  })
}

const goShopProds = () => {
  uni.navigateTo({
    url: `/pages/shop-prods/shop-prods?shopId=${Data.shopId}`
  })
}

const goShopCategory = () => {
  uni.navigateTo({
    url: `/pages/shop-category/shop-category?shopId=${Data.shopId}`
  })
}

// 切换列表样式
const changeListStyle = () => {
  Data.isLineProds = !Data.isLineProds
}
</script>

<style lang="scss" scoped>
@use "../shop-index/shop-index";
</style>
