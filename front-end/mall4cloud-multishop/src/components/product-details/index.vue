<template>
  <div class="component-product-details">
    <el-tabs type="card">
      <el-form-item label="产品详情">
        <tiny-mce
          ref="content"
          v-model="setDataForm.detail"
          :width="850"
        />
      </el-form-item>
    </el-tabs>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const emit = defineEmits(['update:dataForm'])

const props = defineProps({
  dataForm: {
    type: Object,
    default () {
      return {}
    }
  }
})

const setDataForm = computed({
  get () {
    return props.dataForm
  },
  set (val) {
    return emit('update:dataForm', val)
  }
})
</script>

<style lang="scss" scoped>
</style>
