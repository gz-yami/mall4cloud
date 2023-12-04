import { createI18n } from 'vue-i18n'
import en from './langs/en'
import zh from './langs/zh'
const messages = {
  en: {
    ...en
  },
  zh_CN: {
    ...zh
  }
}
const localeData = {
  // 如果设置true, $t() 函数将注册到全局
  globalInjection: true,
  // 如果想在composition api中使用需要设置为false
  legacy: false,
  // 默认语言
  locale: uni.getStorageSync('cloudLang') || 'zh_CN',
  // 所有语言
  messages
}

export const i18n = createI18n(localeData)
export const setupI18n = {
  install (app) {
    app.use(i18n)
  }
}
