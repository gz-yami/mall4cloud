import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['Authorization'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

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
    if (res.code === 'A00001' || res.code === 'A00003' || res.code === 'A00005') {
      Message({
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
          Message({
            message: errorMsg || 'Error',
            type: 'error',
            duration: 1.5 * 1000
          })
        })
      } else {
        Message({
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
      MessageBox.confirm('您已注销，您可以取消停留在该页上，或重新登录', '确认注销', {
        confirmButtonText: '重新登陆',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
      })
      return Promise.reject()
    }
    return Promise.reject(res)
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 1.5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
