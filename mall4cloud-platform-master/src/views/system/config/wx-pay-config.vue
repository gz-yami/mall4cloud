<template>
  <div class="pay-wx-set gray-box top-redius border-bottom-gray">
    <div class="title">{{ $t('platform.config.wechatPaymentConfiguration') }}</div>
    <el-form
      ref="dataForm"
      label-width="180px"
      size="mini"
      class="set-form"
      label-position="left"
      :rules="dataRule"
      :model="dataForm"
      @keyup.enter.native="dataFormSubmit()"
    >
      <el-form-item :label="`${$t('platform.config.wechatPay')} mchId:`" style="width:640px" prop="mchId">
        <el-input v-model="dataForm.mchId" :placeholder="`${$t('platform.config.wechatPay')} mchId`" controls-position="right" />
      </el-form-item>
      <el-form-item :label="`${$t('platform.config.wechatPay')} mchKey:`" style="width:640px" prop="mchKey">
        <el-input v-model="dataForm.mchKey" :placeholder="`${$t('platform.config.wechatPay')} mchKey`" controls-position="right" />
      </el-form-item>
      <el-form-item :label="`${$t('platform.config.paymentCertificatePath')}:`" style="width:640px" prop="keyPath">
        <el-input v-model="dataForm.keyPath" :placeholder="`${$t('platform.config.paymentCertificatePath')}`" controls-position="right" />
      </el-form-item>
      <el-button @click="dataFormSubmit()">{{ $t('platform.config.save') }}</el-button>
    </el-form>
    <!-- <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
    </span>-->
  </div>
</template>

<script>
import * as api from '@/api/platform/sys-config'
export default {
  data() {
    return {
      dataForm: {
        // id: null,
        // paramKey: 'WXPAY_CONFIG',
        mchId: null,
        mchKey: null,
        keyPath: null,
        signType: null
        // paramValue: null
      },
      value: [],
      dataRule: {
        mchId: [
          { required: true, message: `${this.$i18n.t('platform.config.wechatPay')} mchId ${this.$i18n.t('platform.config.nullTips')}`, trigger: 'blur' }
        ],
        mchKey: [
          { required: true, message: `${this.$i18n.t('platform.config.wechatPay')} mchKey ${this.$i18n.t('platform.config.nullTips')}`, trigger: 'blur' }
        ],
        keyPath: [
          { required: true, message: `${this.$i18n.t('platform.config.paymentCertificatePath')} ${this.$i18n.t('platform.config.nullTips')}`, trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      // this.$refs['dataForm'].resetFields()
      this.$nextTick(() => {
        api.get('WXPAY_CONFIG').then(data => {
          if (data) {
            this.dataForm = JSON.parse(data)
          }
        })
        this.$refs['dataForm'].resetFields()
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        // this.dataForm.signType = JSON.stringify(this.value)
        if (valid) {
          api.save({
            'paramKey': 'WXPAY_CONFIG',
            'paramValue': JSON.stringify(this.dataForm),
            'remark': this.$i18n.t('platform.config.paymentCertificatePath') }).then(data => {
            this.$message({
              message: this.$i18n.t('publics.operation'),
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.visible = false
                this.$emit('refreshDataList')
              }
            })
          })
        }
      })
    }
  }
}
</script>

