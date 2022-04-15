<template>
  <el-dialog
    :title="
      !dataForm.transportId
        ? this.$i18n.t('table.add')
        : this.$i18n.t('table.edit')
    "
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="1400px"
  >
    <el-form
      ref="dataForm"
      :model="dataForm"
      label-width="80px"
      @keyup.enter.native="dataFormSubmit()"
    >
      <el-form-item
        :label="this.$i18n.t('delivery.transport.name')"
        prop="transName"
        :rules="[
          { required: true, message: $t('delivery.transport.templateNameCannotBeEmpty') },
        ]"
      >
        <el-input
          v-model="dataForm.transName"
          maxlength="36"
          show-word-limit
          :placeholder="this.$i18n.t('delivery.transport.name')"
        />
      </el-form-item>
      <el-form-item
        :label="this.$i18n.t('delivery.transport.type')"
        prop="isFreeFee"
        required="required"
      >
        <template>
          <el-radio-group v-model="dataForm.isFreeFee" @change="changeFreeFee">
            <el-radio :label="0">{{ $t("delivery.transport.buyerPrice") }}</el-radio>
            <el-radio :label="1">{{ $t("delivery.transport.shopPrice") }}</el-radio>
          </el-radio-group>
        </template>
      </el-form-item>
      <el-form-item
        :label="this.$i18n.t('delivery.transport.chargePrice')"
        prop="chargeType"
      >
        <template>
          <el-radio-group
            v-model="dataForm.chargeType"
            :disabled="dataForm.isFreeFee == 1"
          >
            <el-radio :label="0">{{ $t("delivery.transport.byCount") }}</el-radio>
            <el-radio :label="1">{{ $t("delivery.transport.byWeight") }}</el-radio>
            <el-radio :label="2">{{ $t("delivery.transport.byVolume") }}</el-radio>
          </el-radio-group>
        </template>
      </el-form-item>
      <el-table :data="dataForm.transFees" border style="width: 100%">
        <!-- 区县级地址全部显示 -->
        <!-- <el-table-column header-align="center" align="center" width="450" label="可配送区域">
          <template slot-scope="scope">
            <span v-if="scope.$index == 0">所有地区</span>
            <span
              v-if="(!scope.row.cityList || !scope.row.cityList.length) && scope.$index > 0"
            >请选择可配送区域</span>
            <span v-if="scope.$index > 0">
              <el-tag v-for="city in scope.row.cityList" :key="city.areaId">{{city.areaName}}</el-tag>
            </span>
            <el-button
              type="text"
              size="small"
              @click="addOrUpdateHandle(`${scope.$index}`)"
              v-if="scope.$index > 0"
            >编辑</el-button>
            <el-button
              type="text"
              size="small"
              @click="deleteHandle(`${scope.$index}`)"
              v-if="scope.$index > 0"
            >{{$t("text.delBtn")}}</el-button>
          </template>
        </el-table-column>-->

        <!-- 显示动态显示省市区 -->
        <el-table-column
          header-align="center"
          align="center"
          width="650"
          :label="this.$i18n.t('delivery.transport.distributableArea')"
        >
          <template slot-scope="scope">
            <span v-if="scope.$index == 0">{{
              $t("delivery.transport.allRegions")
            }}</span>
            <span
              v-if="
                (!scope.row.addrNameList || !scope.row.addrNameList.length) &&
                  scope.$index > 0
              "
            >{{ $t("delivery.transport.pleaseSelectADeliveryArea") }}
            </span>
            <span v-if="scope.$index > 0">
              <!-- {{scope.row.addrNameList.toString()}} -->
              <el-tag
                v-for="(city, index) in scope.row.addrNameList"
                :key="index"
              >{{ city }}</el-tag>
            </span>
            <el-button
              v-if="scope.$index > 0"
              type="text"
              size="small"
              @click="addOrUpdateHandle(`${scope.$index}`)"
            >{{ $t("table.edit") }}</el-button>
            <el-button
              v-if="scope.$index > 0"
              type="text"
              size="small"
              @click="deleteHandle(`${scope.$index}`)"
            >{{ $t("table.delete") }}</el-button>
          </template>
        </el-table-column>

        <el-table-column
          header-align="center"
          align="center"
          width="180"
          :label="tableTitle[0]"
        >
          <template slot-scope="scope">
            <el-form-item
              :prop="`transFees.${scope.$index}.firstPiece`"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: `${tableTitle[0]}${$t('table.noNull')}`,
                },
              ]"
            >
              <el-input
                v-model="scope.row.firstPiece"
                type="number"
                :disabled="!scope.row.status && scope.$index === 0"
                @change="checkNumber(scope.row, 1)"
              />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          header-align="center"
          align="center"
          :label="tableTitle[1]"
        >
          <template slot-scope="scope">
            <el-form-item
              :prop="`transFees.${scope.$index}.firstFee`"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: `${tableTitle[1]}${$t('table.noNull')}`,
                },
              ]"
            >
              <el-input-number
                v-model="scope.row.firstFee"
                :disabled="!scope.row.status && scope.$index === 0"
                :precision="2"
                :min="0"
                @change="checkNumber(scope.row, 2)"
              />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          header-align="center"
          align="center"
          :label="tableTitle[2]"
        >
          <template slot-scope="scope">
            <el-form-item
              :prop="`transFees.${scope.$index}.continuousPiece`"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: `${tableTitle[2]}${$t('table.noNull')}`,
                },
              ]"
            >
              <el-input
                v-model="scope.row.continuousPiece"
                type="number"
                :disabled="!scope.row.status && scope.$index === 0"
                @change="checkNumber(scope.row, 3)"
              />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          header-align="center"
          align="center"
          :label="tableTitle[3]"
        >
          <template slot-scope="scope">
            <el-form-item
              :prop="`transFees.${scope.$index}.continuousFee`"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: `${tableTitle[3]}${$t('table.noNull')}`,
                },
              ]"
            >
              <el-input-number
                v-model="scope.row.continuousFee"
                :precision="2"
                :min="0"
                :disabled="!scope.row.status && scope.$index === 0"
                @change="checkNumber(scope.row, 4)"
              />
            </el-form-item>
          </template>
        </el-table-column>
      </el-table>
      <div v-if="dataForm.isFreeFee == 0" style="margin-top: 20px">
        <el-button
          type="primary"
          icon="el-icon-location-outline"
          @click="addTransfee()"
        >{{ $t("delivery.transport.addAreaAndShipping") }}</el-button>
      </div>
      <el-checkbox
        v-if="!dataForm.isFreeFee"
        v-model="dataForm.hasFreeCondition"
        style="margin-top: 10px; font-size: 50px"
      >{{ $t("delivery.transport.freeShippingOnSpecifiedConditions") }}</el-checkbox>
      <el-table
        v-if="dataForm.hasFreeCondition && !dataForm.isFreeFee"
        :data="dataForm.transFeeFrees"
        border
        style="width: 100%"
      >
        <!-- 区县级地址全部显示 -->
        <!-- <el-table-column header-align="center" align="center" width="350" label="指定区域">
          <template slot-scope="scope">
            <span v-if="!scope.row.freeCityList || !scope.row.freeCityList.length">请选择指定包邮城市</span>
            <el-tag v-for="city in scope.row.freeCityList" :key="city.areaId">{{city.areaName}}</el-tag>
            <el-button
              type="text"
              size="small"
              @click="addOrUpdateTransfeeFree(`${scope.$index}`)"
            >编辑</el-button>
            <el-button type="text" size="small" @click="deleteTransfeeFree(`${scope.$index}`)">{{$t("text.delBtn")}}</el-button>
          </template>
        </el-table-column>-->

        <!-- 显示动态显示省市区 -->
        <el-table-column
          header-align="center"
          align="center"
          width="350"
          :label="this.$i18n.t('delivery.transport.selArea')"
        >
          <template slot-scope="scope">
            <span
              v-if="
                !scope.row.freeAddrNameList ||
                  !scope.row.freeAddrNameList.length
              "
            >{{ $t("delivery.transport.selCity") }}</span>
            <el-tag
              v-for="(city, index) in scope.row.freeAddrNameList"
              :key="index"
            >{{ city }}</el-tag>
            <el-button
              type="text"
              size="small"
              @click="addOrUpdateTransfeeFree(`${scope.$index}`)"
            >{{ $t("table.edit") }}</el-button>
            <el-button
              type="text"
              size="small"
              @click="deleteTransfeeFree(`${scope.$index}`)"
            >{{ $t("table.delete") }}</el-button>
          </template>
        </el-table-column>
        <el-table-column
          header-align="center"
          align="center"
          width="600"
          :label="this.$i18n.t('delivery.transport.setShopPrice')"
        >
          <template slot-scope="scope">
            <el-radio-group v-model="scope.row.freeType">
              <el-radio
                :label="0"
              >{{ $t("delivery.transport.fullPieces")
              }}{{ $t("delivery.transport.freeShipping") }}</el-radio>
              <el-radio :label="1">{{
                $t("delivery.transport.freeShiullAmount")
              }}</el-radio>
              <el-radio
                :label="2"
              >{{ $t("delivery.transport.fullPieces") }}{{ $t("delivery.transport.further")
              }}{{ $t("delivery.transport.freeShiullAmount") }}</el-radio>
            </el-radio-group>
          </template>
        </el-table-column>
        <el-table-column header-align="center" align="left">
          <template slot-scope="scope">
            <el-form-item
              v-if="scope.row.freeType == 1 || scope.row.freeType == 2"
              :prop="`transFeeFrees.${scope.$index}.amount`"
              label-width="0px"
              :rules="[{ required: true, message: `${$t('table.noNull')}` }]"
            >
              <el-input
                v-model="scope.row.amount"
                style="width: 350px"
                type="number"
                :precision="2"
                :min="0"
                @change="checkNumber(scope.row, 5)"
              >
                <template slot="prepend">{{ $t("delivery.transport.full") }}</template>
                <template
                  slot="append"
                >{{ $t("delivery.transport.dollar")
                }}{{ $t("delivery.transport.freeShipping") }}</template>
              </el-input>
            </el-form-item>
            <el-form-item
              v-if="scope.row.freeType == 0 || scope.row.freeType == 2"
              :prop="`transFeeFrees.${scope.$index}.piece`"
              label-width="0px"
              :rules="[{ required: true, message: `${$t('table.noNull')}` }]"
            >
              <el-input
                v-model="scope.row.piece"
                style="width: 350px"
                type="number"
                @change="checkNumber(scope.row, 6)"
              >
                <template slot="prepend">{{ $t("delivery.transport.full") }}</template>
                <template slot="append">{{ $t("delivery.transport.pwvFree") }}</template>
              </el-input>
            </el-form-item>
          </template>
        </el-table-column>
      </el-table>
      <div v-if="dataForm.isFreeFee == 0" style="margin-top: 20px">
        <el-button
          type="primary"
          icon="el-icon-location-outline"
          @click="addTransfeeFree()"
        >{{ $t("delivery.transport.clickToAddTheSpecifiedShippingConditions") }}</el-button>
      </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">{{
        $t("table.cancel")
      }}</el-button>
      <el-button type="primary" @click="dataFormSubmit()">{{
        $t("table.confirm")
      }}</el-button>
    </span>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update
      v-if="addOrUpdateVisible"
      ref="addOrUpdate"
      @refreshDataList="getDataList"
    />
  </el-dialog>
