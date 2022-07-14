<template>
  <div class="ease-sku-table">
    <!-- 批量设置 -->
    <div class="batch-settings" v-if="lists[0] && lists[0].spuSkuAttrValues">
      <div class="batch">
        <div>
          <span class="bat-tit">批量设置</span>
          <span class="set-tips">在下方栏中选择内容进行批量填充</span>
        </div>
        <el-button size="mini" plain @click="setNow" class="set">立即设置</el-button>
      </div>
      <div class="bat-set-sel">
        <el-select v-if="firstSkuValOptions.length>0" v-model="firstSkuVal" size="mini" class="bat-set-item" placeholder="请选择" style="width:90px">
          <el-option
            v-for="item in firstSkuValOptions"
            :key="item.id"
            :label="item.text"
            :value="item.id"
          />
        </el-select>
        <el-select v-if="secondSkuValOptions.length>0" v-model="secondSkuVal" size="mini" class="bat-set-item" placeholder="全部" style="width:90px">
          <el-option
            v-for="item in secondSkuValOptions"
            :key="item.id"
            :label="item.text"
            :value="item.id"
          />
        </el-select>
        <!-- 库存 -->
        <el-input class="bat-set-item" size="mini" v-model.number="stockIntVal" :mini="0 "
          oninput="this.value=this.value.replace(/^\.+|[^\d.]/g,'')"
          placeholder="库存" style="width:90px"
        />
        <!-- 市场价 -->
        <el-input-number 
          class="bat-set-item"
          type='number'
          controls-position="right"
          :precision="2" :step="0.1"
          :min="0" :max="1000000000"
          size="mini"
          v-model.number="markedPriceIntVal"
          placeholder="市场价"
          style="width:90px"
        />
        <!-- 销售价 -->
        <el-input-number
          class="bat-set-item"
          type='number'
          controls-position="right"
          :precision="2" :step="0.1" 
          :min="0.01" :max="1000000000"
          size="mini"
          v-model.number="priceIntVal"
          placeholder="销售价"
          style="width:90px"
        />
        <!-- 商品条形码 -->
        <el-input class="bat-set-item" size="mini" v-model.number="barCodeIntVal"
          oninput="this.value=this.value.replace(/^\.+|[^\d.]/g,'')"
          placeholder="商品条形码" style="width:90px"
        />
        <!-- 商品编码 -->
        <el-input class="bat-set-item" size="mini" v-model.number="skuCodeIntVal"
          oninput="this.value=this.value.replace(/^\.+|[^\d.]/g,'')"
          placeholder="商品编码" style="width:90px" />
      </div>
    </div>

    <!-- 表格 -->
    <el-table
      border
      size="mini"
      :data="lists"
      :span-method="handleSpanMethod"
      class="tab-con"
    >
      <template v-for="(label, index) in columns">
        <!-- 为什么要判断label: 动态添加规格名的时候规格名不为undefiend时未动态显示, 没有看table-column实现暂时这么解决  -->
        <el-table-column
          v-if="label"
          :label="label"
          :key="index">
          <template slot-scope="scope">
            {{scope.row.spuSkuAttrValues[index] && scope.row.spuSkuAttrValues[index].attrValueName}}
          </template>
        </el-table-column>
      </template>
      <el-table-column
        prop="stock"
        label="库存"
        class="tab-int"
        >
        <template slot-scope="scope">
          <el-input-number :min="0" @blur="stockValidAndChange(scope)" v-model.number="scope.row.stock" :disabled="scope.row.status===0" controls-position="right" class="tab-int"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column
        prop="marketPriceFee"
        label="市场价(元)"
        class="tab-int"
        >
        <template slot-scope="scope">
          <el-input-number :min="0" :max="1000000000" v-model="scope.row.marketPriceFee" :disabled="scope.row.status===0" controls-position="right" :precision="2" class="tab-int"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column
        prop="priceFee" 
        label="销售价(元)"
        class="tab-int"
        >
        <template slot-scope="scope">
          <el-input-number :min="0.01" :max="1000000000" v-model="scope.row.priceFee" :disabled="scope.row.status===0" controls-position="right" :precision="2" class="tab-int"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column
        prop="barCode"
        label="商品条形码"
        class="tab-int"
        >
        <template slot-scope="scope">
          <el-input :min="0" v-model.number="scope.row.modelId" :disabled="scope.row.status===0" oninput="this.value=this.value.replace(/^\.+|[^\d.]/g,'')" class="tab-int"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        prop="skuCode"
        label="商品编码"
        class="tab-int"
        >
        <template slot-scope="scope">
          <el-input :min="0" v-model.number="scope.row.partyCode" :disabled="scope.row.status===0" oninput="this.value=this.value.replace(/^\.+|[^\d.]/g,'')" class="tab-int"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        prop="skuCode"
        label="sku状态"
        v-if="spuId && !isNoSkuValue"
      >
        <template slot-scope="scope">
         <el-button type="text" @click="skuStatusOperation(scope)">{{scope.row.status === 1 ? '禁用' : '启用' }}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { flatten as genFlatten } from '@/utils'


