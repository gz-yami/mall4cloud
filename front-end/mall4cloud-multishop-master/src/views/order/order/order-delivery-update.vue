<template>
  <el-dialog
    v-if="visible"
    :title="this.$i18n.t('order.order.orderShipping')"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="50%"
  >

    <!-- :rules="dataRule" -->
    <el-form
      ref="dataForm"
      :model="dataForm"
      label-width="100px"
      @keyup.enter.native="dataFormSubmit()"
    >
      <div class="detail-cont">
        <div class="detail01">
          <div class="text-width">
            <el-form-item :label="this.$i18n.t('order.order.waitForDelivery')">
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
      <el-form-item :label="this.$i18n.t('order.order.deliveryMethod') + ':'">
        <el-radio-group v-model="dataForm.deliveryType" @change="clear()">
          <el-radio :label="1" disabled>{{
            $t("order.order.selfConOrd")
          }}</el-radio>
          <el-radio :label="3">{{
            $t("order.order.noNeedRequired")
          }}</el-radio>
          <el-radio :label="4" disabled>{{
            $t("order.order.sameCityDelivery")
          }}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span slot="footer">
      <el-button type="primary" @click="visible = false">{{
        $t("table.cancel")
      }}</el-button>
      <el-button type="primary" @click="submitProds()">{{
        $t("table.confirm")
      }}</el-button>
    </span>
  </el-dialog>
</template>

<script>
import * as deliveryCompanyApi from '@/api/delivery/delivery-company'
import * as orderApi from '@/api/order/order'

export default {
  components: {},
  data() {
    return {
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
    }
  },
  mounted() { },
  methods: {
    processingStr(str) {
      // str = str.replace(/\u200B/g,'');
      // return str
    },

    /**
     * 获取数据列表
     */
    init(order) {
      this.isSubmit = false
      this.dataForm.orderAddr = {}
      this.visible = true
      this.orderId = order.orderId
      this.clear()
      // 修改
      orderApi.getOrderItemAndAddress(order.orderId).then(data => {
        this.dataForm.orderAddr = data.orderAddr
        this.dataList = data.orderItems
        this.dataForm.deliveryType = parseInt(data.deliveryType)
        console.log(this.dataList)
        this.dataList.forEach(element => {
          element.changeNum = element.count
          this.selectOrderItems.push({
            changeNum: element.count,
            orderItemId: element.orderItemId,
            pic: element.pic,
            spuName: element.spuName
          })
        })
      })
      // this.getDeliveryList()
    },
    getDeliveryList() {
      deliveryCompanyApi.list().then((data) => {
        this.dataForm.names = data
      })
    },
    clear() {
      this.dataForm.deliveryNo = 0
      this.dataForm.deliveryCompanyId = ''
    },
    /**
     * 确定事件
     */
    submitProds() {
      if (this.isSubmit) {
        return
      }
      this.isSubmit = true
      // let param = this.dataForm
      orderApi.delivery({
        orderId: this.orderId,
        selectOrderItems: this.selectOrderItems,
        deliveryType: this.dataForm.deliveryType
      }).then((data) => {
        this.$message({
          message: this.$i18n.t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => {
            this.visible = false
            setTimeout(() => {
              this.$emit('refreshOrderDeliveryUpdate')
            }, 1000)
          }
        })
      }).catch(({ e }) => {
        this.isSubmit = false
      })
    },
    errorMsg(message) {
      this.$message({
        message: message,
        type: 'error',
        duration: 1500
      })
    }
  }
}
</script>

<style>
.el-input__inner {
  border: 1px solid #dcdfe6 !important;
}
</style>
<style scoped>
.mod-order-orderInfo {
  height: 100%;
  width: 100%;
  font: 14px Arial, "PingFang SC", "Hiragino Sans GB", STHeiti,
    "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;
  color: #495060;
}
.element.style {
  border-top: 1px solid #eeeeee;
  border-right: 1px solid #eeeeee;
  border-bottom: 1px solid #eeeeee;
}
.prods-select-body {
  height: auto;
  overflow: auto;
  border-top: 1px solid #eeeeee;
  border-right: 1px solid #eeeeee;
  border-bottom: 1px solid #eeeeee;
}
.mod-order-order .contprod-:last-child {
  border-bottom: 0;
  border-left: 0;
}
.mod-order-order .prod-cont.prod-bottom,
.mod-order-order .prod-cont {
  border-bottom: 1px solid #dddee1;
  border-left: 1px solid #dddee1;
}
</style>
