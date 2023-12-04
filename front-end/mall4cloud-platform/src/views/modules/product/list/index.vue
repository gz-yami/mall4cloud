<template>
  <div class="page-product-list">
    <!-- 1.条件筛选 -->
    <div class="screening-conditions">
      <el-form
        :inline="true"
        :model="dataForm"
      >
        <div class="condition-line">
          <!-- 商品编码 -->
          <el-form-item label="商品编码">
            <el-input
              v-model="dataForm.prodCode"
              placeholder="商品编码"
              clearable
            />
          </el-form-item>
          <!-- 商品名称 -->
          <el-form-item label="商品名称">
            <el-input
              v-model="dataForm.prodName"
              placeholder="商品名称"
              clearable
            />
          </el-form-item>
          <!-- 商品分类 -->
          <el-form-item label="平台分类">
            <el-cascader
              v-model="selectedMenu"
              clearable
              expand-trigger="hover"
              :options="categoryList"
              :props="categoryListTreeProps"
              change-on-select
              @change="handleSelectCategoryChange"
            />
          </el-form-item>
        </div>
        <div class="condition-line">
          <!-- 累计销量 -->
          <el-form-item label="累计销量">
            <div class="cumulative-sales range-box">
              <div class="unit-wrapper first-iterm">
                <el-input
                  v-model="dataForm.saleBeginInp"
                  type="number"
                  min="0"
                  max="100000"
                />
              </div>
              <span class="interval-line">-</span>
              <div class="unit-wrapper">
                <el-input
                  v-model="dataForm.saleEndInp"
                  type="number"
                  min="0"
                  max="100000"
                />
              </div>
            </div>
          </el-form-item>
          <!-- 当前价 -->
          <el-form-item label="当前价">
            <div class="current-pirce range-box">
              <div class="unit-wrapper first-iterm">
                <el-input
                  v-model="dataForm.currentPriceBeginInp"
                  type="number"
                  min="0"
                  max="100000"
                />
              </div>
              <span class="interval-line">-</span>
              <div class="unit-wrapper">
                <el-input
                  v-model="dataForm.currentPriceEndInp"
                  type="number"
                  min="0"
                  max="100000"
                />
              </div>
            </div>
          </el-form-item>
        </div>
        <div class="condition-line">
          <div class="operation-box">
            <el-form-item>
              <!-- 查询按钮 -->
              <el-button
                class="operation-btn"
                type="primary"
                @click="conditionSearch()"
              >
                查询
              </el-button>
            </el-form-item>
          </div>
        </div>
      </el-form>
    </div>

    <!-- 2.发布/展示/处理商品操作 -->
    <div class="prod-opration">
      <div class="select-prod-status">
        <el-radio-group
          v-model="prodStatusRadio"
          @change="switchProdList()"
        >
          <!-- 全部按钮 -->
          <el-radio-button label="0">
            全部
          </el-radio-button>
          <!-- 在售中按钮 -->
          <el-radio-button label="1">
            在售中
          </el-radio-button>
          <!-- 已售罄按钮 -->
          <el-radio-button label="2">
            已售罄
          </el-radio-button>
          <!-- 已下架按钮 -->
          <el-radio-button label="3">
            已下架
          </el-radio-button>
        </el-radio-group>
      </div>
    </div>

    <!-- 3.商品展示表格 -->
    <div class="prod-form">
      <div
        ref="closepopoverRef"
        class="table"
      >
        <el-table
          ref="prodInfTableRef"
          :data="pageVO.list"
          :header-cell-style="{ background: '#f9f9f9', color: '#606266' }"
          @selection-change="handleSelectionChange"
          @cell-mouse-enter="enterTableRow"
          @cell-mouse-leave="leaveTableRow"
          @sort-change="conditionSort"
        >
          <!-- 商品信息 -->
          <el-table-column
            class="table-item"
            label="商品信息"
            width="200"
            fixed
          >
            <template #default="scope">
              <div class="prod-name-box can-edit-item">
                <img
                  class="prod-img"
                  :src="(scope.row.mainImgUrl).indexOf('http') === -1 ? resourcesUrl + scope.row.mainImgUrl : scope.row.mainImgUrl"
                >
                <el-popover
                  placement="top-start"
                  trigger="hover"
                  :content="scope.row.exhibitionProdName"
                >
                  <template #reference>
                    <div class="prod-name">
                      {{ scope.row.exhibitionProdName }}
                    </div>
                  </template>
                </el-popover>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            class="table-item"
            prop="spuName"
            label="商品名称"
            align="center"
          />
          <!-- 当前价 -->
          <el-table-column
            class="table-item"
            prop="priceFee"
            label="当前价"
            sortable="custom"
          >
            <template #reference="scope">
              <div class="prod-price-box">
                <div class="name">
                  {{ scope.row.priceFee }}
                </div>
              </div>
            </template>
          </el-table-column>
          <!-- 市场价 -->
          <el-table-column
            class="table-item"
            prop="marketPriceFee"
            label="市场价"
            sortable="custom"
          />
          <!-- 累计销量 -->
          <el-table-column
            class="table-item"
            prop="saleNum"
            label="累计销量"
            sortable="custom"
          />
          <!-- 库存 -->
          <el-table-column
            class="table-item"
            prop="totalStock"
            label="库存"
            sortable="custom"
          >
            <template #reference="scope">
              <div class="prod-price-box">
                <div class="name">
                  {{ scope.row.stock }}
                </div>
              </div>
            </template>
          </el-table-column>
          <!-- 序号 -->
          <el-table-column
            class="table-item"
            prop="seq"
            label="序号"
            sortable="custom"
          >
            <template #reference="scope">
              <div class="seq-box can-edit-item">
                <div class="name">
                  {{ scope.row.exhibitionSeq }}
                </div>
              </div>
            </template>
          </el-table-column>
          <!-- 商品状态状态 1:enable, 0:disable, -1:deleted -->
          <el-table-column
            label="商品状态"
            prop="spuStatus"
            align="center"
          >
            <template #default="{ row }">
              <el-tag
                v-if="row.spuStatus === 0"
                type="danger"
              >
                下架
              </el-tag>
              <el-tag v-if="row.spuStatus === 1">
                上架
              </el-tag>
              <el-tag
                v-if="row.spuStatus === 2"
                type="danger"
              >
                违规下架
              </el-tag>
              <el-tag
                v-if="row.spuStatus === 3"
                type="warning"
              >
                等待审核
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            class="table-item"
            label="操作"
            width="200"
            fixed="right"
          >
            <template #default="{ row }">
              <!-- 查看商品按钮 -->
              <el-button
                type="primary"
                link
                @click="toReleaseProdPage(row.spuId)"
              >
                查看详情
              </el-button>
              <span style="padding: 0 5px">|</span>
              <el-button
                type="primary"
                link
                @click="prodDelete(row.spuId)"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="page-number">
        <el-pagination
          v-show="pageVO.total > 0"
          :page-sizes="[10, 20, 50, 100]"
          :current-page="pageQuery.pageNum"
          :page-size="pageQuery.pageSize"
          :total="pageVO.total"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
        />
      </div>
    </div>
    <prod-details
      v-if="prodDetailsVisible"
      ref="prodDetailsRef"
    />
  </div>
