-- 1. 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS `db_user`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

-- 2. 使用数据库
USE `db_user`;

-- 3. 创建角色表
CREATE TABLE `dict_role` (
                             `role_id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
                             `role_code` varchar(20) NOT NULL COMMENT '角色编码(admin,customer,shopkeeper,clerk等)',
                             `description` varchar(200) DEFAULT NULL COMMENT '角色描述',
                             `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0-未删除，1-已删除）',
                             PRIMARY KEY (`role_id`),
                             UNIQUE KEY `idx_role_code` (`role_code`)
) ENGINE=InnoDB COMMENT='用户角色表';

-- 4. 初始化基础角色数据
INSERT INTO `dict_role` (`role_code`, `description`) VALUES
                                                         ('admin', '系统管理员，拥有所有权限'),
                                                         ('customer', '普通消费者用户'),
                                                         ('shopkeeper', '店铺所有者，可以管理自己店铺'),
                                                         ('clerk', '店铺员工，可以管理自己店铺的部分功能');

-- 5. 创建用户分表的存储过程
DELIMITER //
DROP PROCEDURE IF EXISTS `create_user_sharding_tables`//
CREATE PROCEDURE `create_user_sharding_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE table_name VARCHAR(50);
    DECLARE index_suffix VARCHAR(10);

    -- 循环创建所有分表
    WHILE i < table_count DO
            SET table_name = CONCAT('user_', i);
            SET index_suffix = CONCAT('_', i);

            -- 动态生成建表SQL（包含用户字段）
            SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `', table_name, '` (
          `user_id` bigint NOT NULL COMMENT ''用户ID（雪花ID）'',
          `username` varchar(50) NOT NULL COMMENT ''用户名'',
          `real_name` varchar(50) DEFAULT NULL COMMENT ''真实姓名'',
          `real_name_verify` tinyint NOT NULL DEFAULT ''0'' COMMENT ''验证标志（0-未验证，1-已验证）'',
          `nickname` varchar(50) DEFAULT NULL COMMENT ''昵称'',
          `password` varchar(100) NOT NULL COMMENT ''加密后的密码'',
          `avatar` varchar(255) DEFAULT NULL COMMENT ''头像URL'',
          `birth_date` date DEFAULT NULL COMMENT ''出生日期'',
          `birth_date_verify` tinyint NOT NULL DEFAULT ''0'' COMMENT ''验证标志（0-未验证，1-已验证）'',
          `phone` varchar(20) DEFAULT NULL COMMENT ''手机号'',
          `phone_verify` tinyint NOT NULL DEFAULT ''0'' COMMENT ''验证标志（0-未验证，1-已验证）'',
          `email` varchar(100) DEFAULT NULL COMMENT ''邮箱'',
          `email_verify` tinyint NOT NULL DEFAULT ''0'' COMMENT ''验证标志（0-未验证，1-已验证）'',
          `qq` varchar(15) DEFAULT NULL COMMENT ''QQ号'',
          `qq_verify` tinyint NOT NULL DEFAULT ''0'' COMMENT ''验证标志（0-未验证，1-已验证）'',
          `wechat` varchar(50) DEFAULT NULL COMMENT ''微信号'',
          `wechat_verify` tinyint NOT NULL DEFAULT ''0'' COMMENT ''验证标志（0-未验证，1-已验证）'',
          `gender` tinyint DEFAULT ''0'' COMMENT ''性别（0-未知，1-男，2-女）'',
          `id_card` varchar(18) DEFAULT NULL COMMENT ''身份证号'',
          `id_card_verify` tinyint NOT NULL DEFAULT ''0'' COMMENT ''验证标志（0-未验证，1-已验证）'',
          `role_id` int NOT NULL DEFAULT ''2'' COMMENT ''角色ID(默认customer)'',
          `status` tinyint NOT NULL DEFAULT ''1'' COMMENT ''状态(1-正常,0-禁用)'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ''创建时间'',
          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT ''更新时间'',
          `last_login_time` datetime DEFAULT NULL COMMENT ''上次登录时间'',
          `last_login_ip` varchar(50) DEFAULT NULL COMMENT ''最后登录IP'',
          `last_password_change_time` datetime DEFAULT NULL COMMENT ''上次修改密码时间'',
          `lock_flag` tinyint NOT NULL DEFAULT ''0'' COMMENT ''锁定标志（0-未锁定，1-已锁定）'',
          `del_flag` tinyint NOT NULL DEFAULT ''0'' COMMENT ''删除标志（0-未删除，1-已删除）'',
          PRIMARY KEY (`user_id`),
          UNIQUE KEY `idx_username', index_suffix, '` (`username`),
          KEY `idx_phone', index_suffix, '` (`phone`),
          KEY `idx_email', index_suffix, '` (`email`),
          KEY `idx_nickname', index_suffix, '` (`nickname`),
          KEY `idx_qq', index_suffix, '` (`qq`),
          KEY `idx_wechat', index_suffix, '` (`wechat`),
          KEY `idx_create_time', index_suffix, '` (`create_time`),
          KEY `idx_update_time', index_suffix, '` (`update_time`),
          KEY `idx_birth_date', index_suffix, '` (`birth_date`),
          KEY `idx_role_id', index_suffix, '` (`role_id`),
          KEY `idx_status', index_suffix, '` (`status`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT=''用户分表', i, '''');

            -- 执行建表语句
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;

            -- 输出创建信息（可选）
            SELECT CONCAT('Created table: ', table_name) AS message;

            SET i = i + 1;
        END WHILE;
END//
DELIMITER ;

-- 6. 创建用户-角色关联表的存储过程
DELIMITER //
DROP PROCEDURE IF EXISTS `create_permission_tables`//
CREATE PROCEDURE `create_permission_tables`(IN table_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE table_name VARCHAR(50);
    DECLARE index_suffix VARCHAR(10);

    -- 循环创建所有分表
    WHILE i < table_count DO
            SET table_name = CONCAT('permission_', i);
            SET index_suffix = CONCAT('_', i);

            -- 动态生成建表SQL（用户与角色的关系）
            SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `', table_name, '` (
          `user_id` bigint NOT NULL COMMENT ''用户ID'',
          `role_id` int NOT NULL COMMENT ''角色ID'',
          `tenant_id` bigint COMMENT ''租户ID(店铺ID)'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ''创建时间'',
          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT ''更新时间'',
          `del_flag` tinyint NOT NULL DEFAULT ''0'' COMMENT ''删除标志(0-未删除,1-已删除)'',
          PRIMARY KEY (`user_id`),
          KEY `idx_role_id', index_suffix, '` (`role_id`)
        ) ENGINE=InnoDB COMMENT=''用户-角色关联表', i, '''');

            -- 执行建表语句
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;

            -- 输出创建信息（可选）
            SELECT CONCAT('Created table: ', table_name) AS message;

            SET i = i + 1;
        END WHILE;
END//
DELIMITER ;

-- 7. 执行存储过程创建10张用户分表
CALL `create_user_sharding_tables`(10);

-- 8. 执行存储过程创建10张用户-角色关联表
CALL `create_permission_tables`(10);

-- 9. 删除存储过程（可选）
DROP PROCEDURE IF EXISTS `create_user_sharding_tables`;
DROP PROCEDURE IF EXISTS `create_permission_tables`;

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
