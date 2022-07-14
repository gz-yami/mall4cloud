<template>
  <!-- 属性组 -->
  <div class="part-spec-section">
    <div class="spec-box">
      <div class="spec-select">
        <div class="sel-box">
          <el-select
            size="mini"
            v-model="skuValue"
            placeholder="请选择"
            allow-create
            filterable
            default-first-option
            :popper-append-to-body="false"
            :filter-method="filterMethod"
            style="min-width:100px"
            @change="handleSelectSku">
            <el-option
              v-for="item in ease.skuTreeData"
              :key="item[optionValue]"
              :label="item[optionText]"
              :value="item[optionValue]"
            />
          </el-select>
        </div>
<!--        <div v-if="index === 0 && ease.showAddSkuImage" class="sel-add-img">-->
<!--          <el-checkbox v-model="hasSkuImage"  @change="handleCheckedSkuImage">添加属性图片</el-checkbox>-->
<!--        </div>-->
      </div>
      <div class="del-btn" @click="onSkuRemove(sku, index)">删除属性</div>
    </div>
    <!-- sku值 -->
    <sku-container :sku="sku" :hasSkuImage="hasSkuImage" :onSkuLeafChange="handleSkuLeafChange"/>
  </div>

</template>

<script>
// import ImgUpload from '@/components/ImgUpload'
import skuContainer from '@/components/Sku/SkuContainer'
const noop = res => res

export default {
  components: { skuContainer },
  inject: [
    'ease'
  ],
  props: {
    index: {
      type: Number,
      default: 0
    },
    sku: {
      type: Object,
      default() {
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
      default: noop
    },
    onSkuRemove: {
      type: Function,
      default: noop
    }
  },

  data() {
    return {
      currentValue: '',
      currentSku: '',
      skuValue: '',
      newsSkuText: '',
      skus: this.sku,
      hasSkuImage: this.sku.leaf
        ? this.sku.leaf.some(item => item.imgUrl)
        : false
    }
  },

  watch: {
    sku: {
      deep: true,
      immediate: true,
      handler(sku) {
        if (sku[this.optionText]) {
          this.$nextTick(() => {
            this.skuValue = sku[this.optionText]
            this.currentSku = sku
          })
        }
      }
    }
  },

  methods: {
    filterMethod(keyword) {
      // let { optionText } = this
      // if (this.ease.skuTreeData.some(item => item[optionText] === keyword)) return

      // this.newsSkuText = keyword
    },

    // 选择sku
    handleSelectSku(value) {
      if (value.length > 10) {
        this.$message({
          message: `属性名长度不可超过10个字符`,
          duration: 1500
        })
        this.skuValue = ''
        return
      }
      let { index, optionValue } = this
      // 当切换当前属性时，把当前属性重新放入可选择列表中
      console.log(value)
      console.log(this.currentSku)
      if (this.currentSku !== '' && value !== this.currentSku.id) {
        console.log(value)
        this.ease.skuTreeData.push(this.currentSku)
      }

      if (typeof (value) === 'number') {
        let sku = this.ease.skuTreeData.find(item => item[optionValue] === value)
        sku.leaf = []
        if (this.onSkuChange(sku, index) === false) {
          this.skuValue = ''
        } else {
          this.ease.skuTreeData.some((item,idx) => {
            // 列表删除已选中属性
            if (item[optionValue] === value) {
              console.log(this.ease)
              this.currentSku = this.ease.skuTreeData[idx]
              this.ease.skuTreeData.splice(idx,1)
            }
            return false
          })
        }
        return
      }

      this.createSku(value)
    },
    // 创建sku
    createSku(text) {
      let { sku, index, optionValue, optionText } = this

      this.ease.onCreateGroup(text).then(data => {
        if (data > 0) {
          sku = {
            [optionValue]: data,
            [optionText]: text,
            leaf: [],
          }

          this.onSkuChange(sku, index)
        }
      })
    },

    // 添加图片复选框
    handleCheckedSkuImage(checked) {
      let { sku, index } = this
      sku.leaf = sku.leaf.map(item => {
        item.is_show = checked
        return item
      })
      this.onSkuChange(sku, index)
    },

    handleSkuLeafChange(leaf) {
      let { sku, index } = this
      sku.leaf = leaf
      this.onSkuChange(sku, index)
    }

  }
}
</script>
<style lang="scss">
/* 商品属性 */
.part-spec-section {
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
    .del-btn {
      font-size: 13px;
      color: #02a1e9;
      cursor: pointer;
    }
  }
}
</style>
