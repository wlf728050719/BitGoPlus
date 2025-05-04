package cn.bit.taskservice.core.manager.impl;

import java.util.List;

import cn.bit.core.pojo.po.task.TaskPO;
import cn.bit.data.snowflake.core.DistributedSnowflakeIdGenerator;
import org.springframework.stereotype.Service;

import cn.bit.taskservice.core.mapper.TaskMapper;
import cn.bit.taskservice.core.manager.TaskManager;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskManagerImpl implements TaskManager {
    private final TaskMapper taskMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public List<TaskPO> selectAllTask() {
        return taskMapper.selectAllTask();
    }

    @Override
    public int updateTaskInfo(TaskPO taskPO) {
        return taskMapper.updateTaskInfo(taskPO);
    }

    @Override
    public int updateTaskStatus(TaskPO taskPO) {
        return taskMapper.updateTaskStatus(taskPO);
    }

    @Override
    public int insertTask(TaskPO taskPO) {
        taskPO.setTaskId(idGenerator.nextId());
        return taskMapper.insertTask(taskPO);
    }

    @Override
    public int deleteTask(TaskPO taskPO) {
        return taskMapper.deleteTask(taskPO);
    }

    @Override
    public int setTaskResult(TaskPO taskPO) {
        return taskMapper.setTaskResult(taskPO);
    }

    @Override
    public TaskPO selectTaskByNameAndGroup(String taskName, String groupName) {
        return taskMapper.selectTaskByNameAndGroup(taskName, groupName);
    }
}
