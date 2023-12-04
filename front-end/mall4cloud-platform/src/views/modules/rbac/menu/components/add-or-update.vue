<template>
  <el-dialog
    v-model="visible"
    class="component-menu-add-or-update"
    :title="dataForm.menuId? $t('table.edit'): $t('table.create')"
  >
    <!-- 目录和菜单框 -->
    <el-form
      ref="dataFormRef"
      :model="dataForm"
      :rules="dataRule"
      label-width="80px"
      @keyup.enter="dataFormSubmit()"
    >
      <!-- 类型选择 0：目录 1：菜单 -->
      <el-form-item
        label="类型"
        prop="type"
      >
        <el-radio-group v-model="dataForm.type">
          <el-radio
            v-for="(type, index) in dataForm.typeList"
            :key="index"
            :label="index"
          >
            {{ type }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 目录/菜单标题 -->
      <el-form-item
        :label="dataForm.typeList[dataForm.type] + '标题'"
        prop="title"
      >
        <el-input
          v-model="dataForm.title"
          :placeholder="dataForm.typeList[dataForm.type] + '标题'"
        />
      </el-form-item>
      <!-- 目录/菜单名称 -->
      <el-form-item
        :label="dataForm.typeList[dataForm.type] + '名称'"
        prop="name"
      >
        <el-input
          v-model="dataForm.name"
          :placeholder="dataForm.typeList[dataForm.type] + '名称'"
        />
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
      <el-form-item
        label="菜单路由"
        prop="url"
      >
        <el-input
          v-model="dataForm.url"
          placeholder="菜单路由"
        />
      </el-form-item>
      <!-- 组件路径 -->
      <el-form-item
        v-if="dataForm.type === 1"
        label="组件路径"
        prop="componentUrl"
      >
        <el-input
          v-model="dataForm.componentUrl"
          placeholder="组件路径"
        />
      </el-form-item>
      <!-- 菜单隐藏 -->
      <el-form-item
        v-if="dataForm.type === 1"
        label="菜单隐藏"
        prop="isMenuHide"
      >
        <el-select
          v-model="dataForm.isMenuHide"
          placeholder="请选择"
        >
          <el-option
            v-for="item in menuHideOpts"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <!-- 排序号 -->
      <el-form-item
        v-if="dataForm.type !== 2"
        label="排序号"
        prop="seq"
      >
        <el-input-number
          v-model="dataForm.seq"
          controls-position="right"
          :min="0"
        />
      </el-form-item>
      <!-- 图标 -->
      <el-form-item
        label="icon"
        prop="icon"
      >
        <el-popover
          ref="iconListPopoverRef"
          placement="top"
          trigger="click"
          :width="500"
          popper-class="mod-menu-home__icon-popover"
        >
          <template #reference>
            <el-input
              v-model="dataForm.icon"
              :readonly="true"
              maxlength="32"
            />
          </template>
          <icons-select @select-icon="selectIcon" />
        </el-popover>
      </el-form-item>
    </el-form>
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
import * as api from '@/api/rbac/menu'
import { reactive, watch } from 'vue'

const emit = defineEmits(['refreshDataList'])

const Data = reactive({
  visible: false,
  dataForm: {
    menuId: 0,
    type: 1,
    typeList: ['目录', '菜单'],
    title: '',
    name: '',
    parentId: 0,
    url: '',
    componentUrl: '',
    isMenuHide: '',
    seq: 0,
    icon: '',
    iconList: []
  },

  menuHideOpts: [{
    value: 0,
    label: '否'
  }, {
    value: 1,
    label: '是'
  }],
  menuList: [],
  selectedMenu: [],
  menuListTreeProps: {
    value: 'id',
    label: 'title',
    checkStrictly: true

  },
  iconPopoverVisible: []
})
const { visible, dataForm, menuHideOpts, menuList, selectedMenu, menuListTreeProps } = toRefs(Data)

// 必填输入框验证 validateName：验证名称/validateUrl：验证菜单/validateComponent：验证组件路径
const validateTitle = (rule, value, callback) => {
  if (!value) {
    callback(new Error('菜单标题不能为空'))
  } else {
    callback()
  }
}
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
  if (Data.dataForm.type === 1 && !/\S/.test(value)) {
    callback(new Error('组件路径不能为空'))
  } else {
    callback()
  }
}
const validateMenuHide = (rule, value, callback) => {
  if (Data.dataForm.isMenuHide === '') {
    callback(new Error('菜单是否隐藏不能为空'))
  } else {
    callback()
  }
}

