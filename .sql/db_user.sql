-- 1. 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS `db_user`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

-- 2. 使用数据库
USE `db_user`;

-- 3. 创建存储过程：动态生成用户分表
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

            -- 动态生成建表SQL（包含新增字段）
            SET @sql = CONCAT('
        CREATE TABLE IF NOT EXISTS `', table_name, '` (
          `user_id` bigint NOT NULL COMMENT ''用户ID（雪花ID）'',
          `username` varchar(50) NOT NULL COMMENT ''用户名'',
          `real_name` varchar(50) DEFAULT NULL COMMENT ''真实姓名'',
          `nickname` varchar(50) DEFAULT NULL COMMENT ''昵称'',
          `password` varchar(100) NOT NULL COMMENT ''加密后的密码'',
          `salt` varchar(50) NOT NULL COMMENT ''加密盐值'',
          `avatar` varchar(255) DEFAULT NULL COMMENT ''头像URL'',
          `birth_date` date DEFAULT NULL COMMENT ''出生日期'',
          `phone` varchar(20) DEFAULT NULL COMMENT ''手机号'',
          `email` varchar(100) DEFAULT NULL COMMENT ''邮箱'',
          `qq` varchar(15) DEFAULT NULL COMMENT ''QQ号'',
          `wechat` varchar(50) DEFAULT NULL COMMENT ''微信号'',
          `gender` tinyint DEFAULT ''0'' COMMENT ''性别（0-未知，1-男，2-女）'',
          `id_card` varchar(18) DEFAULT NULL COMMENT ''身份证号'',
          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ''创建时间'',
          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT ''更新时间'',
          `last_login_time` datetime DEFAULT NULL COMMENT ''上次登录时间'',
          `last_login_ip` varchar(50) DEFAULT NULL COMMENT ''最后登录IP'',
          `last_password_change_time` datetime DEFAULT NULL COMMENT ''上次修改密码时间'',
          `lock_flag` tinyint NOT NULL DEFAULT ''0'' COMMENT ''锁定标志（0-未锁定，1-已锁定）'',
          `del_flag` tinyint NOT NULL DEFAULT ''0'' COMMENT ''删除标志（0-未删除，1-已删除）'',
          PRIMARY KEY (`user_id`),
          UNIQUE KEY `idx_username', index_suffix, '` (`username`),
          UNIQUE KEY `idx_id_card', index_suffix, '` (`id_card`),
          KEY `idx_phone', index_suffix, '` (`phone`),
          KEY `idx_email', index_suffix, '` (`email`),
          KEY `idx_nickname', index_suffix, '` (`nickname`),
          KEY `idx_qq', index_suffix, '` (`qq`),
          KEY `idx_wechat', index_suffix, '` (`wechat`),
          KEY `idx_create_time', index_suffix, '` (`create_time`),
          KEY `idx_update_time', index_suffix, '` (`update_time`),
          KEY `idx_birth_date', index_suffix, '` (`birth_date`)
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

-- 4. 执行存储过程创建10张分表
CALL `create_user_sharding_tables`(10);

-- 5. 删除存储过程（可选）
DROP PROCEDURE IF EXISTS `create_user_sharding_tables`;

