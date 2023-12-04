<template>
  <div class="page-shop-user">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button
        v-permission="['multishop:shopUser:save']"
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
      <!-- 昵称 -->
      <el-table-column
        :label="$t('multishop.shopUser.nickName')"
        prop="nickName"
        align="center"
        width="250"
      >
        <template #default="{row}">
          <span>{{ row.nickName }}</span>
        </template>
      </el-table-column>
      <!-- 头像 -->
      <el-table-column
        :label="$t('multishop.shopUser.avatar')"
        prop="avatar"
        align="center"
        width="100"
      >
        <template #default="{row}">
          <el-image
            :src="row.avatar"
            fit="contain"
          />
        </template>
      </el-table-column>
      <!-- 员工编号 -->
      <el-table-column
        :label="$t('multishop.shopUser.code')"
        prop="code"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.code }}</span>
        </template>
      </el-table-column>
      <!-- 联系方式 -->
      <el-table-column
        :label="$t('multishop.shopUser.phoneNum')"
        prop="phoneNum"
        align="center"
      >
        <template #default="{row}">
          <span>{{ row.phoneNum }}</span>
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
            v-if="!row.hasAccount"
            v-permission="['account:authAccount:shopSave']"
            type="primary"
            link
            @click="addOrUpdateAccountHandle(row.shopUserId,row.hasAccount)"
          >
            设置账号
          </el-button>
          <el-button
            v-if="row.hasAccount"
            v-permission="['account:authAccount:shopUpdate']"
            type="primary"
            link
            @click="addOrUpdateAccountHandle(row.shopUserId,row.hasAccount)"
          >
            修改账号
          </el-button>
          <el-button
            v-permission="['multishop:shopUser:update']"
            type="primary"
            link
            @click="addOrUpdateHandle(row.shopUserId)"
          >
            {{ $t('table.edit') }}
          </el-button>
          <el-button
            v-permission="['multishop:shopUser:delete']"
            type="primary"
            link
            @click="deleteHandle(row.shopUserId)"
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
    <!-- 账户弹窗, 新增 / 修改 -->
    <account-add-or-update
      v-if="accountaddOrUpdateVisible"
      ref="accountAddOrUpdateRef"
      @refresh-data-list="getPage()"
    />
  </div>
</template>

<script setup>
import * as api from '@/api/multishop/shop-user'
import { onMounted, reactive } from 'vue'
import AddOrUpdate from './components/add-or-update.vue'
import AccountAddOrUpdate from './components/account-add-or-update.vue'

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
  addOrUpdateVisible: false,
  accountaddOrUpdateVisible: false
})
const { pageQuery, pageVO, pageLoading, addOrUpdateVisible, accountaddOrUpdateVisible } = toRefs(Data)

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
const addOrUpdateHandle = (shopUserId) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(shopUserId)
  })
}

const accountAddOrUpdateRef = ref()
const addOrUpdateAccountHandle = (shopUserId, hasAccount) => {
  Data.accountaddOrUpdateVisible = true
  nextTick(() => {
    accountAddOrUpdateRef.value.init(shopUserId, hasAccount)
  })
}

const deleteHandle = (shopUserId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => deleteById(shopUserId))
}

const deleteById = (shopUserId) => {
  api.deleteById(shopUserId).then(() => {
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
