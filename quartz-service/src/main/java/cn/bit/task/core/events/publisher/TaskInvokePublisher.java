package cn.bit.task.core.events.publisher;

import cn.bit.pojo.po.TaskPO;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import cn.bit.task.core.events.event.TaskInvokeEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Component
public class TaskInvokePublisher implements Job {

    private final ApplicationEventPublisher publisher;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        TaskPO taskPO = (TaskPO) jobExecutionContext.getJobDetail().getJobDataMap().get("task");
        // 发布事件异步执行任务
        TaskInvokeEvent event = new TaskInvokeEvent(taskPO.getTaskName(), taskPO.getTaskGroup());
        publisher.publishEvent(event);
        log.info("任务执行事件发布:{}", event);
    }
}
