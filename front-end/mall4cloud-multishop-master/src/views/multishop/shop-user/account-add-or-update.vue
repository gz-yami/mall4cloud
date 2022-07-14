<template>
  <el-dialog :title="dataForm.hasAccount? $t('table.edit'): $t('table.create')" :visible.sync="visible">
    <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
      <!-- 用户名 -->
      <el-form-item :label="$t('multishop.shopUserAccount.username')" prop="username">
        <el-input v-model="dataForm.username" />
      </el-form-item>
      <!-- 密码 -->
      <el-form-item :label="$t('multishop.shopUserAccount.password')" prop="password">
        <el-input v-model="dataForm.password" show-password />
      </el-form-item>
      <!-- 状态 -->
      <el-form-item :label="$t('multishop.shopUserAccount.status')" prop="status">
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
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
</template>

<script>
import * as api from '@/api/multishop/shop-user-account'
import { USER_NAME_REGEXP } from '@/utils/validate'

export default {
  data() {
    return {
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
          { pattern: /^(?!\d+$)([a-zA-Z0-9_]{4,16})$/, message: '密码不能为纯数字且由数字字母下划线 4-16位组成' }
        ]
      }
    }
  },
  methods: {
    init(userId, hasAccount) {
      this.dataForm.userId = userId
      this.dataForm.hasAccount = hasAccount
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!this.dataForm.hasAccount) {
          return
        }
        api.get(userId).then(data => {
          this.dataForm = data
          this.dataForm.hasAccount = hasAccount
        })
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          return
        }
        console.log(this.dataForm)
        const request = this.dataForm.hasAccount ? api.update(this.dataForm) : api.save(this.dataForm)
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
