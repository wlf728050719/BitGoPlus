package cn.bit.quartz.core.exception;

import lombok.Getter;

@Getter
public class TaskInvokeException extends Exception {
    private final Exception exception;
    public TaskInvokeException(String message,Exception exception) {
        super(message);
        this.exception = exception;
    }
}
