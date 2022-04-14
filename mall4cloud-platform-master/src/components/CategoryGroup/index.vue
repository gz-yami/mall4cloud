<template>
  <div class="category-group">
    <div class="category classify-show">
      <div v-if="selectedCategories.length > 0 & single" class="category-sel1 classify">
        <!-- 商品详情/分类 -->
          <span v-if="selectedCategories[0]">{{ selectedCategories[0] }}</span>
          <span v-if="selectedCategories[1]">&nbsp;>&nbsp;{{ selectedCategories[1] }}</span>
          <span v-if="selectedCategories[2]">&nbsp;>&nbsp;{{ selectedCategories[2] }}</span>
      </div>
      <div v-if="showCategorySelectBtn && single" class="change-category change-classify" @click="selectOrReviseCategory">{{ selectedCategories.length?'修改':'选择' }}分类</div>
      <!-- 品牌/属性 -->
      <div v-if="selectedCategories.length > 0 & multiple" class="category-sel2">
        <p v-for="(item,index) in selectedCategories" :key="index">
          <span v-if="item.firstCategoryName">{{ item.firstCategoryName }}</span>
          <span v-if="item.secondCategoryName">&nbsp;>&nbsp;{{ item.secondCategoryName }}</span>
          <span v-if="item.threeCategoryName">&nbsp;>&nbsp;{{ item.threeCategoryName }}</span>
          <span class="del-cate" @click="deleteCategoryItemOfSelected(index)">×</span>
        </p>
      </div>
      <div v-if="multiple" class="change-category" @click="selectOrReviseCategory">选择分类</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    selectedCategories: {
      type: Array,
      default() {
        return []
      }
    },
    showCategorySelectBtn: {
      type: Boolean,
      default() {
        return true
      }
    },
    multiple: {
      type: Boolean,
      default() {
        return false
      }
    },
    single: {
      type: Boolean,
      default() {
        return false
      }
    },
  },

  data() {
    return {

    }
  },

  methods: {
    // 选择/修改分类
    selectOrReviseCategory() {
      this.$emit('selectOrReviseCategory')
    },

    // 删除
    deleteCategoryItemOfSelected(index) {
      console.log('组件内删除分类index：', index)
      this.$emit('deleteCategoryItemOfSelected', index)
    }
  }
}
</script>

<style lang="scss" scoped>
p {
  margin: 0;
}
.category-group {
  .category {
    display: flex;
    align-items: top;
    font-size: 13px;
    .category-sel1 {
      line-height: 1em;
      padding: 3px 6px;
      border: 1px solid #ddd;
    }
    .category-sel2 {
      display: inline-block;
      max-width: 450px;
      p {
        display: inline-block;
        line-height: 1em;
        padding: 4px;
        border: 1px solid #ccc;
        margin-right: 5px;
        .del-cate {
          display: inline-block;
          text-align: center;
          font-size: 13px;
          width: 13px;
          height: 13px;
          line-height: 12px;
          border: 1px solid #bbb;
          border-radius: 50%;
          color: #bbb;
          margin-left: 6px;
        }
        .del-cate:hover {
          cursor: pointer;
          display: inline-block;
          border-color: #666;
          color: #666;
        }
      }
    }
    .change-category {
      color: #0071db;
      margin-left: 15px;
      cursor: pointer;
    }
    .change-category:hover {
      color: #0258a8;
    }
  }
}
</style>