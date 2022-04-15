<template>
  <div class="miniapp-小程序配置-set gray-box top-redius border-bottom-gray">
    <div class="title">{{ $t('platform.config.miniProgramsConfig') }}</div>
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
      <el-form-item :label="`${$t('platform.config.miniPrograms')} AppId:`" style="width:640px" prop="appId">
        <el-input v-model="dataForm.appId" :placeholder="`${$t('platform.config.miniPrograms')} AppId`" controls-position="right" />
      </el-form-item>
      <el-form-item :label="`${$t('platform.config.miniPrograms')} secret:`" style="width:640px" prop="secret">
        <el-input v-model="dataForm.secret" :placeholder="`${$t('platform.config.miniPrograms')} secret`" controls-position="right" />
      </el-form-item>
      <el-button @click="dataFormSubmit()">{{ $t('platform.config.save') }}</el-button>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <!-- <el-button @click="visible = false">取消</el-button> -->
    </span>
  </div>
</template>

<script>
import * as api from '@/api/platform/sys-config'

export default {
  data() {
    return {
      dataForm: {
        // id: null,
        // paramKey: 'MA_CONFIG',
        appId: null,
        secret: null
        // paramValue: null
      },
      dataRule: {
        appId: [
          { required: true, message: `${this.$i18n.t('platform.config.miniPrograms')} appId ${this.$i18n.t('platform.config.nullTips')}`, trigger: 'blur' }
        ],
        secret: [
          { required: true, message: `${this.$i18n.t('platform.config.miniPrograms')} secret ${this.$i18n.t('platform.config.nullTips')}`, trigger: 'blur' }
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
        api.get('MA_CONFIG').then(data => {
          if (data) {
            this.dataForm = JSON.parse(data)
          }
        })
        this.$refs['dataForm'].resetFields()
      })
    },
    // 表单提交
    dataFormSubmit() {
      const obj = {}
      obj.appId = this.dataForm.appId
      obj.secret = this.dataForm.secret

      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          api.save({
            'paramKey': 'MA_CONFIG',
            'paramValue': JSON.stringify(this.dataForm),
            'remark': this.$i18n.t('platform.config.miniProgramsConfig') }).then(data => {
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
