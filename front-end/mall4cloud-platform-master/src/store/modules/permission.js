import { constantRoutes } from '@/router'
import { menuList } from '@/api/rbac/menu'
import { treeDataTranslate } from '@/utils'
import Layout from '@/layout'
import store from '@/store'

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param menuIds
 */
export function filterAsyncRoutes(routes, menuIds) {
  const res = []

  routes.forEach(route => {
    if (menuIds.indexOf(route.id) > -1) {
      res.push(route)
    }
  })

  return res
}

export const loadView = (view) => { // 路由懒加载
  return () => Promise.resolve(require(`@/views/${view}`).default)
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, menuIds) {
    return new Promise((resolve, reject) => {
      menuList().then(menuList => {
        for (const menu of menuList) {
          if (menu.component === 'Layout') {
            menu.component = Layout
          } else {
            menu.component = loadView(menu.component)
          }
        }
        console.log(store.getters)
        const isAdmin = store.getters && store.getters.isAdmin
        if (!isAdmin) {
          menuList = filterAsyncRoutes(menuList, menuIds)
        }
        const accessedRoutes = treeDataTranslate(menuList)
        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)
      }).catch(error => {
        console.log(error)
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
