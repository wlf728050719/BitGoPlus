package cn.bit.taskservice.core.mapper;

import java.util.List;

import cn.bit.core.pojo.po.task.TaskPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskMapper {
    List<TaskPO> selectAllTask();

    int updateTaskInfo(@Param("taskPO") TaskPO taskPO);

    int updateTaskStatus(@Param("taskPO") TaskPO taskPO);

    int insertTask(@Param("taskPO") TaskPO taskPO);

    int deleteTask(@Param("taskPO") TaskPO taskPO);

    int setTaskResult(@Param("taskPO") TaskPO taskPO);

    TaskPO selectTaskByNameAndGroup(@Param("name") String name, @Param("group") String group);
}
