<template>
  <div class="login-container">
    <el-form
      ref="loginFormRef"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      autocomplete="on"
      label-position="left"
    >
      <div class="title-container">
        <h3 class="title">
          {{ $t('login.title') }}
        </h3>
        <lang-select class="set-language" />
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="usernameRef"
          v-model="loginForm.username"
          :placeholder="$t('login.username')"
          name="username"
          type="primary"
          link
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="passwordRef"
          v-model="loginForm.password"
          :type="passwordType"
          :placeholder="$t('login.password')"
          name="password"
          tabindex="2"
          autocomplete="on"
          @keyup="checkCapslock"
          @blur="capsTooltip = false"
          @keyup.enter="handleLogin"
        />
        <span
          class="show-pwd"
          @click="showPwd"
        >
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        style="width:100%;margin-bottom:30px;"
        @click.prevent="handleLogin"
      >
        {{ $t('login.logIn') }}
      </el-button>
    </el-form>
    <Verify
      v-if="initVerify"
      ref="verifyRef"
      :captcha-type="'blockPuzzle'"
      :img-size="{ width: '400px', height: '200px' }"
      @success="login"
    />
  </div>
</template>

<script setup>
import { validUsername } from '@/utils/validate'

const validateUsername = (rule, value, callback) => {
  if (!validUsername(value)) {
    callback(new Error($t('login.validateUsername')))
  } else {
    callback()
  }
}
const validatePassword = (rule, value, callback) => {
  if (value.length < 6) {
    callback(new Error($t('login.validatePassword')))
  } else {
    callback()
  }
}

const Data = reactive({
  loginForm: {
    username: 'admin', // 用户名
    password: '123456', // 密码
    captcha: '' // 验证码
  },
  loginRules: {
    username: [{ required: true, trigger: 'blur', validator: validateUsername }],
    password: [{ required: true, trigger: 'blur', validator: validatePassword }]
  },
  passwordType: 'password',
  capsTooltip: false,
  loading: false,
  initVerify: false,
  redirect: undefined,
  otherQuery: {}
})

const { loginForm, loginRules, passwordType, capsTooltip, loading, initVerify } = toRefs(Data)

const route = useRoute()
watch(route, (route) => {
  const query = route.query
  if (query) {
    Data.redirect = query.redirect
    Data.otherQuery = getOtherQuery(query)
  }
})

const username = ref()
const password = ref()
onMounted(() => {
  if (Data.loginForm.username === '') {
    username.value.focus()
  } else if (Data.loginForm.password === '') {
    password.value.focus()
  }
})

const checkCapslock = (e) => {
  const { key } = e
  Data.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
}

const showPwd = () => {
  if (Data.passwordType === 'password') {
    Data.passwordType = ''
  } else {
    Data.passwordType = 'password'
  }
  nextTick(() => {
    password.value.focus()
  })
}

const loginFormRef = ref()
const verifyRef = ref()
const handleLogin = () => {
  loginFormRef.value.validate(valid => {
    if (valid) {
      Data.initVerify = true
      nextTick(() => {
        verifyRef.value.show()
      })
    } else {
      return false
    }
  })
}

const userStore = useUserStore()
const router = useRouter()
const login = (verifyResult) => {
  Data.loading = true
  Data.loginForm.captcha = verifyResult.captchaVerification
  userStore.login(Data.loginForm)
    .then(() => {
      router.push({ path: Data.redirect || '/', query: Data.otherQuery })
      Data.loading = false
    })
    .catch((e) => {
      Data.loading = false
    })
}

const getOtherQuery = (query) => {
  return Object.keys(query).reduce((acc, cur) => {
    if (cur !== 'redirect') {
      acc[cur] = query[cur]
    }
    return acc
  }, {})
}

</script>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100vh;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;

    :deep(.el-form-item) {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      box-sizing: border-box;
      border-radius: 5px;
      color: #454545;

      .el-form-item__content {
        .el-input {
          width: 418px;
          border: none;

          .el-input__wrapper {
            background-color: transparent;
            box-shadow:none;

            input{
              color:#fff;
            }
          }
        }
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }

    .set-language {
      color: #fff;
      position: absolute;
      top: 3px;
      font-size: 18px;
      right: 0px;
      cursor: pointer;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
