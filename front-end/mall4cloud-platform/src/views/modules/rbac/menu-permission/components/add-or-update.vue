<template>
  <el-dialog
    v-model="visible"
    class="component-menu-permission-add-or-update"
    :title="dataForm.menuPermissionId? $t('table.edit'): $t('table.create')"
  >
    <div class="up-wrapper">
      <el-form
        ref="dataFormRef"
        :model="dataForm"
        :rules="dataRule"
        label-width="80px"
        @keyup.enter="dataFormSubmit()"
      >
        <!-- 资源名称 -->
        <el-form-item
          :label="$t('rbac.menuPermission.name')"
          prop="name"
        >
          <el-input v-model="dataForm.name" />
        </el-form-item>
        <!-- 上级菜单 -->
        <el-form-item
          label="上级菜单"
          prop="menuId"
        >
          <el-cascader
            v-model="selectedMenu"
            expand-trigger="hover"
            :options="menuList"
            :props="menuListTreeProps"
            change-on-select
            @change="handleSelectMenuChange"
          />
        </el-form-item>
        <!-- 请求方法 1.GET 2.POST 3.PUT 4.DELETE -->
        <el-form-item
          label="请求方法"
          prop="method"
        >
          <el-radio-group v-model="dataForm.method">
            <el-radio
              v-for="index of 4"
              :key="index"
              :label="index"
            >
              {{ ['', 'GET', 'POST', 'PUT', 'DELETE'][index] }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- 资源对应服务器路径 -->
        <el-form-item
          label="资源路径"
          prop="uri"
        >
          <el-input v-model="dataForm.uri" />
        </el-form-item>
        <!-- 权限对应的编码 -->
        <el-form-item
          label="授权标识"
          prop="permission"
        >
          <el-input v-model="dataForm.permission" />
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <div
        class="dialog-footer"
      >
        <el-button @click="visible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button
          type="primary"
          @click="dataFormSubmit()"
        >
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import * as menuApi from '@/api/rbac/menu'
import * as api from '@/api/rbac/menu-permission'
import { reactive } from 'vue'

const emit = defineEmits(['refreshDataList'])

const Data = reactive({
  visible: false,
  dataForm: {
    menuPermissionId: 0,
    menuId: null,
    permission: null,
    name: '',
    uri: '',
    method: 1
  },
  dataRule: {
    name: [
      { required: true, message: '资源名称不能为空', trigger: 'blur' }
    ],
    menuId: [
      { required: true, message: '上级菜单不能为空', trigger: 'blur' }
    ],
    method: [
      { required: true, message: '请求方法不能为空', trigger: 'blur' }
    ],
    uri: [
      { required: true, message: '资源路径不能为空', trigger: 'blur' }
    ],
    permission: [
      { required: true, message: '授权标识不能为空', trigger: 'blur' }
    ]
  },
  menuList: [],
  selectedMenu: [],
  menuListTreeProps: {
    value: 'id',
    label: 'title'
  }
})
const { visible, dataForm, dataRule, menuList, selectedMenu, menuListTreeProps } = toRefs(Data)

const dataFormRef = ref()
const init = (menuPermissionId) => {
  Data.dataForm.menuPermissionId = menuPermissionId || 0
  Data.visible = true
  nextTick(() => {
    dataFormRef.value.resetFields()
    menuApi.menuList({ ...Data.searchParam }).then(data => {
      data.forEach(item => {
        item.title = item.meta.title
      })
      Data.menuList = treeDataTranslate(data)
      Data.pageLoading = false
    })
    if (Data.dataForm.menuPermissionId) {
      api.get(menuPermissionId).then(data => {
        Data.dataForm = data
        // idList方法返回的选中菜单数组格式(内容:[{id: value, name: value}])
        // el-cascader回显的数组只需一个id的数字数组，因此需要把id取出来
        const selectedMenuList = idList(Data.menuList, data.menuId).reverse()
        const tempSelectedMenu = []
        selectedMenuList.forEach(item => {
          tempSelectedMenu.push(item.id)
        })
        Data.selectedMenu = tempSelectedMenu
      })
    } else {
      Data.selectedMenu = []
    }
  })
}

const handleSelectMenuChange = (val) => {
  Data.dataForm.menuId = val[val.length - 1]
}

// 表单提交
const dataFormSubmit = () => {
  dataFormRef.value.validate(valid => {
    if (valid) {
      const params = {
        menuPermissionId: Data.dataForm.menuPermissionId || undefined,
        menuId: Data.dataForm.menuId || undefined,
        name: Data.dataForm.name,
        permission: Data.dataForm.permission,
        uri: Data.dataForm.uri,
        method: Data.dataForm.method
      }

      const request = Data.dataForm.menuPermissionId ? api.update(params) : api.save(params)
      request.then(() => {
        ElMessage({
          message: $t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => {
            Data.visible = false
            emit('refreshDataList')
            dataFormRef.value.resetFields()
          }
        })
      })
    }
  })
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>

</style>
