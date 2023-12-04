<template>
  <view class="page-detail">
    <!-- 头部 -->
    <!--  -->
    <view
      class="det-header"
      :style="{ opacity: topTabOpacity, zIndex: topTabOpacity > 0.2 ? '1' : '-1' }"
    >
      <view
        :class="['item', topTabSts === 0 ? 'active' : '']"
        @tap="hanleTopTabClick(0)"
      >
        商品
      </view>
      <view
        :class="['item', topTabSts === 2 ? 'active' : '']"
        @tap="hanleTopTabClick(2)"
      >
        详情
      </view>
    </view>

    <!-- 大图 -->
    <swiper
      class="pic-box"
      indicator-dots="true"
      indicator-color="rgba(255,252,255, .3)"
      indicator-active-color="rgba(255,252,255, .6)"
      autoplay="true"
      circular="true"
    >
      <swiper-item
        v-for="(img, imgIdx) in imgList"
        :key="imgIdx"
        class="item"
      >
        <image :src="img" />
      </swiper-item>
    </swiper>

    <!-- 名称、价格 -->
    <view class="name-box">
      <view class="name">
        {{ prodInfo.name }}
      </view>
      <view class="action-box">
        <view class="price-box">
          <view class="price">
            <view class="symbol">
              ￥
            </view>
            <view class="big">
              {{ wxs.parsePrice(defaultSku.priceFee)[0] }}
            </view>
            <view class="symbol">
              .{{ wxs.parsePrice(defaultSku.priceFee)[1] }}
            </view>
          </view>
          <view class="stock">
            仅剩{{ defaultSku.stock }}件
          </view>
        </view>
      </view>
    </view>

    <!-- sku、配送 -->
    <view class="det-con">
      <view class="item">
        <view class="tit">
          已选
        </view>
        <view
          class="con sku-con"
          @tap="isShowSkuPopup = true"
        >
          <view
            v-if="defaultSku.skuName"
            class="s-item"
          >
            <block
              v-for="(skuItem, index) in selectedProp"
              :key="index"
            >
              <text decode="true">
                {{ index < selectedProp.length - 1 ? skuItem + '，' : skuItem + '&nbsp; &nbsp;' }}
              </text>
            </block>
          </view>
          {{ prodNumber }} 件
        </view>
      </view>
    </view>

    <!-- 店铺 -->
    <view class="shop-box">
      <view class="shop-info">
        <view class="info">
          <view class="img">
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
        </view>
        <view
          class="go-shop"
          @tap="toShopIndex"
        >
          进店逛逛
        </view>
      </view>
    </view>

    <!-- 详情 -->
    <div class="det-det">
      <div class="tit">
        商品详情
      </div>
      <div class="con">
        <rich-text :nodes="prodDetail" />
      </div>
    </div>

    <!-- 底部 -->
    <view class="det-foot">
      <view class="actions">
        <view
          class="item"
          @tap="toIndex"
        >
          <view class="img">
            <image src="/static/images/detail-index.png" />
          </view>
          <view class="text">
            首页
          </view>
        </view>
        <view
          class="item"
          @tap="toCart"
        >
          <view class="img">
            <image src="/static/images/detail-cart.png" />
            <view
              v-if="cartCount"
              class="mark"
            >
              {{ cartCount }}
            </view>
          </view>
          <view class="text">
            购物车
          </view>
        </view>
      </view>
      <view class="btns">
        <view
          class="btn"
          @tap="addCart"
        >
          加入购物车
        </view>
        <view
          class="btn btn-r"
          @tap="goSubmit"
        >
          立即购买
        </view>
      </view>
    </view>

    <!-- 选择sku弹窗 -->
    <view
      class="popup"
      :class="{ show: isShowSkuPopup }"
    >
      <!-- <SkuSelectPopup :prod-info="prodInfo" /> -->
      <view
        class="popup-mask"
        @tap="closePopup"
      />
      <view class="popup-con-bottom check-sku">
        <view class="goods-box">
          <view class="img">
            <image :src="prodInfo.mainImgUrl" />
          </view>
          <view class="info">
            <view class="name">
              {{ prodInfo.name }}
            </view>
            <view class="price">
              <view class="symbol">
                ￥
              </view>
              <view class="big">
                {{ wxs.parsePrice(defaultSku.priceFee)[0] }}
              </view>
              <view class="symbol">
                .{{ wxs.parsePrice(defaultSku.priceFee)[1] }}
              </view>
            </view>
          </view>
          <view
            class="close"
            @tap="closePopup"
          >
            <image src="/static/images/close.png" />
          </view>
        </view>
        <view class="con-box">
          <block
            v-for="(skuLine, key) in skuGroup"
            :key="key"
          >
            <view
              v-if="defaultSku.skuName"
              class="sku-item"
            >
              <view class="item-tit">
                {{ key }}
              </view>
              <view class="item-box">
                <view
                  v-for="skuLineItem in skuLine"
                  :key="skuLineItem"
                  :class="['item-item', selectedProp.indexOf(skuLineItem) !== -1 ? 'active' : '', isSkuLineItemNotOptional(allProperties, selectedPropObj, key, skuLineItem, propKeys) ? 'cannot' : '']"
                  @tap="toChooseItem(skuLineItem, key)"
                >
                  {{ skuLineItem }}
                </view>
              </view>
            </view>
          </block>
          <view class="prod-number">
            <view class="item-tit">
              数量
            </view>
            <view class="number-box">
              <view
                :class="['minus', prodNumber === 1 ? 'limit' : '']"
                @tap="changeNumber(-1)"
              />
              <input
                v-model="prodNumber"
                type="number"
                class="number"
                maxlength="4"
              >
              <view
                :class="['plus', isMaxProdNumber ? 'limit' : '']"
                @tap="changeNumber(1)"
              />
            </view>
          </view>
        </view>
        <view class="btn-box">
          <view
            :class="['btn', findSku ? '' : 'gray']"
            class=""
            @tap="skuPopupConfirm"
          >
            确定
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive, watch } from 'vue'

