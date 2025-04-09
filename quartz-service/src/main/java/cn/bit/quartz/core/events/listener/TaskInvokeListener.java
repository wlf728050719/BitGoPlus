package cn.bit.quartz.core.events.listener;

import java.util.Date;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.bit.quartz.core.entity.Task;
import cn.bit.quartz.core.entity.TaskLog;
import cn.bit.quartz.core.enums.Result;
import cn.bit.quartz.core.events.event.TaskInvokeEvent;
import cn.bit.quartz.core.exception.TaskInvokeException;
import cn.bit.quartz.core.handler.ITaskHandler;
import cn.bit.quartz.core.handler.TaskHandlerFactory;
import cn.bit.quartz.core.service.TaskLogService;
import cn.bit.quartz.core.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Component
public class TaskInvokeListener {
    private final TaskLogService taskLogService;
    private final TaskService taskService;

    @Async
    @Order
    @EventListener(TaskInvokeEvent.class)
    public void notifyTaskInvoke(TaskInvokeEvent event) {
        // 从数据库中拿取任务
        Task task = taskService.selectTaskByNameAndGroup(event.getTaskName(), event.getTaskGroup());
        log.info("任务执行事件监听，准备执行任务{}", task);
        ITaskHandler handler = TaskHandlerFactory.getTaskHandler(task);

        long startTime = System.currentTimeMillis();
        TaskLog taskLog = new TaskLog();
        taskLog.setTaskId(task.getId());
        taskLog.setStartTime(new Date());
        boolean success = true;
        try {
            handler.invoke(task);
        } catch (TaskInvokeException e) {
            log.error("{},Task:{}", e.getMessage(), task);
            success = false;
            taskLog.setMessage(e.getMessage());
            if (e.getException() != null) {
                taskLog.setExceptionInfo(e.getException().getCause().toString());
                e.getException().printStackTrace();
            }
        }
        if (success) {
            taskLog.setMessage("执行成功");
            taskLog.setResult(Result.SUCCESS.getCode());
            task.setResult(Result.SUCCESS.getCode());
            taskService.setTaskResult(task);
        } else {
            taskLog.setResult(Result.FAIL.getCode());
            task.setResult(Result.FAIL.getCode());
            taskService.setTaskResult(task);
        }
        long endTime = System.currentTimeMillis();
        taskLog.setExecuteTime(String.valueOf(endTime - startTime));
        taskLogService.insert(taskLog);
    }
}
