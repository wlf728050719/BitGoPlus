package cn.bit.taskservice.core.handler.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.bit.pojo.po.task.TaskPO;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import cn.bit.taskservice.core.enums.Result;
import cn.bit.taskservice.core.exception.TaskInvokeException;
import cn.bit.taskservice.core.handler.ITaskHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JavaClassTaskHandler implements ITaskHandler {
    @Override
    public void invoke(TaskPO taskPO) throws TaskInvokeException {
        try {
            Object target;
            Class<?> clazz;
            Method method;
            Result returnValue;
            clazz = Class.forName(taskPO.getClassName());
            target = clazz.newInstance();
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
                throw new TaskInvokeException("JavaClassTaskHandler方法执行失败", null);
            }
        } catch (NoSuchMethodException e) {
            throw new TaskInvokeException("JavaClassTaskHandler找不到对应方法", e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new TaskInvokeException("JavaClassTaskHandler执行反射方法异常", e);
        } catch (ClassCastException e) {
            throw new TaskInvokeException("JavaClassTaskHandler方法返回值定义错误", e);
        } catch (ClassNotFoundException e) {
            throw new TaskInvokeException("JavaClassTaskHandler找不到对应类", e);
        } catch (InstantiationException e) {
            throw new TaskInvokeException("JavaClassTaskHandler实例化错误", e);
        }
    }
}
