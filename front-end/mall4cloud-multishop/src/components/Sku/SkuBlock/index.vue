<template>
  <div class="component-sku-block">
    <sku-group
      v-for="(item, index) in data"
      :key="index"
      v-model:sku="data[index]"
      :index="index"
      :sku-tree="skuTreeData"
      :on-change-has-img="onChangeHasImg"
      :on-sku-change="rebuildSku"
      :on-sku-remove="handleSkuRemove"
    />
    <!-- 添加属性 -->
    <div class="part-form-item">
      <el-button
        class="add-btn"
        @click="addSku"
      >
        <i class="el-icon-plus" />添加属性
      </el-button>
      <div
        v-if="!categoryId && warning"
        class="warning"
      >
        请先选择分类！
      </div>
      <div
        v-if="!hasSkuValue"
        class="warning"
      >
        请先选择已添加属性的属性值！
      </div>
      <div
        v-if="!hasSkuId"
        class="warning"
      >
        请先选择已添加属性！
      </div>
    </div>
  </div>
</template>

<script setup>
import skuGroup from '@/components/Sku/SkuGroup/index.vue'

import { computed, onBeforeMount, reactive, watch } from 'vue'

const emit = defineEmits(['update:modelValue', 'update:salesAttrs', 'update:skuTree', 'update:hasSkuVal', 'on-change'])

const props = defineProps({
  maxSize: {
    type: Number,
    default: 2
  },
  hasSkuImg: {
    type: Boolean,
    default: false
  },
  showAddSkuImage: {
    type: Boolean,
    default: true
  },
  salesAttrs: {
    type: Array,
    default () {
      return []
    }
  },
  saveSalesAttrs: {
    type: Array,
    default () {
      return []
    }
  },
  action: {
    type: String,
    default: ''
  },
  headers: {
    type: Object,
    default () {
      return {}
    }
  },
  accept: {
    type: String,
    default: ''
  },
  uploadName: {
    type: String,
    default: 'resource'
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
  // 自定义sku的text key
  optionText: {
    type: String,
    default: 'text'
  },
  // 异步获取规格列表
  onFetchGroup: {
    type: Function,
    default: Promise.resolve(() => {})
  },
  // 异步获取规格可选值
  onFetchSku: {
    type: Function,
    default: Promise.resolve(() => {})
  },
  // 创建新的规格名
  onCreateGroup: {
    type: Function,
    default: Promise.resolve(() => {})
  },
  // 创建新的规格值
  onCreateSku: {
    type: Function,
    default: Promise.resolve(() => {})
  },
  // 图片预览
  onPreviewImg: {
    type: Function,
    default: Promise.resolve(() => {})
  },
  hasSkuVal: {
    type: Boolean,
    default: null
  }
})

const Data = reactive({
  warning: false,
  hasSkuId: true
})
const { warning, hasSkuId } = toRefs(Data)

const data = computed({
  get () {
    return props.salesAttrs
  },
  set (val) {
    emit('update:salesAttrs', val)
  }
})

const skuTreeData = computed({
  get () {
    return props.skuTree
  },
  set (val) {
    emit('update:skuTree', val)
  }
})

const hasSkuValue = computed({
  get () {
    return props.hasSkuVal
  },
  set (val) {
    emit('update:hasSkuVal', val)
  }
})

watch(() => data.value, () => {
  // 已选属性值，隐藏错误提示
  if (data.value?.some((item) => item.id && item.leaf?.length > 0)) {
    hasSkuValue.value = true
  }
  // 已选属性，隐藏错误提示
  if (data.value?.some((item) => item.id)) {
    Data.hasSkuId = true
  }
})

watch(() => props.categoryId, () => {
  if (!props.categoryId) {
    data.value.splice(0, data.value.length)
  }
})

onBeforeMount(() => {
  if (typeof (props.onFetchGroup) == 'function') {
    props.onFetchGroup().then(skuTree => {
      skuTreeData.value = skuTree
    })
  }
})

const onChangeHasImg = (data) => {
  Data.hasSkuImg = data
}

const addSku = () => {
  hasSkuValue.value = true
  Data.hasSkuId = true
  if (!props.categoryId) {
    Data.warning = true
    return
  }
  // 未选属性值，提示用户并禁继续选属性
  if (data.value?.some((item) => item.id && item.leaf && !item.leaf.length)) {
    hasSkuValue.value = false
    return
  }
  // 未选已添加属性，提示用户并禁止继续加选属性
  if (data.value?.some((item) => !item.id)) {
    Data.hasSkuId = false
    return
  }
  data.value.push({
    leaf: []
  })
}

const rebuildSku = (sku, index) => {
  if (
    data.value.some(
      (item, idx) => item[props.optionText] === sku[props.optionText] && index !== idx
    )
  ) {
    ElMessage({
      message: '规格名不能重复',
      duration: 800
    })
    return false
  }
  data.value[index] = Object.assign({}, sku)

  emit('on-change', data.value)
}

const handleSkuRemove = (sku, index) => {
  data.value.splice(index, 1)

  if (index === 0 && data.value.length > 0) {
    data.value[0].leaf.forEach((item) => {
      item.is_show = sku?.leaf[0].is_show
    })
  }
  // 属性选择列表中不存在被删除的已选属性
  if (!skuTreeData.value?.some(item => item[props.optionValue] === sku[props.optionValue])) {
    // 被删除的已选属性id为数字类型（即非手动创建的属性）
    if (typeof (sku[props.optionValue]) === 'number') {
      // 删除某个已选属性后，往属性选项列表中增加被删除的已选属性
      skuTreeData.value.push(sku)
    }
  }
  emit('on-change', data.value)
}

provide('ease', props)

</script>

<style lang="scss" scoped>
.component-sku-block {
  .warning {
    display: inline-block;
    margin-left: 15px;
    color: #e43130;
    font-size: 13px;
  }
}
</style>
