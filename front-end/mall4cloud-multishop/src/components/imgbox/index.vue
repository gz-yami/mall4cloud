<template>
  <el-dialog
    v-model="visible"
    :title="$t('biz.imgbox.picManager')"
    class="component-imgbox"
    top="10vh"
    :append-to-body="visible"
  >
    <div class="file-group">
      <div class="upload-btn">
        <div class="up-btn">
          <el-button
            type="primary"
            plain
            class="el-icon-top"
            @click="uploadFileBtn"
          >
            上传图片
          </el-button>
          <el-button
            class="el-icon-plus"
            @click="selectOrCreateGroup(1)"
          >
            新建分组
          </el-button>
        </div>
        <!-- 搜索 -->
        <el-form
          :inline="true"
          :model="dataForm"
          class="demo-form-inline form"
        >
          <el-form-item>
            <el-input
              v-model="fileName"
              :placeholder="$t('biz.imgbox.picName')"
              clearable
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="searchImg"
            >
              {{ $t("biz.imgbox.query") }}
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="img-group-box">
        <!-- 左侧栏 -->
        <div class="left-sidebar">
          <div class="group-list">
            <div :class="['group-item', dataForm.attachFileGroupId === 0 ? 'active' : '']">
              <span
                class="group-name"
                @click="clickOneGroup(0)"
              >全部图片</span>
            </div>
            <div
              v-for="(gItem, gIdx) in groupList"
              :key="gIdx"
              :class="['group-item', dataForm.attachFileGroupId === gItem.attachFileGroupId ? 'active' : '']"
            >
              <span
                class="group-name"
                @click="clickOneGroup(gItem.attachFileGroupId)"
              >{{ gItem.name }}</span>
              <span class="sidebar-operate">
                <i
                  class="el-icon-edit"
                  @click="selectOrCreateGroup(1, gItem.attachFileGroupId, gIdx)"
                />
                <i
                  class="el-icon-delete"
                  @click="deleteFileGroup(gItem.attachFileGroupId)"
                />
              </span>
            </div>
          </div>
        </div>

        <!-- 图片列表 -->
        <div
          v-if="options.activeTab === 'pick'"
          class="pick-block"
          name="pick"
        >
          <div class="elx-main elx-img-list">
            <div
              v-if="isLoading"
              class="elx-img-list-loading"
            >
              <div class="el-icon-loading" />
            </div>
            <div
              v-for="(img, itemIndex) in imgRes"
              :key="itemIndex"
              class="img-item"
            >
              <div
                class="thumb-wp"
                @click="onClickListImage(img)"
              >
                <img
                  :src="(img.filePath).indexOf('http') === -1 ? resourcesUrl + img.filePath : img.filePath"
                  alt="img.name"
                >
              </div>
              <div
                class="title"
                @click="onClickListImage(img)"
              >
                {{ img.fileName }}
              </div>
              <div class="operate">
                <el-link
                  type="info"
                  class="edit"
                  @click="changeName(img)"
                >
                  {{ $t("table.edit") }}
                </el-link>
                <el-link
                  type="danger"
                  class="del"
                  @click="delectImg(img.fileId)"
                >
                  {{ $t("table.delete") }}
                </el-link>
              </div>
              <!-- <div class="label" v-if="img.label">{{img.label}}</div> -->
              <span
                v-if="img.selected"
                class="selected"
                @click="onClickListImage(img)"
              >
                <span class="icon el-icon-check" />
              </span>
            </div>
            <div
              v-if="!imgRes.length"
              class="empty"
            >
              暂无图片
            </div>
          </div>

          <el-pagination
            layout="total, prev, pager, next"
            :current-page="page.current"
            :page-size="page.size"
            :total="page.total"
            @current-change="onPageNumChange"
          />

          <div class="elx-foot">
            <el-badge
              v-if="!type"
              :value="images.length"
              class="item"
            >
              <el-button
                type="primary"
                :disabled="images.length == 0"
                @click="onConfirm"
              >
                {{ $t("table.confirm") }}
              </el-button>
            </el-badge>
            <el-button
              v-else
              type="primary"
              :disabled="disabled"
              @click="onConfirm"
            >
              {{ $t("table.confirm") }}
            </el-button>
            <el-button
              type="primary"
              plain
              @click="uploadFileBtn"
            >
              {{ $t("biz.imgbox.uploadPic") }}
            </el-button>
          </div>
        </div>

        <!-- 上传图片 -->
        <div
          v-if="options.activeTab === 'upload'"
          class="upload-block"
          name="upload"
        >
          <div class="elx-main">
            <div class="upload-title">
              {{ $t("biz.imgbox.selectLocalPic") }}
            </div>
            <el-upload
              ref="uploadRef"
              v-model:file-list="fileList"
              class="upload-img-preview"
              list-type="picture-card"
              accept="image/*"
              action=""
              :http-request="httpRequest"
              :multiple="true"
              :auto-upload="false"
              :limit="uploadNumberLimit()"
              :before-upload="beforeUpload"
              :on-success="onUploadSuccess"
              :on-error="onUploadError"
              :on-exceed="onUploadExceedTip"
            >
              <i class="el-icon-plus" />
            </el-upload>

            <div class="file-gropu-btn">
              <span
                v-if="groupForm.name"
                class="sel-group-name"
              >{{ groupForm.name }}</span><el-button
                type="primary"
                link
                @click="selectOrCreateGroup(0)"
              >
                选择分组
              </el-button>
              <el-button
                type="primary"
                link
                @click="selectOrCreateGroup(1)"
              >
                新建分组
              </el-button>
            </div>

            <div class="upload-tip">
              {{ uploadTips() }}
            </div>

            <div class="elx-foot">
              <el-button
                type="primary"
                @click="onUploadConfirm"
              >
                {{ $t("biz.imgbox.confirmUpload") }}
              </el-button>
            </div>
          </div>
        </div>
      </div>

      <!-- 选择/创建/修改分组 -->
      <el-dialog
        v-model="showGroupVisible"
        :title="selectGroup ? '选择分组' : createGroup ? '新建分组' : ''"
        :append-to-body="showGroupVisible"
      >
        <el-form
          ref="groupFormRef"
          :model="groupForm"
          label-width="90px"
          style="width:400px"
        >
          <el-form-item
            v-if="createGroup"
            label="分组名称"
            prop="name"
          >
            <el-input
              v-model="groupForm.name"
              placeholder="请输入分组名称"
            />
          </el-form-item>
          <el-form-item
            v-if="selectGroup"
            label="选择分组"
          >
            <el-select
              v-model="groupForm.attachFileGroupId"
              placeholder="请选择"
            >
              <el-option
                v-for="item in groupList"
                :key="item.attachFileGroupId"
                :label="item.name"
                :value="item.attachFileGroupId"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="showGroupVisible = false">
              {{ $t('table.cancel') }}
            </el-button>
            <el-button
              type="primary"
              @click="groupFormSubmit()"
            >
              {{ $t('table.confirm') }}
            </el-button>
          </div>
        </template>
      </el-dialog>
    </div>

    <!-- 修改图片名称 -->
    <el-dialog
      v-model:visible="changeNameVisible"
      :title="$t('biz.imgbox.revisePicName')"
      :close-on-click-modal="false"
      top="200px"
      :append-to-body="visible"
      width="600px"
    >
      <el-form
        v-if="changeNameVisible"
        ref="imgDataForm"
        :model="imageObject"
        label-width="80px"
      >
        <el-form-item :label="$t('biz.imgbox.oldName')">
          <span>{{ imageObject.fileName }}</span>
        </el-form-item>
        <el-form-item :label="$t('biz.imgbox.revName')">
          <el-input
            v-model="newImgName"
            :placeholder="$t('biz.imgbox.inputNewName')"
            clearable
          />
        </el-form-item>
        <el-form-item label="选择分组">
          <el-select
            v-model="groupForm.attachFileGroupId"
            placeholder="请选择"
          >
            <el-option
              v-for="item in groupList"
              :key="item.attachFileGroupId"
              :label="item.name"
              :value="item.attachFileGroupId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="changeNameVisible = false">{{ $t("table.cancel") }}</el-button>
          <el-button @click="submitImgName()">{{ $t("table.confirm") }}</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- /修改图片名称 -->
  </el-dialog>
