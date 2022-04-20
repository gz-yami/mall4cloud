<template>
  <view class="user-settings">
    <view class="first-box">
      <view class="item" @tap="getUploadImg()">
        <view class="text">头像</view>
        <view class="img">
          <image v-if="userInfo.pic" :src="userInfo.pic" />
          <image v-else src="/static/images/head-sculpture.png" />
        </view>
        <view class="text-arrow" />
      </view>
      <view class="item" @tap="toAddressList">
        <view class="text">地址管理</view>
        <view class="text-arrow" />
      </view>
    </view>
    <view class="other-entrance">
      <view class="item" @tap="toEditPage('nickName')">
        <view class="text">昵称</view>
        <view class="text-arrow">{{ userInfo.nickName }}</view>
      </view>
      <view class="item" @tap="toEditPage('password')">
        <view class="text">修改密码</view>
        <view class="text-arrow" />
      </view>
    </view>
    <view class="login-out" @click="loginOut">退出登录</view>
    <!-- 上传图片 -->
    <img-box v-if="showImgBoxPopup" ref="imgbox" class="imgbox-pop" @closeImgPop="ImgBoxPopVisible" />
  </view>
</template>

<script>
var http = require('../../utils/http')
import imgBox from '../../components/ImgBox/imgBox'
export default {
  components: {
    imgBox
  },
  data() {
    return {
      userInfo: {},
      showImgBoxPopup: false,
      userPic: '',
      userPicUrl: ''
    }
  },
  onLoad() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      var params = {
        url: '/mall4cloud_user/a/user/ma/user_detail_info',
        method: 'get',
        callBack: res => {
          this.userInfo = res
        }
      }
      http.request(params)
    },
    // 去修改信息（性别生日用插件在本页选就好）
    toEditPage(type) {
      if (type === 'nickName') {
        uni.navigateTo({
          url: `/pages/edit-page/edit-page?type=${type}&nickName=${this.userInfo.nickName}`
        })
      } else {
        uni.navigateTo({
          url: '/pages/edit-page/edit-page?type=' + type
        })
      }
    },

    loginOut() {
      var params = {
        url: '/mall4cloud_auth/login_out',
        method: 'post',
        callBack: res => {
          console.log('退出登录token:', uni.getStorageSync('token'))
          uni.removeStorageSync('token')
          uni.removeStorageSync('userDetails')
          uni.switchTab({
            url: '/pages/my/my'
          })
        }
      }
      http.request(params)
    },
    getUploadImg() {
      this.showImgBoxPopup = true
    },
    // 上传头像
    ImgBoxPopVisible(imgUrl, url) {
      if (url) {
        this.userPic = imgUrl
        this.userPicUrl = url
        this.submitUserPic()
      }
      this.showImgBoxPopup = !this.showImgBoxPopup
    },
    // 修改头像信息
    submitUserPic() {
      var params = {
        url: '/mall4cloud_user/a/user/ma/update_user',
        method: 'post',
        data: {
          pic: this.userPicUrl
        },
        callBack: res => {
          uni.showToast({
            title: '头像修改成功',
            duration: 2000,
            icon: 'none'
          })
          this.getUserInfo()
        }
      }
      http.request(params)
    },
    // 去管理地址
    toAddressList() {
      uni.navigateTo({
        url: '/pages/address-list/address-list'
      })
    }

  }
}
</script>

<style>
@import "./user-settings.css";
</style>
