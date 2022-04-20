<template>
  <view class="submit-order">
    <!-- 邮寄到家 -->
    <view class="address-box">
      <view v-if="isEditAddress" class="edit-address">
        <view class="tit">
          <!-- <view class="text">填写收货信息</view> -->
          <view class="text">历史选用地址</view>
          <view
            class="total text-arrow"
            @tap="showAddressListPopup"
          >共 {{ addressList.length }} 个</view>
        </view>
        <view class="add-box">
          <view class="add-item">
            <input v-model="consignee" type="text" maxlength="20" class="input" placeholder="请输入收件人姓名">
          </view>
          <view class="add-item">
            <input v-model="mobile" type="number" maxlength="11" class="input" placeholder="请输入联系手机号">
          </view>
          <view class="add-item" @tap="showPicker=true">
            <view class="area text-arrow">
              <text v-if="province" class="text">{{ province }} {{ city }} {{ area }}</text>
              <text v-else class="tip-text">请选择所在地区</text>
              <address-picker v-if="showPicker" :show-picker="showPicker" :addr-ids="addrIds" @setAddressInfo="addressPickerConfirm" />
            </view>
          </view>
          <view class="add-item">
            <input v-model="addr" type="text" class="input" placeholder="详细地址、街道、门牌号等，5-50字">
          </view>
          <view class="add-item btns">
            <view class="btn btn-r" @tap="saveAndUse">保存并使用</view>
            <view class="btn" @tap="exitEditAddress">取消</view>
          </view>
        </view>
      </view>
      <view v-if="!isEditAddress" class="current-address">
        <block v-if="userAddr && userAddr.addrId">
          <view class="c-address">{{ userAddr.province + userAddr.city + userAddr.area+userAddr.addr }}</view>
          <view class="c-user">
            <text class="name">{{ userAddr.consignee }}</text>
            <text class="phone">{{ userAddr.mobile }}</text>
          </view>
        </block>
        <block v-else>
          <view class="c-address">暂无可用地址，请点击右方编辑按钮添加</view>
        </block>
        <view class="c-edit" @tap="editAddress">
          <image src="/static/images/edit.png" />
        </view>
      </view>
    </view>
    <!-- /邮寄到家 -->
    <!-- 可用地址弹窗 -->
    <view class="popup" :class="{ show: isShowAddressListPopup }">
      <view class="popup-mask" @tap="closePopup" />
      <view class="popup-con-bottom check-address">
        <view class="popup-tit">
          <view class="tit-text">可选用地址</view>
          <view class="close" @tap="closePopup">
            <image src="/static/images/close.png" />
          </view>
        </view>
        <radio-group class="con-box-min" @change="addrRadioChange">
          <block v-for="(addressItem, addressIndex) in addressList" :key="addressIndex">
            <label class="address-item">
              <view class="text-box">
                <view class="address">
                  <text v-if="addressItem.isDefault" class="default">默认</text>
                  {{ addressItem.province+addressItem.city+addressItem.area+addressItem.addr }}
                </view>
                <view class="user">
                  <view class="name">{{ addressItem.consignee }}</view>
                  <view class="phone">{{ addressItem.mobile }}</view>
                </view>
              </view>
              <radio color="#fff" :value="String(addressItem.addrId)" :checked="addressItem.addrId==addrId" />
            </label>
          </block>
        </radio-group>
        <view class="btn-box">
          <view class="btn" @tap="confirmChangeAddr">确定</view>
        </view>
      </view>
    </view>
    <view class="shop-item">
      <!-- 店铺信息 -->
      <block v-for="(shopItem, shopIndex) in shopCartOrders" :key="shopIndex">
        <view class="shop-box">
          <view class="shop-icon">
            <image src="/static/images/shop.png" />
          </view>
          <view class="shop-name">{{ shopItem.shopName }}</view>
        </view>
        <!-- /店铺信息 -->
        <!-- 店铺商品明细 -->
        <view class="shop-prods">
          <!-- 活动信息 -->
          <block v-for="(item, index) in shopItem.shopCartItemVO" :key="'1'+index">
            <view class="prod-item">
              <view class="single-prod">
                <view class="pic">
                  <image :src="item.imgUrl" />
                </view>
                <view class="info">
                  <view class="name">{{ item.spuName }}</view>
                  <view class="sku">{{ item.skuName }}</view>
                  <view class="price-box">
                    <view class="price">
                      <text class="symbol">￥</text>
                      <text class="big">{{ wxs.parsePrice(item.skuPriceFee)[0] }}</text>
                      <text class="symbol">.{{ wxs.parsePrice(item.skuPriceFee)[1] }}</text>
                    </view>
                    <view class="count">×{{ item.count }}</view>
                  </view>
                </view>
              </view>
            </view>
          </block>
        </view>
      </block>
    </view>

    <!-- 总金额计算 -->
    <view class="msg-item">

      <!-- 商品总额 -->
      <view class="item">
        <view class="item-tit">商品总额</view>
        <view class="price black">
          <text class="symbol">￥</text>
          <text class="big-num">{{ wxs.parsePrice(total)[0] }}</text>
          <text class="small-num">.{{ wxs.parsePrice(total)[1] }}</text>
        </view>
      </view>
    </view>

    <!-- 底部栏 -->
    <view class="submit-footer">
      <view class="total">
        <view class="text">合计：</view>
        <view class="price">
          <text class="symbol">￥</text>
          <text class="big">{{ wxs.parsePrice(total)[0] }}</text>
          <text class="symbol">.{{ wxs.parsePrice(total)[1] }}</text>
        </view>
      </view>
      <view class="btn" @tap="toPay">提交订单</view>
    </view>
    <!-- 确认弹窗 -->
    <confirm-pop :show-pop="showPop" :pop-content="popContent" :show-cancel="false" @handlePopConfirm="popConfirm" />
  </view>
