package cn.bit.taskservice.core.handler.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import cn.bit.pojo.po.task.TaskPO;
import cn.bit.taskservice.core.enums.Result;
import cn.bit.taskservice.core.exception.TaskInvokeException;
import cn.bit.taskservice.core.handler.ITaskHandler;
import cn.bit.taskservice.core.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpringBeanTaskHandler implements ITaskHandler {
    @Override
    public void invoke(TaskPO taskPO) throws TaskInvokeException {
        try {
            Object target;
            Method method;
            Result returnValue;
            // 上下文寻找对应bean
            target = SpringContextHolder.getApplicationContext().getBean(taskPO.getBeanName());
            // 寻找对应方法
            if (taskPO.getParams() == null || taskPO.getParams().isEmpty()) {
                method = target.getClass().getDeclaredMethod(taskPO.getMethodName());
                ReflectionUtils.makeAccessible(method);
                returnValue = (Result) method.invoke(target);
            } else {
                method = target.getClass().getDeclaredMethod(taskPO.getMethodName(), String.class);
                ReflectionUtils.makeAccessible(method);
                returnValue = (Result) method.invoke(target, taskPO.getParams());
            }
            // 判断业务是否执行成功
            if (returnValue == null || Result.FAIL.equals(returnValue)) {
                throw new TaskInvokeException("SpringBeanTaskHandler方法执行失败", null);
            }
        } catch (NoSuchBeanDefinitionException e) {
            throw new TaskInvokeException("SpringBeanTaskHandler找不到对应bean", e);
        } catch (NoSuchMethodException e) {
            throw new TaskInvokeException("SpringBeanTaskHandler找不到对应方法", e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new TaskInvokeException("SpringBeanTaskHandler执行反射方法异常", e);
        } catch (ClassCastException e) {
            throw new TaskInvokeException("SpringBeanTaskHandler方法返回值定义错误", e);
        }
    }
}
