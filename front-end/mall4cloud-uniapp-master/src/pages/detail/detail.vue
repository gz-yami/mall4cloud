<template>
  <view class="detail">
    <!-- 头部 -->
    <!--  -->
    <view class="det-header" :style="{ opacity: topTabOpacity, zIndex: topTabOpacity >0.2?'1':'-1'}">
      <view :class="['item', topTabSts === 0? 'active' : '']" @tap="hanleTopTabClick(0)">商品</view>
      <view :class="['item', topTabSts === 2? 'active' : '']" @tap="hanleTopTabClick(2)">详情</view>
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
      <swiper-item v-for="(img, imgIdx) in imgList" :key="imgIdx" class="item">
        <image :src="img" />
      </swiper-item>
    </swiper>

    <!-- 名称、价格 -->
    <view class="name-box">
      <view class="name">{{ prodInfo.name }}</view>
      <view class="action-box">
        <view class="price-box">
          <view class="price">
            <view class="symbol">￥</view>
            <view class="big">{{ wxs.parsePrice(defaultSku.priceFee)[0] }}</view>
            <view class="symbol">.{{ wxs.parsePrice(defaultSku.priceFee)[1] }}</view>
          </view>
          <view class="stock">仅剩{{ defaultSku.stock }}件</view>
        </view>
      </view>
    </view>

    <!-- sku、配送 -->
    <view class="det-con">
      <view class="item">
        <view class="tit">已选</view>
        <view class="con sku-con" @tap="isShowSkuPopup = true">
          <view v-if="defaultSku.skuName" class="s-item">
            <block v-for="(skuItem,index) in selectedProp" :key="index">
              <text decode="true">{{ index < selectedProp.length-1 ? skuItem +'，' : skuItem+'&nbsp; &nbsp;' }}</text>
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
            <view class="name">{{ shopInfo.shopName }}</view>
            <view class="focus-box">
              <view v-if="shopInfo.type === 1" class="self">自营</view>
            </view>
          </view>
        </view>
        <view class="go-shop" @tap="toShopIndex">进店逛逛</view>
      </view>
    </view>

    <!-- 详情 -->
    <div class="det-det">
      <div class="tit">商品详情</div>
      <div class="con">
        <rich-text :nodes="prodDetail" />
      </div>
    </div>

    <!-- 底部 -->
    <view class="det-foot">
      <view class="actions">
        <view class="item" @tap="toIndex">
          <view class="img">
            <image src="/static/images/detail-index.png" />
          </view>
          <view class="text">首页</view>
        </view>
        <view class="item" @tap="toCart">
          <view class="img">
            <image src="/static/images/detail-cart.png" />
            <view v-if="cartCount" class="mark">{{ cartCount }}</view>
          </view>
          <view class="text">购物车</view>
        </view>
      </view>
      <view class="btns">
        <view class="btn" @tap="addCart">加入购物车</view>
        <view class="btn btn-r" @tap="goSubmit">立即购买</view>
      </view>
    </view>

    <!-- 选择sku弹窗 -->
    <view class="popup" :class="{ show: isShowSkuPopup }">
      <!-- <SkuSelectPopup :prod-info="prodInfo" /> -->
      <view class="popup-mask" @tap="closePopup" />
      <view class="popup-con-bottom check-sku">
        <view class="goods-box">
          <view class="img">
            <image :src="prodInfo.mainImgUrl" />
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
            <view v-if="defaultSku.skuName" class="sku-item">
              <view class="item-tit">{{ key }}</view>
              <view class="item-box">
                <view v-for="skuLineItem in skuLine" :key="skuLineItem" :class="['item-item',selectedProp.indexOf(skuLineItem) !== -1?'active':'', isSkuLineItemNotOptional(allProperties,selectedPropObj,key,skuLineItem,propKeys)? 'cannot' : '']" @tap="toChooseItem(skuLineItem, key)">
                  {{ skuLineItem }}
                </view>
              </view>
            </view>
          </block>
          <view class="prod-number">
            <view class="item-tit">数量</view>
            <view class="number-box">
              <view :class="['minus', prodNumber===1?'limit':'']" @tap="changeNumber(-1)" />
              <input v-model="prodNumber" type="number" class="number" maxlength="4">
              <view :class="['plus', isMaxProdNumber?'limit':'']" @tap="changeNumber(1)" />
            </view>
          </view>
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
const util = require('../../utils/util')

