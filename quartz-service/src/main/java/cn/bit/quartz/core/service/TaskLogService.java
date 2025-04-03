package cn.bit.quartz.core.service;



import cn.bit.quartz.core.entity.TaskLog;

import java.util.List;

public interface TaskLogService {
    int insert(TaskLog taskLog);
    List<TaskLog> selectByTaskId(int taskId);
}
