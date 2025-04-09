package cn.bit.quartz.core.entity;

import java.util.Date;

import lombok.Data;

@Data
public class TaskLog {
    private Integer id;
    private Integer taskId;
    private Date startTime;
    private String executeTime;
    private Integer result; // 0失败 1成功
    private String message; // 日志信息
    private String exceptionInfo; // 异常信息
}
