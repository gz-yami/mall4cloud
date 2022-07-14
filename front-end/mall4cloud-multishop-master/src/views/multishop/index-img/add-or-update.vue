<template>
  <div class="index-img">
    <el-dialog :title="dataForm.attrId? $t('table.edit'): $t('table.create')" :close-on-click-modal="false" :destroy-on-close="true" :visible.sync="visible" top="12vh" width="750px" @close="closeDialog">
      <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="left" label-width="90px" style="min-width: 600px; width: 600px; margin-left:35px;">
        <!-- 图片 -->
        <el-form-item label="轮播图片" prop="imgUrl">
          <img-upload v-model="dataForm.imgUrl" />
          <div class="">建议图片尺寸为710*780</div>
        </el-form-item>
        <!-- 平台 -->
        <el-form-item label="展示平台" prop="imgType">
          <el-radio v-model="dataForm.imgType" :label="0">移动端</el-radio>
        </el-form-item>
        <!-- 状态 -->
        <el-form-item label="轮播图状态" prop="status">
          <el-radio v-model="dataForm.status" :label="0">禁用</el-radio>
          <el-radio v-model="dataForm.status" :label="1">正常</el-radio>
        </el-form-item>
        <!-- 排序 -->
        <el-form-item label="轮播图排序" prop="seq">
          <el-input-number
            v-model="dataForm.seq"
            :min="0"
            controls-position="right"
            :precision="0"
          />
        </el-form-item>
        <!-- 类型 -->
        <el-form-item label="轮播图类型" prop="">
          <el-radio v-model="relatedSpu" :label="0">无</el-radio>
          <el-radio v-model="relatedSpu" :label="1">商品</el-radio>
          <div v-if="relatedSpu === 1 && dataForm.spu && dataForm.spu.spuId">
            <el-card :body-style="{ padding: '0px' }" style="height: 160px;width: 120px">
              <img :src="(dataForm.spu.mainImgUrl).indexOf('http')===-1 ? resourcesUrl + dataForm.spu.mainImgUrl : dataForm.spu.mainImgUrl" style="height:104px;width:100%">
              <div class="card-prod-bottom">
                <span class="card-prod-name">{{ dataForm.spu.spuName }}</span>
                <el-button
                  type="text"
                  class="card-prod-name-button"
                  @click="deleteCurrentProd"
                >删除</el-button>
              </div>
            </el-card>
          </div>
          <div v-if="relatedSpu === 1 && !dataForm.spuId" class="select-pro">
            <el-button @click="selectProds">选择商品</el-button>
          </div>
          <prods-select v-if="prodSelectVisible" ref="prodSelect" :is-single="true" @refreshSelectProds="getSelectedProd" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" @click="dataFormSubmit()">
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as api from '@/api/multishop/index-img'
import ImgUpload from '@/components/ImgUpload'
import ProdsSelect from '@/components/ProdsSelect'
export default {
  components: {
    ImgUpload,
    ProdsSelect
  },
  data() {
    return {
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
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
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
    }
  },
  methods: {
    init(imgId) {
      this.visible = true
      this.dataForm.imgId = imgId || 0
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!this.dataForm.imgId) {
          return
        }
        api.get(imgId).then(data => {
          this.dataForm = data
          this.dataForm.spu.spuName = this.dataForm.spu.name
          this.spus = data.spus || []
          this.relatedSpu = data.spuId ? 1 : 0
        })
      })
    },

    selectProds() {
      this.prodSelectVisible = true
      this.$nextTick(() => {
        this.$refs.prodSelect.init()
      })
    },

    getSelectedProd(prods) {
      console.log(prods)
      this.dataForm.spu = prods[0]
      this.dataForm.spuId = prods[0].spuId
      this.dataForm.spuName = prods[0].spuName
    },

    deleteCurrentProd() {
      this.dataForm.spu = {}
      this.dataForm.spuId = ''
    },

    closeDialog() {
      this.dataForm = {
        imgId: 0,
        imgUrl: null,
        seq: 0,
        imgType: 0,
        status: 1,
        spuId: null,
        spu: {}
      }
    },

    /**
     * 表单提交
     */
    dataFormSubmit() {
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          return
        }
        if (!this.dataForm.imgUrl) {
          this.$message({
            message: '请选择轮播图片',
            type: 'warning',
            duration: 1500
          })
          return
        }
        if (this.relatedSpu === 1 && !this.dataForm.spuId) {
          this.$message({
            message: '请选择商品',
            type: 'warning',
            duration: 1500
          })
          return
        }
        if (this.relatedSpu === 0) {
          this.dataForm.spuId = ''
        }
        const dataForm = JSON.parse(JSON.stringify(this.dataForm))
        dataForm.spu = undefined
        const request = this.dataForm.imgId ? api.update(dataForm) : api.save(dataForm)
        request.then(data => {
          this.$message({
            message: this.$t('table.actionSuccess'),
            type: 'success',
            duration: 1500,
            onClose: () => {
              this.visible = false
              this.$emit('refreshDataList')
              this.$refs['dataForm'].resetFields()
            }
          })
        })
      })
    }

  }
}
</script>

<style lang="scss" scoped>
.index-img {
  .el-form--label-left {
    .el-form-item__label {
      text-align: right !important;
    }
  }
  .select-pro {
    margin-top: 10px;
  }
  .card-prod-bottom {
    position: relative;
    text-align: left;
    top: -15px;
    .card-prod-name {
      margin: auto;
      padding: 0 4px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      width: 118px;
      display: inline-block;
    }
    .card-prod-name-button {
      position: absolute;
      top: 30px;
      right: 0;
    }
    .el-button--medium {
      padding: 2px 6px !important;
    }
  }
}
</style>
