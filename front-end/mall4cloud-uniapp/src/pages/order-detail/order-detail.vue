<template>
  <view class="order-detail">
    <!-- 订单状态 -->
    <view class="status-box">
      <view class="bg">
        <image src="/static/images/status-bg.jpg" />
      </view>
      <view class="text">
        <block v-if="status === 1">
          <view class="status">
            等待付款
          </view>
          <view class="des">
            请您在
            <text class="time">
              29:59
            </text>内付款，逾期订单将取消
          </view>
        </block>
        <block v-else>
          <view class="status">
            {{ ['', '', '等待发货', '等待收货', '', '已完成', '已取消'][status] }}
          </view>
          <view class="des">
            {{ ['', '', '商家还未发货，请您耐心等待', '商品正在运输中，请您耐心等待', '', '感谢您的购买，欢迎下次光临', '您的订单已成功取消'][status] }}
          </view>
        </block>
      </view>
      <view class="icon">
        <image
          v-if="status === 1"
          src="/static/images/det-paid.png"
        />
        <!-- 待付款 -->
        <image
          v-if="status === 2"
          src="/static/images/det-delivered.png"
        />
        <!-- 待发货 -->
        <image
          v-if="status === 3"
          src="/static/images/det-received.png"
        />
        <!-- 带收货 -->
        <image
          v-if="status === 5"
          src="/static/images/det-finish.png"
        />
        <!-- 已完成 -->
        <image
          v-if="status === 6"
          src="/static/images/det-cancel.png"
        />
        <!-- 已取消 -->
      </view>
    </view>

    <!-- 地址 -->
    <view
      v-if="orderAddr"
      class="address-box"
    >
      <view class="icon">
        <image src="/static/images/local.png" />
      </view>
      <view class="text-box">
        <view class="user">
          <view class="name">
            {{ orderAddr.consignee }}
          </view>
          <view class="phone">
            {{ orderAddr.mobile }}
          </view>
        </view>
        <view class="address">
          {{ orderAddr.province }}{{ orderAddr.city }}{{ orderAddr.area }}{{ orderAddr.addr }}
        </view>
      </view>
    </view>

    <!-- 商品 -->
    <view class="prods-box">
      <view class="shop">
        <view class="icon">
          <image src="/static/images/shop.png" />
        </view>
        <view class="name text-arrow">
          {{ shopName }}
        </view>
      </view>
      <block
        v-for="(prodItem, prodIdx) in orderItems"
        :key="prodIdx"
      >
        <view
          class="prod-item"
          @tap="toProdDetail(prodItem.spuId)"
        >
          <view class="info">
            <view class="prod-img">
              <image :src="prodItem.pic" />
            </view>
            <view class="text-box">
              <view class="name">
                {{ prodItem.spuName }}
              </view>
              <view class="sku">
                {{ prodItem.skuName }}
              </view>
              <view class="price-box">
                <view class="price">
                  <view class="symbol">
                    ￥
                  </view>
                  <view class="big">
                    {{ wxs.parsePrice(prodItem.price)[0] }}
                  </view>
                  <view class="symbol">
                    .{{ wxs.parsePrice(prodItem.price)[1] }}
                  </view>
                </view>
                <view class="count">
                  ×{{ prodItem.count }}
                </view>
              </view>
            </view>
          </view>
          <view
            v-if="status != 1"
            class="action"
          >
            <view
              class="btn"
              @tap.stop="addCart(prodItem.spuId, prodItem.skuId, prodItem.count)"
            >
              加购物车
            </view>
          </view>
          <!-- 待付款时不显示action,已取消时显示“加购物车”,待发货、待收货、已完成显示“申请售后”和“加购物车” -->
        </view>
      </block>
    </view>

    <!-- 订单信息 -->
    <view class="order-info">
      <view class="item-box">
        <view class="item">
          <view class="tit">
            订单编号：
          </view>
          <view class="text">
            {{ orderId }}
          </view>
          <view
            class="copy"
            @tap="copy"
          >
            复制
          </view>
        </view>
        <view class="item">
          <view class="tit">
            下单时间：
          </view>
          <view class="text">
            {{ createTime }}
          </view>
        </view>
        <view class="item-box">
          <view class="item">
            <view class="tit">
              配送方式：
            </view>
            <view class="text">
              {{ ['', '普通快递', '用户自提', '无需快递', '同城配送'][deliveryType] }}
            </view>
          </view>
        </view>
      </view>
    </view>
    <view class="order-info">
      <view class="item-box">
        <view class="item order-price">
          <view class="price">
            <view class="price-text">
              订单总额：
            </view>
            <view class="symbol">
              ￥
            </view>
            <view class="big">
              {{ wxs.parsePrice(actualTotal)[0] }}
            </view>
            <view class="symbol">
              .{{ wxs.parsePrice(actualTotal)[1] }}
            </view>
          </view>
        </view>
      </view>
    </view>

    <!-- 底部 -->
    <view class="foot-box">
      <!-- <view class="del-order">删除订单</view> -->
      <!-- 已完成或已取消才出现 -->
      <view class="btn-box">
        <!-- 待付款时出现 -->
        <view
          v-if="status == 1"
          class="btn btn-r"
          @tap="payNow"
        >
          立即支付
        </view>
        <!-- 待发货时出现 -->
        <view
          v-if="status == 3"
          class="btn btn-r"
          @tap="orderOperation(2)"
        >
          确认收货
        </view>
        <!-- 待收货时出现 -->
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue'
import uniCopy from '../../js_sdk/uni-copy'

