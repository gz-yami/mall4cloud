<template>
  <div class="detail-dialog">
    <el-dialog
      :title="
        !dataForm.orderId
          ? this.$i18n.t('table.add')
          : this.$i18n.t('order.order.orderDetail')
      "
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="80%"
    >
      <el-form
        ref="dataForm"
        :model="dataForm"
        label-width="80px"
        @keyup.enter.native="dataFormSubmit()"
      >
        <div class="mod-order-orderInfo">
          <div class="content">
            <div class="order-number">
              <div class="number">
                <span class="text">{{ $t("order.order.number") }}:</span>
                {{ dataForm.orderId }}
              </div>
              <div class="time">
                <span class="text">{{ $t("order.order.createTime") }}:</span>
                {{ dataForm.createTime }}
              </div>
              <div class="type">
                {{
                  [
                    $t("order.order.normalOrder"),
                    "",
                    "",
                  ][dataForm.orderType]
                }}
              </div>
            </div>
            <div class="order-state">
              <div class="state-box">
                <div class="state">
                  {{
                    [
                      $t("order.order.waitingFosPayment"),
                      $t("order.order.waitiooShip"),
                      $t("order.order.waitingFeGoods"),
                      $t("order.order.waitingtion"),
                      $t("order.order.commoditful"),
                      $t("order.order.commodityFailed"),
                      $t("order.order.commodited"),
                    ][dataForm.status - 1]
                  }}
                </div>
                <div class="state-des">
                  <div v-if="dataForm.status === 1">
                    {{ $t("order.order.buyerDidNTime") }}
                  </div>
                  <div v-if="dataForm.status === 2">
                    {{ $t("order.order.buyPleF") }}
                  </div>
                  <div v-if="dataForm.status === 3 && dataForm.deliveryType !== 2">
                    {{ $t("order.order.shelF") }}
                  </div>
                  <div v-if="dataForm.status === 3 && dataForm.deliveryType === 2">
                    {{ $t("order.order.buyA") }}
                  </div>
                  <!-- <div v-if="dataForm.status === 4">订单已完成，等待买家发表评价。</div> -->
                  <div v-if="dataForm.status === 5">{{ $t("order.order.buyB") }}</div>
                  <div v-if="dataForm.status === 6">
                    {{ $t("order.order.orderCanc") }}
                  </div>
                  <div v-if="dataForm.status === 7">
                    {{ $t("order.order.outTimeCanOrd") }}
                  </div>
                </div>
                <!-- <div class="actions">
                  <div class="item" v-if="dataForm.status === 1">
                    <div class="el-button--text">{{$t("table.edit")}}价格</div>
                  </div>
                  <div class="item" v-if="dataForm.status === 2">
                    <div class="el-button--text">立即发货</div>
                    <div
                      class="item-btn"
                      @click="changeUserAddrOrder(dataForm.orderAddr)"
                    >{{$t("table.edit")}}收货地址</div>
                  </div>
                 <div class="item" v-if="dataForm.status === 3">
                    <div class="el-button--text">延长收货</div>
                  </div>
                </div>-->
              </div>
              <div class="state-steps">
                <el-form-item>
                  <el-steps
                    :active="stepsStatus"
                    align-center
                    :process-status="dataForm.status == 6 ? 'error' : 'wait'"
                  >
                    <el-step
                      :title="this.$i18n.t('order.order.submitOrders')"
                      :description="dataForm.createTime"
                    />
                    <el-step
                      :title="this.$i18n.t('order.order.theBuyerHasPaid')"
                      :description="dataForm.payTime"
                    />
                    <el-step
                      :title="this.$i18n.t('order.order.buyerHasReceived')"
                      :description="dataForm.finallyTime"
                    />
                  </el-steps>
                </el-form-item>
              </div>
            </div>
            <div
              v-if="
                dataForm.deliveryExpresses && dataForm.deliveryExpresses.length
              "
              class="packages"
            >
              <div v-if="dataForm.deliveryExpresses.length > 1" class="p-tab">
                <div
                  v-for="(deliveryExpresse,index) in dataForm.deliveryExpresses"
                  :key="index"
                  :class="indexs === index ? 'item active' : 'item'"
                  @click="onClickListDelivery(deliveryExpresse, index)"
                >
                  {{ $t("order.order.package") }}{{ index + 1 }}
                </div>
              </div>
              <div v-if="deliveryExpresse" class="p-con">
                <div class="deliver-msg">
                  <div class="d-item">
                    <div class="text">{{ $t("order.order.deliveryMethod") }}：</div>
                    <div class="res">
                      {{
                        [
                          this.$i18n.t("order.order.expressDelivery"),
                          "",
                          this.$i18n.t("order.order.noNeedRequired"),
                        ][dataForm.deliveryType - 1]
                      }}
                    </div>
                  </div>
                  <div
                    class="d-goods"
                    :class="{ over: deliveryExpresse.orderItems.length > 5 }"
                  >
                    <div
                      class="arrow prev"
                      :class="{
                        disable:
                          deliveryExpresse.orderItems.length - 5 <= 0 ||
                          offsetCount < 1,
                      }"
                      @click="prevItem"
                    />
                    <div
                      class="arrow next"
                      :class="{
                        disable:
                          deliveryExpresse.orderItems.length - 5 <= 0 ||
                          offsetCount >= deliveryExpresse.orderItems.length - 5,
                      }"
                      @click="nextItem"
                    />
                    <div class="item-goods">
                      <div ref="carouser" class="goods-box">
                        <div
                          v-for="(
                            orderItem, index
                          ) in deliveryExpresse.orderItems"
                          :key="index"
                          class="item"
                        >
                          <div class="img">
                            <img :src="orderItem.pic" alt>
                            <div class="number">×{{ orderItem.count }}</div>
                          </div>
                          <div class="name">{{ orderItem.spuName }}</div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="order-info">
              <div v-if="dataForm.orderAddr" class="info-item">
                <div class="item-tit">
                  {{ $t("order.order.recipientInformation") }}
                </div>
                <div class="item">
                  <div class="text">
                    {{
                      dataForm.deliveryType === 2
                        ? $t("order.order.deliveryPerson")
                        : $t("constant.consignee")
                    }}：
                  </div>
                  <div class="res">{{ dataForm.orderAddr.consignee }}</div>
                </div>
                <div class="item">
                  <div class="text">{{ $t("constant.contactTel") }}：</div>
                  <div class="res">{{ dataForm.orderAddr.mobile }}</div>
                </div>
                <div v-if="dataForm.deliveryType !== 2" class="item">
                  <div class="text">{{ $t("constant.deliveryAddr") }}：</div>
                  <div class="res">
                    {{ dataForm.orderAddr.province
                    }}{{ dataForm.orderAddr.city
                    }}{{ dataForm.orderAddr.area
                    }}{{ dataForm.orderAddr.addr }}
                  </div>
                </div>
              </div>
              <div class="info-item">
                <div class="item-tit">
                  {{ $t("order.order.shippingInformation") }}
                </div>
                <div class="item">
                  <div class="text">{{ $t("order.order.delType") }}：</div>
                  <div class="res">
                    {{
                      [
                        $t("order.order.ordinaryExpress"),
                        $t("order.order.buyerMention"),
                        $t("order.order.noNeedRequired")
                      ][dataForm.deliveryType - 1]
                    }}
                  </div>
                  <div v-if="dataForm.deliveryType === null" class="res">
                    {{ $t("order.order.no") }}
                  </div>
                </div>
              </div>
              <div class="info-item">
                <div class="item-tit">{{ $t("order.order.paymentInformation") }}</div>
                <div class="item">
                  <div class="text">{{ $t("order.order.paymentTime") }}：</div>
                  <div class="res">{{ dataForm.payTime }}</div>
                  <div v-if="dataForm.payTime === null" class="res">
                    {{ $t("order.order.no") }}
                  </div>
                </div>
              </div>
            </div>
            <div class="goods-list">
              <el-table :data="dataForm.orderItems" border>
                <el-table-column prop="" :label="this.$i18n.t('constant.product')">
                  <template slot-scope="scope">
                    <div class="df">
                      <img
                        :src="scope.row.pic"
                        width="60"
                        height="60"
                      >
                      <span class="name">
                        {{ scope.row.spuName }}
                      </span>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="price"
                  :label="this.$i18n.t('order.order.unitPrice')"
                  width="180"
                  align="center"
                >
                  <template slot-scope="scope">
                    <span>{{ (scope.row.price / 100).toFixed(2) }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="count"
                  :label="this.$i18n.t('order.order.quantity')"
                  width="180"
                  align="center"
                >
                  <template slot-scope="scope">
                    <span>{{ scope.row.count }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="totalPrice"
                  :label="this.$i18n.t('order.order.totalPrice')"
                  width="180"
                  align="center"
                >
                  <template slot-scope="scope">
                    <span>{{ (scope.row.spuTotalAmount / 100).toFixed(2) }}</span>
                  </template>
                </el-table-column>
              </el-table>
              <div class="goods-total">
                <div class="text-box">
                  <div class="item">
                    <div class="text">{{ $t("order.order.prodTotalPrice") }}:</div>
                    <div class="number">￥{{ (dataForm.total / 100).toFixed(2) }}</div>
                  </div>
                  <div class="item act-price">
                    <div class="text">{{ $t("order.order.amountDue") }}:</div>
                    <div class="number">￥{{ (dataForm.total / 100).toFixed(2) }}</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="order-log">
              <div class="log-title">{{ $t("order.order.logs") }}</div>
              <div v-if="dataForm.createTime" class="log-cont">
                {{ dataForm.createTime }} {{ dataForm.nickName }}
                {{ $t("order.order.createOrder") }}
              </div>
              <div v-if="dataForm.updateTime" class="log-cont">
                {{ dataForm.updateTime }} {{ dataForm.nickName }}
                {{ $t("order.order.orderUpdate") }}
              </div>
              <div v-if="dataForm.payTime" class="log-cont">
                {{ dataForm.payTime }} {{ dataForm.nickName }}
                {{ $t("order.order.payment") }}
              </div>
              <div v-if="dataForm.finallyTime" class="log-cont">
                {{ dataForm.finallyTime }} {{ dataForm.nickName
                }}{{ $t("order.order.completed") }}
              </div>
              <div v-if="dataForm.cancelTime" class="log-cont">
                {{ dataForm.cancelTime }} {{ dataForm.nickName }}
                {{ $t("order.order.cancelOrder") }}
              </div>
            </div>
          </div>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import * as api from '@/api/order/order'

export default {
  data() {
    return {
      visible: false,
      dataForm: {
        orderId: '',
        remarks: '',
        shopRemarks: '',
        total: 0,
        deliveryExpresses: [],
        actualTotal: 0,
        deliveryType: '',
        status: 1,
        addrOrderId: 0,
        nickName: '',
        orderItems: [],
        orderTime: '',
        updateTime: '',
        payTime: '',
        finallyTime: '',
        cancelTime: '',
        orderType: '',
        userAddrOrder: {}
      },
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
      indexs: 0,
      orderRemarkUpdateVisible: false,
      orderAddrUpdateVisible: false,
      offsetCount: 0 // 偏移量
    }
  },
  computed: {
    stepsStatus: function() {
      if (this.dataForm.finallyTime) {
        return 4
      } else if (this.dataForm.payTime) {
        return 2
      } else if (this.dataForm.createTime) {
        return 1
      } else {
        return 0
      }
    }
  },
  watch: {
    visible: function() {
      if (!this.visible) {
        this.orderRemarkUpdateVisible = false
        this.orderAddrUpdateVisible = false
      }
    },
    deliveryExpresse: function(newVal, oldVal) {
      this.$nextTick(() => {
        this.offsetCount = 0 // 初始化变量
        if (this.deliveryExpresse) {
          this.$refs.carouser.style.left = '0px'
        }
      })
    }
  },
  methods: {
    init(orderId) {
      this.dataForm.orderId = orderId || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
      })
      if (this.dataForm.orderId) {
        this.getDataList()
      }
    },
    getDataList() {
      api.orderInfo(this.dataForm.orderId).then((data) => {
        this.dataForm = data
        if (this.dataForm.deliveryExpresses) {
          this.deliveryExpresse = this.dataForm.deliveryExpresses[0]
        }
      })
    },
    /**
     * 物流事件
     */
    onClickListDelivery(delivery, index) {
      this.deliveryExpresse = delivery
      this.indexs = index
    },
    // 商品切换
    prevItem() {
      var len = this.deliveryExpresse.orderItems.length
      if (len - 5 > 0) {
        if (this.offsetCount > 0) {
          this.offsetCount--
          this.$refs.carouser.style.left = '-' + (70 * this.offsetCount) + 'px'
        } else {
          return false
        }
      } else if (len - 5 <= 0) {
        return false
      } else {
        return false
      }
    },

    nextItem() {
      var len = this.deliveryExpresse.orderItems.length
      if (len - 5 > 0) {
        if (this.offsetCount < len - 5) {
          this.offsetCount++
          this.$refs.carouser.style.left = '-' + (70 * this.offsetCount) + 'px'
        } else if (len - 5 <= 0) {
          return false
        } else {
          return false
        }
      } else {
        return false
      }
    },
    // 表单提交
    dataFormSubmit() {
    },
    // 修改地址
    changeUserAddrOrder(userAddrOrder) {
      this.orderAddrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.orderAddrUpdate.init(this.dataForm)
      })
    },
    // 修改备注
    changeRemarks() {
      this.orderRemarkUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.orderRemarkUpdate.init(this.dataForm)
      })
    }
  }
}
</script>

