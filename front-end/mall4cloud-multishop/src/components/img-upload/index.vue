<template>
  <div class="component-img-upload">
    <div class="plugin-images">
      <div
        tabindex="0"
        class="el-upload el-upload--text"
        @click="elxImgboxHandle"
      >
        <img
          v-if="modelValue"
          :src="getImgSrc()"
          class="pic"
        >
        <el-icon
          v-else
          class="el-icon-plus pic-uploader-icon"
        >
          <Plus />
        </el-icon>
      </div>
    </div>
    <!-- 弹窗, 新增图片 -->
    <imgbox
      v-if="elxImgboxVisible"
      ref="elxImgboxRef"
      @refresh-pic="refreshPic"
    />
    <el-dialog
      v-model="visible"
      :append-to-body="visible"
    >
      <img
        width="100%"
        :src="getImgSrc()"
        alt
      >
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

const emit = defineEmits('update:modelValue')

const props = defineProps({
  modelValue: {
    default: '',
    type: String
  },
  disabled: {
    default: false,
    type: Boolean
  }
})

const Data = reactive({
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  elxImgboxVisible: false,
  visible: false
})

const { elxImgboxVisible, visible } = toRefs(Data)

const elxImgboxRef = ref()
// 打开图片选择窗
const elxImgboxHandle = () => {
  if (props.disabled) {
    openImg()
    return false
  }
  Data.elxImgboxVisible = true
  nextTick(() => {
    elxImgboxRef.value.init(1)
  })
}

/**
 * 获取图片路径
 */
const getImgSrc = () => {
  if (!props.modelValue) {
    return ''
  }
  if (props.modelValue.indexOf('http://') === 0 || props.modelValue.indexOf('https://') === 0) {
    return props.modelValue
  }
  return Data.resourcesUrl + props.modelValue
}

const openImg = () => {
  Data.visible = true
}

const refreshPic = (imagePath) => {
  emit('update:modelValue', imagePath)
}
</script>

<style lang="scss" scoped>
.component-img-upload {
  .plugin-images {
    display: inline-block;
    width: auto;

    .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;

      .pic-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
      }

      .pic {
        width: 120px;
        height: 120px;
        display: block;
      }

      .el-upload:hover {
        border-color: #409eff;
      }
    }
  }
}
</style>
