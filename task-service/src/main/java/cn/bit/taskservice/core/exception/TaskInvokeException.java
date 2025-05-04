package cn.bit.taskservice.core.exception;

import cn.bit.core.exception.SysException;
import lombok.Getter;

@Getter
public class TaskInvokeException extends SysException {
    private final Exception exception;

    public TaskInvokeException(String message, Exception exception) {
        super(message);
        this.exception = exception;
    }
}
