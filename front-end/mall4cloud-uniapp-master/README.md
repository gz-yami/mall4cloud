# README

![image-20210705143529597](doc/img/readme/image-20210705143529597.png)

## 前言

本商城是基于Spring Cloud、Nacos、Seata、Mysql、Redis、RocketMQ、canal、ElasticSearch、minio的微服务B2B2C电商商城系统，采用主流的互联网技术架构、全新的UI设计、支持集群部署、服务注册和发现以及拥有完整的订单流程等，代码完全开源，没有任何二次封装，是一个非常适合二次开发的电商平台系统。

本商城致力于为中大型企业打造一个功能完整、易于维护的微服务B2B2C电商商城系统，采用主流微服务技术实现。后台管理系统包含平台管理，店铺管理、商品管理、订单管理、规格管理、权限管理、资源管理等模块



## 授权

除开源版本外，本商城还提供商业版本的商城，欲知详情，请访问官网。

商城官网：https://www.mall4j.com

商城使用 AGPLv3 开源，请遵守 AGPLv3 的相关条款，或者联系作者获取商业授权(https://www.mall4j.com)



## 项目链接

JAVA后台：https://gitee.com/gz-yami/mall4cloud

平台端：https://gitee.com/gz-yami/mall4cloud-platform

商家端：https://gitee.com/gz-yami/mall4cloud-multishop

uni-app：https://gitee.com/gz-yami/mall4cloud-uniapp


## 启动

- 安装node模块 （不要用cnpm，否则会出现不可预知的后果）

```
npm install
```

- 运行

```
npm run dev:h5
```

- 部署

```
npm run build:h5
```


## 技术介绍



本项目是一个uniapp的项目，使用cli进行构建，目录结构如下



```
├── public                     # 公共文件目录
│     └── index.html           # html模版 
├── src                        # 源代码
│   ├── components             # 公共组件
│   ├── js_sdk                 # uniapp第三方插件目录
│   ├── packageActivities      # 活动功能分包
│   ├── packageShop            # 店铺功能分包
│   ├── pages                  # 主包
│   ├── static                 # 静态资源
│   │   │── empty-img          # 提示图片
│   │   │── images             # 各页面的图片资源
│   │   └── tabbar             # 底部tab栏的图标
│   ├── utils                  # 存放通用工具
│   ├── wxs                    # wxs文件目录  
│   ├── app.css                # 全局样式
│   ├── App.vue                # 入口页面
│   ├── main.js                # 初始化入口文件
│   ├── mainfest.json          # uniapp项目配置文件
│   ├── pages.json             # 全局页面配置文件
│   ├── popup.css              # 公共弹窗css样式
│   ├── router.js              # 导航路由
│   └── uni.scss               # uni-app内置的常用样式变量
├── .eslintignore              # eslint忽略配置
├── .eslintrc.js               # eslint规则制定文件
├── babel.config.js            # babel配置
├── package-lock.json          # 锁定安装时包的版本号
├── package.json               # package.json 项目基本信息
├── postcss.config             # postcss配置文件
└── vue.config.js              # vue-cli 配置
```


## 运行相关截图

### 1.小程序截图

![小程序](doc/img/readme/小程序.png)

### 2.uni-app截图

![uniapp](doc/img/readme/uniapp.png)

## 提交反馈
- Mall4j官网 https://www.mall4j.com
- 商务合作微信

![输入图片说明](https://images.gitee.com/uploads/images/2021/0703/131508_13858876_5094767.jpeg "法宝微信2.jpg")


- mall4j开源技术QQ群：722835385

![输入图片说明](https://images.gitee.com/uploads/images/2021/0703/110919_835cf484_5094767.jpeg "mall4j群.jpg")

## 你的点赞鼓励，是我们前进的动力~
## 你的点赞鼓励，是我们前进的动力~
## 你的点赞鼓励，是我们前进的动力~
