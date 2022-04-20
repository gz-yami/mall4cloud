<template>
  <view class="order">
    <view class="search-box">
      <view class="search">
        <view class="icon">
          <image src="/static/images/search.png" />
        </view>
        <view class="text">输入关键字搜索</view>
      </view>
    </view>
    <view class="tab">
      <view :class="['item', orderStatus==0? 'active' : '']" @tap="switchOrderSts(0)">全部</view>
      <view :class="['item', orderStatus==1? 'active' : '']" @tap="switchOrderSts(1)">待付款</view>
      <view :class="['item', orderStatus==2? 'active' : '']" @tap="switchOrderSts(2)">待发货</view>
      <view :class="['item', orderStatus==3? 'active' : '']" @tap="switchOrderSts(3)">待收货</view>
      <view :class="['item', orderStatus==5? 'active' : '']" @tap="switchOrderSts(5)">已完成</view>
    </view>
    <view class="order-box">
      <block v-for="(orderItem, orderIdx) in orderList" :key="orderIdx">
        <view class="item">
          <!-- 店铺 -->
          <view class="shop-info">
            <view class="shop">
              <view class="icon">
                <image src="/static/images/shop.png" />
              </view>
              <view class="name text-arrow">{{ orderItem.shopName }}</view>
            </view>
            <view class="status">{{ ['','待付款','待发货','','待收货','已完成','已取消'][orderItem.status] }}</view>
            <view v-if="orderItem.status==5" class="order-complete">
              <image src="/static/images/order-finish.png" />
              <!-- <image src="/static/images/order-cancel.png" /> -->
            </view>
            <view v-if="orderItem.status==6" class="del" @tap="hanldeShowOrderOperPopup(0,orderItem.orderId)">
              <image src="/static/images/del.png" />
            </view>
            <!-- 已完成或已取消时才显示“order-complete”和“del” -->
          </view>

          <!-- 单商品 -->
          <view v-if="orderItem.orderItems.length===1" class="single-prod" @tap="toOrderDetail(orderItem.orderId)">
            <block v-for="(prodItem,prodIdx) in orderItem.orderItems" :key="prodIdx">
              <view class="prod-img">
                <image :src="prodItem.pic" />
              </view>
              <view class="text-box">
                <view class="name">{{ prodItem.spuName }}</view>
                <view class="sku">{{ prodItem.skuName }}</view>
                <view class="price-box">
                  <view class="price">
                    <view class="symbol">￥</view>
                    <view class="big">{{ wxs.parsePrice(orderItem.total)[0] }}</view>
                    <view class="symbol">.{{ wxs.parsePrice(orderItem.total)[1] }}</view>
                  </view>
                  <view class="count">×{{ orderItem.allCount }}</view>
                </view>
              </view>
            </block>
          </view>

          <!-- 多商品 -->
          <view v-else class="many-prods" @tap="toOrderDetail(orderItem.orderId)">
            <scroll-view scroll-x="true" class="img-box">
              <view v-for="(prodItem,prodIdx) in orderItem.orderItems" :key="prodIdx" class="img">
                <image :src="prodItem.pic" />
              </view>
            </scroll-view>
          </view>

          <!-- 操作 -->
          <view class="action-box">
            <view class="number-box">
              <view class="price">
                <view class="symbol">￥</view>
                <view class="big">{{ wxs.parsePrice(orderItem.total)[0] }}</view>
                <view class="symbol">.{{ wxs.parsePrice(orderItem.total)[1] }}</view>
              </view>
              <view class="count">共{{ orderItem.allCount }}件</view>
            </view>
            <view class="btn-box">
              <view v-if="orderItem.status==1" class="btn" @tap="hanldeShowOrderOperPopup(1,orderItem.orderId)">取消订单</view>
              <view v-if="orderItem.status==1" class="btn btn-r" @tap="payNow(orderItem.orderId)">立即支付</view>
              <view v-if="orderItem.status==3" class="btn" @tap="hanldeShowOrderOperPopup(2,orderItem.orderId)">确认收货</view>
            </view>
          </view>
        </view>
      </block>

      <view class="nomore">没有更多了，看看别的吧</view>
    </view>
    <!-- 删除订单弹窗 -->
    <view class="popup-min" :class="{'show' : isShowDelOrderPopup}">
      <view class="popup-mask" @tap="closePopup" />
      <view class="popup-con-middle del-order">
        <view v-if="operType!=2" class="big-text">是否{{ operType===0?'删除':'取消' }}此订单？</view>
        <view v-if="operType!=2" class="small-text">订单{{ operType===0?'删除':'取消' }}后将不能恢复，请您慎重考虑</view>
        <view v-if="operType===2" class="big-text">我已收到货？</view>
        <view v-if="operType===2" class="small-text">确认收货后将改变订单状态</view>
        <view class="btns">
          <view class="btn" @tap="closePopup">取消</view>
          <view class="btn btn-r" @tap="confirmCancelOrder">确认</view>
        </view>
      </view>
    </view>
  </view>
