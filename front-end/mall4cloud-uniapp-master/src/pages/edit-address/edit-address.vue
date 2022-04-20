<template>
  <view class="edit-address">
    <view>
      <view class="item">
        <view class="text">收件人</view>
        <input v-model="consignee" type="text" maxlength="20" class="input" placeholder="请输入收件人名称">
      </view>
      <view class="item">
        <view class="text">手机号</view>
        <input v-model="mobile" type="number" maxlength="11" class="input" placeholder="请输入联系手机号">
      </view>
      <view class="item">
        <view class="text">所在地区</view>
        <view class="text-arrow">
          <view v-if="province" class="area-text" @tap="showPicker=true">{{ province }} {{ city }} {{ area }}</view>
          <view v-else class="placeholder-text" @tap="showPicker=true">选择地区</view>
          <address-picker v-if="showPicker" :show-picker="showPicker" :addr-ids="addrIds" @setAddressInfo="addressPickerConfirm" />
        </view>
      </view>
      <view class="item">
        <view class="text">详细地址</view>
        <textarea
          v-model="addr"
          class="textarea"
          maxlength="50"
          placeholder-style="color:#999"
          placeholder="详细地址、街道、门牌号等，5-50字"
        />
      </view>
      <view v-if="showSetDefault" class="item">
        <view class="text">设为默认</view>
        <checkbox-group class="set-default">
          <label class="check-label">
            <checkbox class="check" color="#fff" :checked="Boolean(isDefault)" @tap="setDefaultAddr" />
          </label>
        </checkbox-group>
      </view>

      <view class="btn-box">
        <view class="save" @tap="saveAddress">保存地址</view>
        <view v-if="addrId" class="del" @tap="handleShowPop">删除地址</view>
      </view>
    </view>
    <!-- 确认删除弹窗 -->
    <confirm-pop :show-pop="showPop" :pop-content="popContent" @handlePopCancel="popCancel" @handlePopConfirm="popConfirm" />

  </view>
</template>

<script>
const http = require('../../utils/http.js')
const util = require('../../utils/util.js')
import AddressPicker from '../../components/AddressPicker/index'
export default {
  components: {
    AddressPicker
  },
  data() {
    return {
      addrId: 0,
      showPicker: false,
      addrIds: [],
      provinceId: 0,
      cityId: 0,
      areaId: 0,
      province: '',
      city: '',
      area: '',
      consignee: '',
      mobile: '',
      addr: '',
      showSetDefault: false,
      isDefault: 0, // 是否为默认地址
      saveing: false, // 是否正在保存地址
      showPop: false,
      popContent: {
        title: '是否确定删除该地址？',
        content: '删除地址后将无法恢复， 请您慎重考虑'
      }
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (options.addrId) {
      this.addrId = options.addrId
      this.getAddressById()
    }
  },

  methods: {
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
     * 默认地址复选框切换
     */
    setDefaultAddr() {
      const flag = Boolean(!this.isDefault)
      this.isDefault = flag ? 1 : 0
    },
    /**
     * 删除地址
     */
    delAddress() {
      const params = {
        url: '/mall4cloud_user/user_addr?addrId=' + this.addrId,
        method: 'DELETE',
        callBack: res => {
          uni.showToast({
            title: '删除成功',
            icon: 'none',
            mask: true
          })
          this.showPop = false
          setTimeout(() => {
            uni.navigateTo({
              url: '/pages/address-list/address-list'
            })
          }, 1200)
        }
      }
      http.request(params)
    },

    /**
     * 显示弹窗
     */
    handleShowPop(addrId) {
      this.showPop = true
    },

    /**
     * 弹窗取消
     */
    popCancel() {
      this.showPop = false
    },
    /**
     * 弹窗确认
     */
    popConfirm() {
      this.delAddress()
    },

    /**
     * 保存地址
     */
    saveAddress() {
      // 数据校验
      if (!this.consignee.trim()) {
        uni.showToast({
          title: '请输入收件人姓名',
          icon: 'none',
          mask: true
        })
        return
      }
      if (this.consignee.length < 2) {
        uni.showToast({
          title: '收件人姓名需在2到20个字符之间',
          icon: 'none',
          mask: true
        })
        return
      }
      if (!util.checkPhoneNumber(this.mobile)) {
        uni.showToast({
          title: '请输入正确的手机号',
          icon: 'none',
          mask: true
        })
        return
      }
      if (!this.provinceId || !this.cityId || !this.areaId) {
        uni.showToast({
          title: '请选择完整地区信息',
          icon: 'none',
          mask: true
        })
        return
      }
      if (!this.addr.trim() || this.addr.length < 5) {
        uni.showToast({
          title: '请输入字数在5-50之间的详细地址',
          icon: 'none',
          mask: true
        })
        return
      }

      if (this.saveing) {
        return
      }
      this.saveing = true
      // 保存地址
      const params = {
        url: '/mall4cloud_user/user_addr',
        method: this.addrId ? 'PUT' : 'POST',
        data: {
          addr: this.addr,
          addrId: this.addrId,
          area: this.area,
          areaId: this.areaId,
          city: this.city,
          cityId: this.cityId,
          consignee: this.consignee,
          isDefault: this.isDefault,
          mobile: this.mobile,
          province: this.province,
          provinceId: this.provinceId
        },
        callBack: res => {
          uni.showToast({
            title: '保存成功',
            icon: 'none',
            mask: true
          })
          setTimeout(() => {
            uni.navigateTo({
              url: `/pages/address-list/address-list`
            })
            this.saveing = false
          }, 1200)
        }
      }
      http.request(params)
    },

    /**
     * 根据地址id获取地址信息
     */
    getAddressById() {
      const params = {
        url: '/mall4cloud_user/user_addr',
        method: 'GET',
        data: {
          addrId: this.addrId
        },
        callBack: res => {
          this.addrIds = [res.provinceId, res.cityId, res.areaId]
          this.province = res.province
          this.city = res.city
          this.area = res.area
          this.provinceId = res.provinceId
          this.cityId = res.cityId
          this.areaId = res.areaId
          this.consignee = res.consignee
          this.mobile = res.mobile
          this.addr = res.addr
          this.isDefault = res.isDefault
          this.showSetDefault = !res.isDefault
        }
      }
      http.request(params)
    }
  }
}
</script>

<style>
@import "./edit-address.css";
@import "./../../popup.css";
</style>
