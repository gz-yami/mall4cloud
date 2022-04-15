<template>
  <div class="app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-form :inline="true" :model="pageQuery" class="demo-form-inline">
        <el-form-item :label="$t('delivery.transport.name')">
          <el-input
            v-model="pageQuery.transName"
            size="mini"
            :placeholder="$t('delivery.transport.name')"
            clearable
          />
        </el-form-item>
        <!-- <el-form-item label="属性类型">
          <el-select v-model="pageQuery.attrType" size="mini" placeholder="请选择属性类型">
            <el-option label="销售属性" value="0" />
            <el-option label="基本属性" value="1" />
          </el-select>
        </el-form-item> -->
        <el-form-item>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-search"
            @click="getPage()"
          >{{ $t('delivery.transport.query') }}</el-button>
        </el-form-item>
      </el-form>
      <!-- <el-button size="mini" icon="el-icon-search" class="filter-item" @click="getPage()">{{ $t('table.search') }}</el-button> -->
      <el-button
        size="mini"
        icon="el-icon-plus"
        type="primary"
        class="filter-item"
        @click="addOrUpdateHandle()"
      >{{ $t("table.create") }}</el-button>
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
      style="width: 100%"
    >
      <!-- 名称 -->
      <el-table-column
        :label="$t('delivery.transport.name')"
        prop="transName"
        align="center"
        width="300px"
      >
        <template slot-scope="{ row }">
          <span>{{ row.transName }}</span>
        </template>
      </el-table-column>
      <!-- 创建时间 -->
      <el-table-column
        :label="$t('table.createTime')"
        prop="createTime"
        align="center"
      >
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <!-- 更新时间 -->
      <el-table-column
        :label="$t('table.updateTime')"
        prop="updateTime"
        align="center"
      >
        <template slot-scope="{ row }">
          <span>{{ row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.actions')"
        align="center"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{ row }">
          <el-button
            v-permission="['product:attr:update']"
            type="text"
            @click="addOrUpdateHandle(row.transportId)"
          >
            {{ $t("table.edit") }}
          </el-button>
          <el-button
            v-permission="['product:attr:delete']"
            type="text"
            @click="deleteHandle(row.transportId)"
          >
            {{ $t("table.delete") }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页条 -->
    <pagination
      v-show="pageVO.total > 0"
      :total="pageVO.total"
      :page.sync="pageQuery.pageNum"
      :limit.sync="pageQuery.pageSize"
      @pagination="getPage()"
    />
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update
      v-if="addOrUpdateVisible"
      ref="addOrUpdate"
      @refreshDataList="getPage()"
    />
  </div>
</template>

<script>
import permission from '@/directive/permission/index.js'
import Pagination from '@/components/Pagination'
import AddOrUpdate from './add-or-update.vue'
import * as api from '@/api/delivery/transport'
export default {
  transName: '',
  components: { Pagination, AddOrUpdate },
  directives: { permission },
  data() {
    return {
      // 查询的参数
      pageQuery: {
        pageSize: 10,
        pageNum: 1,
        name: null
        // attrType: null
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
    }
  },
  mounted() {
    this.getPage()
  },
  methods: {
    // 获取数据列表
    getPage(page) {
      console.log('进入加载页面数据')
      this.pageLoading = true
      api.page({ ...this.pageQuery, ...this.searchParam }).then(pageVO => {
        this.pageVO = pageVO
        this.pageLoading = false
      })
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    // 删除
    deleteHandle(id) {
      // var ids = id
      //   ? [id]
      //   : this.dataListSelections.map(item => {
      //     return item.transportId
      //   })
      this.$confirm(
        this.$i18n.t('table.sureToDelete'),
        this.$i18n.t('table.tips'),
        {
          confirmButtonText: this.$i18n.t('table.confirm'),
          cancelButtonText: this.$i18n.t('table.cancel'),
          type: 'warning'
        }
      )
        .then(() => {
          api.deleteById(id).then(() => {
            this.$message({
              message: this.$t('table.actionSuccess'),
              type: 'success',
              duration: 1500,
              onClose: () => this.getPage()
            })
          })
        })
        .catch(() => {
        })
    },

    // 条件查询
    searchChange(params) {
      this.searchForm = params
      this.page.currentPage = 1
      this.page.pageSize = 10
      this.getDataList(this.page)
    },
    // 刷新回调用
    refreshChange() {
      this.page = this.$refs.crud.$refs.tablePage.defaultPage
      this.getDataList(this.page)
    },
    // 多选变化
    selectionChange(val) {
      this.dataListSelections = val
    }
  }
}
</script>
