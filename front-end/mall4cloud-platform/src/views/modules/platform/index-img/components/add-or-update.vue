<template>
  <el-dialog
    v-model="visible"
    :title="dataForm.attrId? $t('table.edit'): $t('table.create')"
    :close-on-click-modal="false"
    :destroy-on-close="true"
    top="10vh"
    class="comoonent-index-img-add-or-update"
    width="750px"
    @close="closeDialog"
  >
    <el-form
      ref="dataFormRef"
      :rules="rules"
      :model="dataForm"
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
        label="平台"
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
        label="状态"
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
        label="排序"
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
        label="类型"
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
        <div v-if="relatedSpu === 1 && dataForm.spuId!==null">
          <el-card
            :body-style="{ padding: '0px' }"
            style="height: 160px;width: 120px"
          >
            <img
              :src="prods.mainImgUrl"
              style="height:104px;width:100%"
            >
            <div class="card-prod-bottom">
              <span class="card-prod-name">{{ prods.name }}</span>
              <el-button
                type="primary"
                link
                class="card-prod-name-button"
                @click="dataForm.spuId = null"
              >
                删除
              </el-button>
            </div>
          </el-card>
        </div>
        <div
          v-if="relatedSpu === 1 && dataForm.spuId===null"
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
          :disabled="!canSubmit"
          @click="dataFormSubmit()"
        >
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import * as api from '@/api/platform/index-img'
import { reactive } from 'vue'

const emit = defineEmits(['refreshDataList'])

const Data = reactive({
  canSubmit: true,
  visible: false,
  dataForm: {
    imgId: 0,
    imgUrl: null,
    seq: 0,
    imgType: 0,
    status: 1,
    spuId: null
  },
  relatedSpu: 0,
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
const { canSubmit, visible, dataForm, relatedSpu, prodSelectVisible, prods, rules } = toRefs(Data)

const dataFormRef = ref()
const init = (imgId) => {
  Data.canSubmit = true
  Data.visible = true
  Data.dataForm.imgId = imgId || 0
  nextTick(() => {
    dataFormRef.value.resetFields()
    if (!Data.dataForm.imgId) {
      return
    }
    api.get(imgId).then(data => {
      Data.dataForm = data
      Data.prods = data.spu
      Data.relatedSpu = data.spu ? 1 : 0
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

const closeDialog = () => {
  Data.dataForm = {
    imgId: 0,
    imgUrl: null,
    seq: 0,
    imgType: 0,
    status: 1,
    spuId: null
  }
}

const getSelectedProd = (prods) => {
  Data.prods = prods[0]
  Data.dataForm.spuId = Data.prods.spuId
}

/**
     * 表单提交
     */
const dataFormSubmit = () => {
  Data.canSubmit = false
  dataFormRef.value.validate(valid => {
    if (!valid) {
      Data.canSubmit = true
      return
    }
    if (!Data.dataForm.imgUrl) {
      ElMessage({
        message: '请选择轮播图片',
        type: 'warning',
        duration: 1500
      })
      Data.canSubmit = true
      return
    }
    if (Data.relatedSpu === 1 && !Data.dataForm.spuId) {
      ElMessage({
        message: '请选择商品',
        type: 'warning',
        duration: 1500
      })
      Data.canSubmit = true
      return
    }
    const data = JSON.parse(JSON.stringify(Data.dataForm))
    if (!Data.relatedSpu) {
      data.spu = null
      data.spuId = null
    }
    const request = data.imgId ? api.update(data) : api.save(data)
    request.then(() => {
      ElMessage({
        message: $t('table.actionSuccess'),
        type: 'success',
        duration: 1500,
        onClose: () => {
          Data.visible = false
          Data.canSubmit = true
          emit('refreshDataList')
          dataFormRef.value.resetFields()
        }
      })
    }).catch(() => {
      Data.canSubmit = true
    })
  })
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>

</style>
