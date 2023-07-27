import spu from './product/spu/zh'
import attr from './product/attr/zh'
import brand from './product/brand/zh'
import category from './product/category/zh'
import shopUser from './platform/sys-user/zh'
import hotSearch from './platform/hot-search/zh'
import imgbox from './biz/imgbox/zh'
import role from './rbac/role/zh'
import menu from './rbac/menu/zh'
import menuPermission from './rbac/menu-permission/zh'
import selector from './components/category-selector/zh'
import shopUserAccount from './platform/sys-user-account/zh'
import score from './user/score/zh'
import config from './platform/config/zh'
import notice from './platform/notice/zh'

export default {
  route: {
    dashboard: '首页'
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
  login: {
    title: '系统登录',
    logIn: '登录',
    username: '账号',
    password: '密码',
    any: '随便填',
    thirdparty: '第三方登录',
    thirdpartyTips: '本地不能模拟，请结合自己业务进行模拟！！！',
    validateUsername: '请输入正确的用户名',
    validatePassword: '密码不能少于6位'
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
    save: '保存',
    create: '新建',
    clear: '清空',
    publish: '发布',
    delete: '删除',
    cancel: '取消',
    confirm: '确定',
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
  platform: {
    config,
    shopUser,
    hotSearch,
    shopUserAccount,
    notice
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
