import axios from 'axios' // 只用来minio上传文件
var config = require('./config.js') // 统一的网络请求方法
// import {
//   AppType
// } from './constant.js'

// create an axios instance
const service = axios.create({
  baseURL: config, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    config.headers['Authorization'] = uni.getStorageSync('token')
    return config
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    const res = response.data
    console.log(response.status)
    if (response.status) {
      return Promise.resolve(res)
    }
    return Promise.reject(res)
  },
  error => {
    console.log('err' + error)
    return Promise.reject(error)
  }
)

export default service
