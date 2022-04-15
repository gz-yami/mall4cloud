<template>
  <div class="app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button size="mini" icon="el-icon-search" class="filter-item" @click="getPage()">{{ $t('table.search') }}</el-button>
      <el-button v-permission="['multishop:shopUser:save']" size="mini" icon="el-icon-plus" type="primary" class="filter-item" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
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
      <!-- 昵称 -->
      <el-table-column :label="$t('platform.shopUser.nickName')" prop="nickName" align="center" width="250">
        <template slot-scope="{row}">
          <span>{{ row.nickName }}</span>
        </template>
      </el-table-column>
      <!-- 头像 -->
      <el-table-column :label="$t('platform.shopUser.avatar')" prop="avatar" align="center" width="100">
        <template slot-scope="{row}">
          <el-image :src="row.avatar" fit="contain" />
        </template>
      </el-table-column>
      <!-- 员工编号 -->
      <el-table-column :label="$t('platform.shopUser.code')" prop="code" align="center">
        <template slot-scope="{row}">
          <span>{{ row.code }}</span>
        </template>
      </el-table-column>
      <!-- 联系方式 -->
      <el-table-column :label="$t('platform.shopUser.phoneNum')" prop="phoneNum" align="center">
        <template slot-scope="{row}">
          <span>{{ row.phoneNum }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-if="!row.hasAccount" v-permission="['account:authAccount:shopSave']" type="text" @click="addOrUpdateAccountHandle(row.sysUserId,row.hasAccount)">
            设置账号
          </el-button>
          <el-button v-if="row.hasAccount" v-permission="['account:authAccount:shopUpdate']" type="text" @click="addOrUpdateAccountHandle(row.sysUserId,row.hasAccount)">
            修改账号
          </el-button>
          <el-button v-permission="['platform:shopUser:update']" type="text" @click="addOrUpdateHandle(row.sysUserId)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button v-permission="['platform:shopUser:delete']" type="text" @click="deleteHandle(row.sysUserId)">
            {{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页条 -->
    <pagination v-show="pageVO.total>0" :total="pageVO.total" :page.sync="pageQuery.pageNum" :limit.sync="pageQuery.pageSize" @pagination="getPage()" />
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getPage()" />
    <!-- 账户弹窗, 新增 / 修改 -->
    <account-add-or-update v-if="accountaddOrUpdateVisible" ref="accountAddOrUpdate" @refreshDataList="getPage()" />
  </div>
</template>

<script>
import permission from '@/directive/permission/index.js'
import Pagination from '@/components/Pagination'
import AddOrUpdate from './add-or-update.vue'
import AccountAddOrUpdate from './account-add-or-update.vue'
import * as api from '@/api/platform/sys-user'

export default {
  name: '',
  components: { Pagination, AddOrUpdate, AccountAddOrUpdate },
  directives: { permission },
  data() {
    return {
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
      addOrUpdateVisible: false,
      accountaddOrUpdateVisible: false
    }
  },
  mounted() {
    this.getPage()
  },
  methods: {
    getPage() {
      this.pageLoading = true
      api.page({ ...this.pageQuery, ...this.searchParam }).then(pageVO => {
        this.pageVO = pageVO
        this.pageLoading = false
      })
    },
    addOrUpdateHandle(sysUserId) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(sysUserId)
      })
    },
    addOrUpdateAccountHandle(sysUserId, hasAccount) {
      this.accountaddOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.accountAddOrUpdate.init(sysUserId, hasAccount)
      })
    },
    deleteHandle(sysUserId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => this.deleteById(sysUserId))
    },
    deleteById(sysUserId) {
      api.deleteById(sysUserId).then(() => {
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