const dataRule = reactive({
  title: [
    { required: true, validator: validateTitle, trigger: 'blur' }
  ],
  name: [
    { required: true, validator: validateName, trigger: 'blur' }
  ],
  url: [
    { required: true, validator: validateUrl, trigger: 'blur' }
  ],
  componentUrl: [
    { required: true, validator: validateComponent, trigger: 'blur' }
  ],
  isMenuHide: [
    { required: true, validator: validateMenuHide, trigger: 'blur' }
  ]
})

const dataFormRef = ref()
watch(() => Data.visible, (val) => {
  if (val === false) {
    dataFormRef.value.resetFields()
    initFrameCon()
  }
})

const init = (menuId) => {
  Data.dataForm.menuId = menuId || 0
  Data.visible = true
  nextTick(() => {
    api.menuList({ ...Data.searchParam }).then(data => {
      data.forEach(item => {
        item.title = item.meta.title
      })
      Data.menuList = treeDataTranslate(data)
      Data.pageLoading = false
    })
    if (Data.dataForm.menuId) {
      api.get(menuId).then(data => {
        // 不是目录，才显示组件路径（data.component返回Layout就为目录）
        if (data.component !== 'Layout') {
          Data.dataForm.type = 1
          Data.dataForm.componentUrl = data.component
        } else {
          Data.dataForm.type = 0
          Data.dataForm.componentUrl = ''
        }
        Data.dataForm.menuId = data.menuId
        Data.dataForm.title = data.title
        Data.dataForm.name = data.name
        Data.dataForm.parentId = data.parentId
        Data.dataForm.url = data.path
        Data.dataForm.isMenuHide = data.hidden
        Data.dataForm.seq = data.seq
        Data.dataForm.icon = data.icon
        // idList方法返回的选中菜单数组格式(内容:[{id: value, name: value}])
        // el-cascader回显的数组只需一个id的数字数组，因此需要把id取出来
        const selectedMenuList = idList(Data.menuList, data.parentId).reverse()
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

// 弹框内容初始化
const initFrameCon = () => {
  Data.dataForm = {
    menuId: 0,
    type: 1,
    typeList: ['目录', '菜单'],
    title: '',
    name: '',
    parentId: 0,
    url: '',
    componentUrl: '',
    isMenuHide: '',
    seq: 0,
    icon: '',
    iconList: []
  }
}

// 监听上级菜单的改动
const handleSelectMenuChange = (val) => {
  Data.dataForm.parentId = val[val.length - 1]
  if (Data.dataForm.parentId === undefined) {
    Data.dataForm.parentId = 0
  }
}

// 选择图标
const selectIcon = (iconName) => {
  Data.dataForm.icon = iconName
}

// 表单提交
const dataFormSubmit = () => {
  dataFormRef.value.validate(valid => {
    if (valid) {
      // 提交为目录，component为Layout
      let component = ''
      if (parseInt(Data.dataForm.type) === 0) {
        component = 'Layout'
      } else {
        component = Data.dataForm.componentUrl
      }
      // 把菜单是否隐藏的值，从0/1改为false/true
      let isMenuHide = ''
      if (parseInt(Data.dataForm.type) === 0) {
        isMenuHide = 0
      } else {
        if (parseInt(Data.dataForm.isMenuHide) === 1) {
          isMenuHide = 1
        } else {
          isMenuHide = 0
        }
      }
      const params = {
        menuId: Data.dataForm.menuId || undefined,
        title: Data.dataForm.title,
        name: Data.dataForm.name,
        parentId: Data.dataForm.parentId,
        component,
        path: Data.dataForm.url,
        hidden: isMenuHide,
        seq: Data.dataForm.seq,
        icon: Data.dataForm.icon
      }
      const request = Data.dataForm.menuId ? api.update(params) : api.save(params)
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
