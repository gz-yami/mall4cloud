<template>
  <div class="components-add-or-upload">
    <el-tooltip
      v-if="props.tinymceUploadType === 'prod'"
      :content="$t('product.uploadDetailPicTips')"
      placement="top"
    >
      <el-button
        type="primary"
        @click="clickUpload()"
      >
        <el-icon><UploadFilled /></el-icon>
        上传图片
      </el-button>
    </el-tooltip>

    <el-button
      v-else
      :style="{ background: props.color, borderColor: props.color }"
      type="primary"
      @click="clickUpload()"
    >
      <el-icon><UploadFilled /></el-icon>
      上传图片
    </el-button>
    <!-- 弹窗, 新增图片 -->
    <imgbox
      v-if="elxImgboxVisible"
      ref="elxImgboxRef"
      @refresh-pic="refreshPic"
    />
  </div>
</template>
<script setup>
const emit = defineEmits(['successCBK'])
const props = defineProps({
  color: {
    type: String,
    default: '#155bd4'
  },
  tinymceUploadType: {
    default: '',
    type: String
  }
})

const elxImgboxVisible = ref(false)
const maxNum = ref(15) // 可选择的最大图片数量
const imgUrls = ref([])
const resourcesUrl = import.meta.env.VITE_APP_RESOURCES_URL
const elxImgboxRef = ref()
const dialogVisible = ref()
/**
 * 打开图片选择窗
 */
const clickUpload = () => {
  imgUrls.value = ''
  elxImgboxVisible.value = true
  nextTick(() => {
    elxImgboxRef.value.init(0, maxNum.value)
  })
}
/**
 * 接收回调的图片数据
 */
const refreshPic = imagePath => {
  const imageArray = imagePath.split(',')
  const data = []
  imageArray.forEach(img => {
    data.push(resourcesUrl + img)
  })
  imgUrls.value = ''
  dialogVisible.value = false
  emit('successCBK', data)
}
</script>
<style lang="scss" scoped>
.components-add-or-upload{
  margin-bottom: 20px;
}
</style>
