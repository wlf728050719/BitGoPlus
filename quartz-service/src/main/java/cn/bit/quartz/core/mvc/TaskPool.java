package cn.bit.quartz.core.mvc;

import cn.bit.quartz.core.entity.Task;
import cn.bit.quartz.core.events.publisher.TaskInvokePublisher;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TaskPool {

    public static JobKey getJobKey(@NonNull Task task) {
        return JobKey.jobKey(task.getTaskName(),task.getTaskGroup());
    }
    public static TriggerKey getTriggerKey(@NonNull Task task) {
        return TriggerKey.triggerKey(task.getTaskName(),task.getTaskGroup());
    }

    /**
     * 任务池添加任务
     * @param task
     * @param scheduler
     * @throws SchedulerException
     */
    public void addTask(@NonNull Task task,Scheduler scheduler) throws SchedulerException {
        JobKey jobKey = getJobKey(task);
        TriggerKey triggerKey = getTriggerKey(task);
        JobDetail jobDetail = JobBuilder.newJob(TaskInvokePublisher.class).withIdentity(jobKey).build();
        jobDetail.getJobDataMap().put("task",task);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder)
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 任务池暂停并移除任务
     * @param task
     * @param scheduler
     * @throws SchedulerException
     */
    public void pauseTask(@NonNull Task task,Scheduler scheduler) throws SchedulerException {
        scheduler.pauseJob(getJobKey(task));
        scheduler.deleteJob(getJobKey(task));
    }

}
