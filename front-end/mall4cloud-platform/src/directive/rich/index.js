import DOMPurify from 'dompurify'
// 记录旧数据
let oldInnerHTML = ''

function setHTML (el, value) {
  let innerHTML = value
  innerHTML = DOMPurify.sanitize(innerHTML, { ADD_ATTR: ['target'] })
  el.innerHTML = innerHTML
}

export const richDirective = {
  mounted (el, binding) {
    if (binding.value) {
      setHTML(el, binding.value)
    }
  },
  updated (el, binding) {
    // 如果旧数据跟新数据一样 直接 return
    if (binding.value === oldInnerHTML && !binding.value) {
      return
    }
    oldInnerHTML = binding.value
    if (binding.value) {
      setHTML(el, binding.value)
    }
  }
}
