<template>
  <el-tabs
    class="icons-container"
    type="border-card"
  >
    <el-tab-pane label="Icons">
      <div class="grid">
        <el-button
          v-for="item of svgIcons"
          :key="item"
          @click="selectIcon(getIconCode(item))"
        >
          <div class="icon-item">
            <svg-icon
              :icon-class="item"
              class-name="disabled"
            />
          </div>
        </el-button>
      </div>
    </el-tab-pane>
    <el-tab-pane label="Element-Plus Icons">
      <div class="grid">
        <el-button
          v-for="(icon, index) in elementIcons"
          :key="index"
        >
          <component
            :is="icon"
            style="width: 20px; height:20px;"
            @click="selectIcon(getElementIconCode(icon))"
          />
        </el-button>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import svgIcons from './svg-icons'
import elementIcons from './element-icons'

const emit = defineEmits(['selectIcon'])

const getIconCode = (symbol) => {
  return `${symbol}`
}
const getElementIconCode = (symbol) => {
  return `${symbol}plus`
}
const selectIcon = (text) => {
  emit('selectIcon', text)
}

</script>

<style lang="scss" scoped>
.icons-container {
  :deep(.el-tabs__content){
    height: 200px;
    overflow: scroll;
  }
  .grid{
    padding: 0;
    margin: -8px 0 0 -8px;
    > :deep(.el-button) {
      padding: 8px;
      margin: 8px 0 0 8px;
      > span {
        display: inline-block;
        vertical-align: middle;
        width: 18px;
        height: 18px;
        font-size: 18px;
        > .icon-item i {
          display: inline-block;
        }
      }
    }
  }
}
</style>
