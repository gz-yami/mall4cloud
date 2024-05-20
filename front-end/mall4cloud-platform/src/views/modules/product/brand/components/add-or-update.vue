<template>
  <el-dialog
    v-model="visible"
    :title="dataForm.brandId? $t('table.edit'): $t('table.create')"
    :close-on-click-modal="false"
    class="component-brand-add-or-update"
    width="800px"
    @close="closeDialog"
  >
    <el-form
      ref="dataFormRef"
      :rules="rules"
      :model="dataForm"
      label-width="100px"
      style="width:620px;margin-left:50px;"
    >
      <!-- 品牌名称 -->
      <el-form-item
        :label="$t('product.brand.name')"
        prop="name"
      >
        <el-input
          v-model="dataForm.name"
          placeholder="请输入品牌名称"
        />
      </el-form-item>
      <!-- 品牌描述 -->
      <el-form-item
        :label="$t('product.brand.desc')"
        prop="desc"
      >
        <el-input
          v-model="dataForm.desc"
          type="textarea"
          :rows="2"
          maxlength="30"
          show-word-limit
          placeholder="请输入品牌描述"
        />
      </el-form-item>
      <!-- 品牌logo图片 -->
      <el-form-item
        :label="$t('product.brand.imgUrl')"
        prop="imgUrl"
      >
        <img-upload v-model="dataForm.imgUrl" />
      </el-form-item>
      <!-- 检索首字母 -->
      <el-form-item
        :label="$t('product.brand.firstLetter')"
        prop="firstLetter"
      >
        <el-input
          v-model="dataForm.firstLetter"
          maxlength="1"
          placeholder="请输入首字母"
        />
      </el-form-item>
      <!-- 选择分类 -->
      <el-form-item :label="$t('product.category.categoryParent')">
        <category-group
          :selected-categories="selectedCategories"
          :multiple="true"
          @select-or-revise-category="selectOrReviseCategory"
          @delete-category-item-of-selected="deleteCategoryItemOfSelected"
        />
      </el-form-item>
      <!-- 排序 -->
      <el-form-item
        :label="$t('product.brand.seq')"
        prop="seq"
      >
        <el-input-number
          v-model="dataForm.seq"
          controls-position="right"
          :precision="0"
          :min="0"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div
        class="dialog-footer"
      >
        <el-button @click="visible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button
          type="primary"
          @click="dataFormSubmit()"
        >
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </template>

    <category-selector
      v-if="categorySelectorVisible"
      ref="categorySelectorRef"
      @get-category-selected="getCategorySelected"
    />
  </el-dialog>
</template>

<script setup>
import { reactive } from 'vue'
import * as api from '@/api/product/brand'
const emit = defineEmits(['refreshDataList'])

const Data = reactive({
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
      { required: true, message: '首字母不能为空', trigger: 'blur' },
      { pattern: /^[A-Z]$/, message: '首字母必须为大写英文字母', trigger: 'blur' }
    ]
  }
})
const { visible, dataForm, selectedCategories, categorySelectorVisible, rules } = toRefs(Data)

const dataFormRef = ref()
const init = (brandId) => {
  Data.dataForm.brandId = brandId || 0
  Data.visible = true
  nextTick(() => {
    dataFormRef.value.resetFields()
    if (!Data.dataForm.brandId) {
      return
    }
    api.get(brandId).then(data => {
      Data.dataForm = data
      categoryShow(data.categories) // 分类回显
    })
  })
}

/**
     * 分类回显
     */
const categoryShow = (categories) => {
  if (categories.length) {
    let categoryObj = {}
    const categoryIds = []
    let newCatagories = []
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
      Data.selectedCategories.push(categoryObj)
      newCatagories = []
      categoryObj = {}
    })
    Data.dataForm.categoryIds = categoryIds
  }
}

/**
     * 选择分类弹窗
     */
const categorySelectorRef = ref()
const selectOrReviseCategory = () => {
  Data.categorySelectorVisible = true
  nextTick(() => {
    categorySelectorRef.value.init() // 1代表从创建分类进入
  })
}

/**
     * 获取子组件返回数据
     */
const getCategorySelected = (selectedCategories, categoryId) => {
  Data.categorySelectorVisible = false
  const categoryObj = {}
  categoryObj.firstCategoryName = selectedCategories[0]
  categoryObj.secondCategoryName = selectedCategories[1]
  categoryObj.threeCategoryName = selectedCategories[2]
  // 去重
  if (Data.selectedCategories.length) {
    Data.dataForm.categoryIds.forEach((item, index) => {
      if (item === categoryId) {
        Data.selectedCategories.splice(index, 1)
        Data.dataForm.categoryIds.splice(index, 1)
      }
    })
  }
  Data.dataForm.categoryIds.push(categoryId)
  Data.selectedCategories.push(categoryObj)
}

// 删除选中的某一项分类
const deleteCategoryItemOfSelected = (index) => {
  Data.selectedCategories.splice(index, 1)
  Data.dataForm.categoryIds.splice(index, 1)
}

// 关闭dialog时
const closeDialog = () => {
  Data.dataForm = {
    brandId: 0,
    name: null,
    desc: null,
    imgUrl: null,
    firstLetter: null,
    categoryIds: [], // 分类id列表
    seq: null
  }
  Data.selectedCategories = []
}

// 表单提交
const dataFormSubmit = () => {
  dataFormRef.value.validate(valid => {
    if (!valid) {
      return
    }
    const dataForm = {
      brandId: Data.dataForm.brandId,
      name: Data.dataForm.name,
      desc: Data.dataForm.desc,
      imgUrl: Data.dataForm.imgUrl,
      firstLetter: Data.dataForm.firstLetter,
      categoryIds: Data.dataForm.categoryIds, // 分类id列表
      seq: Data.dataForm.seq || 0
    }
    const request = Data.dataForm.brandId ? api.update(dataForm) : api.save(dataForm)
    request.then(() => {
      ElMessage({
        message: $t('table.actionSuccess'),
        type: 'success',
        duration: 1500,
        onClose: () => {
          Data.visible = false
          emit('refreshDataList')
          dataFormRef.value.resetFields()
        }
      })
    })
  })
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>
</style>
