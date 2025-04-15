package cn.bit.taskservice.core.service;

import cn.bit.exception.SysException;
import cn.bit.pojo.po.task.TaskPO;

public interface TaskPool {
    void addTask(TaskPO taskPO) throws SysException;
    void pauseTask(TaskPO taskPO) throws SysException;
}
