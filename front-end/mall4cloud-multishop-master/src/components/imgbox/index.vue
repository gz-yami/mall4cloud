<template>
  <div class="elx-imgbox">
    <el-dialog
      :title="$t('biz.imgbox.picManager')"
      :visible.sync="visible"
      class="elx-imgbox-dialog"
      top="10vh"
      :append-to-body="visible"
    >
      <div class="file-group">
        <div class="upload-btn">
          <div class="up-btn">
            <el-button type="primary" plain size="medium" class="el-icon-top" @click="uploadFileBtn">上传图片</el-button>
            <el-button size="medium" @click="selectOrCreateGroup(1)" class="el-icon-plus">新建分组</el-button>
          </div>
          <!-- 搜索 -->
          <el-form :inline="true" :model="dataForm" class="demo-form-inline form">
              <el-form-item>
                <el-input
                  v-model="fileName"
                  size="medium"
                  :placeholder="$t('biz.imgbox.picName')"
                  clearable
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  @click="searchImg"
                  icon="el-icon-search"
                  size="medium"
                >{{$t("biz.imgbox.query")}}</el-button>
              </el-form-item>
            </el-form>
        </div>
        <div class="img-group-box">
          <!-- 左侧栏 -->
          <div class="left-sidebar">
            <div class="group-list">
              <div :class="['group-item', dataForm.attachFileGroupId===0?'active':'']">
                <span class="group-name" @click="clickOneGroup(0)">全部图片</span>
              </div>
              <div :class="['group-item', dataForm.attachFileGroupId===gItem.attachFileGroupId?'active':'']" v-for="(gItem, gIdx) in groupList" :key="gIdx">
                <span class="group-name" @click="clickOneGroup(gItem.attachFileGroupId)">{{ gItem.name }}</span>
                <span class="sidebar-operate">
                  <i class="el-icon-edit" @click="selectOrCreateGroup(1,gItem.attachFileGroupId,gIdx)"></i>
                  <i class="el-icon-delete" @click="deleteFileGroup(gItem.attachFileGroupId)"></i>
                </span>
              </div>
            </div>
          </div>

          <!-- 图片列表 -->
          <div v-if="options.activeTab==='pick'" class="pick-block" name="pick">
            <div class="elx-main elx-img-list">
              <div class="elx-img-list-loading" v-if="isLoading">
                <div class="el-icon-loading"></div>
              </div>
              <div class="img-item" v-for="(img, itemIndex) in imgRes" :key="itemIndex">
                <div class="thumb-wp" @click="onClickListImage(img)">
                  <img :src="(img.filePath).indexOf('http')===-1 ? resourcesUrl + img.filePath : img.filePath" alt="img.name" />
                </div>
                <div class="title" @click="onClickListImage(img)">{{img.fileName}}</div>
                <div class="operate">
                  <el-link type="info" class="edit" @click="changeName(img)">{{$t("table.edit")}}</el-link>
                  <el-link
                    type="danger"
                    class="del"
                    @click="delectImg(img.fileId)"
                  >{{$t("table.delete")}}</el-link>
                </div>
                <!-- <div class="label" v-if="img.label">{{img.label}}</div> -->
                <span class="selected" v-if="img.selected" @click="onClickListImage(img)">
                  <span class="icon el-icon-check"></span>
                </span>
              </div>
              <div class="empty" v-if="!imgRes.length">暂无图片</div>
            </div>

            <el-pagination
              layout="total, prev, pager, next"
              :current-page="page.current"
              :page-size="page.size"
              :total="page.total"
              @current-change="onPageNumChange"
            ></el-pagination>

            <div class="elx-foot">
              <el-badge :value="images.length" class="item" v-if="!type">
                <el-button
                  type="primary"
                  size="medium"
                  :disabled="images.length == 0"
                  @click="onConfirm"
                >{{$t("table.confirm")}}</el-button>
              </el-badge>
              <el-button
                type="primary"
                size="medium"
                :disabled="disabled"
                @click="onConfirm"
                v-else
              >{{$t("table.confirm")}}</el-button>
              <el-button
                type="primary"
                size="medium"
                @click="uploadFileBtn"
                plain
              >{{$t("biz.imgbox.uploadPic")}}</el-button>
            </div>
          </div>

          <!-- 上传图片 -->
          <div v-if="options.activeTab==='upload'" class="upload-block" name="upload">
            <div class="elx-main">
              <div class="upload-title">{{$t("biz.imgbox.selectLocalPic")}}</div>
              <el-upload
                ref="upload"
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
                <i class="el-icon-plus"></i>
              </el-upload>

              <div class="file-gropu-btn">
                <span v-if="groupForm.name" class="sel-group-name">{{groupForm.name}}</span><el-button type="text" size="medium" @click="selectOrCreateGroup(0)">选择分组</el-button>
                <el-button type="text" size="medium" @click="selectOrCreateGroup(1)">新建分组</el-button>
              </div>

              <div class="upload-tip">{{ uploadTips() }}</div>

              <div class="elx-foot">
                <el-button
                  type="primary"
                  size="medium"
                  @click="onUploadConfirm"
                >{{$t("biz.imgbox.confirmUpload")}}</el-button>
              </div>
            </div>
          </div>

        </div>

        <!-- 选择/创建/修改分组 -->
        <el-dialog :title="selectGroup?'选择分组':createGroup?'新建分组':''" :visible.sync="showGroupVisible" :append-to-body="showGroupVisible">
          <el-form :model="groupForm" ref="groupForm" label-width="90px" style="width:400px">
            <el-form-item v-if="createGroup" label="分组名称" prop="name">
              <el-input placeholder="请输入分组名称" v-model="groupForm.name"></el-input>
            </el-form-item>
            <el-form-item v-if="selectGroup" label="选择分组">
              <el-select v-model="groupForm.attachFileGroupId" placeholder="请选择">
                <el-option
                  v-for="item in groupList"
                  :key="item.attachFileGroupId"
                  :label="item.name"
                  :value="item.attachFileGroupId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="showGroupVisible = false">
              {{ $t('table.cancel') }}
            </el-button>
            <el-button type="primary" @click="groupFormSubmit()">
              {{ $t('table.confirm') }}
            </el-button>
          </div>
        </el-dialog>
      </div>

      <!-- 修改图片名称 -->
      <el-dialog
        :title="$t('biz.imgbox.revisePicName')"
        :close-on-click-modal="false"
        :visible.sync="changeNameVisible"
        top="200px"
        :append-to-body="visible"
        width="600px"
      >
        <el-form :model="imageObject" ref="imgDataForm" label-width="80px" v-if="changeNameVisible">
          <el-form-item :label="$t('biz.imgbox.oldName')">
            <span>{{imageObject.fileName}}</span>
          </el-form-item>
          <el-form-item :label="$t('biz.imgbox.revName')">
            <el-input
              :placeholder="$t('biz.imgbox.inputNewName')"
              v-model="newImgName"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="选择分组">
            <el-select v-model="groupForm.attachFileGroupId" placeholder="请选择">
              <el-option
                v-for="item in groupList"
                :key="item.attachFileGroupId"
                :label="item.name"
                :value="item.attachFileGroupId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="changeNameVisible = false">{{$t("table.cancel")}}</el-button>
          <el-button @click="submitImgName()">{{$t("table.confirm")}}</el-button>
        </span>
      </el-dialog>
      <!-- /修改图片名称 -->

    </el-dialog>

  </div>
