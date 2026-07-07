/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.33 : Database - mall4cloud_nacos
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mall4cloud_nacos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mall4cloud_nacos`;


/******************************************/
/*   表名称 = config_info                  */
/******************************************/
CREATE TABLE `config_info` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `data_id` varchar(255) NOT NULL COMMENT 'data_id',
                               `group_id` varchar(128) DEFAULT NULL COMMENT 'group_id',
                               `content` longtext NOT NULL COMMENT 'content',
                               `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
                               `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                               `src_user` text COMMENT 'source user',
                               `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
                               `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
                               `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
                               `c_desc` varchar(256) DEFAULT NULL COMMENT 'configuration description',
                               `c_use` varchar(64) DEFAULT NULL COMMENT 'configuration usage',
                               `effect` varchar(64) DEFAULT NULL COMMENT '配置生效的描述',
                               `type` varchar(64) DEFAULT NULL COMMENT '配置的类型',
                               `c_schema` text COMMENT '配置的模式',
                               `encrypted_data_key` varchar(1024) NOT NULL DEFAULT '' COMMENT '密钥',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info';

/******************************************/
/*   表名称 = config_info  since 2.5.0                */
/******************************************/
CREATE TABLE `config_info_gray` (
                                    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                                    `data_id` varchar(255) NOT NULL COMMENT 'data_id',
                                    `group_id` varchar(128) NOT NULL COMMENT 'group_id',
                                    `content` longtext NOT NULL COMMENT 'content',
                                    `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
                                    `src_user` text COMMENT 'src_user',
                                    `src_ip` varchar(100) DEFAULT NULL COMMENT 'src_ip',
                                    `gmt_create` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'gmt_create',
                                    `gmt_modified` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'gmt_modified',
                                    `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
                                    `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
                                    `gray_name` varchar(128) NOT NULL COMMENT 'gray_name',
                                    `gray_rule` text NOT NULL COMMENT 'gray_rule',
                                    `encrypted_data_key` varchar(256) NOT NULL DEFAULT '' COMMENT 'encrypted_data_key',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `uk_configinfogray_datagrouptenantgray` (`data_id`,`group_id`,`tenant_id`,`gray_name`),
                                    KEY `idx_dataid_gmt_modified` (`data_id`,`gmt_modified`),
                                    KEY `idx_gmt_modified` (`gmt_modified`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='config_info_gray';

/******************************************/
/*   表名称 = config_info_beta             */
/******************************************/
CREATE TABLE `config_info_beta` (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                    `data_id` varchar(255) NOT NULL COMMENT 'data_id',
                                    `group_id` varchar(128) NOT NULL COMMENT 'group_id',
                                    `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
                                    `content` longtext NOT NULL COMMENT 'content',
                                    `beta_ips` varchar(1024) DEFAULT NULL COMMENT 'betaIps',
                                    `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
                                    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                    `src_user` text COMMENT 'source user',
                                    `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
                                    `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
                                    `encrypted_data_key` varchar(1024) NOT NULL DEFAULT '' COMMENT '密钥',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_beta';

/******************************************/
/*   表名称 = config_info_tag              */
/******************************************/
CREATE TABLE `config_info_tag` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `data_id` varchar(255) NOT NULL COMMENT 'data_id',
                                   `group_id` varchar(128) NOT NULL COMMENT 'group_id',
                                   `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
                                   `tag_id` varchar(128) NOT NULL COMMENT 'tag_id',
                                   `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
                                   `content` longtext NOT NULL COMMENT 'content',
                                   `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
                                   `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                   `src_user` text COMMENT 'source user',
                                   `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_tag';

/******************************************/
/*   表名称 = config_tags_relation         */
/******************************************/
CREATE TABLE `config_tags_relation` (
                                        `id` bigint(20) NOT NULL COMMENT 'id',
                                        `tag_name` varchar(128) NOT NULL COMMENT 'tag_name',
                                        `tag_type` varchar(64) DEFAULT NULL COMMENT 'tag_type',
                                        `data_id` varchar(255) NOT NULL COMMENT 'data_id',
                                        `group_id` varchar(128) NOT NULL COMMENT 'group_id',
                                        `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
                                        `nid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增长标识',
                                        PRIMARY KEY (`nid`),
                                        UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
                                        KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_tag_relation';

/******************************************/
/*   表名称 = group_capacity               */
/******************************************/
CREATE TABLE `group_capacity` (
                                  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                  `group_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
                                  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
                                  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
                                  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
                                  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
                                  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
                                  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
                                  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

/******************************************/
/*   表名称 = his_config_info              */
/******************************************/
CREATE TABLE `his_config_info` (
                                   `id` bigint(20) unsigned NOT NULL COMMENT 'id',
                                   `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增标识',
                                   `data_id` varchar(255) NOT NULL COMMENT 'data_id',
                                   `group_id` varchar(128) NOT NULL COMMENT 'group_id',
                                   `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
                                   `content` longtext NOT NULL COMMENT 'content',
                                   `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
                                   `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                   `src_user` text COMMENT 'source user',
                                   `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
                                   `op_type` char(10) DEFAULT NULL COMMENT 'operation type',
                                   `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
                                   `encrypted_data_key` varchar(1024) NOT NULL DEFAULT '' COMMENT '密钥',
                                   `publish_type` varchar(50)  DEFAULT 'formal' COMMENT 'publish type gray or formal',
                                   `gray_name` varchar(50)  DEFAULT NULL COMMENT 'gray name',
                                   `ext_info`  longtext DEFAULT NULL COMMENT 'ext info',
                                   PRIMARY KEY (`nid`),
                                   KEY `idx_gmt_create` (`gmt_create`),
                                   KEY `idx_gmt_modified` (`gmt_modified`),
                                   KEY `idx_did` (`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多租户改造';


/******************************************/
/*   表名称 = tenant_capacity              */
/******************************************/
CREATE TABLE `tenant_capacity` (
                                   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                   `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'Tenant ID',
                                   `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
                                   `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
                                   `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
                                   `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
                                   `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
                                   `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
                                   `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户容量信息表';


CREATE TABLE `tenant_info` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `kp` varchar(128) NOT NULL COMMENT 'kp',
                               `tenant_id` varchar(128) default '' COMMENT 'tenant_id',
                               `tenant_name` varchar(128) default '' COMMENT 'tenant_name',
                               `tenant_desc` varchar(256) DEFAULT NULL COMMENT 'tenant_desc',
                               `create_source` varchar(32) DEFAULT NULL COMMENT 'create_source',
                               `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
                               `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
                               KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tenant_info';

CREATE TABLE `users` (
                         `username` varchar(50) NOT NULL PRIMARY KEY COMMENT 'username',
                         `password` varchar(500) NOT NULL COMMENT 'password',
                         `enabled` boolean NOT NULL COMMENT 'enabled'
);

CREATE TABLE `roles` (
                         `username` varchar(50) NOT NULL COMMENT 'username',
                         `role` varchar(50) NOT NULL COMMENT 'role',
                         UNIQUE INDEX `idx_user_role` (`username` ASC, `role` ASC) USING BTREE
);

CREATE TABLE `permissions` (
                               `role` varchar(50) NOT NULL COMMENT 'role',
                               `resource` varchar(128) NOT NULL COMMENT 'resource',
                               `action` varchar(8) NOT NULL COMMENT 'action',
                               UNIQUE INDEX `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
);


/*Data for the table `config_info` */

insert  into `config_info`(`id`,`data_id`,`group_id`,`content`,`md5`,`gmt_create`,`gmt_modified`,`src_user`,`src_ip`,`app_name`,`tenant_id`,`c_desc`,`c_use`,`effect`,`type`,`c_schema`,`encrypted_data_key`) values
(2,'application.yml','DEFAULT_GROUP','# 数据源\nspring:\n  #避免nacos取网卡出错\n  cloud:\n    inetutils:\n      preferred-networks: 192.168.1\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n    hikari:\n      minimum-idle: 0\n      maximum-pool-size: 20\n      idle-timeout: 25000\n      auto-commit: true\n      connection-test-query: SELECT 1\n  data:\n    redis:\n      host: 192.168.1.46\n      password: 80jpnH4.r5g\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n\nspringdoc:\n  # 默认是false，需要设置为true\n  default-flat-param-object: true\n      \n#mybatis的相关配置\nmybatis:\n  #mapper配置文件\n  mapper-locations: classpath:mapper/*Mapper.xml\n  type-aliases-package: com.mall4j.cloud.**.model\n  #开启驼峰命名\n  configuration:\n    map-underscore-to-camel-case: true\n\nseata:\n  service:\n    grouplist:\n      default: 192.168.1.46:8091\n    vgroupMapping:\n      default_tx_group: default\n\n\nbiz:\n  oss:\n    # resources-url是带有bucket的\n    resources-url: http://192.168.1.46:9000/mall4cloud\n    # 文件上传类型 0.阿里云 1.minio\n    type: 1\n    endpoint: http://192.168.1.46:9000\n    bucket: mall4cloud\n    access-key-id: admin\n    access-key-secret: 80jpnH4.r5g\n\n\nlogging:\n  level:\n    root: info\n    com:\n      mall4cloud:\n        shop: debug\n# 分页合理化，当查询到页码大于最后一页的时候，返回最后一页的数据，防止vue在最后一页删除时，数据不对的问题\npagehelper:\n  reasonable: true\n\nfeign:\n  client:\n    config:\n      default:\n        connectTimeout: 5000\n        readTimeout: 5000\n        loggerLevel: basic\n  inside:\n    key: mall4cloud-feign-inside-key\n    secret: mall4cloud-feign-inside-secret\n    # ip白名单，如果有需要的话，用小写逗号分割\n    ips: \n\nmall4cloud:\n  job:\n    accessToken:\n    admin:\n      addresses: http://192.168.1.46:8999\n\nrocketmq:\n  name-server: 192.168.1.46:9876','a40a1363c930a389ec1dbb74ecd37259','2020-09-07 05:54:23','2024-03-28 10:41:25','nacos','192.168.1.62','','','','','','yaml','',''),
(8,'mall4cloud-auth.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_auth}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:80jpnH4.r5g}','d99e29908f968c8addb8f647d98b30b6','2020-09-07 06:05:57','2023-09-07 17:10:22','nacos','192.168.1.4','','','','','','yaml','',''),
(13,'mall4cloud-multishop.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_multishop}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:80jpnH4.r5g}','e6e62d5a4d40101dc899cc37aecf7306','2020-09-07 06:38:01','2021-07-31 11:21:45','nacos','192.168.1.11','','','','','','yaml','',''),
(16,'mall4cloud-leaf.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_leaf}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:80jpnH4.r5g}','f416d9ca7750b8cf5b78154e008233e8','2020-09-07 06:44:22','2021-07-31 11:21:55','nacos','192.168.1.11','','','','','','yaml','',''),
(18,'mall4cloud-rbac.yml','DEFAULT_GROUP','spring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_rbac}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:80jpnH4.r5g}\n','d33047562436e609a37c493ad337ca85','2020-09-07 06:47:49','2021-07-31 11:22:04','nacos','192.168.1.11','','','','','','yaml','',''),
(31,'mall4cloud-biz.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_biz}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:80jpnH4.r5g}','31606d955a15474a826dd7decd3a231e','2020-09-10 07:26:09','2023-09-07 16:11:51','nacos','192.168.1.4','','','','','','yaml','',''),
(72,'mall4cloud-product.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_product}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:80jpnH4.r5g}','2d7d7afa8b27e6044377a4a3d584885a','2020-11-11 09:35:20','2021-07-31 11:22:45','nacos','192.168.1.11','','','','','','yaml','',''),
(73,'mall4cloud-search.yml','DEFAULT_GROUP','elastic:\n  # elastic的地址\n  address: 192.168.1.46\n  username: elastic\n  password: 80jpnH4.r5g\n  port: 9200','0e8ea5c03a559a26407a21e0efcc6a97','2020-11-12 06:57:25','2021-04-02 15:13:25',NULL,'','','','','','','yaml','',''),
(84,'mall4cloud-gateway.yml','DEFAULT_GROUP','spring:\n  http:\n    codecs:\n      max-in-memory-size: 2MB\n\n  cloud:\n    gateway:\n      server:\n        webflux:\n          globalcors:\n            cors-configurations:\n              \'[/**]\':\n                allow-credentials: true\n                allowed-headers: \"*\"\n                # 半个月内都允许\n                max-age: 1296000\n                # 测试环境，全部允许\n                allowedOriginPatterns: \"*\"\n                # allowedOrigins:\n                #   - \"http://localhost:9527\"\n                #   - \"http://localhost:9528\"\n                #   - \"http://localhost:9529\"\n                #   - \"http://:9527\"\n                allowed-methods:\n                  - GET\n                  - POST\n                  - PUT\n                  - OPTIONS\n                  - DELETE\n\n          discovery:\n            locator:\n              # 开启服务注册和发现\n              enabled: true\n              lower-case-service-id: true\n              # 不手动写路由的话，swagger整合不了...\n\n          routes:\n            # 认证中心\n            - id: mall4cloud-auth\n              uri: lb://mall4cloud-auth\n              predicates:\n                - Path=/mall4cloud_auth/**\n              filters:\n                - RewritePath=/mall4cloud_auth(?<segment>/?.*), ${segment}\n\n            # 平台\n            - id: mall4cloud-platform\n              uri: lb://mall4cloud-platform\n              predicates:\n                - Path=/mall4cloud_platform/**\n              filters:\n                - RewritePath=/mall4cloud_platform(?<segment>/?.*), ${segment}\n\n            # 商家\n            - id: mall4cloud-multishop\n              uri: lb://mall4cloud-multishop\n              predicates:\n                - Path=/mall4cloud_multishop/**\n              filters:\n                - RewritePath=/mall4cloud_multishop(?<segment>/?.*), ${segment}\n\n            # 业务服务\n            - id: mall4cloud-biz\n              uri: lb://mall4cloud-biz\n              predicates:\n                - Path=/mall4cloud_biz/**\n              filters:\n                - RewritePath=/mall4cloud_biz(?<segment>/?.*), ${segment}\n\n            # leaf服务\n            - id: mall4cloud-leaf\n              uri: lb://mall4cloud-leaf\n              predicates:\n                - Path=/mall4cloud_leaf/**\n              filters:\n                - RewritePath=/mall4cloud_leaf(?<segment>/?.*), ${segment}\n\n            # 商品服务\n            - id: mall4cloud-product\n              uri: lb://mall4cloud-product\n              predicates:\n                - Path=/mall4cloud_product/**\n              filters:\n                - RewritePath=/mall4cloud_product(?<segment>/?.*), ${segment}\n\n            # 权限服务\n            - id: mall4cloud-rbac\n              uri: lb://mall4cloud-rbac\n              predicates:\n                - Path=/mall4cloud_rbac/**\n              filters:\n                - RewritePath=/mall4cloud_rbac(?<segment>/?.*), ${segment}\n\n            # 用户服务\n            - id: mall4cloud-user\n              uri: lb://mall4cloud-user\n              predicates:\n                - Path=/mall4cloud_user/**\n              filters:\n                - RewritePath=/mall4cloud_user(?<segment>/?.*), ${segment}\n\n            # 订单服务\n            - id: mall4cloud-order\n              uri: lb://mall4cloud-order\n              predicates:\n                - Path=/mall4cloud_order/**\n              filters:\n                - RewritePath=/mall4cloud_order(?<segment>/?.*), ${segment}\n\n            # 搜索服务\n            - id: mall4cloud-search\n              uri: lb://mall4cloud-search\n              predicates:\n                - Path=/mall4cloud_search/**\n              filters:\n                - RewritePath=/mall4cloud_search(?<segment>/?.*), ${segment}\n\n            # 支付服务\n            - id: mall4cloud-payment\n              uri: lb://mall4cloud-payment\n              predicates:\n                - Path=/mall4cloud_payment/**\n              filters:\n                - RewritePath=/mall4cloud_payment(?<segment>/?.*), ${segment}\n\nknife4j:\n  gateway:\n    enabled: true\n    strategy: discover\n    discover:\n      version: openapi3\n      enabled: true\n','3a712e9a1867a17fe849079b48c9eb2d','2020-11-19 06:49:26','2026-03-23 18:02:44','nacos_namespace_migrate','192.168.1.46','','','',NULL,NULL,'yaml',NULL,''),
(3727,'mall4cloud-order.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_order}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:80jpnH4.r5g}','c33dca13b41bb90b2477f9ea7228ff48','2020-12-04 05:45:13','2021-04-02 12:53:44',NULL,'','','','','','','yaml','',''),
(3736,'mall4cloud-user.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_user}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:80jpnH4.r5g}','a2c60ea7887ebb8a0f6f5f5005048b4b','2020-12-04 05:51:25','2021-04-02 12:54:49',NULL,'','','','','','','yaml','',''),                                                                                                     (3759,'mall4cloud-platform.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_platform}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:80jpnH4.r5g}','274a64e67ef4fab65de1e7b70e0a9ff7','2020-12-21 07:38:16','2021-04-02 12:55:59',NULL,'','','','','','','yaml','',''),
(3812,'mall4cloud-payment.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_payment}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:80jpnH4.r5g}\napplication:\n  domainUrl: http://47.112.182.96:8126/mall4cloud_payment','2f4b89c096ab0cef9359c98b91ade543','2021-02-03 03:19:16','2021-04-02 12:56:14',NULL,'','','','','','','yaml','','');

# nacos登录密码：80jpnH4.r5g
insert  into `users`(`username`,`password`,`enabled`) values ('nacos','$2a$10$rqH.KLtcitmBBccLsf6Hdem9tfRcLLULI2prIh9Rh/Ndg4mmAIXTS',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
