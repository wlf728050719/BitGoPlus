package cn.bit.task.core.handler;

import cn.bit.pojo.po.TaskPO;
import cn.bit.task.core.exception.TaskInvokeException;

public interface ITaskHandler {
    void invoke(TaskPO taskPO) throws TaskInvokeException;
}