const wxs = number()

const Data = reactive({
  isShowSkuPopup: false, // 显示sku弹窗
  isShowCommentPopup: false, // 显示评价弹窗
  spuId: 0, // 商品id
  shopId: 0,
  prodInfo: {}, // 商品详情
  shopInfo: {}, // 店铺详情
  deliveryModeVO: {}, // 商品配送方式
  imgList: [], // 商品轮播图
  prodDetail: '', // 商品详情富文本

  // pageScorllTop: 0, // 页面滚动的高度
  topTabSts: 0, // 顶部 topTab 选中的项目
  topTabOpacity: 0, // 顶部 topTab 的透明度

  // sku 商品规格
  findSku: true,
  defaultSku: {},
  skuList: [],
  selectedProp: [],
  selectedPropObj: {},
  selectedProperties: '',
  propKeys: [],
  allProperties: [],
  skuGroup: {},
  skuPopConfirmType: 0, // 规格弹窗类型: 0普通sku切换; 1加入购物车; 2立即购买

  prodNumber: 1, // 商品数量
  hasMaxNumber: 0, // 是否限购
  maxNum: 10, // 限购数量(仅团购商品) TODO:假的,以后要删的~()
  isMaxProdNumber: false, // 是否达到限购或达到最大库存

  cartCount: 0
})
const { isShowSkuPopup, prodInfo, shopInfo, imgList, prodDetail, topTabSts, topTabOpacity, findSku, defaultSku, selectedProp, selectedPropObj, propKeys, allProperties, skuGroup, prodNumber, isMaxProdNumber, cartCount } = toRefs(Data)

/**
 * 生命周期函数--监听页面加载
 */
onLoad((options) => {
  if (options.spuId) {
    Data.spuId = options.spuId
  }
})

onShow(() => {
  // 获取商品详情
  getProdInfo()

  // 获取购物车数字
  getCartCount()
})

onPageScroll((e) => {
  Data.topTabOpacity = e.scrollTop / 46
})

watch(() => Data.prodNumber, (nv) => {
  if (nv >= Data.defaultSku.stock) {
    Data.isMaxProdNumber = true
  } else {
    Data.isMaxProdNumber = false
  }
})

/**
 * 规格弹窗底部确定按钮
 */
const skuPopupConfirm = () => {
  if (!Data.defaultSku.stock) {
    uni.showToast({
      title: '所选规格无货',
      duration: 2000,
      icon: 'none'
    })
    return
  }
  if (Data.skuPopConfirmType === 1) {
    const params = {
      url: '/mall4cloud_product/a/shop_cart/change_item',
      method: 'POST',
      data: {
        skuId: Data.defaultSku.skuId,
        spuId: Data.defaultSku.spuId,
        count: Data.prodNumber,
        oldSkuId: ''
      }
    }
    http.request(params).then(() => {
      uni.showToast({
        title: '加入购物车成功~',
        duration: 2000,
        icon: 'none'
      })
      getCartCount()
    })
  }
  if (Data.skuPopConfirmType === 2) {
    uni.setStorageSync('cloudShopCartItem', JSON.stringify({
      count: Data.prodNumber,
      shopId: Data.prodInfo.shopId,
      skuId: Data.defaultSku.skuId,
      spuId: Data.spuId
    }))
    uni.navigateTo({
      url: '/pages/submit-order/submit-order?orderEntry=1'
    })
  }
  Data.skuPopConfirmType = 0
  closePopup()
}

