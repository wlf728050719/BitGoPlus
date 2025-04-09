package cn.bit.quartz.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 定时任务实体类
 */
@Data
@Accessors(chain = true)
@TableName(value = "task", autoResultMap = true)
public class Task {
    /** 任务ID */
    @TableId(value = "task_id")
    private Long taskId;

    /** 任务名称 */
    @TableField("task_name")
    private String taskName;

    /** 任务分组 */
    @TableField("task_group")
    private String taskGroup;

    /** 任务类型：1-Java类，2-Spring Bean，3-HTTP请求 */
    @TableField("type")
    private Integer type;

    /** Spring Bean名称 */
    @TableField("bean_name")
    private String beanName;

    /** Java类名 */
    @TableField("class_name")
    private String className;

    /** REST请求路径 */
    @TableField("path")
    private String path;

    /** 方法名称 */
    @TableField("method_name")
    private String methodName;

    /** 方法参数 */
    @TableField("params")
    private String params;

    /** Cron表达式 */
    @TableField("cron_expression")
    private String cronExpression;

    /** 任务描述 */
    @TableField("description")
    private String description;

    /** 任务当前状态：0-未执行，1-执行中，2-已完成，3-已失败 */
    @TableField("status")
    private Integer status;

    /** 任务执行结果：0-失败，1-成功 */
    @TableField("result")
    private Integer result;

    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
