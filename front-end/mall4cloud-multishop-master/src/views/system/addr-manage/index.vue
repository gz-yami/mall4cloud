<template>
  <div class="mod-sys-area">
    <!-- 搜索相关区域 -->
    <!-- <div class="filter-container">
      <el-input v-model="areaName" size="mini" class="area-search-input" placeholder="地区关键词" />
    </div> -->
    <div class="filter-item">
      <el-button v-permission="['system:addrManage:save']" size="mini" icon="el-icon-plus" type="primary" class="rea-add-btn" @click="addOrUpdateHandle()">{{ $t('table.create') }}</el-button>
    </div>

    <!--  省市区列表相关区域 -->
    <div :class="['addr', areaList.length ? 'box-border' : '']">
      <div v-for="(renderList, level) in renderListData" :key="level" :class="['addr-list-box', renderListData.length?'active':'']">
        <div class="addr-list">
          <!-- <div v-for="(item, idx) in renderList" :key="idx" :class="['addr-item', selectedsData.length > 0 && selectedsData[selectedsData.length-1].areaId == item.areaId ? 'active':'']"> -->
          <div v-for="(item, idx) in renderList" :key="idx" :class="['addr-item', currentSelectId == item.areaId ? 'active':'']">
            <div class="dot" @click="handleAddrListSelect(item, level)">{{ item.areaName }}</div>
            <div class="btn">
              <el-button
                v-permission="['system:addrManage:update']"
                type="text"
                icon="el-icon-edit"
                size="small"
                @click="addOrUpdateHandle(item, level)"
              >编辑</el-button>
              <el-button
                v-permission="['system:addrManage:delete']"
                type="text"
                icon="el-icon-delete"
                size="small"
                @click="deleteHandle(item, level)"
              >删除</el-button>
            </div>
          </div>
        </div>
      </div>
      <div v-if="!renderListData.length" class="nodata">暂无数据</div>
    </div>
    <!-- 省市区列表相关区域 end -->

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getPage()" />
  </div>
</template>

<script>
import permission from '@/directive/permission/index.js'
import * as api from '@/api/system/addr-manage'
import AddOrUpdate from './add-or-update.vue'
import { treeDataTranslate } from '@/utils'
export default {
  components: {
    AddOrUpdate
  },
  directives: { permission },
  data() {
    return {
      treeData: [],
      selectedsData: [], // 记录选中值
      renderListData: [], // 地址列表
      data: [],
      regionList: [], // 区域
      provList: [], // 省
      cityList: [], // 市
      areaList: [], // 区
      areaName: '',
      params: {
        areaName: null
      },
      pageLoading: true,
      props: {
        id: 'areaId',
        label: 'areaName',
        children: 'children'
      },
      regionId: 0,
      provId: 0,
      cityId: 0,
      // 查询参数
      searchParam: {
      },
      addOrUpdateVisible: false,
      disable: false,
      currentSelectId: '' // 当前选中id
    }
  },
  watch: {
    areaName(val) {
      console.log('监听val:', val)
    }
  },
  mounted() {
    this.getPage()
  },
  methods: {
    /**
     * 获取地址数据
     */
    getPage() {
      this.pageLoading = true
      api.page().then(data => {
        this.pageLoading = false
        const treeData = treeDataTranslate(data, 'areaId', 'parentId')
        this.treeData = treeData
        this.setInitRenderListData(treeData)
      })
    },
    // 设置初始数据
    setInitRenderListData(treeData) {
      this.selectedsData.splice(0, this.selectedsData.length) // 1. 删除旧已选中数据
      // 删除旧的渲染列表并添加新的第一级渲染列表
      this.changeRenderListData({
        startLevel: 0,
        changeLength: this.renderListData.length, // 删除的长度
        changeValue: [treeData] // 添加新值
      })
      console.log('初始数据renderListData:', this.renderListData)
    },
    // 获取渲染列表
    getRenderListData(data) {
      return data && data.children || []
    },
    /**
     * 监听列表项选择
     */
    handleAddrListSelect(selectData, level) {
      this.selectedsData.splice(level, 1, selectData) // 记录选中值
      this.currentSelectId = selectData.areaId
      // 生成下一级的列表渲染数据
      const childRenderData = this.getRenderListData(selectData)
      // 删除所有子孙级渲染数据, 并添加下级渲染数据
      if (childRenderData.length > 0) {
        this.changeRenderListData({
          startLevel: level + 1,
          changeLength: this.renderListData.length,
          changeValue: [childRenderData]
        })
      }
      console.log('renderListData:', this.renderListData)
    },
    // 更改渲染列表数据
    changeRenderListData({ startLevel, changeLength, changeValue }) {
      this.renderListData.splice(startLevel, changeLength, ...changeValue)
    },

    /**
     * 新增
     */
    addOrUpdateHandle(item, level) {
      // console.log('新增item.level:', item.level)
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(item?.areaId, item?.level)
      })
    },

    /**
     * 删除
     */
    // remove(node, data) {
    //   this.deleteHandle(data.areaId)
    // },
    deleteHandle(item, level) {
      this.$confirm('确认删除当前地址项?', this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => {
        this.deleteById(item.areaId)
        this.changeRenderListData({
          startLevel: level,
          changeLength: this.renderListData.length
        })
      })
    },
    deleteById(areaId) {
      api.deleteById(areaId).then(() => {
        this.$message({
          message: this.$t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => this.getPage()
        })
      })
    },

    filterNode(value, data) {
      if (!value) return true
      return data.areaName.indexOf(value) !== -1
    }
  }

}
</script>

<style lang="scss" scoped>
.mod-sys-area {
  .filter-container {
    margin: 0 30px;
    margin-top: 15px;
    // margin-bottom: 10px;
  }
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
  .area-search-input {
    width: 30%;
  }
  .area-add-btn {
    float: right;
  }
  .filter-item {
    margin: 0 30px;
    margin-top: 15px;
  }

  // 地址列表区域
  .addr {
    // margin: 0 auto;
    display: flex;
    margin: 20px 30px;
    overflow-x: auto;
    .addr-list-box {
      width: 22%;
      min-width: 350px;
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
            width: 60%;
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
            min-width: 98px;
            margin-right: 15px;
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
  .addr.box-border {
    border-left: 1px solid #eee;
    border-right: 1px solid #eee;
  }
}
</style>
