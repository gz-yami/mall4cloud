import { createI18n } from 'vue-i18n'
import en from './en'
import zhCn from './zhCn'
import Cookies from 'vue-cookies'
const messages = {
  en: {
    ...en
  },
  zh: {
    ...zhCn
  }
}
export function getLanguage () {
  const chooseLanguage = Cookies.get('cloudLanguage')
  if (chooseLanguage) return chooseLanguage
  // if has not choose language
  const language = (navigator.language || navigator.browserLanguage).toLowerCase()
  const locales = Object.keys(messages)
  for (const locale of locales) {
    if (language.indexOf(locale) > -1) {
      return locale
    }
  }
  return 'zh'
}

const localeData = {
  globalInjection: true, // 如果设置true, $t() 函数将注册到全局
  legacy: false, // 如果想在composition api中使用需要设置为false
  // 默认语言
  // locale: settings.defaultLanguage,
  locale: localStorage.getItem('cloudLanguage') || 'zh',
  messages // set locale messages
}

export const i18n = createI18n(localeData)
export const setupI18n = {
  install (app) {
    app.use(i18n)
  }
}
