<template>
  <div class="component-attr-add-or-update">
    <el-dialog
      v-model="visible"
      :title="dataForm.attrId? $t('table.edit'): $t('table.create')"
      :close-on-click-modal="false"
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
                v-for="(attr,idx) in dataForm.attrValues"
                :key="attr.attrValueId"
                :class="['attr-val-item',attr.error?'error':'']"
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
  </div>
</template>

<script setup>
import * as api from '@/api/product/attr'
import { reactive } from 'vue'

const emit = defineEmits(['refreshDataList'])

const Data = reactive({
  visible: false,
  dataForm: {
    attrId: '',
    attrType: 0, // 属性类型 0:销售属性，1:基本属性
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
  selectedCategorys: [],
  categorySelectorVisible: false // 分类弹窗显隐
})
const { visible, dataForm, rules, attrValue } = toRefs(Data)

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
      // Data.categoryShow(data.categories) // 分类回显
    })
  })
}

// 关闭dialog时
const closeDialog = () => {
  Data.dataForm = {
    attrId: 0,
    attrValues: [], // 属性值列表
    desc: '', // 属性描述
    name: '' // 属性名称
  }
  Data.selectedCategorys = []
}

/**
     * 添加属性值
     */
const addAttrValue = (attrVal) => {
  if (!attrVal) {
    return
  }
  Data.dataForm.attrValues.forEach((el, idx) => {
    if (el.value === attrVal) {
      Data.dataForm.attrValues.splice(idx, 1)
    }
  })
  Data.dataForm.attrValues.push({ attrValueId: null, attrId: null, value: attrVal })
  Data.attrValue = ''
  // console.log('添加属性值dataForm.attrValues：', Data.dataForm.attrValues)
}

/**
     * 删除属性值
     */
const deleteAttrCalue = (idx) => {
  Data.dataForm.attrValues.splice(idx, 1)
  // console.log('删除属性值dataForm.attrValues：', Data.dataForm.attrValues)
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
    if (!Data.dataForm.attrValues.length) {
      ElMessage({
        message: '请至少创建一个属性值',
        type: 'warining',
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

</style>
