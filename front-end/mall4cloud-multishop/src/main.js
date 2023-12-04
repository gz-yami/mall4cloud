import { createApp } from 'vue'
import { setupStore } from '@/stores'
import App from './App.vue'
import router from '@/router'
// i18n
import { setupI18n } from '@/lang'
import 'virtual:svg-icons-register'
import svgIcon from '@/icons/svg-icon.vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// import 'default-passive-events'
// 全局样式
import '@/styles/index.scss'
// 权限路由、菜单
import '@/permission.js'
// 自定义指令
import { setupDirective } from '@/directive'
import moment from 'moment'

// element-plus 图标
const app = createApp(App)
for (const [key, component] of Object.entries(
  ElementPlusIconsVue)) {
  app.component(key, component)
}

// i18n
app.use(setupI18n)
// router
app.use(router)
// 状态管理
setupStore(app)
// 注册指令(directive)
setupDirective(app)

// svg
app.component('SvgIcon', svgIcon)

app.mount('#app')

// 自定义moment（js时间组件）
moment.locale('zh-cn', {
  longDateFormat: {
    LT: 'HH:mm',
    LTS: 'HH:mm:ss',
    L: 'YYYY-MM-DD',
    LL: 'YYYY-MM-DD HH:mm:ss'
  },
  week: {
    // GB/T 7408-1994《数据元和交换格式·信息交换·日期和时间表示法》与ISO 8601:1988等效
    dow: 1, // 星期一， 是一个星期的第一天
    doy: 4 // 1月4日所在的的一周是一年的第一周
  }
})