</template>

<script module="wxs" lang="wxs" src="../../wxs/index.wxs"></script>

<script>
var index = [18, 0, 0]
var t = 0
var show = false
var moveY = 200
const http = require('../../utils/http.js')
const util = require('../../utils/util.js')
const config = require('../../utils/config.js')
import AddressPicker from '../../components/AddressPicker/index'
export default {
  components: {
    AddressPicker
  },
  data() {
    return {
      isFirst: true,
      showPop: false,
      isSelfRaising: true, // 是否自提
      isEditAddress: false, // 编辑地址
      isShowAddressListPopup: false, // 历史选用地址弹窗
      popContent: {
        content: ''
      },
      total: 0,
      actualTotal: 0,
      shopCartOrders: [],
      orderReduce: 0,
      totalTransfee: 0,

      // 地址相关
      // orderEntry 1为立即购买,否则为从购物车提交
      orderEntry: '',
      // 地址列表
      addressList: [],
      // 当前订单选择的地址id
      addrId: 0,
      addrIds:[],
      // 当前订单选择的用户地址
      userAddr: {},
      consignee: '',
      mobile: '',
      addr: '',
      showPicker: false,
      value: [0, 0, 0],
      provinceId: 0,
      cityId: 0,
      areaId: 0,
      province: '',
      city: '',
      area: '',
      // 订单类型
      orderType: '',

    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (options.orderEntry) {
      this.orderEntry = options.orderEntry
    }
    // 获取用户地址列表
    this.getAddressList()
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() { },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    // 加载订单数据
    this.loadOrder(this.isFirst)

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
   * 用户点击右上角分享
   */
  onShareAppMessage: function() { },
  methods: {
    /**
     * 添加新的地址并使用
     */
    saveAndUse() {
      // 数据校验
      if (!this.consignee.trim()) {
        uni.showToast({
          title: '请输入收件人',
          icon: 'none'
        })
        return
      }
      if (!util.checkPhoneNumber(this.mobile)) {
        uni.showToast({
          title: '请输入正确的手机号',
          icon: 'none'
        })
        return
      }
      if (!this.addr.trim()) {
        uni.showToast({
          title: '请输入详细地址',
          icon: 'none'
        })
        return
      }
      // 保存地址
      const params = {
        url: '/mall4cloud_user/a/user_addr',
        method: 'POST',
        data: {
          addr: this.addr,
          area: this.area,
          areaId: this.areaId,
          city: this.city,
          cityId: this.cityId,
          consignee: this.consignee,
          mobile: this.mobile,
          province: this.province,
          provinceId: this.provinceId
        },
        callBack: res => {
          this.isEditAddress = false
          this.getAddressList()
          if (!this.userAddr) {
            this.loadOrder()
          }
        }
      }
      http.request(params)
    },
    /**
     * 弹窗确认
     */
    popConfirm() {
      this.showPop = false
    },

    /**
     * 地区弹窗确认按钮
    */
    addressPickerConfirm(selectedAddrInfo, isChange) {
      if (isChange) {
        this.area = selectedAddrInfo.area
        this.areaId = selectedAddrInfo.areaId
        this.city = selectedAddrInfo.city
        this.cityId = selectedAddrInfo.cityId
        this.province = selectedAddrInfo.province
        this.provinceId = selectedAddrInfo.provinceId
        this.addrIds = [selectedAddrInfo.provinceId, selectedAddrInfo.cityId, selectedAddrInfo.areaId]
      }
      this.showPicker = false
    },

    /**
     * 添加新的地址并使用
     */
    saveAndUse() {
      // 数据校验
      if (!this.consignee.trim()) {
        uni.showToast({
          title: '请输入收件人姓名',
          icon: 'none'
        })
        return
      }
      if (this.consignee.length < 2) {
        uni.showToast({
          title: '收件人姓名需在2到20个字符之间',
          icon: 'none'
        })
        return
      }
      if (!util.checkPhoneNumber(this.mobile)) {
        uni.showToast({
          title: '请输入正确的手机号',
          icon: 'none'
        })
        return
      }
      if (!this.area) {
        uni.showToast({
          title: '请选择完整地区信息',
          icon: 'none'
        })
        return
      }
      if (!this.addr.trim() || this.addr.length < 5) {
        uni.showToast({
          title: '请输入字数在5-50之间的详细地址',
          icon: 'none'
        })
        return
      }
      // 保存地址
      const params = {
        url: '/mall4cloud_user/user_addr',
        method: 'POST',
        data: {
          addr: this.addr,
          area: this.area,
          areaId: this.areaId,
          city: this.city,
          cityId: this.cityId,
          consignee: this.consignee,
          mobile: this.mobile,
          province: this.province,
          provinceId: this.provinceId
        },
        callBack: res => {
          this.isEditAddress = false
          this.clearAddressInput()
          this.getAddressList('reloadOrder')
          if (!this.userAddr) {
            this.loadOrder()
          }
        }
      }
      http.request(params)
    },

    /**
     * 选择地址
     */
    addrRadioChange(e) {
      this.addrId = e.detail.value
    },
    /**
     * 确定地址
     */
    confirmChangeAddr() {
      this.isEditAddress = false
      this.closePopup()
      this.loadOrder()
    },
    /**
     * 获取用户地址列表
     */
    getAddressList(reload) {
      const params = {
        url: '/mall4cloud_user/user_addr/list',
        method: 'GET',
        data: {},
        callBack: res => {
          this.addressList = res
          if (reload) {
            this.addrId = res[0].addrId
            this.loadOrder()
          }
        }
      }
      http.request(params)
    },

    /**
     * 退出编辑地址
     */
    exitEditAddress() {
      this.isEditAddress = false
      this.clearAddressInput()
    },

    /**
     * 清除编辑地址输入框
     */
    clearAddressInput() {
      this.consignee = ''
      this.mobile = ''
      this.addr = ''
      this.cityId = ''
      this.provinceId = ''
    },

    // 滑动事件
    bindChange(e) {
      const val = e.detail.value // 判断滑动的是第几个column
      // 若省份column做了滑动则定位到地级市和区县第一位

      if (index[0] != val[0]) {
        val[1] = 0
        val[2] = 0 // 更新数据

        this.getCityArray(this.provArray[val[0]].areaId) // 获取地级市数据
      } else {
        // 若省份column未做滑动，地级市做了滑动则定位区县第一位
        if (index[1] != val[1]) {
          val[2] = 0 // 更新数据

          this.getAreaArray(this.cityArray[val[1]].areaId) // 获取区县数据
        }
      }

      index = val
      this.value = [val[0], val[1], val[2]]
      this.province = this.provArray[this.value[0]].areaName
      this.city = this.cityArray[this.value[1]].areaName
      this.area = this.areaArray[this.value[2]].areaName
      this.provinceId = this.provArray[this.value[0]].areaId
      this.cityId = this.cityArray[this.value[1]].areaId
      this.areaId = this.areaArray[this.value[2]].areaId
    },

    // 移动按钮点击事件
    translate: function(e) {
      if (t === 0) {
        moveY = 0
        show = false
        t = 1
      } else {
        moveY = 200
        show = true
        t = 0
      }
      this.show = true
      this.showPicker = true
      this.animationEvents(this, moveY, show)
    },

    // 隐藏弹窗浮层
    hiddenFloatView(e) {
      moveY = 200
      show = true
      t = 0
      this.animationEvents(this, moveY, show)
      this.showPicker = false
    },

    // 动画事件
    animationEvents: function(that, moveY, show) {
      that.animation = uni.createAnimation({
        transformOrigin: '50% 50%',
        duration: 400,
        timingFunction: 'ease',
        delay: 0
      })
      that.animation.translateY(moveY + 'vh').step()
      that.animation = that.animation.export()
    },
    /**
     * 空方法
     */
    nono() {

    },
    /**
     * 加载订单数据
     */
    loadOrder(isFirst) {
      const params = {
        url: '/mall4cloud_order/a/order/confirm',
        method: 'POST',
        data: {
          addrId:this.addrId,
          dvyType: 3,
          shopCartItem: this.orderEntry==='1' ? JSON.parse(uni.getStorageSync('shopCartItem')) : undefined
        },
        callBack: res => {
          let shopCartOrders = res.shopCartOrders
          let remarksList = []
          shopCartOrders.forEach((shopCart, index) => {
              shopCart.shopCartItemVO.forEach(prodItem => {
                if (prodItem.imgUrl.indexOf('http')===-1) {
                  prodItem.imgUrl = config.resourcesUrl + prodItem.imgUrl
                }
              })
            if (isFirst) {
              shopCart.remarks = '';
            } else {
              remarksList.push(shopCart.remarks)
              shopCartOrders[index].remarks = remarksList[index]
            }
          })
          this.shopCartOrders = shopCartOrders
          this.total = res.total
          this.actualTotal = res.actualTotal
          this.totalTransfee = res.totalTransfee
          this.userAddr = res.userAddr
          this.addrId = res.userAddr.addrId
          this.addrIds = [res.userAddr.provinceId, res.userAddr.cityId, res.userAddr.areaId]
        }
      }
      http.request(params)
      this.isFirst = false
    },

    // 切换配送方式
    changeDistribution() {
      this.isSelfRaising = !this.isSelfRaising
    },

    // 编辑地址
    editAddress() {
      this.isEditAddress = true
    },

    // 历史选用地址弹窗
    showAddressListPopup() {
      this.isShowAddressListPopup = true
    },

    // 关闭弹窗
    closePopup() {
      this.isShowAddressListPopup = false
      this.isShowDeliveryTimePopup = false
      this.isShowGeneralCouponPopup = false
    },

    // 去支付
    toPay() {
      this.submitOrder()
    },

    /**
     * 提交订单
     */
    submitOrder() {
      let shopCartOrders = this.shopCartOrders;
      let orderShopParam = []
      shopCartOrders.forEach(shopCart => {
        orderShopParam.push({
          remarks: shopCart.remarks,
          shopId: shopCart.shopId
        })
      })
      const params = {
        url: '/mall4cloud_order/a/order/submit',
        method: 'POST',
        data: {
          orderShopParam: orderShopParam
        },
        callBack: res => {
          let orderIds = res.join(',')
          uni.redirectTo({
            url: `/pages/payment/payment?orderIds=${orderIds}&dvyType=1`
          })
        }
      }
      http.request(params)
    },
  }
}
</script>
<style>
@import "./submit-order.css";
@import "./../../popup.css";
</style>
