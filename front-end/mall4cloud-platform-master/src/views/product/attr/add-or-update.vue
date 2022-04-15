<template>
  <div class="attr-manage">
    <el-dialog :title="dataForm.attrId? $t('table.edit'): $t('table.create')" :close-on-click-modal="false" top="8vh" :destroy-on-close="true" :visible.sync="visible" width="750px" @close="closeDialog">
      <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="left" label-width="80px" style="min-width: 600px; width: 600px; margin-left:35px;">
        <!-- 属性名称 -->
        <el-form-item :label="$t('product.attr.name')" prop="name">
          <el-input v-model="dataForm.name" maxlength="20" show-word-limit placeholder="请输入属性名称" />
        </el-form-item>
        <!-- 属性描述 -->
        <el-form-item :label="$t('product.attr.desc')" prop="desc">
          <el-input v-model="dataForm.desc" type="textarea" :rows="2" maxlength="30" show-word-limit placeholder="请输入新增属性的相关描述，30字以内" />
        </el-form-item>
        <!-- 是否需要搜索 -->
        <el-form-item v-if="dataForm.attrType === 1" :label="$t('product.attr.searchType')" prop="searchType">
          <el-radio v-model="dataForm.searchType" :label="0">不需要</el-radio>
          <el-radio v-model="dataForm.searchType" :label="1">需要</el-radio>
        </el-form-item>
        <!-- 选择分类 -->
        <el-form-item v-if="dataForm.attrType === 1" :label="this.$i18n.t('product.category.categoryParent')">
          <category-group
            :selected-categories="selectedCategories"
            :multiple="true"
            @selectOrReviseCategory="selectOrReviseCategory"
            @deleteCategoryItemOfSelected="deleteCategoryItemOfSelected"
          />
        </el-form-item>
        <!-- 属性值 -->
        <el-form-item :label="$t('product.attr.attrValue')" prop="attrValues">
          <div class="attr-int">
            <el-input v-model="attrValue" clearable maxlength="20" show-word-limit placeholder="请输入要添加的属性值，20字以内" />
            <el-button icon="el-icon-plus" class="add-btn" @click="addAttrValue(attrValue)">添加</el-button>
          </div>
          <div v-if="dataForm.attrValues.length" class="attr-val">
            <div class="attr-scr">
              <div v-for="(attr,idx) in dataForm.attrValues" :key="attr.attrValueId" :class="['attr-val-item',attr.error?'error':'']">
                <el-input v-model="attr.value" maxlength="20" show-word-limit placeholder="请输入要添加的属性值，20字以内" @blur="checkIntValue(idx)" />
                <el-button type="text" class="txt-del-btn" @click="deleteAttrCalue(idx)">删除</el-button>
              </div>
            </div>
          </div>
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
    </el-dialog>
    <category-selector v-if="categorySelectorVisible" ref="categorySelector" @getCategorySelected="getCategorySelected" />
  </div>
</template>

