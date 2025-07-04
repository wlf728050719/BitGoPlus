package cn.bit.taskservice.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.bit.core.pojo.po.task.TaskLogPO;

@Mapper
public interface TaskLogMapper {
    int insert(TaskLogPO taskLogPO);

    List<TaskLogPO> selectByTaskId(int taskId);
}