</template>

<script type="text/babel">
import { ossInfo, pageFileGroup, saveFileGroup, updateFileGroup, deleteFileGroup, aliImgUpdate, minIoImgUpdate } from '@/api/biz/oss'
import { save, page, updateFileName, deleteFile } from '@/api/biz/attach-file'
import { getUUID } from '@/utils/index'
export default {
  name: 'ElxImgbox',
  data() {
    return {
      images: [], // 已选图片
      dataForm: {
        policy: '',
        signature: '',
        key: '',
        ossaccessKeyId: '',
        dir: '',
        host: '',
        attachFileGroupId: 0,
      },
      groupList: [], // 分组列表
      attachFiles: [],
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
      resourcesActionType:process.env.VUE_APP_RESOURCES_TYPE,
      showGroupVisible: false, // 选择/创建分组弹窗
      selectGroup: false, // 选择分组
      createGroup: false, // 创建分组
      groupForm: {
        attachFileGroupId: '', // 选择分组的值
        name: '', // 分组名称
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
    }
  },

  methods: {
    /**
     * 初始化
     * type 1：单图  2：多图
     */
    init(type, limit) {
      this.visible = true
      this.isLoading = false
      this.images = []
      this.type = type
      if (type === 1) {
        this.type = true
        this.disabled = true
      } else {
        this.type = false
        this.limit = limit
      }
      if (this.$refs.upload) {
        this.$refs.upload.uploadFiles = []
      }
      // 获取产品数据 - 第一页的数据
      this.page.current = 1
      this.loadImageGroup() // 分组
      this.loadListImage() // 图片列表
    },
    show() {
      this.visible = true
    },

    hide() {
      this.visible = false
    },

    /**
     * 加载图片分组列表
     */
    loadImageGroup() {
      pageFileGroup().then((data) => {
        this.groupList = data
      })
    },
    /**
     * 点击图片分组item
     */
    clickOneGroup(id) {
      this.dataForm.attachFileGroupId = id
      this.options.activeTab = 'pick'
      this.loadListImage()
    },
    /**
     * 打开选择/新建分组
     */
    selectOrCreateGroup(st, groupId, gIdx) {
      if (!groupId) {
        this.groupForm.attachFileGroupId = null
        this.groupForm.name = null
      }
      if (st === 0) { // 选择分组
        if (!this.groupList.length) {
          this.$message({
            message: '当前暂无分组可选择，请先创建分组',
            duration: 1500
          })
          this.showGroupVisible = false
          return
        } else {
          this.selectGroup = true
          this.createGroup = false
        }
      } else if (st === 1) { // 编辑/创建分组
        this.createGroup = true
        this.selectGroup = false
        if (groupId) {
          this.groupForm.name = this.groupList[gIdx].name
          this.groupForm.attachFileGroupId = groupId
          this.groupId = groupId
        }
      }
      this.showGroupVisible = true
    },
    /**
     * 提交选择/创建/修改分组
     */
    groupFormSubmit() {
      this.$refs.groupForm.validate(valid => {
        if (!valid) {
          return
        }
        if (this.selectGroup) {
          if (!this.groupForm.attachFileGroupId) {
            this.$message({
              message: '请选择分组',
              type: 'warning',
              duration: 1000
            })
            return
          }
          this.groupList.forEach(el => {
            if (this.groupForm.attachFileGroupId == el.attachFileGroupId) {
              this.groupForm.name = el.name
            }
          })
          this.showGroupVisible = false
          return
        }
        if (this.createGroup) {
          if (!this.groupForm.name) {
            this.$message({
              message: '分组名称不能为空',
              type: 'warning',
              duration: 1000
            })
          }
          var param = {
            attachFileGroupId: this.groupForm.attachFileGroupId,
            name: this.groupForm.name
          }
          const request = this.groupId ? updateFileGroup(param) : saveFileGroup(param)
          request.then(data => {
            this.$message({
              message: this.$t('table.actionSuccess'),
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.showGroupVisible = false
                this.loadImageGroup()
              }
            })
          })
          return
        }
      })
    },
    /**
     * 删除分组
     */
    deleteFileGroup(groupId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => {
        const data = {
          attachFileGroupId: groupId
        }
        deleteFileGroup(data).then((delData) => {
          this.$message({
            message: this.$t('table.actionSuccess'),
            type: 'success',
            duration: 1500,
            onClose: () => {
              this.loadImageGroup()
              this.searchImg()
            }
          })
        })
      })
    },

    /**
     * 加载图片列表数据
     * @param page
     */
    loadListImage() {
      this.isLoading = true
      const param = {
        pageNum: this.page.current ? this.page.current : 1,
        pageSize: this.page.size,
        fileName: this.fileName ? this.fileName : null,
        fileGroupId: this.dataForm.attachFileGroupId || 0
      }
      page(param).then(response => {
        this.imgRes = response.list
        this.page.pages = response.pages
        this.page.total = response.total
        this.isLoading = false
      })
    },

    onConfirm() {
      if (this.type) {
        this.$emit('refreshPic', this.images[0].filePath)
      } else {
        let imgPaths = this.images.map(file => {
          return file.filePath
        }).join(',')
        this.$emit('refreshPic', imgPaths)
      }
      this.visible = false
    },
    /**
     * 修改图片名称
     */
    changeName(img) {
      this.newImgName = ''
      this.imageObject = img
      this.isTrue = true
      this.changeNameVisible = true
      this.oriImgName = img.fileName
      this.groupList.forEach(el => {
        if (img.attachFileGroupId == el.attachFileGroupId) {
          this.groupForm.name = el.name
          this.groupForm.attachFileGroupId = el.attachFileGroupId
          return
        } else {
          this.groupForm.name = null
          this.groupForm.attachFileGroupId = null
        }
      })
    },
    /**
     * 提交修改后的图片名称
     */
    submitImgName() {
      if (!this.isTrue) {
        return false
      }
      this.isTrue = false
      const data = {
        fileId: this.imageObject.fileId,
        fileName: this.newImgName ? this.newImgName : this.oriImgName,
        attachFileGroupId: this.groupForm.attachFileGroupId
      }
      updateFileName(data).then(({ data }) => {
        this.newImgName = ''
        this.isTrue = true
        this.changeNameVisible = false
        this.loadListImage()
      }).catch(({ e }) => {
        this.isTrue = true
      })
    },
    /**
     * 删除图片
     */
    delectImg(fileId) {
      this.$confirm(this.$t('table.sureToDelete'), this.$t('table.tips'), {
        confirmButtonText: this.$t('table.confirm'),
        cancelButtonText: this.$t('table.cancel'),
        type: 'warning'
      }).then(() => {
        // this.$http({
        //   url: this.$http.adornUrl('/admin/file/deleteFile/' + fileId),
        //   method: 'delete'
        // }).then(({ data }) => {
        //   this.images = []
        //   this.loadListImage()
        // })
        const data = {
          fileId: fileId
        }
        deleteFile(data).then((data) => {
          this.images = []
          this.searchImg()
        })
      })
    },

    /**
     * 点击上传图片按钮
     */
    uploadFileBtn() {
      this.options.activeTab = 'upload'
      this.dataForm.attachFileGroupId = null
    },

    /**
     * 点击图片时选中或取消选中图片
     * @param img object
     */
    onClickListImage(img) {
      console.log(img)
      if (this.type) {
        this.clearListSelected()
        this.images = []
        this.disabled = false
      } else {
        var imgIndex = this.selectedImageIndex(img)
          // 取消图片已选状态
          img.selected = false
          this.images.splice(imgIndex, 1)
      }
      if (!this.type && this.images.length >= this.limit) {
        this.message(this.$t('biz.imgbox.superiorLimit'))
        return false
      }
      this.images.push(JSON.parse(JSON.stringify(img)))
      img.selected = true
      this.$forceUpdate()
    },
    /**
     * 清除所有已点击图片样式
     */
    clearListSelected() {
      if (this.type) {
        let list = this.imgRes
        list.forEach(element => {
          element.selected = false
        })
      }
    },
    /**
     * 按图片名称搜索图片
     */
    searchImg() {
      this.page.current = 1
      this.loadListImage()
    },

    /**
     * 图片已选则返回下标，未选则返回-1
     */
    selectedImageIndex(img) {
      for (let i = 0; i < this.images.length; i++) {
        var selectedImg = this.images[i]
        if (selectedImg.fileId === img.fileId) {
          return i
        }
      }
      return -1
    },

    /**
     * 分页页面变化时刷新数据
     * @param page
     */
    onPageNumChange(page) {
      this.page.current = page
      this.loadListImage()
    },

    /**
     * 获取上传图片数据
     */
    onUploadConfirm() {
      let fileNum = this.$refs.upload.uploadFiles.length
      ossInfo(fileNum).then(response => {
        console.log('选择图片response：', response)
        if (this.resourcesActionType === '0') {
          this.dataForm.policy = response.policy
          this.dataForm.signature = response.signature
          this.dataForm.ossaccessKeyId = response.accessid
          this.dataForm.dir = response.dir
        }
        this.ossList = response.ossList
        this.notImg = true
        this.$nextTick(() => {
          this.$refs.upload.submit()
        })
      })
    },
    /**
     * 上传图片
     */
    httpRequest(event) {
      console.log('上传图片event:', event)
      
      let file = event.file
      let typeArray = file.type.split('/')
      const attachFile = Object.assign({
        fileType: typeArray[1],
        fileName: file.name,
        fileSize: file.size,
        type: 1,
      })

      if (this.ossList.length <= 0) {
        this.message('数据异常，请刷洗后重试')
      }

      // aliOss 上传
      if (this.resourcesActionType === '0') {
        attachFile.filePath = '/' + this.dataForm.dir + this.ossList[0].fileName
        const formdata = new FormData();
        formdata.append('policy', this.dataForm.policy);
        formdata.append('signature', this.dataForm.signature);
        formdata.append('ossaccessKeyId', this.dataForm.ossaccessKeyId);
        formdata.append('dir', this.dataForm.dir);
        formdata.append('host', this.resourcesUrl);
        formdata.append('key', this.ossList[0].dir + this.ossList[0].fileName);
        formdata.append('file', file);
        aliImgUpdate(this.resourcesUrl, formdata).then(data => {
        }).catch(error => {
          if (!error) {
            event.onError()
          }
        })
      }
      // mioIo 上传
      else {
        attachFile.filePath = '/' + this.ossList[0].dir + this.ossList[0].fileName
        console.log('mioIo 上传 file:', file)
        minIoImgUpdate(this.ossList[0].actionUrl, file).then(data => {
        })
      }
      this.attachFiles.push(attachFile)
      this.ossList.splice(0, 1)
      if (this.ossList <= 0) {
        event.onSuccess()
      }
    },

    /**
     * 上传图片前检查合法性
     * @param file
     * @returns {boolean}
     */
    beforeUpload(file) {
      let typeArray = file.type.split('/')
      if (typeArray[0] !== 'image') {
        if (this.notImg) {
          this.message(this.$t('biz.imgbox.onlyPictures'), true)
          this.notImg = false
        }
        return false
      }
      const isSize = file.size / (1024 * 1024) < 2
      if (!isSize) {
        this.message(this.uploadSizeTip())
        return false
      }
      // this.dataForm.key = this.dataForm.dir + getUUID()
      // const attachFile = Object.assign({
      //   filePath: '/' + this.dataForm.key,
      //   fileType: typeArray[1],
      //   fileName: file.name,
      //   fileSize: file.size,
      //   type: 1,
      // })
      // this.attachFiles.push(attachFile)
      return true
    },

    uploadNumberLimit() {
      if (!this.options.multiple) {
        return 1
      }

      return this.options.limit - this.images.length
    },

    uploadTypeTip() {
      return this.$t('biz.imgbox.onlySupported') + ' jpg/png/gif ' + this.$t('biz.imgbox.pic')
    },

    uploadSizeTip() {
      return this.$t('biz.imgbox.notExceed') + '2M'
    },

    uploadTips() {
      let tips = [this.uploadTypeTip(), this.uploadSizeTip()]

      if (!this.options.multiple) {
        return tips.join('，')
      }

      if (this.images.length > 0) {
        tips.push(this.$t('biz.imgbox.alreadyExist') + this.images.length + this.$t('biz.imgbox.unit'))
      }

      const uploadFileNum = this.$refs.upload ? this.$refs.upload.uploadFiles.length : 0
      if (uploadFileNum > 0) {
        tips.push(this.$t('biz.imgbox.soonUpload') + uploadFileNum + this.$t('biz.imgbox.unit'))
      }

      tips.push(this.$t('biz.imgbox.remainder') + (this.options.limit - this.images.length - uploadFileNum) + this.$t('biz.imgbox.unit') + this.$t('biz.imgbox.upload'))

      return tips.join('，')
    },

    /**
     * 上传错误处理
     * @param err
     * @param file
     * @param fileList
     */
    onUploadError(err, file, fileList) {
      this.message(this.$t('biz.imgbox.requestError'), true)
      throw err
    },

    /**
     * 上传成功处理，并提交图片数据
     * @param response
     * @param file
     * @param fileList
     * @returns {boolean}
     */
    onUploadSuccess(response, file, fileList) {
      var filesNames = []
      fileList.forEach(file => {
        filesNames.push(file.name)
      })
      var uploadFile = []
      this.attachFiles.forEach(file => {
        if (filesNames.indexOf(file.fileName) !== -1) {
          file.attachFileGroupId = this.groupForm.attachFileGroupId
          uploadFile.push(file)
        }
      })
      console.log('上传成功处理attachFiles：', this.attachFiles)
      console.log('上传成功处理uploadFile：', uploadFile)
      save(uploadFile).then(response => {
        this.attachFiles = []
        this.options.activeTab = 'pick'
        this.dataForm.attachFileGroupId = 0
        this.loadListImage()
      })
      this.disabled = true
      this.$refs.upload.uploadFiles = []
      this.page.current = 1
    },

    /**
     * 选择上传文件超过限制文件个数提示
     */
    onUploadExceedTip() {
      this.message(this.$t('biz.imgbox.maxSelect') + this.uploadNumberLimit() + this.$t('biz.imgbox.unit') + this.$t('biz.imgbox.upload'))
    },
    message(msg, isInfo) {
      var type = 'error'
      if (isInfo) {
        type = 'info'
      }
      this.$message({
        message: msg,
        type: type,
        duration: 1500
      })
    }
  },

  // mounted() {
  //   this.loadListImage()
  // },

  computed: {

  }
}
</script>

