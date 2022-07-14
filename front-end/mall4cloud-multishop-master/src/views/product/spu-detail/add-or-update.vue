<template>
  <el-dialog :title="dataForm.spuId? $t('table.edit'): $t('table.create')" :visible.sync="visible">
    <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
      <!-- 商品详情 -->
      <el-form-item :label="$t('product.spuDetail.detail')" prop="detail">
        <el-input v-model="dataForm.detail" />
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
import * as api from '@/api/product/spu-detail'

export default {
  data() {
    return {
      visible: false,
      dataForm: {
        spuId: 0,
        detail: null
      },
      rules: {
      }
    }
  },
  methods: {
    init(spuId) {
      this.dataForm.spuId = spuId || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!this.dataForm.spuId) {
          return
        }
        api.get(spuId).then(data => {
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
        const request = this.dataForm.spuId ? api.update(this.dataForm) : api.save(this.dataForm)
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
