### Docker安装Nacos

#### 环境准备

由于Nacos是配置中心，需要连接数据库，所以请先参考文件'**Docker安装Mysql**'安装mysql8版本后，再安装Nacos。

#### 步骤

```shell
#新建logs目录
mkdir -p /mydata/nacos/logs/
mkdir -p /mydata/nacos/init.d/
```

docker拉取指定镜像版本

```shell
docker pull nacos/nacos-server:2.0.1
```

安装mysql后，进入数据库执行sql脚本，文件'**mall4cloud_nacos.sql**'。

```shell
#修改配置文件
vi /mydata/nacos/init.d/custom.properties
```

将以下内容复制到配置文件中，根据实际情况更改数据库的ip地址以及账号密码。

```shell
server.contextPath=/nacos
server.servlet.contextPath=/nacos
server.port=8848
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://localhost:3306/mall4cloud_nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=root
db.password=root
nacos.cmdb.dumpTaskInterval=3600
nacos.cmdb.eventTaskInterval=10
nacos.cmdb.labelTaskInterval=300
nacos.cmdb.loadDataAtStart=false
management.metrics.export.elastic.enabled=false
management.metrics.export.influx.enabled=false
server.tomcat.accesslog.enabled=true
server.tomcat.accesslog.pattern=%h %l %u %t "%r" %s %b %D %{User-Agent}i
nacos.security.ignore.urls=/,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-fe/public/**,/v1/auth/login,/v1/console/health/**,/v1/cs/**,/v1/ns/**,/v1/cmdb/**,/actuator/**,/v1/console/server/**
nacos.naming.distro.taskDispatchThreadCount=1
nacos.naming.distro.taskDispatchPeriod=200
nacos.naming.distro.batchSyncKeyCount=1000
nacos.naming.distro.initDataRatio=0.9
nacos.naming.distro.syncRetryDelay=5000
nacos.naming.data.warmup=true
nacos.naming.expireInstance=true
```

修改完毕后，按ESC键后，键入":wq"，wq：表示保存退出

启动容器，MYSQL_SERVICE_HOST修改为数据库ip地址

```shell
docker  run \
--name nacos -d \
-p 8848:8848 \
-p 9848:9848 \
-p 9849:9849 \
--privileged=true \
--restart=always \
-e JVM_XMS=256m \
-e JVM_XMX=256m \
-e MODE=standalone \
-e PREFER_HOST_MODE=hostname \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=192.168.1.21 \
-e MYSQL_SERVICE_DB_NAME=mall4cloud_nacos \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=root \
-v /mydata/nacos/logs:/home/nacos/logs \
-v /mydata/nacos/init.d/custom.properties:/home/nacos/init.d/custom.properties \
nacos/nacos-server:2.0.1
```

由于是docker部署，所以启动时需要配置9848、9849两个端口[参考官方文档https://nacos.io/zh-cn/docs/2.0.0-compatibility.html]

![image-20210617145246516](../img/开发环境搭建文档/nacos官方文档.png)

即可访问

http://localhost:8848/

用户名：nacos

密码：nacos

![image-20210615092901375](../img/开发环境搭建文档/nacos配置列表.png)

修改public中各个配置的内容，点击"编辑"，根据自己的ip地址填入信息，例如：

![image-20210617092746781](../img/开发环境搭建文档/nacos配置编辑.png)

修改该服务模块连接的数据库ip地址。

修改"application-dev.yml"文件内容：

```shell
# 数据源
spring:
  #避免nacos取网卡出错
  cloud:
    inetutils:
      preferred-networks: 192.168.1
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      minimum-idle: 0
      maximum-pool-size: 20
      idle-timeout: 25000
      auto-commit: true
      connection-test-query: SELECT 1
  redis:
  	#填写配置redis的ip
    host: 192.168.1.21
    # password: null
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
      
#mybatis的相关配置
mybatis:
  #mapper配置文件
  mapper-locations: classpath:mapper/*Mapper.xml
  type-aliases-package: com.mall4j.cloud.**.model
  #开启驼峰命名
  configuration:
    map-underscore-to-camel-case: true

seata:
  config:
    type: nacos
    nacos:
      #填写nacos中seata的命名id
      namespace: 4b70485d-72dd-44df-a76a-7a3f578a3001
      server-addr: ${spring.cloud.nacos.discovery.server-addr}
      password: ${spring.cloud.nacos.discovery.username}
      username: ${spring.cloud.nacos.discovery.password}
  registry:
    type: nacos
    nacos:
      server-addr: ${spring.cloud.nacos.discovery.server-addr}
      password: ${spring.cloud.nacos.discovery.username}
      username: ${spring.cloud.nacos.discovery.password}
      namespace: ${seata.config.nacos.namespace}

logging:
  level:
    root: info
    com:
      mall4cloud:
        shop: debug
# 分页合理化，当查询到页码大于最后一页的时候，返回最后一页的数据，防止vue在最后一页删除时，数据不对的问题
pagehelper:
  reasonable: true

biz:
  oss:
    type: 1
    endpoint: http://192.168.1.21:9000
    bucket: mall4cloud
    access-key-id: AKIAIOSFODN342XAMPLE
    access-key-secret: wJalrXUtnFEMI/K7MD81G/bPxRfiCYEXAMPLEKEY

feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000
        loggerLevel: basic
  inside:
    key: mall4cloud-feign-inside-key
    secret: mall4cloud-feign-inside-secret
    # ip白名单，如果有需要的话，用小写逗号分割
    ips: 

rocketmq:
  #填写配置rocketmq的地址，端口号默认9876
  name-server: 192.168.1.21:9876

```

