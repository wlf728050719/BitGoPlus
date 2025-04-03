package cn.bit.quartz.core.handler.impl;



import cn.bit.quartz.core.entity.Task;
import cn.bit.quartz.core.enums.Result;
import cn.bit.quartz.core.exception.TaskInvokeException;
import cn.bit.quartz.core.handler.ITaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Component
public class JavaClassTaskHandler implements ITaskHandler {
    @Override
    public void invoke(Task task) throws TaskInvokeException {
        try {
            Object target;
            Class<?> clazz;
            Method method;
            Result returnValue;
            clazz = Class.forName(task.getClassName());
            target = clazz.newInstance();
            if (task.getParams() == null || task.getParams().isEmpty()) {
                method = target.getClass().getDeclaredMethod(task.getMethodName());
                ReflectionUtils.makeAccessible(method);
                returnValue = (Result) method.invoke(target);
            } else {
                method = target.getClass().getDeclaredMethod(task.getMethodName(), String.class);
                ReflectionUtils.makeAccessible(method);
                returnValue = (Result) method.invoke(target, task.getParams());
            }
            //判断业务是否执行成功
            if (returnValue == null || Result.FAIL.equals(returnValue))
                throw new TaskInvokeException("JavaClassTaskHandler方法执行失败",null);
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
