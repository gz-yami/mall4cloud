<template>
  <view class="cart" :class="{ 'edit-cart': isEditCart }">
    <!-- 购物车头部 -->
    <view class="cart-top">
      <view class="address">
        <view class="img">
          <image src="/static/images/local.png" />
        </view>
        <view class="text" @tap="toEditAddress">配送至：{{ defaultAddrStr || '暂无地址' }}</view>
      </view>
      <view class="edit" @tap="editCart">{{ isEditCart ? '完成' : '编辑' }}</view>
    </view>
    <view class="cart-con">
      <view class="con-box">

        <block v-for="(shopItem, shopIndex) in shopCarts" :key="shopIndex">
          <view class="shop-item">
            <!-- 店铺信息 -->
            <view class="shop-info">
              <checkbox color="#fff" :checked="shopItem.checked ? true : false" @tap="handleShopSelect(shopIndex)" />
              <view class="info">
                <view class="icon">
                  <image src="/static/images/shop.png" />
                </view>
                <view class="name text-arrow">{{ shopItem.shopName }}</view>
              </view>
              <!-- <view class="get-coupon" @tap="showCouponPopup">优惠券</view> -->
            </view>
            <!-- 商品 -->
            <view class="item-box">
              <view :class="['prod-item']">
                <!-- 产品信息 -->
                <view v-for="(prodItem, prodIndex) in shopItem.shopCartItem" :key="prodIndex" class="prod-box" @tap="toProdDetail(prodItem.spuId)">
                  <checkbox class="check" color="#fff" :checked="prodItem.isChecked ? true : false" @tap.stop="handleProdItemSelect(shopIndex,prodIndex)" />
                  <view class="prod-img">
                    <image :src="prodItem.imgUrl" />
                  </view>
                  <view class="prod-info">
                    <view class="name">{{ prodItem.spuName }}</view>
                    <view v-if="prodItem.skuName" class="sku" @tap.stop="showSkuPopup(shopIndex,prodIndex,prodItem.skuId)">{{ prodItem.skuName }}</view>
                    <view class="price-number">
                      <view class="price">
                        <view class="symbol">￥</view>
                        <view class="big">{{ wxs.parsePrice(prodItem.priceFee)[0] }}</view>
                        <view class="symbol">.{{ wxs.parsePrice(prodItem.priceFee)[1] }}</view>
                      </view>
                      <view class="number-box">
                        <view class="minus" @tap.stop="changeNumber(shopIndex,prodIndex,-1)" />
                        <input v-model="prodItem.count" type="number" class="number" maxlength="4">
                        <view class="plus" @tap.stop="changeNumber(shopIndex,prodIndex,1)" />
                      </view>
                    </view>
                  </view>
                </view>
              </view>
            </view>
          </view>
        </block>
        <view v-if="!shopCarts.length" class="empty">
          <view class="img">
            <image src="/static/empty-img/cart-empty.png" />
          </view>
          <view class="text">购物车空空的，去逛逛吧~</view>
        </view>
      </view>
    </view>

    <!-- 底部 -->
    <view class="settlement">
      <view class="all-check" @tap="handleSelectAll">
        <checkbox color="#fff" :checked="allChecked" />
        <view class="text">全选</view>
      </view>
      <view class="price-box" @tap="showPriceDetPopup">
        <view class="total">
          <view class="text">总计：</view>
          <view class="price">
            <view class="symbol">￥</view>
            <view class="big">{{ wxs.parsePrice(totalMoney)[0] }}</view>
            <view class="symbol">.{{ wxs.parsePrice(totalMoney)[1] }}</view>
          </view>
        </view>
      </view>
      <view class="btn-box">
        <view class="gray-btn" @tap="handleEditOper(-1)">删除所选</view>
      </view>
      <view class="btn" @tap="submitOrder">去结算({{ selectedCounts }})</view>
    </view>

    <!-- 选择sku弹窗 -->
    <view v-if="isShowSkuPopup" class="popup" :class="{ 'show': isShowSkuPopup }">
      <view class="popup-mask" @tap="closePopup" />
      <view class="popup-con-bottom check-sku">
        <view class="goods-box">
          <view class="img">
            <image :src="defaultSku?defaultSku.imgUrl : prodInfo.mainImgUrl" />
          </view>
          <view class="info">
            <view class="name">{{ prodInfo.name }}</view>
            <view class="price">
              <view class="symbol">￥</view>
              <view class="big">{{ wxs.parsePrice(defaultSku.priceFee)[0] }}</view>
              <view class="symbol">.{{ wxs.parsePrice(defaultSku.priceFee)[1] }}</view>
            </view>
          </view>
          <view class="close" @tap="closePopup">
            <image src="/static/images/close.png" />
          </view>
        </view>
        <view class="con-box">
          <block v-for="(skuLine, key) in skuGroup" :key="key">
            <view class="sku-item">
              <view class="item-tit">{{ key }}</view>
              <view class="item-box">
                <view v-for="skuLineItem in skuLine" :key="skuLineItem" :class="['item-item',selectedProp.indexOf(skuLineItem) !== -1?'active':'', isSkuLineItemNotOptional(allProperties,selectedPropObj,key,skuLineItem,propKeys)? 'cannot' : '']" @tap="toChooseItem(skuLineItem, key)">
                  {{ skuLineItem }}
                </view>
              </view>
            </view>
          </block>
        </view>
        <view class="btn-box">
          <view :class="['btn',findSku?'':'gray']" class="" @tap="skuPopupConfirm">确定</view>
        </view>
      </view>
    </view>
  </view>
