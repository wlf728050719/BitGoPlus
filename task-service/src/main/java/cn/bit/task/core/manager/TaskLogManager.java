package cn.bit.task.core.manager;

import java.util.List;

import cn.bit.pojo.po.TaskLogPO;

public interface TaskLogManager {
    int insert(TaskLogPO taskLogPO);

    List<TaskLogPO> selectByTaskId(int taskId);
}
