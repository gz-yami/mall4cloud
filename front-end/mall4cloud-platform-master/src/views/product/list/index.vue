<template>
  <div class="mod-prod-list app-container">
    <!-- 1.条件筛选 -->
    <div class="screening-conditions">
      <el-form :inline="true" :model="dataForm">
        <div class="condition-line">
          <!-- 商品ID -->
<!--          <el-form-item label="商品ID">-->
<!--            <el-input v-model="dataForm.prodId" type="number" placeholder="商品ID" clearable size="mini" />-->
<!--          </el-form-item>-->
          <!-- 商品编码 -->
          <el-form-item label="商品编码">
            <el-input v-model="dataForm.prodCode" placeholder="商品编码" clearable size="mini" />
          </el-form-item>
          <!-- 商品名称 -->
          <el-form-item label="商品名称">
            <el-input v-model="dataForm.prodName" placeholder="商品名称" clearable size="mini" />
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
                  size="mini"
                  min="0"
                  max="100000"
                />
              </div>
              <span class="interval-line">-</span>
              <div class="unit-wrapper">
                <el-input
                  v-model="dataForm.saleEndInp"
                  type="number"
                  size="mini"
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
                  size="mini"
                  min="0"
                  max="100000"
                />
              </div>
              <span class="interval-line">-</span>
              <div class="unit-wrapper">
                <el-input
                  v-model="dataForm.currentPriceEndInp"
                  type="number"
                  size="mini"
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
                icon="el-icon-search"
                size="mini"
                @click="conditionSearch()"
              >查询
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
          size="mini"
          @change="switchProdList()"
        >
          <!-- 全部按钮 -->
          <el-radio-button label="0">全部</el-radio-button>
          <!-- 在售中按钮 -->
          <el-radio-button label="1">在售中</el-radio-button>
          <!-- 已售罄按钮 -->
          <el-radio-button label="2">已售罄</el-radio-button>
          <!-- 已下架按钮 -->
          <el-radio-button label="3">已下架</el-radio-button>
        </el-radio-group>
      </div>
    </div>

    <!-- 3.商品展示表格 -->
    <div class="prod-form">
      <div ref="closepopover" class="table">
        <el-table
          ref="prodInfTable"
          :data="pageVO.list"
          :header-cell-style="{background:'#f9f9f9',color:'#606266'}"
          @selection-change="handleSelectionChange"
          @cell-mouse-enter="enterTableRow"
          @cell-mouse-leave="leaveTableRow"
          @sort-change="conditionSort"
        >
          <!-- 商品信息 -->
          <el-table-column class="table-item" label="商品信息" width="200" fixed>
            <template slot-scope="scope">
              <div class="prod-name-box can-edit-item">
                <img class="prod-img" :src="(scope.row.mainImgUrl).indexOf('http')===-1 ? resourcesUrl + scope.row.mainImgUrl : scope.row.mainImgUrl">
                <el-popover
                  placement="top-start"
                  trigger="hover"
                  :content="scope.row.exhibitionProdName"
                >
                  <div slot="reference" class="prod-name">{{ scope.row.exhibitionProdName }}</div>
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
            <template slot-scope="scope">
              <div class="prod-price-box">
                <div slot="reference" class="name">{{ scope.row.priceFee }}</div>
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
            <template slot-scope="scope">
              <div class="prod-price-box">
                <div slot="reference" class="name">{{ scope.row.stock }}</div>
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
            <template slot-scope="scope">
              <div class="seq-box can-edit-item">
                <div slot="reference" class="name">{{ scope.row.exhibitionSeq }}</div>
              </div>
            </template>
          </el-table-column>
          <!-- 商品状态状态 1:enable, 0:disable, -1:deleted -->
          <el-table-column label="商品状态" prop="spuStatus" align="center">
            <template slot-scope="{row}">
              <el-tag v-if="row.spuStatus === 0" size="small" type="danger">下架</el-tag>
              <el-tag v-if="row.spuStatus === 1" size="small">上架</el-tag>
              <el-tag v-if="row.spuStatus === 2" size="small" type="danger">违规下架</el-tag>
              <el-tag v-if="row.spuStatus === 3" size="small" type="warning">等待审核</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            class="table-item"
            label="操作"
            width="200"
            fixed="right"
          >
            <template slot-scope="{row}">
              <!-- 查看商品按钮 -->
              <el-button size="mini" type="text" @click="toReleaseProdPage(row.spuId)">查看详情</el-button>
              <span style="padding: 0 5px">|</span>
              <el-button size="mini" type="text" @click="prodDelete(row.spuId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="page-number">
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
    <prod-details v-if="prodDetailsVisible" ref="prodDetails" />
  </div>
