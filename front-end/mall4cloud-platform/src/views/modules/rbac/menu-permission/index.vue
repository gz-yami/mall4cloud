<template>
  <div class="page-menu-permission">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button
        class="filter-item"
        @click="getPage()"
      >
        {{ $t('table.search') }}
      </el-button>
      <el-button
        v-permission="['rbac:menuPermission:save']"
        type="primary"
        class="filter-item"
        @click="addOrUpdateHandle()"
      >
        {{ $t('table.create') }}
      </el-button>
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
      <el-table-column
        :label="$t('rbac.menuPermission.menuTitle')"
        prop="menuTitle"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.menuTitle }}</span>
        </template>
      </el-table-column>
      <!-- 资源名称 -->
      <el-table-column
        :label="$t('rbac.menuPermission.name')"
        prop="name"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <!-- 权限对应的编码 -->
      <el-table-column
        :label="$t('rbac.menuPermission.permission')"
        prop="permission"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.permission }}</span>
        </template>
      </el-table-column>
      <!-- 资源对应服务器路径 -->
      <el-table-column
        :label="$t('rbac.menuPermission.uri')"
        prop="uri"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.uri }}</span>
        </template>
      </el-table-column>
      <!-- 请求方法 -->
      <el-table-column
        :label="$t('rbac.menuPermission.method')"
        prop="method"
        align="center"
      >
        <template #default="{row}">
          <span>{{ ['', 'GET', 'POST', 'PUT', 'DELETE'][row.method] }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.actions')"
        align="center"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template #default="{row}">
          <el-button
            v-permission="['rbac:menuPermission:update']"
            type="primary"
            link
            @click="addOrUpdateHandle(row.menuPermissionId)"
          >
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            v-permission="['rbac:menuPermission:delete']"
            type="primary"
            link
            @click="deleteHandle(row.menuPermissionId)"
          >
            {{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页条 -->
    <pagination
      v-show="pageVO.total>0"
      v-model:page="pageQuery.pageNum"
      v-model:limit="pageQuery.pageSize"
      :total="pageVO.total"
      @pagination="getPage()"
    />
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update
      v-if="addOrUpdateVisible"
      ref="addOrUpdateRef"
      @refresh-data-list="getPage()"
    />
  </div>
</template>

<script setup>
import * as api from '@/api/rbac/menu-permission'
import { onMounted, reactive } from 'vue'
import AddOrUpdate from './components/add-or-update.vue'

const Data = reactive({
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
})
const { pageQuery, pageVO, pageLoading, addOrUpdateVisible } = toRefs(Data)

onMounted(() => {
  getPage()
})

const getPage = () => {
  Data.pageLoading = true
  api.page({ ...Data.pageQuery, ...Data.searchParam }).then(pageVO => {
    Data.pageVO = pageVO
    Data.pageLoading = false
  })
}

const addOrUpdateRef = ref()
const addOrUpdateHandle = (menuPermissionId) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(menuPermissionId)
  })
}

const deleteHandle = (menuPermissionId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => deleteById(menuPermissionId))
}

const deleteById = (menuPermissionId) => {
  api.deleteById(menuPermissionId).then(() => {
    ElMessage({
      message: $t('table.actionSuccess'),
      type: 'success',
      duration: 1500,
      onClose: () => getPage()
    })
  })
}
</script>

<style lang="scss" scoped>

</style>
