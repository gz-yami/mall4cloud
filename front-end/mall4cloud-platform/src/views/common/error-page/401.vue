<template>
  <div class="errPage-container">
    <el-button
      class="pan-back-btn"
      @click="back"
    >
      返回
    </el-button>
    <el-row>
      <el-col>
        <h2 class="text-jumbo text-ginormous">
          你没有权限去该页面 ！
        </h2>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
const router = useRouter()
const back = () => {
  router.go(-1)
}

</script>

<style lang="scss" scoped>
  .errPage-container {
    width: 800px;
    max-width: 100%;
    margin: 100px auto;
    .pan-back-btn {
      background: #008489;
      color: #fff;
      border: none!important;
    }
    .text-jumbo {
      font-size: 60px;
      font-weight: 700;
      color: #484848;
    }
  }
</style>
