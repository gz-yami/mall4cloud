<template>
  <view class="page-edit-address">
    <view>
      <view class="item">
        <view class="text">
          收件人
        </view>
        <input
          v-model="consignee"
          type="text"
          maxlength="20"
          class="input"
          placeholder="请输入收件人名称"
        >
      </view>
      <view class="item">
        <view class="text">
          手机号
        </view>
        <input
          v-model="mobile"
          type="number"
          maxlength="11"
          class="input"
          placeholder="请输入联系手机号"
        >
      </view>
      <view class="item">
        <view class="text">
          所在地区
        </view>
        <view class="text-arrow">
          <view
            v-if="province"
            class="area-text"
            @tap="showPicker=true"
          >
            {{ province }} {{ city }} {{ area }}
          </view>
          <view
            v-else
            class="placeholder-text"
            @tap="showPicker=true"
          >
            选择地区
          </view>
          <address-picker
            v-if="showPicker"
            :show-picker="showPicker"
            :addr-ids="addrIds"
            @set-address-info="addressPickerConfirm"
          />
        </view>
      </view>
      <view class="item">
        <view class="text">
          详细地址
        </view>
        <textarea
          v-model="addr"
          class="textarea"
          maxlength="50"
          placeholder-style="color:#999"
          placeholder="详细地址、街道、门牌号等，5-50字"
        />
      </view>
      <view
        v-if="showSetDefault"
        class="item"
      >
        <view class="text">
          设为默认
        </view>
        <checkbox-group class="set-default">
          <label class="check-label">
            <checkbox
              class="check"
              color="#fff"
              :checked="Boolean(isDefault)"
              @tap="setDefaultAddr"
            />
          </label>
        </checkbox-group>
      </view>

      <view class="btn-box">
        <view
          class="save"
          @tap="saveAddress"
        >
          保存地址
        </view>
        <view
          v-if="addrId"
          class="del"
          @tap="handleShowPop"
        >
          删除地址
        </view>
      </view>
    </view>
    <!-- 确认删除弹窗 -->
    <confirm-pop
      :show-pop="showPop"
      :pop-content="popContent"
      @handle-pop-cancel="popCancel"
      @handle-pop-confirm="popConfirm"
    />
  </view>
</template>

<script setup>
import { reactive } from 'vue'

const Data = reactive({
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
})
const { addrId, showPicker, addrIds, province, city, area, consignee, mobile, addr, showSetDefault, isDefault, showPop, popContent } = toRefs(Data)

/**
   * 生命周期函数--监听页面加载
   */
onLoad((options) => {
  if (options.addrId) {
    Data.addrId = options.addrId
    getAddressById()
  }
})

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
     * 默认地址复选框切换
     */
const setDefaultAddr = () => {
  const flag = Boolean(!Data.isDefault)
  Data.isDefault = flag ? 1 : 0
}

/**
     * 删除地址
     */
const delAddress = () => {
  const params = {
    url: '/mall4cloud_user/user_addr?addrId=' + Data.addrId,
    method: 'DELETE'
  }
  http.request(params).then(() => {
    uni.showToast({
      title: '删除成功',
      icon: 'none',
      mask: true
    })
    Data.showPop = false
    setTimeout(() => {
      uni.navigateTo({
        url: '/pages/address-list/address-list'
      })
    }, 1200)
  })
}

/**
     * 显示弹窗
     */
const handleShowPop = () => {
  Data.showPop = true
}

/**
     * 弹窗取消
     */
const popCancel = () => {
  Data.showPop = false
}

/**
     * 弹窗确认
     */
const popConfirm = () => {
  delAddress()
}

/**
     * 保存地址
     */
const saveAddress = () => {
  // 数据校验
  if (!Data.consignee.trim()) {
    uni.showToast({
      title: '请输入收件人姓名',
      icon: 'none',
      mask: true
    })
    return
  }
  if (Data.consignee.length < 2) {
    uni.showToast({
      title: '收件人姓名需在2到20个字符之间',
      icon: 'none',
      mask: true
    })
    return
  }
  if (!util.checkPhoneNumber(Data.mobile)) {
    uni.showToast({
      title: '请输入正确的手机号',
      icon: 'none',
      mask: true
    })
    return
  }
  if (!Data.provinceId || !Data.cityId || !Data.areaId) {
    uni.showToast({
      title: '请选择完整地区信息',
      icon: 'none',
      mask: true
    })
    return
  }
  if (!Data.addr.trim() || Data.addr.length < 5) {
    uni.showToast({
      title: '请输入字数在5-50之间的详细地址',
      icon: 'none',
      mask: true
    })
    return
  }

  if (Data.saveing) {
    return
  }
  Data.saveing = true
  // 保存地址
  const params = {
    url: '/mall4cloud_user/user_addr',
    method: Data.addrId ? 'PUT' : 'POST',
    data: {
      addr: Data.addr,
      addrId: Data.addrId,
      area: Data.area,
      areaId: Data.areaId,
      city: Data.city,
      cityId: Data.cityId,
      consignee: Data.consignee,
      isDefault: Data.isDefault,
      mobile: Data.mobile,
      province: Data.province,
      provinceId: Data.provinceId
    }
  }
  http.request(params).then(() => {
    uni.showToast({
      title: '保存成功',
      icon: 'none',
      mask: true
    })
    setTimeout(() => {
      uni.navigateTo({
        url: '/pages/address-list/address-list'
      })
      Data.saveing = false
    }, 1200)
  })
}

/**
     * 根据地址id获取地址信息
     */
const getAddressById = () => {
  const params = {
    url: '/mall4cloud_user/user_addr',
    method: 'GET',
    data: {
      addrId: Data.addrId
    }
  }
  http.request(params).then((res) => {
    Data.addrIds = [res.provinceId, res.cityId, res.areaId]
    Data.province = res.province
    Data.city = res.city
    Data.area = res.area
    Data.provinceId = res.provinceId
    Data.cityId = res.cityId
    Data.areaId = res.areaId
    Data.consignee = res.consignee
    Data.mobile = res.mobile
    Data.addr = res.addr
    Data.isDefault = res.isDefault
    Data.showSetDefault = !res.isDefault
  })
}

</script>

<style lang="scss" scoped>
@use 'edit-address';
@use '../../popup';

</style>
