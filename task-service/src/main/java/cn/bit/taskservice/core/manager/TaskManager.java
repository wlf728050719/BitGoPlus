package cn.bit.taskservice.core.manager;

import java.util.List;

import cn.bit.core.pojo.po.task.TaskPO;

public interface TaskManager {
    List<TaskPO> selectAllTask();

    int updateTaskInfo(TaskPO taskPO);

    int updateTaskStatus(TaskPO taskPO);

    int insertTask(TaskPO taskPO);

    int deleteTask(TaskPO taskPO);

    int setTaskResult(TaskPO taskPO);

    TaskPO selectTaskByNameAndGroup(String taskName, String groupName);
}
