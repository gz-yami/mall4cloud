<template>
  <div class="app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button size="mini" icon="el-icon-search" class="filter-item" @click="getPage()">{{ $t('table.search') }}</el-button>
      <el-button v-permission="['multishop:notice:save']" size="mini" icon="el-icon-plus" type="primary" class="filter-item" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
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
      <!-- 发布时间 -->
      <el-table-column :label="$t('platform.notice.publishTime')" prop="publishTime" align="center">
        <template slot-scope="{row}">
          <span>{{ row.publishTime }}</span>
        </template>
      </el-table-column>
      <!-- 公告标题 -->
      <el-table-column :label="$t('platform.notice.title')" prop="title" align="center">
        <template slot-scope="{row}">
          <span>{{ row.title }}</span>
        </template>
      </el-table-column>
      <!-- 状态(1:公布 0:撤回) -->
      <el-table-column :label="$t('platform.notice.status')" prop="status" align="center">
        <template slot-scope="{row}">
          <el-tag :type="row.status === 0 ? 'danger' : ''">{{ ['撤销','公布'][row.status] }}</el-tag>
        </template>
      </el-table-column>
      <!-- 是否置顶 -->
      <el-table-column :label="$t('platform.notice.isTop')" prop="isTop" align="center">
        <template slot-scope="{row}">
          <el-tag :type="row.isTop === 0 ? 'warning' : ''">{{ ['否','是'][row.isTop] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-permission="['multishop:notice:update']" type="text" @click="addOrUpdateHandle(row.id)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button v-permission="['multishop:notice:delete']" type="text" @click="deleteHandle(row.id)">
            {{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页条 -->
    <pagination v-if="pageVO.total>0" :total="pageVO.total" :page.sync="pageQuery.pageNum" :limit.sync="pageQuery.pageSize" @pagination="getPage()" />
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getPage()" />
  </div>
</template>

<script>
import permission from '@/directive/permission/index.js'
import Pagination from '@/components/Pagination'
import AddOrUpdate from './add-or-update.vue'
import * as api from '@/api/platform/notice'

export default {
  name: '',
  components: { Pagination, AddOrUpdate },
  directives: { permission },
  data() {
    return {
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
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    deleteHandle(id) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => this.deleteById(id))
    },
    deleteById(id) {
      api.deleteById(id).then(() => {
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
