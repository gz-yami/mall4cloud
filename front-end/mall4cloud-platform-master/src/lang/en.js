import spu from './product/spu/en'
import attr from './product/attr/en'
import score from './user/score/en'
import brand from './product/brand/en'
import category from './product/category/en'
import shopUser from './platform/sys-user/en'
import hotSearch from './platform/hot-search/en'
import imgbox from './biz/imgbox/en'
import role from './rbac/role/en'
import menu from './rbac/menu/en'
import menuPermission from './rbac/menu-permission/en'
import selector from './components/category-selector/en'
import shopUserAccount from './platform/sys-user-account/en'
import config from './platform/config/en'

export default {
  route: {
    dashboard: 'Dashboard'
  },
  navbar: {
    logOut: 'Log Out'
  },
  tagsView: {
    refresh: 'Refresh',
    close: 'Close',
    closeOthers: 'Close Others',
    closeAll: 'Close All'
  },
  login: {
    title: 'Login Form',
    logIn: 'Login',
    username: 'Username',
    password: 'Password',
    any: 'any',
    thirdparty: 'Or connect with',
    thirdpartyTips: 'Can not be simulated on local, so please combine you own business simulation! ! !',
    validateUsername: 'Please enter the correct username',
    validatePassword: 'Password cannot be less than 6 digits'
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
    clear: 'clear',
    confirm: 'Confirm',
    actionSuccess: 'Actions Success',
    tips: 'Tips',
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
  platform: {
    config,
    shopUser,
    hotSearch,
    shopUserAccount
  },
  product: {
    attr,
    brand,
    category,
    spu
  },
  biz: {
    imgbox
  },
  user: {
    score
  },
  components: {
    selector
  }
}
