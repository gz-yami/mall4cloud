<template>
  <div>
    <div class="plugin-images">
      <div tabindex="0" class="el-upload el-upload--text" @click="elxImgboxHandle">
        <img  v-if="value" :src="getImgSrc()" class="pic" />
        <i v-else class="el-icon-plus pic-uploader-icon"></i>
      </div>
    </div>
    <!-- 弹窗, 新增图片 -->
    <elx-imgbox v-if="elxImgboxVisible" ref="elxImgbox" @refreshPic="refreshPic"></elx-imgbox>
    <el-dialog :visible.sync="visible" :append-to-body="visible">
      <img width="100%" :src="getImgSrc()" alt />
    </el-dialog>
  </div>
</template>

<script>
import ElxImgbox from '@/components/imgbox'
export default {
  data() {
    return {
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
      elxImgboxVisible: false,
      visible: false
    }
  },
  props: {
    value: {
      default: '',
      type: String
    },
    disabled: {
      default: false,
      type: Boolean
    }
  },
  components: {
    ElxImgbox
  },
  methods: {
    // 打开图片选择窗
    elxImgboxHandle() {
      if (this.disabled) {
        this.openImg()
        return false
      }
      this.elxImgboxVisible = true
      this.$nextTick(() => {
        this.$refs.elxImgbox.init(1)
      })
    },
    /**
     * 获取图片路径
     */
    getImgSrc() {
      if (!this.value) {
        return ''
      }
      if (this.value.indexOf('http://') === 0 || this.value.indexOf('https://') === 0) {
        return this.value
      }
      return this.resourcesUrl + this.value
    },
    openImg() {
      this.visible = true
    },
    refreshPic(imagePath) {
      console.log('imagePath', imagePath)
      this.$emit('input', imagePath)
    }
  }
}
</script>

<style lang="scss">
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
</style>
