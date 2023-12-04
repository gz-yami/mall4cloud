<template>
  <view class="free-shop">
    <!-- 店铺资料 -->
    <view class="item">
      <view class="label">
        店铺名称
      </view>
      <input
        v-model="shopName"
        type="text"
        :disabled="applyVisible"
        class="input"
        placeholder="仅允许汉字、英文和数字"
      >
      <view
        v-if="errorTips === 1"
        class="error-tips"
      >
        <text class="error-icon">
          !
        </text>店铺名称不能为空
      </view>
      <view
        v-if="errorTips === 2"
        class="error-tips"
      >
        <text class="error-icon">
          !
        </text>仅允许输入汉字、英文和数字
      </view>
    </view>
    <view class="item">
      <view class="label">
        商家账号
      </view>
      <input
        v-model="shopUserName"
        type="text"
        :disabled="applyVisible"
        class="input"
        placeholder="请输入账号"
      >
      <view
        v-if="errorTips === 6"
        class="error-tips"
      >
        <text class="error-icon">
          !
        </text>商家账号不能为空
      </view>
      <view
        v-if="errorTips == 11"
        class="error"
      >
        <text class="error-icon">
          !
        </text>账号为4~16位字母、数字或下划线
      </view>
    </view>
    <view
      v-if="!applyVisible"
      class="item"
    >
      <view class="label">
        商家密码
      </view>
      <input
        v-model="shopPassword"
        type="password"
        :disabled="applyVisible"
        class="input"
        placeholder="请输入密码"
      >
      <view
        v-if="errorTips === 7"
        class="error-tips"
      >
        <text class="error-icon">
          !
        </text>商家密码不能为空
      </view>
      <view
        v-if="errorTips === 12"
        class="error-tips"
      >
        <text class="error-icon">
          !
        </text>密码为6~16位字母、数字或下划线
      </view>
    </view>
    <view
      v-if="!applyVisible"
      class="item"
    >
      <view class="label">
        确认密码
      </view>
      <input
        v-model="confirmationShopPassword"
        type="password"
        :disabled="applyVisible"
        class="input"
        placeholder="请输入密码"
      >
      <view
        v-if="errorTips === 7"
        class="error-tips"
      >
        <text class="error-icon">
          !
        </text>商家密码不能为空
      </view>
      <view
        v-if="errorTips === 12"
        class="error-tips"
      >
        <text class="error-icon">
          !
        </text>密码为6~16位字母、数字或下划线
      </view>
      <view
        v-if="errorTips === 13"
        class="error-tips"
      >
        <text class="error-icon">
          !
        </text>两次输入的密码不一致
      </view>
    </view>
    <view class="item">
      <view class="label">
        店铺描述
      </view>
      <textarea
        v-model="intro"
        :disabled="applyVisible"
        class="textarea"
        maxlength="200"
        placeholder="店铺描述不超过200字"
      />
      <view
        v-if="errorTips === 5"
        class="error-tips"
      >
        <text class="error-icon">
          !
        </text>请输入200字以内的店铺描述
      </view>
    </view>
    <view class="item">
      <view class="label">
        店铺Logo
      </view>
      <view class="upload-box">
        <view class="upload upload-normal">
          <view
            v-if="!shopLogo"
            class="text-box"
            @tap="getUploadImg('shopLogo')"
          >
            <view class="add">
              +添加
            </view>
            <view class="des">
              上传清晰照片
            </view>
          </view>
          <view
            v-if="shopLogo"
            class="img"
          >
            <image
              :src="shopLogo"
              @tap="getUploadImg('shopLogo')"
            />
          </view>
        </view>
      </view>
    </view>
    <view class="item">
      <view class="label">
        身份证件
      </view>
      <view class="label-des" />
      <view class="upload-box">
        <view class="upload upload-middle">
          <view
            v-if="!idCardFront"
            class="text-box"
            @tap="getUploadImg('idCardFront')"
          >
            <view class="add">
              +添加
            </view>
            <view class="des">
              上传身份证正面照
            </view>
          </view>
          <view
            v-if="idCardFront"
            class="img"
          >
            <image
              :src="idCardFront"
              @tap="getUploadImg('idCardFront')"
            />
          </view>
        </view>
        <view class="upload upload-middle">
          <view
            v-if="!idCardBack"
            class="text-box"
            @tap="getUploadImg('idCardBack')"
          >
            <view class="add">
              +添加
            </view>
            <view class="des">
              上传身份证反面照
            </view>
          </view>
          <view
            v-if="idCardBack"
            class="img"
          >
            <image
              :src="idCardBack"
              @tap="getUploadImg('idCardBack')"
            />
          </view>
        </view>
      </view>
    </view>
    <view class="item">
      <view class="label">
        移动端背景图
      </view>
      <view class="upload-box">
        <view
          v-if="!mobileBackground"
          class="upload upload-big"
          @tap="getUploadImg('mobileBackground')"
        >
          <view class="text-box">
            <view class="add">
              +添加
            </view>
          </view>
        </view>
        <view
          v-if="mobileBackground"
          class="upload license-img"
        >
          <view
            class="img-box"
            @tap="getUploadImg('mobileBackground')"
          >
            <image
              class="img"
              :src="mobileBackground"
            />
          </view>
        </view>
      </view>
    </view>
    <view class="item">
      <view class="label">
        营业执照
      </view>
      <view class="upload-box">
        <view
          v-if="!license"
          class="upload upload-big"
          @tap="getUploadImg('license')"
        >
          <view class="text-box">
            <view class="add">
              +添加
            </view>
            <view class="des">
              上传文字清晰照片，露出边框和国徽
            </view>
          </view>
        </view>
        <view
          v-if="license"
          class="upload license-img"
        >
          <view
            class="img"
            @tap="getUploadImg('license')"
          >
            <image :src="license" />
          </view>
        </view>
      </view>
    </view>

    <view class="item">
      <view
        v-if="!applyVisible"
        class="btn"
        @click="examineShopName"
      >
        提交申请
      </view>
      <!-- <view v-if="applyVisible" class="btn" @click="modifyInfo">修改信息</view> -->
    </view>
    <view class="item">
      <view class="notes">
        温馨提示：申请人须保证以上所填信息的真实性。如有因虚假信息而产生的所有后果均由申请人承担。
      </view>
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
  errorTips: 0, // 1店铺名称为空 2店铺名称不正确 3联系电话为空 4联系电话不正确 5简介为空或超限
  showImgBoxPopup: false, // 上传图片弹窗显隐
  storeType: 0, // 店铺类型 0个人点 1个体工商户

  currentImg: '', // 当前图片

  shopName: '', // 店铺名称， 50字以内
  intro: '', // 简介
  shopLogo: '', // 店铺logo
  shopLogoUrl: '',
  idCardFront: '', // 身份证正面
  idCardFrontUrl: '',
  idCardBack: '', // 身份证反面
  idCardBackUrl: '',
  license: '', // 营业执照
  licenseUrl: '',
  shopUserName: '', // 商家端账号
  shopPassword: '', // 商家端密码
  confirmationShopPassword: '',
  type: 2, // 1自营店 2普通店
  mobileBackground: '',
  mobileBackgroundPic: '', // 移动端背景图
  applyVisible: false // 禁用输入框
})
const { errorTips, showImgBoxPopup, shopName, intro, shopLogo, idCardFront, idCardBack, license, shopUserName, shopPassword, confirmationShopPassword, mobileBackground, applyVisible } = toRefs(Data)

