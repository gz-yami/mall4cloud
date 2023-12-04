<template>
  <!-- 属性组 -->
  <div class="component-sku-group">
    <div class="spec-box">
      <div class="spec-select">
        <div class="sel-box">
          <el-select
            v-model="skuValue"
            placeholder="请选择"
            allow-create
            filterable
            :filter-method="filterMethod"
            style="min-width:100px"
            disabled
            @change="handleSelectSku"
          >
            <el-option
              v-for="item in ease.skuTreeData"
              :key="item[optionValue]"
              :label="item[optionText]"
              :value="item[optionValue]"
            />
          </el-select>
        </div>
        <div
          v-if="index === 0 && ease.showAddSkuImage"
          class="sel-add-img"
        >
          <el-checkbox
            v-model="hasSkuImage"
            :disabled="true"
            @change="handleCheckedSkuImage"
          >
            添加属性图片
          </el-checkbox>
        </div>
      </div>
    </div>
    <!-- sku值 -->
    <sku-container
      v-model:sku="setSku"
      :has-sku-image="hasSkuImage"
      :on-sku-leaf-change="handleSkuLeafChange"
    />
  </div>
</template>

<script setup>
import skuContainer from '@/components/Sku/SkuContainer/index.vue'
import { computed, reactive } from 'vue'
const ease = inject('ease')

const emit = defineEmits(['update:sku'])
const props = defineProps({
  index: {
    type: Number,
    default: 0
  },
  sku: {
    type: Object,
    default () {
      return {}
    }
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
  onSkuChange: {
    type: Function,
    default: () => {}
  },
  onSkuRemove: {
    type: Function,
    default: () => {}
  }
})

const Data = reactive({
  skuValue: '',
  newsSkuText: '',
  hasSkuImage: props.sku.leaf ? props.sku.leaf.some(item => item.imgUrl) : false
})

const { skuValue, hasSkuImage } = toRefs(Data)

const setSku = computed({
  get () {
    return props.sku
  },
  set (val) {
    emit('update:sku', val)
  }
})
// 选择sku
const handleSelectSku = (value) => {
  if (typeof (value) === 'number') {
    const sku = ease.skuTreeData.find(item => item[props.optionValue] === value)
    sku.leaf = []
    if (props.onSkuChange(sku, props.index) === false) {
      Data.skuValue = ''
    }
    return
  }

  createSku(value)
}

// 创建sku
const createSku = (text) => {
  ease.onCreateGroup(text).then(data => {
    if (data > 0) {
      setSku.value = {
        [props.optionValue]: data,
        [props.optionText]: text,
        leaf: []
      }

      props.onSkuChange(props.sku, props.index)
    }
  })
}

// 添加图片复选框
const handleCheckedSkuImage = (checked) => {
  setSku.value.leaf = props.sku.leaf.map(item => {
    item.is_show = checked
    return item
  })
  props.onSkuChange(props.sku, props.index)
}

const handleSkuLeafChange = (leaf) => {
  setSku.value.leaf = leaf
  this.onSkuChange(props.sku, props.index)
}
</script>

<style lang="scss" scoped>
/* 商品属性 */
.component-sku-group {
  margin-top: 10px;
  margin-bottom: 20px;
  .spec-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    min-width: 400px;
    padding: 8px 10px;
    background: #f4f5f9;
    .spec-select {
      display: flex;
      align-items: center;
    }
    .spec-select .sel-add-img {
      margin-left: 20px;
    }
  }
}
</style>
