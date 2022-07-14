<template>
  <div class="group-container">
    <div class="spec-name">
      <div
        v-for="(item, index) in sku.leaf"
        :key="index"
        class="spec-name-int">
          <el-tag :type="item.type" effect="plain" closable @close="handleRemoveSkuLeaf(index)">{{ item[optionText] }}</el-tag>
          <div v-if="item.imgUrl" class="spec-imgbox">
            <img-upload
              v-if="hasSkuImage"
              v-model="item.imgUrl"
              @input="handleUploadSuccess(item, $event)"
              class="spec-img" />
              <div v-if="hasSkuImage && item.imgUrl" class="preview-btn" @click="picturePreview(item.imgUrl)">预览</div>
          </div>
      </div>

      <!-- 新增 -->
      <div class="sku-item" v-if="sku[optionValue] && sku[optionValue] !== 0">
        <el-popover
          placement="bottom"
          width="200"
          trigger="click"
          v-model="visiable">
          <el-select
            class="popover-select"
            size="mini"
            v-model="leafValue"
            multiple
            filterable
            allow-create
            default-first-option
            :popper-append-to-body="false"
            style="width:100%"
            placeholder="属性值"
            @change="createSkuLeaf"
            @visible-change="status => !status && this.handleSelectSku()"
            >
            <el-option
              v-for="item in skuOptions"
              :key="item[optionValue]"
              :label="item[optionText]"
              :value="item[optionValue]">
            </el-option>
          </el-select>
          <!-- 新增 -->
          <el-button slot="reference" circle  type="primary" plain size="mini" class="cursor"><i class="el-icon-plus" /></el-button>
        </el-popover>
      </div>

    </div>
  </div>
</template>

<script>
import ImgUpload from '@/components/ImgUpload'
const noop = res => res
export default {
  inject: [
    'ease'
  ],

  components: { ImgUpload },

  data() {
    return {
      visiable: false,
      leafValue: [],
      skuOptions: [],
      id: 0
    }
  },

  props: {
    sku: {
      type: Object,
      default() {
        return {}
      }
    },
    hasSkuImage: {
      type: Boolean,
      default: false
    },
    onSkuLeafChange: {
      type: Function,
      default: noop
    }
  },

  computed: {
    optionValue() {
      return this.ease.optionValue
    },

    optionText() {
      return this.ease.optionText
    }
  },

  watch: {
    sku: {
      deep: true,
      immediate: true,
      handler(sku) {
        this.fetchLeafById(sku[this.optionValue])
      }
    }
  },

  methods: {
    handleHideVisiable() {
      this.visiable = false
    },

    handleResetLeafValue() {
      this.leafValue = []
    },

    fetchLeafById(id) {
      if (!id) return
      this.ease.onFetchSku(id).then(skuOptions => {
        this.id = id
        this.skuOptions = skuOptions
        let skuList = []
        // 筛选未选择的属性
        this.skuOptions.forEach(skuItem => {
          if (!this.sku.leaf.find(item => item.id === skuItem.id)) {
            skuList.push(skuItem)
          }
        })
        this.skuOptions = skuList
      })
    },

    handleRemoveSkuLeaf(index) {
      let { sku } = this
      sku.leaf.splice(index, 1)

      this.onSkuLeafChange(sku.leaf)
    },

    handleRemoveImage(id) {
      let { sku, optionValue } = this
      sku?.leaf?.forEach(item => {
        if (item[optionValue] === id) {
          item.imgUrl = ''
        }
      })

      this.onSkuLeafChange(sku.leaf)
    },

    filterSkuOptions(data) {
      const oldSellData = [];
      const addSelData = []
      const skuOptions = this.skuOptions
      data.forEach(item => {
        if (skuOptions.find(skuItem => skuItem.id === item)) {
          oldSellData.push(item)
        } else {
          addSelData.push(item)
        }
      })
      return {
        oldSellData, addSelData
      }
    },

    createSkuLeaf(selVal) {
      let { sku, optionValue, skuOptions } = this
      // 过滤需要新增的规格值
      // data = data.filter(item => typeof (item) === 'string')
      const { addSelData, oldSellData } = this.filterSkuOptions(selVal)
      if (!addSelData.length) return
      this.ease.onCreateSku({
        data: addSelData,
        id: sku[optionValue]
      }).then((processedNewOptions) => {
        if (processedNewOptions[0].text.length > 20) {
          this.$message({
            message: `属性名长度不可超过20个字符`,
            duration: 1500
          })
          processedNewOptions = []
        }
        skuOptions.push(...processedNewOptions)
        this.$nextTick(() => {
          const values = processedNewOptions.map(item => item.id).concat(oldSellData)
          this.leafValue = values;

          // const values = processedNewOptions.map(item => item.id)
          // this.leafValue = this.leafValue.filter(item => typeof (item) === 'number')
          // this.leafValue.push(...values)
        })
      })
    },

    handleSelectSku(data) {
      let { sku, hasSkuImage, optionValue, optionText, skuOptions, leafValue } = this
      let skuLeaf = skuOptions.filter(item => leafValue.indexOf(item[optionValue]) >= 0)
      skuLeaf.map(item => {
        item.is_show = hasSkuImage
      })

      let skuLeafIds = sku.leaf.map(item => item[optionValue])


      skuLeaf.forEach(item => {
        if (skuLeafIds.indexOf(item[optionValue]) < 0) {
          sku.leaf.push(item)
        }
      })

      // 过滤同名规格值
      for (var i = 0; i < sku.leaf.length; i++) {
        for (var j = i + 1; j < sku.leaf.length; j++) {
          if (sku.leaf[i][optionText] === sku.leaf[j][optionText]) {
            sku.leaf.splice(i, 1)
            j--
          }
        }
      }

      this.handleResetLeafValue()
      this.handleHideVisiable()
      this.onSkuLeafChange(sku.leaf)
    },

    handleUploadSuccess(item, urls) {
      this.onSkuLeafChange(this.sku.leaf)
    },
    // handleUploadSuccess2(response, file, fileList, id) {
    //   let { sku, optionValue } = this

    //   sku.leaf.forEach(item => {
    //     if (item[optionValue] === id) {
    //       item.imgUrl = response.imgUrl
    //     }
    //   })

    //   this.onSkuLeafChange(sku.leaf)
    // },

    // 图片预览
    picturePreview(imgUrl) {
      this.ease.onPreviewImg(imgUrl)
    },

    created() {
      let { sku, optionValue } = this
      sku[optionValue] && this.fetchLeafById(sku[optionValue])
    }

  },

  created() {
    let { sku, optionValue } = this
    sku[optionValue] && this.fetchLeafById(sku[optionValue])
  }
}
</script>

