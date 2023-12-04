import { defineStore } from 'pinia'
export const useWebConfigStore = defineStore('webConfigStore', {
  state: () => {
    return {
      webConfig: {
        loginLogoImg: null,
        loginBgImg: null,
        copyrightCn: null,
        copyrightEn: null,
        titleContentCn: null,
        titleContentEn: null,
        titleImg: null,
        bsMenuTitleOpenCn: null,
        bsMenuTitleOpenEn: null,
        bsMenuTitleCloseCn: null,
        bsMenuTitleCloseEn: null
      }
    }
  },
  actions: {
    addData (webConfigDataForm) {
      localStorage.setItem('cloudPlatformWebConfigData', JSON.stringify(webConfigDataForm || '{}'))
      this.webConfig = webConfigDataForm
      const lang = localStorage.getItem('cloudLang')
      if (lang !== 'en') {
        document.title = webConfigDataForm.titleContentCn || 'Mall4j白洞版-平台端'
      } else {
        document.title = webConfigDataForm.titleContentEn || 'Mall4j White hole version Supplier side'
      }

      let facicon = document.querySelector('link[rel="icon"]')
      if (facicon !== null) {
        facicon.href = webConfigDataForm.titleImg
      } else {
        facicon = document.createElement('link')
        facicon.rel = 'icon'
        facicon.href = webConfigDataForm.titleImg
        document.head.appendChild(facicon)
      }
    }
  }
})
