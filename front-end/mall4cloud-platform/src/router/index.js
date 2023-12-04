import { createRouter, createWebHistory } from 'vue-router'

export const Layout = () => import('@/layout/index.vue')

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  { path: '/', redirect: '/product/list' },
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/common/redirect/index.vue')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/common/login/index.vue'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/common/error-page/401.vue'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/common/error-page/404.vue'),
    hidden: true
  }
  // {
  //   path: '/',
  //   component: Layout,
  //   redirect: '/dashboard',
  //   children: [
  //     {
  //       path: 'dashboard',
  //       component: () => import('@/views/common/dashboard/index.vue'),
  //       name: 'Dashboard',
  //       meta: { title: 'dashboard', icon: 'index', affix: true }
  //     }
  //   ]
  // }
]

const router = createRouter({
  history: createWebHistory(),
  isAddDynamicMenuRoutes: false,
  // 刷新时，滚动条位置还原
  scrollBehavior: () => ({ left: 0, top: 0 }),
  routes: constantRoutes
})

/**
 * 重置路由
 */
export async function resetRouter () {
  router.options.isAddDynamicMenuRoutes = false
  await router.replace({ path: '/login' })
}
export default router
