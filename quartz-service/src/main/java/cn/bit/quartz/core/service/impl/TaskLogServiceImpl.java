package cn.bit.quartz.core.service.impl;

import java.util.List;

import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import org.springframework.stereotype.Service;

import cn.bit.quartz.core.entity.TaskLog;
import cn.bit.quartz.core.mapper.TaskLogMapper;
import cn.bit.quartz.core.service.TaskLogService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskLogServiceImpl implements TaskLogService {
    private final TaskLogMapper taskLogMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public int insert(TaskLog taskLog) {
        taskLog.setLogId(idGenerator.nextId());
        return taskLogMapper.insert(taskLog);
    }

    @Override
    public List<TaskLog> selectByTaskId(int taskId) {
        return taskLogMapper.selectByTaskId(taskId);
    }
}
