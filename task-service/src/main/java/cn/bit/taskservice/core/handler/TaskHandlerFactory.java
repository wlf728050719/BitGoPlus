package cn.bit.taskservice.core.handler;

import cn.bit.core.pojo.po.task.TaskPO;
import org.springframework.stereotype.Component;

import cn.bit.taskservice.core.enums.TaskType;
import cn.bit.taskservice.core.handler.impl.JavaClassTaskHandler;
import cn.bit.taskservice.core.handler.impl.SpringBeanTaskHandler;
import cn.bit.taskservice.core.util.SpringContextHolder;

@Component
public class TaskHandlerFactory {
    public static ITaskHandler getTaskHandler(TaskPO taskPO) {
        ITaskHandler taskHandler = null;
        if (TaskType.SPRING_BEAN.getCode().equals(taskPO.getType())) {
            taskHandler = SpringContextHolder.getApplicationContext().getBean(SpringBeanTaskHandler.class);
        }
        if (TaskType.JAVA_CLASS.getCode().equals(taskPO.getType())) {
            taskHandler = SpringContextHolder.getApplicationContext().getBean(JavaClassTaskHandler.class);
        }
        return taskHandler;
    }
}
