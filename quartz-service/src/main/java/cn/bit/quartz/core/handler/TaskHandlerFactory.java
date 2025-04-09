package cn.bit.quartz.core.handler;

import org.springframework.stereotype.Component;

import cn.bit.quartz.core.entity.Task;
import cn.bit.quartz.core.enums.TaskType;
import cn.bit.quartz.core.handler.impl.JavaClassTaskHandler;
import cn.bit.quartz.core.handler.impl.SpringBeanTaskHandler;
import cn.bit.quartz.core.util.SpringContextHolder;

@Component
public class TaskHandlerFactory {
    public static ITaskHandler getTaskHandler(Task task) {
        ITaskHandler taskHandler = null;
        if (TaskType.SPRING_BEAN.getCode().equals(task.getType())) {
            taskHandler = SpringContextHolder.getApplicationContext().getBean(SpringBeanTaskHandler.class);
        }
        if (TaskType.JAVA_CLASS.getCode().equals(task.getType())) {
            taskHandler = SpringContextHolder.getApplicationContext().getBean(JavaClassTaskHandler.class);
        }
        return taskHandler;
    }
}
