/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.25 : Database - mall4cloud_nacos
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mall4cloud_nacos` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mall4cloud_nacos`;

/*Table structure for table `config_info` */

DROP TABLE IF EXISTS `config_info`;

CREATE TABLE `config_info` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
    `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
    `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
    `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin,
    `encrypted_data_key` text COLLATE utf8_bin NOT NULL COMMENT '秘钥',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4122 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`, `encrypted_data_key`) VALUES
('2','application-dev.yml','DEFAULT_GROUP','# 数据源\nspring:\n  #避免nacos取网卡出错\n  cloud:\n    inetutils:\n      preferred-networks: 192.168.1\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n    hikari:\n      minimum-idle: 0\n      maximum-pool-size: 20\n      idle-timeout: 25000\n      auto-commit: true\n      connection-test-query: SELECT 1\n  redis:\n    host: 192.168.1.46\n    # password: null\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n      \n#mybatis的相关配置\nmybatis:\n  #mapper配置文件\n  mapper-locations: classpath:mapper/*Mapper.xml\n  type-aliases-package: com.mall4j.cloud.**.model\n  #开启驼峰命名\n  configuration:\n    map-underscore-to-camel-case: true\n\nseata:\n  enableAutoDataSourceProxy: false\n  config:\n    type: nacos\n    nacos:\n      namespace: 4b70485d-72dd-44df-a76a-7a3f578a3001\n      dataId: \"seataServer.properties\"\n      server-addr: ${spring.cloud.nacos.discovery.server-addr}\n      password: ${spring.cloud.nacos.discovery.password}\n      username: ${spring.cloud.nacos.discovery.username}\n  registry:\n    type: nacos\n    nacos:\n      server-addr: ${spring.cloud.nacos.discovery.server-addr}\n      password: ${spring.cloud.nacos.discovery.password}\n      username: ${spring.cloud.nacos.discovery.username}\n      namespace: ${seata.config.nacos.namespace}\n\nlogging:\n  level:\n    root: info\n    com:\n      mall4cloud:\n        shop: debug\n# 分页合理化，当查询到页码大于最后一页的时候，返回最后一页的数据，防止vue在最后一页删除时，数据不对的问题\npagehelper:\n  reasonable: true\n\nbiz:\n  oss:\n    # resources-url是带有bucket的\n    resources-url: http://192.168.1.46:9000/mall4cloud\n    type: 1\n    endpoint: http://192.168.1.46:9000\n    bucket: mall4cloud\n    access-key-id: admin\n    access-key-secret: admin123456\n\nfeign:\n  client:\n    config:\n      default:\n        connectTimeout: 5000\n        readTimeout: 5000\n        loggerLevel: basic\n  inside:\n    key: mall4cloud-feign-inside-key\n    secret: mall4cloud-feign-inside-secret\n    # ip白名单，如果有需要的话，用小写逗号分割\n    ips: \n\nmall4cloud:\n  job:\n    accessToken:\n    admin:\n      addresses: http://192.168.1.46:8999\n\nrocketmq:\n  name-server: 192.168.1.46:9876','52865a7fbd56786e2245e336ddc84642','2020-09-07 05:54:23','2023-03-15 11:58:11','nacos','192.168.1.16','','','','','','yaml','',''),
('8','mall4cloud-auth.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_auth}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\n\n# 用于token aes签名的key，16位\nauth:\n  token:\n    signKey: -mall4cloud-mall ','56d607c86e383e27174c8b7ce2e690bb','2020-09-07 06:05:57','2021-07-31 11:21:33','nacos','192.168.1.11','','','','','','yaml','',NULL),
('13','mall4cloud-multishop.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_multishop}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}','e6e62d5a4d40101dc899cc37aecf7306','2020-09-07 06:38:01','2021-07-31 11:21:45','nacos','192.168.1.11','','','','','','yaml','',NULL),
('16','mall4cloud-leaf.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_leaf}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}','f416d9ca7750b8cf5b78154e008233e8','2020-09-07 06:44:22','2021-07-31 11:21:55','nacos','192.168.1.11','','','','','','yaml','',NULL),
('18','mall4cloud-rbac.yml','DEFAULT_GROUP','spring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_rbac}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\n','d33047562436e609a37c493ad337ca85','2020-09-07 06:47:49','2021-07-31 11:22:04','nacos','192.168.1.11','','','','','','yaml','',NULL),
('31','mall4cloud-biz.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_biz}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\n\nbiz:\n  oss:\n    # resources-url是带有bucket的\n    resources-url: http://192.168.1.46:9000/mall4cloud\n    # 文件上传类型 0.阿里云 1.minio\n    type: 1\n    endpoint: http://192.168.1.46:9000\n    bucket: mall4cloud\n    access-key-id: admin\n    access-key-secret: admin123456','4619160fe2cdd49f21ccc7327dfe6857','2020-09-10 07:26:09','2021-07-31 11:22:21','nacos','192.168.1.11','','','','','','yaml','',NULL),
('72','mall4cloud-product.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_product}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}','2d7d7afa8b27e6044377a4a3d584885a','2020-11-11 09:35:20','2021-07-31 11:22:45','nacos','192.168.1.11','','','','','','yaml','',NULL),
('73','mall4cloud-search.yml','DEFAULT_GROUP','elastic:\n  # elastic的地址\n  address: http://192.168.1.46:9200','0e8ea5c03a559a26407a21e0efcc6a97','2020-11-12 06:57:25','2021-04-02 15:13:25',NULL,'','','','','','','yaml','',NULL),
('84','mall4cloud-gateway.yml','DEFAULT_GROUP','spring:\n  cloud:\n    gateway:\n      globalcors:\n        cors-configurations:\n          \'[/**]\':\n            allow-credentials: true\n            allowed-headers: \"*\"\n            # 半个月内都允许\n            max-age: 1296000\n            # 测试环境，全部允许\n            allowedOriginPatterns: \"*\"\n            # allowedOrigins:\n              # - \"http://localhost:9527\"\n              # - \"http://localhost:9527\"\n              # - \"http://localhost:9528\"\n              # - \"http://localhost:9529\"\n              # - \"http://:9527\"\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - OPTIONS\n              - DELETE\n      discovery:\n        locator:\n          # 开启服务注册和发现\n          enabled: true\n          # 不手动写路由的话，swagger整合不了...\n      routes:\n        - id: mall4cloud-rbac\n          uri: lb://mall4cloud-rbac\n          predicates:\n            - Path=/mall4cloud_rbac/**\n          filters:\n            - RewritePath=/mall4cloud_rbac(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-auth\n          uri: lb://mall4cloud-auth\n          predicates:\n            - Path=/mall4cloud_auth/**\n          filters:\n            - RewritePath=/mall4cloud_auth(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-multishop\n          uri: lb://mall4cloud-multishop\n          predicates:\n            - Path=/mall4cloud_multishop/**\n          filters:\n            - RewritePath=/mall4cloud_multishop(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-biz\n          uri: lb://mall4cloud-biz\n          predicates:\n            - Path=/mall4cloud_biz/**\n          filters:\n            - RewritePath=/mall4cloud_biz(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-product\n          uri: lb://mall4cloud-product\n          predicates:\n            - Path=/mall4cloud_product/**\n          filters:\n            - RewritePath=/mall4cloud_product(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-user\n          uri: lb://mall4cloud-user\n          predicates:\n            - Path=/mall4cloud_user/**\n          filters:\n            - RewritePath=/mall4cloud_user(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-order\n          uri: lb://mall4cloud-order\n          predicates:\n            - Path=/mall4cloud_order/**\n          filters:\n            - RewritePath=/mall4cloud_order(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-delivery\n          uri: lb://mall4cloud-delivery\n          predicates:\n            - Path=/mall4cloud_delivery/**\n          filters:\n            - RewritePath=/mall4cloud_delivery(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-discount\n          uri: lb://mall4cloud-discount\n          predicates:\n            - Path=/mall4cloud_discount/**\n          filters:\n            - RewritePath=/mall4cloud_discount(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-platform\n          uri: lb://mall4cloud-platform\n          predicates:\n            - Path=/mall4cloud_platform/**\n          filters:\n            - RewritePath=/mall4cloud_platform(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-search\n          uri: lb://mall4cloud-search\n          predicates:\n            - Path=/mall4cloud_search/**\n          filters:\n            - RewritePath=/mall4cloud_search(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-coupon\n          uri: lb://mall4cloud-coupon\n          predicates:\n            - Path=/mall4cloud_coupon/**\n          filters:\n            - RewritePath=/mall4cloud_coupon(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-payment\n          uri: lb://mall4cloud-payment\n          predicates:\n            - Path=/mall4cloud_payment/**\n          filters:\n            - RewritePath=/mall4cloud_payment(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-group\n          uri: lb://mall4cloud-group\n          predicates:\n            - Path=/mall4cloud_group/**\n          filters:\n            - RewritePath=/mall4cloud_group(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-seckill\n          uri: lb://mall4cloud-seckill\n          predicates:\n            - Path=/mall4cloud_seckill/**\n          filters:\n            - RewritePath=/mall4cloud_seckill(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-flow\n          uri: lb://mall4cloud-flow\n          predicates:\n            - Path=/mall4cloud_flow/**\n          filters:\n            - RewritePath=/mall4cloud_flow(?<segment>/?.*), $\\{segment}','256d52ff10bd08ebf1cb45b6c9a2c8e2','2020-11-19 06:49:26','2021-05-21 07:27:40',NULL,'','','','','','','yaml','',NULL),
('3727','mall4cloud-order.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_order}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}','c33dca13b41bb90b2477f9ea7228ff48','2020-12-04 05:45:13','2021-04-02 12:53:44',NULL,'','','','','','','yaml','',NULL),
('3736','mall4cloud-user.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_user}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}','a2c60ea7887ebb8a0f6f5f5005048b4b','2020-12-04 05:51:25','2021-04-02 12:54:49',NULL,'','','','','','','yaml','',NULL),
('3759','mall4cloud-platform.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_platform}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}','274a64e67ef4fab65de1e7b70e0a9ff7','2020-12-21 07:38:16','2021-04-02 12:55:59',NULL,'','','','','','','yaml','',NULL),
('3812','mall4cloud-payment.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_payment}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\napplication:\n  domainUrl: http://47.112.182.96:8126/mall4cloud_payment','2f4b89c096ab0cef9359c98b91ade543','2021-02-03 03:19:16','2021-04-02 12:56:14',NULL,'','','','','','','yaml','',NULL),
('4125','seataServer.properties','SEATA_GROUP','#For details about configuration items, see https://seata.io/zh-cn/docs/user/configurations.html\r\n#Transport configuration, for client and server\r\ntransport.type=TCP\r\ntransport.server=NIO\r\ntransport.heartbeat=true\r\ntransport.enableTmClientBatchSendRequest=false\r\ntransport.enableRmClientBatchSendRequest=true\r\ntransport.enableTcServerBatchSendResponse=false\r\ntransport.rpcRmRequestTimeout=30000\r\ntransport.rpcTmRequestTimeout=30000\r\ntransport.rpcTcRequestTimeout=30000\r\ntransport.threadFactory.bossThreadPrefix=NettyBoss\r\ntransport.threadFactory.workerThreadPrefix=NettyServerNIOWorker\r\ntransport.threadFactory.serverExecutorThreadPrefix=NettyServerBizHandler\r\ntransport.threadFactory.shareBossWorker=false\r\ntransport.threadFactory.clientSelectorThreadPrefix=NettyClientSelector\r\ntransport.threadFactory.clientSelectorThreadSize=1\r\ntransport.threadFactory.clientWorkerThreadPrefix=NettyClientWorkerThread\r\ntransport.threadFactory.bossThreadSize=1\r\ntransport.threadFactory.workerThreadSize=default\r\ntransport.shutdown.wait=3\r\ntransport.serialization=seata\r\ntransport.compressor=none\r\n\r\n#Transaction routing rules configuration, only for the client\r\nservice.vgroupMapping.default_tx_group=default\r\n#If you use a registry, you can ignore it\r\nservice.default.grouplist=10.1.76.38:8091\r\nservice.enableDegrade=false\r\nservice.disableGlobalTransaction=false\r\n\r\n#Transaction rule configuration, only for the client\r\nclient.rm.asyncCommitBufferLimit=10000\r\nclient.rm.lock.retryInterval=10\r\nclient.rm.lock.retryTimes=30\r\nclient.rm.lock.retryPolicyBranchRollbackOnConflict=true\r\nclient.rm.reportRetryCount=5\r\nclient.rm.tableMetaCheckEnable=true\r\nclient.rm.tableMetaCheckerInterval=60000\r\nclient.rm.sqlParserType=druid\r\nclient.rm.reportSuccessEnable=false\r\nclient.rm.sagaBranchRegisterEnable=false\r\nclient.rm.sagaJsonParser=fastjson\r\nclient.rm.tccActionInterceptorOrder=-2147482648\r\nclient.tm.commitRetryCount=5\r\nclient.tm.rollbackRetryCount=5\r\nclient.tm.defaultGlobalTransactionTimeout=60000\r\nclient.tm.degradeCheck=false\r\nclient.tm.degradeCheckAllowTimes=10\r\nclient.tm.degradeCheckPeriod=2000\r\nclient.tm.interceptorOrder=-2147482648\r\nclient.undo.dataValidation=true\r\nclient.undo.logSerialization=jackson\r\nclient.undo.onlyCareUpdateColumns=true\r\nserver.undo.logSaveDays=7\r\nserver.undo.logDeletePeriod=86400000\r\nclient.undo.logTable=undo_log\r\nclient.undo.compress.enable=true\r\nclient.undo.compress.type=zip\r\nclient.undo.compress.threshold=64k\r\n#For TCC transaction mode\r\ntcc.fence.logTableName=tcc_fence_log\r\ntcc.fence.cleanPeriod=1h\r\n\r\n#Log rule configuration, for client and server\r\nlog.exceptionRate=100\r\n\r\n#Transaction storage configuration, only for the server. The file, DB, and redis configuration values are optional.\r\nstore.mode=DB\r\nstore.lock.mode=DB\r\nstore.session.mode=DB\r\n#Used for password encryption\r\nstore.publicKey=\r\n\r\n#If `store.mode,store.lock.mode,store.session.mode` are not equal to `file`, you can remove the configuration block.\r\nstore.file.dir=file_store/data\r\nstore.file.maxBranchSessionSize=16384\r\nstore.file.maxGlobalSessionSize=512\r\nstore.file.fileWriteBufferCacheSize=16384\r\nstore.file.flushDiskMode=async\r\nstore.file.sessionReloadReadSize=100\r\n\r\n#These configurations are required if the `store mode` is `db`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `db`, you can remove the configuration block.\r\nstore.db.datasource=druid\r\nstore.db.dbType=mysql\r\nstore.db.driverClassName=com.mysql.jdbc.Driver\r\nstore.db.url=jdbc:mysql://192.168.1.46:3306/mall4j_seata?useUnicode=true&rewriteBatchedStatements=true\r\nstore.db.user=root\r\nstore.db.password=root\r\nstore.db.minConn=5\r\nstore.db.maxConn=30\r\nstore.db.globalTable=global_table\r\nstore.db.branchTable=branch_table\r\nstore.db.distributedLockTable=distributed_lock\r\nstore.db.queryLimit=100\r\nstore.db.lockTable=lock_table\r\nstore.db.maxWait=5000\r\n\r\n#These configurations are required if the `store mode` is `redis`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `redis`, you can remove the configuration block.\r\nstore.redis.mode=single\r\nstore.redis.single.host=192.168.1.10\r\nstore.redis.single.port=6379\r\nstore.redis.sentinel.masterName=\r\nstore.redis.sentinel.sentinelHosts=\r\nstore.redis.maxConn=10\r\nstore.redis.minConn=1\r\nstore.redis.maxTotal=100\r\nstore.redis.database=0\r\nstore.redis.password=\r\nstore.redis.queryLimit=100\r\n\r\n#Transaction rule configuration, only for the server\r\nserver.recovery.committingRetryPeriod=1000\r\nserver.recovery.asynCommittingRetryPeriod=1000\r\nserver.recovery.rollbackingRetryPeriod=1000\r\nserver.recovery.timeoutRetryPeriod=1000\r\nserver.maxCommitRetryTimeout=-1\r\nserver.maxRollbackRetryTimeout=-1\r\nserver.rollbackRetryTimeoutUnlockEnable=false\r\nserver.distributedLockExpireTime=10000\r\nserver.xaerNotaRetryTimeout=60000\r\nserver.session.branchAsyncQueueSize=5000\r\nserver.session.enableBranchAsyncRemove=false\r\nserver.enableParallelRequestHandle=false\r\n\r\n#Metrics configuration, only for the server\r\nmetrics.enabled=false\r\nmetrics.registryType=compact\r\nmetrics.exporterList=prometheus\r\nmetrics.exporterPrometheusPort=9898','ffa95f9696dfba7a122b043dc2ae65bd','2023-03-15 11:53:03','2023-03-15 11:53:03',NULL,'192.168.1.16','','4b70485d-72dd-44df-a76a-7a3f578a3001',NULL,NULL,NULL,'text',NULL,'');





