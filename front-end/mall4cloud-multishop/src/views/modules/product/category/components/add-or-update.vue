<template>
  <el-dialog
    v-model="visible"
    :title="dataForm.categoryId? $t('table.edit'): $t('table.create')"
    :close-on-click-modal="false"
    class="component-category-add-or-update"
    top="10vh"
    width="750px"
    @close="closeDialog"
  >
    <el-form
      ref="dataFormRef"
      :rules="rules"
      :model="dataForm"
      label-position="left"
      label-width="90px"
      class="category-form"
      style="width: 500px; margin-left:50px;"
    >
      <!-- 分类名称 -->
      <el-form-item
        :label="$t('product.category.name')"
        prop="name"
      >
        <el-input
          v-model="dataForm.name"
          :placeholder="$t('product.category.enterCateName')"
          maxlength="255"
        />
      </el-form-item>
      <!-- 分类图片 -->
      <el-form-item
        :label="$t('product.category.imgUrl')"
        prop="imgUrl"
      >
        <img-upload v-model="dataForm.imgUrl" />
        <span v-if="dataForm.parentId === 0">{{ $t('product.category.recommImgSize') + '510*80' }}</span>
      </el-form-item>
      <!-- 分类图标 -->
      <el-form-item
        :label="$t('product.category.icon')"
        prop="icon"
      >
        <img-upload v-model="dataForm.icon" />
      </el-form-item>
      <!-- 上级分类 -->
      <el-form-item
        v-if="showSelectColumnOfCategory"
        class="higher-category"
        :label="$t('product.category.categoryParent')"
      >
        <category-group
          :selected-categorys="selectedCategorys"
          :show-category-select-btn="showCategorySelectBtn"
          :single="true"
          @select-or-revise-category="selectOrReviseCategory"
        />
      </el-form-item>
      <!-- 排序 -->
      <el-form-item
        :label="$t('product.category.seq')"
        prop="seq"
      >
        <el-input-number
          v-model="dataForm.seq"
          :min="0"
          controls-position="right"
          :precision="0"
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
import * as api from '@/api/product/category'
import { reactive } from 'vue'

const emit = defineEmits(['refreshDataList'])

const Data = reactive({
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
  selectedCategorys: [],
  showSelectColumnOfCategory: true, // 是否显示上级分类栏
  rules: {
    name: [
      { required: true, message: $t('product.category.categoryNoNull'), trigger: 'blur' }
    ],
    imgUrl: [
      { required: true, message: $t('product.category.imageNoNull'), trigger: 'blur' }
    ]
  }
})
const { visible, dataForm, showCategorySelectBtn, categorySelectorVisible, selectedCategorys, showSelectColumnOfCategory, rules } = toRefs(Data)

const dataFormRef = ref()
const init = (categoryId) => {
  Data.visible = true
  Data.dataForm.categoryId = categoryId || 0
  nextTick(() => {
    dataFormRef.value.resetFields()
    if (!Data.dataForm.categoryId) {
      return
    }
    if (categoryId) {
      api.get(categoryId).then(data => {
        Data.dataForm = data
        Data.selectedCategorys = data.pathNames ? data.pathNames : []
        Data.showCategorySelectBtn = false
        data.level === 0 ? Data.showSelectColumnOfCategory = false : Data.showSelectColumnOfCategory = true
      })
    }
  })
}

// 关闭dialog时
const closeDialog = () => {
  Data.dataForm = {
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
  }
  Data.selectedCategorys = []
  Data.showCategorySelectBtn = true
  Data.showSelectColumnOfCategory = true
}

/**
 * 选择分类弹窗
 */
const categorySelectorRef = ref()
const selectOrReviseCategory = () => {
  Data.categorySelectorVisible = true
  nextTick(() => {
    categorySelectorRef.value.init(1) // 1代表从创建分类进入
  })
}

/**
 * 获取子组件返回数据
 */
const getCategorySelected = (selectedCategorys, parentId) => {
  Data.categorySelectorVisible = false
  Data.selectedCategorys = selectedCategorys
  Data.dataForm.parentId = parentId
}

// 表单提交
const dataFormSubmit = () => {
  dataFormRef.value.validate(valid => {
    if (!valid) {
      return
    }
    // 层级 0第一级
    Data.dataForm.level = !Data.selectedCategorys.length ? 0 : Data.selectedCategorys.length
    const request = Data.dataForm.categoryId ? api.update(Data.dataForm) : api.save(Data.dataForm)
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
