<template>
  <view class="page-submit-order">
    <!-- 邮寄到家 -->
    <view class="address-box">
      <view
        v-if="isEditAddress"
        class="edit-address"
      >
        <view class="tit">
          <!-- <view class="text">填写收货信息</view> -->
          <view class="text">
            历史选用地址
          </view>
          <view
            class="total text-arrow"
            @tap="showAddressListPopup"
          >
            共 {{ addressList.length }} 个
          </view>
        </view>
        <view class="add-box">
          <view class="add-item">
            <input
              v-model="consignee"
              type="text"
              maxlength="20"
              class="input"
              placeholder="请输入收件人姓名"
            >
          </view>
          <view class="add-item">
            <input
              v-model="mobile"
              type="number"
              maxlength="11"
              class="input"
              placeholder="请输入联系手机号"
            >
          </view>
          <view
            class="add-item"
            @tap="showPicker = true"
          >
            <view class="area text-arrow">
              <text
                v-if="province"
                class="text"
              >
                {{ province }} {{ city }} {{ area }}
              </text>
              <text
                v-else
                class="tip-text"
              >
                请选择所在地区
              </text>
              <address-picker
                v-if="showPicker"
                :show-picker="showPicker"
                :addr-ids="addrIds"
                @set-address-info="addressPickerConfirm"
              />
            </view>
          </view>
          <view class="add-item">
            <input
              v-model="addr"
              type="text"
              class="input"
              placeholder="详细地址、街道、门牌号等，5-50字"
            >
          </view>
          <view class="add-item btns">
            <view
              class="btn btn-r"
              @tap="saveAndUse"
            >
              保存并使用
            </view>
            <view
              class="btn"
              @tap="exitEditAddress"
            >
              取消
            </view>
          </view>
        </view>
      </view>
      <view
        v-if="!isEditAddress"
        class="current-address"
      >
        <block v-if="userAddr && userAddr.addrId">
          <view class="c-address">
            {{ userAddr.province + userAddr.city + userAddr.area + userAddr.addr }}
          </view>
          <view class="c-user">
            <text class="name">
              {{ userAddr.consignee }}
            </text>
            <text class="phone">
              {{ userAddr.mobile }}
            </text>
          </view>
        </block>
        <block v-else>
          <view class="c-address">
            暂无可用地址，请点击右方编辑按钮添加
          </view>
        </block>
        <view
          class="c-edit"
          @tap="editAddress"
        >
          <image src="/static/images/edit.png" />
        </view>
      </view>
    </view>
    <!-- /邮寄到家 -->
    <!-- 可用地址弹窗 -->
    <view
      class="popup"
      :class="{ show: isShowAddressListPopup }"
    >
      <view
        class="popup-mask"
        @tap="closePopup"
      />
      <view class="popup-con-bottom check-address">
        <view class="popup-tit">
          <view class="tit-text">
            可选用地址
          </view>
          <view
            class="close"
            @tap="closePopup"
          >
            <image src="/static/images/close.png" />
          </view>
        </view>
        <radio-group
          class="con-box-min"
          @change="addrRadioChange"
        >
          <block
            v-for="(addressItem, addressIndex) in addressList"
            :key="addressIndex"
          >
            <label class="address-item">
              <view class="text-box">
                <view class="address">
                  <text
                    v-if="addressItem.isDefault"
                    class="default"
                  >默认</text>
                  {{ addressItem.province + addressItem.city + addressItem.area + addressItem.addr }}
                </view>
                <view class="user">
                  <view class="name">{{ addressItem.consignee }}</view>
                  <view class="phone">{{ addressItem.mobile }}</view>
                </view>
              </view>
              <radio
                color="#fff"
                :value="String(addressItem.addrId)"
                :checked="addressItem.addrId == addrId"
              />
            </label>
          </block>
        </radio-group>
        <view class="btn-box">
          <view
            class="btn"
            @tap="confirmChangeAddr"
          >
            确定
          </view>
        </view>
      </view>
    </view>
    <view class="shop-item">
      <!-- 店铺信息 -->
      <block
        v-for="(shopItem, shopIndex) in shopCartOrders"
        :key="shopIndex"
      >
        <view class="shop-box">
          <view class="shop-icon">
            <image src="/static/images/shop.png" />
          </view>
          <view class="shop-name">
            {{ shopItem.shopName }}
          </view>
        </view>
        <!-- /店铺信息 -->
        <!-- 店铺商品明细 -->
        <view class="shop-prods">
          <!-- 活动信息 -->
          <block
            v-for="(item, index) in shopItem.shopCartItemVO"
            :key="'1' + index"
          >
            <view class="prod-item">
              <view class="single-prod">
                <view class="pic">
                  <image :src="item.imgUrl" />
                </view>
                <view class="info">
                  <view class="name">
                    {{ item.spuName }}
                  </view>
                  <view class="sku">
                    {{ item.skuName }}
                  </view>
                  <view class="price-box">
                    <view class="price">
                      <text class="symbol">
                        ￥
                      </text>
                      <text class="big">
                        {{ wxs.parsePrice(item.skuPriceFee)[0] }}
                      </text>
                      <text class="symbol">
                        .{{ wxs.parsePrice(item.skuPriceFee)[1] }}
                      </text>
                    </view>
                    <view class="count">
                      ×{{ item.count }}
                    </view>
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
        <view class="item-tit">
          商品总额
        </view>
        <view class="price black">
          <text class="symbol">
            ￥
          </text>
          <text class="big-num">
            {{ wxs.parsePrice(total)[0] }}
          </text>
          <text class="small-num">
            .{{ wxs.parsePrice(total)[1] }}
          </text>
        </view>
      </view>
    </view>

    <!-- 底部栏 -->
    <view class="submit-footer">
      <view class="total">
        <view class="text">
          合计：
        </view>
        <view class="price">
          <text class="symbol">
            ￥
          </text>
          <text class="big">
            {{ wxs.parsePrice(total)[0] }}
          </text>
          <text class="symbol">
            .{{ wxs.parsePrice(total)[1] }}
          </text>
        </view>
      </view>
      <view
        class="btn"
        @tap="toPay"
      >
        提交订单
      </view>
    </view>
    <!-- 确认弹窗 -->
    <confirm-pop
      :show-pop="showPop"
      :pop-content="popContent"
      :show-cancel="false"
      @handle-pop-confirm="popConfirm"
    />
  </view>
