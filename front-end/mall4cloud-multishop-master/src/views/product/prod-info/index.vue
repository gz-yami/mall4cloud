<template>
  <div class="mod-prod-info release-goods">
    <div class="title">{{ dataForm.spuId ? '编辑商品信息' : '发布新商品' }}</div>
    <el-form ref="dataForm" class="part-form" :model="dataForm" :rules="rules" label-width="110px">
      <div class="info-box">

        <!-- 1.商品基本信息 -->
        <div class="part-content">
          <div class="part-tit"><span class="part-tit-num">1</span><span class="part-tit-name">商品基本信息</span></div>
          <!-- 商品平台分类（不可修改） -->
          <div class="part-form-div">
            <div class="part-form-item-tit star">平台分类</div>
            <div class="part-form-item">
              <div class="classify-show">
                <category-group
                  :selected-categorys="selectedCategorys"
                  :show-category-select-btn="showCategorySelectBtn"
                  :single="true"
                  @selectOrReviseCategory="selectOrReviseCategory('platform')"
                />
                <!-- <div v-if="!dataForm.categoryId" class="warning">请先选择分类再填写以下商品信息！</div> -->
              </div>
              <category-selector v-if="categorySelectorVisible" ref="categorySelector" @getCategorySelected="getCategorySelected" />
            </div>
          </div>

          <!-- 商品店铺分类 -->
          <div class="part-form-div">
            <div class="part-form-item-tit star">店铺分类</div>
            <div class="part-form-item">
              <div class="classify-show">
                <category-group
                  :selected-categorys="selectedShopCategorys"
                  :show-category-select-btn="showShopCategorySelectBtn"
                  :single="true"
                  @selectOrReviseCategory="selectOrReviseCategory('shop')"
                />
                <div v-if="!dataForm.categoryId || !dataForm.shopCategoryId" class="warning">请先选择分类再填写以下商品信息！</div>
              </div>
              <category-selector v-if="categorySelectorVisible" ref="categorySelector" @getCategorySelected="getCategorySelected" />
            </div>
          </div>

          <!-- 商品标题 -->
          <el-form-item label="商品标题" prop="name">
            <el-input v-model.trim="dataForm.name" placeholder="商品标题组成：商品描述+属性" :disabled="!dataForm.categoryId" style="width:90%" />
          </el-form-item>
          <!-- 商品卖点 -->
          <el-form-item label="商品卖点" prop="sellingPoint">
            <el-input v-model.trim="dataForm.sellingPoint" type="textarea" placeholder="商品卖点" :disabled="!dataForm.categoryId" style="width:90%" />
          </el-form-item>
          <!-- 商品品牌 -->
          <el-form-item label="所属品牌">
            <div class="brand">
              <img v-if="brandImgUrl" :src="brandImgUrl" class="brand-img">
              <el-tag v-if="brandName" class="br" :disable-transitions="true" closable @close="handleCloseBrand()">
                <span class="brandname">{{ brandName }}</span>
              </el-tag>
              <el-button
                type="text"
                :class="['sel-brand', dataForm.categoryId ? '' : 'gray']"
                @click="brandSelectHandle()"
              >{{ brandName ? '修改' : '选择' }}品牌</el-button>
            </div>
            <brand-selector v-if="brandSelectVisible" ref="brandSelect" :is-single="true" @refreshSelectBrand="selectBrand" />
          </el-form-item>
          <!-- 商品基本属性 -->
          <el-form-item v-if="basicAttrs.length > 0" label="基本属性">
            <div class="text">请准确填写属性，有利于商品在搜索和推荐中露出，错误填写可能面临商品下架或流量损失！（注：带<span class="stars-icon" />为重要属性，必填）</div>
            <spu-category-attrs :attrs-list="basicAttrs" @getValueOfBasicAttrs="getValueOfBasicAttrs" />
          </el-form-item>
          <!-- 商品排序 -->
          <el-form-item label="商品排序">
            <el-input v-model.number="dataForm.seq" :max="32767" oninput="value=value.replace(/^\.+|[^\d.]/g,'')" placeholder="请输入商品排序" :min="1" validate-event style="width:200px" />
          </el-form-item>
          <!-- 商品轮播图 -->
          <div class="part-form-div">
            <div class="part-form-item-tit star">商品主图</div>
            <div class="part-form-item">
              <img-upload v-model="dataForm.mainImgUrl" />
            </div>
          </div>
          <!-- 商品轮播图 -->
          <div class="part-form-div">
            <div class="part-form-item-tit star">商品轮播图</div>
            <div class="part-form-item">
              <imgs-upload v-model="dataForm.imgUrls" />
              <div class="banner-tips">建议图片尺寸为 800*800，可拖动排序，最多上传9张</div>
            </div>
          </div>
        </div>

        <!-- 2.商品sku与库存 -->
        <div class="part-content">
          <div class="part-tit"><span class="part-tit-num">2</span><span class="part-tit-name">商品销售属性与库存</span></div>
          <div class="part-form-div">
            <div class="part-form-item-tit">销售属性</div>
            <div class="part-form-item">
              <div class="part-tips">最多添加两个商品属性，第一个商品属性可添加属性图片</div>
              <!-- sku-组件 -->
              <sku-block
                v-model="salesAttrs"
                :save-sales-attrs="originalSalesAttrs"
                :sku-tree="originalSkuTree"
                :on-fetch-group="fetchSkuTree"
                :on-fetch-sku="fetchSku"
                :on-create-sku="createSku"
                :on-create-group="createGroup"
                :on-preview-img="picturePreview"
                :category-id="dataForm.shopCategoryId"
                :has-sku-val="hasSkuValue"
                @on-change="changeSkuGroupData"
              />
              <!-- :is-category-id="dataForm.categoryId ? true : false" -->
            </div>
          </div>
          <!-- 价格及库存 -->
          <div class="part-form-div">
            <div class="part-form-item-tit star">价格及库存</div>
            <div class="setup-spec">
              <div class="imp-tips">请如实填写库存信息，以确保商品可以在承诺发货时间内发出，避免可能的物流违规</div>
              <div class="sku-table">
                <!-- sku表格 -->
                <sku-table :data="salesAttrs" :flatten="flatten" :is-no-sku-value="isNoSkuValue" :spu-id="parseInt(dataForm.spuId)" :is-category-id="dataForm.shopCategoryId ? true : false" @on-change-data="handleChangeData" />
              </div>
            </div>
          </div>
          <div class="part-form-div">
            <div class="part-form-item-tit">
              总库存
              <el-tooltip class="item" effect="light" content="每个属性库存的总和" placement="top">
                <i class="el-icon-question" />
              </el-tooltip>
            </div>
            <el-input v-model="dataForm.totalStock" :readonly="true" style="width:150px" />
          </div>
        </div>

        <!-- 3.商品详情 -->
        <div class="part-content">
          <div class="part-tit"><span class="part-tit-num">3</span><span class="part-tit-name">商品详情</span></div>
          <div class="part-form-info">
            <product-details :data-form="dataForm" />
          </div>
        </div>

      </div>

      <div class="foot-btn">
        <el-form-item>
          <el-button type="primary" @click="changeFormatOfFormData('ruleForm')">立即发布</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </div>

      <picture-preview v-if="picturePreviewVisible" ref="picturePreview" />

    </el-form>
  </div>
