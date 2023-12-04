<!--
 Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.

 https://www.mall4j.com/

 未经允许，不可做商业用途！

 版权所有，侵权必究！
-->
<template>
  <div
    class="Mall4j container"
    :style="isDecorate || route.name === 'Dashboard' ? '' : 'margin-top: 40px;'"
  >
    <el-card
      v-if="route.meta && route.name !== 'Dashboard' && !isDecorate"
      class="main-head"
    >
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item
          v-for="(item,index) in selectMenu"
          :key="index"
          class="breadcrumb-item"
        >
          <span>{{ item }}</span>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </el-card>
    <section
      class="app-main"
      :style="{
        ...(route.name === 'Dashboard' ? style : {}),
        ...(isDecorate ? {'margin-left':'0','width':'100%'} : {})
      }"
    >
      <router-view />
    </section>
  </div>
</template>

<script setup>
const route = useRoute()
const style = reactive({
  'margin-top': '0'
})

const selectMenu = computed(() => useCommonStore().selectMenu)
const router = useRouter()
const isDecorate = computed(() => {
  return (/^\/(platform-decorate\/decorate-create-edit|platform-feature\/feature-create-edit)/.test(router.currentRoute.value.path))
})
</script>

<style lang="scss" scoped>
.container {
  box-sizing: border-box;
  flex: 1;
  width: 100%;
  .main-head {
    background: #FFFFFF;
    width: 100%;
    height: 40px;
    position: fixed;
    top: 50px;
    z-index: 10;
    display: flex;
    align-items: center;
    border-radius: 0;
    box-shadow: none;
    border-top: none;

    .breadcrumb-item:last-child span {
      color: #155BD4 !important;
    }
  }

  .app-main {
    padding: 20px;
    width: calc(100% - 40px);
    position: relative;
    background: #fff;
    overflow: hidden;
    margin-left: 20px;
  }

}
</style>
