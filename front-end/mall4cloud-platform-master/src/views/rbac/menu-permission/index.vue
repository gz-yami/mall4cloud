<template>
  <div class="app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button size="mini" icon="el-icon-search" class="filter-item" @click="getPage()">{{ $t('table.search') }}</el-button>
      <el-button v-permission="['rbac:menuPermission:save']" size="mini" icon="el-icon-plus" type="primary" class="filter-item" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
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
      <!-- 资源关联菜单 -->
      <el-table-column :label="$t('rbac.menuPermission.menuTitle')" prop="menuTitle" align="center">
        <template slot-scope="{row}">
          <span>{{ row.menuTitle }}</span>
        </template>
      </el-table-column>
      <!-- 资源名称 -->
      <el-table-column :label="$t('rbac.menuPermission.name')" prop="name" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <!-- 权限对应的编码 -->
      <el-table-column :label="$t('rbac.menuPermission.permission')" prop="permission" align="center">
        <template slot-scope="{row}">
          <span>{{ row.permission }}</span>
        </template>
      </el-table-column>
      <!-- 资源对应服务器路径 -->
      <el-table-column :label="$t('rbac.menuPermission.uri')" prop="uri" align="center">
        <template slot-scope="{row}">
          <span>{{ row.uri }}</span>
        </template>
      </el-table-column>
      <!-- 请求方法 -->
      <el-table-column :label="$t('rbac.menuPermission.method')" prop="method" align="center">
        <template slot-scope="{row}">
          <span>{{ ['', 'GET', 'POST', 'PUT', 'DELETE'][row.method] }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-permission="['rbac:menuPermission:update']" type="text" @click="addOrUpdateHandle(row.menuPermissionId)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button v-permission="['rbac:menuPermission:delete']" type="text" @click="deleteHandle(row.menuPermissionId)">
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
import * as api from '@/api/rbac/menu-permission'

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
    addOrUpdateHandle(menuPermissionId) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(menuPermissionId)
      })
    },
    deleteHandle(menuPermissionId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => this.deleteById(menuPermissionId))
    },
    deleteById(menuPermissionId) {
      api.deleteById(menuPermissionId).then(() => {
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
