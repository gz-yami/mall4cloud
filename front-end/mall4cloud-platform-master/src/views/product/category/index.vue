<template>
  <div class="app-container">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <!-- <el-button size="mini" icon="el-icon-search" class="filter-item" @click="getPage()">{{ $t('table.search') }}</el-button> -->
      <el-button v-permission="['product:category:save']" size="mini" icon="el-icon-plus" type="primary" class="filter-item" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
    </div>

    <!-- 分类列表 -->
    <div class="category-list">
      <!-- 一级 -->
      <div v-if="pageVO.list.length" :class="['addr-list-box', pageVO.list.length?'active':'']">
        <div class="addr-list">
          <div v-for="(firstItem, index) in pageVO.list" :key="index" :class="['addr-item', firstItemId===firstItem.categoryId?'active':'']">
            <div class="dot" @click="getSecondList(index)">{{ firstItem.name }}</div>
            <div class="btn">
              <div class="tag">
                <el-tag v-if="firstItem.status == 0" type="danger">下架</el-tag>
                <el-tag v-else size="small">正常</el-tag>
              </div>
              <el-button
                v-permission="['product:category:update']"
                type="text"
                icon="el-icon-edit"
                size="small"
                @click="addOrUpdateHandle(firstItem.categoryId)"
              >编辑</el-button>
              <!-- 上架 -->
              <el-button
                v-if="firstItem.status === 0"
                type="text"
                icon="el-icon-top"
                @click="enableOrDisable(firstItem.categoryId, firstItem.status, index)"
              >{{ $t('action.putOnShelf') }}</el-button>
              <!-- 下架 -->
              <el-button
                v-if="firstItem.status === 1"
                type="text"
                icon="el-icon-bottom"
                @click="enableOrDisable(firstItem.categoryId, firstItem.status, index)"
              >{{ $t('action.offShelf') }}</el-button>
              <el-button
                v-permission="['product:category:delete']"
                type="text"
                icon="el-icon-delete"
                size="small"
                @click="deleteHandle(firstItem.categoryId)"
              >删除</el-button>
            </div>
          </div>
        </div>
      </div>
      <!-- 二级 -->
      <div v-if="secondList.length" :class="['addr-list-box', secondList.length?'active':'']">
        <div class="addr-list">
          <div v-for="(secondItem, secondIdx) in secondList" :key="secondIdx" :class="['addr-item', secondItemId===secondItem.categoryId?'active':'']">
            <div class="dot" @click="getThreeList(secondIdx)">{{ secondItem.name }}</div>
            <div class="btn">
              <div class="tag">
                <el-tag v-if="secondItem.status == 0" type="danger">下架</el-tag>
                <el-tag v-else size="small">正常</el-tag>
              </div>
              <el-button
                v-permission="['product:category:update']"
                type="text"
                icon="el-icon-edit"
                size="small"
                @click="addOrUpdateHandle(secondItem.categoryId)"
              >编辑</el-button>
              <!-- 上架 -->
              <el-button
                v-if="secondItem.status === 0"
                type="text"
                icon="el-icon-top"
                @click="enableOrDisable(secondItem.categoryId, secondItem.status, secondIdx)"
              >{{ $t('action.putOnShelf') }}
              </el-button><!-- 下架 -->
              <el-button
                v-if="secondItem.status === 1"
                type="text"
                icon="el-icon-bottom"
                @click="enableOrDisable(secondItem.categoryId, secondItem.status, secondIdx)"
              >{{ $t('action.offShelf') }}</el-button>
              <el-button
                v-permission="['product:category:delete']"
                type="text"
                icon="el-icon-delete"
                size="small"
                @click="deleteHandle(secondItem.categoryId)"
              >删除</el-button>
            </div>
          </div>
        </div>
      </div>
      <!-- 三级 -->
      <div v-if="threeList.length" :class="['addr-list-box', threeList.length?'active':'']">
        <div class="addr-list">
          <div v-for="(threeItem, threeIdx) in threeList" :key="threeIdx" class="addr-item">
            <div class="dot">{{ threeItem.name }}</div>
            <div class="btn">
              <div class="tag">
                <el-tag v-if="threeItem.status == 0" type="danger">下架</el-tag>
                <el-tag v-else size="small">正常</el-tag>
              </div>
              <el-button
                v-permission="['product:category:update']"
                type="text"
                icon="el-icon-edit"
                size="small"
                @click="addOrUpdateHandle(threeItem.categoryId)"
              >编辑</el-button>
              <!-- 上架 -->
              <el-button
                v-if="threeItem.status === 0"
                type="text"
                icon="el-icon-top"
                @click="enableOrDisable(threeItem.categoryId, threeItem.status, threeIdx)"
              >{{ $t('action.putOnShelf') }}</el-button>
              <!-- 下架 -->
              <el-button
                v-if="threeItem.status === 1"
                type="text"
                icon="el-icon-bottom"
                @click="enableOrDisable(threeItem.categoryId, threeItem.status, threeIdx)"
              >{{ $t('action.offShelf') }}</el-button>
              <el-button
                v-permission="['product:category:delete']"
                type="text"
                icon="el-icon-delete"
                size="small"
                @click="deleteHandle(threeItem.categoryId)"
              >删除</el-button>
            </div>
          </div>
        </div>
      </div>
      <div v-if="!pageVO.list.length" class="nodata">暂无数据</div>
    </div>
    <!-- 分页条 -->
    <!-- <pagination v-show="pageVO.total>0" :total="pageVO.total" :page.sync="pageQuery.pageNum" :limit.sync="pageQuery.pageSize" @pagination="getPage()" /> -->
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getPage()" />
  </div>