const wxs = number()
const Data = reactive({
  orderId: '',
  actualTotal: 0,
  userAddrDto: {},
  remarks: '',
  orderItems: [],
  createTime: '',
  status: 0,
  productTotalAmount: '',
  transfee: '',
  freeTransfee: '',
  reduceAmount: '',
  orderType: 0,
  shopName: '',
  canRefund: '',
  canAllRefund: '',
  payType: '',
  deliveryType: 3, // 配送类型 3：无需快递
  orderScore: '',
  orderAddr: {}
})
const { orderId, actualTotal, orderItems, createTime, status, shopName, deliveryType, orderAddr } = toRefs(Data)

onLoad((options) => {
  if (options.orderId) {
    Data.orderId = options.orderId
  }
})

onShow(() => {
  // 请求订单详情数据
  loadOrderDetail()
})

/**
 * 加入购物车
 */
const addCart = (spuId, skuId, count) => {
  const params = {
    url: '/mall4cloud_product/a/shop_cart/change_item',
    method: 'POST',
    data: {
      skuId,
      spuId,
      count
    }
  }
  http.request(params).then(() => {
    uni.showToast({
      title: '加入购物车成功~',
      duration: 2000,
      icon: 'none'
    })
  })
}

/**
 * 立即支付
 */
const payNow = () => {
  uni.navigateTo({
    url: '/pages/payment/payment?orderIds=' + Data.orderId
  })
}

/**
 * 订单操作按钮
 * @param {number} operType 0:取消订单; 1删除订单; 2确认收货
 */
const orderOperation = (operType) => {
  let url = ''
  let reqMethod = ''
  if (operType === 0) {
    url = '/mall4cloud_order/p/myOrder/'
    reqMethod = 'DELETE'
  } else if (operType === 1) {
    url = '/mall4cloud_order/p/myOrder/cancel/'
    reqMethod = 'PUT'
  } else if (operType === 2) {
    url = '/mall4cloud_order/p/myOrder/receipt/'
    reqMethod = 'PUT'
  }
  url = url + Data.orderId
  const params = {
    url,
    method: reqMethod
  }
  http.request(params).then(() => {
    uni.showToast({
      title: '操作成功',
      duration: 2000
    })
    setTimeout(() => {
      loadOrderDetail()
    }, 1000)
  })
}

/**
 * 跳转商品详情
 */
const toProdDetail = (spuId) => {
  uni.navigateTo({
    url: '/pages/detail/detail?spuId=' + spuId
  })
}

/**
 * 加载订单数据
 */
const loadOrderDetail = () => {
  const params = {
    url: '/mall4cloud_order/p/myOrder/order_detail',
    method: 'GET',
    data: {
      orderId: Data.orderId
    }
  }
  http.request(params).then(res => {
    Data.orderAddr = res.orderAddr
    Data.actualTotal = res.total
    Data.userAddrDto = res.userAddrDto
    Data.remarks = res.remarks
    Data.orderItems = res.orderItems
    Data.createTime = res.createTime
    Data.status = res.status
    Data.transfee = res.transfee
    Data.freeTransfee = res.freeTransfee // 运费减免
    Data.reduceAmount = res.reduceAmount
    Data.orderType = res.orderType
    Data.shopName = res.shopName
    Data.canRefund = res.canRefund
    Data.canAllRefund = res.canAllRefund
    Data.payType = res.payType
    Data.deliveryType = res.deliveryType // 配送类型 1:快递 2:自提 3：无需快递
    Data.orderScore = res.orderScore // 整单使用积分
  })
}

/**
 * 复制订单号
 */
const copy = () => {
  uniCopy({
    content: Data.orderId,
    success: (res) => {
      uni.showToast({
        title: res,
        icon: 'none'
      })
    },
    error: (e) => {
      uni.showToast({
        title: e,
        icon: 'none',
        duration: 3000
      })
    }
  })
}

</script>

<style lang="scss" scoped>
@use "order-detail";
</style>
