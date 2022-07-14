<template>
  <el-dialog
    :visible.sync="operateDialogVisible"
    :close-on-click-modal="false"
    title="下线管理"
    :lock-scroll="true"
    :append-to-body="true"
    :destroy-on-close="true"
    width="500px"
    top="0"
    class="operate-dlg"
  >
    <div class="off-shelf-mag">
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
        <div class="msg-item">
          <div class="tit">申请理由：</div>
          <div class="int"><el-input v-model="reapplyReason" type="textarea" /></div>
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
        <el-button type="primary" @click="rereapplyDataSubmit()">确认审核</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import * as api from '@/api/product/list'
export default {
  data() {
    return {
      operateDialogVisible: false, // 操作对话框
      offlineDetail: {}, // 最新下线商品
      offlineReasonError: false, // 理由出错
      reapplyReason: '', // 申请理由
    }
  },

  methods: {
    init(data) {
      this.operateDialogVisible = true
      this.offlineDetail = data
    },

    /**
     * 提交上架申请
     */
    rereapplyDataSubmit() {
      if (!this.reapplyReason) {
        this.$message({
          message: '请填写申请理由',
          type: 'warning',
          duration: 1000
        })
        return
      }
      let data = {
        eventId: this.offlineDetail.eventId,
        reapplyReason: this.reapplyReason
      }
      this.$emit('rereapplyDataSubmit', data)
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
