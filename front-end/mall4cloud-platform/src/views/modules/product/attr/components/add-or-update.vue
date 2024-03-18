<template>
  <div class="component-attr-add-or-update">
    <el-dialog
      v-model="visible"
      :title="dataForm.attrId ? $t('table.edit') : $t('table.create')"
      :close-on-click-modal="false"
      top="8vh"
      :destroy-on-close="true"
      width="750px"
      @close="closeDialog"
    >
      <el-form
        ref="dataFormRef"
        :rules="rules"
        :model="dataForm"
        label-position="left"
        label-width="80px"
        style="min-width: 600px; width: 600px; margin-left:35px;"
      >
        <!-- 属性名称 -->
        <el-form-item
          :label="$t('product.attr.name')"
          prop="name"
        >
          <el-input
            v-model="dataForm.name"
            maxlength="20"
            show-word-limit
            placeholder="请输入属性名称"
          />
        </el-form-item>
        <!-- 属性描述 -->
        <el-form-item
          :label="$t('product.attr.desc')"
          prop="desc"
        >
          <el-input
            v-model="dataForm.desc"
            type="textarea"
            :rows="2"
            maxlength="30"
            show-word-limit
            placeholder="请输入新增属性的相关描述，30字以内"
          />
        </el-form-item>
        <!-- 是否需要搜索 -->
        <el-form-item
          v-if="dataForm.attrType === 1"
          :label="$t('product.attr.searchType')"
          prop="searchType"
        >
          <el-radio
            v-model="dataForm.searchType"
            :label="0"
          >
            不需要
          </el-radio>
          <el-radio
            v-model="dataForm.searchType"
            :label="1"
          >
            需要
          </el-radio>
        </el-form-item>
        <!-- 选择分类 -->
        <el-form-item
          v-if="dataForm.attrType === 1"
          :label="$t('product.category.categoryParent')"
        >
          <category-group
            :selected-categories="selectedCategories"
            :multiple="true"
            @select-or-revise-category="selectOrReviseCategory"
            @delete-category-item-of-selected="deleteCategoryItemOfSelected"
          />
        </el-form-item>
        <!-- 属性值 -->
        <el-form-item
          :label="$t('product.attr.attrValue')"
          prop="attrValues"
        >
          <div class="attr-int">
            <el-input
              v-model="attrValue"
              clearable
              maxlength="20"
              show-word-limit
              placeholder="请输入要添加的属性值，20字以内"
            />
            <el-button
              icon="el-icon-plus"
              class="add-btn"
              @click="addAttrValue(attrValue)"
            >
              添加
            </el-button>
          </div>
          <div
            v-if="dataForm.attrValues.length"
            class="attr-val"
          >
            <div class="attr-scr">
              <div
                v-for="(attr, idx) in dataForm.attrValues"
                :key="attr.attrValueId"
                :class="['attr-val-item', attr.error ? 'error' : '']"
              >
                <el-input
                  v-model="attr.value"
                  maxlength="20"
                  show-word-limit
                  placeholder="请输入要添加的属性值，20字以内"
                  @blur="checkIntValue(idx)"
                />
                <el-button
                  type="primary"
                  link
                  class="txt-del-btn"
                  @click="deleteAttrCalue(idx)"
                >
                  删除
                </el-button>
              </div>
            </div>
          </div>
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
    </el-dialog>
    <category-selector
      v-if="categorySelectorVisible"
      ref="categorySelectorRef"
      @get-category-selected="getCategorySelected"
    />
  </div>
</template>

<script setup>
import * as api from '@/api/product/attr'
import { reactive } from 'vue'

const emit = defineEmits(['refreshDataList'])

const Data = reactive({
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
})

const { visible, dataForm, rules, attrValue, selectedCategories, categorySelectorVisible } = toRefs(Data)

const dataFormRef = ref()
const init = (attrId) => {
  Data.dataForm.attrId = attrId || 0
  Data.visible = true
  nextTick(() => {
    dataFormRef.value.resetFields()
    if (!Data.dataForm.attrId) {
      Data.attrValue = null
      Data.dataForm.attrValues = []
      return
    }
    api.get(attrId).then(data => {
      Data.dataForm = data
      Data.dataForm.attrValues = data.attrValues ? data.attrValues : []
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
  } else {
    Data.dataForm.categoryIds = []
  }
}

// 关闭dialog时
const closeDialog = () => {
  Data.dataForm = {
    attrId: 0,
    attrType: 1, // 属性类型 0:销售属性(无需绑定分类)，1:基本属性(必须绑定分类)
    attrValues: [], // 属性值列表
    categoryIds: [], // 分类id列表
    desc: '', // 属性描述
    name: '', // 属性名称
    searchType: 1 // 作为搜索参数 0:不需要，1:需要
  }
  Data.selectedCategories = []
}

const categorySelectorRef = ref()
/**
 * 选择分类弹窗
 */
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

/**
 * 添加属性值
 */
const addAttrValue = (attrVal) => {
  if (!attrVal) {
    return ElMessage({
      message: '添加属性值为空',
      type: 'warning'
    })
  }
  Data.dataForm.attrValues.forEach((el, idx) => {
    if (el.value === attrVal) {
      Data.dataForm.attrValues.splice(idx, 1)
      ElMessage({
        message: '属性值重复',
        type: 'warning'
      })
    }
  })
  Data.dataForm.attrValues.push({ value: attrVal })
  Data.attrValue = ''
}

/**
 * 删除属性值
 */
const deleteAttrCalue = (idx) => {
  Data.dataForm.attrValues.splice(idx, 1)
}

/**
 * 失焦时属性值是否为空
 */
const checkIntValue = (idx) => {
  if (!Data.dataForm.attrValues[idx].value) {
    ElMessage({
      message: '已创建的属性值不能为空！请重新创建',
      type: 'warining',
      duration: 1500
    })
    Data.dataForm.attrValues.splice(idx, 1)
  }
}

/**
 * 表单提交
 */
const dataFormSubmit = () => {
  dataFormRef.value.validate(valid => {
    if (!valid) {
      return
    }
    if (Data.dataForm.attrType === 1 && (!Data.dataForm.categoryIds || !Data.dataForm.categoryIds.length)) {
      ElMessage({
        message: '请至少选择一个分类',
        duration: 1500
      })
      return
    }
    if (Data.dataForm.attrType === 1 && Data.dataForm.searchType === 1 && !Data.dataForm.attrValues.length) {
      ElMessage({
        message: '请至少创建一个属性值',
        duration: 1500
      })
      return
    }
    const request = Data.dataForm.attrId ? api.update(Data.dataForm) : api.save(Data.dataForm)
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
.component-attr-add-or-update {
  .attr-int {
    display: flex;
    align-items: center;
    justify-content: space-between;
    .add-btn {
      margin-left: 10px;
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
    .attr-val-item {
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
    .attr-val-item:not(:last-child) {
      margin-bottom: 10px;
    }
    .attr-val-item .txt-del-btn {
      margin-left: 15px;
    }
  }
}
</style>
