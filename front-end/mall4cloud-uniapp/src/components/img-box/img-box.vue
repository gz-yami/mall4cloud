<template>
  <view class="component-img-box">
    上传文件
    <view class="mask" />
    <view class="content">
      <view class="tit-top">
        <view class="tit">
          上传图片
        </view>
        <view
          class="close"
          @click="close"
        >
          ×
        </view>
      </view>
      <view class="upload">
        <view
          v-if="!tempFilePaths.length"
          class="upload-chequer"
          @click="getUploadImg"
        >
          <view class="cross">
            ＋
          </view>
        </view>
        <view
          v-if="tempFilePaths.length"
          class="preview"
        >
          <image
            v-for="item in tempFilePaths"
            :key="item"
            class="img"
            :src="item"
            @click="getUploadImg"
          />
        </view>

        <view class="foot-btn">
          <span
            :class="['btn', tempFilePaths.length ? '' : 'gray']"
            @click="beforeUpload"
          >确认</span>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue'

const emit = defineEmits(['closeImgPop'])

const Data = reactive({
  imgNum: 1, // 图片上传数量，默认1，最大9
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  resourcesActionType: import.meta.env.VITE_APP_RESOURCES_ACTION_TYPE,
  notImg: false,
  tempFilePaths: [], // 图片上传列表
  dataForm: {},
  attachFiles: [],
  feilType: [],
  ossList: null
})
const { tempFilePaths } = toRefs(Data)

/**
 * 上传图片
 */
const getUploadImg = () => {
  uni.chooseImage({
    count: Data.imgNum, // 默认9
    sizeType: ['original', 'compressed'],
    sourceType: ['album', 'camera'],
    success: (res) => {
      uni.getImageInfo({
        src: res.tempFilePaths[0],
        success: () => {
          // 图片的本地临时文件路径列表
          Data.tempFilePaths = res.tempFilePaths
          Data.tempFiles = res.tempFiles
        },
        fail: () => {
          uni.showToast({
            title: '只能上传图片!',
            icon: 'none'
          })
        }
      })
    }
  })
}

/**
 * 校验图片合法性
 */
const beforeUpload = () => {
  if (!Data.tempFiles) {
    return
  }

  const isSize = Data.tempFiles[0].size / (1024 * 1024) < 2
  if (!isSize) {
    uni.showToast({
      title: '大小不能超过2M',
      icon: 'none'
    })
    return
  }
  getUploadData()
}

/**
 * 获取上传图片数据
 */
const getUploadData = () => {
  // minio特殊处理
  if (Data.resourcesActionType === '1') {
    mioIoUpload()
    return
  }
  const params = {
    url: '/mall4cloud_biz/oss/info',
    method: 'GET',
    data: {
      fileNum: Data.tempFilePaths.length // 需要获取token的文件数量
    },
    callBack: res => {
      uni.hideLoading()
      if (Data.resourcesActionType === '0') {
        Data.dataForm.policy = res.policy
        Data.dataForm.signature = res.signature
        Data.dataForm.ossaccessKeyId = res.accessid
        Data.dataForm.dir = res.dir
      }
      Data.ossList = res.ossList
      Data.notImg = true
      confirmUpload()
    }
  }
  http.request(params)
}

/**
 * 确认上传
 */
const confirmUpload = () => {
  const attachFile = Object.assign({
    fileType: Data.feilType[1],
    fileName: Data.tempFiles[0].name,
    fileSize: Data.tempFiles[0].size,
    type: 1
  })
  // aliOss 上传
  if (Data.resourcesActionType === '0') {
    attachFile.filePath = '/' + Data.dataForm.dir + Data.ossList[0].fileName
    upload(Data.resourcesUrl)
  }

  Data.attachFiles.push(attachFile)
  Data.ossList.splice(0, 1)
}

/**
 * 上传文件接口
 */
const upload = (url) => {
  const filePath = Data.ossList[0].dir + Data.ossList[0].fileName

  uni.uploadFile({
    url,
    header: {
      Authorization: uni.getStorageSync('cloudToken')
    },
    filePath: Data.tempFiles[0].path,
    name: 'file',
    formData: {
      policy: Data.dataForm.policy,
      signature: Data.dataForm.signature,
      ossaccessKeyId: Data.dataForm.ossaccessKeyId,
      dir: Data.dataForm.dir,
      host: Data.resourcesUrl,
      key: filePath
    },
    dataType: 'json',
    responseType: 'json',
    success: () => {
      emit('closeImgPop', Data.tempFilePaths[0], '/' + filePath)
    },
    fail: () => {
      uni.showToast({
        title: '请求失败',
        icon: 'none'
      })
    }
  })
}

// mioIo 上传文件
const mioIoUpload = () => {
  const params = {
    url: '/mall4cloud_biz/oss/upload_minio',
    filePath: Data.tempFilePaths[0],
    name: 'file',
    callBack: res => {
      const data = JSON.parse(res).data
      const filePath = data.dir + data.fileName
      Data.resourcesVal = Data.resourcesUrl + '/' + data.dir + data.fileName
      emit('closeImgPop', Data.tempFilePaths[0], '/' + filePath)
    }
  }
  http.upload(params)
}

/**
 * 关闭弹窗
 */
const close = () => {
  emit('closeImgPop')
}
</script>

<style lang="scss" scoped>
.component-img-box {
  position: fixed;
  top: 0;
  bottom: 0;
  display: block;
  width: 100%;
  height: 100%;
  z-index: 11;

  .mask {
    position: fixed;
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, .3);
  }

  .content {
    position: absolute;
    top: 30%;
    left: 50%;
    transform: translateX(-50%);
    display: block;
    width: 70%;
    height: auto;
    box-sizing: border-box;
    background: #fff;
  }

  .content .tit-top {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 30px;
    line-height: 30px;
    background: #eee;
    padding: 0 10px;
    box-sizing: border-box;
  }

  .content .tit-top .close {
    font-size: 24px;
    font-weight: 300;
    color: #aaa;
  }

  .content .upload {
    display: block;
    padding: 15px;
  }

  .content .upload .preview,
  .content .upload .upload-chequer {
    display: block;
    width: 120px;
    height: 120px;
    margin: 0 auto;
    margin-top: 10px;
    border: 1px dashed #ccc;
    border-radius: 4px;
  }

  .content .upload .preview .img {
    display: block;
    width: 100%;
    height: 100%;
  }

  .content .upload .upload-chequer {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .content .upload .upload-chequer .cross {
    font-size: 48px;
    font-weight: 200;
    color: #ccc;
  }

  .foot-btn {
    display: block;
    width: 100%;
    text-align: center;
    margin-top: 20px;
    margin-bottom: 10px;
  }

  .btn {
    display: inline-block;
    padding: 8px 15px;
    background: #1286f4;
    color: #fff;
    letter-spacing: 1px;
    border-radius: 3px;
  }

  .gray {
    background: #ddd;
  }
}
</style>
