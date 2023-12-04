<template>
  <div class="components-tiny-mce">
    <div
      class="tinymce-container"
      :class="{ 'tox-fullscreen': toxFullscreen }"
    >
      <Editor
        :id="props.id"
        :init="init"
        api-key="i6mv006qcwsfu1t7ebisntg5w261vpowkwirnx9cnse3ho5o"
      />
      <div class="add-or-upload">
        <add-or-upload
          v-show="isLoaded"
          @success-c-b-k="imageSuccessCBK"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { nextTick, reactive } from 'vue'
import Editor from '@tinymce/tinymce-vue'
import AddOrUpload from './add-or-upload.vue'
const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  id: {
    type: String,
    default: function () {
      return 'vue-tinymce-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
    }
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
const toxFullscreen = ref(false)
const hasChange = ref(false)
const hasInit = ref(false)
watch(
  () => props.modelValue,
  val => {
    if (!hasChange.value && hasInit.value) {
      nextTick(() => window.tinymce.get(props.id).setContent(val || ''))
    }
  }
)

const language = computed(() => {
  return localStorage.getItem('b2cLang') || 'zh_CN'
})
watch(language, () => {
  destroyTinymce()
})
const emit = defineEmits(['update:modelValue'])
const isLoaded = ref(false)
const init = reactive({
  plugins: 'preview anchor autolink  codesample emoticons image link lists media searchreplace table visualblocks wordcount  pagebreak insertdatetime fullscreen',
  content_css: 'default', // 主题tinymce-5-dark || tinymce-5 || default || writer || document || dark
  custom_undo_redo_levels: 50, // 回退数量
  end_container_on_empty_block: true, // 块级文本是否换行
  keep_styles: false, // 回车是否保存原有样式，例如code块回车插件初始化失败: tinycomments是否截断
  menubar: true, // 是否开启顶部菜单 > false 关闭菜单 |  'edit insert view format table tools help' 菜单按照这里排序 | 参考:https://www.tiny.cloud/docs/tinymce/6/menus-configuration-options/
  toolbar_mode: 'wrap', // 功能栏是否换行 > | wrap 换行  | scrolling 滚动 | sliding 省略
  toolbar: ' searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample hr numlist link image  preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen',
  powerpaste_word_import: 'clean',
  toolbar_location: 'top', // 菜单栏位置 > bottom 底部 | top 顶部
  style_formats_merge: true, // 是否开启默认功能
  elementpath: false, // 是否展示编辑层级  > p span
  advlist_bullet_styles: 'square',
  resize: true, // 调整宽高 > true 调整高 | false 不可调整宽高 | both 宽高可调
  language, // 中文
  init_instance_callback: editor => {
    isLoaded.value = true
    if (props.modelValue) {
      editor.setContent(props.modelValue)
    }
    hasInit.value = true
    editor.on('NodeChange Change KeyUp SetContent', () => {
      hasChange.value = true
      emit('update:modelValue', editor.getContent())
    })
  },
  setup (editor) {
    const addOrupload = document.querySelectorAll('.components-tiny-mce .tinymce-container .add-or-upload')
    addOrupload.forEach(v => {
      v.style.zIndex = 10
    })
    editor.on('FullscreenStateChanged', e => {
      toxFullscreen.value = e.state
      handleFullScreenStateChange(e.state)
    })
  }

})
const handleFullScreenStateChange = (isFullscreen) => {
  const addOrupload = document.querySelectorAll('.components-tiny-mce .tinymce-container .add-or-upload')
  if (isFullscreen) {
    // 进入全屏模式时执行的代码
    addOrupload.forEach(v => {
      v.style.zIndex = 0
    })
  } else {
    // 退出全屏模式时执行的代码
    addOrupload.forEach(v => {
      v.style.zIndex = 10
    })
  }
}
const destroyTinymce = () => {
  const tinymce = window.tinymce?.get(props.id)
  if (toxFullscreen.value) {
    tinymce.execCommand('mceFullScreen')
  }
  onUnmounted(() => {
    destroyTinymce()
  })

  if (tinymce) {
    tinymce.destroy()
  }
}
const imageSuccessCBK = (arr) => {
  arr.forEach(v => {
    window.tinymce.get(props.id).insertContent(`<img alt="" class="wscnph" src="${v}" >`)
  })
}

</script>
<!--eslint-disable-next-line vue-scoped-css/enforce-style-type -->
<style lang="scss">
.components-tiny-mce {
  .tox-fullscreen .add-or-upload {
    z-index: 9999 !important;
    position: fixed !important;
    right: 0;
    top: 0;

  }

  .tox .tox-mbtn {
    width: auto !important;
  }

  .tinymce-container {
    position: relative;

    .add-or-upload {
      position: absolute;
      top: 10px;
      right: 10px;
    }
  }

  .tox-tinymce-aux {
    z-index: 9999 !important;
  }
}
</style>
