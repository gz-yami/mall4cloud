<template>
  <div class="component-spu-category-attrs">
    <div class="part-section">
      <div
        v-for="(attrs, attrIdx) in attrsList"
        :key="attrIdx"
        class="part-sec-item"
      >
        <div class="flx">
          <div class="sec-tit">
            <span
              v-if="attrs.searchType == 1"
              class="stars-icon"
            />
            <span class="name">{{ attrs.name }}</span>
          </div>
          <el-select
            v-if="attrs.attrValues && attrs.attrValues.length > 0"
            v-model="attrs.attrValue"
            placeholder="请选择"
            style="min-width: 150px; width:60%"
            @change="getValueOfBasicAttrs(attrs, 0)"
          >
            <el-option
              v-for="item in attrs.attrValues"
              :key="item.attrValueId"
              :label="item.value"
              :value="item.attrValueId"
            />
          </el-select>
          <el-input
            v-else
            v-model="attrs.attrValue"
            :placeholder="'请输入'+attrs.name"
            style="min-width: 150px; width:60%"
            @blur="getValueOfBasicAttrs(attrs, 1)"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  attrsList: {
    type: Array,
    default () {
      return []
    }
  }
})

const emit = defineEmits(['getValueOfBasicAttrs'])

// 获取数据
const getValueOfBasicAttrs = (attrs, st) => {
  emit('getValueOfBasicAttrs', props.attrsList, attrs, st)
}

</script>

<style lang="scss" scoped>
.component-spu-category-attrs {
  .part-section {
    background: #f9f9f9;
    padding: 20px;
    padding-bottom: 0;
    text-align: left;
    width: 100%;
    max-width: 100%;
    overflow: hidden;
    .part-sec-item {
      display: inline-block;
      width: 48%;
      margin-bottom: 20px;
      margin-right: 10px;
      .flx {
        display: flex;
        align-items: center;
        .sec-tit {
          position: relative;
          margin-right: 15px;
          width: auto;
          text-align: right;
          line-height: 1.5em;
          // 三角形样式
          .stars-icon {
            display: inline-block;
            width: 12px;
            height: 8px;
          }
          .stars-icon::before {
            position: absolute;
            top: 16px;
            left: 0;
            content: "";
            width: 0;
            height: 0;
            margin-top: -10px;
            border-left: 4px solid transparent;
            border-right: 4px solid transparent;
            border-bottom: 8px solid #ff7300;
          }
        }
      }
    }
    .part-sec-item:nth-child(2n) {
      margin-right: 0;
    }
  }
}
</style>
