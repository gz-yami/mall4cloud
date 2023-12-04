<template>
  <el-dialog
    v-model="visible"
    class="component-sys-user-account-add-or-update"
    :title="dataForm.hasAccount? $t('table.edit'): $t('table.create')"
  >
    <el-form
      ref="dataFormRef"
      :rules="rules"
      :model="dataForm"
      label-position="left"
      label-width="70px"
      style="width: 400px; margin-left:50px;"
    >
      <!-- 用户名 -->
      <el-form-item
        :label="$t('platform.shopUserAccount.username')"
        prop="username"
      >
        <el-input v-model="dataForm.username" />
      </el-form-item>
      <!-- 密码 -->
      <el-form-item
        :label="$t('platform.shopUserAccount.password')"
        prop="password"
      >
        <el-input
          v-model="dataForm.password"
          show-password
        />
      </el-form-item>
      <!-- 状态 -->
      <el-form-item
        :label="$t('platform.shopUserAccount.status')"
        prop="status"
      >
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="1">
            启用
          </el-radio>
          <el-radio :label="0">
            禁用
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
          @click="dataFormSubmit()"
        >
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import * as api from '@/api/platform/sys-user-account'
import { reactive } from 'vue'

const emit = defineEmits(['refreshDataList'])

const Data = reactive({
  visible: false,
  dataForm: {
    hasAccount: 0,
    userId: 0,
    email: null,
    phone: null,
    username: null,
    password: null,
    status: 1
  },
  rules: {
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { pattern: USER_NAME_REGEXP, message: '用户名不能为纯数字且由数字字母下划线 4-16位组成' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 1, max: 6, message: '密码由6-16位组成' }
    ]
  }
})
const { visible, dataForm, rules } = toRefs(Data)

const dataFormRef = ref()
const init = (userId, hasAccount) => {
  Data.dataForm.userId = userId
  Data.dataForm.hasAccount = hasAccount
  Data.visible = true
  nextTick(() => {
    dataFormRef.value.resetFields()
    if (!Data.dataForm.hasAccount) {
      return
    }
    api.get(userId).then(data => {
      Data.dataForm = data
      Data.dataForm.hasAccount = hasAccount
    })
  })
}

// 表单提交
const dataFormSubmit = () => {
  dataFormRef.value.validate(valid => {
    if (!valid) {
      return
    }
    const request = Data.dataForm.hasAccount ? api.update(Data.dataForm) : api.save(Data.dataForm)
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
