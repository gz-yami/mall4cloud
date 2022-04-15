export default {
  menuId: '菜单id',
  parentId: '父菜单ID，一级菜单为0',
  bizType: '业务类型 0平台菜单 1 店铺菜单',
  permission: '权限，需要有哪个权限才能访问该菜单',
  path: '路径 就像uri',
  component: '组件如：1.layout/Layout 为布局，不会跳页面 2.views/components-demo/tinymce 跳转到该页面',
  redirect: '当设置 noRedirect 的时候该路由在面包屑导航中不可被点击',
  alwaysShow: '一直显示根路由',
  hidden: '当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1',
  name: '设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题',
  title: '设置该路由在侧边栏和面包屑中展示的名字',
  icon: '设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon',
  noCache: '如果设置为true，则不会被 <keep-alive> 缓存(默认 false)',
  breadcrumb: '如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)',
  affix: '若果设置为true，它则会固定在tags-view中(默认 false)',
  activeMenu: '当路由设置了该属性，则会高亮相对应的侧边栏。',
  seq: '排序，越小越靠前'
}
