import { useCancelFocusIntervalStore } from '@/stores/modules/cancel-focus-interval.js'

/**
 * 元素被点击后一段时间自动取消聚焦
 * 主要用于解决 element-ui 按钮点击后高亮显示不会复原问题
 * binding.value 设定几秒后取消，尽可能不低于1000，防止定时器内存占用过大（单位：毫秒）
 */
export const cancelFocus = {
  mounted: (el, binding) => {
    const btnInterval = useCancelFocusIntervalStore()
    let time = binding.value
    if (time < 1000 || !time) time = 1500
    el.addEventListener('click', ($event) => {
      btnInterval.createInterval($event, time)
    })
  },
  beforeUnmount: (el) => {
    const btnInterval = useCancelFocusIntervalStore()
    btnInterval.clearInterval()
    el.removeEventListener('click', () => {})
  }
}
