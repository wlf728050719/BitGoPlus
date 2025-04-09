package cn.bit.quartz.core.handler;

import cn.bit.quartz.core.entity.Task;
import cn.bit.quartz.core.exception.TaskInvokeException;

public interface ITaskHandler {
    void invoke(Task task) throws TaskInvokeException;
}
