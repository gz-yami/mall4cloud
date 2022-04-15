<template>
  <!-- 下架理由/下架管理 -->
  <el-dialog
    :visible.sync="operateDialogVisible"
    :close-on-click-modal="false"
    :title="showOfflineOperate?'提示':showOfflineManage?'下线管理':''"
    :lock-scroll="true"
    :append-to-body="true"
    :destroy-on-close="true"
    width="500px"
    top="0"
    class="operate-dlg"
    @close="onClose"
  >
    <!-- 下架 -->
    <div v-if="showOfflineOperate" class="offline">
      <div class="confirm-tips">{{ confirmTips }}</div>
      <el-input v-model="offlineReason" placeholder="请填写下架理由" @blur="blurOfflineReason" />
      <div v-if="offlineReasonError" class="error">请填写下架理由</div>
      <div class="foot">
        <el-button @click="operateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onOrOffTheShelf">确定</el-button>
      </div>
    </div>

    <!-- 下架管理 -->
    <div v-if="showOfflineManage" class="off-shelf-mag">
      <div class="msg">
        <div class="msg-item">
          <div class="tit">处理人：</div>
          <div class="int">{{ offlineDetail.handler }}</div>
        </div>
        <div class="msg-item">
          <div class="tit">下线时间：</div>
          <div class="int">{{ offlineDetail.createTime }}</div>
        </div>
        <div class="msg-item">
          <div class="tit">处理状态：</div>
          <el-tag v-if="offlineDetail.status === 1" type="danger">平台下线</el-tag>
          <el-tag v-if="offlineDetail.status === 2" type="warning">等待审核</el-tag>
          <el-tag v-if="offlineDetail.status === 3" type="success">审核通过</el-tag>
          <el-tag v-if="offlineDetail.status === 4" type="danger">审核未通过</el-tag>
        </div>
        <div class="msg-item">
          <div class="tit">下线原因：</div>
          <div class="int">{{ offlineDetail.offlineReason }}</div>
        </div>
        <div v-if="offlineDetail.status === 2" class="msg-item">
          <div class="tit">处理状态：</div>
          <div class="int">
            <el-radio-group v-model="examineStatus">
              <el-radio :label="3">审核通过</el-radio>
              <el-radio :label="4">审核不通过</el-radio>
            </el-radio-group>
          </div>
        </div>
        <div v-if="offlineDetail.status === 2 && examineStatus===4" class="msg-item">
          <div class="tit">拒绝原因：</div>
          <div class="int"><el-input v-model="refuseReason" type="textarea" /></div>
        </div>
      </div>
      <div v-if="offlineDetail.offlineHandleEventItemList.length > 0" class="log">
        <div class="log-tit">申请历史</div>
        <div v-for="(item,index) in offlineDetail.offlineHandleEventItemList" :key="index" class="log-item">
          <p>申请时间：{{ item.reapplyTime }}</p>
          <p>申请理由：{{ item.reapplyReason }}</p>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="operateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="offlineDataSubmit()">确认审核</el-button>
      </div>
    </div>
  </el-dialog>
  <!-- 下架理由/下架管理 end -->
</template>

<script>
export default {
  data() {
    return {
      operateDialogVisible: false, // 操作对话框
      showOfflineOperate: false, // 下架
      showOfflineManage: false, // 下架管理
      offlineReason: '', // 下架原因
      confirmTips: '', // 下线确认提示
      offlineDetail: {}, // 最新下线
      offlineReasonError: false, // 理由出错
      refuseReason: '', // 拒绝理由
      examineStatus: 3, // 审核
      showApplicationHistory: false // 申请历史显隐
    }
  },

  methods: {
    init(key, confirmTips, data) {
      this.operateDialogVisible = true
      if (key === 'offline') {
        this.showOfflineOperate = true
        this.showOfflineManage = false
        this.confirmTips = confirmTips
      } else if (key === 'offlineManage') {
        this.showOfflineManage = true
        this.showOfflineOperate = false
        this.offlineDetail = data
      }
    },
    blurOfflineReason() {
      if (this.offlineReason) {
        this.offlineReasonError = false
      }
    },
    // 下线
    onOrOffTheShelf() {
      if (!this.offlineReason) {
        this.offlineReasonError = true
        return
      }
      this.$emit('onOrOffTheShelf', this.offlineReason)
    },
    // 审核
    offlineDataSubmit() {
      if (this.examineStatus === 4 && !this.refuseReason) {
        this.$message({
          message: '请填写拒绝理由',
          type: 'warning',
          duration: 1000
        })
        return
      }
      const data = {
        eventId: this.offlineDetail.eventId,
        status: this.examineStatus, // 审核状态
        refuseReason: this.refuseReason // 拒绝理由
      }
      this.$emit('offlineDataSubmit', data)
    },

    // 当关闭时
    onClose() {
      this.operateDialogVisible = false, // 操作对话框
      this.showOfflineOperate = false, // 下架
      this.showOfflineManage = false, // 下架管理
      this.offlineReason = '', // 下架原因
      this.couponName = '', // 名称
      this.offlineDetail = {}, // 最新下线
      this.offlineReasonError = false, // 理由出错
      this.refuseReason = '', // 拒绝理由
      this.examineStatus = 3, // 审核
      this.showApplicationHistory = false // 申请历史显隐
    }
  }
}
</script>

<style lang="scss" scoped>
// 下架管理-操作弹窗
.operate-dlg.el-dialog__wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
}
.operate-dlg {
  .el-dialog__body {
    padding: 10px 20px;
    .offline {
      margin-bottom: 10px;
      .confirm-tips {
        margin-bottom: 20px;
        line-height: 1.5em;
      }
      .error {
        margin-top: 10px;
        color: #e43130;
      }
      .foot {
        display: block;
        width: 100%;
        text-align: right;
        margin-top: 30px;
      }
    }
    .off-shelf-mag {
      .msg {
        margin-bottom: 10px;
        .msg-item {
          display: flex;
          align-items: center;
          justify-content: flex-start;
          width: 100%;
          margin-bottom: 20px;
          .tit {
            width: 80px;
            padding-right: 10px;
            text-align: right;
          }
          .int {
            width: 380px;
          }
        }
      }
      .log {
        border-top: 1px solid #eee;
        border-bottom: 1px solid #eee;
        padding: 10px 0;
        p {
          margin: 0;
          padding: 0;
          line-height: 1.5em;
        }
        .log-tit {
          font-size: 14px;
          font-weight: bold;
          margin-bottom: 10px;
        }
        .log-item:not(:last-child) {
          margin-bottom: 8px;
        }
      }
      .dialog-footer {
        text-align: right;
        margin-bottom: 10px;
        margin-top: 30px;
      }
    }
  }
}
</style>
