<!--
 Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.

 https://www.mall4j.com/

 未经允许，不可做商业用途！

 版权所有，侵权必究！
-->
<template>
  <div
    :class="classObj"
    class="Mall4j app-wrapper"
  >
    <div :class="{'fixed-header':fixedHeader}">
      <Navbar v-if="isDecorate" />
    </div>
    <Sidebar
      v-if="isDecorate"
      :class="{'sidebar-container':isDecorate}"
    />
    <div
      :class="{'main-container':isDecorate}"
    >
      <AppMain />
    </div>
  </div>
</template>

<script setup>
import AppMain from './components/AppMain.vue'
import Navbar from './components/Navbar.vue'
import Sidebar from './components/sidebar/index.vue'
const appStore = useAppStore()
const fixedHeader = ref(true)
const classObj = computed(() => {
  return {
    withoutAnimation: appStore.sidebar.withoutAnimation,
    hideSidebar: !appStore.sidebar.opened
  }
})
const router = useRouter()
const isDecorate = computed(() => {
  return !(/^\/(platform-decorate\/decorate-create-edit|platform-feature\/feature-create-edit)/.test(router.currentRoute.value.path))
})
</script>

<style lang="scss" scoped>
@import "@/styles/mixin.scss";
@import "@/styles/variables.scss";

.app-wrapper {
  //@include clearfix;
  position: relative;
  width: 100%;
  height: 100%;
  background: #f9f9f9;

  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}

.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}

.mobile .fixed-header {
  z-index: 999;
  width: 100%;
}
</style>
