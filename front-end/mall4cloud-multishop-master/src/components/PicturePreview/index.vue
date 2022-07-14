<template>
  <el-dialog
    :close-on-click-modal="false"
    :visible.sync="visible"
    top="10vh"
    :append-to-body="true"
    :lock-scroll="true"
    class="img-preview" width="650px">
    <div class="img-box">
      <img :src="(imgUrl).indexOf('http')===-1 ? resourcesUrl + imgUrl : imgUrl" class="img">
    </div>
  </el-dialog>
</template>

<script>
export default {

  data() {
    return {
      imgUrl: '',
      visible: false,
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
    }
  },

  methods: {
    init(imgUrl) {
      this.imgUrl = imgUrl
      this.visible = true
    }
  }

}
</script>

<style lang="scss" scoped>
.img-preview {
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
</style>