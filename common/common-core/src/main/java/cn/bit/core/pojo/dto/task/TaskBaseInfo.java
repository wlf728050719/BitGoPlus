package cn.bit.core.pojo.dto.task;

import cn.bit.core.pojo.po.task.TaskPO;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * <p>任务基本信息</p>
 * Date:2025/05/07 20:28:22
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class TaskBaseInfo {
    /** 任务名称 */
    @NotNull
    private String taskName;

    /** 任务分组 */
    @NotNull
    private String taskGroup;

    /** 任务类型：1-Java类，2-Spring Bean，3-HTTP请求 */
    @Min(1)
    @Max(3)
    private Integer type;

    /** Spring Bean名称 */
    private String beanName;

    /** Java类名 */
    private String className;

    /** REST请求路径 */
    private String path;

    /** 方法名称 */
    private String methodName;

    /** 方法参数 */
    private String params;

    /** Cron表达式 */
    private String cronExpression;

    /** 任务描述 */
    private String description;

    /** 任务当前状态：0-未执行，1-执行中 */
    @Min(0)
    @Max(1)
    private Integer status;

    public TaskPO newTaskPO() {
        TaskPO taskPO = new TaskPO();
        taskPO.setTaskName(taskName);
        taskPO.setTaskGroup(taskGroup);
        taskPO.setType(type);
        taskPO.setBeanName(beanName);
        taskPO.setClassName(className);
        taskPO.setPath(path);
        taskPO.setMethodName(methodName);
        taskPO.setParams(params);
        taskPO.setCronExpression(cronExpression);
        taskPO.setDescription(description);
        taskPO.setStatus(status);
        return taskPO;
    }
    public TaskBaseInfo(TaskPO taskPO) {
        this.taskName = taskPO.getTaskName();
        this.taskGroup = taskPO.getTaskGroup();
        this.type = taskPO.getType();
        this.beanName = taskPO.getBeanName();
        this.className = taskPO.getClassName();
        this.path = taskPO.getPath();
        this.methodName = taskPO.getMethodName();
        this.params = taskPO.getParams();
        this.cronExpression = taskPO.getCronExpression();
        this.description = taskPO.getDescription();
        this.status = taskPO.getStatus();
    }
}
