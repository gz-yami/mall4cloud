/* eslint-disable no-console */
/*
* Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
*
* https://www.mall4j.com/
*
* 未经允许，不可做商业用途！
*
* 版权所有，侵权必究！
*/
import axios from 'axios'
import Qs from 'qs'
import { ElMessageBox, ElMessage } from 'element-plus'
import 'element-plus/es/components/message-box/style/index'
import 'element-plus/es/components/message/style/index'

// create an axios instance
const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API,
  timeout: 50000 // request timeout
})

// request interceptor
service.interceptors.request.use(async config => {
  if (useUserStore().token) {
    config.headers.Authorization = useUserStore().token
  }
  // 只针对get方式进行序列化
  if (config.method === 'get' || config.method === 'GET') {
    config.paramsSerializer = {
      serialize: function (params) {
        return Qs.stringify(params, { arrayFormat: 'repeat' })
      }
    }
  }
  return config
}, error => {
  return Promise.reject(error)
})

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    // if the custom code is not 00000, it is judged as an error.
    if (res.code === '00000') {
      return res.data
    }

    // A00001 用于直接显示提示用户的错误，内容由输入内容决定
    // A00003 无法读取获取请求参数
    // A00011 敏感词输入报错
    if (res.code === 'A00001' || res.code === 'A00003' || res.code === 'A00005' || res.code === 'A00011') {
      if (res.code === 'A00005') {
        console.error('============== 请求异常 ==============')
        console.log('接口地址: ', response.config.url.replace(import.meta.env.VITE_APP_BASE_API, ''))
        console.log('异常信息: ', res)
        console.error('============== 请求异常 end ==========')
      }
      ElMessage({
        message: res.msg || 'Error',
        type: 'error',
        duration: 1.5 * 1000
      })
      return Promise.reject(res)
    }

    // A00002 方法参数没有校验，内容由输入内容决定
    if (res.code === 'A00002') {
      if (res.data && res.data.length) {
        res.data.forEach(errorMsg => {
          ElMessage({
            message: errorMsg || 'Error',
            type: 'error',
            duration: 1.5 * 1000
          })
        })
      } else {
        ElMessage({
          message: res.msg || 'Error',
          type: 'error',
          duration: 1.5 * 1000
        })
      }
      return Promise.reject()
    }

    // A00004 未授权
    if (res.code === 'A00004') {
      // to re-login
      ElMessageBox.confirm('您已注销，您可以取消停留在该页上，或重新登录', '确认注销', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        useUserStore().resetToken()
        location.reload()
      })
      return Promise.reject()
    }
    if (response.status && (response.status >= 200 && response.status < 300)) {
      return Promise.resolve(res)
    }
    return Promise.reject(res)
  },
  error => {
    const url = error.response.config.url
    if (!url.includes('/mall4cloud_admin/p/sys_config/info')) {
      ElMessage({
        message: error.message,
        type: 'error',
        duration: 1.5 * 1000
      })
    }
    return Promise.reject(error)
  }
)

export default service
