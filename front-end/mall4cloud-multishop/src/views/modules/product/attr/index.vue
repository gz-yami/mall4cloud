<template>
  <div class="page-attr">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-form
        :inline="true"
        :model="pageQuery"
        class="demo-form-inline"
      >
        <el-form-item label="属性名称">
          <el-input
            v-model="pageQuery.name"
            placeholder="属性名称"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="getPage()"
          >
            查询
          </el-button>
          <el-button
            @click="clearSearchInfo()"
          >
            清空
          </el-button>
        </el-form-item>
      </el-form>
      <el-button
        v-permission="['product:attr:save']"
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
      <!-- 属性名称 -->
      <el-table-column
        :label="$t('product.attr.name')"
        prop="name"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <!-- 属性值 -->
      <el-table-column
        label="属性值"
        prop="attrType"
        align="center"
      >
        <template #default="{row}">
          <el-tag
            v-for="(item,index) in row.attrValues"
            :key="index"
          >
            {{ item.value }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column
        :label="$t('table.actions')"
        align="center"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template #default="{row}">
          <el-button
            v-permission="['product:attr:update']"
            type="primary"
            link
            @click="addOrUpdateHandle(row.attrId)"
          >
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            v-permission="['product:attr:delete']"
            type="primary"
            link
            @click="deleteHandle(row.attrId)"
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
import * as api from '@/api/product/attr'
import { onMounted, reactive } from 'vue'
import AddOrUpdate from './components/add-or-update.vue'

const Data = reactive({
  // 查询的参数
  pageQuery: {
    pageSize: 10,
    pageNum: 1,
    name: undefined,
    attrType: undefined
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
  addOrUpdateVisible: false,
  options: [
    { value: '销售属性', label: 0 },
    { value: '基本属性', label: 1 }
  ]
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
const addOrUpdateHandle = (attrId) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(attrId)
  })
}

const deleteHandle = (attrId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => deleteById(attrId))
}

const deleteById = (attrId) => {
  api.deleteById(attrId).then(() => {
    ElMessage({
      message: $t('table.actionSuccess'),
      type: 'success',
      duration: 1500,
      onClose: () => getPage()
    })
  })
}

// 清空搜索内容
const clearSearchInfo = () => {
  Data.pageQuery.name = ''
  Data.pageQuery.attrType = ''
}

</script>

<style lang="scss" scoped>

</style>
