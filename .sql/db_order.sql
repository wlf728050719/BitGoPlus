CREATE DATABASE IF NOT EXISTS `db_order`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE `db_order`;

DELIMITER //
CREATE PROCEDURE `create_order_master_sharding_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < table_count DO
            SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `order_master_', i, '` (
          `order_id` bigint NOT NULL COMMENT ''订单ID(雪花ID)'',
          `order_sn` varchar(32) NOT NULL COMMENT ''订单编号'',
          `user_id` bigint NOT NULL COMMENT ''用户ID'',
          `order_status` tinyint NOT NULL DEFAULT ''0'' COMMENT ''订单状态(0-待支付,1-已支付待发货,2-已发货,3-已完成,4-已取消,5-已退款)'',
          `total_amount` decimal(10,2) NOT NULL COMMENT ''订单总金额'',
          `pay_amount` decimal(10,2) NOT NULL COMMENT ''实付金额'',
          `freight_amount` decimal(10,2) DEFAULT ''0'' COMMENT ''运费金额'',
          `discount_amount` decimal(10,2) DEFAULT ''0'' COMMENT ''优惠金额'',
          `pay_type` tinyint DEFAULT NULL COMMENT ''支付方式(1-支付宝,2-微信,3-银联)'',
          `pay_time` datetime DEFAULT NULL COMMENT ''支付时间'',
          `delivery_time` datetime DEFAULT NULL COMMENT ''发货时间'',
          `receive_time` datetime DEFAULT NULL COMMENT ''收货时间'',
          `close_time` datetime DEFAULT NULL COMMENT ''关闭时间'',
          `source_type` tinyint DEFAULT ''1'' COMMENT ''订单来源(1-PC,2-APP,3-小程序,4-H5)'',
          `delivery_company` varchar(50) DEFAULT NULL COMMENT ''物流公司'',
          `delivery_sn` varchar(50) DEFAULT NULL COMMENT ''物流单号'',
          `receiver_name` varchar(50) NOT NULL COMMENT ''收货人姓名'',
          `receiver_phone` varchar(20) NOT NULL COMMENT ''收货人电话'',
          `receiver_province` varchar(20) DEFAULT NULL COMMENT ''省份'',
          `receiver_city` varchar(20) DEFAULT NULL COMMENT ''城市'',
          `receiver_region` varchar(20) DEFAULT NULL COMMENT ''区县'',
          `receiver_detail_address` varchar(200) DEFAULT NULL COMMENT ''详细地址'',
          `note` varchar(500) DEFAULT NULL COMMENT ''订单备注'',
          `confirm_status` tinyint DEFAULT ''0'' COMMENT ''确认收货状态(0-未确认,1-已确认)'',
          `delete_status` tinyint NOT NULL DEFAULT ''0'' COMMENT ''删除状态(0-未删除,1-已删除)'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          PRIMARY KEY (`order_id`),
          UNIQUE KEY `idx_order_sn_', i, '` (`order_sn`),
          KEY `idx_user_id_', i, '` (`user_id`),
          KEY `idx_create_time_', i, '` (`create_time`),
          KEY `idx_order_status_', i, '` (`order_status`)
        ) ENGINE=InnoDB COMMENT=''订单主表分表', i, '''');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
            SET i = i + 1;
        END WHILE;
END//
DELIMITER ;

CALL `create_order_master_sharding_tables`(10);
DROP PROCEDURE IF EXISTS `create_order_master_sharding_tables`;

DELIMITER //
CREATE PROCEDURE `create_order_item_sharding_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < table_count DO
            SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `order_item_', i, '` (
          `item_id` bigint NOT NULL COMMENT ''订单商品ID'',
          `order_id` bigint NOT NULL COMMENT ''订单ID'',
          `order_sn` varchar(32) NOT NULL COMMENT ''订单编号'',
          `product_id` bigint NOT NULL COMMENT ''商品SPU ID'',
          `product_sn` varchar(50) NOT NULL COMMENT ''商品SPU编码'',
          `product_name` varchar(100) NOT NULL COMMENT ''商品名称'',
          `product_brand` varchar(50) DEFAULT NULL COMMENT ''商品品牌'',
          `product_category_id` bigint DEFAULT NULL COMMENT ''商品分类ID'',
          `sku_id` bigint NOT NULL COMMENT ''商品SKU ID'',
          `sku_code` varchar(50) NOT NULL COMMENT ''SKU编码'',
          `spec_values` text COMMENT ''商品规格(JSON格式)'',
          `product_attr` text COMMENT ''商品属性(JSON格式)'',
          `product_image` varchar(255) DEFAULT NULL COMMENT ''商品图片'',
          `price` decimal(10,2) NOT NULL COMMENT ''销售价格'',
          `quantity` int NOT NULL COMMENT ''购买数量'',
          `total_amount` decimal(10,2) NOT NULL COMMENT ''商品总金额'',
          `discount_amount` decimal(10,2) DEFAULT ''0'' COMMENT ''优惠金额'',
          `real_amount` decimal(10,2) NOT NULL COMMENT ''实付金额'',
          `refund_status` tinyint DEFAULT ''0'' COMMENT ''退款状态(0-未退款,1-退款中,2-已退款)'',
          `refund_amount` decimal(10,2) DEFAULT NULL COMMENT ''退款金额'',
          `refund_time` datetime DEFAULT NULL COMMENT ''退款时间'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
          PRIMARY KEY (`item_id`),
          KEY `idx_order_id_', i, '` (`order_id`),
          KEY `idx_order_sn_', i, '` (`order_sn`),
          KEY `idx_product_id_', i, '` (`product_id`),
          KEY `idx_sku_id_', i, '` (`sku_id`)
        ) ENGINE=InnoDB COMMENT=''订单商品表分表', i, '''');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
            SET i = i + 1;
        END WHILE;
END//
DELIMITER ;

CALL `create_order_item_sharding_tables`(10);
DROP PROCEDURE IF EXISTS `create_order_item_sharding_tables`;

DELIMITER //
CREATE PROCEDURE `create_order_operation_log_sharding_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < table_count DO
            SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `order_operation_log_', i, '` (
          `log_id` bigint NOT NULL COMMENT ''日志ID'',
          `order_id` bigint NOT NULL COMMENT ''订单ID'',
          `order_sn` varchar(32) NOT NULL COMMENT ''订单编号'',
          `operator` varchar(50) NOT NULL COMMENT ''操作人(用户ID/系统/管理员ID)'',
          `operation_type` tinyint NOT NULL COMMENT ''操作类型(1-创建订单,2-支付,3-发货,4-确认收货,5-取消,6-退款,7-修改订单)'',
          `operation_desc` varchar(200) DEFAULT NULL COMMENT ''操作描述'',
          `operation_params` text COMMENT ''操作参数(JSON格式)'',
          `operation_status` tinyint DEFAULT ''1'' COMMENT ''操作状态(0-失败,1-成功)'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
          PRIMARY KEY (`log_id`),
          KEY `idx_order_id_', i, '` (`order_id`),
          KEY `idx_order_sn_', i, '` (`order_sn`),
          KEY `idx_create_time_', i, '` (`create_time`)
        ) ENGINE=InnoDB COMMENT=''订单操作历史表分表', i, '''');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
            SET i = i + 1;
        END WHILE;
END//
DELIMITER ;

CALL `create_order_operation_log_sharding_tables`(10);
DROP PROCEDURE IF EXISTS `create_order_operation_log_sharding_tables`;
