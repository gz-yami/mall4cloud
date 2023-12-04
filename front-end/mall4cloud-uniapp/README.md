- # README

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

  ## 部署教程

  ### 1.安装nodejs

  [NodeJS](https://nodejs.org/) 项目要求最低 18.12.0，推荐 20.9.0

  如果不了解怎么安装nodejs的，可以参考 [菜鸟教程的nodejs相关](https://www.runoob.com/nodejs/nodejs-install-setup.html)

  ### 2.启动

  - 项目要求使用 [pnpm](https://www.pnpm.cn/) 包管理工具
  - 使用编辑器打开项目，在根目录执行以下命令安装依赖

  ```
  pnpm install
  ```

  - 运行

  ```
  pnpm run dev:h5
  ```

  - 部署

  ```
  pnpm run build:h5
  ```

  - 如果不想使用 pnpm，请删除 `package.json` 文件中 `preinstall` 脚本后再进行安装

  ```json
  {
      "scripts" : {
          "preinstall": "npx only-allow pnpm"  // 使用其他包管理工具（npm、yarn、cnpm等）请删除此命令
      }
  }
  ```

  ## 技术介绍

  

  本项目是一个uniapp的项目，使用cli进行构建，目录结构如下

  

  ```
  ├── dist                       # 构建相关
  ├── src                        # 源代码
  │   ├── components             # 全局公用组件
  │   ├── hybrid                 # webview本地页面
  │   ├── js_sdk                 # 外部js
  │   ├── lang                   # 国际化 language
  │   ├── package-activities     # 活动分包
  │   ├── package-refund         # 退款分包
  │   ├── package-shop           # 店铺分包
  │   ├── package-user           # 用户分包
  │   ├── pages                  # 主包
  │   ├── router                 # 路由配置
  │   ├── static                 # 静态资源
  │   ├── uni_modules            # uni第三方组件
  │   ├── utils                  # 全局公用方法
  │   ├── wxs                    # wxs
  │   ├── app.css                # 全局样式
  │   ├── App.vue                # 入口页面
  │   ├── main.js                # 入口文件 加载组件 初始化等
  │   ├── manifest.json          # uniapp 项目配置
  │   ├── pages.json             # 页面配置文件
  │   ├── manifest.json          # uniapp 项目配置
  │   ├── popup.scss             # 全局弹窗样式
  │   └── uni.scss         	   # uni样式变量
  ├── .editorconfig              # 编辑器配置
  ├── .env.xxx                   # 环境变量配置
  ├── .eslintxxx.xx              # eslint 相关配置
  ├── .gitignore                 # git 忽略清单
  ├── .npmrc                 	   # npm 配置
  ├── Dockerfile                 # docker部署配置
  ├── index.html             	   # html 模板
  ├── nginx.conf                 # nginx 配置
  ├── package.json               # package.json
  ├── tsconfig.json			   # ts 编译配置
  └── vite.config.js             # vite 配置文件
  ```


  ## 运行相关截图

  ### 1.小程序截图

  ![小程序](doc/img/readme/小程序.png)

  ### 2.uni-app截图

  ![uniapp](doc/img/readme/uniapp.png)

  ## 提交反馈

  - Mall4j官网 https://www.mall4j.com

  - 如需购买商业版源码，请联系商务微信

    ![输入图片说明](https://19838323.s21i.faiusr.com/4/4/ABUIABAEGAAgksmNlAYojomK2gIwrAI4rAI!160x160.png)


  - mall4cloud开源技术QQ群：561496886

  ![输入图片说明](https://images.gitee.com/uploads/images/2021/1119/134157_dd977d46_5094767.png "微信图片_20211119134107.png")

  ## springboot版本商城请点击

  https://gitee.com/gz-yami/mall4j


  ## 更多信息请查看官网 <https://www.mall4j.com>
