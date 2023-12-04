<template>
  <div
    :class="{ 'hidden': hidden }"
    class="component-pagination"
  >
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :background="background"
      :layout="layout"
      :page-sizes="pageSizes"
      :total="total"
      v-bind="$attrs"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script setup>
import { computed } from 'vue'

const emit = defineEmits(['update:limit', 'update:page', 'pagination'])
const props = defineProps({
  total: {
    required: true,
    type: Number
  },
  page: {
    type: Number,
    default: 1
  },
  limit: {
    type: Number,
    default: 10
  },
  pageSizes: {
    type: Array,
    default () {
      return [10, 20, 30, 50]
    }
  },
  layout: {
    type: String,
    default: 'total, sizes, prev, pager, next, jumper'
  },
  background: {
    type: Boolean,
    default: true
  },
  autoScroll: {
    type: Boolean,
    default: true
  },
  hidden: {
    type: Boolean,
    default: false
  }
})

const currentPage = computed({
  get () {
    return props.page
  },
  set (val) {
    emit('update:page', val)
  }
})

const pageSize = computed({
  get () {
    return props.limit
  },
  set (val) {
    emit('update:limit', val)
  }
})

const handleSizeChange = (val) => {
  emit('pagination', { pageNum: props.currentPage, pageSize: val })
  if (props.autoScroll) {
    scrollTo(0, 800)
  }
}

const handleCurrentChange = (val) => {
  emit('pagination', { pageNum: val, pageSize: props.page })
  if (props.autoScroll) {
    scrollTo(0, 800)
  }
}

</script>

<style lang="scss" scoped>
.component-pagination {
  background: #fff;
  padding: 32px 0;

}
.hidden {
    display: none;
  }
</style>
