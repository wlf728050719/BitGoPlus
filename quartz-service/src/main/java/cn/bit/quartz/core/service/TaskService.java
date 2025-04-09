package cn.bit.quartz.core.service;

import java.util.List;

import cn.bit.quartz.core.entity.Task;

public interface TaskService {
    List<Task> selectAllTask();

    int updateTaskInfo(Task task);

    int updateTaskStatus(Task task);

    int insertTask(Task task);

    int deleteTask(Task task);

    int setTaskResult(Task task);

    Task selectTaskByNameAndGroup(String taskName, String groupName);
}
