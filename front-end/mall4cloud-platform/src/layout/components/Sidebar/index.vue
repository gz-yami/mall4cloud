<!--
 Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.

 https://www.mall4j.com/

 未经允许，不可做商业用途！

 版权所有，侵权必究！
-->
<template>
  <aside class="Mall4j site-sidebar">
    <div
      class="menu-mod"
      :style="{
        display:isPath?'none':''}"
    >
      <el-scrollbar style="height: 100%">
        <div class="menu-left">
          <ul v-if="dashboard">
            <li>
              <div
                :class="{'menu-left-item' :true,'menu-left-active': selectLeftId === ''}"
                @click="toHome"
              >
                <item
                  :icon="dashboard"
                  :title="'首页'"
                />
              </div>
            </li>
          </ul>
          <ul>
            <li
              v-for="(menu,index) in permissionRoutes"
              :key="index"
            >
              <div
                v-if="menu.children"
                :class="{'menu-left-item' :true,'menu-left-active': selectLeftId === menu.id}"
                @click="expandMenu(menu)"
              >
                <item
                  :icon="menu.meta.icon"
                  :title="generateTitle(menu.meta.title)"
                />
              </div>
              <div
                v-else
                :class="{'menu-left-item' :true}"
                @click="gotoRouteHandle(menu)"
              >
                <item
                  :icon="menu.meta.icon"
                  :title="generateTitle(menu.meta.title)"
                />
              </div>
            </li>
          </ul>
        </div>
      </el-scrollbar>
      <sub-menu
        v-if="!isCollapse"
        class="menu-right-con"
        :expand-menu="expandMenuList"
      />
    </div>
  </aside>
</template>

<script setup>
import { generateTitle } from '@/utils/i18n'
import SubMenu from './main-sidebar-sub-menu.vue'

import Item from './item.vue'
import '@/styles/variables.scss'
import { useCommonStore } from '@/stores/modules/common.js'

const showName = '客服'

const permissionStore = usePermissionStore()
const permissionStoreRoutes = computed(() => permissionStore.routes)

const appStore = useAppStore()
const sidebar = computed(() => appStore.sidebar)

const dashboard = computed(() => permissionStore.dashboard)

const permissionRoutes = computed(() => permissionStore.routes.filter(menu => !menu.hidden && menu.name !== showName && menu.meta))
const router = useRouter()

const isCollapse = computed(() => !sidebar.value.opened)
const isPath = computed(() => {
  return /^\/(platform-decorate\/decorate-create-edit)/.test(router.currentRoute.value.path)
})

const commonStore = useCommonStore()
const pathHeader = computed({
  get: () => commonStore.pathHeader,
  set: (val) => commonStore.updatePathHeader(val)
})

const selectLeftId = computed({
  get: () => {
    handleRightRoute(commonStore.selectLeftId)
    return commonStore.selectLeftId || ''
  },
  set: (val) => commonStore.updateSelectLeftId(val)
})
const selectRightId = computed({
  get: () => {
    handleRightRoute(commonStore.selectRightId)
    return commonStore.selectRightId || ''
  },
  set: (val) => commonStore.updateSelectRightId(val)
})

const selectMenu = computed({
  get: () => commonStore.selectMenu,
  set: (val) => commonStore.updateSelectMenu(val)
})

const handleRightRoute = (id) => {
  permissionStoreRoutes.value.forEach(item => {
    if (id === item.id) {
      expandMenu(item, true)
    }
  })
}

const expandMenuList = ref([])

const expandMenu = (menu, flag) => {
  if (isCollapse.value && !flag) {
    appStore.toggleSideBar()
  }
  expandMenuList.value = menu.children || []
  pathHeader.value = menu.path
  const id1 = commonStore.selectLeftId
  commonStore.updateSelectLeftId(menu.id || '')
  const id2 = commonStore.selectLeftId
  if (menu.children) {
    sessionStorage.setItem('cloudIsExpand', '1')
  }
  if (id1 !== id2) {
    routeJump(menu)
  }
}

// 默认跳转展开菜单的第一个路由
const routeJump = (menu) => {
  const routes = menu.children
  for (let i = 0; i < routes.length; i++) {
    if (!routes[i].hidden && !routes[i].children) {
      if (routes[i].path[0] === '/') {
        router.push(routes[i].path)
      } else {
        router.push(pathHeader.value + '/' + routes[i].path)
      }
      selectLeftId.value = routes[i].id

      break
    } else if (routes[i].children) {
      let flag = false
      for (let j = 0; j < routes[i].children.length; j++) {
        // 三级路由
        if (!routes[i].children[j].hidden) {
          if (routes[i].children[j].path[0] === '/') {
            router.push(routes[i].children[j].path)
          } else {
            router.push(routes[i].path + '/' + routes[i].children[j].path)
          }
          flag = true
          break
        }
      }
      if (flag) {
        break
      }
    }
  }
}

const route = useRoute()

