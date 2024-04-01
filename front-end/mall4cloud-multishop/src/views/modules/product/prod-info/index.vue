<template>
  <div class="page-prod-info">
    <div class="title">
      {{ dataForm.spuId ? '编辑商品信息' : '发布新商品' }}
    </div>
    <el-form
      ref="dataFormRef"
      class="part-form"
      :model="dataForm"
      :rules="rules"
      label-width="120px"
    >
      <div class="info-box">
        <!-- 1.商品基本信息 -->
        <div class="part-content">
          <div class="part-tit">
            <span class="part-tit-num">1</span><span class="part-tit-name">商品基本信息</span>
          </div>
          <!-- 商品平台分类（不可修改） -->
          <div class="part-form-div">
            <div class="part-form-item-tit star">
              平台分类
            </div>
            <div class="part-form-item">
              <div class="classify-show">
                <category-group
                  :selected-categorys="selectedCategorys"
                  :show-category-select-btn="showCategorySelectBtn"
                  :single="true"
                  @select-or-revise-category="selectOrReviseCategory('platform')"
                />
                <!-- <div v-if="!dataForm.categoryId" class="warning">请先选择分类再填写以下商品信息！</div> -->
              </div>
              <category-selector
                v-if="categorySelectorVisible"
                ref="categorySelectorRef"
                @get-category-selected="getCategorySelected"
              />
            </div>
          </div>

          <!-- 商品店铺分类 -->
          <div class="part-form-div">
            <div class="part-form-item-tit star">
              店铺分类
            </div>
            <div class="part-form-item">
              <div class="classify-show">
                <category-group
                  :selected-categorys="selectedShopCategorys"
                  :show-category-select-btn="showShopCategorySelectBtn"
                  :single="true"
                  @select-or-revise-category="selectOrReviseCategory('shop')"
                />
                <div
                  v-if="!dataForm.categoryId || !dataForm.shopCategoryId"
                  class="warning"
                >
                  请先选择分类再填写以下商品信息！
                </div>
              </div>
              <category-selector
                v-if="categorySelectorVisible"
                ref="categorySelectorRef"
                @get-category-selected="getCategorySelected"
              />
            </div>
          </div>

          <!-- 商品标题 -->
          <el-form-item
            label="商品标题"
            prop="name"
          >
            <el-input
              v-model.trim="dataForm.name"
              placeholder="商品标题组成：商品描述+属性"
              :disabled="!dataForm.categoryId"
              style="width:90%"
            />
          </el-form-item>
          <!-- 商品卖点 -->
          <el-form-item
            label="商品卖点"
            prop="sellingPoint"
          >
            <el-input
              v-model.trim="dataForm.sellingPoint"
              type="textarea"
              placeholder="商品卖点"
              :disabled="!dataForm.categoryId"
              style="width:90%"
            />
          </el-form-item>
          <!-- 商品品牌 -->
          <el-form-item label="所属品牌">
            <div class="brand">
              <img
                v-if="brandImgUrl"
                :src="brandImgUrl"
                class="brand-img"
              >
              <el-tag
                v-if="brandName"
                class="br"
                :disable-transitions="true"
                closable
                @close="handleCloseBrand()"
              >
                <span class="brandname">{{ brandName }}</span>
              </el-tag>
              <el-button
                type="primary"
                link
                :class="['sel-brand', dataForm.categoryId ? '' : 'gray']"
                @click="brandSelectHandle()"
              >
                {{ brandName ? '修改' : '选择' }}品牌
              </el-button>
            </div>
            <brand-selector
              v-if="brandSelectVisible"
              ref="brandSelectRef"
              :is-single="true"
              @refresh-select-brand="selectBrand"
            />
          </el-form-item>
          <!-- 商品基本属性 -->
          <el-form-item
            v-if="basicAttrs.length > 0"
            label="基本属性"
          >
            <div class="text">
              请准确填写属性，有利于商品在搜索和推荐中露出，错误填写可能面临商品下架或流量损失！（注：带<span class="stars-icon" />为重要属性，必填）
            </div>
            <spu-category-attrs
              :attrs-list="basicAttrs"
              @get-value-of-basic-attrs="getValueOfBasicAttrs"
            />
          </el-form-item>
          <!-- 商品排序 -->
          <el-form-item label="商品排序">
            <el-input
              v-model.number="dataForm.seq"
              :max="32767"
              oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
              placeholder="请输入商品排序"
              :min="1"
              validate-event
              style="width:200px"
            />
          </el-form-item>
          <!-- 商品轮播图 -->
          <div class="part-form-div">
            <div class="part-form-item-tit star">
              商品主图
            </div>
            <div class="part-form-item">
              <img-upload v-model="dataForm.mainImgUrl" />
            </div>
          </div>
          <!-- 商品轮播图 -->
          <div class="part-form-div">
            <div class="part-form-item-tit star">
              商品轮播图
            </div>
            <div class="part-form-item">
              <imgs-upload v-model="dataForm.imgUrls" />
              <div class="banner-tips">
                建议图片尺寸为 800*800，可拖动排序，最多上传9张
              </div>
            </div>
          </div>
        </div>

        <!-- 2.商品sku与库存 -->
        <div class="part-content">
          <div class="part-tit">
            <span class="part-tit-num">2</span><span class="part-tit-name">商品销售属性与库存</span>
          </div>
          <div class="part-form-div">
            <div class="part-form-item-tit">
              销售属性
            </div>
            <div class="part-form-item">
              <div class="part-tips">
                最多添加两个商品属性，第一个商品属性可添加属性图片
              </div>
              <!-- sku-组件 -->
              <sku-block
                v-model:salesAttrs="salesAttrs"
                v-model:save-sales-attrs="originalSalesAttrs"
                v-model:sku-tree="originalSkuTree"
                v-model:has-sku-val="hasSkuValue"
                :on-fetch-group="fetchSkuTree"
                :on-fetch-sku="fetchSku"
                :on-create-sku="createSku"
                :on-create-group="createGroup"
                :on-preview-img="picturePreview"
                :category-id="dataForm.shopCategoryId"
                @on-change="changeSkuGroupData"
              />
              <!-- :is-category-id="dataForm.categoryId ? true : false" -->
            </div>
          </div>
          <!-- 价格及库存 -->
          <div class="part-form-div">
            <div class="part-form-item-tit star">
              价格及库存
            </div>
            <div class="setup-spec">
              <div class="imp-tips">
                请如实填写库存信息，以确保商品可以在承诺发货时间内发出，避免可能的物流违规
              </div>
              <div class="sku-table">
                <!-- sku表格 -->
                <sku-table
                  :data="salesAttrs"
                  :flatten="flatten"
                  :is-no-sku-value="isNoSkuValue"
                  :spu-id="parseInt(dataForm.spuId)"
                  :is-category-id="dataForm.shopCategoryId ? true : false"
                  @on-change-data="handleChangeData"
                />
              </div>
            </div>
          </div>
          <div class="part-form-div">
            <div class="part-form-item-tit">
              总库存
              <el-tooltip
                class="item"
                effect="light"
                content="每个属性库存的总和"
                placement="top"
              >
                <i class="el-icon-question" />
              </el-tooltip>
            </div>
            <el-input
              v-model="dataForm.totalStock"
              :readonly="true"
              style="width:150px"
            />
          </div>
        </div>

        <!-- 3.商品详情 -->
        <div class="part-content">
          <div class="part-tit">
            <span class="part-tit-num">3</span><span class="part-tit-name">商品详情</span>
          </div>
          <div class="part-form-info">
            <product-details v-model:dataForm="dataForm" />
          </div>
        </div>
      </div>

      <div class="foot-btn">
        <el-button
          type="primary"
          @click="changeFormatOfFormData('ruleForm')"
        >
          立即发布
        </el-button>
        <el-button @click="resetForm('ruleForm')">
          重置
        </el-button>
      </div>

      <picture-preview
        v-if="picturePreviewVisible"
        ref="picturePreviewRef"
      />
    </el-form>
  </div>
