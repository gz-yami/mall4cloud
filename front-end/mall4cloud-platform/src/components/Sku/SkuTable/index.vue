<template>
  <div class="component-sku-table">
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
          <span>{{ scope.row.stock }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="marketPriceFee"
        label="市场价(元)"
        class="tab-int"
      >
        <template #default="scope">
          <span>{{ scope.row.marketPriceFee }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="priceFee"
        label="销售价(元)"
        class="tab-int"
      >
        <template #default="scope">
          <span>{{ scope.row.priceFee }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="barCode"
        label="商品条形码"
        class="tab-int"
      >
        <template #default="scope">
          <span>{{ scope.row.modelId }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="skuCode"
        label="商品编码"
        class="tab-int"
      >
        <template #default="scope">
          <span>{{ scope.row.partyCode }}</span>
        </template>
      </el-table-column>
      <el-table-column
        v-if="spuId && !isNoSkuValue"
        prop="skuCode"
        label="sku状态"
      >
        <template #default="scope">
          <!-- status状态 1:enable, 0:disable, -1:deleted -->
          <el-tag :type="scope.row.status === 0 ? 'danger' : ''">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { computed, reactive, watch } from 'vue'
import { flatten as genFlatten } from '@/utils'

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
  originList: [],
  firstSkuVal: -1,
  secondSkuVal: -1,
  stockIntVal: '', // 库存
  markedPriceIntVal: '',
  priceIntVal: '',
  barCodeIntVal: '', // 条形码
  skuCodeIntVal: ''
})

const { lists } = toRefs(Data)

const filter = computed(() => {
  return props.data.filter(item => item.text && item.leaf.length)
})

const columns = computed(() => {
  return filter.value.map(item => item[props.optionText])
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

watch(filter.value, () => {
  const lists = genLists(filter.value, props.flatten)
  Data.lists = lists
  computeRowspan()
}, {
  deep: true,
  immediate: true
})

watch(props.flatten, () => {
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
})

watch(() => Data.lists, (data) => {
  emit('on-change-data', data)
}, {
  deep: true,
  immediate: true
})

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

</script>

<!-- eslint-disable-next-line vue-scoped-css/enforce-style-type -->
<style lang="scss">
.component-sku-table {
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

    .el-input-number.is-controls-right[class*="mini"] [class*="increase"],
    .el-input-number.is-controls-right[class*="mini"] [class*="decrease"] {
      display: none;
    }
  }

  .el-table .cell {
    // padding: 0;
    text-align: center;
  }

  // .el-table--border th:first-child .cell,
  // .el-table--border td:first-child .cell {
  //   padding-left: 0;
  // }
  .el-input-number.is-controls-right .el-input__inner {
    text-align: center;
  }

  .el-table--mini td {
    padding: 12px 0;
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

  .el-table--enable-row-hover .el-table__body tr:hover>td {
    background: transparent;
  }
}
</style>
