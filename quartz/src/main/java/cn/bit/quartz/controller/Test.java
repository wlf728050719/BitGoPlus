package cn.bit.quartz.controller;

import cn.bit.quartz.core.enums.Result;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("test")
public class Test {
    public Result test(String param) {
        System.out.println("test "+param+" "+new Date());
        return Result.SUCCESS;
    }
}
