<template>
  <view class="confirm-pop" :class="{show: showPop}">
    <view class="confirm-pop-mask" @tap="cancel"></view>
    <view class="confirm-pop-main">
      <view class="pop-head">{{popContent.title || '提示'}}</view>
      <view class="pop-content">{{popContent.content}}</view>
      <view class="pop-btns">
        <text class="pop-btn cancel-btn" :class="{hide:!showCancel}" @tap="cancel">{{popContent.cancelText || '取消'}}</text>
        <text class="pop-btn confirm-btn" :class="{noCancle:!showCancel}" @tap="confirm">{{popContent.confirmText || '确认'}}</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  props: {
    showPop: {
      type: Boolean,
      default: false
    },
    popContent: {
      type: Object,
      default: ()=> {}
    },
    showCancel: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    cancel() {
      this.$emit('handlePopCancel')
    },
    confirm() {
      this.$emit('handlePopConfirm')
    }
  }
}
</script>

<style>
.confirm-pop {
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  z-index: -1;
}
.confirm-pop.show {
  z-index: 999;
}
.confirm-pop-mask {
  height: 0%;
  width: 100%;
  background: rgba(0, 0, 0, .4);
}

.show .confirm-pop-mask {
  height: 100%;
}

.confirm-pop-main {
  width: 80%;
  background-color: #fff;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) scale(0);
  transition: all .2s;
  opacity: 0;
  padding: 50rpx 30rpx 40rpx;
  text-align: center;
  border-radius: 20rpx;
}
.show .confirm-pop-main {
  opacity: 1;
  transform: translate(-50%, -50%) scale(1);
}
.pop-head {
  font-weight: bold;
  font-size: 28rpx;
}
.pop-content {
  margin: 30rpx 0 40rpx;
}
.pop-btns {
  text-align: center;
}
.pop-btn {
  display: inline-block;
  padding: 10rpx 70rpx;
  text-align: center;
  color: #fc1b35;
  border-radius: 56px;
  border: 2rpx solid #fc1b35;
  box-sizing: border-box;
}
.confirm-btn {
  background-color: #fc1b35;
  color: #fff;
  margin-left: 30rpx;
}

.hide {
  display: none;
}
.pop-btn.noCancle{
  width: 80%;
  margin: 0;
}
</style>