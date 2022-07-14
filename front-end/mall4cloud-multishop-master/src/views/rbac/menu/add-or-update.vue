<template>
  <el-dialog :title="dataForm.menuId? $t('table.edit'): $t('table.create')" :visible.sync="visible">
    <!-- 目录和菜单框 -->
    <el-form
      ref="dataForm"
      :model="dataForm"
      :rules="dataRule"
      label-width="80px"
      @keyup.enter.native="dataFormSubmit()"
    >
      <!-- 类型选择 0：目录 1：菜单 -->
      <el-form-item label="类型" prop="type">
        <el-radio-group v-model="dataForm.type">
          <el-radio
            v-for="(type, index) in dataForm.typeList"
            :key="index"
            :label="index"
          >{{ type }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 目录/菜单名称 -->
      <el-form-item :label="dataForm.typeList[dataForm.type] + '名称'" prop="name">
        <el-input v-model="dataForm.name" :placeholder="dataForm.typeList[dataForm.type] + '名称'" />
      </el-form-item>
      <!-- 上级菜单 -->
      <el-form-item label="上级菜单">
        <el-cascader
          v-model="selectedMenu"
          clearable
          expand-trigger="hover"
          :options="menuList"
          :props="menuListTreeProps"
          change-on-select
          @change="handleSelectMenuChange"
        />
      </el-form-item>
      <!-- 菜单路由 -->
      <el-form-item label="菜单路由" prop="url">
        <el-input v-model="dataForm.url" placeholder="菜单路由" />
      </el-form-item>
      <!-- 组件路径 -->
      <el-form-item v-if="dataForm.type === 1" label="组件路径" prop="componentUrl">
        <el-input v-model="dataForm.componentUrl" placeholder="组件路径" />
      </el-form-item>
      <!-- 排序号 -->
      <el-form-item v-if="dataForm.type !== 2" label="排序号" prop="seq">
        <el-input-number v-model="dataForm.seq" controls-position="right" :min="0" />
      </el-form-item>
      <!-- 图标 -->
      <el-form-item label="icon" prop="icon">
        <el-input v-model="dataForm.icon" v-popover:iconListPopover :readonly="true" />
        <el-popover
          ref="iconListPopover"
          placement="bottom"
          trigger="click"
          popper-class="mod-menu-home__icon-popover"
        >
          <icons-select @selectIcon="selectIcon" />
        </el-popover>
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
import * as api from '@/api/rbac/menu'
import { treeDataTranslate, idList } from '@/utils'
import IconsSelect from '@/components/IconsSelect'

export default {
  components: { IconsSelect },
  data() {
    // 必填输入框验证 validateName：验证名称/validateUrl：验证菜单/validateComponent：验证组件路径
    const validateName = (rule, value, callback) => {
      if (!value) {
        callback(new Error('菜单名称不能为空'))
      } else {
        callback()
      }
    }
    const validateUrl = (rule, value, callback) => {
      if (!/\S/.test(value)) {
        callback(new Error('菜单URL不能为空'))
      } else {
        callback()
      }
    }
    const validateComponent = (rule, value, callback) => {
      if (this.dataForm.type === 1 && !/\S/.test(value)) {
        callback(new Error('组件路径不能为空'))
      } else {
        callback()
      }
    }
    return {
      visible: false,
      dataForm: {
        menuId: 0,
        type: 1,
        typeList: ['目录', '菜单'],
        name: '',
        parentId: 0,
        url: '',
        componentUrl: '',
        seq: 0,
        icon: '',
        iconList: []
      },
      dataRule: {
        name: [
          { required: true, validator: validateName, trigger: 'blur' }
        ],
        url: [
          { required: true, validator: validateUrl, trigger: 'blur' }
        ],
        componentUrl: [
          { required: true, validator: validateComponent, trigger: 'blur' }
        ]
      },
      menuList: [],
      selectedMenu: [],
      menuListTreeProps: {
        value: 'id',
        label: 'name'
      },
      iconPopoverVisible: []
    }
  },
  watch: {
    // 如果弹窗关闭，dataForm里的值初始化
    visible(val) {
      if (val === false) {
        this.initFrameCon()
      }
    }
  },
  methods: {
    init(menuId) {
      this.dataForm.menuId = menuId || 0
      this.visible = true
      this.$nextTick(() => {
        // this.$refs['dataForm'].resetFields()
        api.menuList({ ...this.searchParam }).then(data => {
          this.menuList = treeDataTranslate(data)
          this.pageLoading = false
        })
        if (this.dataForm.menuId) {
          api.get(menuId).then(data => {
            // 不是目录，才显示组件路径（data.component返回Layout就为目录）
            if (data.component !== 'Layout') {
              this.dataForm.type = 1
              this.dataForm.componentUrl = data.component
            } else {
              this.dataForm.type = 0
              this.dataForm.componentUrl = ''
            }
            this.dataForm.menuId = data.menuId
            this.dataForm.name = data.title
            this.dataForm.parentId = data.parentId
            this.dataForm.url = data.path
            this.dataForm.seq = data.seq
            this.dataForm.icon = data.icon
            this.selectedMenu = idList(this.menuList, data.parentId).reverse()
          })
        } else {
          this.selectedMenu = []
        }
      })
    },
    // 弹框内容初始化
    initFrameCon() {
      this.dataForm = {
        menuId: 0,
        type: 1,
        typeList: ['目录', '菜单'],
        name: '',
        parentId: 0,
        url: '',
        componentUrl: '',
        seq: 0,
        icon: '',
        iconList: []
      }
    },
    // 监听上级菜单的改动
    handleSelectMenuChange(val) {
      this.dataForm.parentId = val[val.length - 1]
      if (this.dataForm.parentId === undefined) {
        this.dataForm.parentId = 0
      }
    },
    // 选择图标
    selectIcon(iconName) {
      this.dataForm.icon = iconName
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          // 提交为目录，component为Layout
          let component = ''
          if (parseInt(this.dataForm.type) === 0) {
            component = 'Layout'
          } else {
            component = this.dataForm.componentUrl
          }
          const params = {
            'menuId': this.dataForm.menuId || undefined,
            'name': this.dataForm.name,
            'title': this.dataForm.name,
            'parentId': this.dataForm.parentId,
            'component': component,
            'path': this.dataForm.url,
            'seq': this.dataForm.seq,
            'icon': this.dataForm.icon
          }
          const request = this.dataForm.menuId ? api.update(params) : api.save(params)
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

<style lang="scss">
.mod-menu-home{
  &__icon-popover {
    max-width: 420px;
    max-height: 280px;
  }
}
</style>
