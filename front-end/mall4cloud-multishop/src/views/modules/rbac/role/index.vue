<template>
  <div class="page-rbac-role">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button
        v-permission="['rbac:role:save']"
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
      <!-- 创建时间 -->
      <el-table-column
        :label="$t('table.createTime')"
        prop="createTime"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <!-- 更新时间 -->
      <el-table-column
        :label="$t('table.updateTime')"
        prop="updateTime"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.updateTime }}</span>
        </template>
      </el-table-column>
      <!-- 角色名称 -->
      <el-table-column
        :label="$t('rbac.role.roleName')"
        prop="roleName"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.roleName }}</span>
        </template>
      </el-table-column>
      <!-- 备注 -->
      <el-table-column
        :label="$t('rbac.role.remark')"
        prop="remark"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.remark }}</span>
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
            v-permission="['rbac:role:update']"
            type="text"
            @click="addOrUpdateHandle(row.roleId)"
          >
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            v-permission="['rbac:role:delete']"
            type="text"
            @click="deleteHandle(row.roleId)"
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
import * as api from '@/api/rbac/role'
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
const addOrUpdateHandle = (roleId) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(roleId)
  })
}

const deleteHandle = (roleId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => deleteById(roleId))
}

const deleteById = (roleId) => {
  api.deleteById(roleId).then(() => {
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
