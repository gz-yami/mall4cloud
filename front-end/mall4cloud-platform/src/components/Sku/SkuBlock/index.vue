<template>
  <div class="component-sku-block">
    <sku-group
      v-for="(item, index) in data"
      :key="index"
      v-model:sku="data[item]"
      :index="index"
      :sku-tree="skuTreeData"
      :on-sku-change="rebuildSku"
      :on-sku-remove="handleSkuRemove"
    />
  </div>
</template>

<script setup>
import skuGroup from '@/components/Sku/SkuGroup/index.vue'
import { onBeforeMount, reactive } from 'vue'

const props = defineProps({
  hasSkuImg: {
    type: Boolean,
    default: false
  },
  salesAttrs: {
    type: Array,
    default () {
      return []
    }
  },
  // 可选规格列表
  skuTree: {
    type: Array,
    default () {
      return []
    }
  },
  sku: {
    type: Object,
    default () {
      return {}
    }
  },
  categoryId: {
    type: Number,
    default: null
  },
  // 自定义sku的id key
  optionValue: {
    type: String,
    default: 'id'
  },
  // 异步获取规格列表
  onFetchGroup: {
    type: Function,
    // eslint-disable-next-line vue/valid-define-props
    default: () => { }
  }
})

provide('ease', props)

const emit = defineEmits(['update:modelValue', 'on-change'])

const Data = reactive({
  data: props.salesAttrs,
  skuTreeData: props.skuTree,
  warning: false
})

const { data, skuTreeData } = toRefs(Data)

watch(() => Data.data, (salesAttrs) => {
  emit('update:modelValue', salesAttrs)
})

watch(() => props.skuTree, (skuTree) => {
  Data.skuTreeData = skuTree
})

watch(() => props.categoryId, () => {
  if (!props.categoryId) {
    Data.data.splice(0, Data.data.length)
  }
})

const rebuildSku = (sku, index) => {
  if (
    Data.data.some(
      (item, idx) => item[props.optionValue] === sku[props.optionValue] && index !== idx
    )
  ) {
    ElMessage({
      message: '规格名不能重复',
      duration: 800
    })
    return false
  }
  Data.data[index] = Object.assign({}, sku)

  if (!Data.skuTreeData.some(item => item[props.optionValue] === sku[props.optionValue])) {
    Data.skuTreeData.push(sku)
  }
  emit('on-change', Data.data)
}

const handleSkuRemove = (index) => {
  Data.data.splice(index, 1)

  emit('on-change', Data.data)
}

onBeforeMount(() => {
  if (typeof (props.onFetchGroup) == 'function') {
    props.onFetchGroup().then(skuTree => {
      Data.skuTreeData = skuTree
    })
  }
})
</script>

<style lang="scss" scoped></style>
