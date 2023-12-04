<template>
  <div class="page-order-order">
    <div class="screening-conditions">
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter="getDataList()"
      >
        <div class="search">
          <!-- &nbsp;&nbsp;&nbsp; -->
          <el-form-item :label="$t('order.order.number') + '：'">
            <el-input
              v-model="dataForm.orderId"
              :placeholder="$t('order.order.number')"
              clearable
            />
          </el-form-item>
          <el-form-item :label="$t('order.order.status') + '：'">
            <el-select
              v-model="status"
              clearable
              :placeholder="$t('order.order.statusMsg')"
              @change="orderStatus"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </div>
        <div class="search">
          <el-form-item :label="$t('order.order.theRecipientSName') + '：'">
            <el-input
              v-model="dataForm.consignee"
              :placeholder="$t('order.order.pleaseEnRecipName')"
              clearable
            />
          </el-form-item>
          <el-form-item :label="$t('constant.contactTel') + '：'">
            <el-input
              v-model="dataForm.mobile"
              :placeholder="$t('order.order.pleaseEnterNumber')"
              clearable
            />
          </el-form-item>
        </div>
        <div class="search">
          <el-form-item :label="$t('order.order.createTime') + '：'">
            <el-date-picker
              v-model="dateRange"
              type="datetimerange"
              :range-separator="$t('date.tip')"
              value-format="YYYY-MM-DD HH:mm:ss"
              :start-placeholder="$t('date.start')"
              :end-placeholder="$t('date.end')"
            />
            <el-button
              class="ml10"
              @click="setDateRange(1)"
            >
              {{
                $t("date.t")
              }}
            </el-button>
            <el-button @click="setDateRange(2)">
              {{
                $t("date.y")
              }}
            </el-button>
            <el-button @click="setDateRange(3)">
              {{
                $t("date.n")
              }}
            </el-button>
            <el-button @click="setDateRange(4)">
              {{
                $t("date.m")
              }}
            </el-button>
          </el-form-item>
        </div>
        <div class="operation-box">
          <el-form-item>
            <el-button
              class="operation-btn"
              type="primary"
              @click="searchChange()"
            >
              {{ $t("order.order.query") }}
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              class="operation-btn"
              @click="clear()"
            >
              {{ $t("table.clear") }}
            </el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
    <div class="main">
      <div class="content">
        <!-- 导航 -->
        <div class="status-nav clearfix">
          <ul class="nav-list clearfix">
            <li
              :class="['nav-item', sts == 0 ? 'selected' : '']"
              data-sts="0"
              @click="selectNav($event)"
            >
              {{ $t("date.a") }}
            </li>
            <li
              :class="['nav-item', sts == 1 ? 'selected' : '']"
              data-sts="1"
              @click="selectNav($event)"
            >
              {{ $t("order.order.pendingPayment") }}
            </li>
            <li
              :class="['nav-item', sts == 2 ? 'selected' : '']"
              data-sts="2"
              @click="selectNav($event)"
            >
              {{ $t("order.order.toBeShipped") }}
            </li>
            <li
              :class="['nav-item', sts == 3 ? 'selected' : '']"
              data-sts="3"
              @click="selectNav($event)"
            >
              {{ $t("order.order.pendingReceipt") }}
            </li>
            <li
              :class="['nav-item', sts == 5 ? 'selected' : '']"
              data-sts="5"
              @click="selectNav($event)"
            >
              {{ $t("order.order.success") }}
            </li>
            <li
              :class="['nav-item', sts == 6 ? 'selected' : '']"
              data-sts="6"
              @click="selectNav($event)"
            >
              {{ $t("order.order.fail") }}
            </li>
          </ul>
        </div>

        <!-- 列标题 -->
        <div :class="['tit', showHeadScroll ? 'fixed-top' : '']">
          <el-row style="width: 100%">
            <el-col :span="5">
              <span class="item product">{{ $t("constant.product") }}</span>
            </el-col>
            <el-col
              :span="5"
              class="transaction-price"
            >
              <span class="item">{{ $t("order.order.transaQuantity") }}</span>
            </el-col>
            <el-col
              :span="3"
              class="column-title"
            >
              <span class="item">{{
                $t("order.order.actualPaymentAmount")
              }}</span>
            </el-col>
            <el-col
              :span="2"
              class="column-title"
            >
              <span class="item">支付状态</span>
            </el-col>
            <el-col
              :span="3"
              class="column-title"
            >
              <span class="item">{{ $t("order.order.buyerConsignee") }}</span>
            </el-col>
            <el-col
              :span="2"
              class="column-title"
            >
              <span class="item">{{ $t("order.order.status") }}</span>
            </el-col>
            <el-col
              :span="2"
              class="column-title"
            >
              <span class="item">{{ $t("table.actions") }}</span>
            </el-col>
          </el-row>
        </div>

        <div
          v-for="order in pageVO.list"
          :key="order.orderId"
          class="prod"
          style="margin-bottom: 15px"
        >
          <div class="prod-tit">
            <span>{{ $t("order.order.number") }}:{{ order.orderId }}</span>
            <span>{{ $t("order.order.createTime") }}:{{ order.createTime }}</span>
            <!-- <span>店铺名称：{{order.shopName}}</span> -->
            <!-- <span>买家：19999999999</span>
            <span >联系电话：19999999999</span>-->
          </div>
          <div class="prod-cont">
            <el-row style="width: 100%">
              <el-col :span="10">
                <div class="prod-item">
                  <div
                    v-for="orderItem in order.orderItems"
                    :key="orderItem.orderItemId"
                    class="items name"
                  >
                    <div class="prod-image">
                      <img :src="orderItem.pic">
                    </div>
                    <div class="prod-name">
                      <div class="prod-con">
                        <div class="prod-name-txt">
                          {{ orderItem.spuName }}
                        </div>
                        <div
                          v-if="!orderItem.returnMoneySts ||
                            orderItem.returnMoneySts < 0 ||
                            orderItem.returnMoneySts > 5
                          "
                          class="order-status"
                        >
                          {{
                            orderItem.status === 0 && order.status === 2
                              ? $t("order.order.pendingReceipt")
                              : [
                                "",
                                $t("order.order.pendingPayment"),
                                $t("order.order.toBeShipped"),
                                $t("order.order.pendingReceipt"),
                                "",
                                $t("order.order.successfulTransaction"),
                                $t("order.order.transactionFailed")
                              ][order.status]
                          }}
                        </div>
                        <!-- <span class="prod-info">{{orderItem.skuName}}</span> -->
                        <div
                          v-if="order.preSaleTime"
                          class="order-status"
                        >
                          {{ $t("order.order.estimatedShippingTime") }}：{{
                            order.preSaleTime
                          }}
                        </div>
                      </div>
                    </div>
                    <div class="prod-price">
                      <span>{{ (orderItem.price / 100).toFixed(2) }}</span>
                      <span>{{ orderItem.count }}{{ $t("constant.piece") }}</span>
                    </div>
                  </div>
                </div>
              </el-col>
              <el-col
                :span="3"
                style="height: 100%"
              >
                <div class="item">
                  <div>
                    <span class="totalprice">{{ (order.total / 100).toFixed(2)
                    }}{{ $t("constant.dollar") }}</span>
                    <br>
                    <span>{{ $t("order.order.total") }}{{ order.allCount
                    }}{{ $t("constant.piece") }}</span>
                    <br>
                  </div>
                </div>
              </el-col>
              <el-col
                :span="2"
                style="height: 100%"
              >
                <div class="item">
                  <div>
                    <span v-if="!order.isPayed">未支付</span>
                    <span v-else>已支付</span>
                  </div>
                </div>
              </el-col>
              <el-col
                :span="3"
                style="height: 100%"
              >
                <div class="item">
                  <div class="buyer-info">
                    <div class="buyer-name">
                      {{ order.consignee || '' }}
                    </div>
                    <div class="buyer-phone">
                      {{ order.mobile || '' }}
                    </div>
                  </div>
                </div>
              </el-col>
              <el-col
                :span="2"
                style="height: 100%"
              >
                <div class="item">
                  <span>
                    <span
                      v-if="order.status === 1"
                      type="danger"
                    >{{ $t("order.order.pendingPayment") }}</span>
                    <span
                      v-else-if="order.status === 2"
                      type="danger"
                    >{{ $t("order.order.toBeShipped") }}</span>
                    <span
                      v-else-if="order.status === 3"
                      type="danger"
                    >{{ $t("order.order.pendingReceipt") }}</span>
                    <span
                      v-else-if="order.status === 5"
                      type="danger"
                    >{{ $t("order.order.successfulTransaction")
                    }}</span>
                    <span v-else-if="order.status === 6">{{
                      $t("order.order.transactionFailed")
                    }}</span>
                  </span>
                </div>
              </el-col>
              <el-col
                :span="2"
                style="height: 100%"
              >
                <div class="item">
                  <div class="operate">
                    <!-- <button onclick="">打印订单</button><br> -->
                    <el-button
                      type="primary"
                      link
                      @click="addOrUpdateHandle(order.orderId)"
                    >
                      {{ $t("order.order.seeDetails") }}
                    </el-button>
                    <el-button
                      v-if="order.status === 2 && order.deliveryType !== 2"
                      type="primary"
                      link
                      @click="changeOrder(order)"
                    >
                      {{ $t("order.order.delivery") }}
                    </el-button>
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
          <!-- <div class="remark">
            <div class="buyer-remark">
              <span>备注:{{order.remarks}}</span>
            </div>
          </div>-->
        </div>
        <div
          v-if="!pageVO.list.length"
          class="empty"
        >
          {{ $t("order.order.noData") }}
        </div>
      </div>
    </div>
    <!-- 分页条 -->
    <pagination
      v-show="pageVO.total > 0"
      v-model:page="pageQuery.pageNum"
      v-model:limit="pageQuery.pageSize"
      :total="pageVO.total"
      @pagination="getDataList()"
    />

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update
      v-if="addOrUpdateVisible"
      ref="addOrUpdateRef"
      @refresh-data-list="getDataList"
    />
    <order-delivery-update
      v-if="devyVisible"
      ref="orderDeliveryUpdateRef"
      @refresh-order-delivery-update="getDataList"
    />
  </div>