/*Table structure for table `config_info_aggr` */

DROP TABLE IF EXISTS `config_info_aggr`;

CREATE TABLE `config_info_aggr` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
    `gmt_modified` datetime NOT NULL COMMENT '修改时间',
    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='增加租户字段';

/*Table structure for table `config_info_beta` */

DROP TABLE IF EXISTS `config_info_beta`;

CREATE TABLE `config_info_beta` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
    `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
    `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
    `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
    `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info_beta';

/*Table structure for table `config_info_tag` */

DROP TABLE IF EXISTS `config_info_tag`;

CREATE TABLE `config_info_tag` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
    `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
    `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
    `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info_tag';

/*Table structure for table `config_tags_relation` */

DROP TABLE IF EXISTS `config_tags_relation`;

CREATE TABLE `config_tags_relation` (
    `id` bigint NOT NULL COMMENT 'id',
    `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
    `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
    `nid` bigint NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`nid`) USING BTREE,
    UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`) USING BTREE,
    KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_tag_relation';

/*Table structure for table `group_capacity` */

DROP TABLE IF EXISTS `group_capacity`;

CREATE TABLE `group_capacity` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_group_id` (`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

/*Table structure for table `his_config_info` */

DROP TABLE IF EXISTS `his_config_info`;

CREATE TABLE `his_config_info` (
    `id` bigint unsigned NOT NULL,
    `nid` bigint unsigned NOT NULL AUTO_INCREMENT,
    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `src_user` text CHARACTER SET utf8 COLLATE utf8_bin,
    `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
    `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
    PRIMARY KEY (`nid`) USING BTREE,
    KEY `idx_gmt_create` (`gmt_create`) USING BTREE,
    KEY `idx_gmt_modified` (`gmt_modified`) USING BTREE,
    KEY `idx_did` (`data_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4379 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='多租户改造';

/*Table structure for table `permissions` */

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
    `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
    `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `tenant_capacity` */

DROP TABLE IF EXISTS `tenant_capacity`;

CREATE TABLE `tenant_capacity` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
    `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='租户容量信息表';

/*Table structure for table `tenant_info` */

DROP TABLE IF EXISTS `tenant_info`;

CREATE TABLE `tenant_info` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
    `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
    `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
    `gmt_create` bigint NOT NULL COMMENT '创建时间',
    `gmt_modified` bigint NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`) USING BTREE,
    KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='tenant_info';

insert  into `tenant_info`(`id`,`kp`,`tenant_id`,`tenant_name`,`tenant_desc`,`create_source`,`gmt_create`,`gmt_modified`) values (2,'1','4b70485d-72dd-44df-a76a-7a3f578a3001','seata','seata','nacos',1653615223670,1653615223670);


/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
    `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


/*Data for the table `users` */
insert  into `users`(`username`,`password`,`enabled`) values
    ('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
