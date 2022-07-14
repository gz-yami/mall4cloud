<template>
  <div class="app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <!-- <el-button size="mini" icon="el-icon-search" class="filter-item" @click="getPage()">{{ $t('table.search') }}</el-button> -->
      <el-button size="mini" icon="el-icon-plus" type="primary" class="filter-item" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
    </div>

    <!-- 列表相关区域 -->
    <el-table
      v-loading="pageLoading"
      :data="pageVO.list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <!-- 创建时间 -->
      <el-table-column :label="$t('table.createTime')" prop="createTime" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <!-- 更新时间 -->
      <el-table-column :label="$t('table.updateTime')" prop="updateTime" align="center">
        <template slot-scope="{row}">
          <span>{{ row.updateTime }}</span>
        </template>
      </el-table-column>
      <!-- 品牌ID -->
      <el-table-column :label="$t('product.spu.brandId')" prop="brandId" align="center">
        <template slot-scope="{row}">
          <span>{{ row.brandId }}</span>
        </template>
      </el-table-column>
      <!-- 分类ID -->
      <el-table-column :label="$t('product.spu.categoryId')" prop="categoryId" align="center">
        <template slot-scope="{row}">
          <span>{{ row.categoryId }}</span>
        </template>
      </el-table-column>
      <!-- spu名称 -->
      <el-table-column :label="$t('product.spu.name')" prop="name" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <!-- 卖点 -->
      <el-table-column :label="$t('product.spu.sellingPoint')" prop="sellingPoint" align="center">
        <template slot-scope="{row}">
          <span>{{ row.sellingPoint }}</span>
        </template>
      </el-table-column>
      <!-- 商品介绍主图 多个图片逗号分隔 -->
      <el-table-column :label="$t('product.spu.imgUrls')" prop="imgUrls" align="center">
        <template slot-scope="{row}">
          <img :src="(row.imgUrls).indexOf('http')===-1 ? resourcesUrl + row.imgUrls : row.imgUrls">
        </template>
      </el-table-column>
      <!-- 售价，整数方式保存 -->
      <el-table-column :label="$t('product.spu.priceFee')" prop="priceFee" align="center">
        <template slot-scope="{row}">
          <span>{{ row.priceFee }}</span>
        </template>
      </el-table-column>
      <!-- 市场价，整数方式保存 -->
      <el-table-column :label="$t('product.spu.marketPriceFee')" prop="marketPriceFee" align="center">
        <template slot-scope="{row}">
          <span>{{ row.marketPriceFee }}</span>
        </template>
      </el-table-column>
      <!-- 状态 1:enable, 0:disable, -1:deleted -->
      <el-table-column :label="$t('product.spu.status')" prop="status" align="center">
        <template slot-scope="{row}">
          <el-tag v-if="row.status === 0" size="small" type="danger">{{ $t("product.category.offline") }}</el-tag>
          <el-tag v-else size="small">{{ $t("product.category.normal") }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <!-- 编辑 -->
          <el-button type="text" @click="addOrUpdateHandle(row.spuId)">
            {{ $t('table.edit') }}
          </el-button>
          <!-- 上架 -->
          <el-button
            v-if="row.status === 0"
            type="text"
            @click="enableOrDisable(row.categoryId,row.status)"
          >
            {{ $t('action.putOnShelf') }}
          </el-button>
          <!-- 下架 -->
          <el-button
            v-if="row.status === 1"
            type="text"
            @click="enableOrDisable(row.categoryId,row.status)"
          >
            {{ $t('action.offShelf') }}
          </el-button>
          <el-button type="text" @click="deleteHandle(row.spuId)">
            {{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页条 -->
    <pagination v-show="pageVO.total>0" :total="pageVO.total" :page.sync="pageQuery.pageNum" :limit.sync="pageQuery.pageSize" @pagination="getPage()" />
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getPage()" />
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import AddOrUpdate from './add-or-update.vue'
import * as api from '@/api/product/spu'

export default {
  name: '',
  components: { Pagination, AddOrUpdate },
  data() {
    return {
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
      // 查询的参数
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
      // 查询参数
      searchParam: {
      },
      addOrUpdateVisible: false
    }
  },
  mounted() {
    this.getPage()
  },
  methods: {
    getPage() {
      this.pageLoading = true
      api.page({ ...this.pageQuery, ...this.searchParam }).then(pageVO => {
        this.pageVO = pageVO
        console.log('获取商品列表页数据pageVO:', pageVO)
        this.pageLoading = false
      })
    },
    addOrUpdateHandle(spuId) {
      // this.addOrUpdateVisible = true
      // this.$nextTick(() => {
      //   this.$refs.addOrUpdate.init(spuId)
      // })
      this.$router.push('/product/prod_info?spuId=' + spuId)
    },
    deleteHandle(spuId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => this.deleteById(spuId))
    },
    deleteById(spuId) {
      api.deleteById(spuId).then(() => {
        this.$message({
          message: this.$t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => this.getPage()
        })
      })
    }
  }
}
</script>
