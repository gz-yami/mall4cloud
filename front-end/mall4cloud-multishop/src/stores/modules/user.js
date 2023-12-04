/*
* Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
*
* https://www.mall4j.com/
*
* 未经允许，不可做商业用途！
*
* 版权所有，侵权必究！
*/
import { defineStore } from 'pinia'
import { login, logout } from '@/api/auth/auth'
import { shopUserInfo } from '@/api/multishop/shop-user'
import { menuPermissionsList } from '@/api/rbac/menu-permission'
import { listMenuIds } from '@/api/rbac/menu'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

export const useUserStore = defineStore('user', {
  state: () => {
    return {
      token: getToken(),
      name: '',
      avatar: '',
      introduction: '',
      isAdmin: 0,
      roles: []
    }
  },
  actions: {
    // 登录
    login (loginData) {
      return new Promise((resolve, reject) => {
        const { username, password, captcha } = loginData
        login({ principal: username.trim(), credentials: password, captchaVerification: captcha, sysType: 1 }).then(response => {
          this.token = response.accessToken
          setToken(response.accessToken)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // get userInfo
    getUserInfo () {
      return new Promise((resolve, reject) => {
        shopUserInfo().then(shopUser => {
          this.name = shopUser.nickName
          this.avatar = shopUser.avatar
          this.isAdmin = shopUser.isAdmin
          resolve(shopUser)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取权限列表
    listPermissions () {
      return new Promise((resolve, reject) => {
        if (this.isAdmin) {
          this.roles = ['']
          resolve([''])
          return
        }
        menuPermissionsList().then(permissions => {
          if (!permissions) {
            reject(new Error('Verification failed, please Login again.'))
          }
          // roles must be a non-empty array
          if (!permissions || permissions.length <= 0) {
            permissions = ['']
          }
          this.roles = permissions
          resolve(permissions)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取拥有的菜单id
    listMenuIds () {
      return new Promise((resolve, reject) => {
        listMenuIds().then(menuIds => {
          resolve(menuIds)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    logout () {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          this.token = ''
          this.roles = []
          this.resetToken()
          resetRouter()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 重置token
    resetToken () {
      this.token = ''
      removeToken()
      this.roles = []
    }
  }
})
