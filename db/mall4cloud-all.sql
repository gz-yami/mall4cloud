create database IF NOT EXISTS `mall4cloud_auth` default character set utf8 collate utf8_general_ci;

USE mall4cloud_auth;

-- ----------------------------
-- Table structure for auth_account
-- ----------------------------
DROP TABLE IF EXISTS `auth_account`;
CREATE TABLE `auth_account`  (
  `uid` bigint UNSIGNED NOT NULL COMMENT '全平台用户唯一id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `create_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '创建ip',
  `status` tinyint NOT NULL COMMENT '状态 1:启用 0:禁用 -1:删除',
  `sys_type` tinyint NOT NULL COMMENT '用户类型见SysTypeEnum 0.普通用户系统 1.商家端 2平台端',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '所属租户',
  `is_admin` tinyint NULL DEFAULT NULL COMMENT '是否是管理员',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `uk_usertype_userid`(`sys_type`, `user_id`) USING BTREE,
  INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_is_0900_ai_ci COMMENT = '统一账户信息';

-- ----------------------------
-- Records of auth_account
-- ----------------------------
INSERT INTO `auth_account` VALUES
(1, '2021-07-01 11:07:38', '2021-07-03 13:11:52', 'admin', '$2a$10$EiwfzqsAVUtuJ0Ry5YPMPOeyc/4shzSUcqMBRInKTijzir48LLkM.', '127.0.0.1', 1, 2, 1, 0, 1),
(112115,'2022-04-14 16:36:13','2022-04-14 16:36:13','admin','$2a$10$fr9bj14bAJW54agVbZizceMODrC4W8sbVotfH5324q0t50HNdpRqa','59.41.161.208',1,1,110400,324,1);
-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 385 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;


create database IF NOT EXISTS `mall4cloud_biz` default character set utf8 collate utf8_general_ci;

USE mall4cloud_biz;

-- ----------------------------
-- Table structure for attach_file
-- ----------------------------
DROP TABLE IF EXISTS `attach_file`;
CREATE TABLE `attach_file`  (
  `file_id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `file_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `file_size` int NULL DEFAULT NULL COMMENT '文件大小',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺id',
  `type` tinyint NULL DEFAULT NULL COMMENT '文件 1:图片 2:视频 3:文件',
  `attach_file_group_id` bigint NULL DEFAULT 0 COMMENT '文件分组id',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1431 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '上传文件记录表';

-- ----------------------------
-- Table structure for attach_file_group
-- ----------------------------
DROP TABLE IF EXISTS `attach_file_group`;
CREATE TABLE `attach_file_group`  (
  `attach_file_group_id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `name` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组名称',
  `type` tinyint NOT NULL COMMENT '1:图片 2:视频 3:文件',
  PRIMARY KEY (`attach_file_group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 162 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;


create database IF NOT EXISTS `mall4cloud_leaf` default character set utf8 collate utf8_general_ci;

USE mall4cloud_leaf;

-- ----------------------------
-- Table structure for leaf_alloc
-- ----------------------------
DROP TABLE IF EXISTS `leaf_alloc`;
CREATE TABLE `leaf_alloc`  (
  `biz_tag` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '区分业务',
  `max_id` bigint UNSIGNED NOT NULL DEFAULT 1 COMMENT '该biz_tag目前所被分配的ID号段的最大值',
  `step` int NOT NULL COMMENT '每次分配的号段长度',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `random_step` int UNSIGNED NOT NULL DEFAULT 1 COMMENT '每次getid时随机增加的长度，这样就不会有连续的id了',
  PRIMARY KEY (`biz_tag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of leaf_alloc
-- ----------------------------
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-auth-account', 112000, 100, '2021-07-01 14:16:40', 'mall4cloud-multishop数据库中auth_account这张表的uid', 10);
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-multishop-user', 110400, 100, '2021-07-01 11:10:32', 'mall4cloud-multishop数据库中shop_user这张表的id', 10);
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-order', 1568693084, 100, '2021-07-05 15:25:19', '订单id', 10);
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-pay', 564994845, 100, '2021-07-05 15:14:40', '支付单号', 10);
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-platform-user', 101600, 100, '2021-07-03 13:13:54', 'mall4cloud-platform数据库中sys_user这张表的id', 10);
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-user', 106600, 100, '2021-07-01 11:22:26', 'mall4cloud-user数据库中user这张表的id', 10);


create database IF NOT EXISTS `mall4cloud_multishop` default character set utf8 collate utf8_general_ci;

USE mall4cloud_multishop;

-- ----------------------------
-- Table structure for hot_search
-- ----------------------------
DROP TABLE IF EXISTS `hot_search`;
CREATE TABLE `hot_search`  (
  `hot_search_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺ID 0为全平台热搜',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `seq` int NULL DEFAULT NULL COMMENT '顺序',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0下线 1上线',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '热搜标题',
  PRIMARY KEY (`hot_search_id`) USING BTREE,
  INDEX `idx_shop_id`(`shop_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '热搜';

-- ----------------------------
-- Table structure for index_img
-- ----------------------------
DROP TABLE IF EXISTS `index_img`;
CREATE TABLE `index_img`  (
  `img_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NOT NULL COMMENT '店铺ID',
  `img_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态 1:enable, 0:disable',
  `seq` int NOT NULL DEFAULT 0 COMMENT '顺序',
  `spu_id` bigint NULL DEFAULT NULL COMMENT '关联商品id',
  `img_type` tinyint NOT NULL COMMENT '图片类型 0:小程序',
  PRIMARY KEY (`img_id`) USING BTREE,
  INDEX `idx_shop_id`(`shop_id`) USING BTREE,
  INDEX `idx_spu_id`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '轮播图';

-- ----------------------------
-- Table structure for shop_detail
-- ----------------------------
DROP TABLE IF EXISTS `shop_detail`;
CREATE TABLE `shop_detail`  (
  `shop_id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '店铺名称',
  `intro` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺简介',
  `shop_logo` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺logo(可修改)',
  `mobile_background_pic` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺移动端背景图',
  `shop_status` tinyint NOT NULL COMMENT '店铺状态(-1:已删除 0: 停业中 1:营业中)',
  `business_license` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '营业执照',
  `identity_card_front` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证正面',
  `identity_card_later` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证反面',
  `type` tinyint NULL DEFAULT NULL COMMENT '店铺类型1自营店 2普通店',
  PRIMARY KEY (`shop_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 322 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '店铺详情';
insert into `shop_detail` (`shop_id`, `create_time`, `update_time`, `shop_name`, `intro`, `shop_logo`, `mobile_background_pic`, `shop_status`, `business_license`, `identity_card_front`, `identity_card_later`, `type`) values
('324','2022-04-14 16:36:13','2022-04-14 16:36:13','商家','商家店铺','/2022/04/14/2bdd14bd15094b15907dfe6c2b86c536','/2022/04/14/791fb3e04fca4fc79adeb79378436068','1','/2022/04/14/136d531dfa9b4cbdb17f555a73b0f7b4','/2022/04/14/50f9525a2b86434e98d97f4df017fcb4','/2022/04/14/dcc4c3c9f44b420a995c292d6df93f2c','2');
-- ----------------------------
-- Table structure for shop_user
-- ----------------------------
DROP TABLE IF EXISTS `shop_user`;
CREATE TABLE `shop_user`  (
  `shop_user_id` bigint UNSIGNED NOT NULL COMMENT '商家用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '关联店铺id',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工编号',
  `phone_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `has_account` tinyint NULL DEFAULT NULL COMMENT '是否已经设置账号 0:未设置 1:已设置',
  PRIMARY KEY (`shop_user_id`) USING BTREE,
  INDEX `idx_shopid`(`shop_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商家用户';
insert into `shop_user` (`shop_user_id`, `create_time`, `update_time`, `shop_id`, `nick_name`, `code`, `phone_num`, `has_account`) values
('110400','2022-04-14 16:36:13','2022-04-14 16:36:13','324','商家',NULL,NULL,'1');
-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 387 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;


create database IF NOT EXISTS `mall4cloud_nacos` default character set utf8 collate utf8_general_ci;

USE mall4cloud_nacos;


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



create database IF NOT EXISTS `mall4cloud_order` default character set utf8 collate utf8_general_ci;

USE mall4cloud_order;


-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_id` bigint UNSIGNED NOT NULL COMMENT '订单ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺id',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `delivery_type` tinyint NULL DEFAULT NULL COMMENT '配送类型：无需快递',
  `shop_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `total` bigint NOT NULL COMMENT '总值',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态 1:待付款 2:待发货 3:待收货(已发货) 5:成功 6:失败',
  `all_count` int NULL DEFAULT NULL COMMENT '订单商品总数',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '付款时间',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `finally_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `settled_time` datetime NULL DEFAULT NULL COMMENT '结算时间',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '取消时间',
  `is_payed` tinyint(1) NULL DEFAULT NULL COMMENT '是否已支付，1.已支付0.未支付',
  `close_type` tinyint NULL DEFAULT NULL COMMENT '订单关闭原因 1-超时未支付 4-买家取消 15-已通过货到付款交易',
  `delete_status` tinyint NULL DEFAULT 0 COMMENT '用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除',
  `version` int NULL DEFAULT NULL COMMENT '订单版本号，每处理一次订单，版本号+1',
  `order_addr_id` bigint NULL DEFAULT NULL COMMENT '用户订单地址id',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `idx_shop_id`(`shop_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_finally_time`(`finally_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单信息';

-- ----------------------------
-- Table structure for order_addr
-- ----------------------------
DROP TABLE IF EXISTS `order_addr`;
CREATE TABLE `order_addr`  (
  `order_addr_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `consignee` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `province_id` bigint NULL DEFAULT NULL COMMENT '省ID',
  `province` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city_id` bigint NULL DEFAULT NULL COMMENT '城市ID',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
  `area_id` bigint NULL DEFAULT NULL COMMENT '区域ID',
  `area` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `addr` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `post_code` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `lng` decimal(12, 6) NULL DEFAULT NULL COMMENT '经度',
  `lat` decimal(12, 6) NULL DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`order_addr_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5192 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户订单配送地址';

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `order_item_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单项ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `category_id` bigint NULL DEFAULT NULL COMMENT '分类id',
  `spu_id` bigint UNSIGNED NOT NULL COMMENT '产品ID',
  `sku_id` bigint UNSIGNED NOT NULL COMMENT '产品SkuID',
  `user_id` bigint NOT NULL COMMENT '用户Id',
  `count` int NULL DEFAULT 0 COMMENT '购物车产品个数',
  `spu_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '产品名称',
  `sku_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku名称',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '产品主图片路径',
  `delivery_type` tinyint NULL DEFAULT NULL COMMENT '单个orderItem的配送类型3：无需快递',
  `shop_cart_time` datetime NULL DEFAULT NULL COMMENT '加入购物车时间',
  `price` bigint NOT NULL COMMENT '产品价格',
  `spu_total_amount` bigint NOT NULL COMMENT '商品总金额',
  PRIMARY KEY (`order_item_id`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE,
  INDEX `idx_shop_id`(`shop_id`) USING BTREE,
  INDEX `idx_spu_id`(`spu_id`) USING BTREE,
  INDEX `idx_sku_id`(`sku_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4805 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单项';

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;


create database IF NOT EXISTS `mall4cloud_payment` default character set utf8 collate utf8_general_ci;

USE mall4cloud_payment;


-- ----------------------------
-- Table structure for pay_info
-- ----------------------------
DROP TABLE IF EXISTS `pay_info`;
CREATE TABLE `pay_info`  (
  `pay_id` bigint UNSIGNED NOT NULL COMMENT '支付单号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `order_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '本次支付关联的多个订单号',
  `biz_pay_no` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外部订单流水号',
  `sys_type` tinyint NULL DEFAULT NULL COMMENT '系统类型 见SysTypeEnum',
  `pay_status` tinyint NULL DEFAULT NULL COMMENT '支付状态',
  `pay_amount` bigint NULL DEFAULT NULL COMMENT '支付金额',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `callback_content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回调内容',
  `callback_time` datetime NULL DEFAULT NULL COMMENT '回调时间',
  `confirm_time` datetime NULL DEFAULT NULL COMMENT '确认时间',
  PRIMARY KEY (`pay_id`) USING BTREE,
  INDEX `idx_biz_pay_no`(`biz_pay_no`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_order_ids`(`order_ids`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单支付记录';

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;


create database IF NOT EXISTS `mall4cloud_platform` default character set utf8 collate utf8_general_ci;

USE mall4cloud_platform;


-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `param_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key',
  `param_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'value',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置信息表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `sys_user_id` bigint UNSIGNED NOT NULL COMMENT '平台用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工编号',
  `phone_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `has_account` tinyint NULL DEFAULT NULL COMMENT '是否已经设置账号',
  PRIMARY KEY (`sys_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '平台用户';

insert  into `sys_user`(`sys_user_id`,`create_time`,`update_time`,`nick_name`,`avatar`,`code`,`phone_num`,`has_account`) values
(1, '2021-06-24 16:59:33', '2021-07-03 11:17:41', 'admin', '/2021/07/02/dd9ff362454d43d184c6d722dc14d7f6', '9527', '12345678910', 1);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 325 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;


create database IF NOT EXISTS `mall4cloud_product` default character set utf8 collate utf8_general_ci;

USE mall4cloud_product;


-- ----------------------------
-- Table structure for attr
-- ----------------------------
DROP TABLE IF EXISTS `attr`;
CREATE TABLE `attr`  (
  `attr_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'attr id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NOT NULL DEFAULT 0 COMMENT '店铺Id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '属性名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '属性描述',
  `search_type` tinyint NOT NULL DEFAULT 0 COMMENT '0:不需要，1:需要',
  `attr_type` tinyint NOT NULL DEFAULT 0 COMMENT '0:销售属性，1:基本属性',
  PRIMARY KEY (`attr_id`) USING BTREE,
  INDEX `idx_shop_id`(`shop_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7145 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '属性信息';

-- ----------------------------
-- Table structure for attr_category
-- ----------------------------
DROP TABLE IF EXISTS `attr_category`;
CREATE TABLE `attr_category`  (
  `attr_category_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '属性与分类关联id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `category_id` bigint UNSIGNED NOT NULL COMMENT '分类id',
  `attr_id` bigint NOT NULL COMMENT '属性id',
  PRIMARY KEY (`attr_category_id`) USING BTREE,
  UNIQUE INDEX `uni_attrgroup_id`(`category_id`, `attr_id`) USING BTREE,
  INDEX `idx_attr_id`(`attr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19015 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '属性与分类关联信息';

-- ----------------------------
-- Table structure for attr_value
-- ----------------------------
DROP TABLE IF EXISTS `attr_value`;
CREATE TABLE `attr_value`  (
  `attr_value_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `attr_id` bigint UNSIGNED NOT NULL COMMENT '属性ID',
  `value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`attr_value_id`) USING BTREE,
  INDEX `idx_attr`(`attr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58234 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '属性值信息';

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`  (
  `brand_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'brand_id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌描述',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '品牌logo图片',
  `first_letter` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检索首字母',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '状态 1:enable, 0:disable, -1:deleted',
  PRIMARY KEY (`brand_id`) USING BTREE,
  UNIQUE INDEX `uni_brand_id`(`brand_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 152 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '品牌信息';

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint UNSIGNED NOT NULL COMMENT '店铺id',
  `parent_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '父ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类描述',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类地址{parent_id}-{child_id},...',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态 1:enable, 0:disable, -1:deleted',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类图标',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类的显示图片',
  `level` int NOT NULL COMMENT '分类层级 从0开始',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`category_id`) USING BTREE,
  INDEX `idx_shop_id`(`shop_id`) USING BTREE,
  INDEX `idx_pid`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100380 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分类信息';

-- ----------------------------
-- Table structure for category_brand
-- ----------------------------
DROP TABLE IF EXISTS `category_brand`;
CREATE TABLE `category_brand`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `brand_id` bigint UNSIGNED NOT NULL COMMENT '品牌id',
  `category_id` bigint UNSIGNED NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_brand_category_id`(`brand_id`, `category_id`) USING BTREE,
  INDEX `idx_category_id`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '品牌分类关联信息';

-- ----------------------------
-- Table structure for shop_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart_item`;
CREATE TABLE `shop_cart_item`  (
  `cart_item_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NOT NULL COMMENT '店铺ID',
  `spu_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '产品ID',
  `sku_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT 'SkuID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `count` int NOT NULL DEFAULT 0 COMMENT '购物车产品个数',
  `price_fee` bigint UNSIGNED NOT NULL COMMENT '售价，加入购物车时的商品价格',
  `is_checked` tinyint NULL DEFAULT NULL COMMENT '是否已勾选',
  PRIMARY KEY (`cart_item_id`) USING BTREE,
  UNIQUE INDEX `uk_user_shop_sku`(`sku_id`, `user_id`, `shop_id`) USING BTREE,
  INDEX `idx_shop_id`(`shop_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 483 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '购物车';

-- ----------------------------
-- Table structure for sku
-- ----------------------------
DROP TABLE IF EXISTS `sku`;
CREATE TABLE `sku`  (
  `sku_id` bigint NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `spu_id` bigint NOT NULL COMMENT 'SPU id',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku名称',
  `attrs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '多个销售属性值id逗号分隔',
  `img_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku图片',
  `price_fee` bigint NOT NULL DEFAULT 0 COMMENT '售价，整数方式保存',
  `market_price_fee` bigint NOT NULL DEFAULT 0 COMMENT '市场价，整数方式保存',
  `party_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  `model_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品条形码',
  `weight` decimal(15, 3) NULL DEFAULT NULL COMMENT '商品重量',
  `volume` decimal(15, 3) NULL DEFAULT NULL COMMENT '商品体积',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态 1:enable, 0:disable, -1:deleted',
  PRIMARY KEY (`sku_id`) USING BTREE,
  INDEX `idx_spuid`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3523 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sku信息';

-- ----------------------------
-- Table structure for sku_stock
-- ----------------------------
DROP TABLE IF EXISTS `sku_stock`;
CREATE TABLE `sku_stock`  (
  `stock_id` bigint NOT NULL AUTO_INCREMENT COMMENT '库存id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `sku_id` bigint UNSIGNED NOT NULL COMMENT 'SKU ID',
  `actual_stock` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '实际库存',
  `lock_stock` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '锁定库存',
  `stock` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '可售卖库存',
  PRIMARY KEY (`stock_id`) USING BTREE,
  INDEX `idx_skuid`(`sku_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3378 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存信息';

-- ----------------------------
-- Table structure for sku_stock_lock
-- ----------------------------
DROP TABLE IF EXISTS `sku_stock_lock`;
CREATE TABLE `sku_stock_lock`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `spu_id` bigint NULL DEFAULT NULL COMMENT '商品id',
  `sku_id` bigint NULL DEFAULT NULL COMMENT 'sku id',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单id',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态-1已解锁 0待确定 1已锁定',
  `count` int NULL DEFAULT NULL COMMENT '锁定库存数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_spu_sku_order`(`spu_id`, `sku_id`, `order_id`) USING BTREE,
  INDEX `idx_sku_id`(`sku_id`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存锁定信息';

-- ----------------------------
-- Table structure for spu
-- ----------------------------
DROP TABLE IF EXISTS `spu`;
CREATE TABLE `spu`  (
  `spu_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'spu id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `brand_id` bigint NULL DEFAULT NULL COMMENT '品牌ID',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `shop_category_id` bigint NOT NULL DEFAULT 0 COMMENT '店铺分类ID',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `selling_point` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卖点',
  `main_img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品介绍主图',
  `img_urls` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品图片 多个图片逗号分隔',
  `video` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品视频',
  `price_fee` bigint NOT NULL DEFAULT 0 COMMENT '售价，整数方式保存',
  `market_price_fee` bigint NOT NULL DEFAULT 0 COMMENT '市场价，整数方式保存',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态 -1:删除, 0:下架, 1:上架',
  `has_sku_img` tinyint NOT NULL DEFAULT 0 COMMENT 'sku是否含有图片 0无 1有',
  `seq` smallint NOT NULL DEFAULT 3 COMMENT '序号',
  PRIMARY KEY (`spu_id`) USING BTREE,
  INDEX `idx_brandid`(`brand_id`) USING BTREE,
  INDEX `idx_catid`(`category_id`) USING BTREE,
  INDEX `idx_shopid`(`shop_id`) USING BTREE,
  INDEX `idx_shop_catid`(`shop_category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 575 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'spu信息';

-- ----------------------------
-- Table structure for spu_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `spu_attr_value`;
CREATE TABLE `spu_attr_value`  (
  `spu_attr_value_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品属性值关联信息id',
  `spu_id` bigint UNSIGNED NOT NULL COMMENT '商品id',
  `attr_id` bigint UNSIGNED NOT NULL COMMENT '规格属性id',
  `attr_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格属性名称',
  `attr_value_id` bigint NULL DEFAULT NULL COMMENT '规格属性值id',
  `attr_value_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格属性值名称',
  `attr_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格属性描述',
  PRIMARY KEY (`spu_attr_value_id`) USING BTREE,
  UNIQUE INDEX `uni_spuid`(`spu_id`, `attr_id`) USING BTREE,
  INDEX `idx_attrid`(`attr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22489 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品规格属性关联信息';

-- ----------------------------
-- Table structure for spu_detail
-- ----------------------------
DROP TABLE IF EXISTS `spu_detail`;
CREATE TABLE `spu_detail`  (
  `spu_id` bigint NOT NULL COMMENT '商品id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `detail` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品详情',
  PRIMARY KEY (`spu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品详情信息';

-- ----------------------------
-- Table structure for spu_extension
-- ----------------------------
DROP TABLE IF EXISTS `spu_extension`;
CREATE TABLE `spu_extension`  (
  `spu_extend_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品扩展信息表id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `spu_id` bigint UNSIGNED NOT NULL COMMENT '商品id',
  `sale_num` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '销量',
  `actual_stock` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '实际库存',
  `lock_stock` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '锁定库存',
  `stock` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '可售卖库存',
  PRIMARY KEY (`spu_extend_id`) USING BTREE,
  INDEX `idx_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 540 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Table structure for spu_sku_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `spu_sku_attr_value`;
CREATE TABLE `spu_sku_attr_value`  (
  `spu_sku_attr_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品sku销售属性关联信息id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `spu_id` bigint NOT NULL DEFAULT 0 COMMENT 'SPU ID',
  `sku_id` bigint NOT NULL DEFAULT 0 COMMENT 'SKU ID',
  `attr_id` int NULL DEFAULT 0 COMMENT '销售属性ID',
  `attr_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售属性名称',
  `attr_value_id` int NULL DEFAULT 0 COMMENT '销售属性值ID',
  `attr_value_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售属性值',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态 1:enable, 0:disable, -1:deleted',
  PRIMARY KEY (`spu_sku_attr_id`) USING BTREE,
  INDEX `idx_spuid`(`spu_id`) USING BTREE,
  INDEX `idx_skuid`(`sku_id`) USING BTREE,
  INDEX `idx_attrid`(`attr_id`) USING BTREE,
  INDEX `idx_attrvalueid`(`attr_value_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11803 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品sku销售属性关联信息';

-- ----------------------------
-- Table structure for spu_tag
-- ----------------------------
DROP TABLE IF EXISTS `spu_tag`;
CREATE TABLE `spu_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分组标签id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `title` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组标题',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺Id',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态(1为正常,-1为删除)',
  `is_default` tinyint(1) NULL DEFAULT NULL COMMENT '默认类型(0:商家自定义,1:系统默认)',
  `prod_count` bigint NULL DEFAULT NULL COMMENT '商品数量',
  `style` int NULL DEFAULT NULL COMMENT '列表样式(0:一列一个,1:一列两个,2:一列三个)',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品分组表';

-- ----------------------------
-- Table structure for spu_tag_reference
-- ----------------------------
DROP TABLE IF EXISTS `spu_tag_reference`;
CREATE TABLE `spu_tag_reference`  (
  `reference_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分组引用id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NULL DEFAULT NULL COMMENT '店铺id',
  `tag_id` bigint NULL DEFAULT NULL COMMENT '标签id',
  `spu_id` bigint NULL DEFAULT NULL COMMENT '商品id',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态(1:正常,-1:删除)',
  `seq` int NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`reference_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 367 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品分组标签关联信息';

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 254 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;


create database IF NOT EXISTS `mall4cloud_rbac` default character set utf8 collate utf8_general_ci;

USE mall4cloud_rbac;


-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `parent_id` bigint UNSIGNED NOT NULL COMMENT '父菜单ID，一级菜单为0',
  `biz_type` tinyint NULL DEFAULT NULL COMMENT '业务类型 1 店铺菜单 2平台菜单',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限，需要有哪个权限才能访问该菜单',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路径 就像uri',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '1.\'Layout\' 为布局，不会跳页面 2.\'components-demo/tinymce\' 跳转到该页面',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '当设置 noRedirect 的时候该路由在面包屑导航中不可被点击',
  `always_show` tinyint NULL DEFAULT NULL COMMENT '一直显示根路由',
  `hidden` tinyint NULL DEFAULT NULL COMMENT '当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设置该路由在侧边栏和面包屑中展示的名字',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon',
  `no_cache` tinyint NULL DEFAULT NULL COMMENT '如果设置为true，则不会被 <keep-alive> 缓存(默认 false)',
  `breadcrumb` tinyint NULL DEFAULT NULL COMMENT '如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)',
  `affix` tinyint NULL DEFAULT NULL COMMENT '若果设置为true，它则会固定在tags-view中(默认 false)',
  `active_menu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '当路由设置了该属性，则会高亮相对应的侧边栏。',
  `seq` int NULL DEFAULT NULL COMMENT '排序，越小越靠前',
  PRIMARY KEY (`menu_id`) USING BTREE,
  INDEX `idx_pid`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 328 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单管理';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (111, '2020-12-01 14:20:42', '2021-01-28 10:12:08', 0, 1, NULL, '/rbac', 'Layout', NULL, NULL, 0, '权限管理', '权限管理', 'el-icon-office-building', NULL, NULL, NULL, NULL, 99);
INSERT INTO `menu` VALUES (112, '2020-12-01 14:22:09', '2021-01-28 10:12:14', 111, 1, NULL, '/multishop/shop_user', 'multishop/shop-user', NULL, NULL, 0, '用户管理', '用户管理', '', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (134, '2020-12-03 16:49:48', '2021-01-28 10:12:22', 111, 1, NULL, 'role', 'rbac/role', NULL, NULL, 0, '角色管理', '角色管理', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `menu` VALUES (137, '2020-12-03 17:54:02', '2021-01-28 10:13:07', 141, 1, NULL, 'attr', 'product/attr', NULL, NULL, 0, '属性管理', '属性管理', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (139, '2020-12-03 17:54:02', '2021-01-28 10:13:20', 141, 1, NULL, 'category', 'product/category', NULL, NULL, 0, 'category', '分类管理', NULL, NULL, NULL, NULL, NULL, 2);
INSERT INTO `menu` VALUES (141, '2020-12-03 17:55:22', '2021-01-01 09:01:48', 0, 1, NULL, '/product', 'Layout', NULL, NULL, 0, '商品管理', '商品管理', 'product', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (142, '2020-12-03 17:54:02', '2021-03-12 09:31:52', 141, 1, NULL, 'prod_info', 'product/prod-info', NULL, NULL, 0, '发布商品', '发布商品', NULL, NULL, NULL, NULL, NULL, 4);
INSERT INTO `menu` VALUES (145, '2020-12-04 10:08:17', '2021-01-28 10:13:47', 0, 1, NULL, '/multishop', 'Layout', NULL, NULL, 0, '店铺管理', '店铺管理', 'el-icon-house', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (146, '2020-12-04 10:09:38', '2021-01-28 10:13:53', 145, 1, NULL, 'index_img', 'multishop/index-img', NULL, NULL, 0, '轮播图管理', '轮播图管理', '', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (147, '2020-12-04 11:30:59', '2021-01-01 09:28:45', 0, 1, NULL, '/order', 'Layout', NULL, NULL, 0, '订单管理', '订单管理', 'order', NULL, NULL, NULL, NULL, 90);
INSERT INTO `menu` VALUES (148, '2020-12-04 11:32:46', '2021-01-28 09:52:10', 147, 1, NULL, 'order', 'order/order', NULL, NULL, 0, 'order', '订单管理', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `menu` VALUES (152, '2020-12-16 10:16:39', '2021-01-28 10:13:32', 141, 1, NULL, 'list', 'product/list', NULL, NULL, 0, '商品列表', '商品列表', '', NULL, NULL, NULL, NULL, 5);
INSERT INTO `menu` VALUES (155, '2020-12-01 14:20:42', '2021-04-22 10:30:44', 0, 2, NULL, '/rbac', 'Layout', NULL, NULL, 0, '权限管理', '权限管理', 'el-icon-office-building', NULL, NULL, NULL, NULL, 99);
INSERT INTO `menu` VALUES (156, '2020-12-01 14:22:09', '2021-04-22 10:30:44', 155, 2, NULL, '/platform/sys_user', 'platform/sys-user', NULL, NULL, 0, '用户管理', '用户管理', '', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (157, '2020-12-03 16:49:48', '2021-04-22 10:30:44', 155, 2, NULL, 'role', 'rbac/role', NULL, NULL, 0, '角色管理', '角色管理', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `menu` VALUES (158, '2020-12-03 16:49:48', '2021-04-22 10:30:44', 155, 2, NULL, 'menu', 'rbac/menu', NULL, NULL, 0, '平台菜单管理', '平台菜单管理', NULL, NULL, NULL, NULL, NULL, 2);
INSERT INTO `menu` VALUES (159, '2020-12-03 16:49:48', '2021-04-22 10:30:44', 155, 2, NULL, 'menu_permission', 'rbac/menu-permission', NULL, NULL, 0, '菜单资源', '菜单资源', NULL, NULL, NULL, NULL, NULL, 3);
INSERT INTO `menu` VALUES (160, '2020-12-03 17:54:02', '2021-04-22 10:30:44', 164, 2, NULL, 'attr', 'product/attr', NULL, NULL, 0, '属性管理', '属性管理', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (161, '2020-12-03 17:54:02', '2021-04-22 10:30:44', 164, 2, NULL, 'brand', 'product/brand', NULL, NULL, 0, '品牌管理', '品牌管理', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `menu` VALUES (162, '2020-12-03 17:54:02', '2021-04-22 10:30:44', 164, 2, NULL, 'category', 'product/category', NULL, NULL, 0, 'category', '分类管理', NULL, NULL, NULL, NULL, NULL, 2);
INSERT INTO `menu` VALUES (164, '2020-12-03 17:55:22', '2021-04-22 10:30:44', 0, 2, NULL, '/product', 'Layout', NULL, NULL, 0, 'product', '商品管理', 'el-icon-shopping-bag-1', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (175, '2020-12-16 10:16:39', '2021-04-22 10:30:44', 164, 2, NULL, 'list', 'product/list', NULL, NULL, 0, '商品管理', '商品管理', '', NULL, NULL, NULL, NULL, 5);
INSERT INTO `menu` VALUES (178, '2020-12-22 09:26:41', '2021-04-22 10:30:44', 155, 2, NULL, 'shop-menu', 'rbac/shop-menu', NULL, NULL, 0, '店铺菜单管理', '店铺菜单管理', '', NULL, NULL, NULL, NULL, 2);
INSERT INTO `menu` VALUES (255, '2021-01-27 14:10:56', '2021-04-22 10:30:44', 0, 2, NULL, '/platform', 'Layout', NULL, NULL, 0, 'platform', '平台管理', 'tree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (256, '2021-01-27 14:14:51', '2021-04-22 10:30:44', 255, 2, NULL, 'hot-search', 'platform/hot-search', NULL, NULL, 0, 'hot-search', '热搜管理', 'el-icon-search', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (257, '2021-01-28 09:48:39', '2021-04-22 10:30:44', 255, 2, NULL, 'index-img', 'platform/index-img', NULL, NULL, 0, '轮播图管理', '轮播图管理', 'el-icon-picture-outline', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (258, '2021-01-28 11:06:52', '2021-01-28 11:06:52', 145, 1, NULL, 'hot-search', 'multishop/hot-search', NULL, NULL, 0, 'hot-search', '热搜管理', '', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (302, '2021-05-13 19:02:02', '2021-05-13 19:02:20', 255, 2, NULL, 'shop-detail', 'platform/shop-detail', NULL, NULL, 1, '店铺详情', '店铺详情', '', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (306, '2021-05-14 17:32:04', '2021-05-14 17:34:08', 255, 2, NULL, 'shop-edit', 'platform/shop-edit', NULL, NULL, 1, '店铺编辑', '店铺编辑', '', NULL, NULL, NULL, NULL, 0);
INSERT INTO `menu` VALUES (327, '2021-06-30 20:22:45', '2021-06-30 20:25:49', 255, 2, NULL, 'shop-manage', 'platform/shop-manage', NULL, NULL, 0, '店铺管理', '店铺管理', 'el-icon-s-order', NULL, NULL, NULL, NULL, 3);

-- ----------------------------
-- Table structure for menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `menu_permission`;
CREATE TABLE `menu_permission`  (
  `menu_permission_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单资源用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `menu_id` bigint NOT NULL COMMENT '资源关联菜单',
  `biz_type` tinyint NOT NULL COMMENT '业务类型 1 店铺菜单 2平台菜单',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限对应的编码',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源名称',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源对应服务器路径',
  `method` tinyint NOT NULL COMMENT '请求方法 1.GET 2.POST 3.PUT 4.DELETE',
  PRIMARY KEY (`menu_permission_id`) USING BTREE,
  UNIQUE INDEX `uk_permission`(`permission`, `biz_type`) USING BTREE,
  INDEX `idx_menuid`(`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 230 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单资源';

-- ----------------------------
-- Records of menu_permission
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint UNSIGNED NOT NULL COMMENT '创建者ID',
  `biz_type` tinyint NULL DEFAULT NULL COMMENT '业务类型 1 店铺菜单 2平台菜单',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '所属租户',
  PRIMARY KEY (`role_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色';

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关联id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色ID',
  `menu_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '菜单ID',
  `menu_permission_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '菜单资源用户id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_roleid_menu_permission_id`(`role_id`, `menu_id`, `menu_permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1924 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色与菜单对应关系';

-- ----------------------------
-- Records of role_menu
-- ----------------------------


-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关联id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_userid_roleid`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 254 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与角色对应关系';

-- ----------------------------
-- Records of user_role
-- ----------------------------



create database IF NOT EXISTS `mall4cloud_seata` default character set utf8 collate utf8_general_ci;

USE mall4cloud_seata;

-- ----------------------------
-- Table structure for branch_table
-- ----------------------------
DROP TABLE IF EXISTS `branch_table`;
CREATE TABLE `branch_table`  (
  `branch_id` bigint NOT NULL,
  `xid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `transaction_id` bigint NULL DEFAULT NULL,
  `resource_group_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `resource_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `branch_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `application_data` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gmt_create` datetime(6) NULL DEFAULT NULL,
  `gmt_modified` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`branch_id`) USING BTREE,
  INDEX `idx_xid`(`xid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of branch_table
-- ----------------------------

-- ----------------------------
-- Table structure for global_table
-- ----------------------------
DROP TABLE IF EXISTS `global_table`;
CREATE TABLE `global_table`  (
  `xid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `transaction_id` bigint NULL DEFAULT NULL,
  `status` tinyint NOT NULL,
  `application_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `transaction_service_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `transaction_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `timeout` int NULL DEFAULT NULL,
  `begin_time` bigint NULL DEFAULT NULL,
  `application_data` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`xid`) USING BTREE,
  INDEX `idx_gmt_modified_status`(`gmt_modified`, `status`) USING BTREE,
  INDEX `idx_transaction_id`(`transaction_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of global_table
-- ----------------------------

-- ----------------------------
-- Table structure for lock_table
-- ----------------------------
DROP TABLE IF EXISTS `lock_table`;
CREATE TABLE `lock_table`  (
  `row_key` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `xid` varchar(96) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `transaction_id` bigint NULL DEFAULT NULL,
  `branch_id` bigint NOT NULL,
  `resource_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `table_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pk` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`row_key`) USING BTREE,
  INDEX `idx_branch_id`(`branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of lock_table
-- ----------------------------



create database IF NOT EXISTS `mall4cloud_user` default character set utf8 collate utf8_general_ci;

USE mall4cloud_user;


-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `area_id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `area_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址',
  `parent_id` bigint NOT NULL COMMENT '上级地址',
  `level` int NOT NULL COMMENT '等级（从1开始）',
  PRIMARY KEY (`area_id`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE COMMENT '上级id'
) ENGINE = InnoDB AUTO_INCREMENT = 659041 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '省市区地区信息';

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint NOT NULL COMMENT 'ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像图片路径',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 1 正常 0 无效',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表';

-- ----------------------------
-- Table structure for user_addr
-- ----------------------------
DROP TABLE IF EXISTS `user_addr`;
CREATE TABLE `user_addr`  (
  `addr_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `is_default` tinyint NOT NULL COMMENT '是否默认地址 1是',
  `consignee` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `province_id` bigint NULL DEFAULT NULL COMMENT '省ID',
  `province` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city_id` bigint NULL DEFAULT NULL COMMENT '城市ID',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
  `area_id` bigint NULL DEFAULT NULL COMMENT '区ID',
  `area` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `post_code` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `lng` decimal(12, 6) NULL DEFAULT NULL COMMENT '经度',
  `lat` decimal(12, 6) NULL DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`addr_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 203 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户地址';
