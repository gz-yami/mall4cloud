<template>
  <view
    class="page-cart"
    :class="{ 'edit-cart': isEditCart }"
  >
    <!-- 购物车头部 -->
    <view class="cart-top">
      <view class="address">
        <view class="img">
          <image src="/static/images/local.png" />
        </view>
        <view
          class="text"
          @tap="toEditAddress"
        >
          配送至：{{ defaultAddrStr || '暂无地址' }}
        </view>
      </view>
      <view
        class="edit"
        @tap="editCart"
      >
        {{ isEditCart ? '完成' : '编辑' }}
      </view>
    </view>
    <view class="cart-con">
      <view class="con-box">
        <block
          v-for="(shopItem, shopIndex) in shopCarts"
          :key="shopIndex"
        >
          <view class="shop-item">
            <!-- 店铺信息 -->
            <view class="shop-info">
              <checkbox
                color="#fff"
                :checked="shopItem.checked ? true : false"
                @tap="handleShopSelect(shopIndex)"
              />
              <view class="info">
                <view class="icon">
                  <image src="/static/images/shop.png" />
                </view>
                <view class="name text-arrow">
                  {{ shopItem.shopName }}
                </view>
              </view>
              <!-- <view class="get-coupon" @tap="showCouponPopup">优惠券</view> -->
            </view>
            <!-- 商品 -->
            <view class="item-box">
              <view :class="['prod-item']">
                <!-- 产品信息 -->
                <view
                  v-for="(prodItem, prodIndex) in shopItem.shopCartItem"
                  :key="prodIndex"
                  class="prod-box"
                  @tap="toProdDetail(prodItem.spuId)"
                >
                  <checkbox
                    class="check"
                    color="#fff"
                    :checked="prodItem.isChecked ? true : false"
                    @tap.stop="handleProdItemSelect(shopIndex, prodIndex)"
                  />
                  <view class="prod-img">
                    <image :src="prodItem.imgUrl" />
                  </view>
                  <view class="prod-info">
                    <view class="name">
                      {{ prodItem.spuName }}
                    </view>
                    <view
                      v-if="prodItem.skuName"
                      class="sku"
                      @tap.stop="showSkuPopup(shopIndex, prodIndex, prodItem.skuId)"
                    >
                      {{ prodItem.skuName }}
                    </view>
                    <view class="price-number">
                      <view class="price">
                        <view class="symbol">
                          ￥
                        </view>
                        <view class="big">
                          {{ wxs.parsePrice(prodItem.priceFee)[0] }}
                        </view>
                        <view class="symbol">
                          .{{ wxs.parsePrice(prodItem.priceFee)[1] }}
                        </view>
                      </view>
                      <view class="number-box">
                        <view
                          class="minus"
                          @tap.stop="changeNumber(shopIndex, prodIndex, -1)"
                        />
                        <input
                          v-model="prodItem.count"
                          type="number"
                          class="number"
                          maxlength="4"
                        >
                        <view
                          class="plus"
                          @tap.stop="changeNumber(shopIndex, prodIndex, 1)"
                        />
                      </view>
                    </view>
                  </view>
                </view>
              </view>
            </view>
          </view>
        </block>
        <view
          v-if="!shopCarts.length"
          class="empty"
        >
          <view class="img">
            <image src="/static/empty-img/cart-empty.png" />
          </view>
          <view class="text">
            购物车空空的，去逛逛吧~
          </view>
        </view>
      </view>
    </view>

    <!-- 底部 -->
    <view class="settlement">
      <view
        class="all-check"
        @tap="handleSelectAll"
      >
        <checkbox
          color="#fff"
          :checked="allChecked"
        />
        <view class="text">
          全选
        </view>
      </view>
      <view
        class="price-box"
      >
        <view class="total">
          <view class="text">
            总计：
          </view>
          <view class="price">
            <view class="symbol">
              ￥
            </view>
            <view class="big">
              {{ wxs.parsePrice(totalMoney)[0] }}
            </view>
            <view class="symbol">
              .{{ wxs.parsePrice(totalMoney)[1] }}
            </view>
          </view>
        </view>
      </view>
      <view class="btn-box">
        <view
          class="gray-btn"
          @tap="handleEditOper(-1)"
        >
          删除所选
        </view>
      </view>
      <view
        class="btn"
        @tap="submitOrder"
      >
        去结算({{ selectedCounts }})
      </view>
    </view>

    <!-- 选择sku弹窗 -->
    <view
      v-if="isShowSkuPopup"
      class="popup"
      :class="{ 'show': isShowSkuPopup }"
    >
      <view
        class="popup-mask"
        @tap="closePopup"
      />
      <view class="popup-con-bottom check-sku">
        <view class="goods-box">
          <view class="img">
            <image :src="defaultSku ? defaultSku.imgUrl : prodInfo.mainImgUrl" />
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
            <view class="sku-item">
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
import { reactive } from 'vue'
import * as cartCount from '@/utils/cart-count.js'

