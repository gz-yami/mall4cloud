import { defineStore } from 'pinia'

/**
 * 为解决element-ui按钮点击后，聚焦不会自动消失问题，
 * 定义统一定时器进行清除聚焦操作
 */
export const useCancelFocusIntervalStore = defineStore('cancelFocus', {
  state: () => {
    return {
      value: null
    }
  },
  actions: {
    clearInterval () {
      clearInterval(this.value)
    },
    createInterval ($event, time) {
      if (this.value) {
        clearInterval(this.value)
        this.value = null
      }
      this.value = setInterval(() => {
        let target = $event.target
        if (target.nodeName === 'SPAN') {
          target = $event.target.parentNode
        }
        target.blur()
      }, time)
    }
  }
})
