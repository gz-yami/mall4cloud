<template>
  <div class="app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <!-- <el-button size="mini" icon="el-icon-search" class="filter-item" @click="getPage()">{{ $t('table.search') }}</el-button> -->
      <el-button v-permission="['product:brand:save']" size="mini" icon="el-icon-plus" type="primary" class="filter-item" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
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
      <!-- 品牌名称 -->
      <el-table-column :label="$t('product.brand.name')" prop="name" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <!-- 品牌描述 -->
      <el-table-column :label="$t('product.brand.desc')" prop="desc" align="center">
        <template slot-scope="{row}">
          <span>{{ row.desc }}</span>
        </template>
      </el-table-column>
      <!-- 品牌logo图片 -->
      <el-table-column :label="$t('product.brand.imgUrl')" prop="imgUrl" align="center" width="200">
        <template slot-scope="{row}">
          <img :src="(row.imgUrl).indexOf('http')===-1 ? resourcesUrl + row.imgUrl : row.imgUrl" class="brand-logo-img">
        </template>
      </el-table-column>
      <!-- 检索首字母 -->
      <el-table-column :label="$t('product.brand.firstLetter')" prop="firstLetter" align="center" width="100">
        <template slot-scope="{row}">
          <span>{{ row.firstLetter }}</span>
        </template>
      </el-table-column>
      <!-- 排序 -->
      <el-table-column :label="$t('product.brand.seq')" prop="seq" align="center" width="100">
        <template slot-scope="{row}">
          <span>{{ row.seq }}</span>
        </template>
      </el-table-column>
      <!-- 状态 1:enable, 0:disable, -1:deleted -->
      <el-table-column :label="$t('product.brand.status')" prop="status" align="center" width="150">
        <template slot-scope="{row}">
          <el-tag v-if="row.status === 0" size="small" type="danger">{{ $t("product.category.offline") }}</el-tag>
          <el-tag v-else size="small">{{ $t("product.category.normal") }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-permission="['product:brand:update']" type="text" @click="addOrUpdateHandle(row.brandId)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            v-if="row.status === 0"
            type="text"
            @click="enableOrDisable(row.brandId,row.status)"
          >
            <!-- 上架 -->
            {{ $t('action.putOnShelf') }}
          </el-button>
          <el-button
            v-if="row.status === 1"
            type="text"
            @click="enableOrDisable(row.brandId,row.status)"
          >
            <!-- 下架 -->
            {{ $t('action.offShelf') }}
          </el-button>
          <el-button v-permission="['product:brand:delete']" type="text" @click="deleteHandle(row.brandId)">
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
import permission from '@/directive/permission/index.js'
import * as api from '@/api/product/brand'

export default {
  name: '',
  components: { Pagination, AddOrUpdate },
  directives: { permission },
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
        this.pageLoading = false
      })
    },
    addOrUpdateHandle(brandId) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(brandId)
      })
    },
    deleteHandle(brandId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => this.deleteById(brandId))
    },
    deleteById(brandId) {
      api.deleteById(brandId).then(() => {
        this.$message({
          message: this.$t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => this.getPage()
        })
      })
    },

    /**
     * 上架/下架品牌
     */
    enableOrDisable(brandId, sts) {
      var brandObj = {}
      brandObj.brandId = brandId
      brandObj.status = sts === 0 ? 1 : 0
      this.$confirm(sts === 1 ? '是否确认下架当前品牌?' : '是否确认上架当前品牌?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.enableOrDisable(brandObj).then((data) => {
          this.$message({
            message: data || '操作成功',
            type: data ? 'error' : 'success',
            duration: 1500,
            onClose: () => this.getPage()
          })
        })
      }).catch(() => { })
    }

  }
}
</script>

<style lang="scss" scoped>
.app-container {
  .brand-logo-img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 200px;
  }
}
</style>