<style lang="scss">
.group-container {
  .spec-name {
    margin: 15px 5px;
    .spec-name-int {
      display: inline-block;
      margin-bottom: 15px;
      margin-right: 15px;
      vertical-align: top;
      // 属性名称
      .spec-imgbox {
        display: block;
        width: 60px;
        height: 60px;
        position: relative;
        .preview-btn {
          position: absolute;
          bottom: -1px;
          left: 1px;
          display: none;
          width: 100%;
          background: rgba(0, 0, 0, 0.2);
          line-height: 1.5em;
          text-align: center;
          font-size: 12px;
          border-radius: 0 0 6px 6px;
          cursor: pointer;
        }
        .spec-img {
          // margin-left: 25px;
          margin-top: 10px;
          // sku上传图片组件大小修改
          .plugin-images {
            .el-upload {
              .pic-uploader-icon {
                width: 60px;
                height: 60px;
                line-height: 60px;
                font-size: 18px;
              }
              .pic {
                height: 60px;
              }
            }
          }
        }
      }
      .spec-imgbox:hover .preview-btn {
        display: block;
      }
      .error-tips {
        margin-left: 25px;
        margin-top: 10px;
        color: #e43130;
      }
      .el-tag--plain {
        position: relative;
        min-width: 100px;
        color: #606266;
        border-color: #dcdfe6;
        padding-right: 22px;
      }
      .el-tag--plain .el-tag__close {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        right: 4px;
        color: #606266;
      }
      .el-tag--plain .el-tag__close:hover {
        background: #dcdfe6;
      }
    }
    .add-spec-btn {
      display: inline-block;
      font-size: 12px;
      line-height: 1.5em;
      margin-top: 10px;
      color: #02a1e9;
      cursor: pointer;
    }
    .sku-item {
      display: inline-block;
    }
  }
}
</style>
