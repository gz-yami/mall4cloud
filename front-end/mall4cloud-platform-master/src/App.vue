<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style lang="scss">
#app {
  .el-input--mini .el-input__inner {
    height: 31px !important;
    line-height: 31px !important;
  }
}
</style>
