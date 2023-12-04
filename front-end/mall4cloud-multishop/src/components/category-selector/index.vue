<template>
  <el-dialog
    v-model="visible"
    :title="$t('components.selector.categorySelector')"
    :append-to-body="visible"
    class="component-category-selector"
    width="1000px"
    @close="onClose"
  >
    <div class="prod-category clearfix">
      <div class="category">
        <!-- 分类 -->
        <div
          v-if="firstCategorys.dataList.length"
          class="category-box"
        >
          <el-input
            v-model="firstCategorys.name"
            :placeholder="$t('components.selector.chooseProdCateg')"
            :disabled="true"
          />
          <ul class="category-list">
            <li
              v-for="(item, index) in firstCategorys.dataList"
              :key="item.categoryId"
              class="category-item"
              :class="item.categoryId == firstCategorys.id ? 'active' : ''"
              @click="selectFirstCategorys(item.categoryId, index)"
            >
              {{ item.name }}
            </li>
          </ul>
        </div>
        <!-- 分类 -->
        <div
          v-if="secondCategorys.dataList.length && firstCategorys.id != 0"
          class="category-box"
        >
          <el-input
            v-model="secondCategorys.name"
            :placeholder="$t('components.selector.chooseProdCateg')"
            :disabled="true"
          />
          <ul class="category-list">
            <li
              v-for="(item, index) in secondCategorys.dataList"
              :key="item.categoryId"
              class="category-item"
              :class="isCreateCategory ? 'prohibit-sel' : item.categoryId == secondCategorys.id ? 'active' : ''"
              @click="selectSecondCategorys(item.categoryId, index)"
            >
              {{ item.name }}
            </li>
          </ul>
        </div>
        <!-- 分类 -->
        <div
          v-if="showthreeCategorys && threeCategorys.dataList.length > 0 && secondCategorys.id != 0"
          class="category-box"
        >
          <el-input
            v-model="threeCategorys.name"
            :placeholder="$t('components.selector.chooseProdCateg')"
            :disabled="true"
          />
          <ul class="category-list">
            <li
              v-for="(item, index) in threeCategorys.dataList"
              :key="item.categoryId"
              class="category-item"
              :class="[isCreateCategory ? 'prohibit-sel' : item.categoryId == threeCategorys.id ? 'active' : '']"
              @click="selectThreeCategorys(item.categoryId, index)"
            >
              {{ item.name }}
            </li>
          </ul>
        </div>
      </div>
      <!-- 当前选择 -->
      <div class="current-selected">
        <span class="blod">{{ $t("components.selector.currCho") }}：</span>
        <span class="select-item">{{ firstCategorys.name }}</span>
        <span
          v-if="!isCreateCategory && secondCategorys.id"
          class="select-item"
        >&nbsp;>&nbsp;&nbsp;{{
          secondCategorys.name }}</span>
        <span
          v-if="showthreeCategorys && threeCategorys.id"
          class="select-item"
        >&nbsp;>&nbsp;&nbsp;{{
          threeCategorys.name }}</span>
      </div>
      <!-- 确认 -->
      <div class="read-rule">
        <div
          class="read-rule-txt"
          :class="buttonHighlight ? 'todo' : ''"
          @click="optionsConfirm"
        >
          {{ $t("components.selector.haveReadFol") }}
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script setup>
import * as api from '@/api/product/category'
import { reactive } from 'vue'

const emit = defineEmits(['getCategorySelected'])

const Data = reactive({
  visible: false,
  allDataList: [],
  // 第一个分类
  firstCategorys: {
    id: 0,
    name: '',
    dataList: []
  },
  // 第二个分类
  secondCategorys: {
    id: 0,
    name: '',
    dataList: []
  },
  // 第三个分类
  threeCategorys: {
    id: 0,
    name: '',
    dataList: []
  },
  parentId: 0,
  isCreateCategory: false, // 是否创建(店铺)分类选择
  showthreeCategorys: false, // 是否显示第三级分类
  buttonHighlight: false // 按钮高亮
})
const { visible, firstCategorys, secondCategorys, threeCategorys, isCreateCategory, showthreeCategorys, buttonHighlight } = toRefs(Data)

/**
 * 初始化
 */
const init = (type, key) => {
  Data.visible = true
  if (key === 'platform') {
    api.platformCategoryPage().then(data => {
      Data.allDataList = data
      Data.firstCategorys.dataList = Data.allDataList.filter(item => item.level === 0)
    })
    Data.showthreeCategorys = true // 平台分类，最高三级
  } else {
    api.shopCategoryPage().then(data => {
      Data.allDataList = data
      Data.firstCategorys.dataList = Data.allDataList.filter(item => item.level === 0)
    })
    Data.showthreeCategorys = false // 店铺分类，最高二级
  }

  Data.isCreateCategory = !!(type && type === 1)
}