</template>

<script setup>
import moment from 'moment'
import * as api from '@/api/order/order'
import { onActivated, onDeactivated, onMounted, reactive } from 'vue'
import AddOrUpdate from './components/order-info.vue'
import OrderDeliveryUpdate from './components/order-delivery-update.vue'

const Data = reactive({
  showHeadScroll: false, // 修改物流相关
  logVisible: false,
  radio: '1',
  logisticsInfo: [], // 包裹列表
  logDataForm: {
    deliveryCompanyId: '', // 当前物流公司id
    deliveryNo: '', // 物流单号
    names: [] // 物流公司列表
  },

  errorNum: 0,
  // 确认修改弹窗
  isReviseLog: true, // 是否正在修改物流信息
  confirmList: [], // 确认修改信息
  dataForm: {},
  sts: 0,
  dateRange: [],
  status: null,
  options: [{
    value: 1,
    label: $t('order.order.pendingPayment')
  },
  {
    value: 2,
    label: $t('order.order.toBeShipped')
  },
  {
    value: 3,
    label: $t('order.order.pendingReceipt')
  },
  {
    value: 5,
    label: $t('order.order.successfulTransaction')
  },
  {
    value: 6,
    label: $t('order.order.transactionFailed')
  }],
  orderType: [{
    value: 0,
    label: $t('order.order.normalOrder')
  }],
  deliveryType: [
    {
      value: 3,
      label: $t('order.order.noNeedRequired')
    }],
  payType: [{
    value: 0,
    label: $t('order.order.pointsPayment')
  }, {
    value: 1,
    label: $t('admin.weChatPay')
  }, {
    value: 2,
    label: $t('admin.aliPay')
  }, {
    value: 3,
    label: $t('admin.balancePay')
  }],
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  // 返回参数
  pageVO: {
    list: [], // 返回的列表
    total: 0, // 一共多少条数据
    pages: 0 // 一共多少页
  },
  // 查询的参数
  pageQuery: {
    pageSize: 10,
    pageNum: 1
  },
  dataListLoading: false,
  dataListSelections: [],
  devyVisible: false,
  addOrUpdateVisible: false,
  orderStationVisible: false,
  consignmentInfoVisible: false,
  orderAmountUpdateVisible: false
})
const { showHeadScroll, dataForm, sts, dateRange, status, options, pageVO, pageQuery, devyVisible, addOrUpdateVisible } = toRefs(Data)