</template>

<script>
import { areaNameList } from '@/utils/addr'
import AddOrUpdate from './transcity-add-or-update'
import * as api from '@/api/delivery/transport'
import Big from 'big.js'
export default {
  components: {
    AddOrUpdate
  },
  data() {
    return {
      hasFreeCondition: 0,
      visible: false,
      addOrUpdateVisible: false,
      dataForm: {
        hasFreeCondition: false,
        transName: '',
        createTime: '',
        chargeType: 0,
        transportId: 0,
        isFreeFee: 0,
        transFees: [{ cityList: [], addrNameList: [], status: 1 }],
        transFeeFrees: [{ freeCityList: [], freeAddrNameList: [] }]
      },
      areaList: [],
      areaListDB: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10 // 每页显示多少条
      },
      editVisible: false,
      transfeesBin: [], // 可配送区域运费，禁止选中列表
      transfeeFreesBin: [] // 指定条件包邮，禁止选中列表
    }
  },
  computed: {
    tableTitle() {
      var titles = [
        [
          this.$i18n.t('delivery.transport.firstPiece'),
          this.$i18n.t('delivery.transport.transportationCost'),
          this.$i18n.t('delivery.transport.continuationPiece'),
          this.$i18n.t('delivery.transport.continuationCost')
        ],
        [
          this.$i18n.t('delivery.transport.firstWeight'),
          this.$i18n.t('delivery.transport.transportationCost'),
          this.$i18n.t('delivery.transport.continuedWeight'),
          this.$i18n.t('delivery.transport.continuationCost')
        ],
        [
          this.$i18n.t('delivery.transport.firstVolume'),
          this.$i18n.t('delivery.transport.transportationCost'),
          this.$i18n.t('delivery.transport.continuedVolume'),
          this.$i18n.t('delivery.transport.continuationCost')
        ]
      ]
      if (this.dataForm.chargeType) {
        return titles[this.dataForm.chargeType]
      }
      return titles[0]
    }
  },
  watch: {
    // 如果当前对话框不可见，则关闭选择城市的对话框
    visible: function(val) {
      if (!val) {
        this.addOrUpdateVisible = false
      }
    }
  },
  methods: {
    init(id) {
      this.visible = true
      this.dataForm.transportId = id || 0
      this.$nextTick(() => {
        this.$refs.dataForm.resetFields()
        this.dataForm = {
          hasFreeCondition: false,
          transName: '',
          createTime: '',
          chargeType: 0,
          transportId: 0,
          isFreeFee: 0,
          transFees: [{ cityList: [], addrNameList: [], status: 1 }],
          transFeeFrees: [{ freeCityList: [], freeAddrNameList: [] }]
        }
      })
      this.transfeesBin = []
      this.transfeeFreesBin = []
      this.getTransport()
    },
    /**
     * 校验输入的数字
     */
    checkNumber(row, type) {
      if (type === 1) {
        row.firstPiece = this.getNumber(row.firstPiece)
        row.firstPiece = row.firstPiece === 0 ? 1 : row.firstPiece
      } else if (type === 3) {
        row.continuousPiece = this.getNumber(row.continuousPiece)
        row.continuousPiece = row.continuousPiece === 0 ? 1 : row.continuousPiece
      } else if (type === 5) {
        row.amount = this.checkAmount(row.amount)
      } else if (type === 6) {
        row.piece = this.getNumber(row.piece)
        row.piece = row.piece === 0 ? 1 : row.piece
      }
    },
    /**
     * 保留整数并小于零的数设为0
     */
    getNumber(num) {
      num = Math.round(num)
      return num < 0 ? 0 : num
    },
    checkAmount(data) {
      const reg = /^[1-9]\d*$|^[1-9]\d*\.\d\d?$|^0\.\d\d?$/
      let num = data
      if (!reg.test(data)) {
        num = Math.round(data * 100) / 100
      }
      if (num < 0) {
        num = 1
      }
      return num
    },
    getTransport() {
      if (this.dataForm.transportId) {
        api.get(this.dataForm.transportId).then(data => {
          // 获取运费模板数据
          this.getAreaListInfo()
          if (data.isFreeFee) {
            data.transFees[0].status = 0
          } else {
            data.transFees[0].status = 1
          }
          this.dataForm = data
          this.dataForm.transFees.forEach(transFee => {
            const continuousFeeActual = new Big(transFee.continuousFee)
            const firstFeeActual = new Big(transFee.firstFee)
            transFee.continuousFee = continuousFeeActual.div(100)
            transFee.firstFee = firstFeeActual.div(100)
          })
          this.dataForm.hasFreeCondition = !!data.hasFreeCondition
          this.handleBinAreas()
        })
      } else {
        this.getAreaListInfo()
        this.handleBinAreas()
      }
    },
    getDataList(row, cityList, type) {
      if (type === 0) {
        this.dataForm.transFees[row].cityList = cityList
        const addrNameList = this.setAddrInfoByAreaList(cityList)
        this.$set(this.dataForm.transFees[row], 'addrNameList', addrNameList)
      }
      if (type === 1) {
        this.dataForm.transFeeFrees[row].freeCityList = cityList
        const addrNameList = this.setAddrInfoByAreaList(cityList)
        this.$set(this.dataForm.transFeeFrees[row], 'freeAddrNameList', addrNameList)
      }
      this.handleBinAreas()
    },
    /**
     * 获取省市区信息
     */
    getAreaListInfo() {
      // this.$http({
      //   url: this.$http.adornUrl('/admin/area/areaListInfo'),
      //   method: 'get'
      // }).then(({ data }) => {
      //   this.areaListDB = data
      //   this.areaList = JSON.parse(JSON.stringify(data))
      //   if (this.dataForm.transFees.length > 0) {
      //     this.getAreaListByType(0)
      //   }
      //   if (this.dataForm.transFeeFrees.length > 0) {
      //     this.getAreaListByType(1)
      //   }
      // })
      api.listAreaInfo().then(data => {
        this.areaListDB = data
        this.areaList = JSON.parse(JSON.stringify(data))
        if (this.dataForm.transFees.length > 0) {
          this.getAreaListByType(0)
        }
        if (this.dataForm.transFeeFrees.length > 0) {
          this.getAreaListByType(1)
        }
      })
      // .catch((e) => this.areaListDB = e)
    },
    getAreaListByType(type) {
      if (type === 0) {
        for (var t = 0; t < this.dataForm.transFees.length; t++) {
          const addrNameList = this.setAddrInfoByAreaList(this.dataForm.transFees[t].cityList)
          this.$set(this.dataForm.transFees[t], 'addrNameList', addrNameList)
        }
      }
      if (type === 1) {
        for (var f = 0; f < this.dataForm.transFeeFrees.length; f++) {
          const addrNameList = this.setAddrInfoByAreaList(this.dataForm.transFeeFrees[f].freeCityList)
          this.$set(this.dataForm.transFeeFrees[f], 'freeAddrNameList', addrNameList)
        }
      }
    },
    /**
     * 将运费模板中的地址存入地址列表中
     */
    setAddrInfoByAreaList(cityList) {
      var areaList = []
      areaList = JSON.parse(JSON.stringify(this.areaListDB))
      const addrNameList = areaNameList(areaList, cityList)
      return addrNameList
    },
    // 添加运费项
    addTransfee() {
      this.editVisible = true
      this.dataForm.transFees.push({ cityList: [], status: 1 })
    },
    // 删除运费项
    deleteHandle(rowIndex) {
      this.dataForm.transFees.splice(rowIndex, 1)
      this.handleBinAreas()
    },
    // 可配送区域和运费编辑
    addOrUpdateHandle(rowIndex) {
      this.addOrUpdateVisible = true
      let allSelectCityList = []
      for (let i = 1; i < this.dataForm.transFees.length; i++) {
        const cityList = this.dataForm.transFees[i].cityList
        allSelectCityList = allSelectCityList.concat(cityList)
      }
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(rowIndex, this.dataForm.transFees[rowIndex].cityList || [], allSelectCityList, 0, this.transfeesBin)
      })
    },
    // 添加指定包邮条件
    addTransfeeFree() {
      this.dataForm.transFeeFrees.push({ freeCityList: [] })
    },
    // 删除指定包邮条件
    deleteTransfeeFree(rowIndex) {
      this.dataForm.transFeeFrees.splice(rowIndex, 1)
      this.handleBinAreas()
    },
    // 指定包邮条件编辑
    addOrUpdateTransfeeFree(rowIndex) {
      this.addOrUpdateVisible = true
      let allSelectCityList = []
      for (let i = 1; i < this.dataForm.transFeeFrees.length; i++) {
        const freeCityList = this.dataForm.transFeeFrees[i].freeCityList
        allSelectCityList = allSelectCityList.concat(freeCityList)
      }
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(rowIndex, this.dataForm.transFeeFrees[rowIndex].freeCityList || [], allSelectCityList, 1, this.transfeeFreesBin)
      })
    },
    // 改变模板类型， 0 买家承担运费 1 卖家包邮
    changeFreeFee(val) {
      this.dataForm.hasFreeCondition = false
      if (val) {
        this.dataForm.chargeType = 0
        this.dataForm.transFees = [{ cityList: [], status: 0, firstPiece: 1, firstFee: 0, continuousPiece: 1, continuousFee: 0 }]
      } else {
        this.dataForm.transFees = [{ cityList: [], status: 1 }]
      }
    },
    /**
     * 验证指定区域是否设置包邮条件
     */
    checkTransfeeFrees() {
      let isTrue = true
      if (this.dataForm.transFeeFrees.length > 0) {
        this.dataForm.transFeeFrees.forEach(element => {
          if (!element.freeType && element.freeType !== 0) {
            isTrue = false
          }
        })
      }
      return isTrue
    },
    // 设置不可选中
    handleBinAreas() {
      this.transfeesBin = []
      this.transfeeFreesBin = []
      var transFees = this.dataForm.transFees
      var transFeeFrees = this.dataForm.transFeeFrees
      if (transFees.length > 0) {
        transFees.forEach(i => {
          if (i.cityList.length > 0) {
            i.cityList.forEach(j => {
              if (!this.judgeArrHaving(this.transfeesBin, j)) {
                this.transfeesBin.push(j.areaId)
              }
            })
          }
        })
      }
      if (transFeeFrees.length > 0) {
        transFeeFrees.forEach(i => {
          if (i.freeCityList.length > 0) {
            i.freeCityList.forEach(j => {
              if (!this.judgeArrHaving(this.transfeeFreesBin, j)) {
                this.transfeeFreesBin.push(j.areaId)
              }
            })
          }
        })
      }
    },
    judgeArrHaving(arr, data) {
      var isExist = false
      if (arr.length < 1) {
        return isExist
      }
      if (arr.includes(data.areaId)) {
        isExist = true
      }
      // arr.forEach(item => {
      //   if (item === data.areaId) {
      //     isExist = true
      //   }
      // })
      return isExist
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.dataForm.transFees.forEach(transFee => {
            const continuousFeeActual = new Big(transFee.continuousFee)
            const firstFeeActual = new Big(transFee.firstFee)
            transFee.continuousFee = continuousFeeActual.times(100)
            transFee.firstFee = firstFeeActual.times(100)
          })
          for (let i = 1; i < this.dataForm.transFees.length; i++) {
            const transfee = this.dataForm.transFees[i]
            if (transfee.cityList.length === 0) {
              this.$message({
                message: this.$i18n.t('delivery.transport.pleaseSelectADeliveryArea'),
                type: 'error',
                duration: 1500
              })
              return
            }
          }
          if (this.dataForm.isFreeFee === 0 && this.dataForm.hasFreeCondition && !this.checkTransfeeFrees()) {
            this.$message.error(this.$i18n.t('delivery.transport.setRegionalConditions'))
            return
          }
          if (this.dataForm.hasFreeCondition) {
            this.hasFreeCondition = 1
          } else {
            this.hasFreeCondition = 0
          }
          this.dataForm.hasFreeCondition = this.hasFreeCondition
          this.dataForm.transFees[0].cityList = []
          const request = this.dataForm.transportId ? api.update(this.dataForm) : api.save(this.dataForm)
          request.then(data => {
            this.$message({
              message: this.$t('table.actionSuccess'),
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.visible = false
                this.$emit('refreshDataList')
                this.$refs['dataForm'].resetFields()
              }
            })
          })
          // this.$http({
          //   url: this.$http.adornUrl(`/shop/transport`),
          //   method: this.dataForm.transportId ? 'put' : 'post',
          //   data: this.$http.adornData({
          //     'transportId': this.dataForm.transportId || undefined,
          //     'transName': this.dataForm.transName,
          //     'chargeType': this.dataForm.chargeType,
          //     'isFreeFee': this.dataForm.isFreeFee,
          //     'transFees': this.dataForm.transFees,
          //     'transFeeFrees': this.dataForm.transFeeFrees,
          //     'hasFreeCondition': this.hasFreeCondition
          //   })
          // }).then(({ data }) => {
          //   this.$message({
          //     message: this.$i18n.t('table.actionSuccess'),
          //     type: 'success',
          //     duration: 1500,
          //     onClose: () => {
          //       this.visible = false
          //       this.$emit('refreshDataList', this.page)
          //     }
          //   })
          // })
        }
      })
    }
  }
}
</script>

<style lang="scss">
.is-error .el-input-number__decrease,
.is-error .el-input-number__increase,
.is-success .el-input-number__decrease,
.is-success .el-input-number__increase {
  right: 0 !important;
}

.el-input-number__decrease,
.el-input-number__increase {
  border-left: 1px solid #edf3ff !important;
}
// .el-form-item__content {
//   min-width: 1400px;
// }

.el-input-number {
  width: 160px !important;
}

.el-form-item.is-error .el-input__inner,
.el-form-item.is-error .el-input__inner:focus,
.el-form-item.is-error .el-textarea__inner,
.el-form-item.is-error .el-textarea__inner:focus,
.el-message-box__input input.invalid,
.el-message-box__input input.invalid:focus {
  border-color: #dcdfe6;
}
</style>
