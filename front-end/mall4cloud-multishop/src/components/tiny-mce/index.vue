<template>
  <div
    :class="{fullscreen:fullscreen}"
    class="component-tinymce-container"
    :style="{width:containerWidth}"
  >
    <textarea
      :id="tinymceId"
      class="tinymce-textarea"
    />
    <div class="editor-custom-btn-container">
      <editorImage
        color="#155bd4"
        class="editor-upload-btn"
        @success-c-b-k="imageSuccessCBK"
      />
    </div>
  </div>
</template>

<script setup>
/**
 * docs:
 * https://panjiachen.github.io/vue-element-admin-site/feature/component/rich-editor.html#tinymce
 */
import editorImage from './add-or-upload.vue'
import plugins from './plugins'
import toolbarPar from './toolbar'
import load from './dynamicLoadScript'

const emit = defineEmits(['input', 'update:modelValue'])

const props = defineProps({
  id: {
    type: String,
    default: function () {
      return 'vue-tinymce-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
    }
  },
  modelValue: {
    type: String,
    default: ''
  },
  toolbar: {
    type: Array,
    required: false,
    default () {
      return []
    }
  },
  menubar: {
    type: String,
    default: 'file edit insert view format table'
  },
  height: {
    type: [Number, String],
    required: false,
    default: 360
  },
  width: {
    type: [Number, String],
    required: false,
    default: 'auto'
  }
})

const language = computed(() => {
  return localStorage.getItem('bbcLang') || 'zh_CN'
})

const containerWidth = computed(() => {
  const widthPar = props.width
  if (/^[\d]+(\.[\d]+)?$/.test(widthPar)) { // matches `100`, `'100'`
    return `${widthPar}px`
  }
  return widthPar
})

let hasInit = false
let hasChange = false
watch(() => props.modelValue, (val) => {
  if (!hasChange && hasInit) {
    setContent(val)
  }
})

watch(() => language.value, () => {
  destroyTinymce()
  nextTick(() => initTinymce())
})

onMounted(() => {
  init()
})

onActivated(() => {
  if (window.tinymce) {
    initTinymce()
  }
})

onDeactivated(() => {
  destroyTinymce()
})

onUnmounted(() => {
  destroyTinymce()
})

const resourceCdn1 = new URL('/static/js/tinymce/js/tinymce/tinymce.min.js', import.meta.url).href
const init = () => {
  // dynamic load tinymce from cdn
  load(resourceCdn1, (err) => {
    if (!err) {
      initTinymce()
    }
  })
}

const tinymceId = ref(props.id)
const fullscreen = ref(false)
const initTinymce = () => {
  window.tinymce.init({
    language: language.value,
    selector: `#${tinymceId.value}`,
    height: props.height,
    body_class: 'panel-body ',
    object_resizing: false,
    toolbar: props.toolbar.length > 0 ? props.toolbar : toolbarPar,
    menubar: props.menubar,
    plugins,
    end_container_on_empty_block: true,
    powerpaste_word_import: 'clean',
    paste_enable_default_filters: false, // 从word复制的内容保持原格式到富文本
    code_dialog_height: 450,
    code_dialog_width: 1000,
    content_style: 'body {-webkit-user-modify: read-write;overflow-wrap: break-word;-webkit-line-break: after-white-space;}img {max-width: 100%;vertical-align:initial}',
    advlist_bullet_styles: 'square',
    advlist_number_styles: 'default',
    imagetools_cors_hosts: ['www.tinymce.com', 'codepen.io'],
    default_link_target: '_blank',
    link_title: false,
    nonbreaking_force_tab: true, // inserting nonbreaking space &nbsp; need Nonbreaking Space Plugin
    init_instance_callback: (editor) => {
      if (props.modelValue) {
        editor.setContent(props.modelValue)
      }
      hasInit = true
      editor.on('NodeChange Change KeyUp SetContent', () => {
        hasChange = true
        emit('update:modelValue', editor.getContent())
      })
    },
    setup: (editor) => {
      editor.on('FullscreenStateChanged', (e) => {
        fullscreen.value = e.state
      })
    },
    convert_urls: false
  })
}

const destroyTinymce = () => {
  try {
    const tinymce = window.tinymce.get(tinymceId.value)
    if (fullscreen.value) {
      tinymce.execCommand('mceFullScreen')
    }
    if (tinymce) {
      tinymce.destroy()
    }
  } catch (e) { }
}
const setContent = (value) => {
  if (window.tinymce) {
    window.tinymce.get(tinymceId.value).setContent(value || '')
  }
}
const imageSuccessCBK = (arr) => {
  arr.forEach(v => {
    window.tinymce.get(tinymceId.value).insertContent(`<img class="wscnph" src="${v}" alt >`)
  })
}

defineExpose({
  setContent
})

</script>

<style lang="scss" scoped>
.component-tinymce-container {
  overflow-wrap: break-word;
}

.component-tinymce-container {
  position: relative;
  line-height: normal;
}

.component-tinymce-container {
  :deep(.mce-fullscreen){
    z-index: 2000;
  }
}

.tinymce-textarea {
  visibility: hidden;
  z-index: -1;
}

.editor-custom-btn-container {
  position: absolute;
  right: 4px;
  top: 4px;
  z-index: 99;
}
.fullscreen .editor-custom-btn-container {
  z-index: 10000;
  position: fixed;
}

.editor-upload-btn {
  display: inline-block;
}
</style>
<!-- eslint-disable-next-line vue-scoped-css/enforce-style-type -->
<style>
.tox-tinymce-aux{
  z-index: 2048 !important;
}
.tox .tox-menu.tox-collection.tox-collection--list{
  min-width: 168px !important;
}
</style>
