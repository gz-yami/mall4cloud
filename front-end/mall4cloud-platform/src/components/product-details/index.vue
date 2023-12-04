<template>
  <div class="prod-details">
    <el-tabs type="card">
      <div
        v-rich="detail"
        class="det"
      />
    </el-tabs>
  </div>
</template>

<script setup>
defineProps({
  detail: {
    type: String,
    default () {
      return ''
    }
  },
  dataForm: {
    type: Object,
    default () {
      return {}
    }
  }
})

</script>

<style lang="scss" scoped>
.prod-details {
  :deep(.el-tabs--card > .el-tabs__header) {
    border-bottom: 0;
  }
  .det {
    padding: 0 30px;
  }
}
</style>
