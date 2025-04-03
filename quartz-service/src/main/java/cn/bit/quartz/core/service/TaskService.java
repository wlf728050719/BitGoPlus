package cn.bit.quartz.core.service;



import cn.bit.quartz.core.entity.Task;

import java.util.List;


public interface TaskService {
    List<Task> selectAllTask();
    int updateTaskInfo(Task task);
    int updateTaskStatus(Task task);
    int insertTask(Task task);
    int deleteTask(Task task);
    int setTaskResult(Task task);
    Task selectTaskByNameAndGroup(String taskName, String groupName);
}
