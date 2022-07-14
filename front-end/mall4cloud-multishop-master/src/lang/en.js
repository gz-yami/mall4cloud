import spu from './product/spu/en'
import attr from './product/attr/en'
import category from './product/category/en'
import notice from './multishop/notice/en'
import shopUser from './multishop/shop-user/en'
import hotSearch from './multishop/hot-search/en'
import imgbox from './biz/imgbox/en'
import role from './rbac/role/en'
import menu from './rbac/menu/en'
import menuPermission from './rbac/menu-permission/en'
import selector from './components/category-selector/en'
import shopUserAccount from './multishop/shop-user-account/en'
import order from './order/order/en'
import constant from './constant/en'
import admin from './admin/en'
import shop from './shop/en'
import address from './address/en'
import product from './product/en'

export default {
  language: 'English',
  route: {
    dashboard: 'Dashboard'
  },
  navbar: {
    logOut: 'Log Out'
  },
  tip: {
    select: 'please select prod',
    input: 'please input'
  },
  tagsView: {
    refresh: 'Refresh',
    close: 'Close',
    closeOthers: 'Close Others',
    closeAll: 'Close All'
  },
  date: {
    start: 'Start date',
    end: 'End date',
    tip: 'to',
    t: 'today',
    y: 'yesterday',
    n: 'nearly 7',
    m: 'nearly 30',
    a: 'whole'
  },
  login: {
    title: 'Login Form',
    logIn: 'Login',
    username: 'Username',
    password: 'Password',
    any: 'any',
    thirdparty: 'Or connect with',
    thirdpartyTips: 'Can not be simulated on local, so please combine you own business simulation! ! !'
  },
  unit: {
    dollar: 'yuan'
  },
  table: {
    search: 'Search',
    add: 'Add',
    export: 'Export',
    id: 'ID',
    status: 'Status',
    actions: 'Actions',
    edit: 'Edit',
    create: 'Create',
    publish: 'Publish',
    delete: 'Delete',
    cancel: 'Cancel',
    offline: 'Offline',
    clear: 'Clear',
    confirm: 'Confirm',
    actionSuccess: 'Actions Success',
    tips: 'Tips',
    seq: 'Queue number',
    sureToDelete: 'Be sure to delete?',
    createTime: 'create time',
    updateTime: 'update time'
  },
  action: {
    putOnShelf: 'Put on the shelf',
    offShelf: 'off the shelf'
  },
  rbac: {
    role,
    menu,
    menuPermission
  },
  multishop: {
    notice,
    shopUser,
    hotSearch,
    shopUserAccount
  },
  product: {
    ...product,
    attr,
    category,
    spu
  },
  biz: {
    imgbox
  },
  order: {
    order
  },
  constant: {
    ...constant
  },
  admin: {
    ...admin
  },
  shop: {
    ...shop
  },
  address: {
    ...address
  },
  components: {
    selector
  }
}
