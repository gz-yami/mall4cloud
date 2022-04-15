<template>
  <el-dialog :title="dataForm.roleId? $t('table.edit'): $t('table.create')" :visible.sync="visible">
    <el-form ref="dataForm" :rules="dataRule" :model="dataForm" label-position="right" label-width="80px">
      <!-- 角色名称 -->
      <el-form-item :label="$t('rbac.role.roleName')" prop="roleName">
        <el-input v-model="dataForm.roleName" />
      </el-form-item>
      <!-- 备注 -->
      <el-form-item :label="$t('rbac.role.remark')" prop="remark">
        <el-input v-model="dataForm.remark" />
      </el-form-item>
      <!-- 权限 -->
      <el-form-item size="mini" label="授权">
        <el-tree
          ref="menuListTree"
          :data="menuWithPermissions"
          :props="menuListTreeProps"
          node-key="menuId"
          show-checkbox
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
</template>

<script>
import * as api from '@/api/rbac/role'
import * as menuApi from '@/api/rbac/menu'
import { treeDataTranslate } from '@/utils'

export default {
  data() {
    return {
      visible: false,
      dataForm: {
        roleId: 0,
        roleName: null,
        remark: null,
        createUserId: null,
        bizType: null,
        tenantId: null,
        menuIds: [],
        menuPermissionIds: []
      },
      dataRule: {
        roleName: [
          { required: true, message: '角色名称不能为空', trigger: 'blur' }
        ]
      },
      selectedMenuWithPermissions: [],
      iniMenuWithPermissions: [],
      menuWithPermissions: [],
      menuListTreeProps: {
        value: 'menuId',
        label: 'title'
      },
      // 用于临时将权限转化为"菜单"的key
      tempPermissionToMenuKey: 5498415864,
      rules: {
      }
    }
  },
  methods: {
    init(roleId) {
      this.dataForm.roleId = roleId || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        this.$refs.menuListTree.setCheckedKeys([])
        menuApi.listWithPermissions().then(menuWithPermissions => {
          this.iniMenuWithPermissions = menuWithPermissions
          // 生成树状结构数据
          this.menuWithPermissionToTree(menuWithPermissions)
        })

        if (this.dataForm.roleId) {
          api.get(roleId).then(data => {
            this.dataForm = data

            // 回显选中的选项
            const menuPermissionIdList = []
            let lastMenuIdList = []
            const tempMenuIdList = []
            const tempHaveSubMenuIdList = []
            if (this.dataForm.menuPermissionIds.length) {
              this.dataForm.menuPermissionIds.forEach(menuPermissionId => {
                if (menuPermissionId !== this.tempPermissionToMenuKey) {
                  menuPermissionIdList.push(menuPermissionId + this.tempPermissionToMenuKey)
                }
              })
            }
            // 获取最后一级的菜单（没有子菜单也没有资源）
            if (this.dataForm.menuIds.length) {
              this.dataForm.menuIds.forEach(menuId => {
                const iniMenuWithPermissions = this.iniMenuWithPermissions
                for (const index in iniMenuWithPermissions) {
                  if ((iniMenuWithPermissions[index].menuId === menuId && iniMenuWithPermissions[index].menuPermissions.length > 0) || iniMenuWithPermissions[index].parentId === menuId) {
                    tempHaveSubMenuIdList.push(menuId)
                    break
                  }
                }
                tempMenuIdList.push(menuId)
              })
              lastMenuIdList = this.arrContrDiff(tempHaveSubMenuIdList, tempMenuIdList)
            }
            // 回显，只选最后一级的菜单及资源
            this.$refs.menuListTree.setCheckedKeys([].concat(menuPermissionIdList, lastMenuIdList))
          })
        }
      })
    },
    // 把菜单和菜单资源数据生成树状结构（参数为数据menuWithPermissions）
    menuWithPermissionToTree(menuWithPermissions) {
      const tempMenus = []
      const permissionMenuIds = []
      for (const key in menuWithPermissions) {
        const menuWithPermission = menuWithPermissions[key]
        tempMenus.push({
          menuId: menuWithPermission.menuId,
          parentId: menuWithPermission.parentId,
          title: menuWithPermission.title,
          type: 0 // 0 菜单 1按钮
        })
        if (menuWithPermission.menuPermissions.length) {
          const permissions = menuWithPermission.menuPermissions
          for (const pkey in permissions) {
            const permission = permissions[pkey]
            tempMenus.push({
              menuId: permission.menuPermissionId + this.tempPermissionToMenuKey,
              menuPermissionId: permission.menuPermissionId,
              parentId: permission.menuId,
              title: permission.name,
              type: 1 // 0 菜单 1按钮
            })
            permissionMenuIds.push(permission.menuId)
          }
        }
      }
      this.menuWithPermissions = treeDataTranslate(tempMenus, 'menuId')
    },
    // 切割最后一级的菜单
    // spliceLastMenu(menus) {
    //   for (const key in menus) {
    //     const menu = menus[key]
    //     if (menu.children && menu.children.length) {
    //       this.spliceLastMenu(menu.children)
    //       continue
    //     }
    //     if (menu.type === 0) {
    //       menus.splice(menus.indexOf(menus), 1)
    //     }
    //   }
    // },
    // 数组对比，选出两数组不同的元素
    arrContrDiff(arr1, arr2) {
      return arr1.concat(arr2).filter(function(v, i, arr) {
        return arr.indexOf(v) === arr.lastIndexOf(v)
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          const menuCheckedKeys = [].concat(this.$refs.menuListTree.getCheckedKeys(), this.$refs.menuListTree.getHalfCheckedKeys())
          const menuIds = []
          const menuPermissionIds = []
          if (menuCheckedKeys.length) {
            menuCheckedKeys.forEach(menuId => {
              if (menuId < this.tempPermissionToMenuKey) {
                menuIds.push(menuId)
              } else {
                menuPermissionIds.push(menuId - this.tempPermissionToMenuKey)
              }
            })
          }

          this.dataForm.menuIds = menuIds
          this.dataForm.menuPermissionIds = menuPermissionIds

          const request = this.dataForm.roleId ? api.update(this.dataForm) : api.save(this.dataForm)
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
