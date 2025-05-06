CREATE DATABASE IF NOT EXISTS `db_product`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE `db_product`;

CREATE TABLE `dict_product_category` (
                                         `category_id` bigint NOT NULL COMMENT '分类ID',
                                         `category_name` varchar(50) NOT NULL COMMENT '分类名称',
                                         `parent_id` bigint DEFAULT NULL COMMENT '父分类ID',
                                         `level` tinyint NOT NULL COMMENT '分类层级（1-一级，2-二级等）',
                                         `sort_order` int DEFAULT '0' COMMENT '排序权重',
                                         `icon_url` varchar(255) DEFAULT NULL COMMENT '分类图标地址',
                                         `description` varchar(500) DEFAULT NULL COMMENT '分类描述',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                         `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志',
                                         PRIMARY KEY (`category_id`),
                                         KEY `idx_parent_id` (`parent_id`),
                                         KEY `idx_level` (`level`),
                                         KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB COMMENT='产品分类表';

-- 插入一级分类
INSERT INTO `dict_product_category` (`category_id`, `category_name`, `parent_id`, `level`, `sort_order`, `icon_url`, `description`)
VALUES
    (101, '电子产品', NULL, 1, 10, '', '各类电子设备和配件'),
    (102, '服装服饰', NULL, 1, 20, '', '男女服装、鞋帽配饰'),
    (103, '家居用品', NULL, 1, 30, '', '家具家居家纺用品'),
    (104, '食品饮料', NULL, 1, 40, '', '各类食品和饮料');

-- 插入电子产品的二级分类
INSERT INTO `dict_product_category` (`category_id`, `category_name`, `parent_id`, `level`, `sort_order`, `icon_url`, `description`)
VALUES
    (101001, '智能手机', 101, 2, 11, '', '各类品牌智能手机'),
    (101002, '笔记本电脑', 101, 2, 12, '', '商务本、游戏本等'),
    (101003, '数码相机', 101, 2, 13, '', '单反、微单、卡片机'),
    (101004, '耳机音响', 101, 2, 14, '', '蓝牙耳机、音响设备');

-- 插入服装服饰的二级分类
INSERT INTO `dict_product_category` (`category_id`, `category_name`, `parent_id`, `level`, `sort_order`, `icon_url`, `description`)
VALUES
    (102001, '男装', 102, 2, 21, '', '男士上衣、裤子等'),
    (102002, '女装', 102, 2, 22, '', '女士上衣、裙子等'),
    (102003, '童装', 102, 2, 23, '', '儿童服装'),
    (102004, '运动服饰', 102, 2, 24, '', '运动服装和配件');

-- 插入家居用品的二级分类
INSERT INTO `dict_product_category` (`category_id`, `category_name`, `parent_id`, `level`, `sort_order`, `icon_url`, `description`)
VALUES
    (103001, '家具', 103, 2, 31, '', '沙发、床、桌椅等'),
    (103002, '家纺', 103, 2, 32, '', '床上用品、窗帘等'),
    (103003, '厨房用品', 103, 2, 33, '', '厨具餐具等'),
    (103004, '装饰品', 103, 2, 34, '', '家居装饰摆件');

-- 插入食品饮料的二级分类
INSERT INTO `dict_product_category` (`category_id`, `category_name`, `parent_id`, `level`, `sort_order`, `icon_url`, `description`)
VALUES
    (104001, '休闲零食', 104, 2, 41, '', '饼干、糖果、坚果等'),
    (104002, '酒水饮料', 104, 2, 42, '', '各类酒水和饮料'),
    (104003, '生鲜食品', 104, 2, 43, '', '蔬菜水果肉类等'),
    (104004, '粮油调味', 104, 2, 44, '', '米面油和调味品');

-- 插入智能手机的三级分类示例
INSERT INTO `dict_product_category` (`category_id`, `category_name`, `parent_id`, `level`, `sort_order`, `icon_url`, `description`)
VALUES
    (101001000001, '苹果手机', 101001, 3, 111, '', 'iPhone系列'),
    (101001000002, '华为手机', 101001, 3, 112, '', '华为系列手机'),
    (101001000003, '小米手机', 101001, 3, 113, '', '小米系列手机'),
    (101001000004, '三星手机', 101001, 3, 114, '', '三星系列手机');
-- 新增品牌表
CREATE TABLE `product_brand` (
                                 `brand_id` bigint NOT NULL COMMENT '品牌ID',
                                 `brand_name` varchar(50) NOT NULL COMMENT '品牌名称',
                                 `logo_url` varchar(255) DEFAULT NULL COMMENT '品牌logo地址',
                                 `description` varchar(500) DEFAULT NULL COMMENT '品牌描述',
                                 `website` varchar(100) DEFAULT NULL COMMENT '品牌官网',
                                 `sort_order` int DEFAULT '0' COMMENT '排序',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 `del_flag` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`brand_id`),
                                 KEY `idx_brand_name` (`brand_name`)
) ENGINE=InnoDB COMMENT='品牌表';

-- 修改为店铺表
CREATE TABLE `shop` (
                        `shop_id` bigint NOT NULL COMMENT '店铺ID',
                        `shop_name` varchar(50) NOT NULL COMMENT '店铺名称',
                        `logo_url` varchar(255) DEFAULT NULL COMMENT '店铺logo地址',
                        `description` varchar(500) DEFAULT NULL COMMENT '店铺描述',
                        `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
                        `address` varchar(200) DEFAULT NULL COMMENT '店铺地址',
                        `business_license` varchar(100) DEFAULT NULL COMMENT '营业执照号',
                        `sort_order` int DEFAULT '0' COMMENT '排序',
                        `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（1-正常，0-禁用）',
                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `del_flag` tinyint NOT NULL DEFAULT '0',
                        PRIMARY KEY (`shop_id`),
                        KEY `idx_shop_name` (`shop_name`),
                        KEY `idx_status` (`status`)
) ENGINE=InnoDB COMMENT='店铺表';

CREATE TABLE `product_spu` (
                               `spu_id` bigint NOT NULL COMMENT 'SPU ID（雪花ID）',
                               `spu_code` varchar(50) NOT NULL COMMENT 'SPU编码',
                               `spu_name` varchar(100) NOT NULL COMMENT '产品名称',
                               `category_id` bigint NOT NULL COMMENT '分类ID',
                               `brand_id` bigint DEFAULT NULL COMMENT '品牌ID',
                               `shop_id` bigint DEFAULT NULL COMMENT '店铺ID',
                               `main_image_url` varchar(255) DEFAULT NULL COMMENT '主图URL',
                               `sub_images_url` text COMMENT '子图URL（JSON数组）',
                               `description` text COMMENT '产品描述',
                               `spec_template` text COMMENT '规格模板（JSON格式）',
                               `sales` int DEFAULT '0' COMMENT '总销量',
                               `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（1-上架，0-下架）',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               `del_flag` tinyint NOT NULL DEFAULT '0',
                               PRIMARY KEY (`spu_id`),
                               UNIQUE KEY `idx_spu_code` (`spu_code`),
                               KEY `idx_category` (`category_id`),
                               KEY `idx_brand` (`brand_id`),
                               KEY `idx_shop` (`shop_id`),
                               KEY `idx_status` (`status`),
                               KEY `idx_sales` (`sales`)
) ENGINE=InnoDB COMMENT='产品SPU表';

