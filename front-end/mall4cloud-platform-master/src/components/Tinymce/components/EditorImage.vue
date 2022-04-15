<template>
  <div class="upload-container">
    <el-button :style="{background:color,borderColor:color}" icon="el-icon-upload" size="mini" type="primary" @click="clickUpload()">
      upload
    </el-button>
    <el-dialog :visible.sync="dialogVisible">
      <imgs-upload v-model="imgUrls" />
      <el-button @click="dialogVisible = false">
        Cancel
      </el-button>
      <el-button type="primary" @click="handleSubmit">
        Confirm
      </el-button>
    </el-dialog>
  </div>
</template>

<script>
// import { getToken } from 'api/qiniu'
import ImgsUpload from '@/components/ImgsUpload'
export default {
  name: 'EditorSlideUpload',
  props: {
    color: {
      type: String,
      default: '#1890ff'
    }
  },
  data() {
    return {
      dialogVisible: false,
      imgUrls: [],
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
    }
  },
  components: {
    ImgsUpload
  },
  methods: {
    clickUpload () {
      console.log('this.imgUrls', this.imgUrls)
      this.imgUrls = ''
      console.log('this.imgUrls', this.imgUrls)
      // this.$set(this.imgUrls, '')
      this.dialogVisible=true
    },
    handleSubmit() {
      let imageArray = this.imgUrls.split(',')
      var data = []
      imageArray.forEach(img => {
        data.push(this.resourcesUrl + img)
      })
      this.imgUrls = ''
      this.dialogVisible = false
      this.$emit('successCBK', data)
    }
  }
}
</script>

<style lang="scss" scoped>
.editor-slide-upload {
  margin-bottom: 20px;
  ::v-deep .el-upload--picture-card {
    width: 100%;
  }
}
</style>
