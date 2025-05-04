package cn.bit.taskservice.core.service;

import cn.bit.core.exception.SysException;
import cn.bit.core.pojo.po.task.TaskPO;

public interface TaskPool {
    void addTask(TaskPO taskPO) throws SysException;
    void pauseTask(TaskPO taskPO) throws SysException;
}
