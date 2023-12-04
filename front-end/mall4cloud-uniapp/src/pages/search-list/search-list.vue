<template>
  <view class="search-list">
    <!-- 搜索框 -->
    <view class="search-top">
      <view
        class="search-box"
        @tap="toSearchPage(1)"
      >
        <view class="icon">
          <image src="/static/images/search.png" />
        </view>
        <div
          v-if="showKeyword"
          class="key-tag"
        >
          {{ showKeyword }}<span
            class="close"
            @tap.stop="toSearchPage(0)"
          >x</span>
        </div>
        <div
          v-if="!showKeyword"
          class="text"
        >
          输入关键字搜索
        </div>
      </view>
      <view
        v-if="state !== 3"
        class="list-style"
        @tap="changeListStyle"
      >
        <image
          v-if="isLineProds"
          src="/static/images/list-row.png"
        />
        <image
          v-else
          src="/static/images/list-line.png"
        />
      </view>
    </view>
    <!-- 商品头部店铺 -->
    <view
      v-if="state !== 3 && shopInfo"
      class="head-shop-info"
    >
      <view class="shop-info-box">
        <view class="con">
          <view class="logo">
            <image :src="shopInfo.shopLogo" />
          </view>
          <view class="text">
            <view class="name">
              {{ shopInfo.shopName }}
            </view>
            <view class="focus-box">
              <view
                v-if="shopInfo.type === 1"
                class="self"
              >
                自营
              </view>
            </view>
          </view>
          <view
            class="btn"
            @tap="toShopPage(shopInfo.shopId)"
          >
            进店
          </view>
        </view>
      </view>
    </view>
    <!-- 筛选栏 -->
    <view class="sortbar">
      <view
        class="item"
        :class="{ active: state === 0 }"
        @tap="switchSearchList(0)"
      >
        默认
      </view>
      <view
        class="item"
        :class="{ active: state === 1, 'arrow-up': pageQuery.sort === 2, 'arrow-down': pageQuery.sort === 3 }"
        @tap="switchSearchList(1)"
      >
        销量
      </view>
      <view
        class="item"
        :class="{ active: state === 2, 'arrow-up': pageQuery.sort === 4, 'arrow-down': pageQuery.sort === 5 }"
        @tap="switchSearchList(2)"
      >
        价格
      </view>
    </view>
    <!-- 产品列表 -->
    <view
      v-if="state !== 3 && prodList.length > 0"
      :class="[isLineProds ? 'line-prods' : 'prods']"
    >
      <block
        v-for="(item, index) in prodList"
        :key="index"
      >
        <view
          class="item"
          @tap="toProdDetail(item.spuId)"
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
                  {{ wxs.parsePrice(item.priceFee)[0] }}
                </view>
                <view class="symbol">
                  .{{ wxs.parsePrice(item.priceFee)[1] }}
                </view>
              </view>
            </view>
          </view>
        </view>
      </block>
    </view>
    <!-- 无商品显示 -->
    <view
      v-if="state !== 3 && prodList.length === 0"
      class="empty"
    >
      <view class="img">
        <image src="/static/empty-img/not-found.png" />
      </view>
      <view class="text">
        没有找到对应商品，看看别的吧
      </view>
    </view>
    <!-- 店铺列表 -->
    <view
      v-if="state === 3 && shopData.total > 0"
      class="shop-list"
    >
      <block
        v-for="(item, index) in shopData.list"
        :key="index"
      >
        <view class="item">
          <view class="shop-info">
            <view class="logo">
              <image :src="item.shopLogo" />
            </view>
            <view class="text">
              <view class="name">
                {{ item.shopName }}
              </view>
              <view class="focus-box">
                <view
                  v-if="item.type === 1"
                  class="self"
                >
                  自营
                </view>
              </view>
            </view>
            <view
              class="btn"
              @tap="toShopPage(item.shopId)"
            >
              进店
            </view>
          </view>
          <view class="shop-prods">
            <block
              v-for="(prodItem, prodIndex) in item.spuList"
              :key="prodIndex"
            >
              <view
                class="p-item"
                @tap="toProdDetail(prodItem.spuId)"
              >
                <view class="img">
                  <image :src="prodItem.mainImgUrl" />
                </view>
                <view class="price">
                  ￥{{ wxs.parsePrice(prodItem.priceFee)[0] + '.' + wxs.parsePrice(prodItem.priceFee)[1] }}
                </view>
              </view>
            </block>
          </view>
        </view>
      </block>
    </view>
    <!-- 无店铺 -->
    <view
      v-if="state === 3 && shopData.total === 0"
      class="empty"
    >
      <view class="img">
        <image src="/static/empty-img/not-found.png" />
      </view>
      <view class="text">
        没有找到对应店铺，看看别的吧
      </view>
    </view>
    <!-- 加载全部 -->
    <view
      v-if="isLoadAll"
      class="nomore"
    >
      没有更多了，看看别的吧
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue'

const wxs = number()

