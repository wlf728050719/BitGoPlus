package cn.bit.taskservice.core.exception;

import cn.bit.exception.BizException;
import cn.bit.pojo.dto.TaskBaseInfo;
import lombok.Getter;

@Getter
public class TaskRepositoryException extends BizException {
    private final TaskBaseInfo taskBaseInfo;

    public TaskRepositoryException(String message, TaskBaseInfo taskBaseInfo) {
        super(message);
        this.taskBaseInfo = taskBaseInfo;
    }
}