</template>

<script>
// import { run } from 'runjs'
import * as api from '@/api/product/prod-info'
import { treeDataTranslate, idList } from '@/utils'
import { shopCategoryPage, platformCategoryPage, getShopAttrs } from '@/api/product/category'
import categorySelector from '@/components/CategorySelector'
import imgsUpload from '@/components/ImgsUpload'
import imgUpload from '@/components/ImgUpload'
import skuBlock from '@/components/Sku/SkuBlock'
import skuTable from '@/components/Sku/SkuTable'
import spuCategoryAttrs from '@/components/SpuCategoryAttrs'
import productDetails from '@/components/ProductDetails'
import categoryGroup from '@/components/CategoryGroup'
import brandSelector from '@/components/BrandSelector'
import picturePreview from '@/components/PicturePreview'
import Big from 'big.js'
const skuTree = [] // 规格列表

const sku = []

export default {
  components: {
    categorySelector,
    imgUpload,
    imgsUpload,
    skuBlock,
    skuTable,
    spuCategoryAttrs,
    productDetails,
    categoryGroup,
    brandSelector,
    picturePreview
  },
  data() {
    return {
      resourcesUrl: process.env.VUE_APP_RESOURCES_URL,
      dataForm: {
        spuId: this.$route.query.spuId || null, // 商品id
        categoryId: null, // 分类id
        shopCategoryId: null, // 店铺分类id
        name: '', // 商品名称
        sellingPoint: '', // 商品卖点
        brandId: 0, // 品牌id（非必选）
        imgUrls: '', // 轮播图 多个图片逗号分隔
        isCompose: 0, // 是否为组合商品0普通商品，1组合商品
        mainImgUrl: '', // 商品主图
        hasSkuImg: 0, // sku是否含有图片 0无 1有
        scoreFee: 0, // 积分价格（非必填）
        seq: null, // 商品排序（非必填）
        spuAttrValues: [], // 商品属性值列表
        detail: '', // 详情信息（非必填）
        skuList: [], // sku列表
        totalStock: 0 // 商品总库存
      },
      backTotalStock: null, // 回显总库存
      brandName: '', // 品牌名称
      brandImgUrl: '', // 品牌logo
      brandSelectVisible: false, // 品牌选择组件显隐
      basicAttrs: [], // 基本属性列表
      salesAttrs: [], // 销售属性列表
      originalSalesAttrs: [], // 起初的(不变的)销售属性
      basicAttrNotComplete: false, // 基本属性不完整
      categorySelectorVisible: false, // 分类选择器弹窗显隐
      selectedCategorys: [], // 选中的平台分类
      selectedShopCategorys: [], // 选中的店铺分类
      showCategorySelectBtn: true, // '选择分类'按钮显隐
      showShopCategorySelectBtn: true, // 店铺'选择分类'按钮显隐
      spuAttrValueList: [], // 商品基本属性值列表
      value: null,
      skuTree,
      sku,
      checked: false,
      content: '',
      contentCn: '',
      skuTableData: [], // sku表格返回数据
      originalSkuTree: [], // sku属性选项列表(不受改变影响)
      rules: {
        name: [
          { required: true, message: '请输入商品标题', trigger: 'blur' }
        ],
        sellingPoint: [
          { required: true, message: '请输入商品卖点', trigger: 'blur' }
        ],
        seq: [
          { required: true, message: '请输入商品排序', trigger: 'blur' }
        ]
      },
      flatten: [],
      originalSpuAttrValueList: [], // 基本属性回显原数据
      isNoSkuValue: false, // 回显时的商品是否有sku
      picturePreviewVisible: false, // 预览图片弹窗
      hasSkuValue: true // 是否有属性值
    }
  },

  created() {
    this.init()
  },

  methods: {
    init() {
      const spuId = this.dataForm.spuId
      this.dataForm.spuId = spuId || ''
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (!this.dataForm.spuId) {
          return
        }
        api.getProdInfoPage(spuId).then(data => {
          console.log('获取data:', data)
          this.dataForm = data
          this.originalSpuAttrValueList = data.spuAttrValues
          this.backTotalStock = data.skus.reduce((res, { stock }) => {
            res += (stock || 0)
            return res
          }, 0)
          // 回显分类
          console.log('回显分类')
          this.getCategoryBack(data.categoryId, data.shopCategoryId)
          // 商品分类选择按钮
          this.showCategorySelectBtn = false
          if (data.brand?.imgUrl) {
            const img = data.brand.imgUrl
            if (img.indexOf('http://') === 0 || img.indexOf('https://') === 0) {
              this.brandImgUrl = img
            } else {
              this.brandImgUrl = this.resourcesUrl + img
            }
          }
          this.brandName = data.brand?.name // 品牌名字
          // 根据分类获取基本属性列表
          this.getAttrsByCategoryId(data.categoryId, data.spuAttrValues)
          // sku回显
          this.skuBackShow(data.shopCategoryId, data.skus) // 销售属性
        })
      })
    },
    /**
     * 获取分类
     */
    getCategoryBack(categoryId, shopCategoryId) {
      let selectedOfPlatCategory = {}
      let selectedOfShopCategory = {}
      platformCategoryPage().then(data => {
        console.log(data)
        // 获取平台分类数据
        const categoryList = treeDataTranslate(data, 'categoryId', 'parentId')
        selectedOfPlatCategory = idList(categoryList, categoryId, 'categoryId', 'children').reverse()
        console.log('选中的平台分类selectedOfPlatCategory:', selectedOfPlatCategory)
        selectedOfPlatCategory.forEach(el => this.selectedCategorys.push(el.name)) // 选中的分类
        console.log('选中的平台分类selectedCategorys:', this.selectedCategorys)
      })
      shopCategoryPage().then(data => {
        // 获取店铺分类数据
        const shopCategoryList = treeDataTranslate(data, 'categoryId', 'parentId')
        selectedOfShopCategory = idList(shopCategoryList, shopCategoryId, 'categoryId', 'children').reverse()
        selectedOfShopCategory.forEach(el => this.selectedShopCategorys.push(el.name)) // 选中的分类
      })
    },

    /**
     * 选择/修改分类
     */
    selectOrReviseCategory(key) {
      console.log('选择分类key:', key)
      this.selectKey = key
      this.categorySelectorVisible = true
      this.$nextTick(() => {
        this.$refs.categorySelector.init(0, key) // 1代表从创建分类进入
      })
    },

    /**
     * 获取子组件返回分类数据
     */
    getCategorySelected(selectedCategorys, parentId) {
      console.log('平台分类子组件数据：selectedCategorys:', selectedCategorys, '；parentId:', parentId)
      this.categorySelectorVisible = false
      if (this.selectKey === 'platform') {
        this.selectedCategorys = selectedCategorys
        this.dataForm.categoryId = parentId
        this.getAttrsByCategoryId(parentId) // 请求基本属性
      } else {
        this.selectedShopCategorys = selectedCategorys
        this.dataForm.shopCategoryId = parentId
        this.querySalesAttrData() // 请求销售属性
      }
      console.log('店铺分类selectedShopCategorys:', this.selectedShopCategorys)
      console.log('平台分类selectedCategorys:', this.selectedCategorys)
      console.log('dataForm:', this.dataForm)
    },

    /**
     * 根据分类获取基本属性列表
     */
    getAttrsByCategoryId(categoryId, spuAttrValues) {
      const param = {
        categoryId: categoryId,
        attrType: 1 // 基本属性
      }
      api.getAttrsByCategoryId(param).then((data) => {
        // 获取数据回显
        if (spuAttrValues) {
          data.forEach((attr, idx) => {
            spuAttrValues.forEach(spuAttr => {
              if (!attr.attrValues.length && (!spuAttr.attrValueId || spuAttr.attrValueId === 0)) {
                if (attr.attrId === spuAttr.attrId) {
                  attr.attrValue = spuAttr.attrValueName
                }
              } else {
                if (attr.attrValues.some(attr => spuAttr.attrValueId === attr.attrValueId)) {
                  attr.attrValue = spuAttr.attrValueId
                }
              }
            })
          })
        }
        this.basicAttrs = data
      })
    },

    /**
     * 基本属性数据
     */
    getValueOfBasicAttrs(attrsList, attrs, st) {
      const originalSpuAttrValueList = this.originalSpuAttrValueList
      const spuAttrValueList = this.spuAttrValueList
      const spuAttrValue = {}
      if (this.dataForm.spuId) { // 回显
        originalSpuAttrValueList.forEach((item, index) => {
          if (item.attrId === attrs.attrId) {
            attrs.spuAttrValueId = item.spuAttrValueId
            spuAttrValue.spuAttrValueId = attrs.spuAttrValueId
          }
        })
      }
      // 获取基本属性子组件返回数据
      spuAttrValue.attrId = attrs.attrId
      spuAttrValue.attrName = attrs.name
      if (st === 0) { // 下拉选择
        attrs.attrValues.some(attr => {
          if (attrs.attrValue === attr.attrValueId) {
            spuAttrValue.attrValueId = attrs.attrValue
            spuAttrValue.attrValueName = attr.value
            spuAttrValueList.push(spuAttrValue)
            return true
          }
        })
      }
      if (st === 1) { // 输入框
        spuAttrValue.attrValueId = ''
        spuAttrValue.attrValueName = attrs.attrValue
        if (attrs.searchType === 1 && !attrs.attrValue) {
          this.$message({
            message: '当前属性为必填属性，请填写属性值！',
            duration: 1500
          })
          return
        } else {
          spuAttrValueList.push(spuAttrValue)
        }
      }
      // 去重
      this.removeDuplication(spuAttrValueList, 'attrId')
      this.dataForm.spuAttrValues = spuAttrValueList
    },

    /**
     * 选择/修改品牌
     */
    brandSelectHandle(value) {
      if (!this.dataForm.categoryId) {
        return
      }
      const brands = []
      brands.push({ brandId: this.dataForm.brandId })
      this.brandSelectVisible = true
      this.$nextTick(() => {
        this.$refs.brandSelect.init(brands, this.dataForm.categoryId)
      })
    },
    /**
     * 添加指定品牌
     */
    selectBrand(brands) {
      if (brands) {
        this.brandImgUrl = brands[0].brandImgUrl
        this.dataForm.brandId = brands[0].brandId
        this.brandName = brands[0].brandName
        // 删除多余参数
        this.dataForm.brand = undefined
      }
    },
    /**
     * 删除品牌
     */
    handleCloseBrand() {
      this.brandImgUrl = ''
      this.brandName = ''
      this.dataForm.brandId = ''
      this.dataForm.brand = undefined
    },

    /**
     * sku回显
     */
    skuBackShow(categoryId, skus) {
      if (skus && skus.length > 0) {
        const salesAttrs = []
        const ids = []
        const attrValueNameMapId = []
        skus.forEach(sku => {
          sku.spuSkuAttrValues?.forEach((attrInfo, idx) => {
            // 无sku
            if (attrInfo.attrName === null && attrInfo.attrValueName === null) {
              this.isNoSkuValue = true
              return
            }

            /** 有sku */

            const randomId = 'random_'

            /** left的每一项 */
            // 每一层id一致
            ids[idx] = ids[idx] || attrInfo.attrId || randomId + parseInt(Math.random() * 100, 10) + 1 // 无attrId则随机生成一个带有前缀的新id
            attrInfo.attrId = ids[idx]

            if (!attrValueNameMapId[idx]) {
              attrValueNameMapId[idx] = {}
            }

            const attrValueId = attrValueNameMapId[idx][attrInfo.attrValueName] || attrInfo.attrValueId || randomId + parseInt(Math.random() * 100, 10) + parseInt(Math.random() * 100, 10)
            attrValueNameMapId[idx][attrInfo.attrValueName] = attrValueId
            attrInfo.attrValueId = attrValueId
            const skuValueItem = {
              id: attrValueId,
              is_show: !!sku.imgUrl,
              text: attrInfo.attrValueName
            }

            if (idx === 0) {
              skuValueItem.imgUrl = sku.imgUrl || ''
            }
            if (salesAttrs[idx] === undefined) {
              salesAttrs[idx] = {
                id: ids[idx],
                leaf: [skuValueItem],
                text: attrInfo.attrName
              }
            } else {
              salesAttrs[idx].leaf.push(skuValueItem)
            }
          })
        })

        // 去重
        salesAttrs.forEach(attr => {
          attr.leaf = this.removeDuplication(attr.leaf, 'id')
        })
        skus.some(skuItem => {
          skuItem.marketPriceFee = parseFloat(this.bigActualTotal(parseFloat(skuItem.marketPriceFee), 100)) // 市场价
          skuItem.priceFee = parseFloat(this.bigActualTotal(parseFloat(skuItem.priceFee), 100)) // 销售价
        })
        // console.log('计算后skus:', skus)
        skus.forEach((skuItem, i) => {
          this.$set(this.flatten, i, skuItem)
        })
        salesAttrs.forEach((attr, i) => {
          this.$set(this.salesAttrs, i, attr)
        })
        console.log('sku回显salesAttrs:', this.salesAttrs)
        // this.flatten = skus
      }
      this.querySalesAttrData(skus)
    },

    /**
     * 获取店铺中的销售属性
     */
    querySalesAttrData(skus) {
      console.log('获取店铺中的销售属性skus:', skus)
      if (!skus) {
        this.originalSalesAttrs.splice(0, this.originalSalesAttrs.length)
      }
      this.skuTree.splice(0, this.skuTree.length) // 清空
      getShopAttrs().then((data) => {
        console.log('获取店铺中的销售属性data:', data)
        this.originalSalesAttrs = data
        let skuTrees = []
        data.forEach((item, idx) => {
          skuTrees.push({ id: item.attrId, text: item.name })
        })
        // 去重
        skuTrees = this.removeDuplication(skuTrees, 'id')
        this.originalSkuTree = skuTrees
        this.skuTree = skuTrees
      })
    },
    // 去重
    removeDuplication(items, validKey = validKey) {
      for (var i = 0; i < items.length; i++) {
        for (var j = i + 1; j < items.length; j++) {
          if (items[i][validKey] === items[j][validKey]) {
            items.splice(i, 1)
            j--
          }
        }
      }
      return items
    },
    fetchSkuTree() {
      // 异步获取规格列表
      return new Promise(resolve => {
        resolve(skuTree)
      })
    },
    fetchSku(id) {
      // 异步获取规格可选值
      return new Promise(resolve => {
        resolve(this.getSkus(id))
      })
    },
    // 获取规格可选值
    getSkus(id) {
      this.sku = []
      this.originalSalesAttrs.forEach(attr => {
        if (id === attr.attrId) {
          attr.attrValues.forEach(attrVal => {
            const attrValue = {}
            attrValue.text = attrVal.value
            attrValue.id = attrVal.attrValueId
            this.sku.push(attrValue)
          })
        }
      })
      return this.sku
    },
    createGroup(text) {
      // 创建新的规格名
      return new Promise((resolve, reject) => {
        const randomId = 'random_'
        if (text) {
          resolve(randomId + parseInt(Math.random() * 100, 10) + 1)
        } else {
          reject(new Error())
        }
      })
    },
    createSku(data) {
      // 创建新的规格值
      return new Promise((resolve, reject) => {
        const randomId = 'random_'
        resolve(data.data.map(item => {
          return {
            id: randomId + parseInt(Math.random() * 100, 10) + parseInt(Math.random() * 100, 10),
            text: item,
            leaf: []
          }
        }))
      })
    },
    // SkuGroup返回数据
    changeSkuGroupData(data) {
      // console.log('SkuGroup返回data:', data)
      this.skuTree = data
      data?.[0]?.leaf.forEach(attr => {
        if (attr.is_show) {
          this.dataForm.hasSkuImg = 1
        } else {
          this.dataForm.hasSkuImg = 0
        }
      })
    },
    // SkuTable返回数据
    handleChangeData(data) {
      // console.log('表格SkuTable返回data:', data)
      this.skuTableData = data
      const salePrices = []
      const marketPrices = []
      let totalStock = 0
      this.skuTableData?.forEach((sku, idx) => {
        if (!sku.marketPriceFee) {
          sku.marketPriceFee = 0
        }
        if (sku.stock) {
          totalStock += Number(sku.stock) // 库存累加
        }
        salePrices.push(sku.priceFee) // 售价
        marketPrices.push(sku.marketPriceFee) // 市场价
      })
      if (this.dataForm.skus) {
        this.dataForm.skus.some((backSkuItem, backIdx) => {
          this.dataForm.changeStock = totalStock - this.backTotalStock
        })
      }
      this.dataForm.priceFee = Math.min.apply(null, salePrices) // 最低价-售价
      this.dataForm.marketPriceFee = Math.min.apply(null, marketPrices) // 最低价-市场价
      this.dataForm.totalStock = totalStock // 总库存
    },

    /**
     * 处理提交的skuList的数据格式
     */
    changeSkuFormat() {
      const { originalSalesAttrs, skuTree, skuTableData } = this
      const attrs = []
      let nameOfAttrs = [] // 属性名组合
      /** 原始sku属性选项Id数组 */
      const originalSkuIds = originalSalesAttrs.map((attr) => attr.attrId)
      skuTableData.forEach(skuItem => {
        if (skuItem.spuSkuAttrValues && skuItem.spuSkuAttrValues.length > 0) {
          skuItem.spuSkuAttrValues?.forEach(attr => {
            // sku图片
            skuTree[0]?.leaf?.forEach(attrVal => {
              if (attr.attrValueId === attrVal.id) {
                if (attrVal.is_show) {
                  skuItem.imgUrl = attrVal.imgUrl ? attrVal.imgUrl : ''
                }
              }
            })
            // 新增(手动输入)属性的id为空
            if (!originalSkuIds.includes(attr.attrId)) {
              attr.attrId = ''
            }
            const originalSku = this.getSkus(attr.attrId)
            const originalSkuValIds = originalSku.map(sku => sku.id) // 原始sku属性值选项Id数组
            // 当sku属性id为空时，sku属性值id也为空 || 请求到的属性值列表中不包含当前的id
            if (!attr.attrId || !originalSkuValIds.includes(attr.attrValueId)) {
              // 新增(手动输入)属性值的id为空
              attr.attrValueId = ''
            }
            // 多个属性名收集
            attrs.push(attr.attrName)
          })

          // attrs（属性名拼接）
          nameOfAttrs = Array.from(new Set(attrs))
          skuItem.attrs = nameOfAttrs.join(',') // 多个销售属性名逗号分隔  如：'颜色,版本'
          // skuName（属性值拼接）
          if (skuItem.spuSkuAttrValues[1]) {
            skuItem.skuName = [skuItem.spuSkuAttrValues[0].attrValueName, skuItem.spuSkuAttrValues[1].attrValueName].join(' ') // 属性值名称 如：'红色 256G'
          } else if (skuItem.spuSkuAttrValues[0] && !skuItem.spuSkuAttrValues[1]) {
            skuItem.skuName = String(skuItem.spuSkuAttrValues[0].attrValueName)
          }
        } else {
          skuItem.spuSkuAttrValues = []
        }
        if (this.dataForm.hasSkuImg === 0) {
          skuItem.imgUrl = ''
        }
      })
      this.dataForm.skuList = skuTableData
      // console.log('处理skuList数据后this.dataForm.skuList:', this.dataForm.skuList)
    },

    /**
     * 基本属性未填写部分数据处理
     */
    basicAttrData() {
      const spuAttrIds = []
      this.dataForm.spuAttrValues.some(spuAttrItem => {
        spuAttrIds.push(spuAttrItem.attrId)
      })
      // 当前项是重要属性 && 已填id数组中不包含当前项id
      this.basicAttrs.forEach(attr => {
        if (!spuAttrIds.includes(attr.attrId)) {
          this.dataForm.spuAttrValues.push({
            attrId: attr.attrId,
            attrName: attr.name,
            attrValueId: '',
            attrValueName: ''
          })
        }
      })
      // 去重
      this.removeDuplication(this.dataForm.spuAttrValues, 'attrId')
      this.basicAttrVerification()
    },

    /**
     * 基本属性填写校验
     */
    basicAttrVerification() {
      this.dataForm.spuAttrValues.some(attrValue => {
        this.basicAttrs.some(attr => {
          if (attr.attrId === attrValue.attrId) {
            if (attr.searchType === 1 && !attrValue.attrValueName) {
              this.$message({
                message: '重要基本属性不能为空！',
                duration: 1000
              })
              this.basicAttrNotComplete = true
              return true
            } else {
              this.basicAttrNotComplete = false
            }
          }
        })
        if (this.basicAttrNotComplete) return true
      })
    },

    /**
     * 表单验证
     */
    formValidation() {
      const { dataForm } = this
      this.basicAttrData() // 基本属性校验
      if (this.basicAttrNotComplete) {
        return false
      }
      if (!dataForm.mainImgUrl) {
        this.$message({
          message: '请上传商品主图！',
          duration: 1000
        })
        return false
      }
      if (!dataForm.imgUrls) {
        this.$message({
          message: '请上传商品轮播图！',
          duration: 1000
        })
        return false
      }
      if (this.skuTree?.some((item) => item.id && item.leaf && !item.leaf.length)) {
        this.$message({
          message: '请选择已添加销售属性的属性值！',
          duration: 1500
        })
        this.hasSkuValue = false
        return false
      }
      // 勾选“添加属性图片”但未上传图片时
      if (dataForm.skuList.some((skuItem, idx) => skuItem.imgUrl === '') && dataForm.hasSkuImg === 1) {
        this.$message({
          message: '请上传属性图片！',
          duration: 1000
        })
        return false
      }
      if (dataForm.skuList.some((skuItem, idx) => skuItem.stock === '' || skuItem.stock === null)) {
        this.$message({
          message: '商品库存不能为空！',
          duration: 1000
        })
        return false
      }
      if (dataForm.skuList.some((skuItem, idx) => !skuItem.priceFee)) {
        this.$message({
          message: '商品销售价格必须大于0！',
          duration: 1000
        })
        return false
      }
      return true
    },

    /**
     * 表单提交数据处理
     */
    changeFormatOfFormData() {
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          this.$message({
            message: '请先将必填信息填写完整！',
            duration: 1500
          })
          return
        }
        // sku数据格式转换
        this.changeSkuFormat()
        // 修改商品详情时，将未修改的基本属性项push到dataForm.spuAttrValues
        if (this.dataForm.spuId) {
          const spuAttrValIds = []
          this.dataForm.spuAttrValues.forEach(attr => {
            spuAttrValIds.push(attr.attrId)
          })
          this.originalSpuAttrValueList.forEach(attr => {
            if (!spuAttrValIds.includes(attr.attrId)) {
              this.dataForm.spuAttrValues.push(attr)
            }
          })
        }
        // 表单验证
        const dataFormVal = this.formValidation()
        if (!dataFormVal) {
          return
        }
        // 深拷贝dataForm，使页面展示数据不受下面数据修改的影响
        const dataForm = JSON.parse(JSON.stringify(this.dataForm))
        // deliveryMode转换格式
        dataForm.deliveryMode = JSON.stringify(dataForm.deliveryMode)
        dataForm.skuList.forEach(sku => {
          // 修改商品时，删除已有属性的spuSkuAttrValues
          if ((sku.skuId !== undefined && sku.spuId !== undefined) && sku.spuSkuAttrValues) {
            sku.spuSkuAttrValues = undefined
          }
          // 价格 * 100
          if (sku.priceFee > 0) {
            sku.priceFee = parseFloat(this.bigProductTotalAmount(parseFloat(sku.priceFee), 100)) // 售价*100
          }
          if (sku.marketPriceFee > 0) {
            sku.marketPriceFee = parseFloat(this.bigProductTotalAmount(parseFloat(sku.marketPriceFee), 100)) // 市场价*100
          }
        })
        dataForm.priceFee = parseFloat(this.bigProductTotalAmount(parseFloat(dataForm.priceFee), 100)) // 最低-售价*100
        dataForm.marketPriceFee = parseFloat(this.bigProductTotalAmount(parseFloat(dataForm.marketPriceFee), 100)) // 最低-市场价*100
        // 删除多余参数(修改商品时)
        if (this.dataForm.skus) {
          dataForm.skus = undefined
        }
        console.log('提交前dataForm:', dataForm)
        // 请求提交表单
        this.dataFormSubmit(dataForm)
      })
    },

    /**
     * 表单提交
     */
    dataFormSubmit(dataForm) {
      const request = this.dataForm.spuId ? api.update(dataForm) : api.save(dataForm)
      request.then(data => {
        this.$message({
          message: this.$t('table.actionSuccess'),
          type: 'success',
          duration: 1000,
          onClose: () => {
            this.resetForm()
            // this.$router.go(0)
            this.$router.push('/product/list')
          }
        })
      })
    },

    // 重置
    resetForm() {
      this.$refs['dataForm'].resetFields()
      this.brandName = ''
      this.brandImgUrl = ''
      this.dataForm.mainImgUrl = ''
      this.dataForm.imgUrls = ''
      this.dataForm.seq = ''
      this.dataForm.deliveryTemplateId = ''
      this.dataForm.deliveryMode = {
        hasShopDelivery: true,
        hasUserPickUp: false,
        hasCityDelivery: false
      }
      this.dataForm.detail = ''
      if (!this.dataForm.spuId) {
        this.selectedCategorys = []
        this.dataForm.categoryId = ''
        this.basicAttrs = [] // 基本属性列表
      }
      console.log('重置this.dataForm:', this.dataForm)
    },

    // 精度运算-乘法
    bigProductTotalAmount(a, b) {
      return new Big(a).times(b).valueOf()
    },
    // 精度运算-除法
    bigActualTotal(a, b) {
      if (a == null) {
        return ''
      }
      return new Big(a).div(b).valueOf()
    },

    // 图片预览
    picturePreview(imgUrl) {
      this.picturePreviewVisible = true
      this.$nextTick(() => {
        this.$refs.picturePreview.init(imgUrl)
      })
    }

  }
}
</script>

