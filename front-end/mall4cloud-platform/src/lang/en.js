import spu from './product/spu/en'
import transport from './delivery/transport/en'
import deliveryCompany from './delivery/delivery-company/en'
import discount from './discount/en'
import attr from './product/attr/en'
import spuTag from './product/spu-tag/en'
import score from './user/score/en'
import level from './user/level/en'
import brand from './product/brand/en'
import category from './product/category/en'
import shopUser from './platform/sys-user/en'
import hotSearch from './platform/hot-search/en'
import imgbox from './biz/imgbox/en'
import notify from './biz/notify/en'
import videobox from './biz/videobox/en'
import role from './rbac/role/en'
import menu from './rbac/menu/en'
import menuPermission from './rbac/menu-permission/en'
import selector from './components/category-selector/en'
import shopUserAccount from './platform/sys-user-account/en'
import shopAddr from './platform/shop-addr/en'
import config from './platform/config/en'
import coupon from './coupon/coupon/en'
import group from './group/en'
import seckill from './seckill/en'
import userManager from './user/user-manager/en'
import recharge from './user/recharge/en'
import rights from './user/rights/en'
import tag from './user/tag/en'
import station from './station/en'
import admin from './admin/en'
import constant from './constant/en'
import chat from './chat/en'
import webConfig from './rbac/web-config/en'
import menuResource from './rbac/menu-resource/en'
import live from './live/en'
import outletConfig from './outletConfig/en'
import printer from './printer/en'

export default {
  pay: {
    notPaid: 'Not Paid',
    obligation: 'obligation'
  },
  route: {
    dashboard: 'Dashboard'
  },
  tip: {
    select: 'please select prod',
    input: 'please input'
  },
  navbar: {
    logOut: 'Log Out',
    changePassword: 'Change Password'
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
    oriPassword: 'Original password',
    newPassword: 'New password',
    conPassword: 'Confirm the password',
    pawNoNull: 'Password cannot be empty',
    passwordEmpty: 'The confirm password cannot be empty',
    passwordTips: 'Please enter a new password',
    any: 'any',
    thirdparty: 'Or connect with',
    thirdpartyTips: 'Can not be simulated on local, so please combine you own business simulation! ! !'
  },
  resetPassword: {
    passwordError: 'The confirmation password is inconsistent',
    passwordTips3: 'The password length is 6-16 digits'
  },
  register: {
    passwordErrorTips2: 'Spaces are not allowed as the beginning or end of a password'
  },
  unit: {
    dollar: 'yuan',
    day: 'day',
    year: 'year',
    pieces: 'pieces'
  },
  table: {
    search: 'Search',
    add: 'Add',
    export: 'Export',
    id: 'ID',
    status: 'Status',
    save: 'Save',
    actions: 'Actions',
    edit: 'Edit',
    view: 'view',
    create: 'Create',
    addGood: ' Add good',
    publish: 'Publish',
    delete: 'Delete',
    batchDelete: 'Batch delete',
    cancel: 'Cancel',
    clear: 'clear',
    reset: 'reset',
    confirm: 'Confirm',
    actionSuccess: 'Actions Success',
    switchSuccess: 'Switch Language Success',
    tips: 'Tips',
    sureToDelete: 'Be sure to delete?',
    sureToOffline: 'Be sure to offline?',
    sureToExport: 'Be sure to export operation?',
    createTime: 'create time',
    updateTime: 'update time',
    shopName: 'Shop name',
    upload: 'Upload',
    tagListIsEmpty: 'Tag list is empty, please add tag after push',
    submit: 'Submit',
    checkAll: 'Check All'
  },
  form: {
    content: 'Please enter content',
    select: 'Please select',
    nameZh: 'Chinese name',
    nameEn: 'English name',
    descZh: 'Chinese desc',
    descEn: 'English desc'
  },
  action: {
    putOnShelf: 'Put on the shelf',
    offShelf: 'off the shelf'
  },
  rbac: {
    role,
    menu,
    menuPermission,
    webConfig,
    menuResource
  },
  platform: {
    config,
    shopUser,
    hotSearch,
    shopUserAccount,
    shopAddr
  },
  product: {
    attr,
    brand,
    category,
    spuTag,
    spu
  },
  delivery: {
    transport,
    deliveryCompany
  },
  discount: {
    discount
  },
  biz: {
    imgbox,
    notify,
    videobox
  },
  user: {
    score,
    level,
    userManager,
    recharge,
    rights,
    tag
  },
  coupon: {
    coupon
  },
  components: {
    selector
  },
  group: {
    ...group
  },
  seckill: {
    ...seckill
  },
  station: {
    ...station
  },
  admin: {
    ...admin
  },
  chat: {
    ...chat
  },
  constant: {
    ...constant
  },
  ...live,
  outletConfig: {
    ...outletConfig
  },
  printer: {
    ...printer
  }
}
