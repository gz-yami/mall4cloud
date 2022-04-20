<template>
  <view class="upload-img">
    上传文件
    <view class="mask"></view>
    <view class="content">
      <view class="tit-top">
        <view class="tit">上传图片</view>
        <view class="close" @click="close">×</view>
      </view>
      <view class="upload">
        <view v-if="!tempFilePaths.length" class="upload-chequer" @click="getUploadImg">
          <view class="cross">＋</view>
        </view>
        <view v-if="tempFilePaths.length" class="preview">
          <image v-for="item in tempFilePaths" :key="item" :src="item" @click="getUploadImg" />
        </view>

        <view class="foot-btn">
          <span :class="['btn', tempFilePaths.length ? '' : 'gray']" @click="beforeUpload">确认</span>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
const http = require('../../utils/http.js')
import config from '../../utils/config.js'
// import { minIoImgUpdate } from '../../utils/api.js'
export default {
  data() {
    return {
      imgNum: 1, // 图片上传数量，默认1，最大9
      resourcesUrl: config.resourcesUrl,
      resourcesActionType: config.resourcesActionType,
      notImg: false,
      tempFilePaths: [], // 图片上传列表
      dataForm: {},
      attachFiles: [],
      feilType: [],
      ossList: null,
    }
  },

  methods: {
    // init() {
      
    // },

    /**
     * 上传图片
     */
    getUploadImg() {
      wx.chooseImage({
        count: this.imgNum, // 默认9
        sizeType: ['original', 'compressed'],
        sourceType: ['album', 'camera'],
        success: (res) => {
          wx.getImageInfo({
            src:res.tempFilePaths[0],
            success:(request)=>{
            // 图片的本地临时文件路径列表
              this.tempFilePaths = res.tempFilePaths;
              this.tempFiles = res.tempFiles
              console.log('resourcesActionType：', this.resourcesActionType)
              console.log('选择本地图片res：', res)
          },
            fail:(err)=>{
              uni.showToast({
                title: '只能上传图片!',
                icon: 'none'})
            // console.log('非图片格式')
          }
          })

        }
      });
    },

    /**
     * 校验图片合法性
     */
    beforeUpload() {
      if(!this.tempFiles) {
        return
      }
      // this.feilType = this.tempFiles ? this.tempFiles[0].type.split('/') : null
      // if (this.feilType && this.feilType[0] !== 'image') {
      //   if (this.notImg) {
      //     uni.showToast({
      //       title: '只能上传图片,其他文件已清除',
      //       icon: 'none'
      //     })
      //     this.notImg = false
      //   }
      //   return
      // }
      const isSize = this.tempFiles[0].size / (1024 * 1024) < 2
      if (!isSize) {
        uni.showToast({
          title: '大小不能超过2M',
          icon: 'none'
        })
        return
      }
      this.getUploadData()
    },

    /**
     * 获取上传图片数据
     */
    getUploadData() {
      //minio特殊处理
      if (this.resourcesActionType === '1') {
        this.mioIoUpload()
        return
      }
      var params = {
        url: '/mall4cloud_biz/oss/info',
        method: 'GET',
        data: {
          fileNum: this.tempFilePaths.length // 需要获取token的文件数量
        },
        callBack: res => {
          console.log('获取图片数据res：', res)
          uni.hideLoading()
          if (this.resourcesActionType === '0') {
            this.dataForm.policy = res.policy
            this.dataForm.signature = res.signature
            this.dataForm.ossaccessKeyId = res.accessid
            this.dataForm.dir = res.dir
          }
          this.ossList = res.ossList
          console.log('获取图片数据ossList：', res.ossList)
          this.notImg = true
          this.confirmUpload()
        }
      }
      http.request(params)
    },

    /**
     * 确认上传
     */
    confirmUpload() {
      const attachFile = Object.assign({
        fileType: this.feilType[1],
        fileName: this.tempFiles[0].name,
        fileSize: this.tempFiles[0].size,
        type: 1,
      })
      // if (this.ossList.length <= 0) {
      //   this.message('数据异常，请刷新后重试')
      // }
      // aliOss 上传
      if (this.resourcesActionType === '0') {
        attachFile.filePath = '/' + this.dataForm.dir + this.ossList[0].fileName
        this.upload(this.resourcesUrl)
      }

      this.attachFiles.push(attachFile)
      this.ossList.splice(0, 1)
      if (this.ossList <= 0) {
        // this.onUploadSuccess()
      }

    },

    /**
     * 上传文件接口
     */
    upload(url) {
      const filePath = this.ossList[0].dir + this.ossList[0].fileName
      console.log('上传文件filePath：', filePath)
      
      uni.uploadFile({
        url: url,
        header: {
          'Authorization': uni.getStorageSync('token')
        },
        filePath: this.tempFiles[0].path,
        name: 'file',
        formData: {
          'policy': this.dataForm.policy,
          'signature': this.dataForm.signature,
          'ossaccessKeyId': this.dataForm.ossaccessKeyId,
          'dir': this.dataForm.dir,
          'host': this.resourcesUrl,
          'key': filePath,
        },
        dataType: 'json',
        responseType: 'json',
        success: (res) => {
          // this.onUploadSuccess()
          console.log('上传成功后返回父组件的图片filePath：', filePath)
          this.$emit('closeImgPop', this.tempFilePaths[0], '/' + filePath)
        },
        fail: (err) => {
          console.log('上传失败err：', err)
          uni.showToast({
            title: '请求失败',
            icon: 'none'
          })
        }
      })

    },
    
    // mioIo 上传文件
    mioIoUpload() {
      //const filePath = this.ossList[0].dir + this.ossList[0].fileName
      const  params = {
        url: '/mall4cloud_biz/oss/upload_minio',
        filePath: this.tempFilePaths[0],
        name: 'file',
        callBack: res => {
    
          let data = JSON.parse(res).data
          console.log(data)
          const filePath=data.dir+data.fileName
          this.resourcesVal = this.resourcesUrl + '/' + data.dir + data.fileName
          // this.$emit('getImagePath', this.resourcesVal, this.imgId)
          this.$emit('closeImgPop', this.tempFilePaths[0], '/' + filePath)
        }
      }
      http.upload(params)
      // const file = files[0]
      // console.log(url)
      // console.log(file)
      // console.log(filePath)
      // // var params = {
      // //   uploadUrl: url,
      // //   header: {
      // //     ContentType: file.type,
      // //   },
      // //   method: 'PUT',
      // //   data: file,

      // //   callBack: res => {
      //     // uni.hideLoading()
      //     // console.log(this.ossList)
      //     // // const filePath = this.ossList[0].dir + this.ossList[0].fileName
      //     // console.log(filePath)
      //     // this.$emit('closeImgPop', this.tempFilePaths[0], filePath)
      // //   }
      // // }
      // // http.request(params)
      // minIoImgUpdate(url,file).then(res=>{
      //   uni.hideLoading()
      //     console.log(this.ossList)
      //     // const filePath = this.ossList[0].dir + this.ossList[0].fileName
      //     console.log(filePath)
      //     this.$emit('closeImgPop', this.tempFilePaths[0], filePath)
      // })
    },

    /**
     * 上传成功处理，并提交图片数据
     */
    // onUploadSuccess() {

    //   var uploadFile = []
    //   this.attachFiles.forEach(file => {
    //     uploadFile.push(file)
    //   })

    //   console.log('上传成功处理uploadFile：', uploadFile)
      
    //   // 保存上传文件记录
    //   var params = {
    //     url: '/mall4cloud_biz/m/attach_file',
    //     method: 'POST',
    //     data: uploadFile,
    //     callBack: res => {
    //       console.log('保存上传文件记录成功res:', res)
    //       uni.hideLoading()
    //     }
    //   }
    //   http.request(params)
    // },

    /**
     * 关闭弹窗
     */
    close() {
      this.$emit('closeImgPop')
    }

  }

}
</script>

<style scoped> @import "./imgBox.css"; </style>