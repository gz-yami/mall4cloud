<template>
  <div class="domain-set gray-box top-redius border-bottom-gray">
    <div class="title">{{ $t('platform.config.domainConfiguration') }}</div>
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
      <el-form-item :label="$t('platform.config.fileResourceServerDomain')" style="width:640px" prop="resourcesDomainName">
        <el-input
          v-model="dataForm.resourcesDomainName"
          :placeholder="$t('platform.config.canAccessPic')"
          controls-position="right"
        />
      </el-form-item>
      <el-form-item :label="$t('platform.config.paymentServiceDomain')" style="width:640px" prop="apiDomainName">
        <el-input
          v-model="dataForm.paymentServiceDomainName"
          :placeholder="$t('platform.config.paymentServiceDomainPH')"
          controls-position="right"
        />
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
      <!-- <el-button @click="visible = false">取消</el-button> -->
      <el-button @click="dataFormSubmit()">{{ $t('platform.config.save') }}</el-button>
    </span>
  </div>
</template>

<script>
import * as api from '@/api/platform/sys-config'

export default {
  data() {
    return {
      visible: false,
      dataForm: {
        accessKeyId: null,
        accessKeySecret: null,
        signName: null
      },
      searchForm: {},
      dataRule: {
        apiDomainName: [
          { required: true, message: this.$i18n.t('platform.config.paymentServiceDomainNullTips'), trigger: 'blur' }
        ],
        multishopDomainName: [
          { required: true, message: this.$i18n.t('platform.config.shopSideDomainNullTips'), trigger: 'blur' }
        ],
        platformDomainName: [
          { required: true, message: this.$i18n.t('platform.config.platformSideDomainNullTips'), trigger: 'blur' }
        ],
        resourcesDomainName: [
          { required: true, message: this.$i18n.t('platform.config.fileResourceServerDomainNullTips'), trigger: 'blur' }
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
        api.get('DOMAIN_CONFIG').then(data => {
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
        if (valid) {
          api.save({
            'paramKey': 'DOMAIN_CONFIG',
            'paramValue': JSON.stringify(this.dataForm),
            'remark': this.$t('platform.config.domainConfig') }).then(data => {
            if (data) {
              this.init()
            }
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
