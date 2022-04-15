<template>
  <div class="shop-transcity-add-or-update">
    <el-dialog :modal="false" :title="this.$i18n.t('delivery.transport.chooseDeliveryArea')" :close-on-click-modal="false" :visible.sync="visible">
      <el-form
        ref="dataForm"
        :model="dataForm"
        label-width="80px"
        style="height:400px"
        @keyup.enter.native="dataFormSubmit()"
      >
        <el-scrollbar style="height:100%">
          <el-form-item size="mini" :label="$t('delivery.transport.deliveryArea')">
            <el-tree
              v-if="visible"
              ref="menuListTree"
              :data="menuList"
              :props="menuListTreeProps"
              node-key="areaId"
              show-checkbox
            />
          </el-form-item>
        </el-scrollbar>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">{{ $t("table.cancel") }}</el-button>
        <el-button type="primary" @click="dataFormSubmit()">{{ $t("table.confirm") }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { treeDataTranslate } from '@/utils'
import * as api from '@/api/delivery/transport'
export default {
  data() {
    return {
      type: 0,
      visible: false,
      menuList: [],
      rowIndex: 0,
      menuListTreeProps: {
        label: 'areaName',
        children: 'children'
      },
      dataForm: {
        transfeeId: 0
      }
    }
  },
  methods: {
    init(rowIndex, cityList, allSelectCityList, type, banArr) {
      this.type = type
      this.rowIndex = rowIndex
      if (this.menuList.length === 0) {
        // 获取所有省和市
        // this.$http({
        //   url: this.$http.adornUrl('/admin/area/list'),
        //   method: 'get',
        //   params: this.$http.adornParams()
        // }).then(({ data }) => {
        //   this.menuList = treeDataTranslate(data, 'areaId', 'parentId')
        // }).then(() => {
        //   this.visible = true
        //   this.disabledNodes(cityList, allSelectCityList, banArr)
        //   this.$nextTick(() => {
        //     this.$refs['dataForm'].resetFields()
        //     this.$refs.menuListTree.setCheckedNodes(cityList)
        //   })
        // })
        api.listArea({}).then(data => {
          this.menuList = treeDataTranslate(data, 'areaId', 'parentId')
        }).then(() => {
          this.visible = true
          this.disabledNodes(cityList, allSelectCityList, banArr)
          this.$nextTick(() => {
            this.$refs['dataForm'].resetFields()
            this.$refs.menuListTree.setCheckedNodes(cityList)
          })
        })
      } else {
        this.visible = true
        this.disabledNodes(cityList, allSelectCityList, banArr)
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$refs.menuListTree.setCheckedNodes(cityList)
        })
      }
    },
    disabledNodes(cityList, allSelectCityList, banArr) {
      let newBanArr = []
      newBanArr = newBanArr.concat(banArr)
      if (newBanArr.length > 0) {
        for (var i = 0; i < newBanArr.length; i++) {
          if (cityList.length > 0) {
            cityList.forEach(item => {
              if (newBanArr[i] === item.areaId) {
                newBanArr.splice(i, 1)
              }
            })
          }
        }
      }
      for (let i = 0; i < this.menuList.length; i++) {
        const childrens = this.menuList[i].children
        this.menuList[i].disabled = false
        let disabledNum = 0
        for (let j = 0; j < childrens.length; j++) {
          // const city = childrens[j]
          this.menuList[i].children[j].disabled = false
          //
          // let allHasCity = allSelectCityList.findIndex((item) => city.areaId === item.areaId) > -1
          // let listHasCity = cityList.findIndex((item) => city.areaId === item.areaId) > -1
          // if (allHasCity && !listHasCity) {
          //   this.menuList[i].children[j].disabled = true
          //   disabledNum++
          // }
          //
          if (this.menuList[i].children[j].children) {
            if (this.menuList[i].children[j].children.length > 0) {
              let disableChildNum = 0
              const childCity = this.menuList[i].children[j].children
              for (let k = 0; k < childCity.length; k++) {
                this.menuList[i].children[j].children[k].disabled = false
                const banCity = newBanArr.findIndex((item) => item === childCity[k].areaId) > -1
                if (banCity) {
                  this.menuList[i].children[j].children[k].disabled = true
                  disableChildNum++
                }
              }
              if (disableChildNum === this.menuList[i].children[j].children.length) {
                this.menuList[i].children[j].disabled = true
                disabledNum++
              }
            }
          }
        }
        if (disabledNum === this.menuList[i].children.length) {
          this.menuList[i].disabled = true
        }
      }
    },
    // 表单提交
    dataFormSubmit() {
      this.$emit('refreshDataList', this.rowIndex, this.$refs.menuListTree.getCheckedNodes(true), this.type)
      this.visible = false
    }
  }
}
</script>

<style  lang="scss">
.shop-transcity-add-or-update {
  .el-scrollbar__wrap {
    overflow-x: hidden;
  }
}
</style>
