package cn.bit.taskservice.core.events.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class TaskInvokeEvent {
    private final String taskName;
    private final String taskGroup;
}
