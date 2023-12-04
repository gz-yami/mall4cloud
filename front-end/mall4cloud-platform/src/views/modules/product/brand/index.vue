<template>
  <div class="page-product-brand">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button
        v-permission="['product:brand:save']"
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
      <!-- 品牌名称 -->
      <el-table-column
        :label="$t('product.brand.name')"
        prop="name"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <!-- 品牌描述 -->
      <el-table-column
        :label="$t('product.brand.desc')"
        prop="desc"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.desc }}</span>
        </template>
      </el-table-column>
      <!-- 品牌logo图片 -->
      <el-table-column
        :label="$t('product.brand.imgUrl')"
        prop="imgUrl"
        align="center"
        width="200"
      >
        <template #default="{row}">
          <img
            :src="(row.imgUrl).indexOf('http')===-1 ? resourcesUrl + row.imgUrl : row.imgUrl"
            class="brand-logo-img"
          >
        </template>
      </el-table-column>
      <!-- 检索首字母 -->
      <el-table-column
        :label="$t('product.brand.firstLetter')"
        prop="firstLetter"
        align="center"
        width="100"
      >
        <template #default="{row}">
          <span>{{ row.firstLetter }}</span>
        </template>
      </el-table-column>
      <!-- 排序 -->
      <el-table-column
        :label="$t('product.brand.seq')"
        prop="seq"
        align="center"
        width="100"
      >
        <template #default="{row}">
          <span>{{ row.seq }}</span>
        </template>
      </el-table-column>
      <!-- 状态 1:enable, 0:disable, -1:deleted -->
      <el-table-column
        :label="$t('product.brand.status')"
        prop="status"
        align="center"
        width="150"
      >
        <template #default="{row}">
          <el-tag
            v-if="row.status === 0"
            type="danger"
          >
            {{ $t("product.category.offline") }}
          </el-tag>
          <el-tag
            v-else
          >
            {{ $t("product.category.normal") }}
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
            v-permission="['product:brand:update']"
            type="primary"
            link
            @click="addOrUpdateHandle(row.brandId)"
          >
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            v-if="row.status === 0"
            type="primary"
            link
            @click="enableOrDisable(row.brandId,row.status)"
          >
            <!-- 上架 -->
            {{ $t('action.putOnShelf') }}
          </el-button>
          <el-button
            v-if="row.status === 1"
            type="primary"
            link
            @click="enableOrDisable(row.brandId,row.status)"
          >
            <!-- 下架 -->
            {{ $t('action.offShelf') }}
          </el-button>
          <el-button
            v-permission="['product:brand:delete']"
            type="primary"
            link
            @click="deleteHandle(row.brandId)"
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
import { onMounted, reactive } from 'vue'
import * as api from '@/api/product/brand'
import AddOrUpdate from './components/add-or-update.vue'

const Data = reactive({
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
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
const addOrUpdateHandle = (brandId) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(brandId)
  })
}

const deleteHandle = (brandId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => deleteById(brandId))
}

const deleteById = (brandId) => {
  api.deleteById(brandId).then(() => {
    ElMessage({
      message: $t('table.actionSuccess'),
      type: 'success',
      duration: 1500,
      onClose: () => getPage()
    })
  })
}

/**
 * 上架/下架品牌
 */
const enableOrDisable = (brandId, sts) => {
  const brandObj = {}
  brandObj.brandId = brandId
  brandObj.status = sts === 0 ? 1 : 0
  ElMessageBox.confirm(sts === 1 ? '是否确认下架当前品牌?' : '是否确认上架当前品牌?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    api.enableOrDisable(brandObj).then((data) => {
      ElMessage({
        message: data || '操作成功',
        type: data ? 'error' : 'success',
        duration: 1500,
        onClose: () => getPage()
      })
    })
  }).catch(() => { })
}
</script>

<style lang="scss" scoped>
.page-product-brand {

  .filter-container{
    margin-bottom: 10px;
  }
  .brand-logo-img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 200px;
  }
}
</style>