const wxs = number()

const Data = reactive({
  isEditCart: false, // 编辑购物车
  isShowSkuPopup: false, // sku弹窗显示

  shopCarts: [],
  totalMoney: 0,

  allChecked: false, // 全选状态

  prodInfo: {},
  skuList: [],
  skuGroup: {},
  findSku: true,
  skuShowType: 0,
  defaultSku: undefined,
  selectedProp: [],
  selectedPropObj: {},
  selectedProperties: '',
  propKeys: [],
  allProperties: [],
  oldSkuId: null,
  shopIndex: 0,
  prodIndex: 0,

  reqItemList: [], // 勾选的项目
  selectedCounts: 0,

  prodDiscounts: [],
  spuId: '',
  curBasketItemCheck: '',
  operBasketItem: '',
  defaultAddrStr: '' // 默认地址
})
const { isEditCart, isShowSkuPopup, shopCarts, totalMoney, allChecked, prodInfo, skuGroup, findSku, defaultSku, selectedProp, selectedPropObj, propKeys, allProperties, selectedCounts, defaultAddrStr } = toRefs(Data)

onLoad(() => {
  getDefaultAddrStr()
})

onShow(() => {
  // 获取购物车数字
  cartCount.getCartCount()
  // 获取用户购物车信息
  getCartInfo()
})

onHide(() => {
  closePopup()
})

/**
* 提交订单
*/
const submitOrder = () => {
  if (!Data.selectedCounts) {
    uni.showToast({
      title: '请选择商品！',
      duration: 2000,
      icon: 'none'
    })
    return
  }
  const basketIds = []
  Data.shopCarts.forEach(shopItem => {
    shopItem.shopCartItem.forEach(prodItem => {
      if (prodItem.checked) {
        basketIds.push(prodItem.cartItemId)
      }
    })
  })
  uni.navigateTo({
    url: '/pages/submit-order/submit-order?orderEntry=0' + '&dvyType=1' // dvyType 配送类型 1:快递 2:自提 3：无需快递 4:同城配送
  })
}

/**
* 编辑状态的操作
* @param operType {Number} 1添加收藏  -1删除
*/
const handleEditOper = (operType) => {
  const selectIds = []
  const shopCarts = Data.shopCarts
  shopCarts.forEach(shopItem => {
    shopItem.shopCartItem.forEach(prodItem => {
      if (prodItem.checked) {
        selectIds.push(prodItem.cartItemId)
      }
    })
  })
  uni.showModal({
    title: '提示',
    content: '确认要删除选中的商品吗？',
    success: function (res) {
      if (res.confirm) {
        http.request({
          url: '/mall4cloud_product/a/shop_cart/delete_item',
          method: 'DELETE',
          data: selectIds
        }).then(() => {
          uni.showToast({
            title: '删除成功',
            duration: 2000
          })
          if (operType === -1) {
            getCartInfo()
            if (operType != 1) {
              cartCount.getCartCount()
            }
          }
        })
      }
    }
  })
}

/**
* 商品数量加减
*/
const changeNumber = (shopIndex, prodIndex, num) => {
  let curItem
  let reqData = {}
  if (num) {
    curItem = Data.shopCarts[shopIndex].shopCartItem[prodIndex]
    reqData = {
      count: num,
      shopCartItemId: curItem.cartItemId,
      skuId: curItem.skuId,
      spuId: curItem.spuId
    }
  }
  const params = {
    url: '/mall4cloud_product/a/shop_cart/change_item',
    method: 'POST',
    data: reqData
  }
  if (num === -1 && curItem.count === 1) {
    uni.showModal({
      title: '提示',
      content: '要从购物车移除此商品吗？',
      success: function (res) {
        if (res.confirm) {
          http.request(params).then(() => {
            getCartInfo()
            if (num != 0) {
              cartCount.getCartCount()
            }
          })
        }
      }
    })
  } else {
    http.request(params).then(() => {
      getCartInfo()
      if (num != 0) {
        cartCount.getCartCount()
      }
    })
  }
}

