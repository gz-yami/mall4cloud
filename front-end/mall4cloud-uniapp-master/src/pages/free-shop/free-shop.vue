<template>
  <view class="free-shop">

    <!-- 店铺资料 -->
    <view class="item">
      <view class="label">店铺名称</view>
      <input v-model="shopName" type="text" :disabled="applyVisible" class="input" placeholder="仅允许汉字、英文和数字">
      <view v-if="errorTips === 1" class="error-tips"><text class="error-icon">!</text>店铺名称不能为空</view>
      <view v-if="errorTips === 2" class="error-tips"><text class="error-icon">!</text>仅允许输入汉字、英文和数字</view>
    </view>
    <view class="item">
      <view class="label">商家账号</view>
      <input v-model="shopUserName" type="text" :disabled="applyVisible" class="input" placeholder="请输入账号">
      <view v-if="errorTips === 6" class="error-tips"><text class="error-icon">!</text>商家账号不能为空</view>
      <view v-if="errorTips == 11" class="error"><text class="error-icon">!</text>账号为4~16位字母、数字或下划线</view>
    </view>
    <view v-if="!applyVisible" class="item">
      <view class="label">商家密码</view>
      <input v-model="shopPassword" type="password" :disabled="applyVisible" class="input" placeholder="请输入密码">
      <view v-if="errorTips === 7" class="error-tips"><text class="error-icon">!</text>商家密码不能为空</view>
      <view v-if="errorTips === 12" class="error-tips"><text class="error-icon">!</text>密码为6~16位字母、数字或下划线</view>
    </view>
    <view v-if="!applyVisible" class="item">
      <view class="label">确认密码</view>
      <input v-model="confirmationShopPassword" type="password" :disabled="applyVisible" class="input" placeholder="请输入密码">
      <view v-if="errorTips === 7" class="error-tips"><text class="error-icon">!</text>商家密码不能为空</view>
      <view v-if="errorTips === 12" class="error-tips"><text class="error-icon">!</text>密码为6~16位字母、数字或下划线</view>
      <view v-if="errorTips === 13" class="error-tips"><text class="error-icon">!</text>两次输入的密码不一致</view>
    </view>
    <view class="item">
      <view class="label">店铺描述</view>
      <textarea v-model="intro" :disabled="applyVisible" class="textarea" maxlength="200" placeholder="店铺描述不超过200字" />
      <view v-if="errorTips === 5" class="error-tips"><text class="error-icon">!</text>请输入200字以内的店铺描述</view>
    </view>
    <view class="item">
      <view class="label">店铺Logo</view>
      <view class="upload-box">
        <view class="upload upload-normal">
          <view v-if="!shopLogo" class="text-box" @tap="getUploadImg('shopLogo')">
            <view class="add">+添加</view>
            <view class="des">上传清晰照片</view>
          </view>
          <view v-if="shopLogo" class="img">
            <image :src="shopLogo" @tap="getUploadImg('shopLogo')" />
          </view>
        </view>
      </view>
    </view>
    <view class="item">
      <view class="label">身份证件</view>
      <view class="label-des" />
      <view class="upload-box">
        <view class="upload upload-middle">
          <view v-if="!idCardFront" class="text-box" @tap="getUploadImg('idCardFront')">
            <view class="add">+添加</view>
            <view class="des">上传身份证正面照</view>
          </view>
          <view v-if="idCardFront" class="img">
            <image :src="idCardFront" @tap="getUploadImg('idCardFront')" />
          </view>
        </view>
        <view class="upload upload-middle">
          <view v-if="!idCardBack" class="text-box" @tap="getUploadImg('idCardBack')">
            <view class="add">+添加</view>
            <view class="des">上传身份证反面照</view>
          </view>
          <view v-if="idCardBack" class="img">
            <image :src="idCardBack" @tap="getUploadImg('idCardBack')" />
          </view>
        </view>
      </view>
    </view>
    <view class="item">
      <view class="label">移动端背景图</view>
      <view class="upload-box">
        <view v-if="!mobileBackground" class="upload upload-big" @tap="getUploadImg('mobileBackground')">
          <view class="text-box">
            <view class="add">+添加</view>
          </view>
        </view>
        <view v-if="mobileBackground" class="upload license-img">
          <view class="img" @tap="getUploadImg('mobileBackground')">
            <image :src="mobileBackground" />
          </view>
        </view>
      </view>
    </view>
    <view class="item">
      <view class="label">营业执照</view>
      <view class="upload-box">
        <view v-if="!license" class="upload upload-big" @tap="getUploadImg('license')">
          <view class="text-box">
            <view class="add">+添加</view>
            <view class="des">上传文字清晰照片，露出边框和国徽</view>
          </view>
        </view>
        <view v-if="license" class="upload license-img">
          <view class="img" @tap="getUploadImg('license')">
            <image :src="license" />
          </view>
        </view>
      </view>
    </view>

    <view class="item">
      <view v-if="!applyVisible" class="btn" @click="examineShopName">提交申请</view>
      <!-- <view v-if="applyVisible" class="btn" @click="modifyInfo">修改信息</view> -->
    </view>
    <view class="item">
      <view
        class="notes"
      >温馨提示：申请人须保证以上所填信息的真实性。如有因虚假信息而产生的所有后果均由申请人承担。</view>
    </view>

    <!-- 上传图片 -->
    <img-box v-if="showImgBoxPopup" ref="imgbox" class="imgbox-pop" @closeImgPop="ImgBoxPopVisible" />
  </view>