</template>

<script setup>
import { reactive } from 'vue'
const wxs = number()

const Data = reactive({
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
  addrIds: [],
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
  orderType: ''

})
const { showPop, isEditAddress, isShowAddressListPopup, popContent, total, shopCartOrders, addressList, addrId, addrIds, userAddr, consignee, mobile, addr, showPicker, province, city, area } = toRefs(Data)

onLoad((options) => {
  if (options.orderEntry) {
    Data.orderEntry = options.orderEntry
  }
  // 获取用户地址列表
  getAddressList()
})

onShow(() => {
  // 加载订单数据
  loadOrder(Data.isFirst)
})

/**
 * 弹窗确认
 */
const popConfirm = () => {
  Data.showPop = false
}

/**
 * 地区弹窗确认按钮
*/
const addressPickerConfirm = (selectedAddrInfo, isChange) => {
  if (isChange) {
    Data.area = selectedAddrInfo.area
    Data.areaId = selectedAddrInfo.areaId
    Data.city = selectedAddrInfo.city
    Data.cityId = selectedAddrInfo.cityId
    Data.province = selectedAddrInfo.province
    Data.provinceId = selectedAddrInfo.provinceId
    Data.addrIds = [selectedAddrInfo.provinceId, selectedAddrInfo.cityId, selectedAddrInfo.areaId]
  }
  Data.showPicker = false
}

/**
 * 添加新的地址并使用
 */
