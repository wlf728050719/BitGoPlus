package cn.bit.taskservice.core.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import cn.bit.core.pojo.dto.TaskBaseInfo;
import cn.bit.core.pojo.po.task.TaskPO;
import cn.bit.taskservice.core.service.TaskService;
import org.springframework.stereotype.Service;

import cn.bit.taskservice.core.enums.TaskStatus;
import cn.bit.taskservice.core.exception.TaskRepositoryException;
import cn.bit.taskservice.core.manager.TaskManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskManager taskManager;
    private final TaskPoolImpl taskPoolImpl;

    /**
     * 从数据库中反序列化任务数据，保证服务器重启后恢复任务池状态
     */
    @PostConstruct
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void init() {
        log.info("TaskManager初始化开始...");
        List<TaskPO> tasks = taskManager.selectAllTask();
        if (tasks != null && !tasks.isEmpty()) {
            for (TaskPO taskPO : tasks) {
                if (TaskStatus.RUNNING.getCode().equals(taskPO.getStatus())) {
                    taskPoolImpl.addTask(taskPO);
                }
            }
            log.info("初始化加载{}项任务", tasks.size());
        }
        log.info("TaskManager初始化结束...");
    }

    /**
     * 添加暂停且未被持久化的新任务
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addTask(TaskBaseInfo taskBaseInfo) {
        TaskPO temp = taskManager.selectTaskByNameAndGroup(taskBaseInfo.getTaskName(), taskBaseInfo.getTaskGroup());
        if (temp != null) {
            throw new TaskRepositoryException("存在相同任务组和任务名任务", taskBaseInfo);
        }
        if (!TaskStatus.PAUSE.getCode().equals(taskBaseInfo.getStatus())) {
            throw new TaskRepositoryException("只能添加暂停的任务", taskBaseInfo);
        }
        taskManager.insertTask(taskBaseInfo.newTaskPO());
        log.info("添加任务{}", taskBaseInfo);
    }

    /**
     * 在任务暂停时更新任务信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTask(TaskBaseInfo taskBaseInfo) {
        TaskPO temp = taskManager.selectTaskByNameAndGroup(taskBaseInfo.getTaskName(), taskBaseInfo.getTaskGroup());
        if (temp == null) {
            throw new TaskRepositoryException("不存在对应相同任务组和任务名任务", taskBaseInfo);
        }
        if (!TaskStatus.PAUSE.getCode().equals(temp.getStatus())) {
            throw new TaskRepositoryException("只能暂停时更新任务", taskBaseInfo);
        }
        taskManager.updateTaskInfo(taskBaseInfo.newTaskPO().setStatus(TaskStatus.PAUSE.getCode()));
        log.info("更新任务{}", taskBaseInfo);
    }

    /**
     * 启动暂停中任务
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void startTask(TaskBaseInfo taskBaseInfo) {
        TaskPO temp = taskManager.selectTaskByNameAndGroup(taskBaseInfo.getTaskName(), taskBaseInfo.getTaskGroup());
        if (temp == null) {
            throw new TaskRepositoryException("不存在对应相同任务组和任务名任务", taskBaseInfo);
        }
        if (!TaskStatus.PAUSE.getCode().equals(temp.getStatus())) {
            throw new TaskRepositoryException("只能启动暂停中任务", taskBaseInfo);
        }
        taskPoolImpl.addTask(temp);
        temp.setStatus(TaskStatus.RUNNING.getCode());
        taskManager.updateTaskStatus(temp);
        log.info("启动任务{}", temp);
    }

    /**
     * 暂停运行中任务
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pauseTask(TaskBaseInfo taskBaseInfo) {
        TaskPO temp = taskManager.selectTaskByNameAndGroup(taskBaseInfo.getTaskName(), taskBaseInfo.getTaskGroup());
        if (temp == null) {
            throw new TaskRepositoryException("不存在对应相同任务组和任务名任务", taskBaseInfo);
        }
        if (!TaskStatus.RUNNING.getCode().equals(temp.getStatus())) {
            throw new TaskRepositoryException("只能暂停运行中任务", taskBaseInfo);
        }
        taskPoolImpl.pauseTask(temp);
        temp.setStatus(TaskStatus.PAUSE.getCode());
        taskManager.updateTaskStatus(temp);
        log.info("暂停任务{}", temp);
    }

    /**
     * 删除暂停中任务
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTask(TaskBaseInfo taskBaseInfo) {
        TaskPO temp = taskManager.selectTaskByNameAndGroup(taskBaseInfo.getTaskName(), taskBaseInfo.getTaskGroup());
        if (temp == null) {
            throw new TaskRepositoryException("不存在对应相同任务组和任务名任务", taskBaseInfo);
        }
        if (!TaskStatus.PAUSE.getCode().equals(temp.getStatus())) {
            throw new TaskRepositoryException("只能删除暂停中任务", taskBaseInfo);
        }
        taskManager.deleteTask(temp);
        log.info("删除任务{}", temp);
    }
}
