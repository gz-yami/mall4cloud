/*
* Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
*
* https://www.mall4j.com/
*
* 未经允许，不可做商业用途！
*
* 版权所有，侵权必究！
*/

import { i18n } from '@/lang'

// translate router.meta.title, be used in breadcrumb sidebar tagsview
export function generateTitle (title) {
  const hasKey = i18n.global.te('route.' + title)
  if (hasKey) {
    // $t :this method from vue-i18n, inject in @/lang/index.js
    return $t('route.' + title)
  }
  return title
}
