/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.25 : Database - mall4cloud_multishop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mall4cloud_multishop` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mall4cloud_multishop`;

/*Table structure for table `hot_search` */

DROP TABLE IF EXISTS `hot_search`;

CREATE TABLE `hot_search` (
  `hot_search_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint DEFAULT NULL COMMENT '店铺ID 0为全平台热搜',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `seq` int DEFAULT NULL COMMENT '顺序',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0下线 1上线',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '热搜标题',
  PRIMARY KEY (`hot_search_id`) USING BTREE,
  KEY `idx_shop_id` (`shop_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='热搜';

/*Table structure for table `index_img` */

DROP TABLE IF EXISTS `index_img`;

CREATE TABLE `index_img` (
  `img_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NOT NULL COMMENT '店铺ID',
  `img_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 1:enable, 0:disable',
  `seq` int NOT NULL DEFAULT '0' COMMENT '顺序',
  `spu_id` bigint DEFAULT NULL COMMENT '关联商品id',
  `img_type` tinyint NOT NULL COMMENT '图片类型 0:小程序',
  PRIMARY KEY (`img_id`) USING BTREE,
  KEY `idx_shop_id` (`shop_id`) USING BTREE,
  KEY `idx_spu_id` (`spu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='轮播图';

/*Table structure for table `shop_detail` */

DROP TABLE IF EXISTS `shop_detail`;

CREATE TABLE `shop_detail` (
  `shop_id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '店铺名称',
  `intro` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '店铺简介',
  `shop_logo` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '店铺logo(可修改)',
  `mobile_background_pic` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '店铺移动端背景图',
  `shop_status` tinyint NOT NULL COMMENT '店铺状态(-1:已删除 0: 停业中 1:营业中)',
  `business_license` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '营业执照',
  `identity_card_front` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证正面',
  `identity_card_later` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证反面',
  `type` tinyint DEFAULT NULL COMMENT '店铺类型1自营店 2普通店',
  PRIMARY KEY (`shop_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=324 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='店铺详情';
insert into `shop_detail` (`shop_id`, `create_time`, `update_time`, `shop_name`, `intro`, `shop_logo`, `mobile_background_pic`, `shop_status`, `business_license`, `identity_card_front`, `identity_card_later`, `type`) values
    ('324','2022-04-14 16:36:13','2022-04-14 16:36:13','商家','商家店铺','/2022/04/14/2bdd14bd15094b15907dfe6c2b86c536','/2022/04/14/791fb3e04fca4fc79adeb79378436068','1','/2022/04/14/136d531dfa9b4cbdb17f555a73b0f7b4','/2022/04/14/50f9525a2b86434e98d97f4df017fcb4','/2022/04/14/dcc4c3c9f44b420a995c292d6df93f2c','2');


/*Table structure for table `shop_user` */

DROP TABLE IF EXISTS `shop_user`;

CREATE TABLE `shop_user` (
  `shop_user_id` bigint unsigned NOT NULL COMMENT '商家用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint DEFAULT NULL COMMENT '关联店铺id',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '员工编号',
  `phone_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系方式',
  `has_account` tinyint DEFAULT NULL COMMENT '是否已经设置账号 0:未设置 1:已设置',
  PRIMARY KEY (`shop_user_id`) USING BTREE,
  KEY `idx_shopid` (`shop_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商家用户';

/*Table structure for table `undo_log` */
insert into `shop_user` (`shop_user_id`, `create_time`, `update_time`, `shop_id`, `nick_name`, `code`, `phone_num`, `has_account`) values
('110400','2022-04-14 16:36:13','2022-04-14 16:36:13','324','商家',NULL,NULL,'1');



DROP TABLE IF EXISTS `undo_log`;

CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=388 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO `mall4cloud_multishop`.`shop_detail` (`shop_id`, `create_time`, `update_time`, `shop_name`, `intro`, `shop_logo`, `mobile_background_pic`, `shop_status`, `business_license`, `identity_card_front`, `identity_card_later`, `type`) VALUES (0, '2022-04-25 14:49:45', '2022-04-25 14:50:35', 'lanhai', NULL, NULL, NULL, 1, '123456789123', '123456789123', NULL, 1);

INSERT INTO `mall4cloud_multishop`.`shop_user` (`shop_user_id`, `create_time`, `update_time`, `shop_id`, `nick_name`, `code`, `phone_num`, `has_account`) VALUES (2, '2022-04-25 14:07:57', '2022-04-25 14:53:38', 0, 'hyg123', '', '18470778787', 1);


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
