<template>
  <el-dialog
    v-model="visible"
    title="商品选择"
    :modal="false"
    top="4vh"
    :close-on-click-modal="false"
    width="820px"
    @close="closeModule"
  >
    <el-form
      :inline="true"
      :model="dataForm"
      class="demo-form-inline"
    >
      <el-form-item label="商品名称">
        <el-input
          v-model.trim="dataForm.name"
          placeholder="商品名称"
          clearable
        />
      </el-form-item>
      <el-form-item label="上级分类">
        <el-cascader
          v-model="selectedCategory"
          expand-trigger="hover"
          :options="categoryList"
          :props="categoryTreeProps"
          :clearable="true"
          @change="handleChange"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="searchProd"
        >
          查询
        </el-button>
        <el-button
          @click="clean"
        >
          清空
        </el-button>
      </el-form-item>
    </el-form>
    <div class="prods-select-body">
      <el-table
        ref="prodTableRef"
        v-loading="dataListLoading"
        :data="pageVO.list"
        border
        highlight-current-row
        style="width: 100%"
        @selection-change="selectChangeHandle"
      >
        <el-table-column
          v-if="isSingle"
          width="50"
          header-align="center"
          align="center"
        >
          <template #default="{row}">
            <div>
              <el-radio
                v-model="singleSelectspuId"
                :label="row.spuId"
                @change="getSelectProdRow(row)"
              >
&nbsp;
              </el-radio>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          v-if="!isSingle"
          type="selection"
          header-align="center"
          align="center"
          width="50"
        />
        <el-table-column
          align="center"
          width="140"
          label="商品图片"
        >
          <template #default="{row}">
            <img
              :src="row.mainImgUrl"
              width="100"
              height="100"
            >
          </template>
        </el-table-column>
        <el-table-column
          prop="spuName"
          header-align="center"
          align="center"
          label="商品名称"
        />
        <el-table-column
          prop="priceFee"
          header-align="center"
          align="center"
          label="商品价格"
          width="200px"
        />
      </el-table>
      <!-- 分页条 -->
      <pagination
        v-show="pageVO.total>0"
        v-model:page="pageQuery.pageNum"
        v-model:limit="pageQuery.pageSize"
        :total="pageVO.total"
        @pagination="getDataList()"
      />
    </div>
    <template #footer>
      <span>
        <el-button @click="visible = false">取消</el-button>
        <el-button
          type="primary"
          @click="submitProds()"
        >确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import Big from 'big.js'
import { page } from '@/api/product/list'
import { categoryPage } from '@/api/product/category'
import { onMounted, reactive } from 'vue'

const emit = defineEmits(['refreshSelectProds'])

defineProps({
  isSingle: {
    default: false,
    type: Boolean
  },
  prodType: {
    default: null,
    type: Number
  },
  dataUrl: {
    default: '/prod/prod/page',
    type: String
  }
})

const Data = reactive({
  visible: false,
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  // 查询的参数
  pageQuery: {
    pageSize: 10,
    pageNum: 1,
    spuStatus: 1
  },
  // 返回参数
  pageVO: {
    list: [], // 返回的列表
    total: 0, // 一共多少条数据
    pages: 0 // 一共多少页
  },
  // 查询参数
  searchParam: {
  },

  dataForm: {
    name: '',
    product: ''
  },
  singleSelectspuId: 0,
  allData: [],
  selectProds: [],
  shopCategoryId: null,
  pageIndex: 1,
  pageSize: 10,
  totalPage: 0,
  dataListLoading: false,
  addOrUpdateVisible: false,
  dataListSelections: [],
  categoryList: [],
  selectedCategory: [],
  categoryTreeProps: {
    value: 'categoryId',
    label: 'name'
  }
})

const { visible, pageQuery, pageVO, dataForm, singleSelectspuId, dataListLoading, categoryList, selectedCategory, categoryTreeProps } = toRefs(Data)

