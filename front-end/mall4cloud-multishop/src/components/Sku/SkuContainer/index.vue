<template>
  <div class="component-sku-container">
    <div class="spec-name">
      <div
        v-for="(item, index) in skus.leaf"
        :key="index"
        class="spec-name-int"
      >
        <el-tag
          :type="item.type"
          effect="plain"
          closable
          @close="handleRemoveSkuLeaf(index)"
        >
          {{ item[ease.optionText] }}
        </el-tag>
        <div
          v-if="item.imgUrl"
          class="spec-imgbox"
        >
          <img-upload
            v-if="hasSkuImage"
            v-model="item.imgUrl"
            class="spec-img"
            @input="handleUploadSuccess(item, $event)"
          />
          <div
            v-if="hasSkuImage && item.imgUrl"
            class="preview-btn"
            @click="picturePreview(item.imgUrl)"
          >
            预览
          </div>
        </div>
      </div>

      <!-- 新增 -->
      <div
        v-if="skus[ease.optionValue] && skus[ease.optionValue] !== 0"
        class="sku-item"
      >
        <el-popover
          v-model="visiable"
          placement="bottom"
          width="200"
          trigger="click"
        >
          <el-select
            v-model="leafValue"
            class="popover-select"
            multiple
            filterable
            allow-create
            default-first-option
            :popper-append-to-body="false"
            :teleported="false"
            style="width:100%"
            placeholder="属性值"
            @change="createSkuLeaf"
            @visible-change="status => !status && handleSelectSku()"
          >
            <el-option
              v-for="item in skuOptions"
              :key="item[ease.optionValue]"
              :label="item[ease.optionText]"
              :value="item[ease.optionValue]"
            />
          </el-select>

          <template #reference>
            <!-- 新增 -->
            <el-button
              circle
              type="primary"
              plain
              class="cursor"
            >
              <el-icon><Plus /></el-icon>
            </el-button>
          </template>
        </el-popover>
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
    default: () => {}
  }
})

const Data = reactive({
  visiable: false,
  leafValue: [],
  skuOptions: [],
  id: 0
})
const { visiable, leafValue, skuOptions } = toRefs(Data)

const skus = computed({
  get () {
    return props.sku
  },
  set (val) {
    emit('update:sku', val)
  }
})

const handleHideVisiable = () => {
  Data.visiable = false
}

const handleResetLeafValue = () => {
  Data.leafValue = []
}

const fetchLeafById = (id) => {
  if (!id) return
  ease.onFetchSku(id).then(skuOptions => {
    Data.id = id
    Data.skuOptions = skuOptions
    const skuList = []
    // 筛选未选择的属性
    Data.skuOptions.forEach(skuItem => {
      if (!skus.value.leaf.find(item => item.id === skuItem.id)) {
        skuList.push(skuItem)
      }
    })
    Data.skuOptions = skuList
  })
}

const handleRemoveSkuLeaf = (index) => {
  skus.value.leaf.splice(index, 1)

  props.onSkuLeafChange(skus.value.leaf)
}

const filterSkuOptions = (data) => {
  const oldSellData = []
  const addSelData = []
  const skuOptions = Data.skuOptions
  data.forEach(item => {
    if (skuOptions.find(skuItem => skuItem.id === item)) {
      oldSellData.push(item)
    } else {
      addSelData.push(item)
    }
  })
  return {
    oldSellData, addSelData
  }
}

const createSkuLeaf = (selVal) => {
  // 过滤需要新增的规格值
  // data = data.filter(item => typeof (item) === 'string')
  const { addSelData, oldSellData } = filterSkuOptions(selVal)
  if (!addSelData.length) return
  ease.onCreateSku({
    data: addSelData,
    id: skus.value[ease.optionValue]
  }).then((processedNewOptions) => {
    if (processedNewOptions[0].text.length > 20) {
      ElMessage({
        message: '属性名长度不可超过20个字符',
        duration: 1500
      })
      processedNewOptions = []
    }
    Data.skuOptions.push(...processedNewOptions)
    nextTick(() => {
      const values = processedNewOptions.map(item => item.id).concat(oldSellData)
      Data.leafValue = values
    })
  })
}

const handleSelectSku = () => {
  const skuLeaf = Data.skuOptions.filter(item => Data.leafValue.indexOf(item[ease.optionValue]) >= 0)
  skuLeaf.forEach(item => {
    item.is_show = props.hasSkuImage
  })

  const skuLeafIds = skus.value.leaf.map(item => item[ease.optionValue])

  skuLeaf.forEach(item => {
    if (skuLeafIds.indexOf(item[ease.optionValue]) < 0) {
      skus.value.leaf.push(item)
    }
  })

  // 过滤同名规格值
  for (let i = 0; i < skus.value.leaf.length; i++) {
    for (let j = i + 1; j < skus.value.leaf.length; j++) {
      if (skus.value.leaf[i][ease.optionText] === skus.value.leaf[j][ease.optionText]) {
        skus.value.leaf.splice(i, 1)
        j--
      }
    }
  }

  handleResetLeafValue()
  handleHideVisiable()
  props.onSkuLeafChange(skus.value.leaf)
}

const handleUploadSuccess = () => {
  props.onSkuLeafChange(skus.value.leaf)
}

// 图片预览
const picturePreview = (imgUrl) => {
  ease.onPreviewImg(imgUrl)
}

watch(skus.value, () => {
  fetchLeafById(skus.value[ease.optionValue])
}, {
  immediate: true
})

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
          bottom: -1px;
          left: 1px;
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
          // margin-left: 25px;
          margin-top: 10px;
          // sku上传图片组件大小修改
        }
      }
      .spec-imgbox:hover .preview-btn {
        display: block;
      }
    }

    .sku-item {
      display: inline-block;
    }
  }
}
</style>
