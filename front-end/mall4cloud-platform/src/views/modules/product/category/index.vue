<template>
  <div class="page-product-category">
    <!-- 搜索相关区域 -->
    <div class="filter-container">
      <el-button
        v-permission="['product:category:save']"
        type="primary"
        class="filter-item"
        @click="addOrUpdateHandle()"
      >
        {{ $t('table.create') }}
      </el-button>
    </div>

    <!-- 分类列表 -->
    <div class="category-list">
      <!-- 一级 -->
      <div
        v-if="pageVO.list.length"
        :class="['addr-list-box', pageVO.list.length ? 'active' : '']"
      >
        <div class="addr-list">
          <div
            v-for="(firstItem, index) in pageVO.list"
            :key="index"
            :class="['addr-item', firstItemId === firstItem.categoryId ? 'active' : '']"
          >
            <div
              class="dot"
              @click="getSecondList(index)"
            >
              {{ firstItem.name }}
            </div>
            <div class="btn">
              <div class="tag">
                <el-tag
                  v-if="firstItem.status == 0"
                  type="danger"
                >
                  下架
                </el-tag>
                <el-tag
                  v-else
                >
                  正常
                </el-tag>
              </div>
              <el-button
                v-permission="['product:category:update']"
                type="primary"
                link
                @click="addOrUpdateHandle(firstItem.categoryId)"
              >
                编辑
              </el-button>
              <!-- 上架 -->
              <el-button
                v-if="firstItem.status === 0"
                type="primary"
                link
                @click="enableOrDisable(firstItem.categoryId, firstItem.status, index)"
              >
                {{ $t('action.putOnShelf') }}
              </el-button>
              <!-- 下架 -->
              <el-button
                v-if="firstItem.status === 1"
                type="primary"
                link
                @click="enableOrDisable(firstItem.categoryId, firstItem.status, index)"
              >
                {{ $t('action.offShelf') }}
              </el-button>
              <el-button
                v-permission="['product:category:delete']"
                type="primary"
                link
                @click="deleteHandle(firstItem.categoryId)"
              >
                删除
              </el-button>
            </div>
          </div>
        </div>
      </div>
      <!-- 二级 -->
      <div
        v-if="secondList.length"
        :class="['addr-list-box', secondList.length ? 'active' : '']"
      >
        <div class="addr-list">
          <div
            v-for="(secondItem, secondIdx) in secondList"
            :key="secondIdx"
            :class="['addr-item', secondItemId === secondItem.categoryId ? 'active' : '']"
          >
            <div
              class="dot"
              @click="getThreeList(secondIdx)"
            >
              {{ secondItem.name }}
            </div>
            <div class="btn">
              <div class="tag">
                <el-tag
                  v-if="secondItem.status == 0"
                  type="danger"
                >
                  下架
                </el-tag>
                <el-tag
                  v-else
                >
                  正常
                </el-tag>
              </div>
              <el-button
                v-permission="['product:category:update']"
                type="primary"
                link
                @click="addOrUpdateHandle(secondItem.categoryId)"
              >
                编辑
              </el-button>
              <!-- 上架 -->
              <el-button
                v-if="secondItem.status === 0"
                type="primary"
                link
                @click="enableOrDisable(secondItem.categoryId, secondItem.status, secondIdx)"
              >
                {{ $t('action.putOnShelf') }}
              </el-button><!-- 下架 -->
              <el-button
                v-if="secondItem.status === 1"
                type="primary"
                link
                @click="enableOrDisable(secondItem.categoryId, secondItem.status, secondIdx)"
              >
                {{ $t('action.offShelf') }}
              </el-button>
              <el-button
                v-permission="['product:category:delete']"
                type="primary"
                link
                @click="deleteHandle(secondItem.categoryId)"
              >
                删除
              </el-button>
            </div>
          </div>
        </div>
      </div>
      <!-- 三级 -->
      <div
        v-if="threeList.length"
        :class="['addr-list-box', threeList.length ? 'active' : '']"
      >
        <div class="addr-list">
          <div
            v-for="(threeItem, threeIdx) in threeList"
            :key="threeIdx"
            class="addr-item"
          >
            <div class="dot">
              {{ threeItem.name }}
            </div>
            <div class="btn">
              <div class="tag">
                <el-tag
                  v-if="threeItem.status == 0"
                  type="danger"
                >
                  下架
                </el-tag>
                <el-tag
                  v-else
                >
                  正常
                </el-tag>
              </div>
              <el-button
                v-permission="['product:category:update']"
                type="primary"
                link
                @click="addOrUpdateHandle(threeItem.categoryId)"
              >
                编辑
              </el-button>
              <!-- 上架 -->
              <el-button
                v-if="threeItem.status === 0"
                type="primary"
                link
                @click="enableOrDisable(threeItem.categoryId, threeItem.status, threeIdx)"
              >
                {{ $t('action.putOnShelf') }}
              </el-button>
              <!-- 下架 -->
              <el-button
                v-if="threeItem.status === 1"
                type="primary"
                link
                @click="enableOrDisable(threeItem.categoryId, threeItem.status, threeIdx)"
              >
                {{ $t('action.offShelf') }}
              </el-button>
              <el-button
                v-permission="['product:category:delete']"
                type="primary"
                link
                @click="deleteHandle(threeItem.categoryId)"
              >
                删除
              </el-button>
            </div>
          </div>
        </div>
      </div>
      <div
        v-if="!pageVO.list.length"
        class="nodata"
      >
        暂无数据
      </div>
    </div>
    <!-- 分页条 -->
    <!-- <pagination v-show="pageVO.total>0" :total="pageVO.total" :page.sync="pageQuery.pageNum" :limit.sync="pageQuery.pageSize" @pagination="getPage()" /> -->
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
import * as api from '@/api/product/category'
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
})
const { pageVO, secondList, threeList, secondItemId, firstItemId, addOrUpdateVisible } = toRefs(Data)