DELIMITER //
CREATE PROCEDURE `create_sku_sharding_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < table_count DO
            SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `product_sku_', i, '` (
          `sku_id` bigint NOT NULL COMMENT ''SKU ID（雪花ID）'',
          `spu_id` bigint NOT NULL COMMENT ''SPU ID'',
          `sku_code` varchar(50) NOT NULL COMMENT ''SKU编码'',
          `spec_values` text COMMENT ''规格值（JSON格式）'',
          `price` decimal(10,2) NOT NULL COMMENT ''售价'',
          `cost_price` decimal(10,2) DEFAULT NULL COMMENT ''成本价'',
          `market_price` decimal(10,2) DEFAULT NULL COMMENT ''市场价'',
          `stock` int NOT NULL DEFAULT ''0'' COMMENT ''库存'',
          `warn_stock` int DEFAULT ''0'' COMMENT ''预警库存'',
          `image_url` varchar(255) DEFAULT NULL COMMENT ''SKU图片地址'',
          `weight` decimal(10,2) DEFAULT NULL COMMENT ''重量（kg）'',
          `volume` decimal(10,2) DEFAULT NULL COMMENT ''体积（m³）'',
          `status` tinyint NOT NULL DEFAULT ''1'' COMMENT ''状态（1-启用，0-禁用）'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          `del_flag` tinyint NOT NULL DEFAULT ''0'',
          PRIMARY KEY (`sku_id`),
          UNIQUE KEY `idx_sku_code_', i, '` (`sku_code`),
          KEY `idx_spu_id_', i, '` (`spu_id`),
          KEY `idx_price_', i, '` (`price`),
          KEY `idx_stock_', i, '` (`stock`),
          KEY `idx_status_', i, '` (`status`)
        ) ENGINE=InnoDB COMMENT=''产品SKU分表', i, '''');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
            SET i = i + 1;
        END WHILE;
END//
DELIMITER ;

CALL `create_sku_sharding_tables`(10);
DROP PROCEDURE IF EXISTS `create_sku_sharding_tables`;

DELIMITER //
CREATE PROCEDURE `create_attr_sharding_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < table_count DO
            SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `product_attribute_', i, '` (
          `attr_id` bigint NOT NULL COMMENT ''属性ID'',
          `spu_id` bigint NOT NULL COMMENT ''SPU ID'',
          `attr_name` varchar(50) NOT NULL COMMENT ''属性名'',
          `attr_value` varchar(500) NOT NULL COMMENT ''属性值'',
          `attr_type` tinyint NOT NULL COMMENT ''属性类型（1-关键属性，2-销售属性，3-普通属性）'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          `del_flag` tinyint NOT NULL DEFAULT ''0'',
          PRIMARY KEY (`attr_id`),
          KEY `idx_spu_id_', i, '` (`spu_id`),
          KEY `idx_attr_type_', i, '` (`attr_type`)
        ) ENGINE=InnoDB COMMENT=''产品属性分表', i, '''');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
            SET i = i + 1;
        END WHILE;
END//
DELIMITER ;

CALL `create_attr_sharding_tables`(10);
DROP PROCEDURE IF EXISTS `create_attr_sharding_tables`;

DELIMITER //
CREATE PROCEDURE `create_inventory_log_sharding_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < table_count DO
            SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `inventory_log_', i, '` (
          `log_id` bigint NOT NULL COMMENT ''日志ID'',
          `sku_id` bigint NOT NULL COMMENT ''SKU ID'',
          `change_amount` int NOT NULL COMMENT ''变更数量（正数增加，负数减少）'',
          `current_stock` int NOT NULL COMMENT ''变更后库存'',
          `order_id` varchar(50) DEFAULT NULL COMMENT ''关联订单号'',
          `operation_type` tinyint NOT NULL COMMENT ''操作类型（1-入库，2-出库，3-调整）'',
          `operator` varchar(50) DEFAULT NULL COMMENT ''操作人'',
          `remark` varchar(200) DEFAULT NULL COMMENT ''备注'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
          PRIMARY KEY (`log_id`),
          KEY `idx_sku_id_', i, '` (`sku_id`),
          KEY `idx_order_id_', i, '` (`order_id`),
          KEY `idx_create_time_', i, '` (`create_time`)
        ) ENGINE=InnoDB COMMENT=''库存变更记录分表', i, '''');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
            SET i = i + 1;
        END WHILE;
END//
DELIMITER ;

CALL `create_inventory_log_sharding_tables`(10);
DROP PROCEDURE IF EXISTS `create_inventory_log_sharding_tables`;

-- seata_demo.undo_log definition

CREATE TABLE `undo_log` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `branch_id` bigint(20) NOT NULL,
                            `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `rollback_info` longblob NOT NULL,
                            `log_status` int(11) NOT NULL,
                            `log_created` datetime NOT NULL,
                            `log_modified` datetime NOT NULL,
                            `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
