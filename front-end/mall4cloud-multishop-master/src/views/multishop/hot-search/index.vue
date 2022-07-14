<template>
  <div class="app-container mod-hot-search">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-form :inline="true" :model="pageQuery" class="demo-form-inline">
        <el-form-item label="热搜标题">
          <el-input v-model="pageQuery.title" size="mini" placeholder="热搜标题" />
        </el-form-item>
        <el-form-item label="热搜内容">
          <el-input v-model="pageQuery.content" size="mini" placeholder="热搜内容" />
        </el-form-item>
        <el-form-item label="启用状态">
          <el-select v-model="pageQuery.status" size="mini" placeholder="启用状态" clearable>
            <el-option label="启用" value="1" />
            <el-option label="未启用" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-search" @click="getSearchList()">查询</el-button>
          <el-button size="mini" icon="el-icon-delete" @click="clearSearchInfo()">清空</el-button>
        </el-form-item>
      </el-form>
      <el-button v-permission="['multishop:hotSearch:save']" size="mini" icon="el-icon-plus" type="primary" class="filter-item" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
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
      <!-- 热搜标题 -->
      <el-table-column :label="$t('multishop.hotSearch.title')" prop="title" align="center">
        <template slot-scope="{row}">
          <span>{{ row.title }}</span>
        </template>
      </el-table-column>
      <!-- 内容 -->
      <el-table-column :label="$t('multishop.hotSearch.content')" prop="content" align="center">
        <template slot-scope="{row}">
          <span>{{ row.content }}</span>
        </template>
      </el-table-column>
      <!-- 创建时间 -->
      <el-table-column :label="$t('multishop.hotSearch.createDate')" prop="createTime" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <!-- 顺序 -->
      <el-table-column :label="$t('multishop.hotSearch.seq')" prop="seq" align="center">
        <template slot-scope="{row}">
          <span>{{ row.seq }}</span>
        </template>
      </el-table-column>
      <!-- 状态 0下线 1上线 -->
      <el-table-column :label="$t('multishop.hotSearch.status')" prop="status" align="center">
        <template slot-scope="{row}">
          <!-- <span>{{ row.status }}</span> -->
          <el-tag v-if="row.status === 0" size="small" type="danger">{{ '未启用' }}</el-tag>
          <el-tag v-else size="small">{{ '启用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button
            v-permission="['multishop:hotSearch:update']"
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="addOrUpdateHandle(row.hotSearchId)"
          >
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            v-permission="['multishop:hotSearch:delete']"
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="deleteHandle(row.hotSearchId)"
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
import * as api from '@/api/multishop/hot-search'

export default {
  name: '',
  components: { Pagination, AddOrUpdate },
  directives: { permission },
  data() {
    return {
      // 查询的参数
      pageQuery: {
        pageSize: 10,
        pageNum: 1,
        title: null,
        content: null,
        status: null
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
    addOrUpdateHandle(hotSearchId) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(hotSearchId)
      })
    },
    deleteHandle(hotSearchId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => this.deleteById(hotSearchId))
    },
    deleteById(hotSearchId) {
      api.deleteById(hotSearchId).then(() => {
        this.$message({
          message: this.$t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => this.getPage()
        })
      })
    },
    getSearchList() {
      this.pageQuery.pageNum = 1
      // 若请求参数某项没值，则不传此参数
      this.pageQuery.title = this.pageQuery.title || null
      this.pageQuery.content = this.pageQuery.content || null
      this.pageQuery.status = this.pageQuery.status || null
      this.getPage()
    },
    clearSearchInfo() {
      // 若请求参数某项没值，则不传此参数
      this.pageQuery.title = null
      this.pageQuery.content = null
      this.pageQuery.status = null
    }
  }
}
</script>

<style lang="scss" scoped>
.mod-hot-search {
  .el-form--inline {
    .el-form-item {
      margin-right: 20px;
    }
  }
}
</style>
