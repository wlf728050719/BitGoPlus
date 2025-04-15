package cn.bit.taskservice.core.manager.impl;

import java.util.List;

import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import org.springframework.stereotype.Service;

import cn.bit.pojo.po.TaskLogPO;
import cn.bit.taskservice.core.mapper.TaskLogMapper;
import cn.bit.taskservice.core.manager.TaskLogManager;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskLogManagerImpl implements TaskLogManager {
    private final TaskLogMapper taskLogMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public int insert(TaskLogPO taskLogPO) {
        taskLogPO.setLogId(idGenerator.nextId());
        return taskLogMapper.insert(taskLogPO);
    }

    @Override
    public List<TaskLogPO> selectByTaskId(int taskId) {
        return taskLogMapper.selectByTaskId(taskId);
    }
}
