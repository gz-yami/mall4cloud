import spu from './product/spu/zh'
import attr from './product/attr/zh'
import category from './product/category/zh'
import notice from './multishop/notice/zh'
import shopUser from './multishop/shop-user/zh'
import hotSearch from './multishop/hot-search/zh'
import imgbox from './biz/imgbox/zh'
import role from './rbac/role/zh'
import menu from './rbac/menu/zh'
import menuPermission from './rbac/menu-permission/zh'
import selector from './components/category-selector/zh'
import shopUserAccount from './multishop/shop-user-account/zh'
import order from './order/order/zh'
import constant from './constant/zh'
import admin from './admin/zh'
import shop from './shop/zh'
import address from './address/zh'
import product from './product/zh'

export default {
  language: '简体中文',
  route: {
    dashboard: '首页'
  },
  tip: {
    select: '请选择',
    input: '请输入'
  },
  navbar: {
    logOut: '退出登录'
  },
  tagsView: {
    refresh: '刷新',
    close: '关闭',
    closeOthers: '关闭其它',
    closeAll: '关闭所有'
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
  login: {
    title: '系统登录',
    logIn: '登录',
    username: '账号',
    password: '密码',
    any: '随便填',
    thirdparty: '第三方登录',
    thirdpartyTips: '本地不能模拟，请结合自己业务进行模拟！！！'
  },
  unit: {
    dollar: '元'
  },
  table: {
    search: '搜索',
    add: '添加',
    export: '导出',
    id: '序号',
    status: '状态',
    actions: '操作',
    edit: '编辑',
    create: '新建',
    clear: '清空',
    publish: '发布',
    delete: '删除',
    cancel: '取消',
    confirm: '确定',
    offline: '下线',
    seq: '排序号',
    actionSuccess: '操作成功',
    tips: '提示',
    noNull: '不能为空',
    sureToDelete: '确定进行删除操作?',
    createTime: '创建时间',
    updateTime: '更新时间'
  },
  action: {
    putOnShelf: '上架',
    offShelf: '下架'
  },
  rbac: {
    role,
    menu,
    menuPermission
  },
  multishop: {
    hotSearch,
    shopUser,
    notice,
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