export default {
  props: {},
  data() {
    return {
      isShowSkuPopup: false, // 显示sku弹窗
      isShowCommentPopup: false, // 显示评价弹窗
      spuId: 0, // 商品id
      shopId: 0,
      prodInfo: {}, // 商品详情
      shopInfo:{}, //店铺详情
      deliveryModeVO: {}, // 商品配送方式
      imgList: [], // 商品轮播图
      prodDetail: '', // 商品详情富文本

      // pageScorllTop: 0, // 页面滚动的高度
      topTabSts: 0, // 顶部 topTab 选中的项目
      topTabOpacity: 0, // 顶部 topTab 的透明度

      // sku 商品规格
      findSku: true,
      defaultSku: "",
      detaultGroupSku: "",
      skuList: [],
      selectedProp: [],
      selectedPropObj: {},
      selectedProperties: "",
      propKeys: [],
      allProperties: [],
      skuGroup: {},
      skuPopConfirmType: 0, // 规格弹窗类型: 0普通sku切换; 1加入购物车; 2立即购买

      // 优惠券
      couponList:[],

      prodNumber: 1, // 商品数量
      hasMaxNumber: 0, // 是否限购
      maxNum: 10, // 限购数量(仅团购商品) TODO:假的,以后要删的~()
      isMaxProdNumber: false, // 是否达到限购或达到最大库存
      isAddCart: false, // 当前sku弹窗是否为加购弹出

      cartCount: 0,

    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (options.spuId) {
      this.spuId = options.spuId
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() { },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    // 获取商品详情
    this.getProdInfo()

    // 获取购物车数字
    this.getCartCount()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() { },

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
   * 页面滚动事件
   */
  onPageScroll: function (e) {
    this.topTabOpacity = e.scrollTop / 46
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() { },

  watch: {
    prodNumber(nv) {
      if (nv >= this.defaultSku.stock) {
        this.isMaxProdNumber = true
      } else {
        this.isMaxProdNumber = false
      }
    }
  },
  methods: {

    /**
     * 规格弹窗底部确定按钮
     */
    skuPopupConfirm() {
      if (!this.defaultSku.stock) {
        uni.showToast({
          title: '所选规格无货',
          duration: 2000,
          icon: 'none'
        })
        return
      }
      if (this.skuPopConfirmType === 1) {
        const params = {
          url: '/mall4cloud_product/a/shop_cart/change_item',
          method: 'POST',
          data: {
            skuId: this.defaultSku.skuId,
            spuId: this.defaultSku.spuId,
            count: this.prodNumber,
            oldSkuId: ''
          },
          callBack: res => {
            uni.showToast({
              title: '加入购物车成功~',
              duration: 2000,
              icon: 'none'
            })
            this.getCartCount()
          }
        }
        http.request(params)
      }
      if (this.skuPopConfirmType === 2) {
        uni.setStorageSync('shopCartItem', JSON.stringify({
          count: this.prodNumber,
          shopId: this.prodInfo.shopId,
          skuId: this.defaultSku.skuId,
          spuId: this.spuId
        }))
        uni.navigateTo({
          url: '/pages/submit-order/submit-order?orderEntry=1'
        })
      }
      this.skuPopConfirmType = 0
      this.closePopup()
    },

    /**
     * 加入购物车按钮
     */
    addCart() {
      this.skuPopConfirmType = 1
      this.isShowSkuPopup = true
    },

    /**
     * 修改商品数量
     */
    changeNumber(num) {
      let prodNumber = parseInt(this.prodNumber)

      if (num < 0) { // 点击数量-
        if (prodNumber===1) {
          return
        } else {
          prodNumber += num
        }
      } else { // 点击数量+
        // 限购判断
        if (this.hasMaxNumber) {
          if (prodNumber >= this.maxNum) {
            uni.showToast({
              title: `限购 ${this.maxNum} 件`,
              duration: 2000,
              icon: 'none'
            })
          }
          if (this.defaultSku.stock < prodNumber) {
             uni.showToast({
              title: '库存不足',
              duration: 2000,
              icon: 'none'
            })
          }
          if (prodNumber < this.maxNum) {
            prodNumber += num
          }
        } else { // 不限购
          if (prodNumber < this.defaultSku.stock) {
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
      this.prodNumber = prodNumber
    },

    /**
     * 获取商品详情
     */
    getProdInfo() {
      uni.showLoading()
      const params = {
        url: '/mall4cloud_product/ua/spu/prod_info',
        method: 'GET',
        data: {
          spuId: this.spuId
        },
        callBack: res => {
          this.shopId = res.shopId
          this.getShopInfo()
          uni.hideLoading()
          let imgList = []
          if (res.imgUrls) {
            imgList = res.imgUrls.split(',')
          }
          console.log(res.priceFee)
          // 组装sku
          this.groupSkuProp(res.skus, res.priceFee)

          this.prodInfo = res
          this.deliveryModeVO = res.deliveryModeVO
          this.prodDetail = util.formatHtml(res.detail)
          this.imgList = imgList
          // this.deliveryModeVO = JSON.parse(res.deliveryMode)
          this.skuList = res.skus

        }
      }
      http.request(params)
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
        },
        errCallBack: errMsg => {
          console.log(errMsg)
        }
      }
      http.request(params)
    },
     /**
     * 组装SKU
     */
    groupSkuProp: function(skuList, defaultPrice) {
      if (skuList.length == 1 && skuList[0].properties == "") {
        this.defaultSku = skuList[0]
        // this.setDefaultGroupSku();
        return;
      }
      let skuGroup = {};
      let allProperties = [];
      let propKeys = [];
      let selectedPropObj = {}
      let defaultSku = null;
      for (var i = 0; i < skuList.length; i++) {
        var isDefault = false;
        if (!defaultSku && skuList[i].priceFee == defaultPrice) { //找到和商品价格一样的那个SKU，作为默认选中的SKU
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
      this.defaultSku = defaultSku
      this.propKeys = propKeys
      this.selectedPropObj = selectedPropObj
      this.skuGroup = skuGroup
      this.allProperties = allProperties
      this.skuList = skuList
      // this.setDefaultGroupSku()
      this.parseSelectedObjToVals(skuList)

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

    /**
     * 顶部Tab栏点击，页面滚动至指定位置
     * @param {number} type 0页面置顶; 1滚动至评价; 2滚动至详情
     */
    hanleTopTabClick(type) {
      this.topTabSts = type
      if (type === 0) {
        uni.pageScrollTo({
          scrollTop: 0,
          duration: 100
        });
        return
      }
      let className = ''
      className = type === 1 ? 'comment-box' : 'det-det'
      uni.createSelectorQuery().select('.' + className).boundingClientRect( data => {//目标节点
        uni.createSelectorQuery().select('.detail').boundingClientRect( res => {//最外层盒子节点
          let scrollTop = 0
          if (data.top < 0) {
            scrollTop =  Math.abs(res.top) - Math.abs(data.top) - 60
          } else {
            scrollTop = Math.abs(res.top) + Math.abs(data.top) - 60
          }
          uni.pageScrollTo({
          duration: 100,
          scrollTop: scrollTop
          })
        }).exec()
      }).exec()
    },

    /**
     * 显示sku弹窗
     */
    showSkuPopup() {
      this.isShowSkuPopup = true
    },

    /**
     * 显示评论弹窗
     */
    showCommentPopup() {
      this.isShowCommentPopup = true
    },

    /**
     * 隐藏弹窗
     */
    closePopup() {
      this.isShowDiscountPopup = false
      this.isShowSkuPopup = false
      this.isShowCommentPopup = false
      this.skuPopConfirmType = 0
    },

    /**
     * 立即购买
     */
    goSubmit() {
      this.skuPopConfirmType = 2
      this.isShowSkuPopup = true
    },

    /**
     * 获取/更新购物车数据
     */
    getCartCount() {
      const params = {
        url: '/mall4cloud_product/a/shop_cart/prod_count',
        method: 'GET',
        data: {},
        callBack: res => {
          if (res) {
            this.cartCount = res
          }
        }
      }
      if (uni.getStorageSync('token')) {
        http.request(params)
      }
    },

    /**
     * 去首页
     */
    toIndex() {
      uni.switchTab({
        url: '/pages/index/index'
      })
    },

    /**
     * 去购物车
     */
    toCart() {
      uni.switchTab({
        url: '/pages/cart/cart'
      })
    },

    /**
     * 去店铺首页
     */
    toShopIndex() {
      uni.navigateTo({
        url: `/pages/shop-page/shop-index?shopId=${this.shopId}`
      })
    }
  }
}
</script>

<style>
@import "./detail.css";
@import "./../../popup.css";
</style>
