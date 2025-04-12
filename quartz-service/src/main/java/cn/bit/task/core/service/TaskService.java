package cn.bit.task.core.service;

import cn.bit.pojo.dto.TaskBaseInfo;

public interface TaskService {
    void init();
    void addTask(TaskBaseInfo taskBaseInfo);
    void updateTask(TaskBaseInfo taskBaseInfo);
    void deleteTask(TaskBaseInfo taskBaseInfo);
    void pauseTask(TaskBaseInfo taskBaseInfo);
    void startTask(TaskBaseInfo taskBaseInfo);
}
