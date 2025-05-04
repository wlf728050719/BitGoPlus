package cn.bit.taskservice.core.service;

import cn.bit.core.pojo.dto.TaskBaseInfo;

public interface TaskService {
    void init();
    void addTask(TaskBaseInfo taskBaseInfo);
    void updateTask(TaskBaseInfo taskBaseInfo);
    void deleteTask(TaskBaseInfo taskBaseInfo);
    void pauseTask(TaskBaseInfo taskBaseInfo);
    void startTask(TaskBaseInfo taskBaseInfo);
}
