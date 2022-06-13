<template>
  <el-dialog :title="dataForm.hotSearchId? $t('table.edit'): $t('table.create')" :visible.sync="visible">
    <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="right" label-width="80px">
      <!-- 热搜标题 -->
      <el-form-item :label="$t('platform.hotSearch.title')" prop="title">
        <el-input v-model="dataForm.title" />
      </el-form-item>
      <!-- 内容 -->
      <el-form-item :label="$t('platform.hotSearch.content')" prop="content">
        <el-input v-model="dataForm.content" />
      </el-form-item>
      <!-- 顺序 -->
      <el-form-item :label="$t('platform.hotSearch.seq')" prop="seq">
        <el-input-number
          v-model="dataForm.seq"
          controls-position="right"
          :precision="0"
        />
      </el-form-item>
      <!-- 状态 0下线 1上线 -->
      <el-form-item :label="$t('platform.hotSearch.status')" prop="status">
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="0">{{ '下线' }}</el-radio>
          <el-radio :label="1">{{ '启用' }}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">
        {{ $t('table.cancel') }}
      </el-button>
      <el-button type="primary" :disabled="!isCanSubmit" @click="dataFormSubmit()">
        {{ $t('table.confirm') }}
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import * as api from '@/api/platform/hot-search'

export default {
  data() {
    return {
      isCanSubmit: true,
      visible: false,
      dataForm: {
        hotSearchId: 0,
        content: null,
        seq: null,
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
    }
  },
  methods: {
    init(hotSearchId) {
      this.dataForm.hotSearchId = hotSearchId || 0
      this.isCanSubmit = true
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!this.dataForm.hotSearchId) {
          return
        }
        api.get(hotSearchId).then(data => {
          this.dataForm = data
        })
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          return
        }
        this.isCanSubmit = false
        const request = this.dataForm.hotSearchId ? api.update(this.dataForm) : api.save(this.dataForm)
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