</template>

<script setup>
import { reactive, toRefs } from 'vue'
import { ossInfo, pageFileGroup, saveFileGroup, updateFileGroup, deleteFileGroup as deleteFileGroupApi, aliImgUpdate, minIoImgUpdate } from '@/api/biz/oss'
import { save, page as pageApi, updateFileName, deleteFile } from '@/api/biz/attach-file'

const emit = defineEmits(['refreshPic'])

const Data = reactive({
  fileList: [],
  images: [], // 已选图片
  dataForm: {
    policy: '',
    signature: '',
    key: '',
    ossaccessKeyId: '',
    dir: '',
    host: '',
    attachFileGroupId: 0
  },
  groupList: [], // 分组列表
  attachFiles: [],
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  resourcesActionType: import.meta.env.VITE_APP_RESOURCES_TYPE,
  showGroupVisible: false, // 选择/创建分组弹窗
  selectGroup: false, // 选择分组
  createGroup: false, // 创建分组
  groupForm: {
    attachFileGroupId: '', // 选择分组的值
    name: '' // 分组名称
  },
  groupId: null,
  oriImgName: null, // 图片原名称
  rules: {
    name: [
      { required: true, message: '分组名称不能为空', trigger: 'blur' },
      { min: 0, max: 6, message: '长度在1-6个字符', trigger: 'blur' }
    ],
    attachFileGroupId: [
      { required: true, message: '请选择分组', trigger: 'change' }
    ]
  },
  options: {
    multiple: true, // 是否支持选取多个图片
    limit: 20, // 最多可选择图片数量
    maxSize: 2, // 最大尺寸（M）
    activeTab: 'pick',
    enableUpload: false, // 是否启用图片上传
    callback: null
  },
  newImgName: '',
  ossList: null,
  isTrue: true,
  imageObject: null,
  changeNameVisible: false,
  type: 2,
  fileName: '',
  listType: 'picture',
  isLoading: true,
  visible: false,
  disabled: false,
  notImg: false,
  uploadSuccessCount: 0,
  imgRes: [],
  page: {
    current: 1,
    size: 15,
    total: 0
  }
})

