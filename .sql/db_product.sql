CREATE DATABASE IF NOT EXISTS `db_product`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE `db_product`;

CREATE TABLE `product_category` (
                                    `category_id` bigint NOT NULL COMMENT '分类ID（雪花ID）',
                                    `category_name` varchar(50) NOT NULL COMMENT '分类名称',
                                    `parent_id` bigint DEFAULT NULL COMMENT '父分类ID',
                                    `level` tinyint NOT NULL COMMENT '分类层级（1-一级，2-二级等）',
                                    `sort_order` int DEFAULT '0' COMMENT '排序权重',
                                    `icon` varchar(255) DEFAULT NULL COMMENT '分类图标',
                                    `description` varchar(500) DEFAULT NULL COMMENT '分类描述',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志',
                                    PRIMARY KEY (`category_id`),
                                    KEY `idx_parent_id` (`parent_id`),
                                    KEY `idx_level` (`level`),
                                    KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB COMMENT='产品分类表';

-- 新增品牌表
CREATE TABLE `product_brand` (
                                 `brand_id` bigint NOT NULL COMMENT '品牌ID',
                                 `brand_name` varchar(50) NOT NULL COMMENT '品牌名称',
                                 `logo` varchar(255) DEFAULT NULL COMMENT '品牌logo',
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
CREATE TABLE `product_shop` (
                                `shop_id` bigint NOT NULL COMMENT '店铺ID',
                                `shop_name` varchar(50) NOT NULL COMMENT '店铺名称',
                                `logo` varchar(255) DEFAULT NULL COMMENT '店铺logo',
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
                               `main_image` varchar(255) DEFAULT NULL COMMENT '主图URL',
                               `sub_images` text COMMENT '子图URL（JSON数组）',
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
          `image` varchar(255) DEFAULT NULL COMMENT ''SKU图片'',
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
CREATE PROCEDURE `create_image_sharding_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < table_count DO
            SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `product_image_', i, '` (
          `image_id` bigint NOT NULL COMMENT ''图片ID'',
          `spu_id` bigint NOT NULL COMMENT ''SPU ID'',
          `sku_id` bigint DEFAULT NULL COMMENT ''SKU ID（NULL表示SPU通用图片）'',
          `image_url` varchar(255) NOT NULL COMMENT ''图片URL'',
          `sort_order` int DEFAULT ''0'' COMMENT ''排序'',
          `is_main` tinyint DEFAULT ''0'' COMMENT ''是否主图（1-是，0-否）'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          `del_flag` tinyint NOT NULL DEFAULT ''0'',
          PRIMARY KEY (`image_id`),
          KEY `idx_spu_id_', i, '` (`spu_id`),
          KEY `idx_sku_id_', i, '` (`sku_id`),
          KEY `idx_sort_order_', i, '` (`sort_order`)
        ) ENGINE=InnoDB COMMENT=''产品图片分表', i, '''');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
            SET i = i + 1;
        END WHILE;
END//
DELIMITER ;

CALL `create_image_sharding_tables`(10);
DROP PROCEDURE IF EXISTS `create_image_sharding_tables`;

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