</template>

<script setup>
import * as api from '@/api/product/prod-info'
import { shopCategoryPage, platformCategoryPage, getShopAttrs } from '@/api/product/category'
import { onMounted, reactive } from 'vue'
import Big from 'big.js'

const route = useRoute()

const Data = reactive({
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  dataForm: {
    spuId: route.query.spuId || null, // 商品id
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
  skuTree: [],
  sku: [],
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
})
const { dataForm, brandName, brandImgUrl, brandSelectVisible, basicAttrs, salesAttrs, originalSalesAttrs, categorySelectorVisible, selectedCategorys, selectedShopCategorys, showCategorySelectBtn, showShopCategorySelectBtn, originalSkuTree, rules, flatten, isNoSkuValue, picturePreviewVisible, hasSkuValue } = toRefs(Data)

onMounted(() => {
  init()
})

const dataFormRef = ref()
const init = () => {
  const spuId = Data.dataForm.spuId
  Data.dataForm.spuId = spuId || ''
  nextTick(() => {
    dataFormRef.value.resetFields()
    if (!Data.dataForm.spuId) {
      return
    }
    api.getProdInfoPage(spuId).then(data => {
      Data.dataForm = data
      Data.originalSpuAttrValueList = data.spuAttrValues
      Data.backTotalStock = data.skus.reduce((res, { stock }) => {
        res += (stock || 0)
        return res
      }, 0)
      // 回显分类
      getCategoryBack(data.categoryId, data.shopCategoryId)
      // 商品分类选择按钮
      Data.showCategorySelectBtn = false
      if (data.brand?.imgUrl) {
        const img = data.brand.imgUrl
        if (img.indexOf('http://') === 0 || img.indexOf('https://') === 0) {
          Data.brandImgUrl = img
        } else {
          Data.brandImgUrl = Data.resourcesUrl + img
        }
      }
      Data.brandName = data.brand?.name // 品牌名字
      // 根据分类获取基本属性列表
      getAttrsByCategoryId(data.categoryId, data.spuAttrValues)
      // sku回显
      skuBackShow(data.skus) // 销售属性
    })
  })
}

/**
     * 获取分类
     */
const getCategoryBack = (categoryId, shopCategoryId) => {
  let selectedOfPlatCategory = {}
  let selectedOfShopCategory = {}
  platformCategoryPage().then(data => {
    // 获取平台分类数据
    const categoryList = treeDataTranslate(data, 'categoryId', 'parentId')
    selectedOfPlatCategory = idList(categoryList, categoryId, 'categoryId', 'children').reverse()
    selectedOfPlatCategory.forEach(el => Data.selectedCategorys.push(el.name)) // 选中的分类
  })
  shopCategoryPage().then(data => {
    // 获取店铺分类数据
    const shopCategoryList = treeDataTranslate(data, 'categoryId', 'parentId')
    selectedOfShopCategory = idList(shopCategoryList, shopCategoryId, 'categoryId', 'children').reverse()
    selectedOfShopCategory.forEach(el => Data.selectedShopCategorys.push(el.name)) // 选中的分类
  })
}

/**
 * 选择/修改分类
 */
const categorySelectorRef = ref()
const selectOrReviseCategory = (key) => {
  Data.selectKey = key
  Data.categorySelectorVisible = true
  nextTick(() => {
    categorySelectorRef.value.init(0, key) // 1代表从创建分类进入
  })
}

/**
     * 获取子组件返回分类数据
     */
const getCategorySelected = (selectedCategorys, parentId) => {
  Data.categorySelectorVisible = false
  if (Data.selectKey === 'platform') {
    Data.selectedCategorys = selectedCategorys
    Data.dataForm.categoryId = parentId
    getAttrsByCategoryId(parentId) // 请求基本属性
  } else {
    Data.selectedShopCategorys = selectedCategorys
    Data.dataForm.shopCategoryId = parentId
    querySalesAttrData() // 请求销售属性
  }
}

/**
     * 根据分类获取基本属性列表
     */
const getAttrsByCategoryId = (categoryId, spuAttrValues) => {
  const param = {
    categoryId,
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
    Data.basicAttrs = data
  })
}

