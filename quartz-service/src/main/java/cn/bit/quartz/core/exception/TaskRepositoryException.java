package cn.bit.quartz.core.exception;

import cn.bit.quartz.core.entity.Task;
import lombok.Getter;

@Getter
public class TaskRepositoryException extends RuntimeException {
    private final Task task;

    public TaskRepositoryException(String message, Task task) {
        super(message);
        this.task = task;
    }
}