/**
* 获取用户购物车信息
*/
const getCartInfo = () => {
  http.request({
    url: '/mall4cloud_product/a/shop_cart/info',
    method: 'GET',
    data: {}
  }).then((res) => {
    const shopCarts = res.shopCarts
    if (!Data.reqItemList.length) { // 初次请求
      // 所有商品添加勾选状态
      shopCarts.forEach(shopItem => {
        shopItem.shopCartItem.forEach(prodItem => {
          if (prodItem.imgUrl.indexOf('http') == -1) {
            prodItem.imgUrl = import.meta.env.VITE_APP_RESOURCES_URL + prodItem.imgUrl
          }
          prodItem.checked = !!prodItem.isChecked
        })
      })
    } else { // 修改购物车
      const checkedLog = []
      Data.shopCarts.forEach(shopItem => {
        shopItem.shopCartItem.forEach(prodItem => {
          if (prodItem.checked) {
            checkedLog.push(prodItem.cartItemId)
          }
        })
      })
      shopCarts.forEach(shopItem => {
        shopItem.shopCartItem.forEach(prodItem => {
          if (prodItem.imgUrl.indexOf('http') == -1) {
            prodItem.imgUrl = import.meta.env.VITE_APP_RESOURCES_URL + prodItem.imgUrl
          }
          if (checkedLog.includes(prodItem.cartItemId)) {
            prodItem.checked = true
          }
        })
      })
    }
    Data.shopCarts = shopCarts
    Data.totalMoney = res.totalMoney
    checkAllSelected()
    calSelectedCounts()
  })
}

/**
* 检查全选状态
*/
const checkAllSelected = () => {
  let allChecked = true
  const shopCarts = Data.shopCarts
  shopCarts.forEach(shopCart => {
    let shopChecked = true
    const cItems = shopCart.shopCartItem
    for (let j = 0; j < cItems.length; j++) {
      if (!cItems[j].isChecked) {
        shopChecked = false
        allChecked = false
      }
      cItems[j].checked = !!cItems[j].checked
    }
    shopCart.checked = shopChecked
  })
  Data.allChecked = allChecked
  Data.shopCarts = shopCarts
}

/**
* 单个店铺的 勾选/取选
*/
const handleShopSelect = (shopIndex) => {
  const shopCarts = Data.shopCarts
  const checked = shopCarts[shopIndex].checked // 获取该店铺当前的选中状态
  shopCarts[shopIndex].checked = !checked // 改变状态
  Data.reqItemList = []
  const cItems = shopCarts[shopIndex].shopCartItem
  for (let j = 0; j < cItems.length; j++) {
    if (cItems[j].checked !== !checked) {
      Data.reqItemList.push({
        isChecked: shopCarts[shopIndex].checked ? 1 : 0,
        shopCartItemId: cItems[j].cartItemId
      })
    }
    cItems[j].checked = !checked
  }
  Data.shopCarts = shopCarts
  checkAllSelected() // 检查全选状态
  calTotalPrice()
}

/**
* 每一个商品的 勾选/取选
*/
const handleProdItemSelect = (shopIndex, prodIndex) => {
  const shopCarts = Data.shopCarts
  const cartItem = shopCarts[shopIndex].shopCartItem[prodIndex]
  const checked = cartItem.isChecked
  cartItem.checked = !checked
  Data.shopCarts = shopCarts
  checkAllSelected()
  // 重新设置勾选列表数据
  Data.reqItemList = []
  Data.reqItemList.push({
    isChecked: cartItem.checked ? 1 : 0,
    shopCartItemId: cartItem.cartItemId
  })
  calTotalPrice()
}

/**
* 点击全选
*/
const handleSelectAll = () => {
  let allChecked = Data.allChecked
  const shopCarts = Data.shopCarts
  allChecked = !allChecked
  Data.reqItemList = []
  shopCarts.forEach(shopItem => {
    shopItem.checked = allChecked
    shopItem.shopCartItem.forEach(item => {
      if (item.isChecked !== allChecked) {
        Data.reqItemList.push({
          isChecked: allChecked ? 1 : 0,
          shopCartItemId: item.cartItemId
        })
      }
    })
  })
  Data.allChecked = allChecked
  Data.shopCarts = shopCarts
  calTotalPrice()
  calSelectedCounts()
}
/**
* 计算购物车选中的商品项的数量
*/
const calSelectedCounts = () => {
  // 计算购物车勾选的项目数量
  let sum = 0
  Data.shopCarts.forEach(shopItem => {
    shopItem.shopCartItem.forEach(prodItem => {
      if (prodItem.isChecked) {
        sum++
      }
    })
  })
  Data.selectedCounts = sum
}

