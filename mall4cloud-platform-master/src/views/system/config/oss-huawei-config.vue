<template>
  <div class="uploadfile-Obs文件上传配置-set gray-box top-redius border-bottom-gray">
    <div class="title">{{ $t('platform.config.hwObsFileUpload') }}</div>
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
      <el-form-item label="obs—accessKeyId:" style="width:640px" prop="accessKeyId">
        <el-input
          v-model="dataForm.accessKeyId"
          :placeholder="`obs ${$t('platform.config.fileUpload')} accessKeyId`"
          controls-position="right"
        />
      </el-form-item>
      <el-form-item label="obs—secretAccessKey:" style="width:640px" prop="accessKeySecret">
        <el-input
          v-model="dataForm.secretAccessKey"
          :placeholder="`obs ${$t('platform.config.fileUpload')} secretAccessKey`"
          controls-position="right"
        />
      </el-form-item>
      <el-form-item label="obs—endpoint:" style="width:640px" prop="endpoint">
        <el-input
          v-model="dataForm.endpoint"
          :placeholder="`obs ${$t('platform.config.fileUpload')} endpoint`"
          controls-position="right"
        />
      </el-form-item>
      <el-form-item label="obs—bucketName:" style="width:640px" prop="bucketName">
        <el-input
          v-model="dataForm.bucketName"
          :placeholder="`obs ${$t('platform.config.fileUpload')} bucketName`"
          controls-position="right"
        />
      </el-form-item>
      <el-form-item :label="$t('platform.config.isActive')" style="width:640px" prop="isOpen">
        <el-switch v-model="dataForm.isOpen" active-color="#13ce66" inactive-color="#ff4949" />
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
        accessKeyId: '',
        secretAccessKey: '',
        endpoint: '',
        bucketName: '',
        isOpen: false
      },
      dataRule: {
        accessKeyId: [
          { required: true, message: `'obs ${this.$i18n.t('platform.config.fileUpload')} accessKeyId ${this.$i18n.t('platform.config.nullTips')}'`, trigger: 'blur' }
        ],
        secretAccessKey: [
          { required: true, message: `'obs ${this.$i18n.t('platform.config.fileUpload')} secretAccessKey ${this.$i18n.t('platform.config.nullTips')}'`, trigger: 'blur' }
        ],
        endpoint: [
          { required: true, message: `'obs ${this.$i18n.t('platform.config.fileUpload')} endpoint ${this.$i18n.t('platform.config.nullTips')}'`, trigger: 'blur' }
        ],
        bucketName: [
          { required: true, message: `'obs ${this.$i18n.t('platform.config.fileUpload')} bucketName ${this.$i18n.t('platform.config.nullTips')}'`, trigger: 'blur' }
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
        api.get('HUAWEI_OBS_CONFIG').then(data => {
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
            'paramKey': 'HUAWEI_OBS_CONFIG',
            'paramValue': JSON.stringify(this.dataForm),
            'remark': this.$i18n.t('platform.config.hwObsFileUpload') }).then(data => {
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
    /**
     * dataform -> oss
     */
    // getOssParam () {
    //   this.oss.accessKeyId = this.dataForm.accessKeyId
    //   this.oss.accessKeySecret = this.dataForm.accessKeySecret
    //   this.oss.endpoint = this.dataForm.endpoint
    //   this.oss.bucketName = this.dataForm.bucketName
    // },
    // /**
    //  *  oss -> dataform
    //  */
    // getDataParam (data) {
    //   this.dataForm.id = data.id
    //   this.dataForm.paramKey = data.paramKey
    //   this.dataForm.paramValue = data.paramValue
    //   this.dataForm.accessKeyId = data.oss.accessKeyId
    //   this.dataForm.accessKeySecret = data.oss.accessKeySecret
    //   this.dataForm.endpoint = data.oss.endpoint
    //   this.dataForm.bucketName = data.oss.bucketName
    // }
  }
}
</script>