/**
     * 基本属性数据
     */
const getValueOfBasicAttrs = (attrsList, attrs, st) => {
  const originalSpuAttrValueList = Data.originalSpuAttrValueList
  const spuAttrValueList = Data.spuAttrValueList
  const spuAttrValue = {}
  if (Data.dataForm.spuId) { // 回显
    originalSpuAttrValueList.forEach((item) => {
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
    attrs.attrValues.forEach(attr => {
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
      ElMessage({
        message: '当前属性为必填属性，请填写属性值！',
        duration: 1500
      })
      return
    } else {
      spuAttrValueList.push(spuAttrValue)
    }
  }
  // 去重
  removeDuplication(spuAttrValueList, 'attrId')
  Data.dataForm.spuAttrValues = spuAttrValueList
}

/**
     * 选择/修改品牌
     */
const brandSelectRef = ref()
const brandSelectHandle = (value) => {
  if (!Data.dataForm.categoryId) {
    return
  }
  const brands = []
  brands.push({ brandId: Data.dataForm.brandId })
  Data.brandSelectVisible = true
  nextTick(() => {
    brandSelectRef.value.init(brands, Data.dataForm.categoryId)
  })
}

/**
     * 添加指定品牌
     */
const selectBrand = (brands) => {
  if (brands) {
    Data.brandImgUrl = brands[0].brandImgUrl
    Data.dataForm.brandId = brands[0].brandId
    Data.brandName = brands[0].brandName
    // 删除多余参数
    Data.dataForm.brand = undefined
  }
}

/**
     * 删除品牌
     */
const handleCloseBrand = () => {
  Data.brandImgUrl = ''
  Data.brandName = ''
  Data.dataForm.brandId = ''
  Data.dataForm.brand = undefined
}

/**
     * sku回显
     */
const skuBackShow = (skus) => {
  if (skus && skus.length > 0) {
    const salesAttrs = []
    const ids = []
    const attrValueNameMapId = []
    skus.forEach(sku => {
      sku.spuSkuAttrValues?.forEach((attrInfo, idx) => {
        // 无sku
        if (attrInfo.attrName === null && attrInfo.attrValueName === null) {
          Data.isNoSkuValue = true
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
      attr.leaf = removeDuplication(attr.leaf, 'id')
    })
    skus.forEach(skuItem => {
      skuItem.marketPriceFee = parseFloat(bigActualTotal(parseFloat(skuItem.marketPriceFee), 100)) // 市场价
      skuItem.priceFee = parseFloat(bigActualTotal(parseFloat(skuItem.priceFee), 100)) // 销售价
    })
    // console.log('计算后skus:', skus)
    skus.forEach((skuItem, i) => {
      Data.flatten[i] = skuItem
    })
    salesAttrs.forEach((attr, i) => {
      Data.salesAttrs[i] = attr
    })
  }
  querySalesAttrData(skus)
}

/**
     * 获取店铺中的销售属性
     */
const querySalesAttrData = (skus) => {
  if (!skus) {
    Data.originalSalesAttrs.splice(0, Data.originalSalesAttrs.length)
  }
  Data.skuTree.splice(0, Data.skuTree.length) // 清空
  getShopAttrs().then((data) => {
    Data.originalSalesAttrs = data
    let skuTrees = []
    data.forEach((item, idx) => {
      skuTrees.push({ id: item.attrId, text: item.name })
    })
    // 去重
    skuTrees = removeDuplication(skuTrees, 'id')
    Data.originalSkuTree = skuTrees
    Data.skuTree = skuTrees
  })
}

// 去重
const removeDuplication = (items, validKey) => {
  for (let i = 0; i < items.length; i++) {
    for (let j = i + 1; j < items.length; j++) {
      if (items[i][validKey] === items[j][validKey]) {
        items.splice(i, 1)
        j--
      }
    }
  }
  return items
}

const fetchSkuTree = () => {
  // 异步获取规格列表
  return new Promise(resolve => {
    resolve(Data.skuTree)
  })
}

const fetchSku = (id) => {
  // 异步获取规格可选值
  return new Promise(resolve => {
    resolve(getSkus(id))
  })
}

// 获取规格可选值
const getSkus = (id) => {
  Data.sku = []
  Data.originalSalesAttrs.forEach(attr => {
    if (id === attr.attrId) {
      attr.attrValues.forEach(attrVal => {
        const attrValue = {}
        attrValue.text = attrVal.value
        attrValue.id = attrVal.attrValueId
        Data.sku.push(attrValue)
      })
    }
  })
  return Data.sku
}

const createGroup = (text) => {
  // 创建新的规格名
  return new Promise((resolve, reject) => {
    const randomId = 'random_'
    if (text) {
      resolve(randomId + parseInt(Math.random() * 100, 10) + 1)
    } else {
      reject(new Error())
    }
  })
}

const createSku = (data) => {
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
}

// SkuGroup返回数据
const changeSkuGroupData = (data) => {
  Data.skuTree = data
  data?.[0]?.leaf.forEach(attr => {
    if (attr.is_show) {
      Data.dataForm.hasSkuImg = 1
    } else {
      Data.dataForm.hasSkuImg = 0
    }
  })
}

// SkuTable返回数据
const handleChangeData = (data) => {
  Data.skuTableData = data
  const salePrices = []
  const marketPrices = []
  let totalStock = 0
  Data.skuTableData?.forEach((sku, idx) => {
    if (!sku.marketPriceFee) {
      sku.marketPriceFee = 0
    }
    if (sku.stock) {
      totalStock += Number(sku.stock) // 库存累加
    }
    salePrices.push(sku.priceFee) // 售价
    marketPrices.push(sku.marketPriceFee) // 市场价
  })
  if (Data.dataForm.skus) {
    Data.dataForm.skus.forEach(() => {
      Data.dataForm.changeStock = totalStock - Data.backTotalStock
    })
  }
  Data.dataForm.priceFee = Math.min.apply(null, salePrices) // 最低价-售价
  Data.dataForm.marketPriceFee = Math.min.apply(null, marketPrices) // 最低价-市场价
  Data.dataForm.totalStock = totalStock // 总库存
}

/**
     * 处理提交的skuList的数据格式
     */
const changeSkuFormat = () => {
  const attrs = []
  let nameOfAttrs = [] // 属性名组合
  /** 原始sku属性选项Id数组 */
  const originalSkuIds = Data.originalSalesAttrs.map((attr) => attr.attrId)
  Data.skuTableData.forEach(skuItem => {
    if (skuItem.spuSkuAttrValues && skuItem.spuSkuAttrValues.length > 0) {
      skuItem.spuSkuAttrValues?.forEach(attr => {
        // sku图片
        Data.skuTree[0]?.leaf?.forEach(attrVal => {
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
        const originalSku = getSkus(attr.attrId)
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
    if (Data.dataForm.hasSkuImg === 0) {
      skuItem.imgUrl = ''
    }
  })
  Data.dataForm.skuList = Data.skuTableData
}

/**
     * 基本属性未填写部分数据处理
     */
const basicAttrData = () => {
  const spuAttrIds = []
  Data.dataForm.spuAttrValues.forEach(spuAttrItem => {
    spuAttrIds.push(spuAttrItem.attrId)
  })
  // 当前项是重要属性 && 已填id数组中不包含当前项id
  Data.basicAttrs.forEach(attr => {
    if (!spuAttrIds.includes(attr.attrId)) {
      Data.dataForm.spuAttrValues.push({
        attrId: attr.attrId,
        attrName: attr.name,
        attrValueId: '',
        attrValueName: ''
      })
    }
  })
  // 去重
  removeDuplication(Data.dataForm.spuAttrValues, 'attrId')
  basicAttrVerification()
}

/**
     * 基本属性填写校验
     */
const basicAttrVerification = () => {
  Data.dataForm.spuAttrValues.forEach(attrValue => {
    Data.basicAttrs.some(attr => {
      if (attr.attrId === attrValue.attrId) {
        if (attr.searchType === 1 && !attrValue.attrValueName) {
          ElMessage({
            message: '重要基本属性不能为空！',
            duration: 1000
          })
          Data.basicAttrNotComplete = true
          return true
        } else {
          Data.basicAttrNotComplete = false
        }
      }
      return false
    })
    if (Data.basicAttrNotComplete) return true
  })
}

/**
     * 表单验证
     */
const formValidation = () => {
  basicAttrData() // 基本属性校验
  if (Data.basicAttrNotComplete) {
    return false
  }
  if (!Data.dataForm.mainImgUrl) {
    ElMessage({
      message: '请上传商品主图！',
      duration: 1000
    })
    return false
  }
  if (!Data.dataForm.imgUrls) {
    ElMessage({
      message: '请上传商品轮播图！',
      duration: 1000
    })
    return false
  }
  if (Data.skuTree?.some((item) => item.id && item.leaf && !item.leaf.length)) {
    ElMessage({
      message: '请选择已添加销售属性的属性值！',
      duration: 1500
    })
    Data.hasSkuValue = false
    return false
  }
  // 勾选“添加属性图片”但未上传图片时
  if (Data.dataForm.skuList.some((skuItem, idx) => skuItem.imgUrl === '') && Data.dataForm.hasSkuImg === 1) {
    ElMessage({
      message: '请上传属性图片！',
      duration: 1000
    })
    return false
  }
  if (Data.dataForm.skuList.some((skuItem, idx) => skuItem.stock === '' || skuItem.stock === null)) {
    ElMessage({
      message: '商品库存不能为空！',
      duration: 1000
    })
    return false
  }
  if (Data.dataForm.skuList.some((skuItem, idx) => !skuItem.priceFee)) {
    ElMessage({
      message: '商品销售价格必须大于0！',
      duration: 1000
    })
    return false
  }
  return true
}

/**
     * 表单提交数据处理
     */
const changeFormatOfFormData = () => {
  dataFormRef.value.validate(valid => {
    if (!valid) {
      ElMessage({
        message: '请先将必填信息填写完整！',
        duration: 1500
      })
      return
    }
    // sku数据格式转换
    changeSkuFormat()
    // 修改商品详情时，将未修改的基本属性项push到dataForm.spuAttrValues
    if (Data.dataForm.spuId) {
      const spuAttrValIds = []
      Data.dataForm.spuAttrValues.forEach(attr => {
        spuAttrValIds.push(attr.attrId)
      })
      Data.originalSpuAttrValueList.forEach(attr => {
        if (!spuAttrValIds.includes(attr.attrId)) {
          Data.dataForm.spuAttrValues.push(attr)
        }
      })
    }
    // 表单验证
    const dataFormVal = formValidation()
    if (!dataFormVal) {
      return
    }
    // 深拷贝dataForm，使页面展示数据不受下面数据修改的影响
    const dataForm = JSON.parse(JSON.stringify(Data.dataForm))
    // deliveryMode转换格式
    dataForm.deliveryMode = JSON.stringify(dataForm.deliveryMode)
    dataForm.skuList.forEach(sku => {
      // 修改商品时，删除已有属性的spuSkuAttrValues
      if ((sku.skuId !== undefined && sku.spuId !== undefined) && sku.spuSkuAttrValues) {
        sku.spuSkuAttrValues = undefined
      }
      // 价格 * 100
      if (sku.priceFee > 0) {
        sku.priceFee = parseFloat(bigProductTotalAmount(parseFloat(sku.priceFee), 100)) // 售价*100
      }
      if (sku.marketPriceFee > 0) {
        sku.marketPriceFee = parseFloat(bigProductTotalAmount(parseFloat(sku.marketPriceFee), 100)) // 市场价*100
      }
    })
    dataForm.priceFee = parseFloat(bigProductTotalAmount(parseFloat(dataForm.priceFee), 100)) // 最低-售价*100
    dataForm.marketPriceFee = parseFloat(bigProductTotalAmount(parseFloat(dataForm.marketPriceFee), 100)) // 最低-市场价*100
    // 删除多余参数(修改商品时)
    if (Data.dataForm.skus) {
      dataForm.skus = undefined
    }
    // 请求提交表单
    dataFormSubmit(dataForm)
  })
}

/**
     * 表单提交
     */
const router = useRouter()
const dataFormSubmit = (dataForm) => {
  const request = Data.dataForm.spuId ? api.update(dataForm) : api.save(dataForm)
  request.then(() => {
    ElMessage({
      message: $t('table.actionSuccess'),
      type: 'success',
      duration: 1000,
      onClose: () => {
        resetForm()
        router.push('/product/list')
      }
    })
  })
}

// 重置
const resetForm = () => {
  dataFormRef.value.resetFields()
  Data.brandName = ''
  Data.brandImgUrl = ''
  Data.dataForm.mainImgUrl = ''
  Data.dataForm.imgUrls = ''
  Data.dataForm.seq = ''
  Data.dataForm.deliveryTemplateId = ''
  Data.dataForm.deliveryMode = {
    hasShopDelivery: true,
    hasUserPickUp: false,
    hasCityDelivery: false
  }
  Data.dataForm.detail = ''
  Data.selectedShopCategorys = []
  if (!Data.dataForm.spuId) {
    Data.selectedCategorys = []
    Data.dataForm.categoryId = ''
    Data.basicAttrs = [] // 基本属性列表
  }
}

// 精度运算-乘法
const bigProductTotalAmount = (a, b) => {
  return new Big(a).times(b).valueOf()
}

// 精度运算-除法
const bigActualTotal = (a, b) => {
  if (a == null) {
    return ''
  }
  return new Big(a).div(b).valueOf()
}

// 图片预览
const picturePreviewRef = ref()
const picturePreview = (imgUrl) => {
  Data.picturePreviewVisible = true
  nextTick(() => {
    picturePreviewRef.value.init(imgUrl)
  })
}
</script>

<style lang="scss" scoped>
.page-prod-info {
  color: #606266;
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
    padding: 0 20px;
    font-weight: bold;
    font-size: 16px;
    height: 50px;
    line-height: 50px;
  }
  .info-box {
    margin: 0 auto;
    border: 1px solid #eee;
    margin-bottom: 45px;
    .part-content {
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
          }
          // 轮播图
          .banner-tips {
            margin-top: 10px;
            font-size: 13px;
            color: #999;
          }
        }
        /* 价格及库存 */
        .setup-spec {
          width: 100%;
          box-sizing: border-box;
          background: #fafafa;
          padding: 15px;
          .imp-tips {
            font-size: 13px;
            color: #ec6b01;
            margin-bottom: 5px;
          }
          .sku-table {
            margin-top: 15px;
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
    }
    .part-content:not(:first-child) {
      border-top: 1px solid #eee;
    }
  }
  /* 脚部按钮 */
  .foot-btn {
    position: fixed;
    bottom: 0;
    width: calc(100% - 270px);
    margin-left: -20px;
    padding: 15px 0;
    display: flex;
    justify-content: center;
    background: #fff;
    // border-top: 1px solid #ddd;
    box-shadow: 0 -2px 3px rgba(139, 139, 139, 0.1);
    z-index: 111;
  }
}
</style>
