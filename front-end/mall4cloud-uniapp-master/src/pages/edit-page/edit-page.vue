<template>
  <view class="edit-page">
    <!-- 修改昵称 -->
    <view v-if="pageType === 'nickName'" class="modify-name">
      <view class="input-box">
        <input v-model="nickName" type="text" class="input">
        <view class="cancel">
          <image src="/static/images/close-w.png" />
        </view>
      </view>
      <view class="des">4-20个字符，可由中英文、数字、“_”、“-”组成</view>
      <view class="btn" @tap="submitNickName">确认修改</view>
    </view>

    <!-- 修改密码 -->
    <view v-if="pageType === 'password'" class="modify-password">
      <view class="item">
        <input v-model="oldPassword" type="password" class="input" placeholder="输入旧密码">
      </view>
      <view v-if="pswErrorTips === 1" class="error"><text class="error-icon">!</text>旧密码不能为空！</view>
      <view class="item">
        <input v-model="newPassword" type="password" class="input" placeholder="输入新密码">
      </view>
      <view v-if="pswErrorTips === 2" class="error"><text class="error-icon">!</text>密码由字母加数字或符号至少两种以上字符组成6-20位半角字符，区分大小写</view>
      <view class="item">
        <input v-model="confirmPsw" type="password" class="input" placeholder="确认新密码">
      </view>
      <view v-if="pswErrorTips === 3" class="error"><text class="error-icon">!</text>请再次输入新密码！</view>
      <view v-if="pswErrorTips === 4" class="error"><text class="error-icon">!</text>新密码与确认密码不一致！请重新输入</view>
      <view class="btn" @tap="changePassword">确认修改</view>

      <!-- 修改密码成功 -->
      <view class="popup-min" :class="{ 'show': isShowSuccessPopup }">
        <view class="popup-mask" />
        <view class="popup-con-middle success">
          <view class="img" />
          <view class="text">密码修改成功</view>
          <view class="des">正在跳转登录页{{ loCount }}s...</view>
        </view>
      </view>

    </view>

  </view>
</template>

<script>
var http = require('../../utils/http')
var util = require('../../utils/util')
export default {
  data() {
    return {
      isShowDelPopup: false, // 显示删除弹窗
      pageType: 'nickName',
      nickName: '',
      // 修改密码
      oldPassword: '',
      newPassword: '',
      confirmPsw: '',
      pswErrorTips: 0, // 0无 1旧密码为空 2新密码格式不正确 3确认密码为空 4两次密码不一致
      isShowSuccessPopup: false, // 修改成功弹窗
      // 完成跳转首页倒计时
      loTimer: null,
      loCount: 0

    }
  },

  onLoad(options) {
    this.pageType = options.type
    if (options.type === 'nickName') {
      this.nickName = options.nickName
      uni.setNavigationBarTitle({
        title: '修改用户昵称'
      })
    } else if (options.type === 'phone') {
      uni.setNavigationBarTitle({
        title: '修改手机号'
      })
    } else if (options.type === 'password') {
      uni.setNavigationBarTitle({
        title: '修改密码'
      })
    }
  },

  methods: {

    /**
     * 修改密码
     */
    changePassword() {
      var reg = new RegExp('^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{6,20}$')
      const passwordResult = reg.test(this.newPassword)
      if (!this.oldPassword) {
        this.pswErrorTips = 1
        return
      }
      if (!this.newPassword || !passwordResult) {
        this.pswErrorTips = 2
        return
      }
      if (!this.confirmPsw) {
        this.pswErrorTips = 3
        return
      }
      if (this.newPassword !== this.confirmPsw) {
        this.pswErrorTips = 4
        return
      }
      this.pswErrorTips = -1
      const params = {
        url: '/mall4cloud_auth/update_password',
        method: 'PUT',
        data: {
          newPassword: this.newPassword,
          oldPassword: this.oldPassword
        },
        callBack: res => {
          this.isShowSuccessPopup = true
          setTimeout(() => {
            uni.reLaunch({
              url: '/pages/login/login'
            })
          }, 3000)
          const loTimeCount = 3
          if (!this.loTimer) {
            this.loCount = loTimeCount
            this.loTimer = setInterval(() => {
              if (this.loCount > 0 && this.loCount <= loTimeCount) {
                this.loCount--
              } else {
                clearInterval(this.loTimer)
                this.loTimer = null
              }
            }, 1000)
          }
        }
      }
      http.request(params)
    },

    /**
     * 获取验证码
     */
    getCode() {
      if (!this.shopAccount) {
        this.shopErrorTips = 1
        return
      }
      if (!util.checkPhoneNumber(this.shopAccount)) {
        this.shopErrorTips = 2
        return
      }
      this.shopErrorTips = 0
      var params = {
        url: '/mall4cloud_biz/ua/sms/verification_code/bind',
        method: 'PUT',
        data: {
          mobile: this.shopAccount,
          shopAccount: 1
        },
        callBack: res => {
          const timeCount = 60
          if (!this.timer) {
            this.count = timeCount
            this.showGetCodeBtn = false
            this.timer = setInterval(() => {
              if (this.count > 0 && this.count <= timeCount) {
                this.count--
              } else {
                clearInterval(this.timer)
                this.timer = null
                this.showGetCodeBtn = true
              }
            }, 1000)
          }
        }
      }
      http.request(params)
    },

    /**
     * 切换手机号/邮箱
     */
    changebindtype(type) {
      this.navType = type
      this.shopAccount = ''
      this.shopAccountCode = ''
      this.shopPassword = ''
    },
    // 修改头像信息
    submitNickName() {
      var params = {
        url: '/mall4cloud_user/a/user/ma/update_user',
        method: 'post',
        data: {
          nickName: this.nickName
        },
        callBack: res => {
          uni.showToast({
            title: '昵称修改成功',
            duration: 2000,
            icon: 'success',
            complete: res => {
            }
          })
          setTimeout(() => {
            uni.navigateTo({
              url: '/pages/user-settings/user-settings'
            })
          }, 1500)
        }
      }
      http.request(params)
    },

    // 删除订单弹窗
    showDelPopup() {
      this.isShowDelPopup = true
    },

    // 关闭弹窗
    closePopup() {
      this.isShowDelPopup = false
    }

  }
}
</script>

<style>
@import "./edit-page.css";
@import "../../popup.css";
</style>
