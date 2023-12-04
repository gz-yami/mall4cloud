<template>
  <div class="component-index-img-add-or-update">
    <el-dialog
      v-model="visible"
      :title="dataForm.attrId? $t('table.edit'): $t('table.create')"
      :close-on-click-modal="false"
      :destroy-on-close="true"
      top="12vh"
      width="750px"
      @close="closeDialog"
    >
      <el-form
        ref="dataFormRef"
        :rules="rules"
        :model="dataForm"
        label-position="left"
        label-width="90px"
        style="min-width: 600px; width: 600px; margin-left:35px;"
      >
        <!-- 图片 -->
        <el-form-item
          label="轮播图片"
          prop="imgUrl"
        >
          <img-upload v-model="dataForm.imgUrl" />
          <div class="">
            建议图片尺寸为710*780
          </div>
        </el-form-item>
        <!-- 平台 -->
        <el-form-item
          label="展示平台"
          prop="imgType"
        >
          <el-radio
            v-model="dataForm.imgType"
            :label="0"
          >
            移动端
          </el-radio>
        </el-form-item>
        <!-- 状态 -->
        <el-form-item
          label="轮播图状态"
          prop="status"
        >
          <el-radio
            v-model="dataForm.status"
            :label="0"
          >
            禁用
          </el-radio>
          <el-radio
            v-model="dataForm.status"
            :label="1"
          >
            正常
          </el-radio>
        </el-form-item>
        <!-- 排序 -->
        <el-form-item
          label="轮播图排序"
          prop="seq"
        >
          <el-input-number
            v-model="dataForm.seq"
            :min="0"
            controls-position="right"
            :precision="0"
          />
        </el-form-item>
        <!-- 类型 -->
        <el-form-item
          label="轮播图类型"
          prop=""
        >
          <el-radio
            v-model="relatedSpu"
            :label="0"
          >
            无
          </el-radio>
          <el-radio
            v-model="relatedSpu"
            :label="1"
          >
            商品
          </el-radio>
          <div v-if="relatedSpu === 1 && dataForm.spu && dataForm.spu.spuId">
            <el-card
              :body-style="{ padding: '0px' }"
              style="height: 160px;width: 120px"
            >
              <img
                :src="(dataForm.spu.mainImgUrl).indexOf('http')===-1 ? resourcesUrl + dataForm.spu.mainImgUrl : dataForm.spu.mainImgUrl"
                style="height:104px;width:100%"
              >
              <div class="card-prod-bottom">
                <span class="card-prod-name">{{ dataForm.spu.spuName }}</span>
                <el-button
                  type="text"
                  class="card-prod-name-button"
                  @click="deleteCurrentProd"
                >
                  删除
                </el-button>
              </div>
            </el-card>
          </div>
          <div
            v-if="relatedSpu === 1 && !dataForm.spuId"
            class="select-pro"
          >
            <el-button @click="selectProds">
              选择商品
            </el-button>
          </div>
          <prods-select
            v-if="prodSelectVisible"
            ref="prodSelectRef"
            :is-single="true"
            @refresh-select-prods="getSelectedProd"
          />
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
            type="primary"
            @click="dataFormSubmit()"
          >
            {{ $t('table.confirm') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import * as api from '@/api/multishop/index-img'
import { reactive } from 'vue'
const emit = defineEmits(['refreshDataList'])

const Data = reactive({
  visible: false,
  dataForm: {
    imgId: 0,
    imgUrl: null,
    seq: 0,
    imgType: 0,
    status: 1,
    spuId: null,
    spu: {} // 商品列表
  },
  relatedSpu: 0,
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  // 关联数据
  card: {
    id: 0,
    pic: '',
    name: '',
    realData: {
      prod: [],
      shop: [],
      activity: []
    }
  },
  prodSelectVisible: false,
  prods: {},
  rules: {
    imgUrl: [
      { required: true, message: '请选择轮播图图片', trigger: 'blur' }
    ]
  }
})
const { visible, dataForm, relatedSpu, resourcesUrl, prodSelectVisible, rules } = toRefs(Data)

const dataFormRef = ref()
const init = (imgId) => {
  Data.visible = true
  Data.dataForm.imgId = imgId || 0
  nextTick(() => {
    dataFormRef.value.resetFields()
    if (!Data.dataForm.imgId) {
      return
    }
    api.get(imgId).then(data => {
      Data.dataForm = data
      Data.dataForm.spu.spuName = Data.dataForm.spu.name
      Data.spus = data.spus || []
      Data.relatedSpu = data.spuId ? 1 : 0
    })
  })
}

const prodSelectRef = ref()
const selectProds = () => {
  Data.prodSelectVisible = true
  nextTick(() => {
    prodSelectRef.value.init()
  })
}

const getSelectedProd = (prods) => {
  Data.dataForm.spu = prods[0]
  Data.dataForm.spuId = prods[0].spuId
  Data.dataForm.spuName = prods[0].spuName
}

const deleteCurrentProd = () => {
  Data.dataForm.spu = {}
  Data.dataForm.spuId = ''
}

const closeDialog = () => {
  Data.dataForm = {
    imgId: 0,
    imgUrl: null,
    seq: 0,
    imgType: 0,
    status: 1,
    spuId: null,
    spu: {}
  }
}

/**
     * 表单提交
     */
const dataFormSubmit = () => {
  dataFormRef.value.validate(valid => {
    if (!valid) {
      return
    }
    if (!Data.dataForm.imgUrl) {
      ElMessage({
        message: '请选择轮播图片',
        type: 'warning',
        duration: 1500
      })
      return
    }
    if (Data.relatedSpu === 1 && !Data.dataForm.spuId) {
      ElMessage({
        message: '请选择商品',
        type: 'warning',
        duration: 1500
      })
      return
    }
    if (Data.relatedSpu === 0) {
      Data.dataForm.spuId = ''
    }
    const dataForm = JSON.parse(JSON.stringify(Data.dataForm))
    dataForm.spu = undefined
    const request = Data.dataForm.imgId ? api.update(dataForm) : api.save(dataForm)
    request.then(() => {
      ElMessage({
        message: $t('table.actionSuccess'),
        type: 'success',
        duration: 1500,
        onClose: () => {
          Data.visible = false
          emit('refreshDataList')
          dataFormRef.value.resetFields()
        }
      })
    })
  })
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>

</style>