</template>

<script setup>
import * as api from '@/api/product/list'
import * as categoryApi from '@/api/product/category'
import { onMounted, reactive } from 'vue'
import prodDetails from './components/prod-details.vue'

const Data = reactive({
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  dataForm: {},
  selectedMenu: [],
  categoryList: [],
  categoryListTreeProps: {
    value: 'categoryId',
    label: 'name'
  },
  prodStatusRadio: 0,
  currentMoveSpuId: '', // 移入项的spuId
  // 多选
  checkAll: false,
  isIndeterminate: false,
  checkedCount: 0,
  selectedProdList: [], // 被选中的商品列表
  // 查询的参数
  searchParam: {
  },
  pageQuery: {
    pageSize: 10,
    pageNum: 1
  },
  // 返回参数
  pageVO: {
    list: [], // 返回的列表
    total: 0, // 一共多少条数据
    pages: 0 // 一共多少页
  },
  // loading
  pageLoading: true,
  prodDetailsVisible: false, // 商品详情

  spuId: null // 商品id
})

const { resourcesUrl, dataForm, selectedMenu, categoryList, categoryListTreeProps, prodStatusRadio, pageQuery, pageVO, prodDetailsVisible } = toRefs(Data)

onMounted(() => {
  getDataList()
  getProdCategoryList()
})

// 条件搜索
const conditionSearch = () => {
  Data.pageQuery.pageNum = 1
  const minPrice = Data.dataForm.currentPriceBeginInp ? Data.dataForm.currentPriceBeginInp * 100 : undefined
  const maxPrice = Data.dataForm.currentPriceEndInp ? Data.dataForm.currentPriceEndInp * 100 : undefined
  Data.searchParam = {
    spuId: Data.dataForm.prodId || undefined,
    partyCode: Data.dataForm.prodCode || undefined,
    keyword: Data.dataForm.prodName || undefined,
    primaryCategoryId: Data.dataForm.primaryCategoryId || undefined, // 平台一级分类id
    secondaryCategoryId: Data.dataForm.secondaryCategoryId || undefined, // 平台二级分类id
    categoryId: Data.dataForm.categoryId || undefined,
    minSaleNum: Data.dataForm.saleBeginInp || undefined,
    maxSaleNum: Data.dataForm.saleEndInp || undefined,
    minPrice,
    maxPrice,
    isCompose: Data.dataForm.currentyuPreSaleStatus === '' ? undefined : Data.dataForm.currentyuPreSaleStatus,
    dataType: parseInt(Data.prodStatusRadio)
  }
  getDataList()
}

