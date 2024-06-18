<template>
  <div class="component-shop-manage-account-manage">
    <el-dialog
      v-model="visible"
      title="账号管理"
      :close-on-click-modal="false"
      :destroy-on-close="true"
      top="10vh"
      width="650px"
      @close="close"
    >
      <el-form
        ref="dataFormRef"
        :rules="rules"
        :model="dataForm"
        label-position="left"
        label-width="90px"
      >
        <!-- 店铺名称 -->
        <el-form-item
          label="店铺名称："
          label-width="110px"
        >
          <el-input
            v-model="shopName"
            placeholder="请输入店铺名称"
            disabled
          />
        </el-form-item>
        <!-- 店铺账号 -->
        <el-form-item
          label="店铺账号："
          label-width="110px"
        >
          <el-input
            v-model="username"
            placeholder="请输入店铺账号"
            disabled
          />
        </el-form-item>
        <!-- 登录密码 -->
        <el-form-item
          label="登录密码："
          label-width="110px"
          prop="password"
        >
          <el-input
            v-model="dataForm.password"
            placeholder="请输入登录密码"
          />
        </el-form-item>
        <!-- 确认密码 -->
        <el-form-item
          label="确认密码："
          label-width="110px"
          prop="confirmPsw"
        >
          <el-input
            v-model="dataForm.confirmPsw"
            placeholder="请再次输入登录密码"
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
    </el-dialog>
  </div>
</template>

<script setup>
import * as api from '@/api/platform/shop'
import { reactive } from 'vue'

const Data = reactive({
  visible: false,
  // 查询的参数
  pageQuery: {
    shopId: null
  },
  shopName: '',
  username: '',
  dataForm: {
    shopId: null,
    password: '',
    confirmPsw: ''
  },
  rules: {
    password: [
      { required: true, message: '请输入登录密码', trigger: 'blur' }
    ],
    confirmPsw: [
      { required: true, message: '请再次输入登录密码', trigger: 'blur' }
    ]
  }
})
const { visible, shopName, username, dataForm, rules } = toRefs(Data)

const dataFormRef = ref()
const init = (shopId) => {
  Data.visible = true
  Data.dataForm.shopId = shopId || null
  nextTick(() => {
    dataFormRef.value.resetFields()
    if (!shopId) {
      return
    }
    api.getManageDetail(shopId).then(data => {
      Data.shopName = data.shopName
      Data.username = data.username
    })
  })
}

/**
 * 提交账号管理
 */
const dataFormSubmit = () => {
  dataFormRef.value.validate((valid) => {
    if (valid) {
      api.savaAccountAndPassword(Data.dataForm).then(() => {
        ElMessage({
          message: $t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => {
            Data.visible = false
          }
        })
      })
    } else {
      ElMessage.error('请输入密码')
    }
  })
}

// 关闭
const close = () => {
  Data.dataForm = {
    shopId: null,
    password: '',
    confirmPsw: ''
  }
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>

</style>
