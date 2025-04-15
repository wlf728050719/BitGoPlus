package cn.bit.taskservice.core.service.impl;

import cn.bit.exception.SysException;
import cn.bit.pojo.po.TaskPO;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

import cn.bit.taskservice.core.events.publisher.TaskInvokePublisher;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class TaskPool implements cn.bit.taskservice.core.service.TaskPool {
    private final Scheduler scheduler;

    public static JobKey getJobKey(@NonNull TaskPO taskPO) {
        return JobKey.jobKey(taskPO.getTaskName(), taskPO.getTaskGroup());
    }

    public static TriggerKey getTriggerKey(@NonNull TaskPO taskPO) {
        return TriggerKey.triggerKey(taskPO.getTaskName(), taskPO.getTaskGroup());
    }

    /**
     * 任务池添加任务
     */
    @Override
    public void addTask(@NonNull TaskPO taskPO) throws SysException {
        JobKey jobKey = getJobKey(taskPO);
        TriggerKey triggerKey = getTriggerKey(taskPO);
        JobDetail jobDetail = JobBuilder.newJob(TaskInvokePublisher.class).withIdentity(jobKey).build();
        jobDetail.getJobDataMap().put("task", taskPO);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(taskPO.getCronExpression());
        CronTrigger trigger =
            TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new SysException("添加任务错误", e);
        }
    }

    /**
     * 任务池暂停并移除任务
     */
    @Override
    public void pauseTask(@NonNull TaskPO taskPO) throws SysException {
        try {
            scheduler.pauseJob(getJobKey(taskPO));
            scheduler.deleteJob(getJobKey(taskPO));
        } catch (SchedulerException e) {
            throw new SysException("暂停任务错误", e);
        }
    }

}
