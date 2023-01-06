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

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4122 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (2, 'application-dev.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  #避免nacos取网卡出错\n  cloud:\n    inetutils:\n      preferred-networks: 192.168.1\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n    hikari:\n      minimum-idle: 0\n      maximum-pool-size: 20\n      idle-timeout: 25000\n      auto-commit: true\n      connection-test-query: SELECT 1\n  redis:\n    host: 192.168.1.49\n    # password: null\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n      \n#mybatis的相关配置\nmybatis:\n  #mapper配置文件\n  mapper-locations: classpath:mapper/*Mapper.xml\n  type-aliases-package: com.mall4j.cloud.**.model\n  #开启驼峰命名\n  configuration:\n    map-underscore-to-camel-case: true\n\nseata:\n  config:\n    type: nacos\n    nacos:\n      namespace: 4b70485d-72dd-44df-a76a-7a3f578a3001\n      server-addr: ${spring.cloud.nacos.discovery.server-addr}\n      password: ${spring.cloud.nacos.discovery.password}\n      username: ${spring.cloud.nacos.discovery.username}\n  registry:\n    type: nacos\n    nacos:\n      server-addr: ${spring.cloud.nacos.discovery.server-addr}\n      username: ${spring.cloud.nacos.discovery.username}\n      password: ${spring.cloud.nacos.discovery.password}\n      namespace: ${seata.config.nacos.namespace}\n\nlogging:\n  level:\n    root: info\n    com:\n      mall4cloud:\n        shop: debug\n# 分页合理化，当查询到页码大于最后一页的时候，返回最后一页的数据，防止vue在最后一页删除时，数据不对的问题\npagehelper:\n  reasonable: true\n\nbiz:\n  oss:\n    # resources-url是带有bucket的\n    resources-url: http://192.168.1.49:9000/mall4cloud\n    type: 1\n    endpoint: http://192.168.1.49:9000\n    bucket: mall4cloud\n    access-key-id: admin\n    access-key-secret: admin123456\n\nfeign:\n  client:\n    config:\n      default:\n        connectTimeout: 5000\n        readTimeout: 5000\n        loggerLevel: basic\n  inside:\n    key: mall4cloud-feign-inside-key\n    secret: mall4cloud-feign-inside-secret\n    # ip白名单，如果有需要的话，用小写逗号分割\n    ips: \n\nmall4cloud:\n  job:\n    accessToken:\n    admin:\n      addresses: http://192.168.1.49:8999\n\nrocketmq:\n  name-server: 192.168.1.49:9876', 'bc01547fe2e5ada0aa489dfd10a35839', '2020-09-07 05:54:23', '2021-07-31 11:20:32', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (8, 'mall4cloud-auth.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_auth}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\n\n# 用于token aes签名的key，16位\nauth:\n  token:\n    signKey: -mall4-mall4-mall4- ', '56d607c86e383e27174c8b7ce2e690bb', '2020-09-07 06:05:57', '2021-07-31 11:21:33', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (13, 'mall4cloud-multishop.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_multishop}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', 'e6e62d5a4d40101dc899cc37aecf7306', '2020-09-07 06:38:01', '2021-07-31 11:21:45', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (16, 'mall4cloud-leaf.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_leaf}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', 'f416d9ca7750b8cf5b78154e008233e8', '2020-09-07 06:44:22', '2021-07-31 11:21:55', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (18, 'mall4cloud-rbac.yml', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_rbac}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\n', 'd33047562436e609a37c493ad337ca85', '2020-09-07 06:47:49', '2021-07-31 11:22:04', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (31, 'mall4cloud-biz.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_biz}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\n\nbiz:\n  oss:\n    # resources-url是带有bucket的\n    resources-url: http://192.168.1.46:9000/mall4cloud\n    # 文件上传类型 0.阿里云 1.minio\n    type: 1\n    endpoint: http://192.168.1.46:9000\n    bucket: mall4cloud\n    access-key-id: admin\n    access-key-secret: admin123456', '4619160fe2cdd49f21ccc7327dfe6857', '2020-09-10 07:26:09', '2021-07-31 11:22:21', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (72, 'mall4cloud-product.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_product}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', '2d7d7afa8b27e6044377a4a3d584885a', '2020-11-11 09:35:20', '2021-07-31 11:22:45', 'nacos', '192.168.1.11', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (73, 'mall4cloud-search.yml', 'DEFAULT_GROUP', 'elastic:\n  # elastic的地址\n  address: http://192.168.1.46:9200', '0e8ea5c03a559a26407a21e0efcc6a97', '2020-11-12 06:57:25', '2021-04-02 15:13:25', NULL, '', '', '', '', '', '', 'yaml', '');
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
INSERT INTO `config_info` VALUES (3665, 'service.default.grouplist', 'SEATA_GROUP', '192.168.1.46:8091', '9481e82f38d58591d24335b9ad4aecb5', '2020-11-30 09:43:38', '2021-03-01 07:48:07', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
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
INSERT INTO `config_info` VALUES (3686, 'store.db.url', 'SEATA_GROUP', 'jdbc:mysql://192.168.1.46:3306/mall4cloud_seata?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true', '2104a66c7d4bb86db640839a10d2c273', '2020-11-30 09:43:40', '2021-06-16 07:57:12', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', '', '', '', 'text', '');
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
INSERT INTO `config_info` VALUES (3727, 'mall4cloud-order.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_order}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', 'c33dca13b41bb90b2477f9ea7228ff48', '2020-12-04 05:45:13', '2021-04-02 12:53:44', NULL, '', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3736, 'mall4cloud-user.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_user}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', 'a2c60ea7887ebb8a0f6f5f5005048b4b', '2020-12-04 05:51:25', '2021-04-02 12:54:49', NULL, '', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3741, 'service.vgroupMapping.mall4cloud-coupon-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-09 01:49:55', '2020-12-09 01:49:55', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3747, 'service.vgroupMapping.mall4cloud-delivery-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-14 03:30:09', '2020-12-14 03:30:09', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3755, 'service.vgroupMapping.mall4cloud-discount-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-16 06:13:14', '2020-12-16 06:13:14', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3759, 'mall4cloud-platform.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_platform}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}', '274a64e67ef4fab65de1e7b70e0a9ff7', '2020-12-21 07:38:16', '2021-04-02 12:55:59', NULL, '', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3761, 'service.vgroupMapping.mall4cloud-platform-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-21 07:41:01', '2020-12-21 07:41:01', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3762, 'service.vgroupMapping.mall4cloud-search-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-12-22 02:19:51', '2020-12-22 02:19:51', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (3812, 'mall4cloud-payment.yml', 'DEFAULT_GROUP', '# 数据源\nspring:\n  datasource:\n    url: jdbc:mysql://${MYSQL_HOST:192.168.1.46}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:mall4cloud_payment}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&useAffectedRows=true\n    username: ${MYSQL_USERNAME:root}\n    password: ${MYSQL_PASSWORD:root}\napplication:\n  domainUrl: http://47.112.182.96:8126/mall4cloud_payment', '2f4b89c096ab0cef9359c98b91ade543', '2021-02-03 03:19:16', '2021-04-02 12:56:14', NULL, '', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3814, 'service.vgroupMapping.mall4cloud-payment-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-02-03 03:20:03', '2021-02-03 03:20:03', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4003, 'client.rm.tableMetaCheckerInterval', 'SEATA_GROUP', '60000', '2b4226dd7ed6eb2d419b881f3ae9c97c', '2021-03-01 07:48:08', '2021-03-01 07:48:08', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4004, 'client.rm.sqlParserType', 'SEATA_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2021-03-01 07:48:08', '2021-03-01 07:48:08', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4040, 'client.undo.compress.enable', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-03-01 07:48:13', '2021-03-01 07:48:13', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4041, 'client.undo.compress.type', 'SEATA_GROUP', 'zip', 'adcdbd79a8d84175c229b192aadc02f2', '2021-03-01 07:48:13', '2021-03-01 07:48:13', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4042, 'client.undo.compress.threshold', 'SEATA_GROUP', '64k', 'bd44a6458bdbff0b5cac721ba361f035', '2021-03-01 07:48:14', '2021-03-01 07:48:14', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4065, 'service.vgroupMapping.mall4cloud-group-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-03-20 03:01:47', '2021-03-20 03:01:47', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4083, 'service.vgroupMapping.mall4cloud-seckill-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-04-01 06:18:16', '2021-04-01 06:18:16', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (4115, 'service.vgroupMapping.mall4cloud-flow-seata-service-group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-05-22 02:36:39', '2021-05-22 02:36:39', NULL, '', '', '4b70485d-72dd-44df-a76a-7a3f578a3001', NULL, NULL, NULL, 'text', NULL);


-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段';

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta';

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag';

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation';

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表';

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint UNSIGNED NOT NULL,
  `nid` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4379 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造';

-- ----------------------------
-- Records of his_config_info
-- ----------------------------


-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表';

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info';

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (1, '1', '4b70485d-72dd-44df-a76a-7a3f578a3001', 'seata', 'seata', 'nacos', 1606728824253, 1606728824253);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);


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
