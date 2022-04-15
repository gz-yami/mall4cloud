<template>
  <el-dialog :title="dataForm.categoryId? $t('table.edit'): $t('table.create')" :close-on-click-modal="false" :visible.sync="visible" top="10vh" width="750px" @close="closeDialog">
    <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="left" label-width="90px" class="category-form" style="width: 500px; margin-left:50px;">
      <!-- 分类名称 -->
      <el-form-item :label="$t('product.category.name')" prop="name">
        <el-input v-model="dataForm.name" :placeholder="$t('product.category.enterCateName')" maxlength="255" />
      </el-form-item>
      <!-- 分类图片 -->
      <el-form-item :label="$t('product.category.imgUrl')" prop="imgUrl">
        <img-upload v-model="dataForm.imgUrl" />
        <span v-if="dataForm.parentId === 0">{{ this.$i18n.t('product.category.recommImgSize') + '510*80' }}</span>
      </el-form-item>
      <!-- 分类图标 -->
      <el-form-item :label="$t('product.category.icon')" prop="icon">
        <img-upload v-model="dataForm.icon" />
      </el-form-item>
      <!-- 上级分类 -->
      <el-form-item v-if="showSelectColumnOfCategory" :label="this.$i18n.t('product.category.categoryParent')">
        <category-group
          :selected-categories="selectedCategories"
          :show-category-select-btn="showCategorySelectBtn"
          :single="true"
          @selectOrReviseCategory="selectOrReviseCategory"
        />
      </el-form-item>
      <!-- 排序 -->
      <el-form-item :label="$t('product.category.seq')" prop="seq">
        <el-input-number
          v-model="dataForm.seq"
          :min="0"
          controls-position="right"
          :precision="0"
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
import * as api from '@/api/product/category'
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
        categoryId: 0,
        shopId: null,
        parentId: 0,
        name: null,
        desc: null,
        path: null,
        status: '1',
        icon: '',
        imgUrl: '',
        level: null,
        seq: 1
      },
      showCategorySelectBtn: true,
      categoryList: [],
      categorySelectorVisible: false,
      selectedCategories: [],
      showSelectColumnOfCategory: true, // 是否显示上级分类栏
      rules: {
        name: [
          { required: true, message: this.$i18n.t('product.category.categoryNoNull'), trigger: 'blur' }
        ],
        imgUrl: [
          { required: true, message: this.$i18n.t('product.category.imageNoNull'), trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init(categoryId) {
      this.visible = true
      this.dataForm.categoryId = categoryId || 0
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!this.dataForm.categoryId) {
          return
        }
        if (categoryId) {
          api.get(categoryId).then(data => {
            this.dataForm = data
            this.selectedCategories = data.pathNames ? data.pathNames : []
            this.showCategorySelectBtn = false
            data.level === 0 ? this.showSelectColumnOfCategory = false : this.showSelectColumnOfCategory = true
          })
        }
      })
    },

    // 关闭dialog时
    closeDialog() {
      this.selectedCategories = []
      this.showCategorySelectBtn = true
      this.showSelectColumnOfCategory = true
    },

    /**
     * 选择分类弹窗
     */
    selectOrReviseCategory() {
      this.categorySelectorVisible = true
      this.$nextTick(() => {
        this.$refs.categorySelector.init(1) // 1代表从创建分类进入
      })
    },

    // handleChange(val) {
    //   this.dataForm.parentId = val[val.length - 1]
    //   console.log('selectedCategory:', this.selectedCategory)
    // },

    /**
     * 获取子组件返回数据
     */
    getCategorySelected(selectedCategories, parentId) {
      console.log('父组件接收子组件数据：selectedCategories:', selectedCategories, '；parentId:', parentId)
      this.categorySelectorVisible = false
      this.selectedCategories = selectedCategories
      this.dataForm.parentId = parentId
    },

    // 表单提交
    dataFormSubmit() {
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          return
        }
        // 层级 0第一级
        this.dataForm.level = !this.selectedCategories.length ? 0 : this.selectedCategories.length
        const request = this.dataForm.categoryId ? api.update(this.dataForm) : api.save(this.dataForm)
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

<style lang="scss">
.category-form {
  .category-group {
    display: flex;
    align-items: center;
    height: 36px;
    line-height: 36px;
    .category {
      display: flex;
      align-items: center;
      .classify {
        height: 30px;
        line-height: 30px;
        padding: 0 8px;
      }
    }
  }
}
</style>
