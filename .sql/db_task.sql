create database db_task;
use db_task;

CREATE TABLE task
(
    task_id         BIGINT PRIMARY KEY COMMENT '任务ID',
    task_name       VARCHAR(255)  NOT NULL COMMENT '任务名称',
    task_group      VARCHAR(255)  NOT NULL COMMENT '任务分组',
    type            TINYINT       NOT NULL COMMENT '任务类型：1-Java类，2-Spring Bean，3-HTTP请求',
    bean_name       VARCHAR(255)  NULL COMMENT 'Spring Bean名称',
    class_name      VARCHAR(255)  NULL COMMENT 'Java类名',
    path            VARCHAR(255)  NULL COMMENT 'REST请求路径',
    method_name     VARCHAR(255)  NULL COMMENT '方法名称',
    params          VARCHAR(255)  NULL COMMENT '方法参数',
    cron_expression VARCHAR(255)  NOT NULL COMMENT 'Cron表达式',
    description     TEXT          NULL COMMENT '任务描述',
    status          INT DEFAULT 0 NOT NULL COMMENT '任务当前状态：0-未执行，1-执行中，2-已完成，3-已失败',
    result          TINYINT       NULL COMMENT '任务执行结果：0-失败，1-成功',
    create_time     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '定时任务信息表';

CREATE TABLE task_log
(
    log_id          BIGINT PRIMARY KEY COMMENT '日志ID',
    task_id         BIGINT        NOT NULL COMMENT '关联的任务ID',
    start_time      DATETIME      NOT NULL COMMENT '任务开始执行时间',
    execute_time    BIGINT        NOT NULL COMMENT '任务执行耗时(毫秒)',
    result          TINYINT       NOT NULL COMMENT '执行结果：0-失败，1-成功',
    message         VARCHAR(500)  NULL COMMENT '日志信息',
    exception_info  TEXT          NULL COMMENT '异常堆栈信息',
    create_time     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志创建时间'
) COMMENT '任务执行日志表';

-- 添加索引建议
ALTER TABLE task_log ADD INDEX idx_task_id (task_id) COMMENT '任务ID索引';
ALTER TABLE task_log ADD INDEX idx_start_time (start_time) COMMENT '执行时间索引';