</template>

<script>
import permission from '@/directive/permission/index.js'
// import Pagination from '@/components/Pagination'
import AddOrUpdate from './add-or-update.vue'
import * as api from '@/api/product/category'
import { treeDataTranslate } from '@/utils'
export default {
  name: '',
  components: {
    // Pagination,
    AddOrUpdate
  },
  directives: { permission },
  data() {
    return {
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
      // 查询的参数
      pageQuery: {
        pageSize: 10,
        pageNum: 1
      },
      // 返回参数
      pageVO: {
        list: [] // 返回的列表
        // total: 0, // 一共多少条数据
        // pages: 0 // 一共多少页
      },
      secondList: [],
      threeList: [],
      secondItemId: null,
      firstItemId: null,
      pageLoading: true,
      // 查询参数
      searchParam: {
      },
      addOrUpdateVisible: false,
      disable: false
    }
  },
  mounted() {
    this.getPage()
  },
  methods: {
    getPage() {
      this.pageLoading = true
      api.categoryPage({ ...this.pageQuery, ...this.searchParam }).then(pageVO => {
        this.secondList = this.threeList = []
        this.pageVO.list = treeDataTranslate(pageVO, 'categoryId', 'parentId')
        this.pageLoading = false
        console.log('分类列表list:', this.pageVO.list)
      })
    },
    getSecondList(idx) {
      this.secondList = this.pageVO.list[idx].children ? this.pageVO.list[idx].children : []
      this.firstItemId = this.pageVO.list[idx].categoryId
      this.threeList = []
    },
    getThreeList(idx) {
      this.threeList = this.secondList[idx].children ? this.secondList[idx].children : []
      this.secondItemId = this.secondList[idx].categoryId
    },

    /**
     * 更新
     */
    addOrUpdateHandle(categoryId) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(categoryId)
      })
    },
    /**
     * 删除
     */
    deleteHandle(categoryId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => this.deleteById(categoryId))
    },
    deleteById(categoryId) {
      api.deleteById(categoryId).then(() => {
        this.$message({
          message: this.$t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => this.getPage()
        })
      })
    },

    /**
     * 上架/下架分类
     */
    enableOrDisable(categoryId, sts, idx) {
      var categoryDTO = {}
      categoryDTO.categoryId = categoryId
      categoryDTO.status = sts === 0 ? 1 : 0
      this.$confirm(sts === 1 ? '是否确认下架当前分类?' : '是否确认上架当前分类?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.enableOrDisable(categoryDTO).then((data) => {
          this.$message({
            message: data ? '操作成功' : data,
            type: data ? 'success' : 'error',
            duration: 1500,
            onClose: () => this.getPage()
          })
        })
      }).catch(() => { })
    }

  }
}
</script>

<style lang="scss">
.app-container {
  .cate-img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 200px;
  }
  .cate-icon-img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100px;
  }

  // 地址列表区域
  .category-list {
    // margin: 0 auto;
    display: flex;
    margin: 20px 30px;
    overflow-x: auto;
    .addr-list-box {
      width: 28%;
      min-width: 380px;
      padding: 15px 0;
      .addr-list {
        height: 650px;
        overflow: auto;
        .addr-item {
          font-size: 14px;
          color: #444;
          line-height: 2.5em;
          display: flex;
          align-items: center;
          justify-content: space-between;
          .dot {
            position: relative;
            padding-left: 20px;
            cursor: pointer;
            width: 40%;
            min-width: 100px;
            box-sizing: border-box;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
          }
          .dot::before {
            content: "";
            position: absolute;
            left: 10px;
            top: 50%;
            transform: translateY(-50%);
            width: 4px;
            height: 4px;
            border-radius: 50%;
            background: #bbb;
          }
          .btn {
            .tag {
              display: inline-block;
              margin-right: 10px;
            }
            min-width: 98px;
            margin-right: 10px;
            .el-button--text {
              color: #02a1e9;
            }
          }
        }
        .addr-item:hover,
        .addr-item.active {
          background: #f5f7fa;
        }
      }
    }
    .addr-list-box.active {
      border: 1px solid #eee;
    }
    .addr-list-box.active:not(:first-child) {
      border-left: 0;
    }
    .nodata {
      width: 100%;
      text-align: center;
      font-size: 13px;
      color: #aaa;
      margin-top: 50px;
    }
  }
}
</style>
