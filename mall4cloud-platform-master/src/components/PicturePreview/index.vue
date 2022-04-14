<template>
  <el-dialog
    :close-on-click-modal="false"
    :visible.sync="visible"
    top="0"
    :append-to-body="true"
    :lock-scroll="true"
    title="查看图片"
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

<style lang="scss" scope>
.img-preview.el-dialog__wrapper {
  display: flex;
  align-items: center;
}
.img-preview {
  // .el-dialog__headerbtn {
  //   top: 15px;
  // }
  .el-dialog__body {
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