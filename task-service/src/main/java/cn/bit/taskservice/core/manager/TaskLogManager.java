package cn.bit.taskservice.core.manager;

import java.util.List;

import cn.bit.core.pojo.po.task.TaskLogPO;

public interface TaskLogManager {
    int insert(TaskLogPO taskLogPO);

    List<TaskLogPO> selectByTaskId(int taskId);
}