onLoad(() => {
  getMyShopInfo()
})

/**
 * 获取店铺信息
 */
const getMyShopInfo = () => {
  const params = {
    url: '/mall4cloud_multishop/my_shop_detail',
    method: 'GET'
  }
  http.request(params).then((res) => {
    uni.hideLoading()
    if (res) {
      Data.applyVisible = true
      Data.shopName = res.shopName
      Data.intro = res.intro
      Data.shopUserName = res.username
      Data.shopLogo = res.shopLogo
      Data.shopLogoUrl = res.shopLogo
      Data.idCardFront = res.identityCardFront
      Data.idCardFrontUrl = res.identityCardFront
      Data.idCardBack = res.identityCardLater
      Data.idCardBackUrl = res.identityCardLater
      Data.license = res.businessLicense
      Data.licenseUrl = res.businessLicense
      Data.mobileBackground = res.mobileBackgroundPic
      Data.mobileBackgroundPic = res.mobileBackgroundPic
    } else {
      Data.applyVisible = false
    }
  })
}

/**
 * 上传图片
 */
const getUploadImg = (type) => {
  if (Data.applyVisible) {
    return
  }
  Data.currentImg = type
  Data.showImgBoxPopup = true
}

/**
 * 上传图片弹窗显隐
 */
const ImgBoxPopVisible = (imgUrl, url) => {
  if (url) {
    switch (Data.currentImg) {
      case 'shopLogo':
        Data.shopLogoUrl = url
        Data.shopLogo = imgUrl
        break
      case 'idCardFront':
        Data.idCardFrontUrl = url
        Data.idCardFront = imgUrl
        break
      case 'idCardBack':
        Data.idCardBackUrl = url
        Data.idCardBack = imgUrl
        break
      case 'license':
        Data.licenseUrl = url
        Data.license = imgUrl
        break
      case 'mobileBackground':
        Data.mobileBackgroundPic = url
        Data.mobileBackground = imgUrl
        break
    }
  }
  Data.showImgBoxPopup = !Data.showImgBoxPopup
}

