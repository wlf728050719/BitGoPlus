package cn.bit.quartz.core.entity;

import lombok.Data;

@Data
public class Task {
    private Integer id;
    private String taskName;
    private String taskGroup;
    private Integer type;//1、java类 2、Spring Bean 3、http请求
    private String beanName;//bean名称
    private String className;//java类名
    private String path;//rest请求路径
    private String methodName;//方法名
    private String params;//方法参数
    private String cronExpression;//cron表达式
    private String description;//描述
    private Integer status;//任务当前状态
    private Integer result;//任务执行结果
}
