<template>
  <div class="page-product-list">
    <!-- 1.条件筛选 -->
    <div class="screening-conditions">
      <el-form
        :inline="true"
        :model="dataForm"
      >
        <div class="condition-line">
          <!-- 商品ID -->
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
          <el-form-item label="商品分类">
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
      <div class="release-prod-btn">
        <el-button
          type="primary"
          @click="toReleaseProdPage(-1)"
        >
          发布新商品
        </el-button>
      </div>
    </div>

    <!-- 3.商品展示表格 -->
    <div class="prod-form">
      <div
        v-if="pageVO.list.length"
        class="change-prod-status"
      >
        <el-checkbox
          v-model="checkAll"
          :indeterminate="isIndeterminate"
          @change="handleCheckAllChange"
        >
          当页全选<span
            v-if="checkedCount"
            class="checked-words"
          >已选商品 {{ checkedCount }}</span>
        </el-checkbox>
        <div class="button-box">
          <!-- 上架按钮 -->
          <el-button
            v-if="prodStatusRadio!=0 && prodStatusRadio!=1 && prodStatusRadio!=2"
            @click="changeProdStatus('onTheShelf')"
          >
            上架
          </el-button>
          <!-- 下架按钮 -->
          <el-button
            v-if="prodStatusRadio!=0 && prodStatusRadio!=3"
            @click="changeProdStatus('offTheShelf')"
          >
            下架
          </el-button>
        </div>
      </div>
      <div
        ref="closepopoverRef"
        class="table"
      >
        <el-table
          ref="prodInfTableRef"
          :data="pageVO.list"
          :header-cell-style="{background:'#f9f9f9',color:'#606266'}"
          @selection-change="handleSelectionChange"
          @cell-mouse-enter="enterTableRow"
          @cell-mouse-leave="leaveTableRow"
          @sort-change="conditionSort"
        >
          <el-table-column
            type="selection"
            width="55"
          />
          <!-- 商品信息 -->
          <el-table-column
            class="table-item"
            label="商品信息"
            width="100"
            fixed
          >
            <template #default="scope">
              <div class="prod-name-box can-edit-item">
                <img
                  class="prod-img"
                  :src="(scope.row.mainImgUrl).indexOf('http')===-1 ? resourcesUrl + scope.row.mainImgUrl : scope.row.mainImgUrl"
                >
                <el-popover
                  placement="top-start"
                  trigger="hover"
                  :content="scope.row.exhibitionProdName"
                >
                  <template #reference>
                    <div
                      class="prod-name"
                    >
                      {{ scope.row.exhibitionProdName }}
                    </div>
                  </template>
                </el-popover>
                <el-popover
                  placement="bottom"
                  trigger="click"
                  @after-enter="getCurrentSpuId(scope.row.spuId, 'prodName')"
                  @hide="initProdInf(scope.row)"
                >
                  <div style="display:flex;align-items:center">
                    <el-input
                      v-model="scope.row.name"
                      type="textarea"
                      :rows="2"
                      placeholder="请输入商品名称"
                    />
                    <div style="display:flex;align-items:center;margin-left:10px;">
                      <el-button
                        type="primary"
                        link
                        @click="modifyProdName(scope.row)"
                      >
                        保存
                      </el-button>
                      <span style="margin:0 3px;color:#e5e5e5;">|</span>
                      <el-button
                        type="primary"
                        link
                        @click="closePopover()"
                      >
                        取消
                      </el-button>
                    </div>
                  </div>
                  <template #reference>
                    <i
                      v-show="scope.row.spuId==currentMoveSpuId || (scope.row.spuId==currentClickSpuId && currentShowPopover=='prodName')"
                      class="el-icon-edit"
                    />
                  </template>
                </el-popover>
              </div>
            </template>
          </el-table-column>
          <!-- 商品名称 -->
          <el-table-column
            class="table-item"
            prop="spuName"
            label="商品名称"
            align="center"
            width="200"
          />
          <!-- 当前价 -->
          <el-table-column
            class="table-item"
            prop="priceFee"
            label="当前价"
            align="center"
            sortable="custom"
            width="120"
          >
            <template #default="scope">
              <div class="prod-price-box">
                <el-popover
                  placement="bottom"
                  trigger="click"
                  @show="getProdDetail(scope.row.spuId)"
                  @after-enter="getCurrentSpuId(scope.row.spuId, 'priceFee')"
                  @hide="clearShowPopoverInf()"
                >
                  <div v-if="currentModifyProdSkuList.length===1">
                    <div style="display:flex;align-items:center">
                      <el-input
                        v-model="currentModifyProdSkuList[0].priceFee"
                        type="number"
                      />
                      <div style="display:flex;align-items:center;margin-left:5px;">
                        <el-button
                          type="primary"
                          link
                          @click="modifyProdPrice(scope.row.spuId, currentModifyProdSkuList)"
                        >
                          保存
                        </el-button>
                        <span style="margin:0 3px;color:#e5e5e5;">|</span>
                        <el-button
                          type="primary"
                          link
                          @click="closePopover()"
                        >
                          取消
                        </el-button>
                      </div>
                    </div>
                  </div>
                  <div v-if="currentModifyProdSkuList.length>1">
                    <el-table :data="currentModifyProdSkuList">
                      <el-table-column
                        width="150"
                        prop="skuName"
                        label="规格名称"
                      />
                      <el-table-column
                        width="200"
                        prop="address"
                        label="价格"
                      >
                        <template #default="{row}">
                          <el-input
                            v-model="row.priceFee"
                            type="number"
                          />
                        </template>
                      </el-table-column>
                    </el-table>
                    <div style="text-align:right;margin-top:10px;">
                      <el-button
                        type="primary"
                        link
                        @click="closePopover()"
                      >
                        取消
                      </el-button>
                      <el-button
                        type="primary"
                        @click="modifyProdPrice(scope.row.spuId, currentModifyProdSkuList)"
                      >
                        保存
                      </el-button>
                    </div>
                  </div>
                  <template #reference>
                    <i
                      v-show="scope.row.spuId==currentMoveSpuId || (scope.row.spuId==currentClickSpuId && currentShowPopover=='priceFee')"
                      class="el-icon-edit"
                    />
                  </template>
                </el-popover>
              </div>
            </template>
            <template #reference="scope">
              <div
                class="name"
              >
                {{ scope.row.priceFee }}
              </div>
            </template>
          </el-table-column>
          <!-- 市场价 -->
          <el-table-column
            class="table-item"
            prop="marketPriceFee"
            label="市场价"
            align="center"
            sortable="custom"
            width="120"
          />

          <!-- 累计销量 -->
          <el-table-column
            class="table-item"
            prop="saleNum"
            label="累计销量"
            align="center"
            sortable="custom"
            width="120"
          />
          <!-- 库存 -->
          <el-table-column
            class="table-item"
            prop="totalStock"
            label="库存"
            align="center"
            sortable="custom"
            width="120"
          >
            <template #default="scope">
              <div class="prod-price-box">
                <el-popover
                  placement="bottom"
                  trigger="click"
                  @show="getProdDetail(scope.row.spuId)"
                  @after-enter="getCurrentSpuId(scope.row.spuId, 'prodStock')"
                  @hide="clearShowPopoverInf()"
                >
                  <div v-if="currentModifyProdSkuList.length===1">
                    <div style="display: flex;align-items: center">
                      <el-input
                        v-model="currentModifyProdSkuList[0].stock"
                        type="number"
                      />
                      <div style="display:flex;align-items:center;margin-left:5px;">
                        <el-button
                          type="primary"
                          link
                          @click="modifyProdStock(scope.row.spuId, currentModifyProdSkuList)"
                        >
                          保存
                        </el-button>
                        <span style="padding:0 5px;color:#e5e5e5;">|</span>
                        <el-button
                          type="primary"
                          link
                          @click="closePopover()"
                        >
                          取消
                        </el-button>
                      </div>
                    </div>
                  </div>
                  <div v-if="currentModifyProdSkuList.length>1">
                    <el-table :data="currentModifyProdSkuList">
                      <el-table-column
                        width="150"
                        prop="skuName"
                        label="规格名称"
                      />
                      <el-table-column
                        width="200"
                        prop="address"
                        label="库存"
                      >
                        <template #default="{row}">
                          <el-input
                            v-model="row.stock"
                            type="number"
                          />
                        </template>
                      </el-table-column>
                    </el-table>
                    <div style="text-align:right;margin-top:10px;">
                      <el-button
                        type="primary"
                        link
                        @click="closePopover()"
                      >
                        取消
                      </el-button>
                      <el-button
                        type="primary"
                        @click="modifyProdStock(scope.row.spuId, currentModifyProdSkuList)"
                      >
                        保存
                      </el-button>
                    </div>
                  </div>
                  <template #reference>
                    <i
                      v-show="scope.row.spuId==currentMoveSpuId || (scope.row.spuId==currentClickSpuId && currentShowPopover=='prodStock')"
                      class="el-icon-edit"
                    />
                  </template>
                </el-popover>
              </div>
            </template>
            <template #reference="scope">
              <div
                class="name"
              >
                {{ scope.row.stock }}
              </div>
            </template>
          </el-table-column>
          <!-- 序号 -->
          <el-table-column
            class="table-item"
            prop="seq"
            label="序号"
            align="center"
            width="120"
            sortable="custom"
          >
            <template #default="scope">
              <div class="seq-box can-edit-item">
                <el-popover
                  placement="bottom"
                  trigger="click"
                  @after-enter="getCurrentSpuId(scope.row.spuId, 'seq')"
                  @hide="initProdInf(scope.row)"
                >
                  <div style="display:flex;align-items:center;">
                    <el-input
                      v-model="scope.row.seq"
                      type="number"
                      max="32767"
                    />
                    <div style="display:flex;align-items:center;margin-left:5px;">
                      <el-button
                        type="primary"
                        link
                        @click="modifySeq(scope)"
                      >
                        保存
                      </el-button>
                      <span style="margin:0 3px;color:#e5e5e5;">|</span>
                      <el-button
                        type="primary"
                        link
                        @click="closePopover()"
                      >
                        取消
                      </el-button>
                    </div>
                  </div>
                  <template #reference>
                    <i
                      v-show="scope.row.spuId==currentMoveSpuId || (scope.row.spuId==currentClickSpuId && currentShowPopover=='seq')"
                      class="el-icon-edit"
                    />
                  </template>
                </el-popover>
              </div>
            </template>
            <template #reference="scope">
              <div
                class="name"
              >
                {{ scope.row.exhibitionSeq }}
              </div>
            </template>
          </el-table-column>
          <!-- 商品状态状态 1:enable, 0:disable, -1:deleted -->
          <el-table-column
            label="商品状态"
            prop="status"
            align="center"
          >
            <template #default="{row}">
              <el-tag
                v-if="row.spuStatus === 0"
                type="danger"
              >
                下架
              </el-tag>
              <el-tag
                v-if="row.spuStatus === 1"
              >
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
            <template #default="{row}">
              <!-- 编辑按钮 -->
              <el-button
                type="primary"
                link
                @click="toReleaseProdPage(row.spuId)"
              >
                编辑
              </el-button>
              <span
                v-if="row.spuStatus === 0"
                style="padding: 0 5px"
              >|</span>
              <el-button
                v-if="row.spuStatus === 0"
                type="primary"
                link
                @click="prodSale(row.spuId)"
              >
                上架
              </el-button>
              <span
                v-if="row.spuStatus === 1"
                style="padding: 0 5px"
              >|</span>
              <el-button
                v-if="row.spuStatus === 1"
                type="primary"
                link
                @click="prodNotSale(row.spuId)"
              >
                下架
              </el-button>
              <span
                v-if="row.spuStatus === 2 || row.spuStatus === 3"
                style="padding: 0 5px"
              >|</span>
              <el-button
                v-if="row.spuStatus === 2 || row.spuStatus === 3"
                type="primary"
                link
                @click="offlineManageHandle(row)"
              >
                下架管理
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
        <div
          v-if="pageVO.list.length"
          class="change-prod-status"
        >
          <el-checkbox
            v-model="checkAll"
            :indeterminate="isIndeterminate"
            @change="handleCheckAllChange"
          >
            当页全选<span
              v-if="checkedCount"
              style="font-size:10px;margin-left:10px"
            >已选商品 {{ checkedCount }}</span>
          </el-checkbox>
          <div class="button-box">
            <!-- 上架按钮 -->
            <el-button
              v-if="prodStatusRadio!=0 && prodStatusRadio!=1 && prodStatusRadio!=2"
              @click="changeProdStatus('onTheShelf')"
            >
              上架
            </el-button>
            <!-- 下架按钮 -->
            <el-button
              v-if="prodStatusRadio!=0 && prodStatusRadio!=3"
              @click="changeProdStatus('offTheShelf')"
            >
              下架
            </el-button>
          </div>
        </div>
        <el-pagination
          v-show="pageVO.total>0"
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
    <!-- 下架管理 -->
    <offline-manage
      v-if="operateDialogVisible"
      ref="offlineDataRef"
      @rereapply-data-submit="rereapplyDataSubmit"
    />
  </div>
