package cn.bit.quartz.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskStatus {
    PAUSE(0, "已发布"),
    RUNNING(1, "运行中");
    private final Integer code;
    private final String desc;
}