const { fileList, images, dataForm, groupList, resourcesUrl, showGroupVisible, selectGroup, createGroup, groupForm, options, newImgName, imageObject, changeNameVisible, type, fileName, isLoading, visible, disabled, imgRes, page } = toRefs(Data)

const uploadRef = ref()
/**
 * 初始化
 * type 1：单图  2：多图
 */
const init = (type, limit) => {
  Data.visible = true
  Data.isLoading = false
  Data.images = []
  Data.type = type
  if (type === 1) {
    Data.type = true
    Data.disabled = true
  } else {
    Data.type = false
    Data.limit = limit
  }
  Data.fileList = []
  // 获取产品数据 - 第一页的数据
  Data.page.current = 1
  loadImageGroup() // 分组
  loadListImage() // 图片列表
}

/**
 * 加载图片分组列表
 */
const loadImageGroup = () => {
  pageFileGroup().then((data) => {
    Data.groupList = data
  })
}

/**
 * 点击图片分组item
 */
const clickOneGroup = (id) => {
  Data.dataForm.attachFileGroupId = id
  Data.options.activeTab = 'pick'
  loadListImage()
}

/**
 * 打开选择/新建分组
 */
const selectOrCreateGroup = (st, groupId, gIdx) => {
  if (!groupId) {
    Data.groupForm.attachFileGroupId = null
    Data.groupForm.name = null
  }
  if (st === 0) { // 选择分组
    if (!Data.groupList.length) {
      ElMessage({
        message: '当前暂无分组可选择，请先创建分组',
        duration: 1500
      })
      Data.showGroupVisible = false
      return
    } else {
      Data.selectGroup = true
      Data.createGroup = false
    }
  } else if (st === 1) { // 编辑/创建分组
    Data.createGroup = true
    Data.selectGroup = false
    if (groupId) {
      Data.groupForm.name = Data.groupList[gIdx].name
      Data.groupForm.attachFileGroupId = groupId
      Data.groupId = groupId
    }
  }
  Data.showGroupVisible = true
}

