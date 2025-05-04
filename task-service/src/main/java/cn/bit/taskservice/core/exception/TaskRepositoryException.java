package cn.bit.taskservice.core.exception;

import cn.bit.core.exception.BizException;
import cn.bit.core.pojo.dto.TaskBaseInfo;
import lombok.Getter;

@Getter
public class TaskRepositoryException extends BizException {
    private final TaskBaseInfo taskBaseInfo;

    public TaskRepositoryException(String message, TaskBaseInfo taskBaseInfo) {
        super(message);
        this.taskBaseInfo = taskBaseInfo;
    }
}
