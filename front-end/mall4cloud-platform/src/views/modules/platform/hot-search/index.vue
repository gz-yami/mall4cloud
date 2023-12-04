<template>
  <div class="page-hot-search">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-form
        :inline="true"
        :model="pageQuery"
        class="demo-form-inline"
      >
        <el-form-item label="热搜标题">
          <el-input
            v-model="pageQuery.title"
            placeholder="热搜标题"
          />
        </el-form-item>
        <el-form-item label="热搜内容">
          <el-input
            v-model="pageQuery.content"
            placeholder="热搜内容"
          />
        </el-form-item>
        <el-form-item label="启用状态">
          <el-select
            v-model="pageQuery.status"
            placeholder="启用状态"
            clearable
          >
            <el-option
              label="启用"
              value="1"
            />
            <el-option
              label="未启用"
              value="0"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="getSearchList()"
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
        v-permission="['platform:hotSearch:save']"
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
      <!-- 热搜标题 -->
      <el-table-column
        :label="$t('platform.hotSearch.title')"
        prop="title"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.title }}</span>
        </template>
      </el-table-column>
      <!-- 内容 -->
      <el-table-column
        :label="$t('platform.hotSearch.content')"
        prop="content"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.content }}</span>
        </template>
      </el-table-column>
      <!-- 创建时间 -->
      <el-table-column
        :label="$t('platform.hotSearch.createDate')"
        prop="createTime"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <!-- 顺序 -->
      <el-table-column
        :label="$t('platform.hotSearch.seq')"
        prop="seq"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.seq }}</span>
        </template>
      </el-table-column>
      <!-- 状态 0下线 1上线 -->
      <el-table-column
        :label="$t('platform.hotSearch.status')"
        prop="status"
        align="center"
      >
        <template #default="{row}">
          <!-- <span>{{ row.status }}</span> -->
          <el-tag
            v-if="row.status === 0"
            type="danger"
          >
            {{ '未启用' }}
          </el-tag>
          <el-tag
            v-else
          >
            {{ '启用' }}
          </el-tag>
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
            v-permission="['platform:hotSearch:update']"
            link
            type="primary"
            @click="addOrUpdateHandle(row.hotSearchId)"
          >
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            v-permission="['platform:hotSearch:delete']"
            link
            type="primary"
            @click="deleteHandle(row.hotSearchId)"
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
import * as api from '@/api/platform/hot-search'
import { onMounted } from 'vue'
import AddOrUpdate from './components/add-or-update.vue'

const Data = reactive({
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
const addOrUpdateHandle = (hotSearchId) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(hotSearchId)
  })
}

const deleteHandle = (hotSearchId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => deleteById(hotSearchId))
}

const deleteById = (hotSearchId) => {
  api.deleteById(hotSearchId).then(() => {
    ElMessage({
      message: $t('table.actionSuccess'),
      type: 'success',
      duration: 1500,
      onClose: () => getPage()
    })
  })
}

const getSearchList = () => {
  Data.pageQuery.pageNum = 1
  // 若请求参数某项没值，则不传此参数
  Data.pageQuery.title = Data.pageQuery.title || null
  Data.pageQuery.content = Data.pageQuery.content || null
  Data.pageQuery.status = Data.pageQuery.status || null
  getPage()
}

const clearSearchInfo = () => {
  // 若请求参数某项没值，则不传此参数
  Data.pageQuery.title = null
  Data.pageQuery.content = null
  Data.pageQuery.status = null
}
</script>

<style lang="scss" scoped>
</style>
