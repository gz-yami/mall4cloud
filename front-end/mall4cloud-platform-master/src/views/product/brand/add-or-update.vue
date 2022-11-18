<template>
  <el-dialog :title="dataForm.brandId? $t('table.edit'): $t('table.create')" :close-on-click-modal="false" :visible.sync="visible" class="brand-add-upd" width="800px" @close="closeDialog">
    <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="left" label-width="90px" style="width:620px;margin-left:50px;">
      <!-- 品牌名称 -->
      <el-form-item :label="$t('product.brand.name')" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入品牌名称" />
      </el-form-item>
      <!-- 品牌描述 -->
      <el-form-item :label="$t('product.brand.desc')" prop="desc">
        <el-input v-model="dataForm.desc" type="textarea" :rows="2" maxlength="30" show-word-limit placeholder="请输入品牌描述" />
      </el-form-item>
      <!-- 品牌logo图片 -->
      <el-form-item :label="$t('product.brand.imgUrl')" prop="imgUrl">
        <img-upload v-model="dataForm.imgUrl" />
      </el-form-item>
      <!-- 检索首字母 -->
      <el-form-item :label="$t('product.brand.firstLetter')" prop="firstLetter">
        <el-input v-model="dataForm.firstLetter" placeholder="请输入首字母" />
      </el-form-item>
      <!-- 选择分类 -->
      <el-form-item :label="this.$i18n.t('product.category.categoryParent')">
        <category-group
          :selected-categories="selectedCategories"
          :multiple="true"
          @selectOrReviseCategory="selectOrReviseCategory"
          @deleteCategoryItemOfSelected="deleteCategoryItemOfSelected"
        />
      </el-form-item>
      <!-- 排序 -->
      <el-form-item :label="$t('product.brand.seq')" prop="seq">
        <el-input-number
          v-model="dataForm.seq"
          controls-position="right"
          :precision="0"
          :min="0"
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">
        {{ $t('table.cancel') }}
      </el-button>
      <el-button type="primary" @click="dataFormSubmit()">
        {{ $t('table.confirm') }}
      </el-button>
    </div>
    <category-selector v-if="categorySelectorVisible" ref="categorySelector" @getCategorySelected="getCategorySelected" />
  </el-dialog>
</template>

<script>
import * as api from '@/api/product/brand'
import ImgUpload from '@/components/ImgUpload'
import categorySelector from '@/components/CategorySelector'
import categoryGroup from '@/components/CategoryGroup'
export default {
  components: {
    ImgUpload,
    categorySelector,
    categoryGroup
  },
  data() {
    return {
      visible: false,
      dataForm: {
        brandId: 0,
        name: null,
        desc: null,
        imgUrl: null,
        firstLetter: null,
        categoryIds: [], // 分类id列表
        seq: null
        // status: 1
      },
      selectedCategories: [],
      categorySelectorVisible: false, // 分类弹窗显隐
      rules: {
        name: [
          { required: true, message: '品牌名称不能为空', trigger: 'blur' }
        ],
        imgUrl: [
          { required: true, message: 'logo图片不能为空', trigger: 'blur' }
        ],
        firstLetter: [
          { required: true, message: '首字母不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init(brandId) {
      this.dataForm.brandId = brandId || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!this.dataForm.brandId) {
          return
        }
        api.get(brandId).then(data => {
          this.dataForm = data
          this.categoryShow(data.categories) // 分类回显
        })
      })
    },

    /**
     * 分类回显
     */
    categoryShow(categories) {
      if (categories.length) {
        var categoryObj = {}
        var categoryIds = []
        var newCatagories = []
        categories.forEach((el, index) => {
          categoryIds.push(el.categoryId)
          if (el.pathNames && el.pathNames.length) {
            if (el.pathNames[0]) newCatagories.push(el.pathNames[0])
            if (el.pathNames[1]) newCatagories.push(el.pathNames[1])
          }
          if (el.name) newCatagories.push(el.name)
          categoryObj.firstCategoryName = newCatagories[0]
          categoryObj.secondCategoryName = newCatagories[1]
          categoryObj.threeCategoryName = newCatagories[2]
          this.selectedCategories.push(categoryObj)
          newCatagories = []
          categoryObj = {}
        })
        this.dataForm.categoryIds = categoryIds
      }
    },

    /**
     * 选择分类弹窗
     */
    selectOrReviseCategory() {
      this.categorySelectorVisible = true
      this.$nextTick(() => {
        this.$refs.categorySelector.init() // 1代表从创建分类进入
      })
    },

    /**
     * 获取子组件返回数据
     */
    getCategorySelected(selectedCategories, categoryId) {
      console.log('获取子组件返回数据：selectedCategories:', selectedCategories, '；parentId:', categoryId)
      this.categorySelectorVisible = false
      var categoryObj = {}
      categoryObj.firstCategoryName = selectedCategories[0]
      categoryObj.secondCategoryName = selectedCategories[1]
      categoryObj.threeCategoryName = selectedCategories[2]
      // 去重
      if (this.selectedCategories.length) {
        this.dataForm.categoryIds.forEach((item, index) => {
          if (item === categoryId) {
            this.selectedCategories.splice(index, 1)
            this.dataForm.categoryIds.splice(index, 1)
            return
          }
        })
      }
      this.dataForm.categoryIds.push(categoryId)
      this.selectedCategories.push(categoryObj)
    },

    // 删除选中的某一项分类
    deleteCategoryItemOfSelected(index) {
      this.selectedCategories.splice(index, 1)
      this.dataForm.categoryIds.splice(index, 1)
    },

    // 关闭dialog时
    closeDialog() {
      this.dataForm = {
        brandId: 0,
        name: null,
        desc: null,
        imgUrl: null,
        firstLetter: null,
        categoryIds: [], // 分类id列表
        seq: null
      }
      this.selectedCategories = []
    },

    // 表单提交
    dataFormSubmit() {
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          return
        }
        var dataForm = {
          brandId: this.dataForm.brandId,
          name: this.dataForm.name,
          desc: this.dataForm.desc,
          imgUrl: this.dataForm.imgUrl,
          firstLetter: this.dataForm.firstLetter,
          categoryIds: this.dataForm.categoryIds, // 分类id列表
          seq: this.dataForm.seq
          // status: this.dataForm.status
        }
        const request = this.dataForm.brandId ? api.update(dataForm) : api.save(dataForm)
        request.then(data => {
          this.$message({
            message: this.$t('table.actionSuccess'),
            type: 'success',
            duration: 1500,
            onClose: () => {
              this.visible = false
              this.$emit('refreshDataList')
              this.$refs['dataForm'].resetFields()
            }
          })
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
p {
  margin: 0;
}
.brand-add-upd {
  .el-form-item.el-form-item--medium {
    .el-form-item--medium .el-form-item__label {
      text-align: right !important;
    }
  }
}
</style>
