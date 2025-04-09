package cn.bit.quartz.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskType {
    JAVA_CLASS(1, "java类"), SPRING_BEAN(2, "spring bean"), HTTP(3, "http");

    private final Integer code;
    private final String desc;
}
