/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.25 : Database - mall4cloud_product
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mall4cloud_product` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mall4cloud_product`;

/*Table structure for table `attr` */

DROP TABLE IF EXISTS `attr`;

CREATE TABLE `attr` (
  `attr_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'attr id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NOT NULL DEFAULT '0' COMMENT '店铺Id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '属性名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '属性描述',
  `search_type` tinyint NOT NULL DEFAULT '0' COMMENT '0:不需要，1:需要',
  `attr_type` tinyint NOT NULL DEFAULT '0' COMMENT '0:销售属性，1:基本属性',
  PRIMARY KEY (`attr_id`) USING BTREE,
  KEY `idx_shop_id` (`shop_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7149 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='属性信息';

/*Table structure for table `attr_category` */

DROP TABLE IF EXISTS `attr_category`;

CREATE TABLE `attr_category` (
  `attr_category_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '属性与分类关联id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `category_id` bigint unsigned NOT NULL COMMENT '分类id',
  `attr_id` bigint NOT NULL COMMENT '属性id',
  PRIMARY KEY (`attr_category_id`) USING BTREE,
  UNIQUE KEY `uni_attrgroup_id` (`category_id`,`attr_id`) USING BTREE,
  KEY `idx_attr_id` (`attr_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19019 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='属性与分类关联信息';

/*Table structure for table `attr_value` */

DROP TABLE IF EXISTS `attr_value`;

CREATE TABLE `attr_value` (
  `attr_value_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `attr_id` bigint unsigned NOT NULL COMMENT '属性ID',
  `value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`attr_value_id`) USING BTREE,
  KEY `idx_attr` (`attr_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=58244 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='属性值信息';

/*Table structure for table `brand` */

DROP TABLE IF EXISTS `brand`;

CREATE TABLE `brand` (
  `brand_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'brand_id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '品牌名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '品牌描述',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '品牌logo图片',
  `first_letter` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '检索首字母',
  `seq` int DEFAULT NULL COMMENT '排序',
  `status` tinyint unsigned DEFAULT '0' COMMENT '状态 1:enable, 0:disable, -1:deleted',
  PRIMARY KEY (`brand_id`) USING BTREE,
  UNIQUE KEY `uni_brand_id` (`brand_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='品牌信息';

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint unsigned NOT NULL COMMENT '店铺id',
  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类描述',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类地址{parent_id}-{child_id},...',
  `status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '状态 1:enable, 0:disable, -1:deleted',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类图标',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类的显示图片',
  `level` int NOT NULL COMMENT '分类层级 从0开始',
  `seq` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`category_id`) USING BTREE,
  KEY `idx_shop_id` (`shop_id`) USING BTREE,
  KEY `idx_pid` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100390 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='分类信息';

/*Table structure for table `category_brand` */

DROP TABLE IF EXISTS `category_brand`;

CREATE TABLE `category_brand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `brand_id` bigint unsigned NOT NULL COMMENT '品牌id',
  `category_id` bigint unsigned NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uni_brand_category_id` (`brand_id`,`category_id`) USING BTREE,
  KEY `idx_category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='品牌分类关联信息';

/*Table structure for table `shop_cart_item` */

DROP TABLE IF EXISTS `shop_cart_item`;

CREATE TABLE `shop_cart_item` (
  `cart_item_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NOT NULL COMMENT '店铺ID',
  `spu_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '产品ID',
  `sku_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT 'SkuID',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `count` int NOT NULL DEFAULT '0' COMMENT '购物车产品个数',
  `price_fee` bigint unsigned NOT NULL COMMENT '售价，加入购物车时的商品价格',
  `is_checked` tinyint DEFAULT NULL COMMENT '是否已勾选',
  PRIMARY KEY (`cart_item_id`) USING BTREE,
  UNIQUE KEY `uk_user_shop_sku` (`sku_id`,`user_id`,`shop_id`) USING BTREE,
  KEY `idx_shop_id` (`shop_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=496 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='购物车';

/*Table structure for table `sku` */

DROP TABLE IF EXISTS `sku`;

CREATE TABLE `sku` (
  `sku_id` bigint NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `spu_id` bigint NOT NULL COMMENT 'SPU id',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'sku名称',
  `attrs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '多个销售属性值id逗号分隔',
  `img_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'sku图片',
  `price_fee` bigint NOT NULL DEFAULT '0' COMMENT '售价，整数方式保存',
  `market_price_fee` bigint NOT NULL DEFAULT '0' COMMENT '市场价，整数方式保存',
  `party_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品编码',
  `model_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品条形码',
  `weight` decimal(15,3) DEFAULT NULL COMMENT '商品重量',
  `volume` decimal(15,3) DEFAULT NULL COMMENT '商品体积',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 1:enable, 0:disable, -1:deleted',
  PRIMARY KEY (`sku_id`) USING BTREE,
  KEY `idx_spuid` (`spu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3566 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='sku信息';

/*Table structure for table `sku_stock` */

DROP TABLE IF EXISTS `sku_stock`;

CREATE TABLE `sku_stock` (
  `stock_id` bigint NOT NULL AUTO_INCREMENT COMMENT '库存id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `sku_id` bigint unsigned NOT NULL COMMENT 'SKU ID',
  `actual_stock` int unsigned NOT NULL DEFAULT '0' COMMENT '实际库存',
  `lock_stock` int unsigned NOT NULL DEFAULT '0' COMMENT '锁定库存',
  `stock` int unsigned NOT NULL DEFAULT '0' COMMENT '可售卖库存',
  PRIMARY KEY (`stock_id`) USING BTREE,
  KEY `idx_skuid` (`sku_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3421 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='库存信息';

/*Table structure for table `sku_stock_lock` */

DROP TABLE IF EXISTS `sku_stock_lock`;

CREATE TABLE `sku_stock_lock` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `spu_id` bigint DEFAULT NULL COMMENT '商品id',
  `sku_id` bigint DEFAULT NULL COMMENT 'sku id',
  `order_id` bigint DEFAULT NULL COMMENT '订单id',
  `status` tinyint DEFAULT NULL COMMENT '状态-1已解锁 0待确定 1已锁定',
  `count` int DEFAULT NULL COMMENT '锁定库存数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uni_spu_sku_order` (`spu_id`,`sku_id`,`order_id`) USING BTREE,
  KEY `idx_sku_id` (`sku_id`) USING BTREE,
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1026 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='库存锁定信息';

/*Table structure for table `spu` */

DROP TABLE IF EXISTS `spu`;

CREATE TABLE `spu` (
  `spu_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'spu id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `brand_id` bigint DEFAULT NULL COMMENT '品牌ID',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `shop_category_id` bigint NOT NULL DEFAULT '0' COMMENT '店铺分类ID',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `selling_point` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '卖点',
  `main_img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品介绍主图',
  `img_urls` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品图片 多个图片逗号分隔',
  `video` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品视频',
  `price_fee` bigint NOT NULL DEFAULT '0' COMMENT '售价，整数方式保存',
  `market_price_fee` bigint NOT NULL DEFAULT '0' COMMENT '市场价，整数方式保存',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 -1:删除, 0:下架, 1:上架',
  `has_sku_img` tinyint NOT NULL DEFAULT '0' COMMENT 'sku是否含有图片 0无 1有',
  `seq` smallint NOT NULL DEFAULT '3' COMMENT '序号',
  PRIMARY KEY (`spu_id`) USING BTREE,
  KEY `idx_brandid` (`brand_id`) USING BTREE,
  KEY `idx_catid` (`category_id`) USING BTREE,
  KEY `idx_shopid` (`shop_id`) USING BTREE,
  KEY `idx_shop_catid` (`shop_category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=587 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='spu信息';

/*Table structure for table `spu_attr_value` */

DROP TABLE IF EXISTS `spu_attr_value`;

CREATE TABLE `spu_attr_value` (
  `spu_attr_value_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品属性值关联信息id',
  `spu_id` bigint unsigned NOT NULL COMMENT '商品id',
  `attr_id` bigint unsigned NOT NULL COMMENT '规格属性id',
  `attr_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '规格属性名称',
  `attr_value_id` bigint DEFAULT NULL COMMENT '规格属性值id',
  `attr_value_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '规格属性值名称',
  `attr_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '规格属性描述',
  PRIMARY KEY (`spu_attr_value_id`) USING BTREE,
  UNIQUE KEY `uni_spuid` (`spu_id`,`attr_id`) USING BTREE,
  KEY `idx_attrid` (`attr_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22517 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品规格属性关联信息';

/*Table structure for table `spu_detail` */

DROP TABLE IF EXISTS `spu_detail`;

CREATE TABLE `spu_detail` (
  `spu_id` bigint NOT NULL COMMENT '商品id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `detail` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '商品详情',
  PRIMARY KEY (`spu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品详情信息';

/*Table structure for table `spu_extension` */

DROP TABLE IF EXISTS `spu_extension`;

CREATE TABLE `spu_extension` (
  `spu_extend_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品扩展信息表id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `spu_id` bigint unsigned NOT NULL COMMENT '商品id',
  `sale_num` int unsigned NOT NULL DEFAULT '0' COMMENT '销量',
  `actual_stock` int unsigned NOT NULL DEFAULT '0' COMMENT '实际库存',
  `lock_stock` int unsigned NOT NULL DEFAULT '0' COMMENT '锁定库存',
  `stock` int unsigned NOT NULL DEFAULT '0' COMMENT '可售卖库存',
  PRIMARY KEY (`spu_extend_id`) USING BTREE,
  KEY `idx_spu` (`spu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=552 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `spu_sku_attr_value` */

DROP TABLE IF EXISTS `spu_sku_attr_value`;

CREATE TABLE `spu_sku_attr_value` (
  `spu_sku_attr_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '商品sku销售属性关联信息id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `spu_id` bigint NOT NULL DEFAULT '0' COMMENT 'SPU ID',
  `sku_id` bigint NOT NULL DEFAULT '0' COMMENT 'SKU ID',
  `attr_id` int DEFAULT '0' COMMENT '销售属性ID',
  `attr_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '销售属性名称',
  `attr_value_id` int DEFAULT '0' COMMENT '销售属性值ID',
  `attr_value_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '销售属性值',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 1:enable, 0:disable, -1:deleted',
  PRIMARY KEY (`spu_sku_attr_id`) USING BTREE,
  KEY `idx_spuid` (`spu_id`) USING BTREE,
  KEY `idx_skuid` (`sku_id`) USING BTREE,
  KEY `idx_attrid` (`attr_id`) USING BTREE,
  KEY `idx_attrvalueid` (`attr_value_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11831 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品sku销售属性关联信息';

/*Table structure for table `spu_tag` */

DROP TABLE IF EXISTS `spu_tag`;

CREATE TABLE `spu_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分组标签id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `title` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组标题',
  `shop_id` bigint DEFAULT NULL COMMENT '店铺Id',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态(1为正常,-1为删除)',
  `is_default` tinyint(1) DEFAULT NULL COMMENT '默认类型(0:商家自定义,1:系统默认)',
  `prod_count` bigint DEFAULT NULL COMMENT '商品数量',
  `style` int DEFAULT NULL COMMENT '列表样式(0:一列一个,1:一列两个,2:一列三个)',
  `seq` int DEFAULT NULL COMMENT '排序',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品分组表';

/*Table structure for table `spu_tag_reference` */

DROP TABLE IF EXISTS `spu_tag_reference`;

CREATE TABLE `spu_tag_reference` (
  `reference_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分组引用id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint DEFAULT NULL COMMENT '店铺id',
  `tag_id` bigint DEFAULT NULL COMMENT '标签id',
  `spu_id` bigint DEFAULT NULL COMMENT '商品id',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态(1:正常,-1:删除)',
  `seq` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`reference_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=367 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品分组标签关联信息';

/*Table structure for table `undo_log` */

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
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