onMounted(() => {
  getDataList()
})

// 获取数据列表
const init = (selectProds) => {
  Data.singleSelectspuId = 0
  Data.selectProds = selectProds
  Data.visible = true
  Data.dataListLoading = true
  clean()
  if (Data.selectProds) {
    Data.selectProds.forEach(row => {
      Data.dataListSelections.push(row)
    })
  }
  getDataList()
  getCategoryList()
}

const getCategoryList = () => {
  categoryPage().then((data) => {
    Data.categoryList = treeDataTranslate(data, 'categoryId', 'parentId')
  })
}

const prodTableRef = ref()
const getDataList = () => {
  page({ ...Data.pageQuery, ...Data.searchParam }).then((pageVO) => {
    Data.pageVO = pageVO
    Data.pageVO.list.forEach(prod => {
      prod.priceFee = new Big(prod.priceFee).div(100).toFixed(2)
    })
    Data.dataListLoading = false
    if (Data.selectProds) {
      nextTick(() => {
        Data.selectProds.forEach(row => {
          const index = Data.pageVO.list.findIndex((prodItem) => prodItem.spuId === row.spuId)
          prodTableRef.value.toggleRowSelection(Data.pageVO.list[index])
        })
      })
    }
  })
}

// 单选商品事件
const getSelectProdRow = (row) => {
  Data.dataListSelections = [row]
}

// 多选点击事件
const selectChangeHandle = (selection) => {
  Data.pageVO.list.forEach((tableItem) => {
    const selectedProdIndex = selection.findIndex((selectedProd) => {
      if (!selectedProd) {
        return false
      }
      return selectedProd.spuId === tableItem.spuId
    })
    const dataSelectedProdIndex = Data.dataListSelections.findIndex((dataSelectedProd) => dataSelectedProd.spuId === tableItem.spuId)
    if (selectedProdIndex > -1 && dataSelectedProdIndex === -1) {
      Data.dataListSelections.push(tableItem)
    } else if (selectedProdIndex === -1 && dataSelectedProdIndex > -1) {
      Data.dataListSelections.splice(dataSelectedProdIndex, 1)
    }
  })
}

/**
     * 获取分类id
     */
const handleChange = (val) => {
  Data.shopCategoryId = val[val.length - 1]
}

/**
     * 根据条件搜索商品
     */
const searchProd = () => {
  Data.pageQuery.pageNum = 1
  Data.searchParam = {
    name: Data.dataForm.name,
    categoryId: Data.shopCategoryId
  }
  getDataList()
}

/**
     * 清空搜索条件
     */
const clean = () => {
  Data.name = ''
  Data.shopCategoryId = null
  Data.selectedCategory = idList(Data.categoryList, Data.shopCategoryId, 'categoryId', 'children').reverse()
}

const closeModule = () => {
  Data.name = ''
  Data.shopCategoryId = null
}

// 确定事件
const submitProds = () => {
  const prods = []
  Data.dataListSelections.forEach(item => {
    const prodIndex = prods.findIndex((prod) => prod.spuId === item.spuId)
    if (prodIndex === -1) {
      prods.push(
        {
          spuId: item.spuId,
          name: item.spuName,
          imgUrls: item.imgUrls,
          mainImgUrl: item.mainImgUrl,
          activityId: item.activityId,
          prodType: item.prodType
        }
      )
    }
  })
  emit('refreshSelectProds', prods)
  Data.dataListSelections = []
  Data.visible = false
}

defineExpose({
  init
})

</script>

<style lang="scss" scoped>
.demo-form-inline {
  margin-bottom: 22px;

  :deep(.el-input){
    width: 200px;
  }
}
.prods-select-body {
  height: 600px;
  overflow: auto;
  border-top: 1px solid #eeeeee;
  border-right: 1px solid #eeeeee;
  border-bottom: 1px solid #eeeeee;
}
</style>
