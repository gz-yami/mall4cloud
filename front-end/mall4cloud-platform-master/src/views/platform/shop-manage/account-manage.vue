<template>
  <div class="account-manage">
    <el-dialog
      title="账号管理"
      :close-on-click-modal="false"
      :destroy-on-close="true"
      :visible.sync="visible"
      top="0"
      width="650px"
      @close="close"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        label-position="left"
        label-width="90px"
      >
        <!-- 店铺名称 -->
        <el-form-item label="店铺名称：" label-width="110px">
          <el-input v-model="shopName" placeholder="请输入店铺名称" disabled />
        </el-form-item>
        <!-- 店铺账号 -->
        <el-form-item label="店铺账号：" label-width="110px">
          <el-input v-model="username" placeholder="请输入店铺账号" disabled />
        </el-form-item>
        <!-- 登录密码 -->
        <el-form-item label="登录密码：" label-width="110px" prop="password">
          <el-input v-model="dataForm.password" placeholder="请输入登录密码" />
        </el-form-item>
        <!-- 确认密码 -->
        <el-form-item label="确认密码：" label-width="110px" prop="confirmPsw">
          <el-input v-model="dataForm.confirmPsw" placeholder="请再次输入登录密码" />
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
  </div>
</template>

<script>
import * as api from '@/api/platform/shop'
export default {
  data() {
    return {
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
    }
  },
  methods: {
    init(shopId) {
      console.log('账号管理shopId:', shopId)
      this.visible = true
      // this.pageQuery.shopId = shopId || null
      this.dataForm.shopId = shopId || null
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!shopId) {
          console.log('this.dataForm：', this.dataForm)
          return
        }
        api.getManageDetail(shopId).then(data => {
          this.shopName = data.shopName
          this.username = data.username
        })
      })
    },

    /**
     * TODO 提交账号管理
     */
    dataFormSubmit() {
       this.$refs.dataForm.validate((valid) => {
        if (valid) {
          api.savaAccountAndPassword(this.dataForm).then((data) => {
          })
        } else {
          this.$message.error('请输入密码')
        }
      })
    },

    // 关闭
    close() {
      this.dataForm = {
        shopId: null,
        password: '',
        confirmPsw: ''
      }
    }
  }
}
</script>

<style lang="scss">
.account-manage {
  .el-dialog__wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    .el-dialog__body {
      padding: 15px 20px;
      max-height: 800px;
      overflow: auto;
      .el-form-item:last-child {
        margin-bottom: 10px;
      }
    }
  }
}

</style>
