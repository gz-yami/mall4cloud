<template>
  <view class="login-register">
    <view class="tab">
      <view class="item active">登录</view>
      <view class="item" @tap="toRegister">注册</view>
    </view>
    <view class="con">
      <!-- 账号登录 -->
      <view v-if="isAccountLogin" class="item-box">
        <view class="item">
          <input v-model="principal" type="text" class="text" placeholder="请输入用户名/手机号">
        </view>
        <view v-if="errorTips == 1" class="error"><text class="error-icon">!</text>账号为4~16位字母、数字或下划线组成的用户名 / 11位手机号</view>
        <view class="item">
          <input v-model="credentials" type="password" class="text" placeholder="请输入账户密码">
        </view>
        <view v-if="errorTips == 2" class="error"><text class="error-icon">!</text>请输入密码</view>
      </view>
      <!-- 验证码登录 -->
      <view v-if="!isAccountLogin" class="item-box">
        <view class="item">
          <input v-model="principal" type="text" class="text" placeholder="请输入手机号">
        </view>
        <view v-if="errorTips == 3" class="error"><text class="error-icon">!</text>请输入手机号</view>
        <view v-if="errorTips == 4" class="error"><text class="error-icon">!</text>请输入正确的手机号</view>
        <view class="item">
          <input v-model="captchaVerification" type="text" class="text" placeholder="请输入验证码">
          <view class="get-captcha">获取验证码</view>
        </view>
        <view v-if="errorTips == 5" class="error"><text class="error-icon">!</text>请输入验证码</view>
      </view>
      <view class="btn" @click="login">登录</view>
    </view>
  </view>
</template>

<script>
var http = require('../../utils/http')
var util = require('../../utils/util.js')
export default {
  data() {
    return {
      isAccountLogin: true, // 账号登录
      captchaVerification: '', // 验证码
      credentials: '', // 密码
      principal: '', // 用户名/邮箱/手机号
      sysType: 0, // 系统类型 0.普通用户系统 1.商家端
      errorTips: 0 // 输入错误提示: 0无  1账号有误或为空 2密码输入错误或为空 3手机号为空 4手机号输入错误 5验证码输入错误
    }
  },
  methods: {

    // 登录
    login() {
      if (this.isAccountLogin && (!this.principal || !util.checkUserName(this.principal))) {
        this.errorTips = 1
        return
      }
      if (this.isAccountLogin && !this.credentials) {
        this.errorTips = 2
        return
      }
      if (!this.isAccountLogin && !this.principal) {
        this.errorTips = 3
        return
      }
      if (!this.isAccountLogin && !util.checkPhoneNumber(this.principal)) {
        this.errorTips = 4
        return
      }
      if (!this.isAccountLogin && !this.captchaVerification) {
        this.errorTips = 5
        return
      }
      this.errorTips = 0
      var params = {
        url: '/mall4cloud_auth/ua/login',
        method: 'POST',
        data: {
          principal: this.principal,
          credentials: this.credentials,
          captchaVerification: this.captchaVerification,
          sysType: this.sysType
        },
        callBack: res => {
          uni.setStorageSync('token', res.accessToken)
          uni.setStorageSync('loginResult', res) // 保存整个登录数据
          uni.reLaunch({ // 关闭所有页面，打开首页
            url: '/pages/index/index'
          })
        }
      }
      http.request(params)
    },

    // 去注册
    toRegister() {
      uni.redirectTo({
        url: '/pages/register/register'
      })
    },

    // 切换登录方式
    changeLoginWay() {
      this.isAccountLogin = !this.isAccountLogin
    },

    // 忘记密码
    forgetPassword() {
      uni.navigateTo({
        url: '/pages/forget-password/forget-password'
      })
    }

  }
}
</script>

<style>
@import "./login.css";
</style>
