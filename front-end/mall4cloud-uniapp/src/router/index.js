import uniCrazyRouter, { beforeEach, afterEach, onError } from 'uni-crazy-router'
// #ifdef H5
import Wechat from '@/utils/wechat.js'
import { AppType } from '../utils/constant.js'
// #endif
// 开放给main.js
export function setupRouter (app) {
  // 接收vue3的实例
  app.use(uniCrazyRouter)
  // eslint-disable-next-line no-unused-vars
  beforeEach(async (to, from, next) => {
    next()
  })

  afterEach((to, from) => {
    const prevPageUrl = uni.getStorageSync('cloudPrevPageUrl')
    if (to.url !== prevPageUrl) {
      uni.setStorageSync('cloudPrevPageUrl', to.url)
    }
    // #ifdef H5
    // 微信分享
    {
      if (uni.getStorageSync('cloudAppType') !== AppType.MP) return
      // 排除不需要分享的页面（页面中已存在分享）
      const excludePathList = ['pages/detail/detail']
      if (excludePathList.includes(to.url)) return
      const isIndex = to.url === 'pages/index/index'
      Promise.resolve().then(async () => {
        // 先获取首页配置
        if (uni.getStorageSync('cloudUniWebConfigData')) {
          await util.getWebConfig()
        }
        const WebConfig = uni.getStorageSync('cloudUniWebConfigData')
        const imgUrl = WebConfig.loginLogoImg || ''
        const title = (uni.getStorageSync('cloudLang') === 'en' ? WebConfig.titleContentEn : WebConfig.titleContentCn) || ''
        const path = '/' + to.url + (to.search ? '?' + to.search : '')
        const sharedata = {
          title, // 分享标题
          imgUrl, // 分享图标
          link: import.meta.env.VITE_APP_DOMAIN_ADDRESS + path,
          desc: document.title || '', // 分享描述
          type: '', // 分享类型,music、video或link，不填默认为link
          dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
          dontShowLogin: true
        }
        if (isIndex) {
          sharedata.desc = WebConfig?.h5WelcomeCn || ''
        }
        Wechat.callWexinShare(
          sharedata,
          () => {
            // console.log('分享成功')
          },
          // eslint-disable-next-line no-unused-vars
          (failMsg) => {
            // console.log(failMsg)
          }
        )
        // 如果页面标题未设置并且不是首页，则监听页面标题设置完成后，再进行分享
        if (!sharedata.desc && !isIndex) {
          uni.addInterceptor('setNavigationBarTitle', {
            invoke ({ title }) {
              if (title) {
                sharedata.desc = title
                Wechat.callWexinShare(
                  sharedata,
                  () => {
                    // console.log('分享成功')
                  },
                  // eslint-disable-next-line no-unused-vars
                  (failMsg) => {
                    // console.log(failMsg)
                  }
                )
                uni.removeInterceptor('setNavigationBarTitle')
              }
            }
          })
        }
      })
    }
  // #endif
  })

  // 除了在beforeEach钩子里没有使用next导致的无法跳转的情况外，其他情况导致的跳转失败都会触发onError
  // eslint-disable-next-line no-unused-vars
  onError((to, from) => {
    uni.showToast({
      title: `${to.url} 不存在`,
      icon: 'none'
    })
  })
}