/**
* 加入购物车按钮
*/
const addCart = () => {
  Data.skuPopConfirmType = 1
  Data.isShowSkuPopup = true
}

/**
* 修改商品数量
*/
const changeNumber = (num) => {
  let prodNumber = parseInt(Data.prodNumber)

  if (num < 0) { // 点击数量-
    if (prodNumber === 1) {
      return
    } else {
      prodNumber += num
    }
  } else { // 点击数量+
    // 限购判断
    if (Data.hasMaxNumber) {
      if (prodNumber >= Data.maxNum) {
        uni.showToast({
          title: `限购 ${Data.maxNum} 件`,
          duration: 2000,
          icon: 'none'
        })
      }
      if (Data.defaultSku.stock < prodNumber) {
        uni.showToast({
          title: '库存不足',
          duration: 2000,
          icon: 'none'
        })
      }
      if (prodNumber < Data.maxNum) {
        prodNumber += num
      }
    } else { // 不限购
      if (prodNumber < Data.defaultSku.stock) {
        prodNumber += num
      } else {
        uni.showToast({
          title: '库存不足',
          duration: 2000,
          icon: 'none'
        })
      }
    }
  }
  Data.prodNumber = prodNumber
}

/**
* 获取商品详情
*/
const getProdInfo = () => {
  uni.showLoading()
  const params = {
    url: '/mall4cloud_product/ua/spu/prod_info',
    method: 'GET',
    data: {
      spuId: Data.spuId
    }
  }
  http.request(params).then((res) => {
    Data.shopId = res.shopId
    getShopInfo()
    uni.hideLoading()
    let imgList = []
    if (res.imgUrls) {
      imgList = res.imgUrls.split(',')
    }
    // 组装sku
    groupSkuProp(res.skus, res.priceFee)

    Data.prodInfo = res
    Data.deliveryModeVO = res.deliveryModeVO
    Data.prodDetail = util.formatHtml(res.detail)
    Data.imgList = imgList
    Data.skuList = res.skus
  })
}

/**
* 获取店铺信息
*/
const getShopInfo = () => {
  const params = {
    url: '/mall4cloud_multishop/ua/shop_detail/head_info',
    method: 'GET',
    data: {
      shopId: Data.shopId
    }
  }
  http.request(params).then((res) => {
    Data.shopInfo = res
  })
}

/**
* 组装SKU
*/
const groupSkuProp = (skuList, defaultPrice) => {
  if (skuList.length == 1 && skuList[0].properties == '') {
    Data.defaultSku = skuList[0] || {}
    // Data.setDefaultGroupSku();
    return
  }
  const skuGroup = {}
  const allProperties = []
  const propKeys = []
  const selectedPropObj = {}
  let defaultSku = null
  for (let i = 0; i < skuList.length; i++) {
    let isDefault = false
    if (!defaultSku && skuList[i].priceFee == defaultPrice) { // 找到和商品价格一样的那个SKU，作为默认选中的SKU
      defaultSku = skuList[i]
      isDefault = true
    }
    const properties = skuList[i].properties // 版本:公开版;颜色:金色;内存:64GB
    allProperties.push(properties)
    const propList = properties.split(';') // ["版本:公开版","颜色:金色","内存:64GB"]
    for (let j = 0; j < propList.length; j++) {
      const propval = propList[j].split(':') // ["版本","公开版"]
      let props = skuGroup[propval[0]] // 先取出 版本对应的值数组
      // 如果当前是默认选中的sku，把对应的属性值 组装到selectedProp
      if (isDefault) {
        propKeys.push(propval[0])
        selectedPropObj[propval[0]] = propval[1]
      }

      if (props == undefined) {
        props = [] // 假设还没有版本，新建个新的空数组
        props.push(propval[1]) // 把 "公开版" 放进空数组
      } else {
        if (props.indexOf(propval[1]) === -1) { // 如果数组里面没有"公开版"
          props.push(propval[1]) // 把 "公开版" 放进数组
        }
      }
      skuGroup[propval[0]] = props // 最后把数据 放回版本对应的值
    }
  }
  Data.defaultSku = defaultSku || {}
  Data.propKeys = propKeys
  Data.selectedPropObj = selectedPropObj
  Data.skuGroup = skuGroup
  Data.allProperties = allProperties
  Data.skuList = skuList
  // Data.setDefaultGroupSku()
  parseSelectedObjToVals(skuList)
}