/**
* 计算价格
*/
const calTotalPrice = () => {
  const params = {
    url: '/mall4cloud_product/a/shop_cart/check_items',
    method: 'POST',
    data: Data.reqItemList

  }
  http.request(params).then(() => {
    getCartInfo()
  })
}

// 编辑购物车
const editCart = () => {
  Data.isEditCart = !Data.isEditCart
}

// 显示Sku弹窗
const showSkuPopup = (shopIndex, prodIndex) => {
  Data.shopIndex = shopIndex
  Data.prodIndex = prodIndex
  const cartItem = Data.shopCarts[shopIndex].shopCartItem[prodIndex]
  const oldSkuId = cartItem.skuId
  Data.oldSkuId = oldSkuId
  getProdInfo(cartItem.spuId)
}

/**
* sku弹窗确认
*/
const skuPopupConfirm = () => {
  if (!Data.findSku) {
    uni.showToast({
      title: '所选规格无货',
      duration: 2000,
      icon: 'none'
    })
    return
  }
  if (Data.defaultSku.skuId === Data.oldSkuId) {
    Data.isShowSkuPopup = false
    return
  }
  const cartItem = Data.shopCarts[Data.shopIndex].shopCartItem[Data.prodIndex]
  const params = {
    url: '/mall4cloud_product/a/shop_cart/change_item',
    method: 'POST',
    data: {
      oldSkuId: Data.oldSkuId,
      shopCartItemId: cartItem.cartItemId,
      count: cartItem.count,
      skuId: Data.defaultSku.skuId,
      spuId: cartItem.spuId
    }
  }
  http.request(params).then(() => {
    Data.isShowSkuPopup = false
    getCartInfo()
  })
}

/**
* 获取商品详情
*/
const getProdInfo = (spuId) => {
  uni.showLoading()
  const params = {
    url: '/mall4cloud_product/ua/spu/prod_info',
    method: 'GET',
    data: {
      spuId
    }
  }
  http.request(params).then((res) => {
    Data.prodInfo = res
    Data.skuList = res.skus
    groupSkuProp(res.skus, res.priceFee)
    uni.hideLoading()
  })
}

/**
* 组装SKU
*/
const groupSkuProp = (skuList) => {
  if (skuList.length == 1 && skuList[0].properties == '') {
    Data.defaultSku = skuList[0]
    return
  }
  const skuGroup = {}
  const allProperties = []
  const propKeys = []
  const selectedPropObj = {}
  let defaultSku = null
  for (let i = 0; i < skuList.length; i++) {
    let isDefault = false
    if (!defaultSku && skuList[i].skuId == Data.oldSkuId) { // 找到和商品价格一样的那个SKU，作为默认选中的SKU
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
  if (Data.oldSkuId) {
    skuList.forEach(skuItem => {
      if (skuItem.skuId === Data.oldSkuId) {
        defaultSku = skuItem
      }
    })
  }
  Data.defaultSku = defaultSku
  Data.propKeys = propKeys
  Data.selectedPropObj = selectedPropObj
  Data.skuGroup = skuGroup
  Data.allProperties = allProperties
  parseSelectedObjToVals(skuList)
  Data.isShowSkuPopup = true
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
const isSkuLineItemNotOptional = (allProperties, selectedPropObjPar, key, item, propKeys) => {
  const selectedPropObj = Object.assign({}, selectedPropObjPar)
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

// 隐藏弹窗
const closePopup = () => {
  Data.isShowSkuPopup = false
}

/**
* 跳转商品详情
*/
const toProdDetail = (spuId) => {
  uni.navigateTo({
    url: `/pages/detail/detail?spuId=${spuId}`
  })
}

/**
* 获取默认地址的地区信息
*/
const getDefaultAddrStr = () => {
  uni.showLoading()
  const params = {
    url: '/mall4cloud_user/user_addr/list',
    method: 'GET'
  }
  http.request(params).then(res => {
    if (!res.length) {
      uni.hideLoading()
      return
    }
    const defaultAddr = res.filter((el) => el.isDefault)[0]
    Data.defaultAddrStr = defaultAddr.province + defaultAddr.city + defaultAddr.area
    uni.hideLoading()
  })
}

/**
* 跳转编辑地址页面
*/
const toEditAddress = () => {
  uni.navigateTo({
    url: '/pages/address-list/address-list'
  })
}
</script>

<style lang="scss" scoped>
@use "cart";
</style>
