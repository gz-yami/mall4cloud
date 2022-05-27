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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4122 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (2, 'application-dev.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  #避免nacos取网卡出错\n  cloud:\n    inetutils:\n      preferred-networks: 192.168.1\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n    hikari:\n      minimum-idle: 0\n      maximum-pool-size: 20\n      idle-timeout: 25000\n      auto-commit: true\n      connection-test-query: SELECT 1\n  redis:\n    host: 192.168.1.49\n    # password: null\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n      \n#mybatis的相关配置\nmybatis:\n  #mapper配置文件\n  mapper-locations: classpath:mapper/*Mapper.xml\n  type-aliases-package: com.mall4j.cloud.**.model\n  #开启驼峰命名\n  configuration:\n    map-underscore-to-camel-case: true\n\nseata:\n  config:\n    type: nacos\n    nacos:\n      namespace: 4b70485d-72dd-44df-a76a-7a3f578a3001\n      server-addr: ${spring.cloud.nacos.discovery.server-addr}\n      password: ${spring.cloud.nacos.discovery.password}\n      username: ${spring.cloud.nacos.discovery.username}\n  registry:\n    type: nacos\n    nacos:\n      server-addr: ${spring.cloud.nacos.discovery.server-addr}\n      username: ${spring.cloud.nacos.discovery.username}\n      password: ${spring.cloud.nacos.discovery.password}\n      namespace: ${seata.config.nacos.namespace}\n\nlogging:\n  level:\n    root: info\n    com:\n      mall4cloud:\n        shop: debug\n# 分页合理化，当查询到页码大于最后一页的时候，返回最后一页的数据，防止vue在最后一页删除时，数据不对的问题\npagehelper:\n  reasonable: true\n\nbiz:\n  oss:\n    # resources-url是带有bucket的\n    resources-url: http://192.168.1.49:9000/mall4cloud\n    type: 1\n    endpoint: http://192.168.1.49:9000\n    bucket: mall4cloud\n    access-key-id: admin\n    access-key-secret: admin123456\n\nfeign:\n  client:\n    config:\n      default:\n        connectTimeout: 5000\n        readTimeout: 5000\n        loggerLevel: basic\n  inside:\n    key: mall4cloud-feign-inside-key\n    secret: mall4cloud-feign-inside-secret\n    # ip白名单，如果有需要的话，用小写逗号分割\n    ips: \n\nmall4cloud:\n  job:\n    accessToken:\n    admin:\n      addresses: http://192.168.1.49:8999\n\nrocketmq:\n  name-server: 192.168.1.49:9876', 'bc01547fe2e5ada0aa489dfd10a35839', '2020-09-07 05:54:23', '2021-07-31 11:20:32', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (8, 'mall4cloud-auth.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.49}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_auth}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\n\n# 用于token aes签名的key，16位\nauth:\n  token:\n    signKey: -mall4cloud-mall ', '56d607c86e383e27174c8b7ce2e690bb', '2020-09-07 06:05:57', '2021-07-31 11:21:33', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (13, 'mall4cloud-multishop.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.49}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_multishop}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', 'e6e62d5a4d40101dc899cc37aecf7306', '2020-09-07 06:38:01', '2021-07-31 11:21:45', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (16, 'mall4cloud-leaf.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.49}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_leaf}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', 'f416d9ca7750b8cf5b78154e008233e8', '2020-09-07 06:44:22', '2021-07-31 11:21:55', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (18, 'mall4cloud-rbac.yml', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.49}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_rbac}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\n', 'd33047562436e609a37c493ad337ca85', '2020-09-07 06:47:49', '2021-07-31 11:22:04', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (31, 'mall4cloud-biz.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.49}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_biz}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\n\nbiz:\n  oss:\n    # resources-url是带有bucket的\n    resources-url: http://192.168.1.49:9000/mall4cloud\n    # 文件上传类型 0.阿里云 1.minio\n    type: 1\n    endpoint: http://192.168.1.49:9000\n    bucket: mall4cloud\n    access-key-id: admin\n    access-key-secret: admin123456', '4619160fe2cdd49f21ccc7327dfe6857', '2020-09-10 07:26:09', '2021-07-31 11:22:21', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (72, 'mall4cloud-product.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.49}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_product}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', '2d7d7afa8b27e6044377a4a3d584885a', '2020-11-11 09:35:20', '2021-07-31 11:22:45', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (73, 'mall4cloud-search.yml', 'DEFAULT_GROUP', 'elastic:\n  # elastic的地址\n  address: http://192.168.1.49:9200', '0e8ea5c03a559a26407a21e0efcc6a97', '2020-11-12 06:57:25', '2021-04-02 15:13:25', NULL, '', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (84, 'mall4cloud-gateway.yml', 'DEFAULT_GROUP', 'spring:\n  cloud:\n    gateway:\n      globalcors:\n        cors-configurations:\n          \'[/**]\':\n            allow-credentials: true\n            allowed-headers: \"*\"\n            # 半个月内都允许\n            max-age: 1296000\n            # 测试环境，全部允许\n            allowedOriginPatterns: \"*\"\n            # allowedOrigins:\n              # - \"http://localhost:9527\"\n              # - \"http://localhost:9527\"\n              # - \"http://localhost:9528\"\n              # - \"http://localhost:9529\"\n              # - \"http://:9527\"\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - OPTIONS\n              - DELETE\n      discovery:\n        locator:\n          # 开启服务注册和发现\n          enabled: true\n          # 不手动写路由的话，swagger整合不了...\n      routes:\n        - id: mall4cloud-rbac\n          uri: lb://mall4cloud-rbac\n          predicates:\n            - Path=/mall4cloud_rbac/**\n          filters:\n            - RewritePath=/mall4cloud_rbac(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-auth\n          uri: lb://mall4cloud-auth\n          predicates:\n            - Path=/mall4cloud_auth/**\n          filters:\n            - RewritePath=/mall4cloud_auth(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-multishop\n          uri: lb://mall4cloud-multishop\n          predicates:\n            - Path=/mall4cloud_multishop/**\n          filters:\n            - RewritePath=/mall4cloud_multishop(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-biz\n          uri: lb://mall4cloud-biz\n          predicates:\n            - Path=/mall4cloud_biz/**\n          filters:\n            - RewritePath=/mall4cloud_biz(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-product\n          uri: lb://mall4cloud-product\n          predicates:\n            - Path=/mall4cloud_product/**\n          filters:\n            - RewritePath=/mall4cloud_product(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-user\n          uri: lb://mall4cloud-user\n          predicates:\n            - Path=/mall4cloud_user/**\n          filters:\n            - RewritePath=/mall4cloud_user(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-order\n          uri: lb://mall4cloud-order\n          predicates:\n            - Path=/mall4cloud_order/**\n          filters:\n            - RewritePath=/mall4cloud_order(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-delivery\n          uri: lb://mall4cloud-delivery\n          predicates:\n            - Path=/mall4cloud_delivery/**\n          filters:\n            - RewritePath=/mall4cloud_delivery(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-discount\n          uri: lb://mall4cloud-discount\n          predicates:\n            - Path=/mall4cloud_discount/**\n          filters:\n            - RewritePath=/mall4cloud_discount(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-platform\n          uri: lb://mall4cloud-platform\n          predicates:\n            - Path=/mall4cloud_platform/**\n          filters:\n            - RewritePath=/mall4cloud_platform(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-search\n          uri: lb://mall4cloud-search\n          predicates:\n            - Path=/mall4cloud_search/**\n          filters:\n            - RewritePath=/mall4cloud_search(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-coupon\n          uri: lb://mall4cloud-coupon\n          predicates:\n            - Path=/mall4cloud_coupon/**\n          filters:\n            - RewritePath=/mall4cloud_coupon(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-payment\n          uri: lb://mall4cloud-payment\n          predicates:\n            - Path=/mall4cloud_payment/**\n          filters:\n            - RewritePath=/mall4cloud_payment(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-group\n          uri: lb://mall4cloud-group\n          predicates:\n            - Path=/mall4cloud_group/**\n          filters:\n            - RewritePath=/mall4cloud_group(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-seckill\n          uri: lb://mall4cloud-seckill\n          predicates:\n            - Path=/mall4cloud_seckill/**\n          filters:\n            - RewritePath=/mall4cloud_seckill(?<segment>/?.*), $\\{segment}\n        - id: mall4cloud-flow\n          uri: lb://mall4cloud-flow\n          predicates:\n            - Path=/mall4cloud_flow/**\n          filters:\n            - RewritePath=/mall4cloud_flow(?<segment>/?.*), $\\{segment}', '256d52ff10bd08ebf1cb45b6c9a2c8e2', '2020-11-19 06:49:26', '2021-05-21 07:27:40', NULL, '', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3650, 'transport.type', 'SEATA_GROUP', 'TCP', 'b136ef5f6a01d816991fe3cf7a6ac763', '2020-11-30 09:43:36', '2021-03-01 07:48:05', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3651, 'transport.server', 'SEATA_GROUP', 'NIO', 'b6d9dfc0fb54277321cebc0fff55df2f', '2020-11-30 09:43:37', '2021-03-01 07:48:05', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3652, 'transport.heartbeat', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2020-11-30 09:43:37', '2021-03-01 07:48:05', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3653, 'transport.enableClientBatchSendRequest', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-11-30 09:43:37', '2021-03-01 07:48:05', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3654, 'transport.threadFactory.bossThreadPrefix', 'SEATA_GROUP', 'NettyBoss', '0f8db59a3b7f2823f38a70c308361836', '2020-11-30 09:43:37', '2021-03-01 07:48:06', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3655, 'transport.threadFactory.workerThreadPrefix', 'SEATA_GROUP', 'NettyServerNIOWorker', 'a78ec7ef5d1631754c4e72ae8a3e9205', '2020-11-30 09:43:37', '2021-03-01 07:48:06', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3656, 'transport.threadFactory.serverExecutorThreadPrefix', 'SEATA_GROUP', 'NettyServerBizHandler', '11a36309f3d9df84fa8b59cf071fa2da', '2020-11-30 09:43:37', '2021-03-01 07:48:06', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3657, 'transport.threadFactory.shareBossWorker', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-11-30 09:43:37', '2021-03-01 07:48:06', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3658, 'transport.threadFactory.clientSelectorThreadPrefix', 'SEATA_GROUP', 'NettyClientSelector', 'cd7ec5a06541e75f5a7913752322c3af', '2020-11-30 09:43:37', '2021-03-01 07:48:06', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3659, 'transport.threadFactory.clientSelectorThreadSize', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2020-11-30 09:43:37', '2021-03-01 07:48:06', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3660, 'transport.threadFactory.clientWorkerThreadPrefix', 'SEATA_GROUP', 'NettyClientWorkerThread', '61cf4e69a56354cf72f46dc86414a57e', '2020-11-30 09:43:37', '2021-03-01 07:48:06', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3661, 'transport.threadFactory.bossThreadSize', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2020-11-30 09:43:38', '2021-03-01 07:48:07', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3662, 'transport.threadFactory.workerThreadSize', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-11-30 09:43:38', '2021-03-01 07:48:07', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3663, 'transport.shutdown.wait', 'SEATA_GROUP', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '2020-11-30 09:43:38', '2021-03-01 07:48:07', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3664, 'service.vgroupMapping.my_test_tx_group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-11-30 09:43:38', '2021-03-01 07:48:07', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3665, 'service.default.grouplist', 'SEATA_GROUP', '192.168.1.49:8091', '9481e82f38d58591d24335b9ad4aecb5', '2020-11-30 09:43:38', '2021-03-01 07:48:07', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3666, 'service.enableDegrade', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-11-30 09:43:38', '2021-03-01 07:48:07', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3667, 'service.disableGlobalTransaction', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-11-30 09:43:38', '2021-03-01 07:48:07', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3668, 'client.rm.asyncCommitBufferLimit', 'SEATA_GROUP', '10000', 'b7a782741f667201b54880c925faec4b', '2020-11-30 09:43:38', '2021-03-01 07:48:07', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3669, 'client.rm.lock.retryInterval', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2020-11-30 09:43:38', '2021-03-01 07:48:08', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3670, 'client.rm.lock.retryTimes', 'SEATA_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2020-11-30 09:43:38', '2021-03-01 07:48:08', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3671, 'client.rm.lock.retryPolicyBranchRollbackOnConflict', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2020-11-30 09:43:38', '2021-03-01 07:48:08', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3672, 'client.rm.reportRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2020-11-30 09:43:39', '2021-03-01 07:48:08', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3673, 'client.rm.tableMetaCheckEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-11-30 09:43:39', '2021-03-01 07:48:08', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3674, 'client.rm.reportSuccessEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-11-30 09:43:39', '2021-03-01 07:48:09', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3675, 'client.rm.sagaBranchRegisterEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-11-30 09:43:39', '2021-03-01 07:48:09', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3676, 'client.tm.commitRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2020-11-30 09:43:39', '2021-03-01 07:48:09', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3677, 'client.tm.rollbackRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2020-11-30 09:43:39', '2021-03-01 07:48:09', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3678, 'client.tm.defaultGlobalTransactionTimeout', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2020-11-30 09:43:39', '2021-03-01 07:48:09', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3679, 'client.tm.degradeCheck', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-11-30 09:43:39', '2021-03-01 07:48:09', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3680, 'client.tm.degradeCheckAllowTimes', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2020-11-30 09:43:39', '2021-03-01 07:48:09', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3681, 'client.tm.degradeCheckPeriod', 'SEATA_GROUP', '2000', '08f90c1a417155361a5c4b8d297e0d78', '2020-11-30 09:43:39', '2021-03-01 07:48:10', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3682, 'store.mode', 'SEATA_GROUP', 'db', 'd77d5e503ad1439f585ac494268b351b', '2020-11-30 09:43:40', '2021-03-01 07:48:10', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3683, 'store.db.datasource', 'SEATA_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2020-11-30 09:43:40', '2021-03-01 07:48:10', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3684, 'store.db.dbType', 'SEATA_GROUP', 'mysql', '81c3b080dad537de7e10e0987a4bf52e', '2020-11-30 09:43:40', '2021-03-01 07:48:10', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3685, 'store.db.driverClassName', 'SEATA_GROUP', 'com.mysql.cj.jdbc.Driver', '33763409bb7f4838bde4fae9540433e4', '2020-11-30 09:43:40', '2021-03-18 07:22:41', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', '', '', '', 'text', '');
INSERT INTO `config_info` VALUES (3686, 'store.db.url', 'SEATA_GROUP', 'jdbc:mysql://192.168.1.49:3306/mall4cloud_seata?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true', '2104a66c7d4bb86db640839a10d2c273', '2020-11-30 09:43:40', '2021-06-16 07:57:12', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', '', '', '', 'text', '');
INSERT INTO `config_info` VALUES (3687, 'store.db.user', 'SEATA_GROUP', 'root', '63a9f0ea7bb98050796b649e85481845', '2020-11-30 09:43:40', '2021-03-01 07:48:10', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3688, 'store.db.password', 'SEATA_GROUP', 'root', '63a9f0ea7bb98050796b649e85481845', '2020-11-30 09:43:40', '2021-03-01 07:48:11', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3689, 'store.db.minConn', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2020-11-30 09:43:40', '2021-03-01 07:48:11', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3690, 'store.db.maxConn', 'SEATA_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2020-11-30 09:43:40', '2021-03-01 07:48:11', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3691, 'store.db.globalTable', 'SEATA_GROUP', 'global_table', '8b28fb6bb4c4f984df2709381f8eba2b', '2020-11-30 09:43:40', '2021-03-01 07:48:11', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3692, 'store.db.branchTable', 'SEATA_GROUP', 'branch_table', '54bcdac38cf62e103fe115bcf46a660c', '2020-11-30 09:43:40', '2021-03-01 07:48:11', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3693, 'store.db.queryLimit', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2020-11-30 09:43:41', '2021-03-01 07:48:11', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3694, 'store.db.lockTable', 'SEATA_GROUP', 'lock_table', '55e0cae3b6dc6696b768db90098b8f2f', '2020-11-30 09:43:41', '2021-03-01 07:48:11', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3695, 'store.db.maxWait', 'SEATA_GROUP', '5000', 'a35fe7f7fe8217b4369a0af4244d1fca', '2020-11-30 09:43:41', '2021-03-01 07:48:11', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3696, 'server.recovery.committingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2020-11-30 09:43:41', '2021-03-01 07:48:12', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3697, 'server.recovery.asynCommittingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2020-11-30 09:43:41', '2021-03-01 07:48:12', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3698, 'server.recovery.rollbackingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2020-11-30 09:43:41', '2021-03-01 07:48:12', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3699, 'server.recovery.timeoutRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2020-11-30 09:43:41', '2021-03-01 07:48:12', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3700, 'server.maxCommitRetryTimeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2020-11-30 09:43:41', '2021-03-01 07:48:12', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3701, 'server.maxRollbackRetryTimeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2020-11-30 09:43:41', '2021-03-01 07:48:12', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3702, 'server.rollbackRetryTimeoutUnlockEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-11-30 09:43:41', '2021-03-01 07:48:12', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3703, 'client.undo.dataValidation', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2020-11-30 09:43:41', '2021-03-01 07:48:13', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3704, 'client.undo.logSerialization', 'SEATA_GROUP', 'jackson', 'b41779690b83f182acc67d6388c7bac9', '2020-11-30 09:43:42', '2021-03-01 08:07:28', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', '', '', '', 'text', '');
INSERT INTO `config_info` VALUES (3705, 'client.undo.onlyCareUpdateColumns', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2020-11-30 09:43:42', '2021-03-01 07:48:13', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3706, 'server.undo.logSaveDays', 'SEATA_GROUP', '7', '8f14e45fceea167a5a36dedd4bea2543', '2020-11-30 09:43:42', '2021-03-01 07:48:13', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3707, 'server.undo.logDeletePeriod', 'SEATA_GROUP', '86400000', 'f4c122804fe9076cb2710f55c3c6e346', '2020-11-30 09:43:42', '2021-03-01 07:48:13', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3708, 'client.undo.logTable', 'SEATA_GROUP', 'undo_log', '2842d229c24afe9e61437135e8306614', '2020-11-30 09:43:42', '2021-03-01 07:48:13', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3709, 'log.exceptionRate', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2020-11-30 09:43:42', '2021-03-01 07:48:14', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3710, 'transport.serialization', 'SEATA_GROUP', 'seata', 'b943081c423b9a5416a706524ee05d40', '2020-11-30 09:43:42', '2021-03-01 07:48:14', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3711, 'transport.compressor', 'SEATA_GROUP', 'none', '334c4a4c42fdb79d7ebc3e73b517e6f8', '2020-11-30 09:43:42', '2021-03-01 07:48:14', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3712, 'metrics.enabled', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-11-30 09:43:42', '2021-03-01 07:48:14', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3713, 'metrics.registryType', 'SEATA_GROUP', 'compact', '7cf74ca49c304df8150205fc915cd465', '2020-11-30 09:43:43', '2021-03-01 07:48:14', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3714, 'metrics.exporterList', 'SEATA_GROUP', 'prometheus', 'e4f00638b8a10e6994e67af2f832d51c', '2020-11-30 09:43:43', '2021-03-01 07:48:14', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3715, 'metrics.exporterPrometheusPort', 'SEATA_GROUP', '9898', '7b9dc501afe4ee11c56a4831e20cee71', '2020-11-30 09:43:43', '2021-03-01 07:48:15', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3716, 'service.vgroupMapping.mall4cloud-auth-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-11-30 09:55:42', '2020-11-30 09:55:42', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3717, 'service.vgroupMapping.mall4cloud-multishop-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-11-30 09:57:03', '2020-11-30 09:57:03', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3718, 'service.vgroupMapping.mall4cloud-rbac-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-11-30 09:57:17', '2020-11-30 09:57:17', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3720, 'service.vgroupMapping.mall4cloud-product-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-01 05:46:53', '2020-12-01 05:46:53', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3721, 'service.vgroupMapping.mall4cloud-biz-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-01 05:47:17', '2020-12-01 05:47:17', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3722, 'service.vgroupMapping.mall4cloud-leaf-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-01 05:47:44', '2020-12-01 05:47:44', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3723, 'service.vgroupMapping.mall4cloud-order-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-01 05:48:00', '2020-12-01 05:48:00', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3724, 'service.vgroupMapping.mall4cloud-user-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-01 05:48:15', '2020-12-01 05:48:15', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3727, 'mall4cloud-order.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.49}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_order}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', 'c33dca13b41bb90b2477f9ea7228ff48', '2020-12-04 05:45:13', '2021-04-02 12:53:44', NULL, '', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3736, 'mall4cloud-user.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.49}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_user}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', 'a2c60ea7887ebb8a0f6f5f5005048b4b', '2020-12-04 05:51:25', '2021-04-02 12:54:49', NULL, '', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3741, 'service.vgroupMapping.mall4cloud-coupon-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-09 01:49:55', '2020-12-09 01:49:55', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3747, 'service.vgroupMapping.mall4cloud-delivery-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-14 03:30:09', '2020-12-14 03:30:09', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3755, 'service.vgroupMapping.mall4cloud-discount-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-16 06:13:14', '2020-12-16 06:13:14', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3759, 'mall4cloud-platform.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.49}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_platform}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', '274a64e67ef4fab65de1e7b70e0a9ff7', '2020-12-21 07:38:16', '2021-04-02 12:55:59', NULL, '', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3761, 'service.vgroupMapping.mall4cloud-platform-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-21 07:41:01', '2020-12-21 07:41:01', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3762, 'service.vgroupMapping.mall4cloud-search-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-22 02:19:51', '2020-12-22 02:19:51', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3812, 'mall4cloud-payment.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.49}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_payment}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\napplication:\n  domainUrl: http://47.112.182.96:8126/mall4cloud_payment', '2f4b89c096ab0cef9359c98b91ade543', '2021-02-03 03:19:16', '2021-04-02 12:56:14', NULL, '', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3814, 'service.vgroupMapping.mall4cloud-payment-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-02-03 03:20:03', '2021-02-03 03:20:03', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4003, 'client.rm.tableMetaCheckerInterval', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2021-03-01 07:48:08', '2021-03-01 07:48:08', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4004, 'client.rm.sqlParserType', 'SEATA_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2021-03-01 07:48:08', '2021-03-01 07:48:08', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4040, 'client.undo.compress.enable', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-03-01 07:48:13', '2021-03-01 07:48:13', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4041, 'client.undo.compress.type', 'SEATA_GROUP', 'zip', 'adcdbd79a8d84175c229b192aadc02f2', '2021-03-01 07:48:13', '2021-03-01 07:48:13', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4042, 'client.undo.compress.threshold', 'SEATA_GROUP', '64k', 'bd44a6458bdbff0b5cac721ba361f035', '2021-03-01 07:48:14', '2021-03-01 07:48:14', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4065, 'service.vgroupMapping.mall4cloud-group-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-03-20 03:01:47', '2021-03-20 03:01:47', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4083, 'service.vgroupMapping.mall4cloud-seckill-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-04-01 06:18:16', '2021-04-01 06:18:16', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4115, 'service.vgroupMapping.mall4cloud-flow-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-05-22 02:36:39', '2021-05-22 02:36:39', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);


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
