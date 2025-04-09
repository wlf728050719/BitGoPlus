package cn.bit.quartz.core.service;

import java.util.List;

import cn.bit.quartz.core.entity.TaskLog;

public interface TaskLogService {
    int insert(TaskLog taskLog);

    List<TaskLog> selectByTaskId(int taskId);
}