// 获取商品列表
const getDataList = () => {
  Data.pageLoading = true
  api.page({ ...Data.pageQuery, ...Data.searchParam }).then(pageVO => {
    Data.pageVO = pageVO
    Data.pageVO.list.forEach(item => {
      item.exhibitionProdName = item.name
      item.exhibitionSeq = item.seq
      item.priceFee = item.priceFee / 100
      item.marketPriceFee = item.marketPriceFee / 100
    })
    Data.pageLoading = false
  })
}

// 获取商品目录
const getProdCategoryList = () => {
  Data.pageLoading = true
  categoryApi.categoryPage({}).then(pageVO => {
    Data.categoryList = treeDataTranslate(pageVO, 'categoryId', 'parentId')
    Data.pageLoading = false
  })
}

// 监听分类选择的变化
const handleSelectCategoryChange = (val) => {
  Data.dataForm.primaryCategoryId = undefined
  Data.dataForm.secondaryCategoryId = undefined
  Data.dataForm.categoryId = undefined
  if (val.length === 1) { // 长度为1，表明当前选择的是一级分类（点击了一级分类）
    Data.dataForm.primaryCategoryId = val[0] // 平台一级分类id
  } else if (val.length === 2) { // 长度为2，表明当前选择的是二级分类（点击了二级分类）
    Data.dataForm.secondaryCategoryId = val[1] // 平台二级分类id
  } else if (val.length === 3) { // 长度为3，表明当前选择的是三级分类（点击了三级分类）
    Data.dataForm.categoryId = val[2] // 平台三级分类id
  }
}

// 切换不同状态的商品展示
// 0：代表全部商品
// 1：在售中的商品
// 2：已售完的商品
// 3：已下架的商品
const switchProdList = () => {
  const dataType = parseInt(Data.prodStatusRadio)
  Data.checkAll = false
  Data.pageQuery.pageNum = 1
  Data.searchParam = {
    dataType
  }
  getDataList()
}

const prodDetailsRef = ref()
// 跳转发布商品页
const toReleaseProdPage = (spuId) => {
  Data.prodDetailsVisible = true
  nextTick(() => {
    prodDetailsRef.value.init(spuId)
  })
}

// 监听表格选中
const handleSelectionChange = (value) => {
  const checkedCount = value.length
  Data.checkedCount = checkedCount
  Data.checkAll = checkedCount === Data.pageVO.list.length
  Data.isIndeterminate = checkedCount > 0 && checkedCount < Data.pageVO.list.length
  // 提取出商品id列表
  const selectedProdList = []
  if (value.length > 0) {
    value.forEach(item => {
      selectedProdList.push(item.spuId)
    })
  }
  Data.selectedProdList = selectedProdList
}

const prodInfTableRef = ref()

// 条件排序
// priceFee:当前价
// marketPriceFee:市场价
// saleNum:累计销量
// totalStock:库存
// seq:序号
// createTime:创建时间
const conditionSort = (column) => {
  closePopover()
  Data.pageQuery.pageNum = 1
  const orderMode = column.order === 'ascending' ? 1 : column.order === 'descending' ? 0 : undefined
  if (column.prop === 'priceFee') {
    Data.searchParam = {
      priceFeeSort: orderMode
    }
  } else if (column.prop === 'marketPriceFee') {
    Data.searchParam = {
      marketPriceFeeSort: orderMode
    }
  } else if (column.prop === 'saleNum') {
    Data.searchParam = {
      saleNumSort: orderMode
    }
  } else if (column.prop === 'totalStock') {
    Data.searchParam = {
      stockSort: orderMode
    }
  } else if (column.prop === 'seq') {
    Data.searchParam = {
      seqSort: orderMode
    }
  } else if (column.prop === 'createTime') {
    Data.searchParam = {
      createTimeSort: orderMode
    }
  }
  getDataList()
}

// 商品删除
const prodDelete = (spuId) => {
  ElMessageBox.confirm('是否确认删除该商品？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    api.deleteById(spuId).then(() => {
      ElMessage({
        message: '商品删除成功',
        type: 'success',
        duration: 1000,
        onClose: () => getDataList()
      })
    })
  }).catch(() => { })
}

// 移入表格行
const enterTableRow = (row) => {
  Data.currentMoveSpuId = row.spuId
}

// 移出表格行
const leaveTableRow = () => {
  Data.currentMoveSpuId = ''
}

const closepopoverRef = ref()
// 关闭popover弹窗
const closePopover = () => {
  closepopoverRef.value.click()
}
// 每页数
const sizeChangeHandle = (val) => {
  Data.pageQuery.pageSize = val
  Data.pageQuery.pageNum = 1
  getDataList()
}

// 当前页
const currentChangeHandle = (val) => {
  Data.pageQuery.pageNum = val
  getDataList()
}
</script>

<style lang="scss" scoped>
@use 'index';
</style>
