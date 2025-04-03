CREATE DATABASE IF NOT EXISTS `db_pay`
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;

USE `db_pay`;

DELIMITER //
CREATE PROCEDURE `create_payment_info_sharding_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < table_count DO
        SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `payment_info_', i, '` (
          `payment_id` bigint NOT NULL COMMENT ''支付ID'',
          `order_id` bigint NOT NULL COMMENT ''订单ID'',
          `order_sn` varchar(32) NOT NULL COMMENT ''订单编号'',
          `payment_sn` varchar(50) NOT NULL COMMENT ''支付流水号'',
          `user_id` bigint NOT NULL COMMENT ''用户ID'',
          `payment_type` tinyint NOT NULL COMMENT ''支付方式(1-支付宝,2-微信,3-银联)'',
          `total_amount` decimal(10,2) NOT NULL COMMENT ''支付金额'',
          `payment_status` tinyint NOT NULL DEFAULT ''0'' COMMENT ''支付状态(0-未支付,1-支付成功,2-支付失败,3-已退款)'',
          `payment_time` datetime DEFAULT NULL COMMENT ''支付时间'',
          `callback_time` datetime DEFAULT NULL COMMENT ''回调时间'',
          `callback_content` text COMMENT ''回调内容'',
          `transaction_id` varchar(100) DEFAULT NULL COMMENT ''第三方交易号'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          PRIMARY KEY (`payment_id`),
          UNIQUE KEY `idx_payment_sn_', i, '` (`payment_sn`),
          KEY `idx_order_id_', i, '` (`order_id`),
          KEY `idx_order_sn_', i, '` (`order_sn`),
          KEY `idx_user_id_', i, '` (`user_id`)
        ) ENGINE=InnoDB COMMENT=''支付信息表分表', i, '''');
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        SET i = i + 1;
    END WHILE;
END//
DELIMITER ;

CALL `create_payment_info_sharding_tables`(10);
DROP PROCEDURE IF EXISTS `create_payment_info_sharding_tables`;

DELIMITER //
CREATE PROCEDURE `create_refund_info_sharding_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < table_count DO
        SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `refund_info_', i, '` (
          `refund_id` bigint NOT NULL COMMENT ''退款ID'',
          `order_id` bigint NOT NULL COMMENT ''订单ID'',
          `order_sn` varchar(32) NOT NULL COMMENT ''订单编号'',
          `payment_id` bigint NOT NULL COMMENT ''支付ID'',
          `payment_sn` varchar(50) DEFAULT NULL COMMENT ''支付流水号'',
          `user_id` bigint NOT NULL COMMENT ''用户ID'',
          `refund_sn` varchar(50) NOT NULL COMMENT ''退款流水号'',
          `refund_amount` decimal(10,2) NOT NULL COMMENT ''退款金额'',
          `refund_type` tinyint NOT NULL COMMENT ''退款方式(1-原路退回,2-线下退款)'',
          `refund_status` tinyint NOT NULL DEFAULT ''0'' COMMENT ''退款状态(0-申请中,1-退款中,2-退款成功,3-退款失败)'',
          `refund_reason` varchar(200) DEFAULT NULL COMMENT ''退款原因'',
          `refund_time` datetime DEFAULT NULL COMMENT ''退款时间'',
          `callback_time` datetime DEFAULT NULL COMMENT ''回调时间'',
          `callback_content` text COMMENT ''回调内容'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          PRIMARY KEY (`refund_id`),
          UNIQUE KEY `idx_refund_sn_', i, '` (`refund_sn`),
          KEY `idx_order_id_', i, '` (`order_id`),
          KEY `idx_order_sn_', i, '` (`order_sn`),
          KEY `idx_user_id_', i, '` (`user_id`)
        ) ENGINE=InnoDB COMMENT=''退款信息表分表', i, '''');
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        SET i = i + 1;
    END WHILE;
END//
DELIMITER ;

CALL `create_refund_info_sharding_tables`(10);
DROP PROCEDURE IF EXISTS `create_refund_info_sharding_tables`;