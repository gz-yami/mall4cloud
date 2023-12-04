<template>
  <div class="component-sku-table">
    <!-- 批量设置 -->
    <div
      v-if="lists[0] && lists[0].spuSkuAttrValues"
      class="batch-settings"
    >
      <div class="batch">
        <div>
          <span class="bat-tit">批量设置</span>
          <span class="set-tips">在下方栏中选择内容进行批量填充</span>
        </div>
        <el-button
          plain
          class="set"
          @click="setNow"
        >
          立即设置
        </el-button>
      </div>
      <div class="bat-set-sel">
        <el-select
          v-if="firstSkuValOptions.length>0"
          v-model="firstSkuVal"
          class="bat-set-item"
          placeholder="请选择"
          style="width:90px"
        >
          <el-option
            v-for="item in firstSkuValOptions"
            :key="item.id"
            :label="item.text"
            :value="item.id"
          />
        </el-select>
        <el-select
          v-if="secondSkuValOptions.length>0"
          v-model="secondSkuVal"
          class="bat-set-item"
          placeholder="全部"
          style="width:90px"
        >
          <el-option
            v-for="item in secondSkuValOptions"
            :key="item.id"
            :label="item.text"
            :value="item.id"
          />
        </el-select>
        <!-- 库存 -->
        <el-input
          v-model.number="stockIntVal"
          class="bat-set-item"
          :mini="0 "
          oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
          placeholder="库存"
          style="width:90px"
        />
        <!-- 市场价 -->
        <el-input-number
          v-model.number="markedPriceIntVal"
          class="bat-set-item"
          type="number"
          controls-position="right"
          :precision="2"
          :step="0.1"
          :min="0"
          :max="1000000000"
          placeholder="市场价"
          style="width:90px"
        />
        <!-- 销售价 -->
        <el-input-number
          v-model.number="priceIntVal"
          class="bat-set-item"
          type="number"
          controls-position="right"
          :precision="2"
          :step="0.1"
          :min="0.01"
          :max="1000000000"
          placeholder="销售价"
          style="width:90px"
        />
        <!-- 商品条形码 -->
        <el-input
          v-model.number="barCodeIntVal"
          class="bat-set-item"
          oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
          placeholder="商品条形码"
          style="width:90px"
        />
        <!-- 商品编码 -->
        <el-input
          v-model.number="skuCodeIntVal"
          class="bat-set-item"
          oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
          placeholder="商品编码"
          style="width:90px"
        />
      </div>
    </div>

    <!-- 表格 -->
    <el-table
      border
      :data="lists"
      :span-method="handleSpanMethod"
      class="tab-con"
    >
      <template v-for="(label, index) in columns">
        <!-- 为什么要判断label: 动态添加规格名的时候规格名不为undefiend时未动态显示, 没有看table-column实现暂时这么解决  -->
        <el-table-column
          v-if="label"
          :key="index"
          :label="label"
        >
          <template #default="scope">
            {{ scope.row.spuSkuAttrValues[index] && scope.row.spuSkuAttrValues[index].attrValueName }}
          </template>
        </el-table-column>
      </template>
      <el-table-column
        prop="stock"
        label="库存"
        class="tab-int"
      >
        <template #default="scope">
          <el-input-number
            v-model.number="scope.row.stock"
            :min="0"
            :disabled="scope.row.status===0"
            controls-position="right"
            class="tab-int"
            @blur="stockValidAndChange(scope)"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="marketPriceFee"
        label="市场价(元)"
        class="tab-int"
      >
        <template #default="scope">
          <el-input-number
            v-model="scope.row.marketPriceFee"
            :min="0"
            :max="1000000000"
            :disabled="scope.row.status===0"
            controls-position="right"
            :precision="2"
            class="tab-int"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="priceFee"
        label="销售价(元)"
        class="tab-int"
      >
        <template #default="scope">
          <el-input-number
            v-model="scope.row.priceFee"
            :min="0.01"
            :max="1000000000"
            :disabled="scope.row.status===0"
            controls-position="right"
            :precision="2"
            class="tab-int"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="barCode"
        label="商品条形码"
        class="tab-int"
      >
        <template #default="scope">
          <el-input
            v-model.number="scope.row.modelId"
            :min="0"
            :disabled="scope.row.status===0"
            oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
            class="tab-int"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="skuCode"
        label="商品编码"
        class="tab-int"
      >
        <template #default="scope">
          <el-input
            v-model.number="scope.row.partyCode"
            :min="0"
            :disabled="scope.row.status===0"
            oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
            class="tab-int"
          />
        </template>
      </el-table-column>
      <el-table-column
        v-if="spuId && !isNoSkuValue"
        prop="skuCode"
        label="sku状态"
      >
        <template #default="scope">
          <el-button
            type="text"
            @click="skuStatusOperation(scope)"
          >
            {{ scope.row.status === 1 ? '禁用' : '启用' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { flatten as genFlatten } from '@/utils'
import { computed, reactive, watch } from 'vue'

const emit = defineEmits(['on-change-data'])

const props = defineProps({
  data: {
    type: Array,
    default () {
      return []
    }
  },
  // 需要附加的字段
  flatten: {
    type: Array,
    default () {
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
})

const Data = reactive({
  rowspan: [],
  lists: [],
  firstSkuVal: -1,
  secondSkuVal: -1,
  stockIntVal: '', // 库存
  markedPriceIntVal: '',
  priceIntVal: '',
  barCodeIntVal: '', // 条形码
  skuCodeIntVal: '',
  originList: []
})
const { lists, firstSkuVal, secondSkuVal, stockIntVal, markedPriceIntVal, priceIntVal, barCodeIntVal, skuCodeIntVal } = toRefs(Data)

const filter = computed(() => {
  return props.data.filter(item => item.text && item.leaf.length)
})
const columns = computed(() => {
  return filter.value.map(item => item[props.optionText])
})

const firstSkuValOptions = computed(() => {
  if (props.data[0]) {
    if (props.data[0].leaf) {
      return [{ id: -1, is_show: true, text: '全部' }, ...props.data[0].leaf]
    }
  }
  return []
})

const secondSkuValOptions = computed(() => {
  if (props.data[1]) {
    if (props.data[1].leaf) {
      return [{ id: -1, is_show: true, text: '全部' }, ...props.data[1].leaf]
    }
  }
  return []
})

const genLists = (filter, flatten) => {
  const baseData = {
    stock: 0, // 库存
    marketPriceFee: '', // 市场价
    priceFee: 0.01, // 销售价
    partyCode: '', // 商品编码
    modelId: '' // 条形码
  }
  if (filter.length && genFlatten(filter, flatten).length) {
    return genFlatten(filter, flatten, { extraData: baseData })
  } else {
    return [baseData]
  }
}

const computeRowspan = () => {
  Data.rowspan = []
  const rowspan = (index) => {
    const span = []
    let dot = 0
    Data.lists.forEach((item, idx) => {
      if (idx === 0) {
        span.push(1)
      } else {
        if (item.spuSkuAttrValues?.[index].attrValueName === Data.lists[idx - 1].spuSkuAttrValues?.[index].attrValueName) {
          span[dot] += 1
          span.push(0)
        } else {
          dot = idx
          span.push(1)
        }
      }
    })

    Data.rowspan.push(span)
  }

  filter.value.forEach((item, index) => {
    rowspan(index)
  })
}

const handleSpanMethod = ({ row, column, rowIndex, columnIndex }) => {
  for (let i = 0; i < filter.value.length; i++) {
    if (columnIndex === i) {
      if (Data.rowspan[i] && Data.rowspan[i][rowIndex]) {
        return {
          rowspan: Data.rowspan[i][rowIndex],
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
}

// 库存修改与验证
const stockValidAndChange = (scope) => {
  const { $index, row } = scope
  const originStock = Data.originList?.[$index]?.stock
  if (!Data.lists[$index].stock) {
    Data.lists[$index].stock = 0
  }
  if (originStock !== undefined) {
    if (originStock > row.stock) {
      // 用户输入错误
      // row.stock = originStock;
      Data.lists[$index].stock = originStock
      ElMessage({
        message: '输入库存不得小于原有库存',
        duration: 1000
      })
      return
    }
    Data.lists[$index].changeStock = parseInt(row.stock) - parseInt(originStock)
  }
}

// 立即设置
const setNow = () => {
  const setItem = (item, index) => {
    item.marketPriceFee = Data.markedPriceIntVal >= 0 ? Data.markedPriceIntVal : item.marketPriceFee || 0
    item.priceFee = Data.priceIntVal || item.priceFee || 0.01 // 销售价
    item.partyCode = Data.skuCodeIntVal || item.partyCode || '' // 商品编码
    item.modelId = Data.barCodeIntVal || item.modelId || '' // 商品条形码
    // 回显 && 原库存 > 0
    if (props.spuId && item.stock >= 0) {
      // 输入库存 是否>=0 且 是否>=原有库存
      if (Data.stockIntVal >= item.stock) {
        item.stock = Data.stockIntVal
        Data.lists[index].changeStock = Data.stockIntVal - parseInt(Data.originList[index].stock)// 改变的库存数量(新增-原有)
      }
    } else {
      item.stock = Data.stockIntVal >= 0 ? Data.stockIntVal : item.stock || 0
    }
    return item
  }
  const vaildSkuValArr = [Data.firstSkuVal, Data.secondSkuVal]
  Data.lists.forEach((item, index) => {
    const { spuSkuAttrValues } = item
    // secondSkuVal
    if (
      spuSkuAttrValues.every((attr, idx) => vaildSkuValArr[idx] === -1 || attr.attrValueId === vaildSkuValArr[idx])
    ) {
      setItem(item, index)
    }
  })
}

/**
     * sku状态
     */
const skuStatusOperation = (scope) => {
  const currentStatus = Data.lists?.[scope.$index]?.status
  const newStatus = currentStatus === 0 ? 1 : 0
  Data.lists[scope.$index].status = newStatus
}

watch(() => filter.value, () => {
  const lists = genLists(filter.value, props.flatten)
  Data.lists = lists
  computeRowspan()
}, {
  deep: true,
  immediate: true
})

watch(() => props.flatten, () => {
  Data.originList = JSON.parse(JSON.stringify(genLists(filter.value, props.flatten)))
  if (Data.lists.length === 1 && !Data.lists[0].spuSkuAttrValues) {
    props.flatten.forEach(el => {
      const baseData = {
        stock: el.stock, // 库存
        marketPriceFee: el.marketPriceFee, // 市场价
        priceFee: el.priceFee, // 销售价
        partyCode: el.partyCode, // 商品编码
        modelId: el.modelId // 条形码
      }
      Data.lists = [baseData]
    })
  }
}, {
  deep: true,
  immediate: true
})

watch(() => Data.lists, (data) => {
  emit('on-change-data', data)
}, {
  deep: true,
  immediate: true
})

</script>

<style lang="scss" scoped>
.component-sku-table {
  // 批量设置
  .batch-settings {
    margin-bottom: 15px;
    .batch {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 10px;
      .set-tips {
        font-size: 13px;
        color: #999;
        margin-left: 15px;
      }
    }
    .bat-set-item {
      margin-right: 10px;
    }
    .el-input-number.is-controls-right[class*=mini] [class*=increase],
    .el-input-number.is-controls-right[class*=mini] [class*=decrease] {
      display: none;
    }
  }
}
</style>
