package cn.bit.pojo.dto;

import cn.bit.pojo.po.TaskPO;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
}