const saveAndUse = () => {
  // 数据校验
  if (!Data.consignee.trim()) {
    uni.showToast({
      title: '请输入收件人姓名',
      icon: 'none'
    })
    return
  }
  if (Data.consignee.length < 2) {
    uni.showToast({
      title: '收件人姓名需在2到20个字符之间',
      icon: 'none'
    })
    return
  }
  if (!util.checkPhoneNumber(Data.mobile)) {
    uni.showToast({
      title: '请输入正确的手机号',
      icon: 'none'
    })
    return
  }
  if (!Data.area) {
    uni.showToast({
      title: '请选择完整地区信息',
      icon: 'none'
    })
    return
  }
  if (!Data.addr.trim() || Data.addr.length < 5) {
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
      addr: Data.addr,
      area: Data.area,
      areaId: Data.areaId,
      city: Data.city,
      cityId: Data.cityId,
      consignee: Data.consignee,
      mobile: Data.mobile,
      province: Data.province,
      provinceId: Data.provinceId
    }
  }
  http.request(params).then(() => {
    Data.isEditAddress = false
    clearAddressInput()
    getAddressList('reloadOrder')
    if (!Data.userAddr) {
      loadOrder()
    }
  })
}

/**
 * 选择地址
 */
const addrRadioChange = (e) => {
  Data.addrId = e.detail.value
}

/**
 * 确定地址
 */
const confirmChangeAddr = () => {
  Data.isEditAddress = false
  closePopup()
  loadOrder()
}

/**
 * 获取用户地址列表
 */
const getAddressList = (reload) => {
  const params = {
    url: '/mall4cloud_user/user_addr/list',
    method: 'GET',
    data: {}
  }
  http.request(params).then((res) => {
    Data.addressList = res
    if (reload) {
      Data.addrId = res[0].addrId
      loadOrder()
    }
  })
}

/**
 * 退出编辑地址
 */
const exitEditAddress = () => {
  Data.isEditAddress = false
  clearAddressInput()
}

/**
 * 清除编辑地址输入框
 */
const clearAddressInput = () => {
  Data.consignee = ''
  Data.mobile = ''
  Data.addr = ''
  Data.cityId = ''
  Data.provinceId = ''
}

/**
 * 加载订单数据
 */
const loadOrder = (isFirst) => {
  const params = {
    url: '/mall4cloud_order/a/order/confirm',
    method: 'POST',
    data: {
      addrId: Data.addrId,
      dvyType: 3,
      shopCartItem: Data.orderEntry === '1' ? JSON.parse(uni.getStorageSync('cloudShopCartItem')) : undefined
    }
  }
  http.request(params).then(res => {
    const shopCartOrders = res.shopCartOrders
    const remarksList = []
    shopCartOrders.forEach((shopCart, index) => {
      shopCart.shopCartItemVO.forEach(prodItem => {
        if (prodItem.imgUrl.indexOf('http') === -1) {
          prodItem.imgUrl = import.meta.env.VITE_APP_RESOURCES_URL + prodItem.imgUrl
        }
      })
      if (isFirst) {
        shopCart.remarks = ''
      } else {
        remarksList.push(shopCart.remarks)
        shopCartOrders[index].remarks = remarksList[index]
      }
    })
    Data.shopCartOrders = shopCartOrders
    Data.total = res.total
    Data.actualTotal = res.actualTotal
    Data.totalTransfee = res.totalTransfee
    Data.userAddr = res.userAddr
    Data.addrId = res.userAddr.addrId
    Data.addrIds = [res.userAddr.provinceId, res.userAddr.cityId, res.userAddr.areaId]
  })
  Data.isFirst = false
}

// 编辑地址
const editAddress = () => {
  Data.isEditAddress = true
}

// 历史选用地址弹窗
const showAddressListPopup = () => {
  Data.isShowAddressListPopup = true
}

// 关闭弹窗
const closePopup = () => {
  Data.isShowAddressListPopup = false
  Data.isShowDeliveryTimePopup = false
  Data.isShowGeneralCouponPopup = false
}

// 去支付
const toPay = () => {
  submitOrder()
}

/**
 * 提交订单
 */
const submitOrder = () => {
  const shopCartOrders = Data.shopCartOrders
  const orderShopParam = []
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
      orderShopParam
    }
  }
  http.request(params).then((res) => {
    const orderIds = res.join(',')
    uni.redirectTo({
      url: `/pages/payment/payment?orderIds=${orderIds}&dvyType=1`
    })
  })
}
</script>

<style lang="scss" scoped>
@use "submit-order";
@use "../../popup";
</style>
