<template>
  <el-dialog
    v-model="visible"
    :close-on-click-modal="false"
    top="0"
    :append-to-body="true"
    :lock-scroll="true"
    title="查看图片"
    class="component-picture-preview"
    width="650px"
  >
    <div class="img-box">
      <img
        :src="(imgUrl).indexOf('http') === -1 ? resourcesUrl + imgUrl : imgUrl"
        class="img"
      >
    </div>
  </el-dialog>
</template>

<script setup>
import { reactive, toRefs } from 'vue'

const Data = reactive({
  imgUrl: '',
  visible: false,
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL
})
const { imgUrl, visible, resourcesUrl } = toRefs(Data)

const init = (imgUrl) => {
  Data.imgUrl = imgUrl
  Data.visible = true
}

defineExpose({
  init
})

</script>

<style lang="scss" scoped>
.component-picture-preview {

  :deep(.el-dialog__wrapper) {
    display: flex;
    align-items: center;
  }

  :deep(.el-dialog__body) {
    padding: 20px;
    padding-top: 10px;

    .img-box {
      display: block;
      width: 100%;
      max-height: 700px;
      overflow: auto;
    }

    .img-box::-webkit-scrollbar {
      display: none;
    }

    .img {
      display: block;
      width: 100%;
      height: auto;
    }
  }
}
</style>