<style lang="scss">
.elx-images {
  .el-upload-list__item,
  .el-upload--picture-card {
    width: 78px;
    height: 78px;
    line-height: 82px;
    border-radius: 5px;
  }
}

.elx-imgbox-dialog {
  $bg: #f6f6f6;
  // top: 100px;

  .el-badge {
    vertical-align: bottom;
  }

  .el-dialog {
    width: 820px;

    .el-dialog__header {
      border-bottom: 1px solid #e8e8e8;
    }

    .el-dialog__body {
      padding: 0;
      // background: $bg;
    }
  }

  .elx-foot {
    padding: 15px 0 0 10px;
    .el-button {
      margin: 0 0 0 10px;
    }
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

            .label {
              position: absolute;
              z-index: 9;
              left: 0;
              bottom: 24px;
              width: 100%;
              height: 21px;
              line-height: 21px;
              text-align: center;
              color: #fff;

              &:after {
                content: " ";
                position: absolute;
                left: 0;
                bottom: 0;
                width: 100%;
                height: 21px;
                background: #000;
                opacity: 0.3;
                z-index: -1;
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

        .el-pagination {
          background: #f9f9f9;
          position: relative;
          padding: 5px;
          margin: 5px 24px 0 20px;
          text-align: right;
          float: none;

          * {
            background: none;
          }
        }
      }
    }
  }

  .upload-block {
    .upload-img-preview {
      padding: 20px;

      /* 上传图片预览改为使用背景图片按比例缩放方式 */
      .el-upload--picture-card,
      .el-upload-list--picture-card .el-upload-list__item,
      .el-upload-list--picture-card .el-upload-list__item .wp {
        width: 90px;
        height: 90px;
        line-height: 98px;
        background-size: cover;
        background-position: 50% 50%;
      }

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
  .el-form-item {
    margin-bottom: 0;
  }
}

.el-upload-list__item-actions {
  a {
    i {
      color: #fff;
    }
  }
}
</style>
