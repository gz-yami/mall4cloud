<template>
  <div class="page-shop-menu">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button
        class="filter-item"
        @click="getPage()"
      >
        {{ $t('table.search') }}
      </el-button>
      <el-button
        v-permission="['rbac:menu:save']"
        type="primary"
        class="filter-item"
        @click="addOrUpdateHandle()"
      >
        {{ $t('table.create') }}
      </el-button>
    </div>
    <!-- 列表相关区域 -->
    <el-table
      :data="menulist"
      border
      style="width: 100%;"
      row-key="id"
    >
      <!-- 名称 -->
      <el-table-column
        prop="meta.title"
        header-align="center"
        tree-key="id"
        width="150"
        label="名称"
      />
      <!-- 图标 -->
      <el-table-column
        header-align="center"
        align="center"
        label="图标"
      >
        <template #default="scope">
          <span v-if="scope.row.meta.icon && (scope.row.meta.icon).includes('plus')">
            <el-icon v-if="scope.row.meta.icon">
              <component
                :is="scope.row.meta.icon.slice(0, -4)"
                v-if="scope.row.meta.icon"
                class="icons"
              />
            </el-icon>

          </span>
          <span v-if="scope.row.meta.icon && !(scope.row.meta.icon).includes('plus')"><svg-icon :icon-class="scope.row.meta.icon" /></span>
          <span v-if="!scope.row.meta.icon">-</span>
        </template>
      </el-table-column>
      <!-- 类型 -->
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="类型"
      >
        <template #default="scope">
          <el-tag
            v-if="scope.row.component === ''"
          >
            {{ '目录' }}
          </el-tag>
          <el-tag
            v-else
            type="success"
          >
            {{ '菜单' }}
          </el-tag>
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
        <template #default="scope">
          <el-button
            v-permission="['rbac:menu:update']"
            type="primary"
            link
            @click="addOrUpdateHandle(scope.row.id)"
          >
            {{ '编辑' }}
          </el-button>
          <el-button
            v-permission="['rbac:menu:delete']"
            type="primary"
            link
            @click="deleteHandle(scope.row.id)"
          >
            {{ '删除' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update
      v-if="addOrUpdateVisible"
      ref="addOrUpdateRef"
      @refresh-data-list="getPage()"
    />
  </div>
</template>

<script setup>
import * as api from '@/api/rbac/menu'
import { onMounted, reactive } from 'vue'
import AddOrUpdate from './components/add-or-update.vue'

const Data = reactive({
  menulist: [],
  // loading
  pageLoading: true,
  // 查询参数
  searchParam: {
    sysType: 1
  },
  addOrUpdateVisible: false
})
const { menulist, addOrUpdateVisible } = toRefs(Data)

onMounted(() => {
  getPage()
})

const getPage = () => {
  Data.pageLoading = true
  api.menuList({ ...Data.searchParam }).then(data => {
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
    Data.menulist = treeDataTranslate(data)
    Data.pageLoading = false
  })
}

const addOrUpdateRef = ref()
const addOrUpdateHandle = (menuId) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(menuId)
  })
}

const deleteHandle = (menuId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => deleteById(menuId))
}

const deleteById = (menuId) => {
  api.deleteByIdAndSysType(menuId, 1).then(() => {
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