/**
 * 检查店铺名是否重复
 */
const examineShopName = () => {
  if (Data.applyVisible) {
    return
  }
  const params = {
    url: '/mall4cloud_multishop/ua/shop_detail/check_shop_name',
    method: 'GET',
    data: {
      shopName: Data.shopName
    }
  }
  http.request(params).then(res => {
    uni.hideLoading()
    if (!res) {
      uni.showToast({
        title: '店铺名重复',
        icon: 'none'
      })
      return
    }
    validationFormData()
  })
}

/**
 * 申请开店数据判断
 */
const validationFormData = () => {
  const reg = /^[A-z0-9\u4e00-\u9fa5]*$/ // 只能输入中文、英文、数字
  const shopName = reg.test(Data.shopName)
  if (!Data.shopName) {
    Data.errorTips = 1
    return
  }
  if (!shopName) {
    Data.errorTips = 2
    return
  }
  if (!Data.shopUserName) {
    Data.errorTips = 6
    return
  }
  if (!util.checkUserName(Data.userName)) {
    Data.errorTips = 4
    return
  }
  if (!Data.shopPassword) {
    Data.errorTips = 7
    return
  }
  if (Data.shopPassword !== Data.confirmationShopPassword) {
    Data.errorTips = 13
    return
  }
  if (Data.shopPassword.length < 6) {
    Data.errorTips = 12
    return
  }
  if (!Data.intro) {
    Data.errorTips = 5
    return
  }
  Data.errorTips = -1
  if (!Data.shopLogo) {
    uni.showToast({
      title: '请上传店铺logo',
      icon: 'none'
    })
    return
  }
  if (!Data.idCardFront) {
    uni.showToast({
      title: '请上传身份证正面照',
      icon: 'none'
    })
    return
  }
  if (!Data.idCardBack) {
    uni.showToast({
      title: '请上传身份证反面照',
      icon: 'none'
    })
    return
  }
  if (!Data.mobileBackgroundPic) {
    uni.showToast({
      title: '请上传移动端背景图',
      icon: 'none'
    })
    return
  }
  if (!Data.license) {
    uni.showToast({
      title: '请上传店铺营业执照',
      icon: 'none'
    })
    return
  }
  Data.errorTips = 0
  applyToOpenAShop()
}

/**
 * 申请开店
 */
const applyToOpenAShop = () => {
  const shopDetailDTO = {
    shopName: Data.shopName, // 店铺名称， 50字以内
    intro: Data.intro, // 简介
    username: Data.shopUserName,
    password: Data.shopPassword,
    shopLogo: Data.shopLogoUrl, // 店铺logo
    identityCardFront: Data.idCardFrontUrl, // 身份证正面
    identityCardLater: Data.idCardBackUrl, // 身份证反面
    businessLicense: Data.licenseUrl, // 营业执照
    mobileBackgroundPic: Data.mobileBackgroundPic,
    type: 2 // 店铺类型1自营店 2普通店
  }
  uni.showLoading()
  const params = {
    url: '/mall4cloud_multishop/my_shop_detail/create',
    method: 'POST',
    data: shopDetailDTO
  }
  http.request(params).then(() => {
    uni.hideLoading()
    uni.showModal({
      content: '开店成功', // 提示内容
      showCancel: false,
      success (res) {
        if (res.confirm) {
          uni.reLaunch({
            url: '/'
          })
        }
      }
    })
  })
}

</script>

<style lang="scss" scoped>
@use "free-shop";
</style>