</template>

<script setup>
import * as api from '@/api/product/list'
import * as categoryApi from '@/api/product/category'
import { onMounted, reactive, ref } from 'vue'
import Big from 'big.js'

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
  // 是否为组合商品下拉框
  preSaleStatusOptions: [{
    value: 1,
    label: '是'
  },
  {
    value: 0,
    label: '否'
  }],
  // 商品分类下拉框
  prodClassifyOptions: [{
    value: 1,
    label: '服装'
  },
  {
    value: 2,
    label: '电器'
  }],
  // 商品表格数据
  tableData: [],
  currentMoveSpuId: '', // 移入项的spuId
  currentClickSpuId: '', // 点击项的spuId
  currentShowPopover: '', // 当前展示的弹窗
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
  currentModifyProdDetail: {}, // 当前准备修改的商品详情
  currentModifyProdSkuList: [], // 当前准备修改的商品的规格列表

  currentSpuId: null, // 商品id
  operateDialogVisible: false // 操作对话框
})

const { resourcesUrl, dataForm, selectedMenu, categoryList, categoryListTreeProps, prodStatusRadio, currentMoveSpuId, currentClickSpuId, currentShowPopover, checkAll, isIndeterminate, checkedCount, pageQuery, pageVO, currentModifyProdSkuList, operateDialogVisible } = toRefs(Data)

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
    shopPrimaryCategoryId: Data.dataForm.shopPrimaryCategoryId || undefined, // 商家一级分类id
    shopSecondaryCategoryId: Data.dataForm.shopSecondaryCategoryId || undefined, // 商家二级分类id
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
      item.priceFee = accuracyCount(item.priceFee, 100, 3)
      item.marketPriceFee = accuracyCount(item.marketPriceFee, 100, 3)
    })
    Data.pageLoading = false
  })
}

