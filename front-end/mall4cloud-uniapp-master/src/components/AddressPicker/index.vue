<template>
  <view class="animation-element-wrapper" :class="{show: showPicker}" @tap.stop="hiddenFloatView(0)">
    <view class="animation-element" :class="{show: showPicker}" @tap.stop="nono">
      <view class="right-bt" @tap.stop="hiddenFloatView">确定</view>
      <view class="line" />
      <picker-view :class="{show: showPicker}" indicator-style="height: 60rpx;" :value="value" @change="bindChange" @tap.stop="nono">
        <!--省-->
        <picker-view-column>
          <view v-for="(item, pIndex) in provArray" :key="pIndex">{{ item.areaName }}</view>
        </picker-view-column>
        <!--地级市-->
        <picker-view-column>
          <view v-for="(item, cIndex) in cityArray" :key="cIndex">{{ item.areaName }}</view>
        </picker-view-column>
        <!--区县-->
        <picker-view-column>
          <view v-for="(item, aIndex) in areaArray" :key="aIndex">{{ item.areaName }}</view>
        </picker-view-column>
      </picker-view>
    </view>
  </view>
</template>

<script>
import http from '../../utils/http'

export default {
  props: {
    addrIds:{
      type: Array,
      default: [0, 0, 0]
    },
    showPicker: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      value: [0, 0, 0],
      provinceId: 0,
      cityId: 0,
      areaId: 0,
      province: '',
      city: '',
      area: '',
      provArray: [],
      cityArray: [],
      areaArray: [],
      index: [0, 0, 0]
    }
  },
  mounted() {
    this.initCityData(this.addrIds[0],this.addrIds[1],this.addrIds[2])
  },
  methods: {
    /**
     * 初始化地址数据
     */
    initCityData(provinceId, cityId, areaId) {
      uni.showLoading()
      const params = {
        url: '/mall4cloud_user/area/list',
        method: 'GET',
        data: {},
        callBack: res => {
          this.provArray = res
          uni.setStorageSync('provArray', res)
          this.initAll(res, provinceId, cityId, areaId)
          uni.hideLoading()
        }
      }
      if (!uni.getStorageSync('provArray')) {
        http.request(params)
      } else {
        this.provArray = uni.getStorageSync('provArray')
        this.initAll(uni.getStorageSync('provArray'), provinceId, cityId, areaId)
      }

    },
    /**
     * 处理省市区地址列表
     */
    initAll(allData, provinceId, cityId, areaId) {
      if (provinceId) {
        for (var provinceIndex in allData) {
          if (allData[provinceIndex].areaId === provinceId) {
            this.value = [Number(provinceIndex), this.value[1], this.value[2]]
            this.cityArray = allData[provinceIndex].areas
          }
        }
        for(let cityIndex in allData[this.value[0]].areas) {
          if (allData[this.value[0]].areas[cityIndex].areaId === cityId) {
            this.value = [ this.value[0], Number(cityIndex), this.value[2]]
            this.areaArray = allData[this.value[0]].areas[cityIndex].areas
          }
        }
        for (let areaIndex in allData[this.value[0]].areas[this.value[1]].areas) {
          if (allData[this.value[0]].areas[this.value[1]].areas[areaIndex].areaId === areaId ) {
            this.value = [ this.value[0], this.value[1], Number(areaIndex)]
          }
        }
        this.index = this.value
      } else {
        this.cityArray = allData[0].areas
        this.areaArray = allData[0].areas[0].areas
      }
      uni.hideLoading()
    },

    // 滑动事件
    bindChange(e) {
      const val = e.detail.value // 判断滑动的是第几个column
      // 若省份column做了滑动则定位到地级市和区县第一位
      if (this.index[0] != val[0]) {
        val[1] = 0
        val[2] = 0 // 更新数据
        this.cityArray = this.provArray[val[0]].areas
        this.areaArray = this.provArray[val[0]].areas[0].areas
      } else if (this.index[1] != val[1]) {
        // 若省份column未做滑动，地级市做了滑动则定位区县第一位
        if (this.index[1] != val[1]) {
          val[2] = 0
          this.areaArray = this.provArray[val[0]].areas[val[1]].areas
        }
      }
      this.index = val
      this.value = [val[0], val[1], val[2]]
      this.province = this.provArray[this.value[0]].areaName
      this.city = this.cityArray[this.value[1]].areaName
      this.area = this.areaArray[this.value[2]].areaName
      this.provinceId = this.provArray[this.value[0]].areaId
      this.cityId = this.cityArray[this.value[1]].areaId
      this.areaId = this.areaArray[this.value[2]].areaId
    },

    // 隐藏弹窗浮层
    hiddenFloatView(isChange) {
      // 选中的省市区名称及id
      const selectedAddrInfo = {
        area: this.area || this.provArray[this.index[0]].areas[this.index[1]].areas[this.index[2]].areaName,
        areaId: this.areaId || this.provArray[this.index[0]].areas[this.index[1]].areas[this.index[2]].areaId,
        city: this.city || this.provArray[this.index[0]].areas[this.index[1]].areaName,
        cityId: this.cityId || this.provArray[this.index[0]].areas[this.index[1]].areaId,
        province: this.province || this.provArray[this.index[0]].areaName,
        provinceId: this.provinceId || this.provArray[this.index[0]].areaId
      }
      this.$emit('setAddressInfo', selectedAddrInfo, isChange)
    },

    /**
     * 空方法
     */
    nono() {

    },
  }
}
</script>

<style scoped>
@import url('./index.css');
</style>
