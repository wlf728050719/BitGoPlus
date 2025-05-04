package cn.bit.taskservice.core.events.listener;

import java.time.ZoneId;
import java.util.Date;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.bit.core.pojo.po.task.TaskPO;
import cn.bit.core.pojo.po.task.TaskLogPO;
import cn.bit.taskservice.core.enums.Result;
import cn.bit.taskservice.core.events.event.TaskInvokeEvent;
import cn.bit.taskservice.core.exception.TaskInvokeException;
import cn.bit.taskservice.core.handler.ITaskHandler;
import cn.bit.taskservice.core.handler.TaskHandlerFactory;
import cn.bit.taskservice.core.manager.TaskLogManager;
import cn.bit.taskservice.core.manager.TaskManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Component
public class TaskInvokeListener {
    private final TaskLogManager taskLogManager;
    private final TaskManager taskManager;

    @Async
    @Order
    @EventListener(TaskInvokeEvent.class)
    public void notifyTaskInvoke(TaskInvokeEvent event) {
        // 从数据库中拿取任务
        TaskPO taskPO = taskManager.selectTaskByNameAndGroup(event.getTaskName(), event.getTaskGroup());
        log.info("任务执行事件监听，准备执行任务{}", taskPO);
        ITaskHandler handler = TaskHandlerFactory.getTaskHandler(taskPO);

        long startTime = System.currentTimeMillis();
        TaskLogPO taskLogPO = new TaskLogPO();
        taskLogPO.setTaskId(taskPO.getTaskId());
        taskLogPO.setStartTime(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        boolean success = true;
        try {
            handler.invoke(taskPO);
        } catch (TaskInvokeException e) {
            log.error("{},Task:{}", e.getMessage(), taskPO);
            success = false;
            taskLogPO.setMessage(e.getMessage());
            if (e.getException() != null) {
                taskLogPO.setExceptionInfo(e.getException().getCause().toString());
                e.getException().printStackTrace();
            }
        }
        if (success) {
            taskLogPO.setMessage("执行成功");
            taskLogPO.setResult(Result.SUCCESS.getCode());
            taskPO.setResult(Result.SUCCESS.getCode());
            taskManager.setTaskResult(taskPO);
        } else {
            taskLogPO.setResult(Result.FAIL.getCode());
            taskPO.setResult(Result.FAIL.getCode());
            taskManager.setTaskResult(taskPO);
        }
        long endTime = System.currentTimeMillis();
        taskLogPO.setExecuteTime(endTime - startTime);
        taskLogManager.insert(taskLogPO);
    }
}
