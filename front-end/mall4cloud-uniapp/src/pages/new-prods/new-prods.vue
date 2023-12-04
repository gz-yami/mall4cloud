<template>
  <view class="new-prods">
    <!-- banner -->
    <view class="banner">
      <image src="/static/img/banner2.png" />
    </view>

    <!-- 新品分类 -->
    <view class="new-category">
      <scroll-view
        scroll-x="true"
        class="category-box"
        :scroll-left="categoryScrollLeft"
        scroll-with-animation
        @scroll="scroll"
      >
        <block
          v-for="(item, index) in categoryList"
          :key="index"
        >
          <view
            class="category-item"
            :class="{ active: selectedIndex === index }"
            :data-index="index"
            :data-categoryid="item.categoryId"
            @tap="switchCategory"
          >
            {{ item.name }}
          </view>
        </block>
      </scroll-view>
    </view>

    <!-- 产品列表 -->
    <view class="prods">
      <block
        v-for="(item, index) in prodList"
        :key="index"
      >
        <view
          class="item"
          @tap="toDetail(item.spuId)"
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
      v-if="prodList.length === 0"
      class="empty"
    >
      <view class="img">
        <image src="/static/empty-img/not-found.png" />
      </view>
      <view class="text">
        没有找到对应商品，看看别的吧
      </view>
    </view>

    <view
      v-if="isLoadAll && prodList.length > 0"
      class="nomore"
    >
      没有更多了，看看别的吧
    </view>
  </view>
</template>

<script setup>

const wxs = number()

const Data = reactive({
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
  contentScrollW: 0
})
const { selectedIndex, categoryList, prodList, isLoadAll, categoryScrollLeft } = toRefs(Data)

onLoad(() => {
  getSearchList()
  getPlatformCategoryList()
})

onReachBottom(() => {
  if (Data.pageQuery.pageNum < Data.searchListData.pages) {
    Data.pageQuery.pageNum = Data.pageQuery.pageNum + 1
    getSearchList()
  } else {
    Data.isLoadAll = true
  }
})

// 去商品详情页
const toDetail = (supId) => {
  uni.navigateTo({
    url: '/pages/detail/detail?spuId=' + supId
  })
}

/**
 * 获取平台一级分类
 */
const getPlatformCategoryList = () => {
  uni.showLoading()
  const params = {
    url: '/mall4cloud_product/ua/category/category_list',
    method: 'GET',
    data: {
      shopId: 0,
      parentId: 0
    }
  }
  http.request(params).then(res => {
    Data.categoryList = res
    uni.hideLoading()
    Data.selectedCategoryId = res[0].categoryId
    getSearchList()
    getCategoryScrollWidth()
  })
}

/**
 * 获取分类栏 scroll-view 滚动相关宽度
 */
const getCategoryScrollWidth = () => {
  const query = uni.createSelectorQuery()
  query.select('.category-box').boundingClientRect(data => {
    // scroll-view 的宽度
    Data.contentScrollW = data.width
  }).exec()
  // 后面优化下这个地方
  setTimeout(() => {
    query.selectAll('.category-item').boundingClientRect(data => {
      const dataLen = data.length
      for (let i = 0; i < dataLen; i++) {
        // scroll-view 各个分类项目的距离左边栏的距离
        Data.categoryList[i].left = data[i].left
        // scroll-view 各个分类项目的宽度(看样式那里原来写死了20%，我改成自动宽度)
        Data.categoryList[i].width = data[i].width
      }
    }).exec()
  }, 1)
}

const scroll = (e) => {
  setTimeout(() => {
    Data.categoryScrollLeft = e.target.scrollLeft
  })
}

/**
 * 获取商品列表
 */
const getSearchList = () => {
  uni.showLoading()
  Data.isLoadAll = false
  Data.pageQuery.primaryCategoryId = Data.selectedCategoryId
  const params = {
    url: '/mall4cloud_search/ua/search/simple_page',
    method: 'GET',
    data: Data.pageQuery
  }
  http.request(params).then(res => {
    Data.searchListData = res
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
 * 切换分类
 */
const switchCategory = (e) => {
  const index = parseInt(e.currentTarget.dataset.index)
  Data.selectedCategoryId = parseInt(e.currentTarget.dataset.categoryid)
  if (Data.selectedIndex === index) {
    return
  }
  Data.selectedIndex = index
  Data.pageQuery.pageNum = 1
  // scorll-view 实现居中显示的滚动位置(偏移值): 当前点击子元素距离左边栏的距离 - scroll-view 宽度的一半  + 当前点击子元素一半的宽度 - 父盒子边距(如有)
  Data.categoryScrollLeft = Data.categoryList[index].left - Data.contentScrollW / 2 + Data.categoryList[index].width / 2 - 15
  getSearchList()
}

</script>

<style lang="scss" scoped>
@use "new-prods";
</style>