<style>
.detail-dialog .el-dialog {
  min-width: 950px;
  margin-top: 10vh !important;
}

.detail-dialog .goods-list .has-gutter th.is-leaf {
  background: #f7f8fa;
  color: #333;
  border-bottom: 1px solid #ebeef5;
  font-weight: 400;
  border-right: 0;
}

.detail-dialog .goods-list .el-table td {
  border-right: 0;
}
</style>

<style lang="scss" scoped>
.mod-order-orderInfo {
  height: 100%;
  width: 100%;
  font: 14px Arial, "PingFang SC", "Hiragino Sans GB", STHeiti,
    "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;
  color: #333;

  .order-number {
    display: flex;
    align-items: center;
    font-size: 14px;
  }

  .order-number .text {
    color: #999;
  }

  .order-number .number,
  .order-number .time {
    display: flex;
    align-items: center;
  }

  .order-number .time {
    margin: 0 15px;
  }

  .order-state {
    position: relative;
    margin-top: 15px;
    border: 1px solid #eee;
    display: flex;
    align-items: center;
  }

  .order-state .state-box {
    padding: 20px 15px;
    width: 40%;
    border-right: 1px solid #eee;
  }

  .order-state .state-box .state {
    font-size: 20px;
    font-weight: 700;
    line-height: 28px;
  }

  .order-state .state-box .state-des {
    margin-top: 10px;
    color: #999;
  }

  .order-state .state-box .actions {
    margin-top: 15px;
  }

  .order-state .state-box .actions .item {
    display: flex;
    align-items: center;
  }

  .order-state .state-box .actions .item .el-button--text {
    cursor: pointer;
  }

  .order-state .state-box .actions .item .item-btn {
    padding: 3px 10px;
    border: 1px solid #eee;
    border-radius: 2px;
  }

  .order-state .state-steps {
    flex: 1;
    margin-left: -80px;
    padding-bottom: 10px;
  }

  .order-state .state-steps .el-form-item {
    margin-bottom: 0;
  }

  .packages {
    margin-top: 15px;
  }

  .packages .p-tab {
    display: flex;
    align-items: center;
  }

  .packages .p-tab .item {
    background: #f2f2f2;
    border: 1px solid #eee;
    margin-right: -1px;
    margin-bottom: -1px;
    position: relative;
    padding: 8px 20px;
    cursor: pointer;
  }

  .packages .p-tab .item:first-child {
    border-radius: 3px 0 0 0;
  }

  .packages .p-tab .item:last-child {
    border-radius: 0 3px 0 0;
  }

  .packages .p-tab .item.active {
    background: #fff;
    border-bottom: 1px solid #fff;
  }

  .packages .p-con {
    border: 1px solid #eee;
    padding: 20px;
    display: flex;
  }

  .packages .p-con .deliver-msg {
    width: 40%;
    min-width: 430px;
  }

  .packages .p-con .deliver-msg .d-item {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
  }

  .packages .p-con .deliver-msg .d-item .text {
    width: 80px;
  }

  .packages .p-con .deliver-msg .d-goods {
    position: relative;
    width: 340px;
    margin: 30px 0 0;
  }

  .packages .p-con .deliver-msg .d-goods.over {
    margin: 30px 32px 0;
  }

  .packages .p-con .deliver-msg .d-goods .arrow {
    position: absolute;
    top: 28px;
    width: 22px;
    height: 22px;
    background: #eee;
    border-radius: 50%;
    cursor: pointer;
    display: none;
  }

  .packages .p-con .deliver-msg .d-goods .arrow.disable {
    opacity: 0.5;
    cursor: not-allowed;
  }

  .packages .p-con .deliver-msg .d-goods.over .arrow {
    display: block;
  }

  .packages .p-con .deliver-msg .d-goods .arrow.prev {
    left: -32px;
  }

  .packages .p-con .deliver-msg .d-goods .arrow.next {
    right: -32px;
    -webkit-transform: rotate(180deg);
    -moz-transform: rotate(180deg);
    transform: rotate(180deg);
  }

  .packages .p-con .deliver-msg .d-goods .arrow::before {
    position: absolute;
    left: 9px;
    top: 8px;
    display: block;
    width: 5px;
    height: 5px;
    content: " ";
    font-size: 0;
    border: 1px solid #666;
    border-width: 1px 1px 0 0;
    -webkit-transform: rotate(225deg);
    -moz-transform: rotate(225deg);
    transform: rotate(225deg);
  }

  .packages .p-con .deliver-msg .d-goods .item-goods {
    position: relative;
    width: 340px;
    height: 80px;
    overflow: hidden;
  }

  .packages .p-con .deliver-msg .d-goods .goods-box {
    position: absolute;
    left: 0;
    display: flex;
    margin-top: 0;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
  }

  .packages .p-con .deliver-msg .d-goods .goods-box .item {
    margin-right: 10px;
    font-size: 12px;
    cursor: pointer;
  }

  .packages .p-con .deliver-msg .d-goods .goods-box .item:last-child {
    margin: 0;
  }

  .packages .p-con .deliver-msg .d-goods .goods-box .item .img {
    width: 60px;
    height: 60px;
    font-size: 0;
    margin-bottom: 4px;
    position: relative;
  }

  .packages .p-con .deliver-msg .d-goods .goods-box .item .img img {
    width: 100%;
    height: 100%;
  }

  .packages .p-con .deliver-msg .d-goods .goods-box .item .img .number {
    position: absolute;
    bottom: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.3);
    color: #fff;
    border-radius: 6px 0 6px 0;
    font-size: 12px;
    height: 16px;
    line-height: 16px;
    padding: 0 5px;
  }

  .packages .p-con .deliver-msg .d-goods .goods-box .item .name {
    width: 60px;
    height: 16px;
    line-height: 16px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    color: #999;
  }

  .packages .p-con .logistics {
    flex: 1;
  }

  .packages .p-con .logistics .l-tit {
    display: flex;
  }

  .packages .p-con .logistics .l-tit .l-state {
    color: #c00;
  }

  .packages .p-con .logistics .logistics-box {
    height: 175px;
    overflow-y: scroll;
    position: relative;
    margin-top: 15px;
  }

  .packages .p-con .logistics .logistics-box::before {
    position: absolute;
    left: 0;
    top: 0;
    display: block;
    width: 10px;
    height: 10px;
    content: " ";
    font-size: 0;
    background: #fff;
    z-index: 1;
  }

  .packages .p-con .logistics .logistics-box::after {
    position: absolute;
    left: 6px;
    top: 0;
    display: block;
    width: 1px;
    height: 100%;
    content: " ";
    font-size: 0;
    background: #eee;
    z-index: 0;
  }

  .packages .p-con .logistics .logistics-box .item {
    padding: 10px 0 30px 25px;
    position: relative;
    margin-left: 6px;
    border-left: 1px solid #eee;
  }

  .packages .p-con .logistics .logistics-box .item::before {
    position: absolute;
    left: -10px;
    top: 10px;
    display: block;
    width: 19px;
    height: 19px;
    border-radius: 50%;
    content: " ";
    font-size: 0;
    background: #ccc;
    border: 5px solid #fff;
    z-index: 2;
  }

  .packages .p-con .logistics .logistics-box .item:first-child:before {
    background: #c00;
  }

  .packages .p-con .logistics .logistics-box .item .time {
    color: #999;
  }

  .packages .p-con .logistics .logistics-box .item .text {
    margin-top: 5px;
    width: 400px;
  }

  .order-info {
    background: #f7f8fa;
    margin-top: 20px;
    display: flex;
    padding: 20px;
  }

  .order-info .info-item {
    width: 25%;
    padding-right: 30px;
  }

  .order-info .info-item .item-tit {
    font-weight: 600;
  }

  .order-info .info-item .item {
    margin-top: 10px;
    display: flex;
    line-height: 20px;
  }

  .order-info .info-item .item .text {
    width: 75px;
    white-space: nowrap;
  }

  .goods-list {
    margin-top: 20px;
  }

  .goods-list .df {
    display: flex;
    align-items: center;
  }

  .goods-list .df .name {
    margin-left: 10px;
    line-height: 20px;
  }

  .goods-list .goods-total {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
  }

  .goods-list .goods-total .item {
    display: flex;
    align-items: center;
    color: #999;
    margin-top: 5px;
  }

  .goods-list .goods-total .item .text {
    width: 80px;
    text-align: center;
  }

  .goods-list .goods-total .item .number {
    width: 100px;
    text-align: right;
  }

  .goods-list .goods-total .item.act-price {
    margin-top: 20px;
    color: #333;
    font-weight: 600;
  }

  .goods-list .goods-total .item.act-price .number {
    color: #c00;
  }

  .order-log {
    margin-top: 20px;
    border-top: 1px dashed #e9eaec;
    padding: 20px 10px;
  }

  .order-log .log-title {
    height: 30px;
    width: 100%;
    line-height: 30px;
    font-weight: bold;
  }

  .order-log .log-cont {
    color: #02a1e9;
    margin-top: 20px;
  }
}
</style>

