import { checkPermission } from './permission'
import { cancelFocus } from './cancelFocus'
import { richDirective } from './rich'

// 全局注册 directive
export function setupDirective (app) {
  // v-permission 挂载到全局
  app.directive('permission', checkPermission)
  // v-cancel-focus 挂载到全局
  app.directive('cancelFocus', cancelFocus)
  app.directive('rich', richDirective)
}
