<template>
  <div class="login-container">
    <div class="login-frame">
      <div class="login-img">
        <img src="~@/assets/images/login-img.jpg">
      </div>
      <div class="login-info">
        <div class="title">蓝海商家端管理系统</div>
        <div class="input-box">
          <div class="number inp-item" :class="[selectItem==='username'? 'selected' : '', usernameErrTips? 'err-line' : '']">
            <img src="~@/assets/images/number.png">
            <input
              v-model="loginInfo.username"
              type="text"
              :placeholder="$t('login.username')"
              @focus="inputFocus('username')"
              @blur="inputDefocus('username')"
            >
            <div v-if="usernameErrTips" class="err-tips">请输入正确的用户名</div>
          </div>
          <div class="password inp-item" :class="[selectItem==='password'? 'selected' : '', passwordErrTips? 'err-line' : '']">
            <img src="~@/assets/images/password.png">
            <input
              v-model="loginInfo.password"
              type="password"
              :placeholder="$t('login.password')"
              @keyup.enter="handleLogin"
              @focus="inputFocus('password')"
              @blur="inputDefocus('password')"
            >
            <div v-if="passwordErrTips" class="err-tips">密码不能少于6位</div>
          </div>
        </div>
        <div class="login-btn" :loading="loading" @click="handleLogin">登录</div>
      </div>
      <div class="switch-language">
        <el-dropdown @command="handleSetLanguage">
          <span class="el-dropdown-link">
            语言<i class="el-icon-caret-bottom el-icon--right" />
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item :disabled="language==='zh'" command="zh">中文</el-dropdown-item>
            <el-dropdown-item :disabled="language==='en'" command="en">English</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <div class="bottom">Copyright 2018-2021 广州市蓝海创新科技有限公司</div>
    <Verify
      v-if="initVerify"
      ref="verify"
      :captcha-type="'blockPuzzle'"
      :img-size="{width:'400px',height:'200px'}"
      @success="login"
    />
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import Verify from '@/components/Verifition/Verify'

export default {
  name: 'Login',
  components: { Verify },
  data() {
    return {
      selectItem: '', // '':未选中；'username':用户名；'password':密码
      usernameErrTips: false,
      passwordErrTips: false,
      loginInfo: {
        username: '', // 用户名
        password: '', // 密码
        captcha: '' // 验证码
      },
      loading: false,
      initVerify: false,
      redirect: undefined,
      otherQuery: {}
    }
  },
  computed: {
    language() {
      return this.$store.getters.language
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  methods: {
    // 切换语言
    handleSetLanguage(lang) {
      this.$i18n.locale = lang
      this.$store.dispatch('app/setLanguage', lang)
      this.$message({
        message: 'Switch Language Success',
        type: 'success'
      })
    },
    // 输入框聚焦监听
    inputFocus(item) {
      this.lightOutline(item)
      if (item === 'username') {
        this.usernameErrTips = false
      } else if (item === 'password') {
        this.passwordErrTips = false
      }
    },
    // 输入框失焦监听
    inputDefocus(item) {
      this.lightDisappear()
      this.judgeMonitorInput(item)
    },
    // 底边框换颜色
    lightOutline(item) {
      this.selectItem = item
    },
    // 底边框恢复成灰色
    lightDisappear() {
      this.selectItem = ''
    },
    // 判断监听的是哪个输入框
    judgeMonitorInput(item) {
      // if (item === 'username') {
      //   this.userNameVerification()
      // } else if (item === 'password') {
      //   this.passwordVerification()
      // }
    },
    // 用户名输入框验证
    userNameVerification() {
      const username = this.loginInfo.username
      if (!validUsername(username)) {
        this.usernameErrTips = true
      } else {
        this.usernameErrTips = false
      }
    },
    // 密码输入框验证
    passwordVerification() {
      const password = this.loginInfo.password
      if (password.length < 6) {
        this.passwordErrTips = true
      } else {
        this.passwordErrTips = false
      }
    },
    handleLogin() {
      // 验证用户名和密码是否符合规则
      // this.userNameVerification()
      // this.passwordVerification()
      if (this.usernameErrTips === true) {
        return
      }
      if (this.passwordErrTips === true) {
        return
      }
      this.initVerify = true
      this.$nextTick(() => {
        this.$refs.verify.show()
      })
    },
    login(verifyResult) {
      console.log(verifyResult.captchaVerification)
      this.loading = true
      this.loginInfo.captcha = verifyResult.captchaVerification
      this.$store.dispatch('user/login', this.loginInfo)
        .then(() => {
          this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
          this.loading = false
        })
        .catch((e) => {
          console.log(e)
          this.loading = false
        })
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
  }
}
</script>

<style lang="scss">
.login-container {
  position: relative;
  width: 100%;
  height: 100vh;
  background: url(~@/assets/images/login-bg.jpg) no-repeat;
  background-size: cover;
  .login-frame {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    display: flex;
    align-items: center;
    width: 1200px;
    height: 678px;
    background: #fff;
    box-shadow: 0px 0px 80px 0px rgba(0, 0, 0, 0.1);
    margin: auto;
    .login-img {
      margin-left: 80px;
    }
    .login-info {
      margin-left: 80px;
      .title {
        font-size: 30px;
        font-weight: 400;
        color: #000000;
      }
      .input-box {
        margin-top: 75px;
        .inp-item {
          position: relative;
          display: flex;
          align-items: center;
          width: 330px;
          height: 40px;
          padding-bottom: 8px;
          border-bottom: 1px solid #eee;
          img {
            width: 20px;
            height: 20px;
          }
          input {
            border: none;
            outline: none;
            width: 100%;
            height: 100%;
            padding: 0 12px;
          }
          .err-tips {
            position: absolute;
            bottom: -22px;
            font-size: 14px;
            color: #d40000;
          }
        }
        .password {
          margin-top: 35px;
        }
        .selected {
          border-bottom: 1px solid #007de4;
        }
        .err-line {
          border-bottom: 1px solid #d40000;
        }
      }
      .login-btn {
        width: 334px;
        height: 48px;
        line-height: 48px;
        font-size: 18px;
        font-weight: 400;
        color: #FFFFFF;
        background: #007DE4;
        border-radius: 24px;
        text-align: center;
        margin-top: 80px;
        cursor: pointer;
      }
    }
    .switch-language {
      position: absolute;
      top: 20px;
      right: 20px;
      padding: 0 8px;
      border: 1px solid #0076FE;
      .el-dropdown-link {
        font-size: 12px;
        color: #0076FE;
        cursor: pointer;
      }
    }
  }
  .bottom {
    position: absolute;
    width: 100%;
    bottom: 63px;
    font-size: 12px;
    color: #AAA;
    text-align: center;
  }
}
</style>
