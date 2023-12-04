import { useUserStore } from '@/stores/modules/user'

/**
 * 按钮权限
 */
export const checkPermission = {
  mounted (el, binding) {
    const { roles, isAdmin } = useUserStore()
    if (isAdmin) {
      return true
    }
    // 「其他角色」按钮权限校验
    const { value } = binding
    if (value) {
      const requiredPerms = value // DOM绑定需要的按钮权限标识
      const hasPerm = roles?.some(perm => {
        return requiredPerms.includes(perm)
      })

      if (!hasPerm) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(
        "need perms! Like v-has-perm=\"['sys:user:add','sys:user:edit']\""
      )
    }
  }
}
