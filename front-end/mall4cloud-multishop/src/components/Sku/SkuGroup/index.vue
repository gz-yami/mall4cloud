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
            default-first-option
            :popper-append-to-body="false"
            :filter-method="filterMethod"
            style="min-width:100px"
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
      </div>
      <div
        class="del-btn"
        @click="onSkuRemove(sku, index)"
      >
        删除属性
      </div>
    </div>
    <!-- sku值 -->
    <sku-container
      v-model:sku="skus"
      :has-sku-image="hasSkuImage"
      :on-sku-leaf-change="handleSkuLeafChange"
    />
  </div>
</template>

<script setup>
import { computed, reactive, watch } from 'vue'
import skuContainer from '@/components/Sku/SkuContainer/index.vue'

const emit = defineEmits(['update:sku'])

const ease = inject('ease')

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
  currentValue: '',
  currentSku: '',
  skuValue: '',
  newsSkuText: '',
  hasSkuImage: props.sku.leaf ? props.sku.leaf.some(item => item.imgUrl) : false
})

const { skuValue, hasSkuImage } = toRefs(Data)

const skus = computed({
  get () {
    return props.sku
  },
  set (val) {
    emit('update:sku', val)
  }
})

watch(() => props.sku, (sku) => {
  if (sku[props.optionText]) {
    nextTick(() => {
      Data.skuValue = sku[props.optionText]
      Data.currentSku = sku
    })
  }
}, {
  deep: true,
  immediate: true
})

const filterMethod = () => {
}

// 选择sku
const handleSelectSku = (value) => {
  if (value.length > 10) {
    ElMessage({
      message: '属性名长度不可超过10个字符',
      duration: 1500
    })
    Data.skuValue = ''
    return
  }
  // 当切换当前属性时，把当前属性重新放入可选择列表中
  if (Data.currentSku !== '' && value !== Data.currentSku.id) {
    ease.skuTreeData.push(Data.currentSku)
  }

  if (typeof (value) === 'number') {
    const sku = ease.skuTreeData.find(item => item[props.optionValue] === value)
    sku.leaf = []
    if (props.onSkuChange(sku, props.index) === false) {
      Data.skuValue = ''
    } else {
      ease.skuTreeData.some((item, idx) => {
        // 列表删除已选中属性
        if (item[props.optionValue] === value) {
          Data.currentSku = ease.skuTreeData[idx]
          ease.skuTreeData.splice(idx, 1)
        }
        return false
      })
    }
    return
  }

  createSku(value)
}

// 创建sku
const createSku = (text) => {
  ease.onCreateGroup(text).then(data => {
    if (data) {
      const sku = {
        [props.optionValue]: data,
        [props.optionText]: text,
        leaf: []
      }

      skus.value = sku

      props.onSkuChange(sku, props.index)
    }
  })
}

const handleSkuLeafChange = (leaf) => {
  skus.value.leaf = leaf
  props.onSkuChange(skus.value, props.index)
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

    .del-btn {
      font-size: 13px;
      color: #02a1e9;
      cursor: pointer;
    }
  }
}
</style>
