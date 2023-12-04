<!-- eslint-disable vue/no-use-v-if-with-v-for -->
<!--
 Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.

 https://www.mall4j.com/

 未经允许，不可做商业用途！

 版权所有，侵权必究！
-->
<template>
  <div class="Mall4j">
    <div class="menu-right-el">
      <el-menu
        class="el-menu-vertical-demo"
        :default-openeds="opened"
      >
        <!--        v-if="item.hidden!==true"-->
        <sub-menu-item
          v-for="(item,index) in expandMenu"
          :key="index"
          :expand-menu="item"
        />
      </el-menu>
    </div>
  </div>
</template>

<script setup>
import SubMenuItem from './main-sidebar-sub-menu-item.vue'
defineProps({
  expandMenu: {
    type: Array,
    default: () => []
  }
})
const permissionStore = usePermissionStore()
const opened = computed(() => permissionStore.menuIds)

</script>
<style lang="scss" scoped>
.menu-right-el {
  background-color: #fff;
  width: 130px !important;
  height: calc(100vh - 50px);
  overflow-y: auto;
  .el-menu-vertical-demo {
    border: none;
    height: 100%;
  }
}
</style>