<script>
import * as api from '@/api/product/attr'
import categorySelector from '@/components/CategorySelector'
import categoryGroup from '@/components/CategoryGroup'
export default {
  components: {
    categorySelector,
    categoryGroup
  },
  data() {
    return {
      visible: false,
      dataForm: {
        attrId: 0,
        attrType: 1, // 属性类型 0:销售属性，1:基本属性
        attrValues: [], // 属性值列表
        categoryIds: [], // 分类id列表
        desc: '', // 属性描述
        name: '', // 属性名称
        searchType: 1 // 作为搜索参数 0:不需要，1:需要
      },
      rules: {
        name: [
          { required: true, message: '请输入属性名称', trigger: 'blur' }
        ]
      },
      attrValue: null,
      selectedCategories: [],
      categorySelectorVisible: false // 分类弹窗显隐
    }
  },
  mounted() {
  },
  created() {
  },
  methods: {
    init(attrId) {
      this.dataForm.attrId = attrId || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!this.dataForm.attrId) {
          this.attrValue = null
          this.dataForm.attrValues = []
          return
        }
        api.get(attrId).then(data => {
          this.dataForm = data
          this.dataForm.attrValues = data.attrValues ? data.attrValues : []
          console.log('attrValues：', this.dataForm.attrValues)
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
      } else {
        this.dataForm.categoryIds = []
      }
    },

    // 关闭dialog时
    closeDialog() {
      this.dataForm = {
        attrId: 0,
        attrType: 1, // 属性类型 0:销售属性(无需绑定分类)，1:基本属性(必须绑定分类)
        attrValues: [], // 属性值列表
        categoryIds: [], // 分类id列表
        desc: '', // 属性描述
        name: '', // 属性名称
        searchType: 1 // 作为搜索参数 0:不需要，1:需要
      }
      this.selectedCategories = []
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
      console.log('获取子组件返回数据categoryIds:', this.dataForm.categoryIds)
    },

    // 删除选中的某一项分类
    deleteCategoryItemOfSelected(index) {
      console.log('删除分类index：', index)
      this.selectedCategories.splice(index, 1)
      this.dataForm.categoryIds.splice(index, 1)
    },

    /**
     * 添加属性值
     */
    addAttrValue(attrVal) {
      if (!attrVal) {
        return
      }
      this.dataForm.attrValues.forEach((el, idx) => {
        if (el.value === attrVal) {
          this.dataForm.attrValues.splice(idx, 1)
        }
      })
      this.dataForm.attrValues.push({ value: attrVal })
      this.attrValue = ''
      console.log('添加属性值dataForm.attrValues：', this.dataForm.attrValues)
    },

    /**
     * 删除属性值
     */
    deleteAttrCalue(idx) {
      this.dataForm.attrValues.splice(idx, 1)
      console.log('删除属性值dataForm.attrValues：', this.dataForm.attrValues)
    },

    /**
     * 失焦时属性值是否为空
     */
    checkIntValue(idx) {
      if (!this.dataForm.attrValues[idx].value) {
        this.$message({
          message: '已创建的属性值不能为空！请重新创建',
          type: 'warining',
          duration: 1500
        })
        this.dataForm.attrValues.splice(idx, 1)
      }
    },

    /**
     * 表单提交
     */
    dataFormSubmit() {
      console.log('categoryIds:', this.dataForm.categoryIds)
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          return
        }
        if (this.dataForm.attrType === 1 && (!this.dataForm.categoryIds || !this.dataForm.categoryIds.length)) {
          this.$message({
            message: '请至少选择一个分类',
            duration: 1500
          })
          return
        }
        if (this.dataForm.attrType === 1 && this.dataForm.searchType === 1 && !this.dataForm.attrValues.length) {
          this.$message({
            message: '请至少创建一个属性值',
            duration: 1500
          })
          return
        }
        console.log(this.dataForm)
        const request = this.dataForm.attrId ? api.update(this.dataForm) : api.save(this.dataForm)
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
p {
  margin: 0;
}
.attr-manage {
  .attr-int {
    display: flex;
    align-items: center;
    justify-content: space-between;
    .add-btn {
      margin-left: 10px;
    }
    .add-btn.el-button--medium {
      padding: 10px 10px !important;
    }
  }
  .attr-val {
    background: #f8f8f8;
    margin-top: 20px;
    padding: 20px;
    box-sizing: border-box;
    .attr-scr {
      min-height: 40px;
      max-height: 220px;
      overflow-y: auto;
      padding: 0 20px;
    }
    // .attr-scr::-webkit-scrollbar { // 隐藏滚动条
    //   display: none;
    // }
    .attr-val-item {
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
    .attr-val-item:not(:last-child) {
      margin-bottom: 10px;
    }
    .attr-val-item .int-con {
      display: block;
      width: 100%;
    }
    .attr-val-item .txt-del-btn {
      margin-left: 15px;
    }
  }
}
</style>
