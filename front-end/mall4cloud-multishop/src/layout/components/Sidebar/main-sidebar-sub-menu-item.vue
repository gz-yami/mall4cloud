<!-- eslint-disable vue/no-use-v-if-with-v-for -->
<!--
 Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.

 https://www.mall4j.com/

 未经允许，不可做商业用途！

 版权所有，侵权必究！
-->
<template>
  <div class="Mall4j menu-mod">
    <div v-if="expandMenu.children">
      <el-sub-menu :index="expandMenu.id + ''">
        <template #title>
          <span
            style="font-size: 14px;"
            :title="expandMenu.meta.title.length > 4 ? expandMenu.meta.title : ''"
          >{{ expandMenu.meta.title }}</span>
        </template>
        <el-menu-item-group class="menu-right-el-item">
          <template v-for="item in expandMenu.children">
            <el-menu-item
              v-if="!item.hidden"
              :key="item.id"
              :index="item.id+''"
              style="font-size: 14px !important;height: 40px !important; line-height:40px;padding-left: 30px !important;padding-right: 10px !important;"
              class="item-text menu-right-el-item is-active"
              :class="{ 'menu-active': selectRightId === item.id }"
              @click="gotoRouteHandle3(item)"
            >
              <span :title="item.meta.title.length > 4 ? item.meta.title : ''">{{ item.meta.title }}</span>
              <sub-menu-item
                v-if="item.children"
                :expand-menu="item"
              />
            </el-menu-item>
          </template>
        </el-menu-item-group>
      </el-sub-menu>
    </div>
    <div v-else>
      <el-menu-item
        v-if="!expandMenu.hidden"
        :key="expandMenu.id"
        style="font-size: 14px !important; padding-left: 15px !important"
        class="item-text menu-right-el-item is-active"
        :class="{ 'menu-active': selectRightId === expandMenu.id }"
        @click="gotoRouteHandle(expandMenu)"
      >
        <span :title="expandMenu.meta.title.length > 4 ? expandMenu.meta.title : ''">{{
          expandMenu.meta.title
        }}</span>
      </el-menu-item>
    </div>
  </div>
</template>

<script setup>
import SubMenuItem from './main-sidebar-sub-menu-item.vue'

defineProps({
  expandMenu: {
    type: Object,
    default: () => {
    }
  }
})

const permissionStore = usePermissionStore()
const secondaryMenuList = computed(() => permissionStore.secondaryMenuList)

const commStore = useCommonStore()
const selectRightId = computed({
  get: () => commStore.selectRightId,
  set: (val) => commStore.updateSelectRightId(val)
})

const pathHeader = computed({
  get: () => commStore.pathHeader,
  set: (val) => commStore.updatePathHeader(val)
})

const router = useRouter()
// 通过id与动态(菜单)路由进行匹配跳转至指定路由
const gotoRouteHandle = (menu) => {
  selectRightId.value = menu.id
  if (menu.path[0] === '/') {
    router.push(menu.path)
  } else {
    router.push(pathHeader.value + '/' + menu.path)
  }
}
// 三级路由
const gotoRouteHandle3 = (menu) => {
  selectRightId.value = menu.id
  if (menu.path[0] === '/') {
    router.push(menu.path)
  } else {
    secondaryMenuList.value.forEach(item => {
      if (item.id === menu.parentId) {
        if (item.path[0] === '/') {
          router.push(item.path + '/' + menu.path)
        } else {
          router.push(pathHeader.value + '/' + item.path + '/' + menu.path)
        }
      }
    })
  }
}
</script>
<style lang="scss" scoped>
.menu-mod {
  .menu-right-el-item {
    &.is-active {
      background-color: #ffffff;
      color: #333;
    }

    &.menu-active {
      background-color: #e7eefb;
      color: #155bd4;
    }

    :deep(.el-menu-item-group__title) {
      padding: 0;
    }
  }
}
</style>
