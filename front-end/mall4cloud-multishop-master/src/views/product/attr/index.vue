<template>
  <div class="app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-form :inline="true" :model="pageQuery" class="demo-form-inline">
        <el-form-item label="属性名称">
          <el-input v-model="pageQuery.name" size="mini" placeholder="属性名称" />
        </el-form-item>
        <!-- <el-form-item label="属性类型">
          <el-select v-model="pageQuery.attrType" size="mini" placeholder="请选择属性类型">
            <el-option label="销售属性" value="0" />
            <el-option label="基本属性" value="1" />
          </el-select>
        </el-form-item> -->
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-search" @click="getPage()">查询</el-button>
          <el-button size="mini" icon="el-icon-delete" @click="clearSearchInfo()">清空</el-button>
        </el-form-item>
      </el-form>
      <!-- <el-button size="mini" icon="el-icon-search" class="filter-item" @click="getPage()">{{ $t('table.search') }}</el-button> -->
      <el-button v-permission="['product:attr:save']" size="mini" icon="el-icon-plus" type="primary" class="filter-item" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
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
      <!-- <el-table-column :label="$t('table.createTime')" prop="createTime" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column> -->
      <!-- 更新时间 -->
      <!-- <el-table-column :label="$t('table.updateTime')" prop="updateTime" align="center">
        <template slot-scope="{row}">
          <span>{{ row.updateTime }}</span>
        </template>
      </el-table-column> -->
      <!-- 属性名称 -->
      <el-table-column :label="$t('product.attr.name')" prop="name" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <!-- 属性描述 -->
      <!-- <el-table-column :label="$t('product.attr.desc')" prop="desc" align="center">
        <template slot-scope="{row}">
          <span>{{ row.desc }}</span>
        </template>
      </el-table-column> -->
      <!-- 0:不需要，1:需要 -->
      <!-- <el-table-column :label="$t('product.attr.searchType')" prop="searchType" align="center">
        <template slot-scope="{row}">
          <span>{{ ['否', '是'][row.searchType] }}</span>
        </template>
      </el-table-column> -->
      <!-- 0:销售属性，1:基本属性 -->
      <!-- <el-table-column :label="$t('product.attr.attrType')" prop="attrType" align="center">
        <template slot-scope="{row}">
          <span>{{ ['销售属性', '基本属性'][row.attrType] }}</span>
        </template>
      </el-table-column> -->
      <!-- 属性值 -->
      <el-table-column label="属性值" prop="attrType" align="center">
        <template slot-scope="{row}">
          <el-tag v-for="(item,index) in row.attrValues" :key="index">{{ item.value }}</el-tag>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-permission="['product:attr:update']" type="text" @click="addOrUpdateHandle(row.attrId)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button v-permission="['product:attr:delete']" type="text" @click="deleteHandle(row.attrId)">
            {{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页条 -->
    <pagination v-show="pageVO.total>0" :total="pageVO.total" :page.sync="pageQuery.pageNum" :limit.sync="pageQuery.pageSize" @pagination="getPage()" />
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getPage()" />
  </div>
</template>

<script>
import permission from '@/directive/permission/index.js'
import Pagination from '@/components/Pagination'
import AddOrUpdate from './add-or-update.vue'
import * as api from '@/api/product/attr'

export default {
  name: '',
  components: { Pagination, AddOrUpdate },
  directives: { permission },
  data() {
    return {
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
      // 查询参数
      searchParam: {
      },
      addOrUpdateVisible: false,
      options: [
        { value: '销售属性', label: 0 },
        { value: '基本属性', label: 1 }
      ]
    }
  },
  mounted() {
    this.getPage()
  },
  methods: {
    getPage() {
      console.log('进入加载页面数据')
      this.pageLoading = true
      api.page({ ...this.pageQuery, ...this.searchParam }).then(pageVO => {
        this.pageVO = pageVO
        this.pageLoading = false
      })
    },
    addOrUpdateHandle(attrId) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(attrId)
      })
    },
    deleteHandle(attrId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => this.deleteById(attrId))
    },
    deleteById(attrId) {
      api.deleteById(attrId).then(() => {
        this.$message({
          message: this.$t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => this.getPage()
        })
      })
    },

    // 清空搜索内容
    clearSearchInfo() {
      this.pageQuery.name = ''
      this.pageQuery.attrType = ''
    },

    // 刷新回调用
    refreshChange() {
      console.log('刷新回调')
      this.page = this.$refs.crud.$refs.tablePage.defaultPage
      this.getPage(this.page)
    }
  }
}
</script>
