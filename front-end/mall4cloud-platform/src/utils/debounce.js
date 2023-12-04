// 防抖 防止表单重复提交
export const Debounce = (fn, t) => {
  const delay = t || 1000
  let timer
  return function () {
    const args = arguments
    if (timer) {
      clearTimeout(timer)
    }

    const callNow = !timer

    timer = setTimeout(() => {
      timer = null
    }, delay)

    if (callNow) fn.apply(this, args)
  }
}
