<template>
  <el-dialog
    v-model="visible"
    title="选择品牌"
    top="50px"
    :close-on-click-modal="false"
    class="component-brand-selector"
  >
    <el-form
      :inline="true"
      :model="dataForm"
      class="demo-form-inline"
    >
      <el-form-item label="品牌名称">
        <el-input
          v-model.trim="brandName"
          placeholder="品牌名称"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="onSearch"
        >
          查询
        </el-button>
      </el-form-item>
    </el-form>

    <div class="prods-select-body">
      <el-table
        ref="brandTable"
        v-loading="brandListLoading"
        :data="pageVO.list"
        border
        style="width: 100%;"
      >
        <el-table-column
          v-if="isSingle"
          width="50"
          header-align="center"
          align="center"
        >
          <template #default="{ row }">
            <div>
              <el-radio
                v-model="singleSelectBrandId"
                :label="row.brandId"
                @change="getSelectBrandRow(row)"
              />
            </div>
          </template>
        </el-table-column>

        <el-table-column
          prop="brandName"
          header-align="center"
          align="center"
          label="品牌名称"
        >
          <template #default="{ row }">
            {{ row.name }}
          </template>
        </el-table-column>

        <el-table-column
          prop="firstChar"
          align="center"
          label="品牌首字母"
        >
          <template #default="{ row }">
            {{ row.firstLetter }}
          </template>
        </el-table-column>

        <el-table-column
          prop="status"
          align="center"
          label="状态"
        >
          <template #default="{ row }">
            <el-tag>
              {{ row.status === 1 ? '正常' : '下线' }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column
          align="center"
          width="140"
          label="品牌图片"
        >
          <template #default="{ row }">
            <img
              v-if="row.imgUrl"
              :src="(row.imgUrl).indexOf('http') === -1 ? resourcesUrl + row.imgUrl : row.imgUrl"
              class="brand-img"
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页条 -->
      <!-- <pagination v-show="pageVO.total>0" :total="pageVO.total" :page.sync="pageQuery.pageNum" :limit.sync="pageQuery.pageSize" @pagination="getBrandList()" /> -->
    </div>
    <template #footer>
      <span>
        <el-button
          type="primary"
          @click="visible = false"
        >取消</el-button>
        <el-button
          type="primary"
          @click="submitBrand()"
        >提交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { getBrandByCategoryId } from '@/api/product/brand'
import { reactive } from 'vue'

const emit = defineEmits(['refreshSelectBrand'])

defineProps({
  isSingle: {
    default: false,
    type: Boolean
  }
})

const Data = reactive({
  visible: false,
  brandName: null,
  selectBrand: [],
  dataForm: {
    brand: ''
  },
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  pageQuery: {
    categoryId: null
  },
  // 返回参数
  pageVO: {
    list: [], // 返回的列表
    total: 0, // 一共多少条数据
    pages: 0 // 一共多少页
  },
  brandListLoading: false,

  singleSelectBrandId: 0,
  brandListSelections: []
})
const { visible, brandName, dataForm, resourcesUrl, pageVO, brandListLoading, singleSelectBrandId } = toRefs(Data)

const init = (selectBrand, categoryId) => {
  Data.selectBrand = selectBrand
  Data.pageQuery.categoryId = categoryId
  Data.visible = true
  Data.brandListLoading = true
  if (Data.selectBrand) {
    Data.selectBrand.forEach(row => {
      Data.brandListSelections.push(row)
    })
  }
  getBrandList()
}

const onSearch = () => {
  getBrandList()
}

const getBrandList = () => {
  getBrandByCategoryId({ ...Data.pageQuery, ...Data.searchParam }).then(pageVO => {
    Data.pageVO.list = pageVO
    Data.brandListLoading = false
  })
}

// 单选
const getSelectBrandRow = (row) => {
  Data.brandListSelections = [row]
}

// 确定事件
const submitBrand = () => {
  const brands = []
  Data.brandListSelections.forEach(item => {
    const brandIndex = brands.findIndex((brand) => brand.brandId === item.brandId)
    if (brandIndex === -1) {
      brands.push({
        brandId: item.brandId,
        brandName: item.name,
        brandImgUrl: (item.imgUrl).indexOf('http') === -1 ? Data.resourcesUrl + item.imgUrl : item.imgUrl
      })
    }
  })
  emit('refreshSelectBrand', brands)
  Data.brandListSelections = []
  Data.visible = false
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>
.component-brand-selector {
  .prods-select-body {
    height: 600px;
    overflow: auto;
  }

  :deep(*) {
    .el-dialog__header {
      border: 1px solid #eee;
      margin-right: 0;
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
  }

  .brand-img {
    width: auto;
    max-width: 100%;
    height: auto;
    max-height: 80px;
  }
}
</style>