/**
* 将已选的 {key:val,key2:val2}转换成 [val,val2]
*/
const parseSelectedObjToVals = (skuList) => {
  const selectedPropObj = Data.selectedPropObj
  let selectedProperties = ''
  const selectedProp = []
  for (const key in selectedPropObj) {
    selectedProp.push(selectedPropObj[key])
    selectedProperties += key + ':' + selectedPropObj[key] + ';'
  }
  selectedProperties = selectedProperties.substring(0, selectedProperties.length - 1)
  Data.selectedProp = selectedProp
  Data.selectedProperties = selectedProperties
  Data.selectedPropObj = selectedPropObj

  let findSku = false
  for (let i = 0; i < skuList.length; i++) {
    if (skuList[i].properties == selectedProperties) {
      findSku = true
      Data.defaultSku = skuList[i]
      break
    }
  }
  Data.findSku = findSku
}

/**
* 判断当前的规格值 是否可以选
*/
const isSkuLineItemNotOptional = (allProperties, selectedPropObjpra, key, item, propKeys) => {
  const selectedPropObj = Object.assign({}, selectedPropObjpra)
  let properties = ''
  selectedPropObj[key] = item
  for (let j = 0; j < propKeys.length; j++) {
    properties += propKeys[j] + ':' + selectedPropObj[propKeys[j]] + ';'
  }
  properties = properties.substring(0, properties.length - 1)
  for (let i = 0; i < allProperties.length; i++) {
    if (properties == allProperties[i]) {
      return false
    }
  }
  for (let i = 0; i < allProperties.length; i++) {
    if (allProperties[i].indexOf(item) >= 0) {
      return true
    }
  }
  return false
}

/**
* 规格点击事件
*/
const toChooseItem = (skuLineItem, key) => {
  Data.selectedPropObj[key] = skuLineItem
  parseSelectedObjToVals(Data.skuList)
}

/**
* 顶部Tab栏点击，页面滚动至指定位置
* @param {number} type 0页面置顶; 1滚动至评价; 2滚动至详情
*/
const hanleTopTabClick = (type) => {
  Data.topTabSts = type
  if (type === 0) {
    uni.pageScrollTo({
      scrollTop: 0,
      duration: 100
    })
    return
  }
  let className = ''
  className = type === 1 ? 'comment-box' : 'det-det'
  uni.createSelectorQuery().select('.' + className).boundingClientRect(data => { // 目标节点
    uni.createSelectorQuery().select('.detail').boundingClientRect(res => { // 最外层盒子节点
      let scrollTop = 0
      if (data.top < 0) {
        scrollTop = Math.abs(res.top) - Math.abs(data.top) - 60
      } else {
        scrollTop = Math.abs(res.top) + Math.abs(data.top) - 60
      }
      uni.pageScrollTo({
        duration: 100,
        scrollTop
      })
    }).exec()
  }).exec()
}

/**
* 隐藏弹窗
*/
const closePopup = () => {
  Data.isShowDiscountPopup = false
  Data.isShowSkuPopup = false
  Data.isShowCommentPopup = false
  Data.skuPopConfirmType = 0
}

/**
* 立即购买
*/
const goSubmit = () => {
  Data.skuPopConfirmType = 2
  Data.isShowSkuPopup = true
}

/**
* 获取/更新购物车数据
*/
const getCartCount = () => {
  const params = {
    url: '/mall4cloud_product/a/shop_cart/prod_count',
    method: 'GET',
    data: {}
  }
  if (uni.getStorageSync('cloudToken')) {
    http.request(params).then((res) => {
      if (res) {
        Data.cartCount = res
      }
    })
  }
}

/**
* 去首页
*/
const toIndex = () => {
  uni.switchTab({
    url: '/pages/index/index'
  })
}

/**
* 去购物车
*/
const toCart = () => {
  uni.switchTab({
    url: '/pages/cart/cart'
  })
}

/**
* 去店铺首页
*/
const toShopIndex = () => {
  uni.navigateTo({
    url: `/pages/shop-index/shop-index?shopId=${Data.shopId}`
  })
}
</script>

<style lang="scss" scoped>
@use "detail";
@use "../../popup.scss";
</style>
