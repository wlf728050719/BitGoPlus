package cn.bit.task.executor;

import java.util.Date;

import org.springframework.stereotype.Component;

import cn.bit.task.core.enums.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("test")
public class Test {
    public Result test(String param) {
        log.error("test {} {}", param, new Date());
        return Result.SUCCESS;
    }
}
