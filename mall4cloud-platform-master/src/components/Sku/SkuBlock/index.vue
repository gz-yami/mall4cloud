<template>
  <div class="ease-sku">
    <sku-group
      v-for="(item, index) in data"
      :key="index"
      :index="index"
      :sku="item"
      :skuTree="skuTreeData"
      :onChangeHasImg="onChangeHasImg"
      :onSkuChange="rebuildSku"
      :onSkuRemove="handleSkuRemove"
      />
    <!-- 添加属性 -->
    <!-- <div  v-if="data.length < maxSize" class="part-form-item">
      <el-button class="add-btn" size="mini" @click="addSku"><i class="el-icon-plus"></i>添加属性</el-button>
      <div v-if="!categoryId && warning" class="warning">请先选择分类！</div>
    </div> -->
  </div>
</template>

<script>
import skuGroup from '@/components/Sku/SkuGroup'
import { Message } from 'element-ui'

const noop = res => res
const noopPromise = () => Promise.resolve(noop)

export default {
  name: 'sku-block',

  provide() {
    return {
      'ease': this
    }
  },

  components: {
    skuGroup
  },

  data() {
    return {
      data: this.salesAttrs,
      skuTreeData: this.skuTree,
      warning: false
    }
  },

  props: {
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
      default() {
        return []
      }
    },
    saveSalesAttrs: {
      type: Array,
      default() {
        return []
      }
    },
    action: {
      type: String,
      default: ''
    },
    headers: {
      type: Object,
      default() {
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
      default() {
        return []
      }
    },
    sku: {
      type: Object,
      default() {
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
      default: noopPromise
    },
    // 异步获取规格可选值
    onFetchSku: {
      type: Function,
      default: noopPromise
    },
    // 创建新的规格名
    onCreateGroup: {
      type: Function,
      default: noopPromise
    },
    // 创建新的规格值
    onCreateSku: {
      type: Function,
      default: noopPromise
    },
    // 图片预览
    onPreviewImg: {
      type: Function,
      default: noopPromise
    },
  },

  watch: {
    data: {
      deep: true,
      immediate: true,
      handler(salesAttrs) {
        this.$emit('input', salesAttrs)
      }
    },

    skuTree(skuTree) {
      this.skuTreeData = skuTree
    },

    categoryId() {
      if (!this.categoryId) {
        this.data.splice(0, this.data.length)
      }
    }
  },

  methods: {
    onChangeHasImg(data) {
      this.hasSkuImg = data
    },


    addSku() {
      if (!this.categoryId) {
        this.warning = true
        return
      }

      this.data.push({
        leaf: []
      })

    },

    rebuildSku(sku, index) {
      let { skuTreeData, optionValue } = this

      if (
        this.data.some(
          (item, idx) => item[optionValue] === sku[optionValue] && index !== idx
        )
      ) {
        this.$message({
          message: '规格名不能重复',
          duration: 800
        });
        return false
      }
      this.$set(this.data, index, Object.assign({}, sku))

      if (!skuTreeData.some(item => item[optionValue] === sku[optionValue])) {
        skuTreeData.push(sku)
      }
      this.$emit('on-change', this.data)
    },

    handleSkuRemove(index) {
      let { data } = this
      data.splice(index, 1)

      this.$emit('on-change', this.data)
    }
  },

  beforeMount() {
    let { onFetchGroup } = this
    if (typeof (onFetchGroup) == 'function') {
      onFetchGroup().then(skuTree => {
        this.skuTreeData = skuTree
      })
    }
  }
}
</script>

<style lang="scss">
.ease-sku {
  .add-btn.el-button {
    border-color: #02a1e9;
    color: #02a1e9;
  }
  .warning {
    display: inline-block;
    margin-left: 15px;
    color: #e43130;
    font-size: 13px;
  }
}
</style>
