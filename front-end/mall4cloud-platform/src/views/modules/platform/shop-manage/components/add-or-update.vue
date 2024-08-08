<template>
  <div class="component-shop-manage-add-or-update">
    <el-dialog
      v-model="visible"
      :title="shopId?'查看店铺':'新建店铺'"
      :close-on-click-modal="false"
      :destroy-on-close="true"
      top="10vh"
      width="750px"
      @close="close"
    >
      <el-form
        ref="dataFormRef"
        :rules="rules"
        :model="dataForm"
        label-position="left"
        label-width="90px"
        style="min-width: 650px; width: 650px; margin-left:35px;"
      >
        <!-- 店铺名称 -->
        <el-form-item
          label="店铺名称："
          label-width="110px"
          prop="shopName"
        >
          <el-input
            v-model="dataForm.shopName"
            placeholder="请输入店铺名称"
            :readonly="shopId ? true : false"
          />
        </el-form-item>
        <!-- 店铺简介 -->
        <el-form-item
          label="店铺简介："
          label-width="110px"
          prop="intro"
        >
          <el-input
            v-model="dataForm.intro"
            type="textarea"
            placeholder="请输入店铺简介"
            :readonly="shopId ? true : false"
          />
        </el-form-item>
        <!-- 店铺类型 -->
        <el-form-item
          label="店铺类型："
          label-width="110px"
          prop="type"
        >
          <el-radio-group
            v-model="dataForm.type"
            :disabled="shopId ? true : false"
          >
            <el-radio :label="1">
              自营店
            </el-radio>
            <el-radio :label="2">
              普通店
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- 店铺logo -->
        <el-form-item
          label="店铺logo："
          label-width="110px"
          prop="shopLogo"
        >
          <img-upload
            v-if="!shopId"
            v-model="dataForm.shopLogo"
          />
          <div
            v-if="shopId"
            class="shoplogo"
          >
            <img
              :src="dataForm.shopLogo"
              @click="picturePreview(dataForm.shopLogo)"
            >
          </div>
        </el-form-item>
        <!-- 身份证正反面 -->
        <el-form-item
          label="身份证件："
          label-width="110px"
          prop="identityCardFront"
        >
          <div class="idcard-box">
            <div class="front">
              <img-upload
                v-if="!shopId"
                v-model="dataForm.identityCardFront"
              />
              <img
                v-if="shopId"
                :src="dataForm.identityCardFront"
                @click="picturePreview(dataForm.identityCardFront)"
              >
              <div class="text">
                身份证正面
              </div>
            </div>
            <div class="back">
              <img-upload
                v-if="!shopId"
                v-model="dataForm.identityCardLater"
              />
              <img
                v-if="shopId"
                :src="dataForm.identityCardLater"
                @click="picturePreview(dataForm.identityCardLater)"
              >
              <div class="text">
                身份证背面
              </div>
            </div>
          </div>
        </el-form-item>
        <!-- 营业执照 -->
        <el-form-item
          label="营业执照："
          label-width="110px"
          prop="businessLicense"
        >
          <img-upload
            v-if="!shopId"
            v-model="dataForm.businessLicense"
          />
          <div
            v-if="shopId"
            class="licens"
          >
            <img
              :src="dataForm.businessLicense"
              @click="picturePreview(dataForm.businessLicense)"
            >
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <div
          class="dialog-footer"
        >
          <el-button @click="visible = false">
            {{ $t('table.cancel') }}
          </el-button>
          <el-button
            v-if="shopId===null"
            type="primary"
            @click="dataFormSubmit()"
          >
            {{ $t('table.confirm') }}
          </el-button>
        </div>
      </template>

      <!-- 图片预览 -->
      <picture-preview
        v-if="picturePreviewVisible"
        ref="picturePreviewRef"
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import * as api from '@/api/platform/shop'

const emit = defineEmits(['refreshDataList'])

