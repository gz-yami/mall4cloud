<template>
  <div class="component-sku-container">
    <div class="spec-name">
      <div
        v-for="(item, index) in sku.leaf"
        :key="index"
        class="spec-name-int"
      >
        <el-tag
          :type="item.type"
          effect="plain"
          @close="handleRemoveSkuLeaf(index)"
        >
          {{ item[optionText] }}
        </el-tag>
        <div
          v-if="item.imgUrl"
          class="spec-imgbox"
        >
          <img
            v-if="hasSkuImage"
            :src="(item.imgUrl).indexOf('http') === -1 ? resourcesUrl + item.imgUrl : item.imgUrl"
            class="spec-img"
          >
          <div
            class="preview-btn"
            @click="picturePreview(item.imgUrl)"
          >
            预览
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, reactive, watch } from 'vue'

const ease = inject('ease')
const emit = defineEmits(['update:sku'])

const props = defineProps({
  sku: {
    type: Object,
    default () {
      return {}
    }
  },
  hasSkuImage: {
    type: Boolean,
    default: false
  },
  onSkuLeafChange: {
    type: Function,
    default: () => { }
  }
})

const Data = reactive({
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  visiable: false,
  skuOptions: [],
  id: 0
})

const { resourcesUrl } = toRefs(Data)

const setSku = computed({
  get () {
    return setSku.value
  },
  set (val) {
    emit('update:sku', val)
  }
})

const optionValue = computed(() => {
  return ease.optionValue
})

const optionText = computed(() => {
  return ease.optionText
})

watch(() => setSku.value, (sku) => {
  fetchLeafById(sku[optionValue.value])
})

const fetchLeafById = (id) => {
  if (!id) return
  ease.onFetchSku(id).then(skuOptions => {
    Data.id = id
    Data.skuOptions = skuOptions
  })
}

const handleRemoveSkuLeaf = (index) => {
  setSku.value.leaf.splice(index, 1)

  props.onSkuLeafChange(setSku.value.leaf)
}

// 图片预览
const picturePreview = (imgUrl) => {
  ease.onPreviewImg(imgUrl)
}
</script>

<style lang="scss" scoped>
.component-sku-container {
  .spec-name {
    margin: 15px 5px;

    .spec-name-int {
      display: inline-block;
      margin-bottom: 15px;
      margin-right: 15px;
      vertical-align: top;

      // 属性名称
      .spec-imgbox {
        display: block;
        width: 60px;
        height: 60px;
        position: relative;

        .preview-btn {
          position: absolute;
          bottom: 0;
          left: 0;
          display: none;
          width: 100%;
          background: rgba(0, 0, 0, 0.2);
          line-height: 1.5em;
          text-align: center;
          font-size: 12px;
          border-radius: 0 0 6px 6px;
          cursor: pointer;
        }

        .spec-img {
          display: block;
          width: 60px;
          height: 60px;
          border: 1px dashed #d9d9d9;
          border-radius: 6px;
          // margin-left: 25px;
          margin-top: 10px;
        }
      }

      .spec-imgbox:hover .preview-btn {
        display: block;
      }
    }
  }
}
</style>
