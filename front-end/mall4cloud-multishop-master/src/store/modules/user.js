import { login, logout } from '@/api/auth/auth'
import { shopUserInfo } from '@/api/multishop/shop-user'
import { menuPermissionsList } from '@/api/rbac/menu-permission'
import { listMenuIds } from '@/api/rbac/menu'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'

const state = {
  token: getToken(),
  name: '',
  avatar: '',
  introduction: '',
  isAdmin: 0,
  roles: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ISADMIN: (state, isAdmin) => {
    state.isAdmin = isAdmin
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password, captcha } = userInfo
    return new Promise((resolve, reject) => {
      login({ principal: username.trim(), credentials: password, captchaVerification: captcha, sysType: 1 }).then(response => {
        console.log(234234)
        commit('SET_TOKEN', response.accessToken)
        console.log(234234)
        setToken(response.accessToken)
        console.log(234234)
        console.log(getToken())
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get permissions
  listPermissions({ commit, state }) {
    return new Promise((resolve, reject) => {
      if (state.isAdmin) {
        commit('SET_ROLES', [''])
        resolve([''])
        return
      }
      menuPermissionsList().then(permissions => {
        console.log(permissions)
        if (!permissions) {
          reject('Verification failed, please Login again.')
        }

        // roles must be a non-empty array
        if (!permissions || permissions.length <= 0) {
          permissions = ['']
        }
        commit('SET_ROLES', permissions)
        resolve(permissions)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get userInfo
  getUserInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      shopUserInfo().then(shopUser => {
        commit('SET_NAME', shopUser.nickName)
        commit('SET_AVATAR', shopUser.avatar)
        commit('SET_ISADMIN', shopUser.isAdmin)
        resolve(shopUser)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get userInfo
  listMenuIds({ commit, state }) {
    return new Promise((resolve, reject) => {
      listMenuIds().then(menuIds => {
        resolve(menuIds)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resetRouter()

        // reset visited views and cached views
        // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
        dispatch('tagsView/delAllViews', null, { root: true })

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const token = role + '-token'

    commit('SET_TOKEN', token)
    setToken(token)

    const { roles } = await dispatch('getInfo')

    resetRouter()

    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })

    // dynamically add accessible routes
    router.addRoutes(accessRoutes)

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, { root: true })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
