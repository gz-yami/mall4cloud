<template>
  <el-tabs class="icons-container" type="border-card">
    <el-tab-pane label="Icons">
      <div class="grid">
        <el-button v-for="item of svgIcons" :key="item" @click="selectIcon(getIconCode(item))">
          <div class="icon-item">
            <svg-icon :icon-class="item" class-name="disabled" />
          </div>
        </el-button>
      </div>
    </el-tab-pane>
    <el-tab-pane label="Element-UI Icons">
      <div class="grid">
        <el-button v-for="item of elementIcons" :key="item" @click="selectIcon(getElementIconCode(item))">
          <div class="icon-item">
            <i :class="'el-icon-' + item" />
          </div>
        </el-button>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import svgIcons from './svg-icons'
import elementIcons from './element-icons'

export default {
  name: 'Icons',
  data() {
    return {
      svgIcons,
      elementIcons
    }
  },
  methods: {
    getIconCode(symbol) {
      return `${symbol}`
    },
    getElementIconCode(symbol) {
      return `el-icon-${symbol}`
    },
    selectIcon(text) {
      this.$emit('selectIcon', text)
    }
  }
}
</script>

<style lang="scss">
.icons-container {
  .el-tabs__content{
    height: 200px;
    overflow: scroll;
  }
  .grid{
    padding: 0;
    margin: -8px 0 0 -8px;
    > .el-button {
      padding: 8px;
      margin: 8px 0 0 8px;
      > span {
        display: inline-block;
        vertical-align: middle;
        width: 18px;
        height: 18px;
        font-size: 18px;
      }
    }
  }
}
</style>
