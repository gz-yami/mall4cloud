<template>
  <div class="prod-details">
    <el-tabs type="card">
      <el-form-item label="产品详情">
        <tiny-mce
          ref="content"
          v-model="dataForm.detail"
          :width="850"
        />
      </el-form-item>

      <!-- 中文 -->
      <!-- <el-tab-pane label="中文信息">
        <el-form-item label="商品名称" prop="nameCn">
          <el-col :span="8">
            <el-input
              v-model="dataForm.prodNameCn"
              placeholder="请输入商品名称"
              maxlength="50"
            />
          </el-col>
        </el-form-item>

        <el-form-item label="产品卖点" prop="briefCn">
          <el-col :span="8">
            <el-input
              v-model="dataForm.briefCn"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4 }"
              placeholder="请输入产品卖点"
            />
          </el-col>
        </el-form-item>

        <el-form-item label="产品详情" prop="contentCn">
          <tiny-mce
            ref="content"
            v-model="dataForm.contentCn"
            style="width: 1000px"
          />
        </el-form-item>
      </el-tab-pane> -->

      <!-- 英文 -->
      <!-- <el-tab-pane label="English Information">
        <el-form-item label="Prod name" prop="prodNameEn">
          <el-col :span="8">
            <el-input
              v-model="dataForm.prodNameEn"
              placeholder="Prod name"
              maxlength="50"
            />
          </el-col>
        </el-form-item>
        <el-form-item label="Selling point" prop="briefEn">
          <el-col :span="8">
            <el-input
              v-model="dataForm.briefEn"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4 }"
              placeholder="Selling point"
            />
          </el-col>
        </el-form-item>
        <el-form-item label="product details" prop="contentEn">
          <tiny-mce
            ref="content"
            v-model="dataForm.contentEn"
            style="width: 1000px"
          />
        </el-form-item>
      </el-tab-pane> -->
    </el-tabs>
  </div>
</template>

<script>
import TinyMce from '@/components/Tinymce'
export default {
  components: {
    TinyMce
  },
  props: {
    dataForm: {
      type: Object,
      default() {
        return {}
      }
    },
  },

  data() {
    return {

    }
  }
}
</script>

<style lang="scss">
.prod-details {
  .el-tabs--card > .el-tabs__header {
    border-bottom: 0;
  }
}
</style>