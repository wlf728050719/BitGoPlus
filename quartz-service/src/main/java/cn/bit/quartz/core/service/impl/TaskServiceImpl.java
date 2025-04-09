package cn.bit.quartz.core.service.impl;

import java.util.List;

import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import org.springframework.stereotype.Service;

import cn.bit.quartz.core.entity.Task;
import cn.bit.quartz.core.mapper.TaskMapper;
import cn.bit.quartz.core.service.TaskService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public List<Task> selectAllTask() {
        return taskMapper.selectAllTask();
    }

    @Override
    public int updateTaskInfo(Task task) {
        return taskMapper.updateTaskInfo(task);
    }

    @Override
    public int updateTaskStatus(Task task) {
        return taskMapper.updateTaskStatus(task);
    }

    @Override
    public int insertTask(Task task) {
        task.setTaskId(idGenerator.nextId());
        return taskMapper.insertTask(task);
    }

    @Override
    public int deleteTask(Task task) {
        return taskMapper.deleteTask(task);
    }

    @Override
    public int setTaskResult(Task task) {
        return taskMapper.setTaskResult(task);
    }

    @Override
    public Task selectTaskByNameAndGroup(String taskName, String groupName) {
        return taskMapper.selectTaskByNameAndGroup(taskName, groupName);
    }
}
