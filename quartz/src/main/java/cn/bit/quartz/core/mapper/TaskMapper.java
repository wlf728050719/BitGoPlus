package cn.bit.quartz.core.mapper;



import cn.bit.quartz.core.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskMapper {
    List<Task> selectAllTask();
    int updateTaskInfo(@Param("task") Task task);
    int updateTaskStatus(@Param("task") Task task);
    int insertTask(@Param("task") Task task);
    int deleteTask(@Param("task") Task task);
    int setTaskResult(@Param("task") Task task);
    Task selectTaskByNameAndGroup(@Param("name") String name, @Param("group") String group );
}