const route = useRoute()
onMounted(() => {
  // 携带参数查询
  getDataList(route.query)
  // 监听页面滚动
  window.addEventListener('scroll', scrollToTop)
})

onActivated(() => {
  // 携带参数查询
  const query = route.query
  if (Object.keys(query).length > 0) {
    getDataList(query)
  }
})

onDeactivated(() => {
  // 页面销毁时移除监听
  window.removeEventListener('scroll')
})

/**
     * 页面滚动事件
     */
const scrollToTop = () => {
  const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
  Data.showHeadScroll = scrollTop > 400
}

/**
     * 获取数据列表
     */
const getDataList = (params) => {
  Data.dataListLoading = true

  api.page(Object.assign({
    ...Data.pageQuery,
    ...Data.searchParam,
    orderId: Data.dataForm.orderId,
    // 'spuName': Data.dataForm.spuName,
    orderType: Data.dataForm.orderType,
    payType: Data.dataForm.payType,
    consignee: Data.dataForm.consignee,
    mobile: Data.dataForm.mobile,
    status: Data.status,
    deliveryType: Data.dataForm.deliveryType,
    stationName: Data.dataForm.stationName,
    refundStatus: Data.dataForm.refundStatus,
    startTime: Data.dateRange === null ? null : Data.dateRange[0], // 开始时间
    endTime: Data.dateRange === null ? null : Data.dateRange[1] // 结束时间
  }, params)).then(pageVO => {
    Data.pageVO = pageVO
    Data.sts = !Data.status ? 0 : Data.status
    Data.dataListLoading = false
  })
}