<style lang="scss">
.mod-prod-info.release-goods {
  color: #606266;
  .el-radio__input.is-checked .el-radio__inner,
  .el-checkbox__input.is-checked .el-checkbox__inner {
    border-color: #02a1e9;
    background: #02a1e9;
  }
  .el-radio__input.is-checked + .el-radio__label,
  .el-checkbox__input.is-checked + .el-checkbox__label {
    color: #02a1e9;
  }
  .el-checkbox__input.is-disabled + span.el-checkbox__label {
    color: #aaabac;
  }
  .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner {
    background-color: #f2f6fc;
    border-color: #dcdfe6;
  }
  .star::before {
    // *样式
    content: "*";
    color: #ff4949;
    margin-right: 4px;
  }
  // 三角形样式
  .stars-icon {
    display: inline-block;
    width: 12px;
    height: 8px;
  }
  .stars-icon::before {
    position: absolute;
    content: "";
    width: 0;
    height: 0;
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-bottom: 8px solid#ff7300;
  }
  .title {
    max-width: 1200px;
    padding: 0 20px;
    font-weight: bold;
    font-size: 16px;
    height: 50px;
    line-height: 50px;
  }
  .info-box {
    max-width: 1200px;
    margin: 0 auto;
    border: 1px solid #eee;
    margin-bottom: 45px;
    .part-content {
      .prohibit {
        // 禁止点击
        cursor: not-allowed;
      }
      // 品牌
      .brand {
        display: flex;
        align-items: center;
        .brand-img {
          display: inline-block;
          margin-right: 6px;
          width: auto;
          max-width: 60px;
          height: auto;
          max-height: 40px;
        }
        .br {
          margin-right: 20px;
        }
        .el-button--text {
          color: #02a1e9;
        }
        .gray {
          color: #bcbcbc;
        }
        .sel-brand.gray:hover {
          cursor: not-allowed;
        }
      }
      padding: 20px;
      .part-tit {
        margin-bottom: 20px;
        .part-tit-num {
          color: #02a1e9;
          font-size: 25px;
          font-style: italic; /* 斜体 */
        }
        .part-tit-name {
          font-size: 15px;
          margin: 0 10px;
        }
      }
      /* part内容 */
      .el-form-item__label {
        padding-right: 15px;
      }
      .warning {
        display: inline-block;
        color: #e43130;
      }
      .part-form-div {
        display: flex;
        align-items: top;
        font-size: 14px;
        margin-bottom: 22px;
        .part-form-item-tit {
          width: 120px;
          min-width: 120px;
          text-align: right;
          padding-right: 14px;
          line-height: 36px;
          box-sizing: border-box;
        }
        .part-tips {
          color: #999;
          font-size: 13px;
          line-height: 36px;
        }
        .part-form-item {
          width: 100%;
          // 分类
          .classify-show {
            line-height: 36px;
            .classify {
              display: inline-block;
              // min-width: 200px;
              line-height: 36px;
              padding: 0;
              border: 0;
              margin-right: 20px;
            }
            .change-classify {
              color: #02a1e9;
              cursor: pointer;
              margin-left: 0;
            }
          }
          // 轮播图
          .banner-tips {
            margin-top: 10px;
            font-size: 13px;
            color: #999;
          }
        }
        // 新增销售属性
        .add-new-attr {
          margin-bottom: 20px;
          .el-form-item {
            margin-bottom: 0;
          }
        }
        /* 价格及库存 */
        .setup-spec {
          width: 100%;
          background: #fafafa;
          padding: 15px;
          .imp-tips {
            font-size: 13px;
            color: #ec6b01;
            margin-bottom: 5px;
          }
          .sku-table {
            margin-top: 15px;
            .el-table th.is-leaf {
              background: #f7f7f7;
            }
          }
        }
      }
      /* 属性面板 */
      .text {
        position: relative;
        min-width: 200px;
        color: #888;
        line-height: 36px;
        .stars-icon::before {
          // top: 15px;
          margin-left: 2px;
        }
      }
      .attr-type {
        p {
          display: inline-block;
          margin: 0;
          color: #606266;
          font-size: 13px;
        }
        p.attr-tit {
          margin-right: 20px;
        }
      }
      // 运费设置
      .part-form-logistics {
        .log-tem-tips {
          font-size: 13px;
          color: #999;
          margin-top: 5px;
        }
        .lb {
          display: inline-block;
          margin-left: 20px;
          .vertical {
            display: inline-block;
            width: 1px;
            height: 10px;
            background: #000;
            margin: 0 6px;
          }
        }
      }
    }
    .part-content:not(:first-child) {
      border-top: 1px solid #eee;
    }
  }
  /* 脚部按钮 */
  .foot-btn {
    position: fixed;
    bottom: 0;
    width: 1034px;
    padding: 15px 0;
    display: flex;
    justify-content: center;
    background: #fff;
    // border-top: 1px solid #ddd;
    box-shadow: 0 -2px 3px rgba(139, 139, 139, 0.1);
    z-index: 111;
    .el-form-item {
      margin-bottom: 0;
    }
  }
}
</style>
