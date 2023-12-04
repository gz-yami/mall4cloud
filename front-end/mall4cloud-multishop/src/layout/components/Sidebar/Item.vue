<template>
  <el-icon
    v-if="icon && (icon).includes('plus')"
    class="icons"
  >
    <component
      :is="icon.slice(0, -4)"
    />
  </el-icon>
  <svg-icon
    v-else
    :icon-class="icon"
  />
  <span>{{ title }}</span>
</template>

<script setup>
import SvgIcon from '@/icons/svg-icon.vue'
defineProps({
  icon: {
    type: String,
    default: ''
  },
  title: {
    type: String,
    default: ''
  }
})

</script>
<style lang="scss" scoped>
.icons{
  margin-right: 10px;
}
</style>
