package cn.bit.quartz.core.mvc;


import cn.bit.quartz.core.entity.Task;
import cn.bit.quartz.core.enums.TaskStatus;
import cn.bit.quartz.core.exception.TaskRepositoryException;
import cn.bit.quartz.core.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TaskManger {
    private final Scheduler scheduler;
    private final TaskService taskService;
    private final TaskPool taskPool;

    /**
     * 从数据库中反序列化任务数据，保证服务器重启后恢复任务池状态
     * @throws SchedulerException
     */
    @PostConstruct
    public void init() throws SchedulerException {
        log.info("TaskManager初始化开始...");
        List<Task> tasks = taskService.selectAllTask();
        if(tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks)
            {
                if(TaskStatus.RUNNING.getCode().equals(task.getStatus()))
                    taskPool.addTask(task,scheduler);
            }
            log.info("初始化加载{}项任务", tasks.size());
        }
        log.info("TaskManager初始化结束...");
    }

    /**
     * 添加暂停且未被持久化的新任务
     * @param task
     * @throws SchedulerException
     */
    public void addTask(Task task) throws SchedulerException,TaskRepositoryException {
        Task temp = taskService.selectTaskByNameAndGroup(task.getTaskName(),task.getTaskGroup());
        if(temp != null)
            throw new TaskRepositoryException("存在相同任务组和任务名任务",task);
        if(!TaskStatus.PAUSE.getCode().equals(task.getStatus()))
            throw new TaskRepositoryException("只能添加暂停的任务",task);
        taskService.insertTask(task);
        log.info("添加任务{}", task);
    }

    /**
     * 在任务暂停时更新任务信息
     * @param task
     * @throws SchedulerException
     */
    public void updateTask(Task task) throws SchedulerException,TaskRepositoryException {
        Task temp = taskService.selectTaskByNameAndGroup(task.getTaskName(),task.getTaskGroup());
        if(temp == null)
            throw new TaskRepositoryException("不存在对应相同任务组和任务名任务",task);
        if(!TaskStatus.PAUSE.getCode().equals(temp.getStatus()))
            throw new TaskRepositoryException("只能暂停时更新任务",task);
        taskService.updateTaskInfo(task);
        log.info("更新任务{}", task);
    }

    /**
     * 启动暂停中任务
     * @param task 只使用name和group字段
     * @throws SchedulerException
     */
    public void startTask(Task task) throws SchedulerException,TaskRepositoryException {
        Task temp = taskService.selectTaskByNameAndGroup(task.getTaskName(),task.getTaskGroup());
        if(temp == null)
            throw new TaskRepositoryException("不存在对应相同任务组和任务名任务",task);
        if(!TaskStatus.PAUSE.getCode().equals(temp.getStatus()))
            throw new TaskRepositoryException("只能启动暂停中任务",task);
        taskPool.addTask(temp,scheduler);
        //添加任务池未有异常时持久化数据
        temp.setStatus(TaskStatus.RUNNING.getCode());
        taskService.updateTaskStatus(temp);
        log.info("启动任务{}", temp);
    }

    /**
     * 暂停运行中任务
     * @param task 只使用name和group字段
     * @throws SchedulerException
     */
    public void pauseTask(Task task) throws SchedulerException,TaskRepositoryException {
        Task temp = taskService.selectTaskByNameAndGroup(task.getTaskName(),task.getTaskGroup());
        if(temp == null)
            throw new TaskRepositoryException("不存在对应相同任务组和任务名任务",task);
        if(!TaskStatus.RUNNING.getCode().equals(temp.getStatus()))
            throw new TaskRepositoryException("只能暂停运行中任务",task);
        taskPool.pauseTask(temp,scheduler);
        //添加任务池未有异常时持久化数据
        temp.setStatus(TaskStatus.PAUSE.getCode());
        taskService.updateTaskStatus(temp);
        log.info("暂停任务{}", temp);
    }

    /**
     * 暂停暂停中任务
     * @param task 只使用name和group字段
     * @throws SchedulerException
     */
    public void deleteTask(Task task) throws SchedulerException,TaskRepositoryException {
        Task temp = taskService.selectTaskByNameAndGroup(task.getTaskName(),task.getTaskGroup());
        if(temp == null)
            throw new TaskRepositoryException("不存在对应相同任务组和任务名任务",task);
        if(!TaskStatus.PAUSE.getCode().equals(temp.getStatus()))
            throw new TaskRepositoryException("只能删除暂停中任务",task);
        taskService.deleteTask(temp);
        log.info("删除任务{}", temp);
    }
}
