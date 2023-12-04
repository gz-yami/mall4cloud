<template>
  <div class="page-shop-manage">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-form
        :inline="true"
        :model="searchParam"
        class="demo-form-inline"
      >
        <el-form-item label="店铺名称">
          <el-input
            v-model="searchParam.shopName"
            placeholder="店铺名称"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="getPage()"
          >
            查询
          </el-button>
        </el-form-item>
      </el-form>
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
      <!-- 店铺名称 -->
      <el-table-column
        label="店铺名称"
        prop=""
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.shopName }}</span>
        </template>
      </el-table-column>
      <!-- 店铺类型1自营店 2普通店 -->
      <el-table-column
        label="店铺类型"
        prop="type"
        align="center"
        width="120px"
      >
        <template #default="{row}">
          <el-tag
            v-if="row.type === 1"
            type="success"
          >
            自营店
          </el-tag>
          <el-tag
            v-if="row.type === 2"
          >
            普通店
          </el-tag>
        </template>
      </el-table-column>
      <!-- 店铺logo -->
      <el-table-column
        label="店铺logo"
        prop="imgUrl"
        align="center"
        width="200px"
      >
        <template #default="{row}">
          <img
            :src="row.shopLogo?.indexOf('http')===-1 ? resourcesUrl + row.shopLogo : row.shopLogo"
            class="img"
          >
        </template>
      </el-table-column>
      <!-- 店铺简介 -->
      <el-table-column
        label="店铺简介"
        prop=""
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.intro }}</span>
        </template>
      </el-table-column>
      <!-- 申请时间 -->
      <el-table-column
        label="申请时间"
        prop=""
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <!-- 店铺状态 shopStatus 店铺状态(-1:未开通 0: 停业中 1:营业中 2:平台下线 3:平台下线待审核) -->
      <el-table-column
        label="店铺状态"
        prop="shopStatus"
        align="center"
        width="120px"
      >
        <template #default="{row}">
          <el-tag
            v-if="row.shopStatus === -1"
            type="danger"
          >
            未开通
          </el-tag>
          <el-tag
            v-if="row.shopStatus === 0"
            type="danger"
          >
            停业中
          </el-tag>
          <el-tag
            v-if="row.shopStatus === 1"
            type="success"
          >
            营业中
          </el-tag>
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
            @click="addOrUpdateHandle(row.shopId)"
          >
            查看
          </el-button>
          <el-button
            v-permission="['multishop:indexImg:update']"
            type="primary"
            link
            @click="accountManageHandle(row.shopId)"
          >
            账号管理
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
    <!-- 弹窗, 账号管理 -->
    <account-manage
      v-if="accountManageVisible"
      ref="accountManageRef"
      @refresh-data-list="getPage()"
    />
  </div>
</template>

<script setup>
import * as api from '@/api/platform/shop'
import { onMounted, reactive } from 'vue'
import AddOrUpdate from './components/add-or-update.vue'
import accountManage from './components/account-manage.vue'

const Data = reactive({
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  // 查询的参数
  pageQuery: {
    pageSize: 10,
    pageNum: 1
  },
  searchParam: {
    shopName: '', // 店铺名称
    noticeMobile: '' // 接收短信号码
  },
  // 返回参数
  pageVO: {
    list: [], // 返回的列表
    total: 0, // 一共多少条数据
    pages: 0 // 一共多少页
  },
  // loading
  pageLoading: true,
  addOrUpdateVisible: false,
  accountManageVisible: false
})

const { resourcesUrl, pageQuery, searchParam, pageVO, pageLoading, addOrUpdateVisible, accountManageVisible } = toRefs(Data)

onMounted(() => {
  getPage()
})

const getPage = () => {
  Data.pageLoading = true
  api.managePage({ ...Data.pageQuery, ...Data.searchParam }).then(pageVO => {
    Data.pageVO = pageVO
    Data.pageLoading = false
  })
}

const addOrUpdateRef = ref()
// 查看
const addOrUpdateHandle = (shopId) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(shopId)
  })
}

const accountManageRef = ref()
// 账号管理
const accountManageHandle = (shopId) => {
  Data.accountManageVisible = true
  nextTick(() => {
    accountManageRef.value.init(shopId)
  })
}

</script>

<style lang="scss" scoped>
.page-shop-manage {
  .img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100px;
  }
}
</style>
