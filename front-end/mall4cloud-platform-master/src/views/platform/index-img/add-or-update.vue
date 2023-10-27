<template>
  <div class="index-img">
    <el-dialog :title="dataForm.attrId? $t('table.edit'): $t('table.create')" :close-on-click-modal="false" :destroy-on-close="true" :visible.sync="visible" top="10vh" width="750px" @close="closeDialog">
      <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="left" label-width="90px" style="min-width: 600px; width: 600px; margin-left:35px;">
        <!-- 图片 -->
        <el-form-item label="轮播图片" prop="imgUrl">
          <img-upload v-model="dataForm.imgUrl" />
          <div class="">建议图片尺寸为710*780</div>
        </el-form-item>
        <!-- 平台 -->
        <el-form-item label="平台" prop="imgType">
          <el-radio v-model="dataForm.imgType" :label="0">移动端</el-radio>
        </el-form-item>
        <!-- 状态 -->
        <el-form-item label="状态" prop="status">
          <el-radio v-model="dataForm.status" :label="0">禁用</el-radio>
          <el-radio v-model="dataForm.status" :label="1">正常</el-radio>
        </el-form-item>
        <!-- 排序 -->
        <el-form-item label="排序" prop="seq">
          <el-input-number
            v-model="dataForm.seq"
            :min="0"
            controls-position="right"
            :precision="0"
          />
        </el-form-item>
        <!-- 类型 -->
        <el-form-item label="类型" prop="">
          <el-radio v-model="relatedSpu" :label="0">无</el-radio>
          <el-radio v-model="relatedSpu" :label="1">商品</el-radio>
          <div v-if="relatedSpu === 1 && dataForm.spuId!==null">
            <el-card :body-style="{ padding: '0px' }" style="height: 160px;width: 120px">
              <img :src="prods.mainImgUrl" style="height:104px;width:100%">
              <div class="card-prod-bottom">
                <span class="card-prod-name">{{ prods.name }}</span>
                <el-button
                  type="text"
                  class="card-prod-name-button"
                  @click="dataForm.spuId = null"
                >删除</el-button>
              </div>
            </el-card>
          </div>
          <div v-if="relatedSpu === 1 && dataForm.spuId===null" class="select-pro">
            <el-button @click="selectProds">选择商品</el-button>
          </div>
          <prods-select v-if="prodSelectVisible" ref="prodSelect" :is-single="true" @refreshSelectProds="getSelectedProd" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" :disabled="!canSubmit" @click="dataFormSubmit()">
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as api from '@/api/platform/index-img'
import ImgUpload from '@/components/ImgUpload'
import ProdsSelect from '@/components/ProdsSelect'
export default {
  components: {
    ImgUpload,
    ProdsSelect
  },
  data() {
    return {
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
    }
  },
  methods: {
    init(imgId) {
      this.canSubmit = true
      this.visible = true
      this.dataForm.imgId = imgId || 0
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!this.dataForm.imgId) {
          return
        }
        api.get(imgId).then(data => {
          this.dataForm = data
          this.prods = data.spu
          this.relatedSpu = data.spu ? 1 : 0
          // this.getSelectedProd(data.spu)
        })
      })
    },

    selectProds() {
      this.prodSelectVisible = true
      this.$nextTick(() => {
        this.$refs.prodSelect.init()
      })
    },

    closeDialog() {
      this.dataForm = {
        imgId: 0,
        imgUrl: null,
        seq: 0,
        imgType: 0,
        status: 1,
        spuId: null
      }
    },

    getSelectedProd(prods) {
      this.prods = prods[0]
      this.dataForm.spuId = this.prods.spuId
    },

    /**
     * 表单提交
     */
    dataFormSubmit() {
      this.canSubmit = false
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          this.canSubmit = true
          return
        }
        if (!this.dataForm.imgUrl) {
          this.$message({
            message: '请选择轮播图片',
            type: 'warning',
            duration: 1500
          })
          this.canSubmit = true
          return
        }
        // if (this.relatedSpu === 1 && !this.datatForm.spuId) {
        //   this.$message({
        //     message: '请选择商品',
        //     type: 'warning',
        //     duration: 1500
        //   })
        //   return
        // }
        console.log(this.dataForm)
        const data = JSON.parse(JSON.stringify(this.dataForm))
        if (!this.relatedSpu) {
          data.spu = null
          data.spuId = null
        }
        const request = data.imgId ? api.update(data) : api.save(data)
        request.then(data => {
          this.$message({
            message: this.$t('table.actionSuccess'),
            type: 'success',
            duration: 1500,
            onClose: () => {
              this.visible = false
              this.canSubmit = true
              this.$emit('refreshDataList')
              this.$refs['dataForm'].resetFields()
            }
          })
        }).catch(() => {
          this.canSubmit = true
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
