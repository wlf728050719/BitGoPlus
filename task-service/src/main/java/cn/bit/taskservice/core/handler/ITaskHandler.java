package cn.bit.taskservice.core.handler;

import cn.bit.pojo.po.task.TaskPO;
import cn.bit.taskservice.core.exception.TaskInvokeException;

public interface ITaskHandler {
    void invoke(TaskPO taskPO) throws TaskInvokeException;
}
