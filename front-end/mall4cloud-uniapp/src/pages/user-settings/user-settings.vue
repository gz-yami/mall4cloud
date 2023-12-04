<template>
  <view class="page-user-settings">
    <view class="first-box">
      <view
        class="item"
        @tap="getUploadImg()"
      >
        <view class="text">
          头像
        </view>
        <view class="img">
          <image
            v-if="userInfo.pic"
            :src="userInfo.pic"
          />
          <image
            v-else
            src="/static/images/head-sculpture.png"
          />
        </view>
        <view class="text-arrow" />
      </view>
      <view
        class="item"
        @tap="toAddressList"
      >
        <view class="text">
          地址管理
        </view>
        <view class="text-arrow" />
      </view>
    </view>
    <view class="other-entrance">
      <view
        class="item"
        @tap="toEditPage('nickName')"
      >
        <view class="text">
          昵称
        </view>
        <view class="text-arrow">
          {{ userInfo.nickName }}
        </view>
      </view>
      <view
        class="item"
        @tap="toEditPage('password')"
      >
        <view class="text">
          修改密码
        </view>
        <view class="text-arrow" />
      </view>
    </view>
    <view
      class="login-out"
      @click="loginOut"
    >
      退出登录
    </view>
    <!-- 上传图片 -->
    <img-box
      v-if="showImgBoxPopup"
      ref="imgbox"
      class="imgbox-pop"
      @close-img-pop="ImgBoxPopVisible"
    />
  </view>
</template>

<script setup>
import { reactive } from 'vue'

const Data = reactive({
  userInfo: {},
  showImgBoxPopup: false,
  userPic: '',
  userPicUrl: ''
})
const { userInfo, showImgBoxPopup } = toRefs(Data)

onLoad(() => {
  getUserInfo()
})

const getUserInfo = () => {
  const params = {
    url: '/mall4cloud_user/a/user/ma/user_detail_info',
    method: 'get'
  }
  http.request(params).then((res) => {
    Data.userInfo = res
  })
}

// 去修改信息（性别生日用插件在本页选就好）
const toEditPage = (type) => {
  if (type === 'nickName') {
    uni.navigateTo({
      url: `/pages/edit-page/edit-page?type=${type}&nickName=${Data.userInfo.nickName}`
    })
  } else {
    uni.navigateTo({
      url: '/pages/edit-page/edit-page?type=' + type
    })
  }
}

const loginOut = () => {
  const params = {
    url: '/mall4cloud_auth/login_out',
    method: 'post'
  }
  http.request(params).then(() => {
    uni.removeStorageSync('cloudToken')
    uni.removeStorageSync('cloudUserDetails')
    uni.switchTab({
      url: '/pages/my/my'
    })
  })
}

const getUploadImg = () => {
  Data.showImgBoxPopup = true
}

// 上传头像
const ImgBoxPopVisible = (imgUrl, url) => {
  if (url) {
    Data.userPic = imgUrl
    Data.userPicUrl = url
    submitUserPic()
  }
  Data.showImgBoxPopup = !Data.showImgBoxPopup
}

// 修改头像信息
const submitUserPic = () => {
  const params = {
    url: '/mall4cloud_user/a/user/ma/update_user',
    method: 'post',
    data: {
      pic: Data.userPicUrl
    }
  }
  http.request(params).then(() => {
    uni.showToast({
      title: '头像修改成功',
      duration: 2000,
      icon: 'none'
    })
    getUserInfo()
  })
}

// 去管理地址
const toAddressList = () => {
  uni.navigateTo({
    url: '/pages/address-list/address-list'
  })
}

</script>

<style lang="scss" scoped>
@use "user-settings";
</style>