</template>
<script module="wxs" lang="wxs" src="../../wxs/index.wxs"></script>

<script>
const http = require('../../utils/http.js')
export default {
  data() {
    return {
      isShowDelOrderPopup: false, // 删除订单弹窗
      orderStatus: 0, // 订单状态
      pageNum: 1,
      pages: 0,
      orderList: [],
      operType: 0, // 订单操作类型:0取消; 1删除
      operOrderId: '', // 当前操作的订单id
    }
  },
  onLoad(options) {
    this.orderStatus = options.orderStatus
  },
  onShow() {
    // 获取订单列表
    this.getOrderList()
  },
  onReachBottom() {
    if (this.pageNum < this.pages) {
      this.pageNum +=1
      this.getOrderList()
    }
  },
  methods: {
    /**
     * 立即支付
     */
    payNow(orderId) {
      // http.request(params)
      uni.navigateTo({
        url: '/pages/payment/payment?orderIds=' + orderId
      })
    },

    /**
     * 确认操作订单
     */
    confirmCancelOrder() {
      let url = ''
      let reqMethod = ''
      if (this.operType===0) {
        url = '/mall4cloud_order/p/myOrder/'
        reqMethod = 'DELETE'
      } else if (this.operType===1) {
        url = '/mall4cloud_order/p/myOrder/cancel/'
        reqMethod = 'PUT'
      } else if (this.operType===2) {
        url = '/mall4cloud_order/p/myOrder/receipt/'
        reqMethod = 'PUT'
      }
      url = url + this.operOrderId
      const params = {
        url: url,
        method: reqMethod,
        callBack: res => {
          this.closePopup()
          uni.showToast({
            title: '操作成功',
            duration: 2000
          })
          setTimeout(()=> {
            this.getOrderList()
          }, 1000)
        }
      }
      http.request(params)
    },

    /**
     * 显示订单操作弹窗
     * @param {number} operType 0:取消订单; 1删除订单; 2确认收货
     */
    hanldeShowOrderOperPopup(operType,orderId) {
      this.operType = operType
      this.operOrderId = orderId
      this.isShowDelOrderPopup = true
    },

    /**
     * 切换订单状态
     */
    switchOrderSts(sts) {
      this.orderStatus = sts
      this.pageNum = 1
      this.getOrderList()
    },

    /**
     * 获取订单列表
     */
    getOrderList() {
      const params = {
        url: '/mall4cloud_order/p/myOrder/search_order',
        method: 'GET',
        data: {
          status: this.orderStatus,
          pageNum: this.pageNum,
          pageSize: 5
        },
        callBack: res => {
          this.pages = res.pages
          res.list.forEach(orderItem => {
            orderItem.totalCounts = 0
            if (orderItem.returnMoneySts == null) {
              orderItem.returnMoneySts = 0
            }
            orderItem.orderItems.forEach(prodItem => {
              orderItem.totalCounts += prodItem.count
            })
          })
          let list = []
          if (this.pageNum === 1) {
            list = res.list
          } else {
            list = this.orderList.concat(res.list)
          }
          this.orderList = list
        }
      }
      http.request(params)
    },

    // 去订单详情
    toOrderDetail(orderId) {
      uni.navigateTo({
        url: '/pages/order-detail/order-detail?orderId=' + orderId
      })
    },

    // 查看物流
    viewLogistics(orderId) {
      uni.navigateTo({
        url: '/pages/logistics-detail/logistics-detail?orderId=' + orderId
      })
    },

    // 关闭弹窗
    closePopup() {
      this.isShowDelOrderPopup = false
    }

  }
}
</script>

<style>
@import "./order.css";
@import "./../../popup.css";
</style>
