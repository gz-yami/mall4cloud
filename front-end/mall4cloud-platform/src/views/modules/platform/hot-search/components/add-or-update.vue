<template>
  <el-dialog
    v-model="visible"
    :title="dataForm.hotSearchId? $t('table.edit'): $t('table.create')"
    class="component-hot-search-add-or-update"
  >
    <el-form
      ref="dataFormRef"
      :rules="rules"
      :model="dataForm"
      label-position="right"
      label-width="80px"
    >
      <!-- 热搜标题 -->
      <el-form-item
        :label="$t('platform.hotSearch.title')"
        prop="title"
      >
        <el-input v-model="dataForm.title" />
      </el-form-item>
      <!-- 内容 -->
      <el-form-item
        :label="$t('platform.hotSearch.content')"
        prop="content"
      >
        <el-input v-model="dataForm.content" />
      </el-form-item>
      <!-- 顺序 -->
      <el-form-item
        :label="$t('platform.hotSearch.seq')"
        prop="seq"
      >
        <el-input-number
          v-model="dataForm.seq"
          controls-position="right"
          :precision="0"
          @blur="onChangeSeq"
        />
      </el-form-item>
      <!-- 状态 0下线 1上线 -->
      <el-form-item
        :label="$t('platform.hotSearch.status')"
        prop="status"
      >
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="0">
            {{ '下线' }}
          </el-radio>
          <el-radio :label="1">
            {{ '启用' }}
          </el-radio>
        </el-radio-group>
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
          :disabled="!isCanSubmit"
          @click="dataFormSubmit()"
        >
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive } from 'vue'
import * as api from '@/api/platform/hot-search'

const emit = defineEmits(['refreshDataList'])

const Data = reactive({
  isCanSubmit: true,
  visible: false,
  dataForm: {
    hotSearchId: 0,
    content: null,
    seq: 0,
    status: 1,
    title: null
  },
  rules: {
    title: [
      { required: true, message: '标题不能为空', trigger: 'blur' }
    ],
    content: [
      { required: true, message: '内容不能为空', trigger: 'blur' }
    ]
  }
})

const { isCanSubmit, visible, dataForm, rules } = toRefs(Data)

const dataFormRef = ref()
const init = (hotSearchId) => {
  Data.dataForm.hotSearchId = hotSearchId || 0
  Data.isCanSubmit = true
  Data.visible = true
  nextTick(() => {
    dataFormRef.value.resetFields()
    if (!Data.dataForm.hotSearchId) {
      return
    }
    api.get(hotSearchId).then(data => {
      Data.dataForm = data
    })
  })
}

const onChangeSeq = () => {
  if (!Data.dataForm.seq) {
    Data.dataForm.seq = 0
  }
}

// 表单提交
const dataFormSubmit = () => {
  dataFormRef.value.validate(valid => {
    if (!valid) {
      return
    }
    Data.isCanSubmit = false
    const request = Data.dataForm.hotSearchId ? api.update(Data.dataForm) : api.save(Data.dataForm)
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
