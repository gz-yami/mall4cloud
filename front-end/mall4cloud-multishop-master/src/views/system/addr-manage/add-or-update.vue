<template>
  <div class="addr-manage">
    <el-dialog :title="dataForm.areaId? $t('table.edit'): $t('table.create')" :close-on-click-modal="false" :destroy-on-close="true" :visible.sync="visible" width="650px" @close="closeDialog">
      <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="left" label-width="90px" style="width: 500px; margin-left:50px;">
        <el-form-item class="addr-item" prop="areaName" label="地区名称">
          <el-input v-model="dataForm.areaName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item class="addr-item" label="上级地区" prop="">
          <el-cascader
            v-model="selectedOptions"
            expand-trigger="hover"
            :options="areaList"
            :props="categoryTreeProps"
            :disabled="disableSelector"
            change-on-select
            filterable
            @change="handleChange"
          />
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
import * as api from '@/api/system/addr-manage'
import { treeDataTranslate } from '@/utils'
export default {
  data() {
    return {
      visible: false,
      disableSelector: false, // 是否禁用选择上级分类
      dataForm: {
        areaId: '',
        areaName: null,
        parentId: null,
        level: null
      },
      areaList: [],
      categoryTreeProps: {
        value: 'areaId',
        label: 'areaName'
      },
      selectedOptions: [],
      value: '',
      rules: {
        name: [
          { required: true, message: '请填写地区名称', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init(areaId, level) {
      this.visible = true
      this.dataForm.areaId = areaId || 0
      this.visible = true
      this.isSubmit = false
      this.selectedOptions = []
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.areaId) {
          api.get(areaId).then((data) => {
            this.dataForm = data
            this.selectedOptions = this.dataForm.parentId
            this.categoryTreeProps.areaId = this.dataForm.areaId
            this.categoryTreeProps.areaName = this.dataForm.areaName
          })
        }
        level === 0 ? this.disableSelector = true : this.disableSelector = false
        api.page().then((data) => {
          this.areaList = treeDataTranslate(data, 'areaId', 'parentId')
          console.log('this.areaList:', this.areaList)
        })
      })
    },

    handleChange(val) {
      this.dataForm.parentId = val[val.length - 1]
    },

    // 关闭弹窗时
    closeDialog() {
      this.dataForm = {
        areaId: '',
        areaName: null,
        parentId: null,
        level: null
      }
    },

    /**
     * 表单提交
     */
    dataFormSubmit() {
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          return
        }
        if (!this.dataForm.areaName) {
          this.$message({
            message: '地区名称不能为空！',
            type: 'warning',
            duration: 1500
          })
        }
        if (!this.dataForm.areaId) {
          if (!this.dataForm.parentId) {
            this.dataForm.parentId = 0
            this.dataForm.level = 0
          } else {
            this.dataForm.level = this.selectedOptions.length
          }
        }
        const request = this.dataForm.areaId ? api.update(this.dataForm) : api.save(this.dataForm)
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

<style lang="scss" scoped>
.addr-manage {
  .addr-item {
    .el-cascader--medium {
      width: 220px;
    }
  }
}
</style>
