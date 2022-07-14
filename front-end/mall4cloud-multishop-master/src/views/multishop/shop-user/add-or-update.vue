<template>
  <el-dialog :title="dataForm.shopUserId? $t('table.edit'): $t('table.create')" :visible.sync="visible">
    <el-form ref="dataForm" :rules="dataRule" :model="dataForm" label-position="right" label-width="80px">
      <!-- 昵称 -->
      <el-form-item :label="$t('multishop.shopUser.nickName')" prop="nickName">
        <el-input v-model="dataForm.nickName" />
      </el-form-item>
      <!-- 头像 -->
      <el-form-item :label="$t('multishop.shopUser.avatar')" prop="avatar" label-width="80px">
        <img-upload v-model="dataForm.avatar" />
        <!-- <pic-upload v-model="dataForm.avatar" /> -->
      </el-form-item>
      <!-- 员工编号 -->
      <el-form-item :label="$t('multishop.shopUser.code')" prop="code">
        <el-input v-model="dataForm.code" />
      </el-form-item>
      <!-- 联系方式 -->
      <el-form-item :label="$t('multishop.shopUser.phoneNum')" prop="phoneNum">
        <el-input v-model="dataForm.phoneNum" />
      </el-form-item>
      <!-- 选择角色 -->
      <el-form-item label="选择角色" prop="roleIds">
        <el-select v-model="dataForm.roleIds" multiple placeholder="请选择" style="width: 60%">
          <el-option
            v-for="item in roleOpts"
            :key="item.roleId"
            :label="item.roleName"
            :value="item.roleId"
          />
        </el-select>
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
import * as api from '@/api/multishop/shop-user'
// import PicUpload from '@/components/PicUpload'
import * as roleApi from '@/api/rbac/role'
import ImgUpload from '@/components/ImgUpload'

export default {
  components: {
    // PicUpload,
    ImgUpload
  },
  data() {
    return {
      visible: false,
      dataForm: {
        shopUserId: 0,
        shopId: null,
        nickName: null,
        avatar: null,
        code: null,
        phoneNum: null,
        hasAccount: null,
        roleIds: []
      },
      roleOpts: [],
      dataRule: {
        nickName: [
          { required: true, message: '昵称不能为空', trigger: 'blur' }
        ],
        avatar: [
          { required: true, message: '头像不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init(shopUserId) {
      this.dataForm.shopUserId = shopUserId || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        roleApi.list().then(data => {
          this.roleOpts = data
        })
        if (this.dataForm.shopUserId) {
          api.get(shopUserId).then(data => {
            this.dataForm = data
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          const request = this.dataForm.shopUserId ? api.update(this.dataForm) : api.save(this.dataForm)
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
        }
      })
    }
  }
}
</script>
