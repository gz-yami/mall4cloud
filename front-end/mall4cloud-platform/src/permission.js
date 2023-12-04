/*
* Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
*
* https://www.mall4j.com/
*
* 未经允许，不可做商业用途！
*
* 版权所有，侵权必究！
*/
import router from './router'
import { getToken } from '@/utils/auth' // get token from cookie
import { useUserStore } from '@/stores/modules/user.js'
import { usePermissionStore } from '@/stores/modules/permission.js'

const whiteList = ['/login'] // no redirect whitelist
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  const permissionStore = usePermissionStore()
  const hasToken = getToken()
  if (hasToken) {
    if (to.path === '/login') {
      next({ path: '/' })
    } else {
      const hasRoles = userStore.roles && userStore.roles.length > 0
      if (hasRoles) {
        next()
      } else {
        try {
          // 获取用户信息
          const userInfo = await userStore.getUserInfo()
          // 获取权限信息
          await userStore.listPermissions()
          let menuIds = []
          if (!userInfo.isAdmin) {
            // 获取菜单信息
            menuIds = await userStore.listMenuIds()
          }
          const accessRoutes = await permissionStore.generateRoutes(menuIds)
          accessRoutes.forEach(route => {
            if (route.path[0] !== '/') {
              route.path = `/${route.path}`
            }
            router.addRoute(route)
          })
          // eslint-disable-next-line require-atomic-updates
          router.addRoute({ path: '/:pathMatch(.*)*', redirect: { path: '/404' } })
          router.options.isAddDynamicMenuRoutes = true
          next({ ...to, replace: true })
        } catch (error) {
          // eslint-disable-next-line no-console
          console.log('beforeEach error', error)
          await userStore.resetToken()
          next(`/login?redirect=${to.path}`)
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)
    }
  }
})
