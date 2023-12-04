import spu from './product/spu/zh'
import transport from './delivery/transport/zh'
import deliveryCompany from './delivery/delivery-company/zh'
import discount from './discount/zh'
import attr from './product/attr/zh'
import spuTag from './product/spu-tag/zh'
import brand from './product/brand/zh'
import category from './product/category/zh'
import shopUser from './platform/sys-user/zh'
import hotSearch from './platform/hot-search/zh'
import imgbox from './biz/imgbox/zh'
import notify from './biz/notify/zh'
import videobox from './biz/videobox/zh'
import role from './rbac/role/zh'
import menu from './rbac/menu/zh'
import menuPermission from './rbac/menu-permission/zh'
import selector from './components/category-selector/zh'
import shopUserAccount from './platform/sys-user-account/zh'
import shopAddr from './platform/shop-addr/zh'
import score from './user/score/zh'
import level from './user/level/zh'
import rights from './user/rights/zh'
import config from './platform/config/zh'
import coupon from './coupon/coupon/zh'
import notice from './platform/notice/zh'
import group from './group/zh'
import pcdecorate from './pcdecorate/zh'
import shopFeature from './shopFeature/zh'
import seckill from './seckill/zh'
import userManager from './user/user-manager/zh'
import recharge from './user/recharge/zh'
import order from './order/order/zh'
import tag from './user/tag/zh'
import station from './station/zh'
import admin from './admin/zh'
import constant from './constant/zh'
import distributionSet from './distribution/set/zh'
import distributionProd from './distribution/prod/zh'
import distributionUser from './distribution/user/zh'
import chat from './chat/zh'
import webConfig from './rbac/web-config/zh'
import menuResource from './rbac/menu-resource/zh'
import live from './live/zh'
import outletConfig from './outletConfig/zh'
import printer from './printer/zh'

export default {
  pay: {
    notPaid: '未付款',
    obligation: '待付款'
  },
  route: {
    dashboard: '首页'
  },
  tip: {
    select: '请选择',
    input: '请输入'
  },
  navbar: {
    logOut: '退出登录',
    changePassword: '修改密码'
  },
  tagsView: {
    refresh: '刷新',
    close: '关闭',
    closeOthers: '关闭其它',
    closeAll: '关闭所有'
  },
  login: {
    title: '系统登录',
    logIn: '登录',
    username: '账号',
    password: '密码',
    oriPassword: '原密码',
    newPassword: '新密码',
    conPassword: '确认密码',
    pawNoNull: '密码不能为空',
    passwordEmpty: '确认密码不能为空',
    passwordTips: '请输入新密码',
    any: '随便填',
    thirdparty: '第三方登录',
    thirdpartyTips: '本地不能模拟，请结合自己业务进行模拟！！！'
  },
  resetPassword: {
    passwordError: '确认密码不一致',
    passwordTips3: '密码长度为6-16位'
  },
  register: {
    passwordErrorTips2: '不允许使用空格作为密码的开头或结尾'
  },
  unit: {
    dollar: '元',
    day: '天',
    year: '年',
    pieces: '件'
  },
  table: {
    search: '搜索',
    add: '添加',
    export: '导出',
    id: '序号',
    status: '状态',
    actions: '操作',
    edit: '编辑',
    save: '保存',
    create: '新建',
    addGood: '添加商品',
    view: '查看',
    clear: '清空',
    reset: '重置',
    publish: '发布',
    delete: '删除',
    batchDelete: '批量删除',
    cancel: '取消',
    confirm: '确定',
    actionSuccess: '操作成功',
    switchSuccess: '成功切换语言',
    tips: '提示',
    noNull: '不能为空',
    sureToDelete: '确定进行删除操作?',
    sureToOffline: '确定进行下线操作?',
    sureToExport: '确定进行导出操作?',
    createTime: '创建时间',
    updateTime: '更新时间',
    applyForListing: '申请上架',
    platformDown: '平台下架',
    disable: '禁用',
    enable: '启用',
    waitReview: '等待审核',
    shopName: '店铺名称',
    upload: '上传',
    tagListIsEmpty: '标签列表为空，请添加标签后在推送',
    submit: '提交',
    checkAll: '全选'
  },
  shopFeature: {
    ...shopFeature
  },
  pcdecorate: {
    ...pcdecorate
  },
  form: {
    content: '请输入内容',
    select: '请选择',
    nameZh: '中文名称',
    nameEn: '英文名称',
    descZh: '中文描述',
    descEn: '英文描述'
  },
  action: {
    putOnShelf: '上架',
    offShelf: '下架'
  },
  date: {
    start: '开始日期',
    end: '结束日期',
    tip: '至',
    t: '今日',
    y: '昨日',
    n: '近7天',
    m: '近30天',
    a: '全部'
  },
  rbac: {
    role,
    menu,
    menuPermission,
    webConfig,
    menuResource
  },
  seckill: {
    ...seckill
  },
  platform: {
    config,
    shopUser,
    hotSearch,
    shopUserAccount,
    shopAddr,
    notice
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
    rights,
    userManager,
    recharge,
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
  order: {
    order
  },
  station: {
    ...station
  },
  chat: {
    ...chat
  },
  admin: {
    ...admin
  },
  constant: {
    ...constant
  },
  distribution: {
    ...distributionSet,
    ...distributionUser,
    ...distributionProd
  },
  ...live,
  outletConfig: {
    ...outletConfig
  },
  printer: {
    ...printer
  }
}