/**
     * 导航选择状态
     */
const selectNav = (e) => {
  const sts = e.currentTarget.dataset.sts
  Data.sts = parseInt(sts)
  Data.status = Data.sts === 0 ? null : parseInt(sts)
  getDataList()
}

const orderStatus = (val) => {
  Data.status = val
}

/**
     * 根据选项设置时间
     * 1:今天 2:昨天 3: 近七天 4:近30天 5:近60天
     */
const setDateRange = (val) => {
  let startDay = null
  let endDay = null
  if (val === 1) {
    startDay = 0
    endDay = 0
  } else if (val === 2) {
    startDay = -1
    endDay = -1
  } else if (val === 3) {
    startDay = -7
    endDay = -1
  } else if (val === 4) {
    startDay = -30
    endDay = -1
  } else {
    return
  }
  // 开始时间
  const startTime = moment().add(startDay, 'days').startOf('days').format('LL')
  // 结束时间
  const endTime = moment().add(endDay, 'days').endOf('days').format('LL')
  Data.dateRange = [startTime, endTime]
}

// 新增 / 修改
const addOrUpdateRef = ref()
const addOrUpdateHandle = (val) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(val)
  })
}

// 清空按钮
const clear = () => {
  Data.dataForm = {}
  Data.dateRange = []
  Data.status = null
}

// 搜索查询
const searchChange = () => {
  Data.pageQuery.pageNum = 1
  getDataList()
}

/**
       * 发货
       */
const orderDeliveryUpdateRef = ref()
const changeOrder = (order) => {
  Data.devyVisible = true
  nextTick(() => {
    orderDeliveryUpdateRef.value.init(order)
  })
}
</script>

