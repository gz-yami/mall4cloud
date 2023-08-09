/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.25 : Database - mall4cloud_leaf
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mall4cloud_leaf` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mall4cloud_leaf`;

/*Table structure for table `leaf_alloc` */

DROP TABLE IF EXISTS `leaf_alloc`;

CREATE TABLE `leaf_alloc` (
  `biz_tag` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '区分业务',
  `max_id` bigint unsigned NOT NULL DEFAULT '1' COMMENT '该biz_tag目前所被分配的ID号段的最大值',
  `step` int NOT NULL COMMENT '每次分配的号段长度',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `random_step` int unsigned NOT NULL DEFAULT '1' COMMENT '每次getid时随机增加的长度，这样就不会有连续的id了',
  PRIMARY KEY (`biz_tag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of leaf_alloc
-- ----------------------------
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-auth-account', 112000, 100, '2021-07-01 14:16:40', 'mall4cloud-multishop数据库中auth_account这张表的uid', 10);
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-multishop-user', 110400, 100, '2021-07-01 11:10:32', 'mall4cloud-multishop数据库中shop_user这张表的id', 10);
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-order', 1568693084, 100, '2021-07-05 15:25:19', '订单id', 10);
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-pay', 564994845, 100, '2021-07-05 15:14:40', '支付单号', 10);
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-platform-user', 101600, 100, '2021-07-03 13:13:54', 'mall4cloud-platform数据库中sys_user这张表的id', 10);
INSERT INTO `leaf_alloc` VALUES ('mall4cloud-user', 106600, 100, '2021-07-01 11:22:26', 'mall4cloud-user数据库中user这张表的id', 10);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
