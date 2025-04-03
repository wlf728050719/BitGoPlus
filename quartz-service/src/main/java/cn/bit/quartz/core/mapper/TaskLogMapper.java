package cn.bit.quartz.core.mapper;

;import cn.bit.quartz.core.entity.TaskLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskLogMapper {
    int insert(TaskLog taskLog);
    List<TaskLog> selectByTaskId(int taskId);
}