// 选中第一个分类
const selectFirstCategorys = (categoryId, index) => {
  Data.secondCategorys.dataList = Data.allDataList.filter(item => item.parentId === categoryId)
  Data.firstCategorys.name = Data.firstCategorys.dataList[index].name
  Data.parentId = Data.firstCategorys.id = categoryId
  Data.secondCategorys.id = 0
  if (Data.showthreeCategorys) Data.threeCategorys.id = 0

  if (Data.isCreateCategory || (!Data.isCreateCategory && Data.secondCategorys.dataList.length == 0)) { // 创建分类
    Data.buttonHighlight = true
  } else {
    Data.buttonHighlight = false
  }
}

// 选中第二个分类
const selectSecondCategorys = (categoryId, index) => {
  if (Data.isCreateCategory) {
    return
  }
  Data.threeCategorys.dataList = Data.allDataList.filter(item => item.parentId === categoryId)
  Data.parentId = Data.secondCategorys.id = categoryId
  Data.secondCategorys.name = Data.secondCategorys.dataList[index].name
  if (Data.showthreeCategorys) Data.threeCategorys.id = 0

  if (!Data.isCreateCategory && !Data.buttonHighlight && !Data.showthreeCategorys) { // 非创建分类&&店铺分类
    Data.buttonHighlight = true
  } else {
    Data.buttonHighlight = false
  }
}

// 选中第三个分类
const selectThreeCategorys = (categoryId, index) => {
  if (Data.isCreateCategory) {
    return
  }
  Data.parentId = Data.threeCategorys.id = categoryId
  Data.threeCategorys.name = Data.threeCategorys.dataList[index].name
  Data.buttonHighlight = true
}

// 新增 / 修改
const optionsConfirm = () => {
  // 平台分类 & 没有第三级分类
  if (Data.showthreeCategorys && !Data.threeCategorys.id) {
    return
  }
  // 店铺分类 & 创建分类 & 没有第一级分类
  if (!Data.showthreeCategorys && Data.isCreateCategory && !Data.firstCategorys.id) {
    return
  }
  // 店铺分类 & 非创建分类 & 没有第二级分类
  if (!Data.showthreeCategorys && !Data.isCreateCategory && Data.secondCategorys.dataList.length > 0 && !Data.secondCategorys.id) {
    return
  }
  // Data.$store.commit('common/removeMainActiveTab')
  const selectedCategorys = []
  if (Data.firstCategorys.id) {
    selectedCategorys.push(Data.firstCategorys.name)
  }
  if (!Data.isCreateCategory && Data.secondCategorys.id) {
    selectedCategorys.push(Data.secondCategorys.name)
  }
  if (Data.showthreeCategorys && !Data.isCreateCategory && Data.threeCategorys.id) {
    selectedCategorys.push(Data.threeCategorys.name)
  }
  emit('getCategorySelected', selectedCategorys, Data.parentId)
}

// 关闭
const onClose = () => {
  Data.allDataList = []
  // 第一个分类
  Data.firstCategorys = {
    id: 0,
    name: '',
    dataList: []
  }
  // 第二个分类
  Data.secondCategorys = {
    id: 0,
    name: '',
    dataList: []
  }
  // 第三个分类
  Data.threeCategorys = {
    id: 0,
    name: '',
    dataList: []
  }
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>
.component-category-selector {
  .clearfix::after {
    display: block;
    content: "";
    visibility: hidden;
    height: 0;
    clear: both;
  }
  width: 90%;
  margin: 0 auto;
  ul,
  li {
    margin: 0;
    padding: 0;
    list-style: none;
  }

  .category-box {
    box-sizing: border-box;
    width: 32%;
    box-sizing: border-box;
    display: inline-block;
    border: 1px solid #e4e4e4;
    padding: 20px;
    background: #fff;
    box-shadow: 0px 5px 8px -4px #e2e2e2;
  }

  .category-list {
    margin-top: 10px;
    height: 250px;
    margin-left: 10px;
    overflow-y: auto;
  }
  .category-item {
    padding: 6px;
    cursor: pointer;
  }
  .category-item:hover,
  .category-item.active {
    // background: #e9f9ff;
    background: #f5f5f5;
    border-radius: 3px;
  }
  .current-selected {
    margin-top: 20px;
    border: 2px solid #fedbab;
    padding: 6px;
    background: #fffaf2;
    line-height: 1.5em;
  }
  .read-rule {
    width: 100%;
    margin-top: 20px;
  }
  .read-rule-txt {
    text-align: center;
    color: #fff;
    background: #999999;
    line-height: 3em;
    width: 50%;
    margin: 0 auto;
  }
  .blod {
    font-weight: bold;
  }
  .todo {
    background: #02a1e9;
    cursor: pointer;
  }
  .prohibit-sel {
    cursor: not-allowed;
  }
}
</style>
