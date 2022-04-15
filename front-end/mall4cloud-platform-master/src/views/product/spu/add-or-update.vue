<template>
  <el-dialog :title="dataForm.spuId? $t('table.edit'): $t('table.create')" :visible.sync="visible">
    <el-form ref="dataForm" :rules="rules" :model="dataForm" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
      <!-- 品牌ID -->
      <el-form-item :label="$t('product.spu.brandId')" prop="brandId">
        <el-input-number
          v-model="dataForm.brandId"
          controls-position="right"
          :precision="0"
        />
      </el-form-item>
      <!-- 分类ID -->
      <el-form-item :label="$t('product.spu.categoryId')" prop="categoryId">
        <el-input-number
          v-model="dataForm.categoryId"
          controls-position="right"
          :precision="0"
        />
      </el-form-item>
      <!-- spu名称 -->
      <el-form-item :label="$t('product.spu.name')" prop="name">
        <el-input v-model="dataForm.name" />
      </el-form-item>
      <!-- 卖点 -->
      <el-form-item :label="$t('product.spu.sellingPoint')" prop="sellingPoint">
        <el-input v-model="dataForm.sellingPoint" />
      </el-form-item>
      <!-- 商品介绍主图 多个图片逗号分隔 -->
      <el-form-item :label="$t('product.spu.imgUrls')" prop="imgUrls">
        <el-input v-model="dataForm.imgUrls" />
      </el-form-item>
      <!-- 售价，整数方式保存 -->
      <el-form-item :label="$t('product.spu.priceFee')" prop="priceFee">
        <el-input-number
          v-model="dataForm.priceFee"
          controls-position="right"
          :precision="0"
        />
      </el-form-item>
      <!-- 售价，金额对应的小数位数 -->
      <el-form-item :label="$t('product.spu.priceScale')" prop="priceScale">
        <el-input-number
          v-model="dataForm.priceScale"
          controls-position="right"
          :precision="0"
        />
      </el-form-item>
      <!-- 市场价，整数方式保存 -->
      <el-form-item :label="$t('product.spu.marketPriceFee')" prop="marketPriceFee">
        <el-input-number
          v-model="dataForm.marketPriceFee"
          controls-position="right"
          :precision="0"
        />
      </el-form-item>
      <!-- 市场价，金额对应的小数位数 -->
      <el-form-item :label="$t('product.spu.marketPriceScale')" prop="marketPriceScale">
        <el-input-number
          v-model="dataForm.marketPriceScale"
          controls-position="right"
          :precision="0"
        />
      </el-form-item>
      <!-- 状态 1:enable, 0:disable, -1:deleted -->
      <el-form-item :label="$t('product.spu.status')" prop="status">
        <el-input-number
          v-model="dataForm.status"
          controls-position="right"
          :precision="0"
        />
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
</template>

<script>
import * as api from '@/api/product/spu'

export default {
  data() {
    return {
      visible: false,
      dataForm: {
        spuId: 0,
        brandId: null,
        categoryId: null,
        name: null,
        sellingPoint: null,
        imgUrls: null,
        priceFee: null,
        priceScale: null,
        marketPriceFee: null,
        marketPriceScale: null,
        status: null
      },
      rules: {
      }
    }
  },
  methods: {
    init(spuId) {
      this.dataForm.spuId = spuId || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!this.dataForm.spuId) {
          return
        }
        api.get(spuId).then(data => {
          this.dataForm = data
        })
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          return
        }
        const request = this.dataForm.spuId ? api.update(this.dataForm) : api.save(this.dataForm)
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