export default {
  name: 'sku-table',

  props: {
    data: {
      type: Array,
      default() {
        return []
      }
    },
    // 需要附加的字段
    flatten: {
      type: Array,
      default() {
        return []
      }
    },
    // 自定义sku的id key
    optionValue: {
      type: String,
      default: 'id'
    },
    // 自定义sku的text key
    optionText: {
      type: String,
      default: 'text'
    },
    spuId: {
      type: Number,
      default: null
    },
    isNoSkuValue: {
      type: Boolean,
      default: false
    }
  },
  originList: [],
  data() {
    return {
      rowspan: [],
      lists: [],
      firstSkuVal: -1,
      secondSkuVal: -1,
      stockIntVal: '', // 库存
      markedPriceIntVal: '',
      priceIntVal: '',
      barCodeIntVal: '', // 条形码
      skuCodeIntVal: '',
    }
  },

  computed: {
    filter() {
      return this.data.filter(item => item.text && item.leaf.length)
    },

    columns() {
      return this.filter.map(item => item[this.optionText])
    },

    firstSkuValOptions() {
      const { data } = this
      if (data[0]) {
        if (data[0].leaf) {
          return [{ id: -1, is_show: true, text: '全部' }, ...data[0].leaf]
        }
      } else {
        return []
      }
    },

    secondSkuValOptions() {
      const { data } = this
      if (data[1]) {
        if (data[1].leaf) {
          return [{ id: -1, is_show: true, text: '全部' }, ...data[1].leaf]
        }
      } else {
        return []
      }
    },

  },



  watch: {
    filter: {
      deep: true,
      immediate: true,
      handler() {
        const lists = this.genLists(this.filter, this.flatten)
        this.lists = lists
        this.computeRowspan()
      }
    },
    flatten() {
      const lists = this.genLists(this.filter, this.flatten)
      this.originList = JSON.parse(JSON.stringify(this.genLists(this.filter, this.flatten)))
      if(this.lists.length === 1 && !this.lists[0].spuSkuAttrValues) {
        this.flatten.forEach(el => {
          let baseData = {
            stock: el.stock, // 库存
            marketPriceFee: el.marketPriceFee, // 市场价
            priceFee: el.priceFee, // 销售价
            partyCode: el.partyCode, // 商品编码
            modelId: el.modelId, // 条形码
          }
          this.lists = [baseData]
        })
      }
    },

    lists: {
      deep: true,
      immediate: true,
      handler(data) {
        this.$emit('on-change-data', data)
      }
    }
  },

  methods: {
    genLists: (filter, flatten) => {
      let baseData = {
        stock: 0, // 库存
        marketPriceFee: '', // 市场价
        priceFee: 0.01, // 销售价
        partyCode: '', // 商品编码
        modelId: '', // 条形码
      }
      if (filter.length && genFlatten(filter, flatten).length) {
        return genFlatten(filter, flatten, { extraData: baseData })
      } else {
        return [baseData]
      }
    },

    computeRowspan() {
      this.rowspan = []
      const rowspan = (index) => {
        let span = []
        let dot = 0
        this.lists.map((item, idx) => {

          if (idx === 0) {
            span.push(1)
          } else {
            if (item.spuSkuAttrValues?.[index].attrValueName === this.lists[idx - 1].spuSkuAttrValues?.[index].attrValueName) {
              span[dot] += 1
              span.push(0)
            } else {
              dot = idx
              span.push(1)
            }
          }
        })

        this.rowspan.push(span)
      }

      this.filter.map((item, index) => {
        rowspan(index)
      })

    },

    handleSpanMethod({ row, column, rowIndex, columnIndex }) {
      for (let i = 0; i < this.filter.length; i++) {
        if (columnIndex === i) {
          if (this.rowspan[i] && this.rowspan[i][rowIndex]) {
            return {
              rowspan: this.rowspan[i][rowIndex],
              colspan: 1
            }
          } else {
            return {
              rowspan: 0,
              colspan: 0
            }
          }
        }
      }
    },

    // 库存修改与验证
    stockValidAndChange(scope) {
      const { $index, row} = scope;
      const originStock = this.originList?.[$index]?.stock;
      if (!this.lists[$index].stock) {
        this.lists[$index].stock = 0
      }
      if (originStock !== undefined) {
        if (originStock > row.stock) {
          // 用户输入错误
          // row.stock = originStock;
          this.lists[$index].stock = originStock
          this.$message({
            message: `输入库存不得小于原有库存`,
            duration: 1000
          })
          return
        }
        this.$set(this.lists[$index], 'changeStock', parseInt(row.stock) - parseInt(originStock))
      }
    },

    // 立即设置
    setNow() {
      const {
        lists, firstSkuVal = '', secondSkuVal = '', spuId,
        stockIntVal = parseInt(stockIntVal), markedPriceIntVal = parseInt(markedPriceIntVal),
        priceIntVal = parseFloat(priceIntVal), skuCodeIntVal = parseInt(skuCodeIntVal),
        barCodeIntVal = parseInt(barCodeIntVal)
      } = this
      let newItem = {}
      const setItem = (item, index) => {
        // item.stock = stockIntVal ? parseInt(stockIntVal) : item.stock || 0 // 库存，正整数
        item.marketPriceFee = markedPriceIntVal >= 0 ? markedPriceIntVal : item.marketPriceFee || 0
        item.priceFee = priceIntVal ? priceIntVal : item.priceFee || 0.01 // 销售价
        item.partyCode = skuCodeIntVal ? skuCodeIntVal : item.partyCode || '' // 商品编码
        item.modelId = barCodeIntVal ? barCodeIntVal : item.modelId || '' // 商品条形码
        // 回显 && 原库存 > 0
        if (spuId && item.stock >= 0) {
          // 输入库存 是否>=0 且 是否>=原有库存
          if (stockIntVal >= item.stock) {
            item.stock = stockIntVal
            this.$set(this.lists[index], 'changeStock', stockIntVal - parseInt(this.originList[index].stock)) // 改变的库存数量(新增-原有)
          }
        } else {
          item.stock = stockIntVal >= 0 ? stockIntVal : item.stock || 0
        }
        return item
      }
      const vaildSkuValArr = [firstSkuVal, secondSkuVal]
      lists.forEach((item, index) => {
        const { spuSkuAttrValues } = item;
        // secondSkuVal
        if (
          spuSkuAttrValues.every((attr, idx) => vaildSkuValArr[idx] === -1 || attr.attrValueId === vaildSkuValArr[idx])
        ) {
          setItem(item, index)
        }
      })
      this.lists = lists
    },

    /**
     * sku状态
     */
    skuStatusOperation(scope) {
      const { $index, row} = scope
      const currentStatus = this.lists?.[$index]?.status
      let newStatus = currentStatus === 0 ? 1 : 0
      this.lists[$index].status = newStatus
    }

  }
}
</script>