// 获取商品目录
const getProdCategoryList = () => {
  Data.pageLoading = true
  categoryApi.shopCategoryPage({}).then(pageVO => {
    Data.categoryList = treeDataTranslate(pageVO, 'categoryId', 'parentId')
    Data.pageLoading = false
  })
}

// 监听分类选择的变化
const handleSelectCategoryChange = (val) => {
  if (val.length === 1) {
    Data.dataForm.shopPrimaryCategoryId = val[0]
    Data.dataForm.shopSecondaryCategoryId = undefined
  } else if (val.length === 2) {
    Data.dataForm.shopPrimaryCategoryId = undefined
    Data.dataForm.shopSecondaryCategoryId = val[1]
  }
}

// 切换不同状态的商品展示
// 0：代表全部商品
// 1：在售中的商品
// 2：已售完的商品
// 3：已下架的商品
const switchProdList = () => {
  const dataType = parseInt(Data.prodStatusRadio)
  Data.pageQuery.pageNum = 1
  Data.searchParam = {
    dataType
  }
  getDataList()
}

const router = useRouter()
// 跳转发布商品页
const toReleaseProdPage = (spuId) => {
  if (spuId === -1) {
    router.push('/product/prod_info')
  } else {
    router.push('/product/prod_info?spuId=' + spuId)
  }
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
// 表格全选/全不选
const handleCheckAllChange = () => {
  prodInfTableRef.value.toggleAllSelection()
  Data.isIndeterminate = false
}

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

// 改变商品状态
// onTheShelf:上架
// offTheShelf:下架
const changeProdStatus = (status) => {
  // 如果没选商品，提示选择商品
  if (Data.selectedProdList.length <= 0) {
    ElMessage({
      message: '请选择商品',
      type: 'warning'
    })
    return
  }

  if (status === 'onTheShelf') {
    prodSale(Data.selectedProdList)
  } else if (status === 'offTheShelf') {
    prodNotSale(Data.selectedProdList)
  }
}

// 商品上架
const prodSale = (spuIdOrSpuIds) => {
  let param = {}
  if (typeof (spuIdOrSpuIds) === 'number') {
    param = {
      status: 1,
      spuId: spuIdOrSpuIds
    }
  } else if (spuIdOrSpuIds.length > 1) {
    param = {
      status: 1,
      spuIds: spuIdOrSpuIds
    }
  } else {
    param = {
      status: 1,
      spuId: spuIdOrSpuIds[0]
    }
  }
  api.updateProdStatus(param).then(() => {
    ElMessage({
      message: '商品上架成功',
      type: 'success',
      duration: 1500,
      onClose: () => getDataList()
    })
  })
}

// 商品下架
const prodNotSale = (spuIdOrSpuIds) => {
  let param = {}
  if (typeof (spuIdOrSpuIds) === 'number') {
    param = {
      status: 0,
      spuId: spuIdOrSpuIds
    }
  } else if (spuIdOrSpuIds.length > 1) {
    param = {
      status: 0,
      spuIds: spuIdOrSpuIds
    }
  } else {
    param = {
      status: 0,
      spuId: spuIdOrSpuIds[0]
    }
  }
  api.updateProdStatus(param).then(() => {
    ElMessage({
      message: '商品下架成功',
      type: 'success',
      duration: 1500,
      onClose: () => getDataList()
    })
  })
}

/**
     * 商品违规下架操作
     */
const offlineManageHandle = (row) => {
  Data.operateDialogVisible = true
  Data.currentSpuId = row.spuId
  getOfflineDetailByProdId(row.spuId)
}

/**
     * 获取最新下线商品的事件
     */
const offlineDataRef = ref()
const getOfflineDetailByProdId = (spuId) => {
  api.getOfflineDetailById(spuId).then((data) => {
    Data.offlineDetail = data
    nextTick(() => {
      offlineDataRef.value.init(data)
    })
  })
}

/**
     * 提交上架申请
     */
const rereapplyDataSubmit = (data) => {
  const param = {
    handleId: Data.currentSpuId,
    eventId: data.eventId,
    reapplyReason: data.reapplyReason // 申请理由
  }
  api.rereapplyOnlineById(param).then(() => {
    Data.operateDialogVisible = false
    ElMessage({
      message: '提交成功',
      type: 'success',
      duration: 1000,
      onClose: () => getDataList()
    })
  })
}

// 商品删除
const prodDelete = (spuId) => {
  api.deleteById(spuId).then(() => {
    ElMessage({
      message: '商品删除成功',
      type: 'success',
      duration: 1500,
      onClose: () => getDataList()
    })
  })
}

// 移入表格行
const enterTableRow = (row) => {
  Data.currentMoveSpuId = row.spuId
}

// 移出表格行
const leaveTableRow = () => {
  Data.currentMoveSpuId = ''
}

// 获取弹出弹窗项信息
const getCurrentSpuId = (spuId, currentShowPopover) => {
  Data.currentClickSpuId = spuId
  Data.currentShowPopover = currentShowPopover
}

// 清空选中项弹窗信息
const clearShowPopoverInf = () => {
  Data.currentClickSpuId = ''
  Data.currentShowPopover = ''
}

// 获取商品详情
const getProdDetail = (spuId) => {
  api.get(spuId).then(data => {
    Data.currentModifyProdSkuList = data.skus
    Data.currentModifyProdSkuList.forEach(item => {
      item.originalStock = item.stock
      item.priceFee = accuracyCount(item.priceFee, 100, 3)
      item.oldPriceFee = item.priceFee
      item.marketPriceFee = accuracyCount(item.marketPriceFee, 100, 3)
    })
  })
}

// 关闭弹窗后,初始化数据
const initProdInf = (row) => {
  row.name = row.exhibitionProdName
  row.seq = row.exhibitionSeq
  clearShowPopoverInf()
}

// 关闭popover弹窗
const closepopoverRef = ref()
const closePopover = () => {
  closepopoverRef.value.click()
}

// 修改商品名称
const modifyProdName = (row) => {
  if (row.name.trim() === '') {
    ElMessage({
      message: '请输入商品名称',
      type: 'warning'
    })
    return
  } else if (row.name === row.exhibitionProdName) {
    closePopover()
    return
  }
  const param = {
    spuId: row.spuId,
    name: row.name
  }
  api.updatePartProdStatus({ ...param }).then(() => {
    ElMessage({
      message: '修改成功',
      type: 'success',
      duration: 1500,
      onClose: () => {
        getDataList()
        closePopover()
      }
    })
  })
}

// 修改商品价格
const modifyProdPrice = (spuId, skus) => {
  const skuList = []
  let isChangePriceFee = false
  let minPriceFee = 0
  // 判断是否价格有变动,没有变动不更新价格,关闭弹窗即可
  for (let i = 0; i < skus.length; i++) {
    if (skus[i].oldPriceFee !== skus[i].priceFee) {
      isChangePriceFee = true
      break
    }
  }
  if (isChangePriceFee === false) {
    closePopover()
    return
  }
  // 把需要传递的参数,单独取出来
  for (let i = 0; i < skus.length; i++) {
    if (skus[i].priceFee === '') {
      ElMessage({
        message: '请输入商品价格',
        type: 'warning'
      })
      return
    } else if (parseFloat(skus[i].priceFee) < 0.01) {
      ElMessage({
        message: '商品价格不能小于0.01',
        type: 'warning'
      })
      return
    } else {
      skuList.push({
        priceFee: accuracyCount(skus[i].priceFee, 100, 2),
        skuId: skus[i].skuId
      })
    }
  }
  // 获取多种规格下的最小价格
  minPriceFee = skuList[0].priceFee
  for (let i = 1; i < skuList.length; i++) {
    if (minPriceFee > skuList[i].priceFee) {
      minPriceFee = skuList[i].priceFee
    }
  }
  const param = {
    spuId,
    priceFee: minPriceFee,
    skuList
  }
  api.updatePartProdStatus({ ...param }).then(() => {
    ElMessage({
      message: '修改成功',
      type: 'success',
      duration: 1500,
      onClose: () => {
        getDataList()
        closePopover()
      }
    })
  })
}
// 修改商品库存
const modifyProdStock = (spuId, skus) => {
  const skuList = []
  let totalChangeStock = 0
  for (let i = 0; i < skus.length; i++) {
    let changeStock = 0
    if (skus[i].stock === '') {
      ElMessage({
        message: '请输入商品库存',
        type: 'warning'
      })
      return
    } else if (skus[i].stock < skus[i].originalStock) {
      ElMessage({
        message: '请输入大于或等于原本的库存',
        type: 'warning'
      })
      return
    } else {
      changeStock = parseInt(skus[i].stock - skus[i].originalStock)
      totalChangeStock += changeStock
      skuList.push({
        changeStock,
        skuId: skus[i].skuId
      })
    }
  }
  // 如果总库存改变为0,就不更新库存,只关闭弹窗
  if (totalChangeStock === 0) {
    closePopover()
    return
  }
  const param = {
    spuId,
    changeStock: totalChangeStock,
    skuList
  }
  api.updatePartProdStatus({ ...param }).then(() => {
    ElMessage({
      message: '修改成功',
      type: 'success',
      duration: 1500,
      onClose: () => {
        getDataList()
        closePopover()
      }
    })
  })
}

// 修改序号
const modifySeq = (scope) => {
  if (scope.row.seq === '') {
    ElMessage({
      message: '请输入序号',
      type: 'warning'
    })
    return
  } else if (scope.row.exhibitionSeq === scope.row.seq) {
    closePopover()
    return
  }
  const param = {
    spuId: scope.row.spuId,
    seq: scope.row.seq
  }
  api.updatePartProdStatus({ ...param }).then(() => {
    ElMessage({
      message: '修改成功',
      type: 'success',
      duration: 1500,
      onClose: () => {
        getDataList()
        closePopover()
      }
    })
  })
}

// 精度运算
// a:第一个计算数(例如:被除数/被减数)
// b:第二个计算数(例如:除数/减数)
// countMode:计算方式(0:加 1:减 2:乘 3:除)
const accuracyCount = (a, b, countMode) => {
  let finalResult = 0
  if (countMode === 0) {
    finalResult = new Big(a).plus(b).valueOf()
  } else if (countMode === 1) {
    finalResult = new Big(a).minus(b).valueOf()
  } else if (countMode === 2) {
    finalResult = new Big(a).times(b).valueOf()
  } else if (countMode === 3) {
    finalResult = new Big(a).div(b).valueOf()
  } else {
    return
  }
  return finalResult
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
.page-product-list {
  // 1.筛选条件搜索
  .screening-conditions {
    padding: 20px;
    background: #f9f9f9;
    margin-bottom: 30px;
    :deep(.el-form-item) {
      margin-right: 30px;
      .el-form-item__label {
        width: 80px;
      }
      .el-form-item__content {
        width: 200px;
      }
      .el-input__inner {
        height: 31px !important;
        line-height: 31px !important;
      }
      .range-box {
        display: flex;
        .interval-line {
          padding: 0 10px;
        }
      }
    }
    .operation-box {
      display: inline-block;
    }
  }

  // 2.商品操作
  .prod-opration {
    display: flex;
    margin-bottom: 30px;
    .release-prod-btn {
      margin-left: 50px;
      margin-right: 20px;
    }
  }

  // 3.商品展示表格
  .prod-form {
    .change-prod-status {
      display: flex;
      align-items: center;
      margin-left: 10px;
      margin-bottom: 20px;
      .checked-words {
        font-size: 12px;
        margin-left: 10px;
      }
      .button-box {
        margin-left: 20px;
      }
    }
    .table {
      :deep(.el-table-column--selection) {
        .cell {
          padding-left: 10px;
        }
      }
      .can-edit-item {
        display: flex;
        align-items: center;
      }
      .prod-name-box {
        .prod-img {
          width: 60px;
          height: 60px;
        }
        .prod-name {
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;
          overflow: hidden;
          width: 100px;
          margin-left: 10px;
        }
      }
      .prod-price-box {
        display: flex;
        align-items: center;
        .el-icon-edit {
          margin-left: 5px;
        }
      }
    }
    .page-number {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
    }
  }
}
</style>
