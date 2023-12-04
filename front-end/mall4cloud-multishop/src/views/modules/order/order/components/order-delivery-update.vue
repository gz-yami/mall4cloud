<template>
  <el-dialog
    v-if="visible"
    v-model="visible"
    class="component-order-delivery-update"
    :title="$t('order.order.orderShipping')"
    :close-on-click-modal="false"
    width="50%"
  >
    <!-- :rules="dataRule" -->
    <el-form
      ref="dataFormRef"
      :model="dataForm"
      label-width="100px"
      @keyup.enter="dataFormSubmit()"
    >
      <div class="detail-cont">
        <div class="detail01">
          <div class="text-width">
            <el-form-item :label="$t('order.order.waitForDelivery')">
              <span>{{ $t("order.order.delType") }}:{{
                $t("order.order.expressDelivery")
              }}</span>
            </el-form-item>
            <el-form-item label>
              <span>{{ $t("constant.consignee") }}：{{
                dataForm.orderAddr.consignee
              }}</span>
            </el-form-item>
            <el-form-item label>
              <span>{{ $t("constant.mobilePhone") }}：{{
                dataForm.orderAddr.mobile
              }}</span>
            </el-form-item>
            <el-form-item label>
              <span>{{ $t("constant.deliveryAddr") }}：{{
                dataForm.orderAddr.province
              }}{{ dataForm.orderAddr.city
              }}{{ dataForm.orderAddr.area
              }}{{ dataForm.orderAddr.addr }}</span>
            </el-form-item>
          </div>
        </div>
      </div>
      <el-form-item :label="$t('order.order.deliveryMethod') + ':'">
        <el-radio-group
          v-model="dataForm.deliveryType"
          @change="clear()"
        >
          <el-radio
            :label="1"
            disabled
          >
            {{
              $t("order.order.selfConOrd")
            }}
          </el-radio>
          <el-radio :label="3">
            {{
              $t("order.order.noNeedRequired")
            }}
          </el-radio>
          <el-radio
            :label="4"
            disabled
          >
            {{
              $t("order.order.sameCityDelivery")
            }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <span>
        <el-button
          type="primary"
          @click="visible = false"
        >{{
          $t("table.cancel")
        }}</el-button>
        <el-button
          type="primary"
          @click="submitProds()"
        >{{
          $t("table.confirm")
        }}</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import * as orderApi from '@/api/order/order'
import { reactive } from 'vue'

const emit = defineEmits(['refreshOrderDeliveryUpdate'])

const Data = reactive({
  visible: false,
  dataForm: {
    deliveryCompanyId: '',
    orderAddr: {},
    deliveryNo: 0,
    names: [],
    orderId: 0,
    deliveryType: 1
  },
  dataList: [],
  selectOrderItems: [],
  isSubmit: false,
  orderItems: [],
  orderId: 0,
  order: null
})
const { visible, dataForm } = toRefs(Data)

/**
* 获取数据列表
*/
const init = (order) => {
  Data.isSubmit = false
  Data.dataForm.orderAddr = {}
  Data.visible = true
  Data.orderId = order.orderId
  clear()
  // 修改
  orderApi.getOrderItemAndAddress(order.orderId).then(data => {
    Data.dataForm.orderAddr = data.orderAddr
    Data.dataList = data.orderItems
    Data.dataForm.deliveryType = parseInt(data.deliveryType)
    Data.dataList.forEach(element => {
      element.changeNum = element.count
      Data.selectOrderItems.push({
        changeNum: element.count,
        orderItemId: element.orderItemId,
        pic: element.pic,
        spuName: element.spuName
      })
    })
  })
}

const clear = () => {
  Data.dataForm.deliveryNo = 0
  Data.dataForm.deliveryCompanyId = ''
}

/**
* 确定事件
*/
const submitProds = () => {
  if (Data.isSubmit) {
    return
  }
  Data.isSubmit = true
  // let param = Data.dataForm
  orderApi.delivery({
    orderId: Data.orderId,
    selectOrderItems: Data.selectOrderItems,
    deliveryType: Data.dataForm.deliveryType
  }).then(() => {
    ElMessage({
      message: $t('table.actionSuccess'),
      type: 'success',
      duration: 1500,
      onClose: () => {
        Data.visible = false
        setTimeout(() => {
          emit('refreshOrderDeliveryUpdate')
        }, 1000)
      }
    })
  }).catch(() => {
    Data.isSubmit = false
  })
}

defineExpose({
  init
})

</script>

<style lang="scss" scoped></style>
