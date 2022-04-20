<template>
  <view class="address-list">
    <view class="list-box">
      <block v-for="(addressItem, addressIndex) in addressList" :key="addressIndex">
        <view :class="['item', addressItem.isDefault===1?'default-address':'']">
          <view class="text-box">
            <view class="address">
              <view v-if="addressItem.isDefault===1" class="default-flag">默认</view>
              {{ addressItem.province+addressItem.city+addressItem.area+addressItem.addr }}
            </view>
            <view class="name-phone">
              <view class="name">{{ addressItem.consignee }}</view>
              <view class="phone">{{ addressItem.mobile }}</view>
            </view>
          </view>
          <view class="edit-icon" @tap.stop="toEditAddress(addressItem.addrId)">
            <image src="/static/images/edit.png" />
          </view>
        </view>
      </block>
      <view v-if="!addressList" class="empty-tips">
        暂无数据
      </view>
    </view>
    <view class="new-address">
      <view class="btn" @tap="addAddress">新建收货地址</view>
    </view>
  </view>
</template>

<script>
const http = require('../../utils/http.js')

export default {
  data() {
    return {
      addressList: []
    }
  },
  onShow() {
    this.getAddressList()
  },
  methods: {
    /**
     * 获取用户地址列表
     */
    getAddressList() {
      const params = {
        url: '/mall4cloud_user/user_addr/list',
        method: 'GET',
        data: {},
        callBack: res => {
          this.addressList = res
        }
      }
      http.request(params)
    },

    // 去编辑地址
    toEditAddress(addrId) {
      uni.navigateTo({
        url: '/pages/edit-address/edit-address?addrId=' + addrId
      })
    },

    /**
     * 新建地址
     */
    addAddress() {
      if (this.addressList.length === 10) {
        uni.showToast({
          title: this.i18n.addNewAddressTips,
          icon: 'none'
        })
      } else {
        uni.navigateTo({
          url: '/pages/edit-address/edit-address'
        })
      }
    }
  }
}
</script>

<style>
@import "./address-list.css";
</style>
