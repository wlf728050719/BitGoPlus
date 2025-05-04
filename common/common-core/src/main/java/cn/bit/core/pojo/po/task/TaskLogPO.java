package cn.bit.core.pojo.po.task;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 任务执行日志实体类
 */
@Data
@Accessors(chain = true)
@TableName(value = "task_log", autoResultMap = true)
public class TaskLogPO {
    /** 日志ID */
    @TableId(value = "log_id")
    private Long logId;

    /** 关联的任务ID */
    @TableField("task_id")
    private Long taskId;

    /** 任务开始执行时间 */
    @TableField("start_time")
    private LocalDateTime startTime;

    /** 任务执行耗时(毫秒) */
    @TableField("execute_time")
    private Long executeTime;

    /** 执行结果：0-失败，1-成功 */
    @TableField("result")
    private Integer result;

    /** 日志信息 */
    @TableField("message")
    private String message;

    /** 异常堆栈信息 */
    @TableField("exception_info")
    private String exceptionInfo;

    /** 日志创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