const Data = reactive({
  isLineProds: true, // 列表格式，默认横排展示
  isLoadAll: false, // 是否加载全部内容
  showKeyword: '', // 用来展示的关键词
  state: 0, // 0:商品 1:商品销量排序 2:商品价格排序 3:店铺
  // 商品相关
  pageQuery: { // 请求的参数
    pageSize: 10, // 每页展示的个数
    pageNum: 1, // 当前页
    keyword: '', // 关键词
    categoryId: '', // 第三级分类ID
    sort: '' // 商品排序
  },
  prodList: [], // 商品列表
  searchListData: { // 搜索数据
    list: [],
    total: 0, // 总数
    pages: 0 // 总页数
  },
  shopInfo: {}, // 关键词搜索中的店铺信息
  // 店铺相关
  pageNum: 1, // 当前页
  shopData: { // 店铺数据
    list: [],
    total: 0,
    page: 0
  }
})
const { isLineProds, isLoadAll, showKeyword, state, pageQuery, prodList, shopInfo, shopData } = toRefs(Data)

onLoad((options) => {
  Data.pageQuery.categoryId = options.categoryId
  Data.showKeyword = options.keyword
  Data.pageQuery.keyword = Data.pageQuery.categoryId ? '' : Data.showKeyword
  getSearchList()
})

onReachBottom(() => {
  if (Data.state !== 3) {
    // 商品下拉触底
    if (Data.pageQuery.pageNum < Data.searchListData.pages) {
      Data.pageQuery.pageNum = Data.pageQuery.pageNum + 1
      getSearchList()
    } else {
      Data.isLoadAll = true
    }
  } else {
    // 店铺下拉触底
    if (Data.pageNum < Data.shopData.pages) {
      Data.pageNum = Data.pageNum + 1
      getShopList()
    } else {
      Data.isLoadAll = true
    }
  }
})

/**
 * get-搜索列表
 */
const getSearchList = () => {
  uni.showLoading()
  const params = {
    url: '/mall4cloud_search/ua/search/simple_page',
    method: 'GET',
    data: Data.pageQuery
  }
  http.request(params).then(res => {
    Data.searchListData = res
    Data.shopInfo = res.list[0].shopInfo
    let list = []
    if (Data.pageQuery.pageNum === 1) {
      list = res.list[0].spus
    } else {
      list = Data.prodList
      list = list.concat(res.list[0].spus)
    }
    if (Data.pageQuery.pageNum === Data.searchListData.pages) {
      Data.isLoadAll = true
    }
    Data.prodList = list
    uni.hideLoading()
  })
}

/**
 * get-店铺列表
 */
const getShopList = () => {
  uni.showLoading()
  const params = {
    url: '/mall4cloud_multishop/ua/shop_detail/page',
    method: 'get',
    data: {
      pageNum: Data.pageNum,
      pageSize: 10,
      shopName: Data.pageQuery.keyword
    }
  }
  http.request(params).then(res => {
    let list = []
    if (Data.pageNum === 1) {
      list = res.list
    } else {
      list = Data.shopData.list
      list = list.concat(res.list)
    }
    Data.shopData = {
      list,
      pages: res.pages,
      total: res.total
    }
    // 当前页等于总页数,则提示加载完成
    if (Data.pageNum == Data.shopData.pages) {
      Data.isLoadAll = true
    }
    uni.hideLoading()
  })
}

/**
 * 切换商品列表样式
 */
const changeListStyle = () => {
  Data.isLineProds = !Data.isLineProds
}

/**
 * 切换搜索条件
 * @param state 0:商品 1:商品销量排序 2:商品价格排序 3:店铺
 */
const switchSearchList = (state) => {
  let sort = ''
  uni.pageScrollTo({
    duration: 0,
    scrollTop: 0
  })
  if ((state === 0 || state === 3) && Data.state === state) {
    return
  }
  // 初始化
  Data.isLoadAll = false
  Data.pageQuery.pageNum = 1
  Data.shopData = {}
  Data.searchListData = {}
  Data.prodList = []
  Data.shopInfo = {}

  if (state === 1) {
    sort = Data.state === state ? Data.pageQuery.sort === 2 ? 3 : 2 : 2
  } else if (state === 2) {
    sort = Data.state === state ? Data.pageQuery.sort === 4 ? 5 : 4 : 4
  }
  Data.state = state
  Data.pageQuery.sort = sort

  if (Data.state !== 3) {
    getSearchList()
  } else {
    getShopList()
  }
}

/**
 * to-搜索页
 * @param type 0:删除关键词跳转 1:直接跳转
 */
const toSearchPage = (type) => {
  const url = type === 0 ? '/pages/search-page/search-page' : '/pages/search-page/search-page?keyword=' + Data.showKeyword
  uni.redirectTo({ url })
}

/**
 * to-店铺页
 */
const toShopPage = (shopId) => {
  uni.navigateTo({
    url: '/pages/shop-page/shop-index?shopId=' + shopId
  })
}

/**
 *  to-商品详情页
 */
const toProdDetail = (spuId) => {
  uni.navigateTo({
    url: '/pages/detail/detail?spuId=' + spuId
  })
}
</script>

<style lang="scss" scoped>
@use "search-list";
</style>
