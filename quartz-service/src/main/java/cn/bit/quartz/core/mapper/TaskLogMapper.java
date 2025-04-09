package cn.bit.quartz.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.bit.quartz.core.entity.TaskLog;

@Mapper
public interface TaskLogMapper {
    int insert(TaskLog taskLog);

    List<TaskLog> selectByTaskId(int taskId);
}