const groupFormRef = ref()
/**
 * 提交选择/创建/修改分组
 */
const groupFormSubmit = () => {
  groupFormRef.value.validate(valid => {
    if (!valid) {
      return
    }
    if (Data.selectGroup) {
      if (!Data.groupForm.attachFileGroupId) {
        ElMessage({
          message: '请选择分组',
          type: 'warning',
          duration: 1000
        })
        return
      }
      Data.groupList.forEach(el => {
        if (Data.groupForm.attachFileGroupId == el.attachFileGroupId) {
          Data.groupForm.name = el.name
        }
      })
      Data.showGroupVisible = false
      return
    }
    if (Data.createGroup) {
      if (!Data.groupForm.name) {
        ElMessage({
          message: '分组名称不能为空',
          type: 'warning',
          duration: 1000
        })
      }
      const param = {
        attachFileGroupId: Data.groupForm.attachFileGroupId,
        name: Data.groupForm.name
      }
      const request = Data.groupId ? updateFileGroup(param) : saveFileGroup(param)
      request.then(() => {
        ElMessage({
          message: $t('table.actionSuccess'),
          type: 'success',
          duration: 1500,
          onClose: () => {
            Data.showGroupVisible = false
            loadImageGroup()
          }
        })
      })
    }
  })
}

/**
 * 删除分组
 */
const deleteFileGroup = (groupId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => {
    const data = {
      attachFileGroupId: groupId
    }
    deleteFileGroupApi(data).then(() => {
      ElMessage({
        message: $t('table.actionSuccess'),
        type: 'success',
        duration: 1500,
        onClose: () => {
          loadImageGroup()
          searchImg()
        }
      })
    })
  })
}

/**
 * 加载图片列表数据
 * @param page
 */
const loadListImage = () => {
  Data.isLoading = true
  const param = {
    pageNum: Data.page.current ? Data.page.current : 1,
    pageSize: Data.page.size,
    fileName: Data.fileName ? Data.fileName : null,
    fileGroupId: Data.dataForm.attachFileGroupId || 0
  }
  pageApi(param).then(response => {
    Data.imgRes = response.list
    Data.page.pages = response.pages
    Data.page.total = response.total
    Data.isLoading = false
  })
}

const onConfirm = () => {
  if (Data.type) {
    emit('refreshPic', Data.images[0].filePath)
  } else {
    const imgPaths = Data.images.map(file => {
      return file.filePath
    }).join(',')
    emit('refreshPic', imgPaths)
  }
  Data.visible = false
}

/**
 * 修改图片名称
 */
const changeName = (img) => {
  Data.newImgName = ''
  Data.imageObject = img
  Data.isTrue = true
  Data.changeNameVisible = true
  Data.oriImgName = img.fileName
  Data.groupList.forEach(el => {
    if (img.attachFileGroupId == el.attachFileGroupId) {
      Data.groupForm.name = el.name
      Data.groupForm.attachFileGroupId = el.attachFileGroupId
    } else {
      Data.groupForm.name = null
      Data.groupForm.attachFileGroupId = null
    }
  })
}

/**
 * 提交修改后的图片名称
 */