<!-- eslint-disable-next-line vue-scoped-css/enforce-style-type -->
<style lang="scss">
.page-order-order {

  .search {
    width: 1500px;
  }

  .clearfix:after {
    /*伪元素是行内元素 正常浏览器清除浮动方法*/
    content: "";
    display: block;
    height: 0;
    clear: both;
    visibility: hidden;
  }

  .clearfix {
    *zoom: 1;
    /*ie6清除浮动的方式 *号只有IE6-IE7执行，其他浏览器不执行*/
  }

  .screening-conditions {
    display: block;
    padding: 20px;
    background: #f8f8f9;
    margin-bottom: 20px;
  }

  .operation-box {
    display: inline-block;
    margin-left: 100px;
  }

  .ml10 {
    margin-left: 10px;
  }

  .main {
    font-size: 14px;

    .status-nav {
      position: relative;
      display: block;
      width: 100%;
      margin-bottom: 15px;
      height: 40px;
      line-height: 40px;
      border-bottom: 1px solid #ddd;

      ul,
      li {
        list-style: none;
        padding: 0;
        margin: 0;
      }

      .nav-item {
        float: left;
        height: 40px;
        line-height: 40px;
        background: #f8f8f9;
        border: 1px solid #ddd;
        padding: 0 20px;
        margin: 0 -1px -1px 0;
        cursor: pointer;
      }

      .selected {
        background: #fff;
        border-bottom: 1px solid #fff;
      }
    }

    .tit {
      margin-bottom: 15px;
      background: #f8f8f9;
      z-index: 11;
    }

    .fixed-top {
      position: fixed;
      width: 992px;
      top: 0;
      left: 50%;
      transform: translateX(-50%);
      margin-left: 113px;
    }

    .column-title {
      text-align: center;
    }

    .transaction-price {
      text-align: right;
    }
  }

  // .prod {
  //   margin-bottom: 15px;
  // }
  .el-input-number--medium .el-input-number__increase,
  .el-input-number--medium .el-input-number__decrease {
    width: 36px;
    font-size: 12px;
    height: 30px;
    line-height: 32px;
  }

  .el-input-number__increase,
  .el-input-number__decrease {
    top: 2px;
  }

  .tit {
    display: flex;
    height: 45px;
    align-items: center;
  }

  .tit .item {
    padding: 0 10px;
    width: 10%;
    text-align: center;
  }

  .tit .product {
    width: 25%;
  }

  .prod-tit {
    padding: 10px;
    background: #f8f8f9;
    border-left: 1px solid #dddee1;
    border-top: 1px solid #dddee1;
    border-right: 1px solid #dddee1;
  }

  .prod-tit span {
    margin-right: 15px;
  }

  .prod-cont {
    display: flex;
    border-top: 1px solid #dddee1;
    border-bottom: 1px solid #dddee1;
    border-left: 1px solid #dddee1;
    color: #495060;
  }

  .prod-cont .item {
    display: flex;
    display: -webkit-flex;
    align-items: center;
    justify-content: center;
    padding: 10px;
    // width: 10%;
    border-right: 1px solid #dddee1;
    text-align: center;
    height: 100%;
  }

  button.revise-btn {
    margin-top: 10px;
  }

  .el-button--text {
    padding-top: 0;
    padding-bottom: 0;
  }

  // .prod-cont .item span {
  //   display: block;
  // }
  .item span {
    display: inline-block !important;
  }

  .prod-cont .prod-item {
    // width: 38%;
    display: flex;
    flex-direction: column;
    border-right: 1px solid #dddee1;
  }

  .prod-name {
    width: 55%;
    text-align: left;
  }

  .prod-con {
    display: block;
    padding: 0 !important;
  }

  .prod-price {
    position: absolute;
    right: 10px;
    text-align: center;
  }

  .prod-price span {
    display: block;
  }

  .prod-price span:first-child {
    margin-bottom: 10px;
  }

  .prod-name .prod-info {
    display: block;
    color: #80848f;
    margin-top: 30px;
  }

  .order-status {
    display: inline-block;
    margin-top: 15px;
    padding: 2px 4px;
    border: 1px solid #e43130;
    border-radius: 2px;
    color: #e43130;
    font-size: 12px;
  }

  .prod-cont .items.name {
    display: flex;
    align-items: center;
    position: relative;
    padding: 10px;
    // height: 100px;
    border-bottom: 1px solid #dddee1;
  }

  .prod-cont .items.name:last-child {
    border-bottom: none;
  }

  .prod-image {
    margin-right: 20px;
    width: 80px;
    height: 80px;
  }

  .prod-image img {
    width: 100%;
    height: 100%;
  }

  .item span {
    display: block;
  }

  .item .operate {
    color: #2d8cf0;
  }

  .item .operate .el-button+.el-button {
    display: block;
    margin-top: 10px;
    margin-left: 0;
  }

  .item .totalprice {
    color: #c00;
    margin-bottom: 10px;
  }

  .prod .remark {
    width: 100%;
    height: 50px;
    line-height: 50px;
    background-color: #e8f7f6;
    border-left: 1px solid #dddee1;
    border-right: 1px solid #dddee1;
    border-bottom: 1px solid #dddee1;
    margin-bottom: 20px;
  }

  .buyer-remark {
    padding: 0 20px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .empty {
    display: block;
    height: 200px;
    line-height: 200px;
    text-align: center;
    color: #aaa;
  }

  // 修改物流弹窗
  .change-logistics {
    color: #333;
    padding: 0 20px;
  }

  // .change-logistics,
  // .revise-log {
  //   max-height: 300px;
  //   overflow-y: auto;
  // }
  .log-list {
    max-height: 600px;
    overflow-y: auto;
  }

  .log-info-table {
    margin-top: 20px;
    max-height: 600px;
    overflow-y: auto;
  }

  .change-logistics .warning {
    padding: 10px;
    border: 1px solid #f1924e;
    background: #fff5ed;
  }

  .change-logistics .item {
    padding-bottom: 20px;
  }

  .change-logistics .item .i-tit {
    display: flex;
    align-items: center;
  }

  .change-logistics .item .i-tit .big {
    font-weight: 600;
    margin-right: 15px;
  }

  .change-logistics .item .i-con {
    display: flex;
    align-items: center;
    margin-top: 15px;
  }

  .change-logistics .item .i-con .label {
    width: 80px;
  }

  .change-logistics .item .i-con .con {
    display: flex;
    align-items: center;
  }

  .change-logistics .item .i-con .con .con-radio {
    margin-right: 10px;
  }

  .change-logistics .item .i-con .con .con-radio input,
  .change-logistics .item .i-con .con .con-radio span {
    display: inline-block;
    vertical-align: middle;
    margin-right: 8px;
  }

  .change-logistics .item .i-con .con .con-radio input[type="radio"] {
    margin-left: 5px;
  }

  .change-logistics .item .i-con .con .con-select {
    position: relative;
    padding: 6px 25px 6px 12px;
    width: 220px;
    height: 32px;
    line-height: 18px;
    border: 1px solid #ddd;
    background: #fff;
    border-radius: 2px;
  }

  .change-logistics .item .i-con .con .con-select::after {
    position: absolute;
    top: 13px;
    right: 8px;
    display: block;
    width: 0;
    height: 0;
    content: " ";
    font-size: 0;
    border: 5px solid transparent;
    border-top: 5px solid #333;
    border-radius: 2px;
  }

  .change-logistics .item .i-con .con .con-text {
    margin-left: 10px;
    display: flex;
    align-items: center;
  }

  .change-logistics .item .i-con .con .con-text .text {
    width: 80px;
  }

  .change-logistics .item .i-con .con .con-text input[type="text"] {
    border: 1px solid #ddd;
    padding: 0 10px;
    height: 30px;
    width: 160px;
    border-radius: 2px;
  }

  // 修改物流弹窗end

  // 修改物流信息弹窗
  .log-list {
    margin-top: 30px;
  }

  .el-form-item__label {
    // text-align: left;
    width: 100px;
  }

  .el-form-item {
    margin-bottom: 0;
    margin-top: 10px;
  }

  .info-int {
    display: flex;
  }

  .form-item {
    margin-right: 15px;
  }

  .int-frame {
    border-radius: 0;
    border: 0;
    border: 1px solid #ddd;
  }

  .info-int .el-form-item__content,
  .info-int .el-form-item__label,
  .el-input__suffix-inner,
  .el-input__icon {
    height: 32px;
    line-height: 32px;
  }

  input.el-input__inner {
    // border: 0;
    height: 32px;
    line-height: 32px;
    font-size: 14px;
  }

  // 修改物流信息弹窗end
  // 确认修改
  .el-table .cell {
    text-align: center;
  }

  .errortip {
    border-color: #e43130;
  }
}
</style>
