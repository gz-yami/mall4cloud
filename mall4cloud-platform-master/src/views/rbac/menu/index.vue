<template>
  <div class="mod-menu-home app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button size="mini" icon="el-icon-search" class="filter-item" @click="getPage()">{{ $t('table.search') }}</el-button>
      <el-button v-permission="['rbac:menu:save']" size="mini" icon="el-icon-plus" type="primary" class="filter-item" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
    </div>
    <!-- 列表相关区域 -->
    <el-table :data="menulist" border style="width: 100%;" row-key="id">
      <!-- 名称 -->
      <el-table-column
        prop="meta.title"
        header-align="center"
        tree-key="id"
        width="150"
        label="名称"
      />
      <!-- 图标 -->
      <el-table-column header-align="center" align="center" label="图标">
        <template slot-scope="scope">
          <span v-if="scope.row.meta.icon && (scope.row.meta.icon).includes('el-icon')"><i :class="scope.row.meta.icon || ''" /></span>
          <span v-if="scope.row.meta.icon && !(scope.row.meta.icon).includes('el-icon')"><svg-icon :icon-class="scope.row.meta.icon" /></span>
        </template>
      </el-table-column>
      <!-- 类型 -->
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="类型"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.component === ''" size="small">{{ '目录' }}</el-tag>
          <el-tag v-else size="small" type="success">{{ '菜单' }}</el-tag>
        </template>
      </el-table-column>
      <!-- 排序号 -->
      <el-table-column
        prop="seq"
        header-align="center"
        align="center"
        label="排序号"
      />
      <!-- 菜单Url -->
      <el-table-column
        prop="path"
        header-align="center"
        align="center"
        width="150"
        :show-overflow-tooltip="true"
        label="菜单Url"
      />
      <!-- 组件路径 -->
      <el-table-column
        prop="component"
        header-align="center"
        align="center"
        width="150"
        :show-overflow-tooltip="true"
        label="组件路径"
      />
      <!-- 菜单显隐 -->
      <el-table-column
        prop="hidden"
        header-align="center"
        align="center"
        label="菜单隐藏"
      />
      <!-- 操作 -->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['rbac:menu:update']"
            type="text"
            size="small"
            @click="addOrUpdateHandle(scope.row.id)"
          >{{ '编辑' }}</el-button>
          <el-button
            v-permission="['rbac:menu:delete']"
            type="text"
            size="small"
            @click="deleteHandle(scope.row.id)"
          >{{ '删除' }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getPage()" />
  </div>
</template>

<script>
import permission from '@/directive/permission/index.js'
import * as api from '@/api/rbac/menu'
import { treeDataTranslate } from '@/utils'
import AddOrUpdate from './add-or-update.vue'

export default {
  name: '',
  components: { AddOrUpdate },
  directives: { permission },
  data() {
    return {
      menulist: [],
      // loading
      pageLoading: true,
      // 系统类型
      sysType: 2,
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
      api.menuList({ ...this.searchParam }).then(data => {
        data.forEach(item => {
          if (item.component === 'Layout') {
            item.component = ''
          }
          if (item.hidden === true) {
            item.hidden = '是'
          } else {
            item.hidden = '否'
          }
        })
        this.menulist = treeDataTranslate(data)
        this.pageLoading = false
      })
    },
    addOrUpdateHandle(menuId) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(menuId)
      })
    },
    deleteHandle(menuId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => this.deleteById(menuId))
    },
    deleteById(menuId) {
      api.deleteByIdAndSysType(menuId, this.sysType).then(() => {
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