const routeHandle = (route) => {
  if (route.name === 'Dashboard') {
    selectMenu.value = ['首页']
    expandMenuList.value = []
    sessionStorage.setItem('cloudIsExpand', '0')
    commonStore.updateSelectLeftId('')
    commonStore.updateSelectRightId('')
    if (!isCollapse.value) {
      appStore.toggleSideBar()
    }
  }
  let path
  const index = route.path.indexOf('/', 1)
  if (index === -1) { // 单级路由
    path = route.path
    permissionStoreRoutes.value.forEach(item => {
      if (item.children) {
        item.children.forEach(term => {
          if (term.children) {
            term.children.forEach(res => {
              if (res.path === path) {
                selectMenu.value = []
                selectMenu.value.push(item.meta.title)
                selectMenu.value.push(term.meta.title)
                selectMenu.value.push(res.meta.title)
                selectRightId.value = res.id
                selectLeftId.value = item.id
                if (isCollapse.value) {
                  appStore.toggleSideBar()
                }
              }
            })
          } else {
            if (term.path === path) {
              selectMenu.value = []
              selectMenu.value.push(item.meta.title)
              selectMenu.value.push(term.meta.title)
              selectRightId.value = term.id
              selectLeftId.value = item.id
              if (item.children.length === 1) {
                if (!isCollapse.value) {
                  appStore.toggleSideBar()
                }
              } else {
                if (isCollapse.value) {
                  appStore.toggleSideBar()
                }
              }
            }
          }
        })
      } else {
        if (item.path === path) {
          gotoRouteHandle(item)
          selectMenu.value = []
          selectMenu.value.push(item.meta.title)
        }
      }
    })
  } else { // 多级路由
    permissionStoreRoutes.value.forEach(item => {
      if (item.children) {
        item.children.forEach(term => {
          if (term.children) {
            term.children.forEach(res => {
              if (term.path + '/' + res.path === route.path) {
                selectMenu.value = []
                selectMenu.value.push(item.meta.title)
                selectMenu.value.push(term.meta.title)
                selectMenu.value.push(res.meta.title)
                selectRightId.value = res.id
                selectLeftId.value = item.id
                if (isCollapse.value) {
                  appStore.toggleSideBar()
                }
              }
            })
          } else {
            if (item.path + '/' + term.path === route.path) {
              selectMenu.value = []
              selectMenu.value.push(item.meta.title)
              selectMenu.value.push(term.meta.title)
              selectRightId.value = term.id
              selectLeftId.value = item.id
              if (item.children.length === 1) {
                if (!isCollapse.value) {
                  appStore.toggleSideBar()
                }
              } else {
                if (isCollapse.value) {
                  appStore.toggleSideBar()
                }
              }
            }
          }
        })
      } else {
        if (item.path === path) {
          selectMenu.value = []
          selectMenu.value.push(item.meta.title)
          gotoRouteHandle(item)
        }
      }
    })
  }
}

watch(route, () => {
  routeHandle(route)
}, { immediate: true })
onBeforeMount(() => {
  routeHandle(route)
})

const gotoRouteHandle = (menu) => {
  if (!isCollapse.value) {
    appStore.toggleSideBar()
  }
  expandMenuList.value = menu.children || []
  selectLeftId.value = menu.id
  router.push(menu.path)
}

const toHome = () => {
  if (!isCollapse.value) {
    appStore.toggleSideBar()
  }
  router.push('/')
  expandMenuList.value = []
  sessionStorage.setItem('cloudIsExpand', '0')
  commonStore.updateSelectLeftId('')
  commonStore.updateSelectRightId('')
}

</script>

<style scoped>
.menu-mod {
  display: flex;
}
.menu-right-con {
  position: absolute;
  z-index: 1;
  left: 100px;
}
.menu-left {
  background: #222222;
  color: #ffffff !important;
  width: 100px;
  height: calc(100vh - 50px);
  overflow-y: auto;
}
.menu-mod .menu-left-item {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.menu-mod .menu-left ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  width: 100px;
  text-align: center;
}
.menu-mod .menu-right ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  width: 130px;
  text-align: center;
}
.menu-left li {
  background: #222222;
  color: #fff;
  height: 40px;
  cursor: pointer;
  font-size: 14px;
  stroke: #fff !important;
}

.menu-right li {
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 14px;
  color: #333333;
}

/* 鼠标移动到选项上修改背景颜色 */
.menu-left li:hover {
  background-color: #ffffff;
  color: #155bd4;
  stroke: #155bd4 !important;
}
/* 鼠标移动到选项上修改背景颜色 */
.menu-right li:hover {
  background-color: rgba(21, 91, 212, 0.1);
  color: #155bd4;
}
.menu-right li:hover {
  background-color: rgba(21, 91, 212, 0.1);
  color: #155bd4;
}
.menu-left-active {
  background-color: #ffffff;
  color: #155bd4;
  stroke: #155bd4 !important;
}
</style>