const Data = reactive({
  visible: false,
  shopId: 0, // 店铺id
  status: 0, // -1:已删除 0: 停业中 1:营业中
  dataForm: {
    shopName: '',
    type: 1, // 店铺类型 1自营店 2普通店
    intro: '',
    shopLogo: '',
    identityCardFront: '',
    identityCardLater: '',
    businessLicense: ''
  },
  picturePreviewVisible: false, // 图片预览组件显隐
  prods: {},
  rules: {
    shopName: [
      { required: true, message: '店铺名称不能为空', trigger: 'blur' }
    ],
    intro: [
      { required: true, message: '店铺简介不能为空', trigger: 'blur' }
    ],
    type: [
      { required: true, message: '请选择开店类型', trigger: 'change' }
    ],
    shopLogo: [
      { required: true, message: '请选择店铺logo', trigger: 'change' }
    ],
    identityCardFront: [
      { required: true, message: '请选择上传身份证件', trigger: 'change' }
    ],
    identityCardLater: [
      { required: true, message: '请选择身份证背面', trigger: 'change' }
    ],
    businessLicense: [
      { required: true, message: '请选择营业执照', trigger: 'change' }
    ]
  }
})
const { visible, shopId, dataForm, picturePreviewVisible, rules } = toRefs(Data)

const dataFormRef = ref()
const init = (shopId) => {
  Data.visible = true
  Data.shopId = shopId ?? null
  nextTick(() => {
    dataFormRef.value.resetFields()
    if (Data.shopId === null) {
      return
    }
    api.getManageDetail(Data.shopId).then(data => {
      Data.dataForm = data
    })
  })
}

/**
 * 提交修改
 */
const dataFormSubmit = () => {
  const dataForm = Data.dataForm
  const data = {
    shopId: Data.shopId,
    shopLogo: dataForm.shopLogo,
    businessLicense: dataForm.businessLicense,
    identityCardFront: dataForm.identityCardFront,
    identityCardLater: dataForm.identityCardLater,
    intro: dataForm.intro,
    shopName: dataForm.shopName,
    shopStatus: Data.dataForm.status,
    type: dataForm.type // 店铺类型1自营店 2普通店
  }
  api.update(data).then(() => {
    ElMessage({
      message: '更新成功',
      type: 'success',
      duration: 1500,
      onClose: () => {
        Data.visible = false
        emit('refreshDataList')
        dataFormRef.value.resetFields()
      }
    })
  })
}

/**
 * 关闭弹窗
 */
const close = () => {
  Data.shopId = 0 // 店铺id
  Data.status = 0
  Data.dataForm = {
    shopName: '',
    noticeMobile: '',
    intro: '',
    type: 1, // 店铺类型 1自营店 2普通店
    shopLogo: '',
    identityCardFront: '',
    identityCardLater: '',
    businessLicense: ''
  }
}

/**
 * 图片预览
 */
const picturePreviewRef = ref()
const picturePreview = (imgUrl) => {
  Data.picturePreviewVisible = true
  nextTick(() => {
    picturePreviewRef.value.init(imgUrl)
  })
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>
.component-shop-manage-add-or-update {
  .shoplogo {
    display: flex;
    align-items: center;
    width: 100px;
    height: 100px;
    border: 1px dashed #ccc;
    border-radius: 4px;
    cursor: pointer;
  }
  .shoplogo img {
    display: block;
    width: 100%;
    height: auto;
    max-height: 100%;
  }
  .idcard-box {
    display: flex;
    align-items: top;
  }
  .idcard-box .front,
  .idcard-box .back {
    display: block;
    // width: 120px;
    height: auto;
    margin-right: 20px;
  }
  .idcard-box .text {
    display: block;
    width: 100%;
    text-align: center;
    font-size: 12px;
    color: #aaa;
  }
  .idcard-box img {
    display: block;
    width: 120px;
    height: 80px;
    border: 1px dashed #ccc;
    border-radius: 4px;
    cursor: pointer;
  }
  .licens {
    display: block;
    width: 100px;
    height: auto;
    border: 1px dashed #ccc;
    border-radius: 4px;
    cursor: pointer;
  }
  .licens img {
    display: block;
    width: 100%;
    height: auto;
    max-height: 100%;
  }
}
</style>
