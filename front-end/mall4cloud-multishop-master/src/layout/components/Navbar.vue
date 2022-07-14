<template>
  <div class="navbar">
    <div class="navbar-content">
      <!-- 1.左边部分 -->
      <div class="left-menu">
        <div class="title">商家后台</div>
      </div>

      <!-- 2.右边部分 -->
      <div class="right-menu">
        <div v-if="device !== 'mobile'" class="switch-language font-item">
          <el-dropdown @command="handleSetLanguage">
            <span class="el-dropdown-link">
              语言<i class="el-icon-arrow-down el-icon--right" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                :disabled="language === 'zh'"
                command="zh"
              >中文</el-dropdown-item>
              <el-dropdown-item
                :disabled="language === 'en'"
                command="en"
              >English</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <el-dropdown class="avatar-container" trigger="click">
          <div class="avatar-wrapper">
            <div class="img-box">
              <img
                :src="avatar + '?imageView2/1/w/80/h/80'"
                class="user-avatar"
              >
            </div>
            <div class="user-name">{{ name }}</div>
            <i class="el-icon-caret-bottom" />
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="logout">
              <span style="display: block">{{ $t("navbar.logOut") }}</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      isHavePlatformNotice: true, // 是否有新的平台公告
      isHaveNewsBox: true // 是否有新消息
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'device'
    ]),
    language() {
      return this.$store.getters.language
    }
  },
  methods: {
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    // 切换语言
    handleSetLanguage(lang) {
      this.$i18n.locale = lang
      this.$store.dispatch('app/setLanguage', lang)
      this.$message({
        message: 'Switch Language Success',
        type: 'success'
      })
    },
    // 跳转到平台公告
    toPlatformNotice() {
      // this.$router.push('')
    },
    // 跳转到消息盒子
    toNewsBox() {
      // this.$router.push('')
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  display: flex;
  align-items: center;
  min-width: 1260px;
  width: 100%;
  height: 60px;
  background: #323232;
  overflow: hidden;
  .navbar-content {
    display: flex;
    align-items: center;
    width: 1260px;
    margin: 0 auto;
    // 1.左边部分
    .left-menu {
      display: flex;
      align-items: flex-end;
      .logo-img {
        width: 104px;
        height: 22px;
        img {
          width: 100%;
          height: 100%;
        }
      }
      .title {
        font-size: 14px;
        color: #ffffff;
        margin-left: 4px;
      }
    }

    // 2.右边部分
    .right-menu {
      display: flex;
      align-items: center;
      margin-left: auto;
      .font-item {
        display: flex;
        align-items: center;
        font-size: 14px;
        font-weight: 400;
        color: #ffffff;
        &::after {
          display: block;
          width: 1px;
          height: 18px;
          content: "";
          background: #ffffff;
          opacity: 0.5;
          margin: 0 15px;
        }
        .el-dropdown {
          color: #ffffff;
        }
      }
      .platform-notice,
      .news-box {
        position: relative;
        span {
          position: absolute;
          top: 0;
          right: 23px;
          display: block;
          width: 5px;
          height: 5px;
          background: #fac94f;
          border-radius: 50%;
        }
      }
      .avatar-container {
        .avatar-wrapper {
          display: flex;
          align-items: center;
          position: relative;
          .img-box {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            background: #fff;
            cursor: pointer;
            overflow: hidden;
          }
          .user-avatar {
            width: 100%;
            height: 100%;
          }
          .user-name {
            font-size: 14px;
            font-weight: 400;
            color: #ffffff;
            padding-left: 10px;
            padding-right: 6px;
          }
          .el-icon-caret-bottom {
            font-size: 12px;
            color: #ffffff;
            cursor: pointer;
          }
        }
      }
    }
  }
}
</style>