</template>

<script>
import * as api from '@/api/product/list'
import * as categoryApi from '@/api/product/category'
import { treeDataTranslate } from '@/utils'
import prodDetails from './prod-details.vue'
import Big from 'big.js'
export default {
  components: {
    prodDetails
  },
  data() {
    return {
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
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
      prodDetailsVisible: false, // 商品详情

      spuId: null, // 商品id
      operateDialogVisible: false // 商品违规下架弹窗
    }
  },
  mounted() {
    this.getDataList()
    this.getProdCategoryList()
  },
  methods: {
    // 条件搜索
    conditionSearch() {
      this.pageQuery.pageNum = 1
      const minPrice = this.dataForm.currentPriceBeginInp ? this.dataForm.currentPriceBeginInp * 100 : undefined
      const maxPrice = this.dataForm.currentPriceEndInp ? this.dataForm.currentPriceEndInp * 100 : undefined
      this.searchParam = {
        spuId: this.dataForm.prodId || undefined,
        partyCode: this.dataForm.prodCode || undefined,
        keyword: this.dataForm.prodName || undefined,
        primaryCategoryId: this.dataForm.primaryCategoryId || undefined, // 平台一级分类id
        secondaryCategoryId: this.dataForm.secondaryCategoryId || undefined, // 平台二级分类id
        categoryId: this.dataForm.categoryId || undefined,
        minSaleNum: this.dataForm.saleBeginInp || undefined,
        maxSaleNum: this.dataForm.saleEndInp || undefined,
        minPrice: minPrice,
        maxPrice: maxPrice,
        isCompose: this.dataForm.currentyuPreSaleStatus === '' ? undefined : this.dataForm.currentyuPreSaleStatus,
        dataType: parseInt(this.prodStatusRadio)
      }
      this.getDataList()
    },
    // 获取商品列表
    getDataList() {
      this.pageLoading = true
      api.page({ ...this.pageQuery, ...this.searchParam }).then(pageVO => {
        this.pageVO = pageVO
        this.pageVO.list.forEach(item => {
          item.exhibitionProdName = item.name
          item.exhibitionSeq = item.seq
          item.priceFee = item.priceFee / 100
          item.marketPriceFee = item.marketPriceFee / 100
        })
        this.pageLoading = false
      })
    },
    // 获取商品目录
    getProdCategoryList() {
      this.pageLoading = true
      categoryApi.categoryPage({}).then(pageVO => {
        this.categoryList = treeDataTranslate(pageVO, 'categoryId', 'parentId')
        this.pageLoading = false
      })
    },
    // 监听分类选择的变化
    handleSelectCategoryChange(val) {
      this.dataForm.primaryCategoryId = undefined
      this.dataForm.secondaryCategoryId = undefined
      this.dataForm.categoryId = undefined
      if (val.length === 1) { // 长度为1，表明当前选择的是一级分类（点击了一级分类）
        this.dataForm.primaryCategoryId = val[0] // 平台一级分类id
      } else if (val.length === 2) { // 长度为2，表明当前选择的是二级分类（点击了二级分类）
        this.dataForm.secondaryCategoryId = val[1] // 平台二级分类id
      } else if (val.length === 3) { // 长度为3，表明当前选择的是三级分类（点击了三级分类）
        this.dataForm.categoryId = val[2] // 平台三级分类id
      }
    },
    // 切换不同状态的商品展示
    // 0：代表全部商品
    // 1：在售中的商品
    // 2：已售完的商品
    // 3：已下架的商品
    switchProdList() {
      const dataType = parseInt(this.prodStatusRadio)
      this.checkAll = false
      this.pageQuery.pageNum = 1
      this.searchParam = {
        dataType
      }
      this.getDataList()
    },
    // 跳转发布商品页
    toReleaseProdPage(spuId) {
      this.prodDetailsVisible = true
      this.$nextTick(() => {
        this.$refs.prodDetails.init(spuId)
      })
    },
    // 监听表格选中
    handleSelectionChange(value) {
      const checkedCount = value.length
      this.checkedCount = checkedCount
      this.checkAll = checkedCount === this.pageVO.list.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.pageVO.list.length
      // 提取出商品id列表
      const selectedProdList = []
      if (value.length > 0) {
        value.forEach(item => {
          selectedProdList.push(item.spuId)
        })
      }
      this.selectedProdList = selectedProdList
    },
    // 表格全选/全不选
    handleCheckAllChange() {
      this.$refs.prodInfTable.toggleAllSelection()
      this.isIndeterminate = false
    },
    // 条件排序
    // priceFee:当前价
    // marketPriceFee:市场价
    // saleNum:累计销量
    // totalStock:库存
    // seq:序号
    // createTime:创建时间
    conditionSort(column) {
      this.closePopover()
      this.pageQuery.pageNum = 1
      const orderMode = column.order === 'ascending' ? 1 : column.order === 'descending' ? 0 : undefined
      if (column.prop === 'priceFee') {
        this.searchParam = {
          priceFeeSort: orderMode
        }
      } else if (column.prop === 'marketPriceFee') {
        this.searchParam = {
          marketPriceFeeSort: orderMode
        }
      } else if (column.prop === 'saleNum') {
        this.searchParam = {
          saleNumSort: orderMode
        }
      } else if (column.prop === 'totalStock') {
        this.searchParam = {
          stockSort: orderMode
        }
      } else if (column.prop === 'seq') {
        this.searchParam = {
          seqSort: orderMode
        }
      } else if (column.prop === 'createTime') {
        this.searchParam = {
          createTimeSort: orderMode
        }
      }
      this.getDataList()
    },
    // 改变商品状态
    // onTheShelf：上架
    // offTheShelf：下架
    // changeProdStatus(status) {
    //   // 如果没选商品，提示选择商品
    //   if (this.selectedProdList.length <= 0) {
    //     this.$message({
    //       message: '请选择商品',
    //       type: 'warning'
    //     })
    //     return
    //   }

    //   if (status === 'onTheShelf') {
    //     this.prodSale(this.selectedProdList)
    //   } else if (status === 'offTheShelf') {
    //     this.prodNotSale(this.selectedProdList)
    //   }
    // },
    // 商品上架
    // prodSale(spuIdOrSpuIds) {
    //   let param = {}
    //   if (typeof (spuIdOrSpuIds) === 'number') {
    //     param = {
    //       status: 1,
    //       spuId: spuIdOrSpuIds
    //     }
    //   } else if (spuIdOrSpuIds.length > 1) {
    //     param = {
    //       status: 1,
    //       spuIds: spuIdOrSpuIds
    //     }
    //   } else {
    //     param = {
    //       status: 1,
    //       spuId: spuIdOrSpuIds[0]
    //     }
    //   }
    //   api.updateProdStatus(param).then(() => {
    //     this.$message({
    //       message: '商品上架成功',
    //       type: 'success',
    //       duration: 1500,
    //       onClose: () => this.getDataList()
    //     })
    //   })
    // },
    // // 商品下架
    // prodNotSale(spuIdOrSpuIds) {
    //   let param = {}
    //   if (typeof (spuIdOrSpuIds) === 'number') {
    //     param = {
    //       status: 0,
    //       spuId: spuIdOrSpuIds
    //     }
    //   } else if (spuIdOrSpuIds.length > 1) {
    //     param = {
    //       status: 0,
    //       spuIds: spuIdOrSpuIds
    //     }
    //   } else {
    //     param = {
    //       status: 0,
    //       spuId: spuIdOrSpuIds[0]
    //     }
    //   }
    //   api.updateProdStatus(param).then(() => {
    //     this.$message({
    //       message: '商品下架成功',
    //       type: 'success',
    //       duration: 1500,
    //       onClose: () => this.getDataList()
    //     })
    //   })
    // },

    /**
     * 商品违规下架操作
     */
    offlineManageHandle(row, key) {
      this.operateDialogVisible = true
      this.spuId = row.spuId
      const confirmTips = '确定对商品 [ ' + row.exhibitionProdName + ' ] 进行下架操作？请填写下架理由：'
      if (key === 'offline') {
        this.$nextTick(() => {
          this.$refs.offlineData.init('offline', confirmTips)
        })
      } else if (key === 'offlineManage') {
        this.getOfflineDetailByCouponId(row.spuId)
      }
    },
    /**
     * 获取最新下线商品的事件
     */
    getOfflineDetailByCouponId(spuId) {
      api.getOfflineDetailByProdId(spuId).then((data) => {
        this.offlineDetail = data
        this.$nextTick(() => {
          this.$refs.offlineData.init('offlineManage', '', data)
        })
      })
    },
    // 下线
    onOrOffTheShelf(data) {
      const param = {
        handleId: this.spuId,
        offlineReason: data
      }
      api.onOrOffTheShelf(param).then(() => {
        this.operateDialogVisible = false
        this.$message({
          message: '商品已下架',
          type: 'success',
          duration: 1000,
          onClose: () => {
            this.$emit('refreshDataList')
          }
        })
      })
    },
    /**
     * 上架申请审核
     */
    offlineDataSubmit(data) {
      const param = {
        handleId: this.spuId,
        eventId: data.eventId,
        status: data.status, // 审核状态
        refuseReason: data.refuseReason // 拒绝理由
      }
      api.examineProdOnShelfApply(param).then(() => {
        this.$message({
          message: '提交成功',
          type: 'success',
          duration: 1000,
          onClose: () => {
            this.operateDialogVisible = false
            this.showOfflineManage = false
            this.getDataList()
          }
        })
      })
    },

    // 商品删除
    prodDelete(spuId) {
      this.$confirm('是否确认删除该商品？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.deleteById(spuId).then(() => {
          this.$message({
            message: '商品删除成功',
            type: 'success',
            duration: 1000,
            onClose: () => this.getDataList()
          })
        })
      }).catch(() => { })
    },
    // 移入表格行
    enterTableRow(row) {
      this.currentMoveSpuId = row.spuId
    },
    // 移出表格行
    leaveTableRow() {
      this.currentMoveSpuId = ''
    },
    // 获取弹出弹窗项信息
    getCurrentSpuId(spuId, currentShowPopover) {
      this.currentClickSpuId = spuId
      this.currentShowPopover = currentShowPopover
    },
    // 清空选中项弹窗信息
    clearShowPopoverInf() {
      this.currentClickSpuId = ''
      this.currentShowPopover = ''
    },
    // 获取商品详情
    getProdDetail(spuId, currentShowPopover) {
      api.get(spuId).then(data => {
        this.currentModifyProdSkuList = data.skus
        this.currentModifyProdSkuList.forEach(item => {
          item.originalStock = item.stock
          item.priceFee = parseFloat(this.bigActualTotal(parseFloat(item.priceFee), 100))
          item.oldPriceFee = item.priceFee
          item.marketPriceFee = parseFloat(this.bigActualTotal(parseFloat(item.marketPriceFee), 100))
        })
      })
    },

    // 精度运算-乘法
    bigProductTotalAmount(a, b) {
      return new Big(a).times(b).valueOf()
    },
    // 精度运算-除法
    bigActualTotal(a, b) {
      if (a == null) {
        return ''
      }
      return new Big(a).div(b).valueOf()
    },

    // 关闭弹窗后,初始化数据
    initProdInf(row) {
      row.name = row.exhibitionProdName
      row.seq = row.exhibitionSeq
      this.clearShowPopoverInf()
    },
    // 关闭popover弹窗
    closePopover() {
      this.$refs.closepopover.click()
    },
    // 修改商品名称
    modifyProdName(row) {
      if (row.name.trim() === '') {
        this.$message({
          message: '请输入商品名称',
          type: 'warning'
        })
        return
      } else if (row.name === row.exhibitionProdName) {
        this.closePopover()
        return
      }
      const param = {
        spuId: row.spuId,
        name: row.name
      }
      api.updatePartProdStatus({ ...param }).then(() => {
        this.$message({
          message: '修改成功',
          type: 'success',
          duration: 1500,
          onClose: () => {
            this.getDataList()
            this.closePopover()
          }
        })
      })
    },
    // 修改商品价格
    modifyProdPrice(spuId, skus) {
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
        this.closePopover()
        return
      }
      // 把需要传递的参数,单独取出来
      for (let i = 0; i < skus.length; i++) {
        if (skus[i].priceFee === '') {
          this.$message({
            message: '请输入商品价格',
            type: 'warning'
          })
          return
        } else if (parseFloat(skus[i].priceFee) < 0.01) {
          this.$message({
            message: '商品价格不能小于0.01',
            type: 'warning'
          })
          return
        } else {
          skuList.push({
            priceFee: parseFloat(skus[i].priceFee * 100),
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
        this.$message({
          message: '修改成功',
          type: 'success',
          duration: 1500,
          onClose: () => {
            this.getDataList()
            this.closePopover()
          }
        })
      })
    },
    // 修改商品库存
    modifyProdStock(spuId, skus) {
      const skuList = []
      let totalChangeStock = 0
      for (let i = 0; i < skus.length; i++) {
        let changeStock = 0
        if (skus[i].stock === '') {
          this.$message({
            message: '请输入商品库存',
            type: 'warning'
          })
          return
        } else if (skus[i].stock < skus[i].originalStock) {
          this.$message({
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
        this.closePopover()
        return
      }
      const param = {
        spuId,
        changeStock: totalChangeStock,
        skuList
      }
      api.updatePartProdStatus({ ...param }).then(() => {
        this.$message({
          message: '修改成功',
          type: 'success',
          duration: 1500,
          onClose: () => {
            this.getDataList()
            this.closePopover()
          }
        })
      })
    },
    // 取消修改商品名
    cancelModifyProdNameBtn(scope) {
      scope._self.$refs[`popover-${scope.$index}`].doClose()
    },
    // 修改序号
    modifySeq(scope) {
      if (scope.row.seq === '') {
        this.$message({
          message: '请输入序号',
          type: 'warning'
        })
        return
      } else if (scope.row.exhibitionSeq === scope.row.seq) {
        this.closePopover()
        return
      }
      const param = {
        spuId: scope.row.spuId,
        seq: scope.row.seq
      }
      api.updatePartProdStatus({ ...param }).then(() => {
        this.$message({
          message: '修改成功',
          type: 'success',
          duration: 1500,
          onClose: () => {
            this.getDataList()
            this.closePopover()
          }
        })
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageQuery.pageSize = val
      this.pageQuery.pageNum = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageQuery.pageNum = val
      this.getDataList()
    }
  }
}
</script>

<style lang="scss">
.mod-prod-list {
  // 1.筛选条件搜索
  .screening-conditions {
    padding: 20px;
    background: #f9f9f9;
    margin-bottom: 30px;
    .el-form-item {
      display: inline-block;
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
      margin-left: 20px;
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
      .el-table-column--selection {
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
