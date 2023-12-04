<!--
 Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.

 https://www.mall4j.com/

 未经允许，不可做商业用途！

 版权所有，侵权必究！
-->
<template>
  <div :class="['Mall4j',isDecorate?'navbar':'']">
    <div class="navbar-content">
      <!-- 1.左边部分 -->
      <div class="left-menu">
        <img
          style="height: 18px;width:59px;margin-right: 10px"
          src="@/assets/images/logo.png"
          alt=""
        >
        <a
          v-if="(webConfig.bsMenuTitleOpenCn || webConfig.bsMenuTitleCloseCn) || (webConfig.bsMenuTitleOpenEn || webConfig.bsMenuTitleCloseEn)"
          class="site-navbar__brand-lg"
          style="text-transform:none;"
          href="javascript:;"
        >{{ sidebar.opened ? webConfig.bsMenuTitleOpenCn : webConfig.bsMenuTitleCloseCn }}</a>
        <div
          class="shrink"
        >
          <svg-icon
            icon-class="icon-zhedie"
            @click="toggleSideBar"
          />
        </div>
      </div>

      <!-- 2.右边部分 -->
      <div class="right-menu">
        <el-dropdown
          class="avatar-container"
          trigger="hover"
        >
          <div class="avatar-wrapper">
            <div class="user-name">
              {{ name || userName }}
            </div>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="logout">
                <span style="display: block">{{ $t("navbar.logOut") }}</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ElMessageBox } from 'element-plus'
// import UpdatePassword from '@/components/update-password/index.vue'

const resourcesUrl = import.meta.env.VITE_APP_RESOURCES_URL
const webConfigStore = useWebConfigStore()
const webConfig = computed(() => webConfigStore.webConfig)

const appStore = useAppStore()
const sidebar = computed(() => appStore.sidebar)

const userStore = useUserStore()
const name = computed(() => userStore.name)
const userName = computed(() => userStore.userName)

const toggleSideBar = () => {
  if (sessionStorage.getItem('cloudIsExpand') === '0' && !sidebar.value.opened) {
    return
  }
  appStore.toggleSideBar()
}

const route = useRoute()
const router = useRouter()
const logout = async () => {
  ElMessageBox.confirm('确认退出登录？', $t('table.tips'), {
    confirmButtonText: $t('table.confirm'),
    cancelButtonText: $t('table.cancel'),
    type: 'warning'
  }).then(async () => {
    await userStore.logout()
    localStorage.setItem('cloudPlatChatWs', Date.now()) // 通知新窗口关闭ws
    await router.push(`/login?redirect=${route.fullPath}`)
  })
}

const isDecorate = computed(() => {
  return !(/^\/(platform-decorate\/decorate-select-decorate)/.test(router.currentRoute.value.path))
})
</script>

<style lang="scss" scoped>
.navbar {
  top: 0;
  z-index: 2000;
  position: fixed;
  display: flex;
  align-items: center;
  min-width: 1260px;
  width: 100%;
  height: 50px;
  background: #fff;
  overflow: hidden;
  border-bottom: 1px solid #EBEDF0;
  box-sizing: border-box;

  .navbar-content {
    display: flex;
    align-items: center;
    width: 100%;
    padding: 0 20px;
    margin: 0 auto;

    // 1.左边部分
    .left-menu {
      display: flex;
      align-items: center;
    }

    // 2.右边部分
    .right-menu {
      display: flex;
      align-items: center;
      margin-left: auto;
      .avatar-container {
        .avatar-wrapper {
          outline: none;
          cursor: pointer;
        }
      }
    }
  }

  .site-navbar__brand-lg {
    margin: 0 auto;
    word-break: break-all;
    /* 按字符截断换行 支持IE和chrome，FF不支持*/
    word-wrap: break-word;
    /* 按英文单词整体截断换行  以上三个浏览器均支持 */
    color: #333333;
    font-weight: bold;
    font-size: 16px;
  }

  .shrink {
    cursor: pointer;
    margin-left: 23px;
    font-size: 18px;
  }

  .navbar-notice img {
    vertical-align: middle;
    width: 20px;
    height: 20px;
    margin: 8px;
  }

}
</style>