const submitImgName = () => {
  if (!Data.isTrue) {
    return false
  }
  Data.isTrue = false
  const data = {
    fileId: Data.imageObject.fileId,
    fileName: Data.newImgName ? Data.newImgName : Data.oriImgName,
    attachFileGroupId: Data.groupForm.attachFileGroupId
  }
  updateFileName(data).then(() => {
    Data.newImgName = ''
    Data.isTrue = true
    Data.changeNameVisible = false
    loadListImage()
  }).catch(() => {
    Data.isTrue = true
  })
}

/**
 * 删除图片
 */
const delectImg = (fileId) => {
  ElMessageBox.confirm($t('table.sureToDelete'), $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(() => {
    const data = {
      fileId
    }
    deleteFile(data).then(() => {
      Data.images = []
      searchImg()
    })
  })
}

/**
 * 点击上传图片按钮
 */
const uploadFileBtn = () => {
  Data.options.activeTab = 'upload'
  Data.dataForm.attachFileGroupId = null
  Data.fileList = []
}

const { proxy } = getCurrentInstance()
/**
 * 点击图片时选中或取消选中图片
 * @param img object
 */
const onClickListImage = (img) => {
  // 单选图片
  if (Data.type) {
    clearListSelected()
    Data.images = []
    Data.disabled = false
  } else {
    // 多选图片-如果已选中则取消选中
    const imgIndex = selectedImageIndex(img)
    if (imgIndex >= 0) {
      // 取消图片已选状态
      img.selected = false
      Data.images.splice(imgIndex, 1)
      return
    }
  }
  if (!Data.type && Data.images.length >= Data.limit) {
    ElMessage($t('biz.imgbox.superiorLimit'))
    return false
  }
  Data.images.push(JSON.parse(JSON.stringify(img)))
  img.selected = true
  proxy.$forceUpdate()
}

/**
 * 清除所有已点击图片样式
 */
const clearListSelected = () => {
  if (Data.type) {
    const list = Data.imgRes
    list.forEach(element => {
      element.selected = false
    })
  }
}

/**
 * 按图片名称搜索图片
 */
const searchImg = () => {
  Data.page.current = 1
  loadListImage()
}

/**
 * 图片已选则返回下标，未选则返回-1
 */
const selectedImageIndex = (img) => {
  for (let i = 0; i < Data.images.length; i++) {
    const selectedImg = Data.images[i]
    if (selectedImg.fileId === img.fileId) {
      return i
    }
  }
  return -1
}

/**
 * 分页页面变化时刷新数据
 * @param page
 */
const onPageNumChange = (page) => {
  Data.page.current = page
  loadListImage()
}

/**
 * 获取上传图片数据
 */
const onUploadConfirm = () => {
  const fileNum = Data.fileList.length

  ossInfo(fileNum).then(response => {
    if (Data.resourcesActionType === '0') {
      Data.dataForm.policy = response.policy
      Data.dataForm.signature = response.signature
      Data.dataForm.ossaccessKeyId = response.accessid
      Data.dataForm.dir = response.dir
    }
    Data.ossList = response.ossList
    Data.notImg = true
    nextTick(() => {
      uploadRef.value.submit()
    })
  })
}
/**
 * 上传图片
 */
const httpRequest = (event) => {
  const file = event.file
  const typeArray = file.type.split('/')
  const attachFile = Object.assign({
    fileType: typeArray[1],
    fileName: file.name,
    fileSize: file.size,
    type: 1
  })

  if (Data.ossList.length <= 0) {
    ElMessage('数据异常，请刷洗后重试')
  }

  // aliOss 上传
  if (Data.resourcesActionType === '0') {
    attachFile.filePath = '/' + Data.dataForm.dir + Data.ossList[0].fileName
    const formdata = new FormData()
    formdata.append('policy', Data.dataForm.policy)
    formdata.append('signature', Data.dataForm.signature)
    formdata.append('ossaccessKeyId', Data.dataForm.ossaccessKeyId)
    formdata.append('dir', Data.dataForm.dir)
    formdata.append('host', Data.resourcesUrl)
    formdata.append('key', Data.ossList[0].dir + Data.ossList[0].fileName)
    formdata.append('file', file)
    aliImgUpdate(Data.resourcesUrl, formdata).then(() => {
    }).catch(error => {
      if (!error) {
        event.onError()
      }
    })
  } else { // mioIo 上传
    attachFile.filePath = '/' + Data.ossList[0].dir + Data.ossList[0].fileName
    minIoImgUpdate(Data.ossList[0].actionUrl, file).then(() => {
    })
  }
  Data.attachFiles.push(attachFile)
  Data.ossList.splice(0, 1)
  if (Data.ossList <= 0) {
    event.onSuccess()
  }
}

/**
  * 上传图片前检查合法性
  * @param file
  * @returns {boolean}
  */
const beforeUpload = (file) => {
  const typeArray = file.type.split('/')
  if (typeArray[0] !== 'image') {
    if (Data.notImg) {
      ElMessage($t('biz.imgbox.onlyPictures'), true)
      Data.notImg = false
    }
    return false
  }
  const isSize = file.size / (1024 * 1024) < 2
  if (!isSize) {
    ElMessage(Data.uploadSizeTip())
    return false
  }

  return true
}

const uploadNumberLimit = () => {
  if (!Data.options.multiple) {
    return 1
  }

  return Data.options.limit - Data.images.length
}

const uploadTypeTip = () => {
  return $t('biz.imgbox.onlySupported') + ' jpg/png/gif ' + $t('biz.imgbox.pic')
}

const uploadSizeTip = () => {
  return $t('biz.imgbox.notExceed') + '2M'
}

const uploadTips = () => {
  const tips = [uploadTypeTip(), uploadSizeTip()]

  if (!Data.options.multiple) {
    return tips.join('，')
  }

  if (Data.images.length > 0) {
    tips.push($t('biz.imgbox.alreadyExist') + Data.images.length + $t('biz.imgbox.unit'))
  }

  const uploadFileNum = Data.fileList ? Data.fileList.length : 0
  if (uploadFileNum > 0) {
    tips.push($t('biz.imgbox.soonUpload') + uploadFileNum + $t('biz.imgbox.unit'))
  }

  tips.push($t('biz.imgbox.remainder') + (Data.options.limit - Data.images.length - uploadFileNum) + $t('biz.imgbox.unit') + $t('biz.imgbox.upload'))

  return tips.join('，')
}

/**
  * 上传错误处理
  * @param err
  * @param file
  * @param fileList
  */
const onUploadError = (err, file, fileList) => {
  ElMessage($t('biz.imgbox.requestError'), true)
  throw err
}

/**
  * 上传成功处理，并提交图片数据
  * @param response
  * @param file
  * @param fileList
  * @returns {boolean}
  */
const onUploadSuccess = (response, file, fileList) => {
  const filesNames = []
  fileList.forEach(file => {
    filesNames.push(file.name)
  })
  const uploadFile = []
  Data.attachFiles.forEach(file => {
    if (filesNames.indexOf(file.fileName) !== -1) {
      file.attachFileGroupId = Data.groupForm.attachFileGroupId
      uploadFile.push(file)
    }
  })
  save(uploadFile).then(response => {
    Data.attachFiles = []
    Data.options.activeTab = 'pick'
    Data.dataForm.attachFileGroupId = 0
    loadListImage()
  })
  Data.disabled = true
  Data.fileList = []
  Data.page.current = 1
}

/**
* 选择上传文件超过限制文件个数提示
*/
const onUploadExceedTip = () => {
  ElMessage($t('biz.imgbox.maxSelect') + uploadNumberLimit() + $t('biz.imgbox.unit') + $t('biz.imgbox.upload'))
}

defineExpose({
  init
})

</script>

<style lang="scss" scoped>
.component-imgbox {
  $bg: #f6f6f6;
  // top: 100px;

  .elx-foot {
    padding: 15px 0 0 10px;
  }

  .file-group {
    padding: 20px 0;

    .upload-btn {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 10px;
      margin-bottom: 15px;
    }

    .img-group-box {
      display: flex;

      // 左侧栏
      .left-sidebar {
        display: block;
        height: 620px;
        font-size: 13px;
        color: #666;

        .group-list {
          height: 100%;
          border-right: 1px solid #f2f3f5;
          overflow: auto;

          .group-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 190px;
            padding: 0 10px;
            height: 38px;
            line-height: 38px;
            text-align: left;
            box-sizing: border-box;

            .group-name {
              margin-right: 6px;
              min-width: 100px;
              max-width: 118px;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              cursor: pointer;
            }

            .sidebar-operate {

              // display: none;
              .el-icon-delete,
              .el-icon-edit {
                margin-left: 10px;
                cursor: pointer;
              }
            }
          }

          .group-item:hover,
          .group-item.active {
            background: #f2f3f5;
            // .sidebar-operate {
            //   display: block;
            // }
          }
        }
      }

      // 图片列表
      .pick-block {
        position: relative;
        width: 620px;
        height: 620px;

        .elx-img-list-loading {
          position: absolute;
          // top: 60px;
          left: 0;
          right: 0;
          z-index: 9;
          width: 100%;
          height: 520px;
          // 加载层高度
          background: #fff;
          text-align: center;

          .el-icon-loading {
            font-size: 50px;
            color: #409eff;
            line-height: 460px;
          }
        }

        .elx-img-list {
          padding: 10px;
          // 图片列表高度
          height: 520px;

          .img-item {
            $imgSize: 100px;
            $size: 104px;
            float: left;
            margin: 10px;
            width: $imgSize;
            cursor: pointer;
            position: relative;
            font-size: 12px;

            img {
              width: $imgSize;
              height: $imgSize;
              display: block;
            }

            .title {
              line-height: 24px;
              height: 24px;
              display: block;
              overflow: hidden;
              background: $bg;
              padding: 0 5px;
            }

            .title {
              line-height: 24px;
              height: 24px;
              display: block;
              overflow: hidden;
              background: $bg;
              padding: 0 5px;
            }

            .operate {
              line-height: 24px;
              height: 24px;
              display: block;
              overflow: hidden;
              // background: $bg;
              margin-top: 2px;
              padding: 0 5px;

              .edit {
                float: left;
                padding-left: 5px;
              }

              .del {
                float: right;
                padding-right: 5px;
              }
            }

            .selected {
              position: absolute;
              right: -3px;
              top: -3px;
              width: $size;
              height: 130px;
              border: 3px solid #409eff;
              border-radius: 3px;
              text-align: right;

              .icon {
                background: #409eff;
                text-align: center;
                height: 24px;
                width: 24px;
                line-height: 24px;
                display: inline-block;
                font-size: 16px;
                color: #fff;
                border-radius: 0 0 0 3px;
                position: absolute;
                right: 0;
                top: 0;
              }
            }
          }

          &::after {
            content: " ";
            display: table;
            height: 0;
            clear: both;
            visibility: hidden;
          }

          .empty {
            display: block;
            width: 100%;
            margin-top: 150px;
            color: #bbb;
            text-align: center;
          }
        }

      }
    }
  }

  .upload-block {
    .upload-img-preview {
      padding: 20px;

      .el-upload-list--picture-card .el-upload-list__item-status-label i {
        margin-top: 12px;
        vertical-align: top;
      }
    }

    .upload-tip {
      padding: 0 20px;
      font-size: 13px;
      color: #999;
    }

    .upload-title {
      font-size: 16px;
      color: #666;
      padding: 20px 0 0 20px;
    }

    .file-gropu-btn {
      padding: 0 20px;
      margin-bottom: 10px;

      .sel-group-name {
        margin-right: 10px;
      }
    }
  }
}

.form {
  // padding: 15px 0px 0px 20px;
  margin-left: 50px;
}
</style>
