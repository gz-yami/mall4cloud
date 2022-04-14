<template>
  <el-dialog
    title="选择品牌"
    top="50px"
    :close-on-click-modal="false"
    :visible.sync="visible"
    class="brand-select"
  >
    <el-form :inline="true" :model="dataForm" class="demo-form-inline">
      <el-form-item label="品牌名称">
        <el-input v-model.trim="brandName" placeholder="品牌名称" size="mini"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="onSearch">查询</el-button>
      </el-form-item>
    </el-form>

    <div class="prods-select-body">
      <el-table
        ref="brandTable"
        :data="pageVO.list"
        border
        v-loading="brandListLoading"
        style="width: 100%;"
      >
        <el-table-column v-if="isSingle" width="50" header-align="center" align="center">
          <template slot-scope="{row}">
            <div>
              <el-radio
                :label="row.brandId"
                v-model="singleSelectBrandId"
                @change.native="getSelectBrandRow(row)"
              ></el-radio>
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="brandName" header-align="center" align="center" label="品牌名称">
          <template slot-scope="{row}">
            {{row.name}}
          </template>
        </el-table-column>

        <el-table-column prop="firstChar" align="center" label="品牌首字母">
          <template slot-scope="{row}">
            {{row.firstLetter}}
          </template>
        </el-table-column>

        <el-table-column prop="status" align="center" label="状态">
          <template slot-scope="{row}">
            <el-tag size="mini">{{row.status===1?'正常':'下线'}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column align="center" width="140" label="品牌图片">
          <template slot-scope="{row}">
            <img v-if="row.imgUrl" :src="(row.imgUrl).indexOf('http')===-1 ? resourcesUrl + row.imgUrl : row.imgUrl" class="brand-img" />
          </template>
        </el-table-column>

      </el-table>

      <!-- 分页条 -->
      <pagination v-show="pageVO.total>0" :total="pageVO.total" :page.sync="pageQuery.pageNum" :limit.sync="pageQuery.pageSize" @pagination="getBrandList()" />
    </div>
    <span slot="footer">
      <el-button type="primary" @click="visible = false">取消</el-button>
      <el-button type="primary" @click="submitBrand()">提交</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { page } from '@/api/product/brand'
import Pagination from '@/components/Pagination'
export default {
  components: { Pagination },
  data() {
    return {
      visible: false,
      brandName: null,
      selectBrand: [],
      dataForm: {
        brand: ''
      },
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
      pageQuery: {
        pageSize: 5,
        pageNum: 1
      },
      // 返回参数
      pageVO: {
        list: [], // 返回的列表
        total: 0, // 一共多少条数据
        pages: 0 // 一共多少页
      },
      brandListLoading: false,

      singleSelectBrandId: 0,
      selectBrand: [],
      brandListSelections: [],
    }
  },

  props: {
    isSingle: {
      default: false,
      type: Boolean
    },
  },

  methods: {
    init(selectBrand) {
      this.selectBrand = selectBrand
      this.visible = true
      this.brandListLoading = true
      if (this.selectBrand) {
        this.selectBrand.forEach(row => {
          this.brandListSelections.push(row)
        })
      }
      this.getBrandList()
    },

    onSearch() {
      this.getBrandList()
    },

    getBrandList() {
      page({ ...this.pageQuery, ...this.searchParam }).then(pageVO => {
        this.pageVO = pageVO
        this.brandListLoading = false
      })
    },

    // 单选
    getSelectBrandRow(row) {
      this.brandListSelections = [row]
    },

    // 确定事件
    submitBrand() {
      let brands = []
      this.brandListSelections.forEach(item => {
        let brandIndex = brands.findIndex((brand) => brand.brandId === item.brandId)
        if (brandIndex === -1) {
          brands.push({
            brandId: item.brandId, brandName: item.name,
            brandImgUrl: (item.imgUrl).indexOf('http') === -1 ? this.resourcesUrl + item.imgUrl : item.imgUrl
          })
        }
      })
      this.$emit('refreshSelectBrand', brands)
      this.brandListSelections = []
      this.visible = false
    }
  }
}
</script>

<style lang="scss">
.brand-select {
  .prods-select-body {
    height: 600px;
    overflow: auto;
  }
  .el-dialog__header {
    border: 1px solid #eee;
  }
  .el-dialog__body {
    padding: 20px;
  }
  .el-form-item {
    margin-bottom: 20px;
  }
  .pagination-container {
    margin-top: 0;
    text-align: right;
    padding: 15px 0;
  }
  .brand-img {
    width: auto;
    max-width: 100%;
    height: auto;
    max-height: 80px;
  }
}
</style>