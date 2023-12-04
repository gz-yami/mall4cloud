<template>
  <div class="page-index-img">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button
        v-permission="['multishop:indexImg:save']"
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
      stripe
      fit
      row-key="imgId"
      highlight-current-row
      style="width: 100%;"
    >
      <!-- 图片 -->
      <el-table-column
        label="图片"
        prop="imgUrl"
        align="center"
        width="300px"
      >
        <template #default="{row}">
          <img
            :src="(row.imgUrl).indexOf('http')===-1 ? resourcesUrl + row.imgUrl : row.imgUrl"
            class="img"
          >
        </template>
      </el-table-column>
      <!-- 顺序 -->
      <el-table-column
        label="顺序"
        prop=""
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.seq }}</span>
        </template>
      </el-table-column>
      <!-- 状态 -->
      <el-table-column
        label="状态"
        prop="status"
        align="center"
      >
        <template #default="{row}">
          <el-tag
            v-if="row.status === 0"
            type="danger"
          >
            {{ $t("product.category.disable") }}
          </el-tag>
          <el-tag
            v-else
          >
            {{ $t("product.category.normal") }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 平台 -->
      <el-table-column
        label="平台"
        prop="icon"
        align="center"
      >
        <template #default="{row}">
          <span>{{ ['移动端', 'PC端'][row.imgType] }}</span>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column
        :label="$t('table.actions')"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="{row}">
          <el-button
            v-permission="['multishop:indexImg:update']"
            type="primary"
            link
            @click="addOrUpdateHandle(row.imgId)"
          >
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            v-permission="['multishop:indexImg:delete']"
            type="primary"
            link
            @click="deleteHandle(row.imgId)"
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
import * as api from '@/api/platform/index-img'
import { onMounted, reactive } from 'vue'
import AddOrUpdate from './components/add-or-update.vue'

const Data = reactive({
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
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
})
const { resourcesUrl, pageQuery, pageVO, pageLoading, addOrUpdateVisible } = toRefs(Data)

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
const addOrUpdateHandle = (imgId) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(imgId)
  })
}

const deleteHandle = (imgId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => deleteById(imgId))
}

const deleteById = (imgId) => {
  api.deleteById(imgId).then(() => {
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
.page-index-img {
  .img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 300px;
  }
}
</style>
