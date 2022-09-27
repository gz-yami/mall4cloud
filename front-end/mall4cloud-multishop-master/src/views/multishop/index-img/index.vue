<template>
  <div class="app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button v-permission="['multishop:indexImg:save']" size="mini" icon="el-icon-plus" type="primary" class="filter-item" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
    </div>

    <!-- 列表相关区域 -->
    <el-table
      v-loading="pageLoading"
      :data="pageVO.list"
      border
      stripe
      fit
      row-key="imgId"
      highlight-current-row
      style="width: 100%;"
    >
      <!-- 图片 -->
      <el-table-column label="图片" prop="imgUrl" align="center" width="300px">
        <template slot-scope="{row}">
          <img :src="(row.imgUrl).indexOf('http')===-1 ? resourcesUrl + row.imgUrl : row.imgUrl" class="img">
        </template>
      </el-table-column>
      <!-- 顺序 -->
      <el-table-column label="顺序" prop="" align="center">
        <template slot-scope="{row}">
          <span>{{ row.seq }}</span>
        </template>
      </el-table-column>
      <!-- 状态 -->
      <el-table-column label="状态" prop="status" align="center">
        <template slot-scope="{row}">
          <el-tag v-if="row.status === 0" size="small" type="danger">{{ $t("product.category.offline") }}</el-tag>
          <el-tag v-else size="small">{{ $t("product.category.normal") }}</el-tag>
        </template>
      </el-table-column>
      <!-- 平台 -->
      <el-table-column label="平台" prop="icon" align="center">
        <template slot-scope="{row}">
          <span>{{ ['移动端', 'PC端'][row.imgType] }}</span>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column :label="$t('table.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button
            v-permission="['multishop:indexImg:update']"
            type="text"
            @click="addOrUpdateHandle(row.imgId)"
          >
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            v-permission="['multishop:indexImg:delete']"
            type="text"
            @click="deleteHandle(row.imgId)"
          >
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
import permission from '@/directive/permission/index.js'
import Pagination from '@/components/Pagination'
import AddOrUpdate from './add-or-update.vue'
import * as api from '@/api/multishop/index-img'
export default {
  components: { Pagination, AddOrUpdate },
  directives: { permission },
  data() {
    return {
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
      // 查询的参数
      pageQuery: {
        pageSize: 10,
        pageNum: 1,
        name: null,
        attrType: null
      },
      // 返回参数
      pageVO: {
        list: [], // 返回的列表
        total: 0, // 一共多少条数据
        pages: 0 // 一共多少页
      },
      // loading
      pageLoading: true,
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
    addOrUpdateHandle(imgId) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(imgId)
      })
    },
    deleteHandle(imgId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => this.deleteById(imgId))
    },
    deleteById(imgId) {
      api.deleteById(imgId).then(() => {
        this.$message({
          message: this.$t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => this.getPage()
        })
      })
    },

    /**
     * 搜索
     */
    search() {

    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  .img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 300px;
  }
}
</style>