onMounted(() => {
  getPage()
})

const getPage = () => {
  Data.pageLoading = true
  api.categoryPage({ ...Data.pageQuery, ...Data.searchParam }).then(pageVO => {
    Data.secondList = Data.threeList = []
    Data.pageVO.list = treeDataTranslate(pageVO, 'categoryId', 'parentId')
    Data.pageLoading = false
  })
}

const getSecondList = (idx) => {
  Data.secondList = Data.pageVO.list[idx].children ? Data.pageVO.list[idx].children : []
  Data.firstItemId = Data.pageVO.list[idx].categoryId
  Data.threeList = []
}

const getThreeList = (idx) => {
  Data.threeList = Data.secondList[idx].children ? Data.secondList[idx].children : []
  Data.secondItemId = Data.secondList[idx].categoryId
}

/**
* 更新
*/
const addOrUpdateRef = ref()
const addOrUpdateHandle = (categoryId) => {
  Data.addOrUpdateVisible = true
  nextTick(() => {
    addOrUpdateRef.value.init(categoryId)
  })
}

/**
* 删除
*/
const deleteHandle = (categoryId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => deleteById(categoryId))
}

const deleteById = (categoryId) => {
  api.deleteById(categoryId).then(() => {
    ElMessage({
      message: $t('table.actionSuccess'),
      type: 'success',
      duration: 1500,
      onClose: () => getPage()
    })
  })
}

/**
* 上架/下架分类
*/
const enableOrDisable = (categoryId, sts, idx) => {
  const categoryDTO = {}
  categoryDTO.categoryId = categoryId
  categoryDTO.status = sts === 0 ? 1 : 0
  ElMessageBox.confirm(sts === 1 ? '是否确认下架当前分类?' : '是否确认上架当前分类?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    api.enableOrDisable(categoryDTO).then((data) => {
      ElMessage({
        message: data ? '操作成功' : data,
        type: data ? 'success' : 'error',
        duration: 1500,
        onClose: () => getPage()
      })
    })
  }).catch(() => { })
}

</script>

<style lang="scss" scoped>
.page-product-category {

  // 地址列表区域
  .category-list {
    // margin: 0 auto;
    display: flex;
    margin: 20px 0;
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
            display: flex;
            align-items: center;

            min-width: 98px;
            margin-right: 10px;

            .tag{
              margin-left: 10px;
            }

            :deep(.el-button){
              margin-left: 10px;

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
