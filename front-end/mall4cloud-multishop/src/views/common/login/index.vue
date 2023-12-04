<template>
  <div class="page-login">
    <div class="login-frame">
      <div class="login-img">
        <img src="~@/assets/images/login-img.jpg">
      </div>
      <div class="login-info">
        <div class="title">
          蓝海商家端管理系统
        </div>
        <div class="input-box">
          <div
            class="number inp-item"
            :class="[selectItem==='username'? 'selected' : '', usernameErrTips? 'err-line' : '']"
          >
            <img src="~@/assets/images/number.png">
            <input
              v-model="loginInfo.username"
              type="text"
              :placeholder="$t('login.username')"
              @focus="inputFocus('username')"
              @blur="inputDefocus('username')"
            >
            <div
              v-if="usernameErrTips"
              class="err-tips"
            >
              请输入正确的用户名
            </div>
          </div>
          <div
            class="password inp-item"
            :class="[selectItem==='password'? 'selected' : '', passwordErrTips? 'err-line' : '']"
          >
            <img src="~@/assets/images/password.png">
            <input
              v-model="loginInfo.password"
              type="password"
              :placeholder="$t('login.password')"
              @keyup.enter="handleLogin"
              @focus="inputFocus('password')"
              @blur="inputDefocus('password')"
            >
            <div
              v-if="passwordErrTips"
              class="err-tips"
            >
              密码不能少于6位
            </div>
          </div>
        </div>
        <div
          class="login-btn"
          :loading="loading"
          @click="handleLogin"
        >
          登录
        </div>
      </div>
      <div class="switch-language">
        <el-dropdown @command="handleSetLanguage">
          <span class="el-dropdown-link">
            语言<el-icon><CaretBottom /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item
                :disabled="language==='zh'"
                command="zh"
              >
                中文
              </el-dropdown-item>
              <el-dropdown-item
                :disabled="language==='en'"
                command="en"
              >
                English
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <div class="bottom">
      Copyright 2018-2021 广州市蓝海创新科技有限公司
    </div>
    <Verify
      v-if="initVerify"
      ref="verifyRef"
      :captcha-type="'blockPuzzle'"
      :img-size="{width:'400px',height:'200px'}"
      @success="login"
    />
  </div>
</template>

<script setup>
import { useUserStore } from '../../../stores/modules/user'

const Data = reactive({
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
})
const { selectItem, usernameErrTips, passwordErrTips, loginInfo, loading, initVerify } = toRefs(Data)

const appStore = useAppStore()

const language = computed(() => appStore.language)

// 切换语言
const handleSetLanguage = (lang) => {
  localStorage.setItem('cloudLanguage', lang)
  appStore.setLanguage(lang)
  window.location.reload()
  ElMessage({
    message: 'Switch Language Success',
    type: 'success'
  })
}

// 输入框聚焦监听
const inputFocus = (item) => {
  lightOutline(item)
  if (item === 'username') {
    Data.usernameErrTips = false
  } else if (item === 'password') {
    Data.passwordErrTips = false
  }
}

// 输入框失焦监听
const inputDefocus = (item) => {
  lightDisappear()
  judgeMonitorInput(item)
}

// 底边框换颜色
const lightOutline = (item) => {
  Data.selectItem = item
}

// 底边框恢复成灰色
const lightDisappear = () => {
  Data.selectItem = ''
}

// 判断监听的是哪个输入框
const judgeMonitorInput = (item) => {

}

const verifyRef = ref()
const handleLogin = () => {
  // 验证用户名和密码是否符合规则
  if (Data.usernameErrTips === true) {
    return
  }
  if (Data.passwordErrTips === true) {
    return
  }
  Data.initVerify = true
  nextTick(() => {
    verifyRef.value.show()
  })
}

const router = useRouter()
const login = (verifyResult) => {
  Data.loading = true
  Data.loginInfo.captcha = verifyResult.captchaVerification
  useUserStore().login(Data.loginInfo)
    .then(() => {
      router.push({ path: Data.redirect || '/', query: Data.otherQuery })
      Data.loading = false
    })
    .catch((e) => {
      Data.loading = false
    })
}

</script>

<style lang="scss" scoped>
.page-login {
  position: relative;
  width: 100%;
  height: 100vh;
  background: url(@/assets/images/login-bg.jpg) no-repeat;
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
        border:none;
        line-height:20px;
        font-size: 12px;
        color: #0076FE;
        cursor: pointer;

        &:hover{
        border:none;

        }
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