<style lang="scss">
.ease-sku-table {
  .el-table__row {
    .el-input-number {
      display: block;
      width: 100%;
    }
    .el-input-number.is-controls-right[class*="medium"] [class*="increase"],
    .el-input-number.is-controls-right[class*="medium"] [class*="decrease"] {
      display: none;
      border: 0;
      background: #fff;
    }
  }
  // 批量设置
  .batch-settings {
    margin-bottom: 15px;
    .batch {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 10px;
      .set-now {
        font-size: 14px;
        line-height: 1em;
        padding: 6px 10px;
        color: #02a1e9;
        border: 1px solid #02a1e9;
        cursor: pointer;
      }
      .set.el-button {
        border-color: #02a1e9;
        color: #02a1e9;
      }
      .set.el-button:active {
        background: #ecf5ff;
      }
      .set-tips {
        font-size: 13px;
        color: #999;
        margin-left: 15px;
      }
    }
    .bat-set-item {
      margin-right: 10px;
    }
    .el-input .el-input__inner {
      padding: 0 8px;
    }
    .el-input-number.is-controls-right .el-input__inner {
      text-align: left;
      padding: 0 8px;
    }
    .el-input-number.is-controls-right[class*=mini] [class*=increase],
    .el-input-number.is-controls-right[class*=mini] [class*=decrease] {
      display: none;
    }
  }
  .el-table .cell {
    padding: 0;
    text-align: center;
  }
  .el-table--border th:first-child .cell, .el-table--border td:first-child .cell {
    padding-left: 0;
  }
  .el-input-number.is-controls-right .el-input__inner {
    text-align: center;
  }
  .el-table--mini td {
    padding: 0;
  }
  .el-input--medium .el-input__inner {
    width: 100%;
    height: 50px;
    border: 0;
    outline: none;
    padding: 0 10px;
    font-size: 13px;
    text-align: center;
  }
  .el-table--enable-row-hover .el-table__body tr:hover > td {
    background: transparent;
  }
}
</style>
