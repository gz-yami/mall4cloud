<template>
  <el-dialog
    v-model="visible"
    class="component-sys-user-add-or-update"
    :title="dataForm.sysUserId? $t('table.edit'): $t('table.create')"
  >
    <el-form
      ref="dataFormRef"
      :rules="dataRule"
      :model="dataForm"
      label-position="right"
      label-width="80px"
    >
      <!-- 昵称 -->
      <el-form-item
        :label="$t('platform.shopUser.nickName')"
        prop="nickName"
      >
        <el-input v-model="dataForm.nickName" />
      </el-form-item>
      <!-- 头像 -->
      <el-form-item
        :label="$t('platform.shopUser.avatar')"
        prop="avatar"
        label-width="80px"
      >
        <img-upload v-model="dataForm.avatar" />
        <!-- <pic-upload v-model="dataForm.avatar" /> -->
      </el-form-item>
      <!-- 员工编号 -->
      <el-form-item
        :label="$t('platform.shopUser.code')"
        prop="code"
      >
        <el-input v-model="dataForm.code" />
      </el-form-item>
      <!-- 联系方式 -->
      <el-form-item
        :label="$t('platform.shopUser.phoneNum')"
        prop="phoneNum"
      >
        <el-input v-model="dataForm.phoneNum" />
      </el-form-item>
      <!-- 选择角色 -->
      <el-form-item
        label="选择角色"
        prop="roleIds"
      >
        <el-select
          v-model="dataForm.roleIds"
          multiple
          placeholder="请选择"
          style="width: 60%"
        >
          <el-option
            v-for="item in roleOpts"
            :key="item.roleId"
            :label="item.roleName"
            :value="item.roleId"
          />
        </el-select>
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
import * as api from '@/api/platform/sys-user'
import * as roleApi from '@/api/rbac/role'
import { reactive } from 'vue'

const emit = defineEmits(['refreshDataList'])

const Data = reactive({
  visible: false,
  dataForm: {
    sysUserId: 0,
    shopId: null,
    nickName: null,
    avatar: null,
    code: null,
    phoneNum: null,
    hasAccount: null,
    roleIds: []
  },
  roleOpts: [],
  dataRule: {
    nickName: [
      { required: true, message: '昵称不能为空', trigger: 'blur' }
    ],
    avatar: [
      { required: true, message: '头像不能为空', trigger: 'blur' }
    ]
  }
})
const { visible, dataForm, roleOpts, dataRule } = toRefs(Data)

const dataFormRef = ref()
const init = (sysUserId) => {
  Data.dataForm.sysUserId = sysUserId || 0
  Data.visible = true
  nextTick(() => {
    dataFormRef.value.resetFields()
    roleApi.list().then(data => {
      Data.roleOpts = data
    })
    if (Data.dataForm.sysUserId) {
      api.get(sysUserId).then(data => {
        Data.dataForm = data
      })
    }
  })
}

// 表单提交
const dataFormSubmit = () => {
  dataFormRef.value.validate(valid => {
    if (valid) {
      const request = Data.dataForm.sysUserId ? api.update(Data.dataForm) : api.save(Data.dataForm)
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
    }
  })
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>

</style>
