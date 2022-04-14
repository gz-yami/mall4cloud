<template>
  <div class="app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-form :inline="true" :model="searchParam" class="demo-form-inline">
        <el-form-item label="店铺名称">
          <el-input v-model="searchParam.shopName" size="mini" placeholder="店铺名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-search" @click="getPage()">查询</el-button>
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
      <el-table-column label="店铺名称" prop="" align="center">
        <template slot-scope="{row}">
          <span>{{ row.shopName }}</span>
        </template>
      </el-table-column>
      <!-- 店铺类型1自营店 2普通店 -->
      <el-table-column label="店铺类型" prop="type" align="center" width="120px">
        <template slot-scope="{row}">
          <el-tag v-if="row.type === 1" size="small" type="success">自营店</el-tag>
          <el-tag v-if="row.type === 2" size="small">普通店</el-tag>
        </template>
      </el-table-column>
      <!-- 店铺logo -->
      <el-table-column label="店铺logo" prop="imgUrl" align="center" width="200px">
        <template slot-scope="{row}">
          <img :src="(row.shopLogo).indexOf('http')===-1 ? resourcesUrl + row.shopLogo : row.shopLogo" class="img">
        </template>
      </el-table-column>
      <!-- 店铺简介 -->
      <el-table-column label="店铺简介" prop="" align="center">
        <template slot-scope="{row}">
          <span>{{ row.intro }}</span>
        </template>
      </el-table-column>
      <!-- 申请时间 -->
      <el-table-column label="申请时间" prop="" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <!-- 店铺状态 shopStatus 店铺状态(-1:未开通 0: 停业中 1:营业中 2:平台下线 3:平台下线待审核) -->
      <el-table-column label="店铺状态" prop="shopStatus" align="center" width="120px">
        <template slot-scope="{row}">
          <el-tag v-if="row.shopStatus === -1" size="small" type="danger">未开通</el-tag>
          <el-tag v-if="row.shopStatus === 0" size="small" type="danger">停业中</el-tag>
          <el-tag v-if="row.shopStatus === 1" size="small" type="success">营业中</el-tag>
        </template>
      </el-table-column>

      <!-- 操作 -->
      <el-table-column :label="$t('table.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button
            v-permission="['multishop:indexImg:update']"
            type="text"
            class="el-icon-view"
            @click="addOrUpdateHandle(row.shopId)"
          >查看</el-button>
          <el-button
            v-permission="['multishop:indexImg:update']"
            type="text"
            class="el-icon-setting"
            @click="accountManageHandle(row.shopId)"
          >账号管理</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页条 -->
    <pagination v-show="pageVO.total>0" :total="pageVO.total" :page.sync="pageQuery.pageNum" :limit.sync="pageQuery.pageSize" @pagination="getPage()" />
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getPage()" />
    <!-- 弹窗, 账号管理 -->
    <account-manage v-if="accountManageVisible" ref="accountManage" @refreshDataList="getPage()" />
  </div>
</template>

<script>
import permission from '@/directive/permission/index.js'
import Pagination from '@/components/Pagination'
import AddOrUpdate from './add-or-update.vue'
import accountManage from './account-manage.vue'
import * as api from '@/api/platform/shop'
export default {
  components: { Pagination, AddOrUpdate, accountManage },
  directives: { permission },
  data() {
    return {
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
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
    }
  },
  mounted() {
    this.getPage()
  },
  methods: {
    getPage() {
      this.pageLoading = true
      api.managePage({ ...this.pageQuery, ...this.searchParam }).then(pageVO => {
        this.pageVO = pageVO
        this.pageLoading = false
      })
    },

    // 查看
    addOrUpdateHandle(shopId) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(shopId)
      })
    },

    // 账号管理
    accountManageHandle(shopId) {
      this.accountManageVisible = true
      this.$nextTick(() => {
        this.$refs.accountManage.init(shopId)
      })
    }

  }
}
</script>

<style lang="scss" scoped>
.app-container {
  .img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100px;
  }
}
</style>

