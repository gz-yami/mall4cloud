import { defineStore } from 'pinia'
import { getLanguage } from '@/lang'
import Cookies from 'vue-cookies'

export const useAppStore = defineStore('app', {
  state: () => {
    return {
      sidebar: {
        opened: Cookies.get('cloudSidebarStatus') ? !!+Cookies.get('cloudSidebarStatus') : true,
        withoutAnimation: false
      },
      language: getLanguage()
    }
  },
  actions: {
    toggleSideBar () {
      this.sidebar.opened = !this.sidebar.opened
      this.sidebar.withoutAnimation = false
      Cookies.set('cloudSidebarStatus', Number(this.sidebar.opened))
    },
    closeSideBar ({ withoutAnimation }) {
      Cookies.set('cloudSidebarStatus', 0)
      this.sidebar.opened = false
      this.sidebar.withoutAnimation = withoutAnimation
    },
    setLanguage (language) {
      this.language = language
      Cookies.set('cloudLanguage', language)
    }
  }
})