</template>

<script module="wxs" lang="wxs" src="../../wxs/index.wxs"></script>

<script>
const http = require('../../utils/http.js')
const cartCount = require('../../utils/cart-count.js')
const config = require('../../utils/config.js')

export default {

  components: {},
  props: {},
  data() {
    return {
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
      selectedProperties: "",
      propKeys: [],
      allProperties: [],
      oldSkuId: null,
      shopIndex: 0,
      prodIndex: 0,

      reqItemList: [],  // 勾选的项目
      selectedCounts: 0,

      prodDiscounts: [],
      spuId: '',
      curBasketItemCheck: '',
      operBasketItem:'',
      defaultAddrStr:'' //默认地址
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function() {
    // this.getCartInfo()
    this.getDefaultAddrStr()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() { },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    // 获取购物车数字
    cartCount.getCartCount()
    // 获取用户购物车信息
    this.getCartInfo()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {
    this.closePopup()
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() { },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() { },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() { },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() { },
  methods: {
    /**
     * 提交订单
     */
    submitOrder() {
      if(!this.selectedCounts) {
        uni.showToast({
          title: '请选择商品！',
          duration: 2000,
          icon: 'none'
        })
        return
      }
      let basketIds = []
      this.shopCarts.forEach(shopItem => {
          shopItem.shopCartItem.forEach(prodItem => {
            if (prodItem.checked) {
              basketIds.push(prodItem.cartItemId)
            }
          })
      })
      uni.navigateTo({
        url: '/pages/submit-order/submit-order?orderEntry=0' + '&dvyType=1' //dvyType 配送类型 1:快递 2:自提 3：无需快递 4:同城配送
      })
    },

    /**
     * 编辑状态的操作
     * @param operType {Number} 1添加收藏  -1删除
     */
    handleEditOper(operType) {
      let selectIds = []
      let shopCarts = this.shopCarts
      shopCarts.forEach(shopItem => {
          shopItem.shopCartItem.forEach(prodItem => {
            if (prodItem.checked) {
              selectIds.push(prodItem.cartItemId)
            }
          })
      })
      let params = {
        url: '/mall4cloud_product/a/shop_cart/delete_item',
        method: 'DELETE',
        data: selectIds,
        callBack: res => {
          uni.showToast({
            title: '删除成功',
            duration: 2000
          })
          if (operType === -1) {
            this.getCartInfo()
            if (operType != 1) {
              cartCount.getCartCount()
            }
          }
        }
      }
        uni.showModal({
          title: '提示',
          content: '确认要删除选中的商品吗？',
          success: function (res) {
            if (res.confirm) {
              http.request(params)
            } else if (res.cancel) {
              console.log('用户点击取消')
            }
          }
        })
    },

    /**
     * 商品数量加减
     */
    changeNumber(shopIndex,prodIndex,num) {
      let curItem
      let reqData = {}
      if (num) {
        curItem = this.shopCarts[shopIndex].shopCartItem[prodIndex]
        reqData =  {
          count: num,
          shopCartItemId: curItem.cartItemId,
          skuId: curItem.skuId,
          spuId: curItem.spuId
        }
      }
      const params = {
        url: '/mall4cloud_product/a/shop_cart/change_item',
        method: 'POST',
        data: reqData,
        callBack: res => {
          this.getCartInfo()
          if(num!=0) {
            cartCount.getCartCount()
          }
        }
      }
      if ( num===-1 && curItem.count===1 ) {
        uni.showModal({
          title: '提示',
          content: '要从购物车移除此商品吗？',
          success: function (res) {
            if (res.confirm) {
              http.request(params)
            } else if (res.cancel) {
              console.log('用户点击取消')
            }
          }
        })
      } else {
        http.request(params)
      }
    },

    /**
     * 获取用户购物车信息
     */
    getCartInfo() {
      const params = {
        url: '/mall4cloud_product/a/shop_cart/info',
        method: 'GET',
        data: {},
        callBack: res => {
          let shopCarts = res.shopCarts
          if (!this.reqItemList.length) {  // 初次请求
            // 所有商品添加勾选状态
            shopCarts.forEach(shopItem => {
                shopItem.shopCartItem.forEach(prodItem => {
                  if (prodItem.imgUrl.indexOf('http')==-1) {
                    prodItem.imgUrl = config.resourcesUrl + prodItem.imgUrl
                  }
                  prodItem.checked = prodItem.isChecked ? true : false
                })
            })
          } else {  // 修改购物车
            let checkedLog = []
            this.shopCarts.forEach(shopItem => {
                shopItem.shopCartItem.forEach(prodItem => {
                  if (prodItem.checked) {
                    checkedLog.push(prodItem.cartItemId)
                  }
                })
            })
            shopCarts.forEach(shopItem => {
                shopItem.shopCartItem.forEach(prodItem => {
                  if (prodItem.imgUrl.indexOf('http')==-1) {
                    prodItem.imgUrl = config.resourcesUrl + prodItem.imgUrl
                  }
                  if (checkedLog.includes(prodItem.cartItemId)) {
                    prodItem.checked = true
                  }
                })
            })
          }
          this.shopCarts = shopCarts
          this.totalMoney = res.totalMoney
          this.checkAllSelected()
          this.calSelectedCounts()
        }
      }
      http.request(params)
    },

    /**
     * 检查全选状态
     */
    checkAllSelected() {
      let allChecked = true
      let shopCarts = this.shopCarts
      let flag = false
      shopCarts.forEach(shopCart => {
        let shopChecked = true
          let cItems = shopCart.shopCartItem;
          for (let j = 0; j < cItems.length; j++) {
            if (!cItems[j].isChecked) {
              shopChecked = false
              allChecked = false
              flag = true
            }
            cItems[j].checked = !!cItems[j].checked
          }
        shopCart.checked = shopChecked
      })
      this.allChecked = allChecked
      this.shopCarts = shopCarts
    },

    /**
     * 单个店铺的 勾选/取选
     */
    handleShopSelect(shopIndex) {
      let shopCarts = this.shopCarts
      let checked = shopCarts[shopIndex].checked // 获取该店铺当前的选中状态
      shopCarts[shopIndex].checked = !checked // 改变状态
      this.reqItemList = []
        var cItems = shopCarts[shopIndex].shopCartItem
        for (var j = 0; j < cItems.length; j++) {
          if(cItems[j].checked !== !checked) {
            this.reqItemList.push({
              isChecked: shopCarts[shopIndex].checked ? 1 : 0,
              shopCartItemId: cItems[j].cartItemId
            })
          }
          cItems[j].checked = !checked
        }
      this.shopCarts = shopCarts
      this.checkAllSelected() //检查全选状态
      this.calTotalPrice()
    },

    /**
     * 每一个商品的 勾选/取选
     */
    handleProdItemSelect(shopIndex, prodIndex) {
      let shopCarts = this.shopCarts
      let cartItem = shopCarts[shopIndex].shopCartItem[prodIndex]
      let checked = cartItem.isChecked
      cartItem.checked = !checked
      this.shopCarts = shopCarts
      this.checkAllSelected()
      // 重新设置勾选列表数据
      this.reqItemList = []
      this.reqItemList.push({
        isChecked: cartItem.checked ? 1 : 0,
        shopCartItemId: cartItem.cartItemId
      })
      this.calTotalPrice()
    },

    /**
     * 点击全选
     */
    handleSelectAll() {
      let allChecked = this.allChecked
      let shopCarts = this.shopCarts
      allChecked = !allChecked
      this.reqItemList = []
      shopCarts.forEach(shopItem=>{
        shopItem.checked = allChecked
        shopItem.shopCartItem.forEach(item=>{
          if (item.isChecked !== allChecked) {
              this.reqItemList.push({
                isChecked: allChecked ? 1 : 0,
                shopCartItemId: item.cartItemId
              })
            }
        })
      })
      this.allChecked = allChecked
      this.shopCarts = shopCarts
      this.calTotalPrice()
      this.calSelectedCounts()
    },
    /**
     * 计算购物车选中的商品项的数量
     */
    calSelectedCounts() {
      // 计算购物车勾选的项目数量
      let sum = 0
      this.shopCarts.forEach(shopItem => {
          shopItem.shopCartItem.forEach(prodItem => {
            if (prodItem.isChecked) {
              sum++
            }
          })
      })
      this.selectedCounts = sum
    },

    /**
     * 计算价格
     */
    calTotalPrice() {
      const params = {
        url: '/mall4cloud_product/a/shop_cart/check_items',
        method: 'POST',
        data: this.reqItemList,
        callBack: res => {
          this.getCartInfo()
        }
      }
      http.request(params)
    },

    // 编辑购物车
    editCart() {
      this.isEditCart = !this.isEditCart
    },

    // 显示Sku弹窗
    showSkuPopup(shopIndex,prodIndex) {
      this.shopIndex = shopIndex
      this.prodIndex = prodIndex
      let cartItem = this.shopCarts[shopIndex].shopCartItem[prodIndex]
      let oldSkuId = cartItem.skuId
      this.oldSkuId = oldSkuId
      this.getProdInfo(cartItem.spuId)
    },

    /**
     * sku弹窗确认
     */
    skuPopupConfirm() {
      if (!this.findSku) {
        uni.showToast({
          title: '所选规格无货',
          duration: 2000,
          icon: 'none'
        })
        return
      }
      if (this.defaultSku.skuId === this.oldSkuId) {
        this.isShowSkuPopup = false
        return
      }
      let cartItem = this.shopCarts[this.shopIndex].shopCartItem[this.prodIndex]
      const params = {
        url: '/mall4cloud_product/a/shop_cart/change_item',
        method: 'POST',
        data: {
          oldSkuId: this.oldSkuId,
          shopCartItemId: cartItem.cartItemId,
          count: cartItem.count,
          skuId: this.defaultSku.skuId,
          spuId: cartItem.spuId
        },
        callBack: res => {
          this.isShowSkuPopup = false
          this.getCartInfo()
        }
      }
      http.request(params)
    },

    /**
     * 获取商品详情
     */
    getProdInfo(spuId) {
      uni.showLoading()
      const params = {
        url: '/mall4cloud_product/ua/spu/prod_info',
        method: 'GET',
        data: {
          spuId: spuId
        },
        callBack: res => {
          this.prodInfo = res
          this.skuList = res.skus
          this.groupSkuProp(res.skus, res.priceFee)
          uni.hideLoading()
        }
      }
      http.request(params)
    },

    /**
     * 组装SKU
     */
    groupSkuProp: function(skuList) {
      if (skuList.length == 1 && skuList[0].properties == "") {
        this.defaultSku = skuList[0]
        return;
      }
      let skuGroup = {};
      let allProperties = [];
      let propKeys = [];
      let selectedPropObj = {}
      let defaultSku = null;
      for (var i = 0; i < skuList.length; i++) {
        var isDefault = false;
        if (!defaultSku && skuList[i].skuId == this.oldSkuId) { //找到和商品价格一样的那个SKU，作为默认选中的SKU
          defaultSku = skuList[i];
          isDefault = true;
        }
        var properties = skuList[i].properties; //版本:公开版;颜色:金色;内存:64GB
        allProperties.push(properties);
        var propList = properties.split(";"); // ["版本:公开版","颜色:金色","内存:64GB"]
        for (var j = 0; j < propList.length; j++) {

          var propval = propList[j].split(":"); //["版本","公开版"]
          var props = skuGroup[propval[0]]; //先取出 版本对应的值数组
          //如果当前是默认选中的sku，把对应的属性值 组装到selectedProp
          if (isDefault) {
            propKeys.push(propval[0]);
            selectedPropObj[propval[0]] = propval[1];
          }

          if (props == undefined) {
            props = []; //假设还没有版本，新建个新的空数组
            props.push(propval[1]); //把 "公开版" 放进空数组
          } else {
            if (props.indexOf(propval[1]) === -1) { //如果数组里面没有"公开版"
              props.push(propval[1]); //把 "公开版" 放进数组
            }
          }
          skuGroup[propval[0]] = props; //最后把数据 放回版本对应的值
        }
      }
       if (this.oldSkuId) {
        skuList.forEach(skuItem => {
          if (skuItem.skuId === this.oldSkuId) {
            defaultSku = skuItem
          }
        })
      }
      this.defaultSku = defaultSku
      this.propKeys = propKeys
      this.selectedPropObj = selectedPropObj
      this.skuGroup = skuGroup
      this.allProperties = allProperties
      this.parseSelectedObjToVals(skuList)
      this.isShowSkuPopup = true
    },

    /**
     * 将已选的 {key:val,key2:val2}转换成 [val,val2]
     */
    parseSelectedObjToVals: function(skuList) {
      var selectedPropObj = this.selectedPropObj
      var selectedProperties = "";
      var selectedProp = [];
      for (var key in selectedPropObj) {
        selectedProp.push(selectedPropObj[key]);
        selectedProperties += key + ":" + selectedPropObj[key] + ";";
      }
      selectedProperties = selectedProperties.substring(0, selectedProperties.length - 1);
      this.selectedProp = selectedProp
      this.selectedProperties = selectedProperties
      this.selectedPropObj = selectedPropObj
      var findSku = false;
      for (var i = 0; i < skuList.length; i++) {
        if (skuList[i].properties == selectedProperties) {
          findSku = true;
          this.defaultSku = skuList[i]
          break;
        }
      }
      this.findSku = findSku
    },

    /**
     * 判断当前的规格值 是否可以选
     */
    isSkuLineItemNotOptional(allProperties, selectedPropObj, key, item, propKeys) {
      var selectedPropObj = Object.assign({}, selectedPropObj)
      var properties = "";
      selectedPropObj[key] = item;
      for (var j = 0; j < propKeys.length; j++) {
        properties += propKeys[j] + ":" + selectedPropObj[propKeys[j]] + ";";
      }
      properties = properties.substring(0, properties.length - 1);
      for (var i = 0; i < allProperties.length; i++) {
        if (properties == allProperties[i]) {
          return false;
        }
      }
      for (var i = 0; i < allProperties.length; i++) {
        if (allProperties[i].indexOf(item) >= 0) {
          return true;
        }
      }
      return false;
    },

    /**
     * 规格点击事件
     */
    toChooseItem(skuLineItem, key) {
      this.selectedPropObj[key] = skuLineItem;
      this.parseSelectedObjToVals(this.skuList);
    },

    /**
     * 判断数组是否包含某对象
     */
    array_contain: function(array, obj) {
      for (var i = 0; i < array.length; i++) {
        if (array[i] == obj) //如果要求数据类型也一致，这里可使用恒等号===
          return true;
      }
      return false;
    },

    // 隐藏弹窗
    closePopup() {
      this.isShowSkuPopup = false
    },

    /**
     * 跳转商品详情
     */
    toProdDetail(spuId) {
      uni.navigateTo({
        url: `/pages/detail/detail?spuId=${spuId}`
      })
    },
    /**
     * 获取默认地址的地区信息
     */
    getDefaultAddrStr() {
      uni.showLoading()
      const params = {
        url: '/mall4cloud_user/user_addr/list',
        method: 'GET',
        callBack: res => {
          if (!res.length) {
            uni.hideLoading()
            return
          }
          const defaultAddr = res.filter((el) => { return el.isDefault })[0]
          this.defaultAddrStr = defaultAddr.province + defaultAddr.city + defaultAddr.area
          uni.hideLoading()
        }
      }
      http.request(params)
    },
    /**
     * 跳转编辑地址页面
     */
    toEditAddress() {
      uni.navigateTo({
        url: '/pages/address-list/address-list'
      })
    }
  }
}
</script>

<style>
@import "./cart.css";
@import "./../../popup.css";
</style>
