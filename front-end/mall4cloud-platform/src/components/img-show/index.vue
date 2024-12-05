<template>
  <img
    v-if="imgPath"
    :src="checkFileUrl(imgPath)"
    :style="imgStyle"
    :class="classList"
    :alt="imgAlt"
    @error="imgError"
    @click="handleClick"
  >
  <img
    v-else
    src="~@/assets/images/def.png"
    :style="imgStyle"
    :class="classList"
    alt
    @click="handleClick"
  >
</template>

<script setup>

const props = defineProps({
  src: {
    type: String,
    default: ''
  },
  imgAlt: {
    type: String,
    default: ''
  },
  classList: {
    type: Array,
    default: () => {
      return []
    }
  },
  imgStyle: {
    type: Object,
    default: () => {
      return {}
    }
  }
})
const emit = defineEmits(['handleClick'])

const imgPath = ref('')

watch(() => props.src, (path) => {
  imgPath.value = path
}, {
  immediate: true
})

const resourcesUrl = import.meta.env.VITE_APP_RESOURCES_URL
const checkFileUrl = (src) => {
  if (/^http(s)?:\/\/.+/.test(src)) {
    return src
  } else {
    return resourcesUrl + src
  }
}

const imgError = () => {
  imgPath.value = ''
}

const handleClick = () => {
  emit('handleClick')
}

</script>

<style scoped>
img{
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
