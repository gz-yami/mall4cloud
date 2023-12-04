import { defineStore } from 'pinia'

export const useCommonStore = defineStore('common', {
  state: () => {
    return {
      // 侧边栏, 折叠状态
      sidebarFold: true,
      menuIds: [],
      selectLeftId: '',
      selectRightId: '',
      pathHeader: '',
      // 当前选择的标签
      selectMenu: [],
      routeList: []
    }
  },
  actions: {
    updateRouteList (list) {
      this.routeList = list
    },
    updatePathHeader (val) {
      this.pathHeader = val
    },
    updateSelectLeftId (id) {
      this.selectLeftId = id
    },
    updateSelectRightId (id) {
      this.selectRightId = id
    },
    updateSelectMenu (list) {
      this.selectMenu = list
    },
    updateSidebarFold (fold) {
      this.sidebarFold = fold
    }
  }
})
