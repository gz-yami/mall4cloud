<script setup>
import zh from 'element-plus/lib/locale/lang/zh-cn'
const language = zh
</script>

<template>
  <el-config-provider
    :locale="language"
    namespace="el"
  >
    <router-view />
  </el-config-provider>
</template>
