<template>
  <view
    class="animation-element-wrapper"
    :class="{show: showPicker}"
    @tap.stop="hiddenFloatView(0)"
  >
    <view
      class="animation-element"
      :class="{show: showPicker}"
      @tap.stop="nono"
    >
      <view
        class="right-bt"
        @tap.stop="hiddenFloatView"
      >
        确定
      </view>
      <view class="line" />
      <picker-view
        :class="{show: showPicker}"
        indicator-style="height: 60rpx;"
        :value="value"
        @change="bindChange"
        @tap.stop="nono"
      >
        <!--省-->
        <picker-view-column>
          <view
            v-for="(item, pIndex) in provArray"
            :key="pIndex"
          >
            {{ item.areaName }}
          </view>
        </picker-view-column>
        <!--地级市-->
        <picker-view-column>
          <view
            v-for="(item, cIndex) in cityArray"
            :key="cIndex"
          >
            {{ item.areaName }}
          </view>
        </picker-view-column>
        <!--区县-->
        <picker-view-column>
          <view
            v-for="(item, aIndex) in areaArray"
            :key="aIndex"
          >
            {{ item.areaName }}
          </view>
        </picker-view-column>
      </picker-view>
    </view>
  </view>
</template>

<script setup>
import { onMounted, reactive } from 'vue'

const emit = defineEmits(['setAddressInfo'])

const props = defineProps({
  addrIds: {
    type: Array,
    default: () => [0, 0, 0]
  },
  showPicker: {
    type: Boolean,
    default: false
  }
})

const Data = reactive({
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
})
const { value, provArray, cityArray, areaArray } = toRefs(Data)

onMounted(() => {
  initCityData(props.addrIds[0], props.addrIds[1], props.addrIds[2])
})

/**
 * 初始化地址数据
 */
const initCityData = (provinceId, cityId, areaId) => {
  uni.showLoading()
  const params = {
    url: '/mall4cloud_user/area/list',
    method: 'GET',
    data: {}
  }
  if (!uni.getStorageSync('cloudProvArray')) {
    http.request(params).then((res) => {
      Data.provArray = res
      uni.setStorageSync('cloudProvArray', res)
      initAll(res, provinceId, cityId, areaId)
      uni.hideLoading()
    })
  } else {
    Data.provArray = uni.getStorageSync('cloudProvArray')
    initAll(uni.getStorageSync('cloudProvArray'), provinceId, cityId, areaId)
  }
}

/**
 * 处理省市区地址列表
 */
const initAll = (allData, provinceId, cityId, areaId) => {
  if (provinceId) {
    for (const provinceIndex in allData) {
      if (allData[provinceIndex].areaId === provinceId) {
        Data.value = [Number(provinceIndex), Data.value[1], Data.value[2]]
        Data.cityArray = allData[provinceIndex].areas
      }
    }
    for (const cityIndex in allData[Data.value[0]].areas) {
      if (allData[Data.value[0]].areas[cityIndex].areaId === cityId) {
        Data.value = [Data.value[0], Number(cityIndex), Data.value[2]]
        Data.areaArray = allData[Data.value[0]].areas[cityIndex].areas
      }
    }
    for (const areaIndex in allData[Data.value[0]].areas[Data.value[1]].areas) {
      if (allData[Data.value[0]].areas[Data.value[1]].areas[areaIndex].areaId === areaId) {
        Data.value = [Data.value[0], Data.value[1], Number(areaIndex)]
      }
    }
    Data.index = Data.value
  } else {
    Data.cityArray = allData[0].areas
    Data.areaArray = allData[0].areas[0].areas
  }
  uni.hideLoading()
}

// 滑动事件
const bindChange = (e) => {
  const val = e.detail.value // 判断滑动的是第几个column
  // 若省份column做了滑动则定位到地级市和区县第一位
  if (Data.index[0] != val[0]) {
    val[1] = 0
    val[2] = 0 // 更新数据
    Data.cityArray = Data.provArray[val[0]].areas
    Data.areaArray = Data.provArray[val[0]].areas[0].areas
  } else if (Data.index[1] != val[1]) {
    // 若省份column未做滑动，地级市做了滑动则定位区县第一位
    if (Data.index[1] != val[1]) {
      val[2] = 0
      Data.areaArray = Data.provArray[val[0]].areas[val[1]].areas
    }
  }
  Data.index = val
  Data.value = [val[0], val[1], val[2]]
  Data.province = Data.provArray[Data.value[0]].areaName
  Data.city = Data.cityArray[Data.value[1]].areaName
  Data.area = Data.areaArray[Data.value[2]].areaName
  Data.provinceId = Data.provArray[Data.value[0]].areaId
  Data.cityId = Data.cityArray[Data.value[1]].areaId
  Data.areaId = Data.areaArray[Data.value[2]].areaId
}

// 隐藏弹窗浮层
const hiddenFloatView = (isChange) => {
// 选中的省市区名称及id
  const selectedAddrInfo = {
    area: Data.area || Data.provArray[Data.index[0]].areas[Data.index[1]].areas[Data.index[2]].areaName,
    areaId: Data.areaId || Data.provArray[Data.index[0]].areas[Data.index[1]].areas[Data.index[2]].areaId,
    city: Data.city || Data.provArray[Data.index[0]].areas[Data.index[1]].areaName,
    cityId: Data.cityId || Data.provArray[Data.index[0]].areas[Data.index[1]].areaId,
    province: Data.province || Data.provArray[Data.index[0]].areaName,
    provinceId: Data.provinceId || Data.provArray[Data.index[0]].areaId
  }
  emit('setAddressInfo', selectedAddrInfo, isChange)
}

const nono = () => {

}
</script>

<style lang="scss" scoped>

.animation-element-wrapper {
  display: flex;
  position: fixed;
  left: 0;
  bottom: 0;
  height: 0;
  width: 100%;
  border-color: transparent;
  z-index: -1;
  transition: all .3s;
}
.animation-element-wrapper.show {
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 2;
}

.animation-element {
  display: flex;
  position: fixed;
  width: 100%;
  height: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 1);
  transition: all .3s;
}
.animation-element.show {
  height: 530rpx;
}

/* 地区picker弹窗 */
picker-view {
  background-color: white;
  padding: 0;
  width: 100%;
  height: 0;
  bottom: 0;
  position: fixed;
  transition: all .3s;
}
picker-view.show {
  height: 440rpx;
}

picker-view-column view {
  vertical-align: middle;
  font-size: 30rpx;
  line-height: 30rpx;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.right-bt {
  right: 40rpx;
  top: 28rpx;
  position: absolute;
}

.line {
  display: block;
  position: fixed;
  height: 2rpx;
  width: 100%;
  margin-top: 89rpx;
  background-color: #eee;
}

</style>