</template>

<script>
const http = require('../../utils/http.js')
import util from '../../utils/util.js'

import imgBox from '../../components/ImgBox/imgBox'
export default {
  components: {
    imgBox
  },
  data() {
    return {
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
    }
  },
  mounted() {
    this.getMyShopInfo()
  },
  methods: {
    /**
     * 获取店铺信息
     */
    getMyShopInfo() {
      var params = {
        url: '/mall4cloud_multishop/my_shop_detail',
        method: 'GET',
        callBack: res => {
          uni.hideLoading()
          if (res) {
            this.applyVisible = true
            this.shopName = res.shopName
            this.intro = res.intro
            this.shopUserName = res.username
            this.shopLogo = res.shopLogo
            this.shopLogoUrl = res.shopLogo
            this.idCardFront = res.identityCardFront
            this.idCardFrontUrl = res.identityCardFront
            this.idCardBack = res.identityCardLater
            this.idCardBackUrl = res.identityCardLater
            this.license = res.businessLicense
            this.licenseUrl = res.businessLicense
            this.mobileBackground = res.mobileBackgroundPic
            this.mobileBackgroundPic = res.mobileBackgroundPic
            // this.getShopAudit(res.shopStatus)
          } else {
            this.applyVisible = false
          }
        }
      }
      http.request(params)
    },
    /**
     * 获取店铺的审核信息
     */
    getShopAudit() {
      var params = {
        url: '/mall4cloud_multishop/a/shop_auditing/shop_audit',
        method: 'GET',
        callBack: res => {
          uni.hideLoading()
          const remarks = res.remarks ? '\n\n审核备注：' + res.remarks : ''
          if (res.status === -1) { // 审核不通过
            uni.showModal({
              title: '提示',
              content: '您的开店申请审核未通过，是否修改信息并重新提交申请？' + remarks,
              cancelText: '暂不修改',
              confirmText: '前往修改',
              success(res) {
                if (res.confirm) {
                  this.applyVisible = false
                } else if (res.cancel) {
                  this.applyVisible = true // 禁用输入框
                }
              }
            })
            return
          }
          if (res.status === 0) { // 未审核
            uni.showModal({
              title: '提示',
              content: '您的开店申请正在加急审核中，请耐心等候...',
              showModal: false,
              confirmText: '确定',
              success(res) {
                if (res.confirm) {
                  this.applyVisible = true // 禁用输入框
                }
              }
            })
            return
          }
        }
      }
      http.request(params)
    },

    /**
     * 选择店铺类型
     */
    selectStoreType(type) {
      this.storeType = type
    },

    /**
     * 上传图片
     */
    getUploadImg: function(type) {
      if (this.applyVisible) {
        return
      }
      this.currentImg = type
      this.showImgBoxPopup = true
      // this.$nextTick(() => {
      //   this.$refs.imgbox.init()
      // })
    },

    /**
     * 上传图片弹窗显隐
     */
    ImgBoxPopVisible(imgUrl, url) {
      if (url) {
        switch (this.currentImg) {
          case 'shopLogo':
            this.shopLogoUrl = url
            this.shopLogo = imgUrl
            break
          case 'idCardFront':
            this.idCardFrontUrl = url
            this.idCardFront = imgUrl
            break
          case 'idCardBack':
            this.idCardBackUrl = url
            this.idCardBack = imgUrl
            break
          case 'license':
            this.licenseUrl = url
            this.license = imgUrl
            break
          case 'mobileBackground':
            this.mobileBackgroundPic = url
            this.mobileBackground = imgUrl
            break
        }
      }
      this.showImgBoxPopup = !this.showImgBoxPopup
    },

    /**
     * 删除图片（当前页面）
     */
    deleteImg(type) {
      switch (type) {
        case 'shopLogo':
          this.shopLogo = ''
          this.shopLogoUrl = ''
          break
        case 'idCardFront':
          this.idCardFront = ''
          this.idCardFrontUrl = ''
          break
        case 'idCardBack':
          this.idCardBack = ''
          this.idCardBackUrl = ''
          break
        case 'license':
          this.license = ''
          this.licenseUrl = ''
          break
        case 'mobileBackground':
          this.mobileBackground = ''
          this.mobileBackgroundPic = ''
          break
      }
    },

    /**
     * 修改信息按钮
     */
    modifyInfo() {
      this.applyVisible = false
    },

    /**
     * 检查店铺名是否重复
     */
    examineShopName() {
      if (this.applyVisible) {
        return
      }
      var params = {
        url: '/mall4cloud_multishop/ua/shop_detail/check_shop_name',
        method: 'GET',
        data: {
          shopName: this.shopName
        },
        callBack: res => {
          uni.hideLoading()
          if (!res) {
            uni.showToast({
              title: '店铺名重复',
              icon: 'none'
            })
            return
          }
          this.validationFormData()
        }
      }
      http.request(params)
    },

    /**
     * 申请开店数据判断
     */
    validationFormData() {
      const reg = new RegExp('^[A-z0-9\\u4e00-\\u9fa5]*$') // 只能输入中文、英文、数字
      const shopName = reg.test(this.shopName)
      if (!this.shopName) {
        this.errorTips = 1
        return
      }
      if (!shopName) {
        this.errorTips = 2
        return
      }
      if (!this.shopUserName) {
        this.errorTips = 6
        return
      }
      if (!util.checkUserName(this.userName)) {
        this.errorTips = 4
        return
      }
      if (!this.shopPassword) {
        this.errorTips = 7
        return
      }
      if (this.shopPassword !== this.confirmationShopPassword) {
        this.errorTips = 13
        return
      }
      if (this.shopPassword.length < 6) {
        this.errorTips = 12
        return
      }
      if (!this.intro) {
        this.errorTips = 5
        return
      }
      this.errorTips = -1
      if (!this.shopLogo) {
        uni.showToast({
          title: '请上传店铺logo',
          icon: 'none'
        })
        return
      }
      if (!this.idCardFront) {
        uni.showToast({
          title: '请上传身份证正面照',
          icon: 'none'
        })
        return
      }
      if (!this.idCardBack) {
        uni.showToast({
          title: '请上传身份证反面照',
          icon: 'none'
        })
        return
      }
      if (!this.mobileBackgroundPic) {
        uni.showToast({
          title: '请上传移动端背景图',
          icon: 'none'
        })
        return
      }
      if (!this.license) {
        uni.showToast({
          title: '请上传店铺营业执照',
          icon: 'none'
        })
        return
      }
      this.errorTips = 0
      this.applyToOpenAShop()
    },

    /**
     * 申请开店
     */
    applyToOpenAShop() {
      const shopDetailDTO = {
        shopName: this.shopName, // 店铺名称， 50字以内
        intro: this.intro, // 简介
        username: this.shopUserName,
        password: this.shopPassword,
        shopLogo: this.shopLogoUrl, // 店铺logo
        identityCardFront: this.idCardFrontUrl, // 身份证正面
        identityCardLater: this.idCardBackUrl, // 身份证反面
        businessLicense: this.licenseUrl, // 营业执照
        mobileBackgroundPic: this.mobileBackgroundPic,
        type: 2 // 店铺类型1自营店 2普通店
      }
      uni.showLoading()
      var params = {
        url: '/mall4cloud_multishop/my_shop_detail/create',
        method: 'POST',
        data: shopDetailDTO,
        callBack: res => {
          uni.hideLoading()
          uni.showModal({
            content: '开店成功', // 提示内容
            showCancel: false,
            success(res) {
              if (res.confirm) {
                uni.reLaunch({
                  url: '/'
                })
              }
            }
          })
        }
      }
      http.request(params)
    }

  }
}
</script>

<style>
@import "./free-shop.css";
@import "../../popup.css";
</style>
