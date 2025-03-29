package cn.bit.quartz.controller;

import cn.bit.common.exception.BizException;
import cn.bit.common.pojo.vo.R;
import cn.bit.quartz.core.entity.Task;
import cn.bit.quartz.core.exception.TaskRepositoryException;
import cn.bit.quartz.core.mvc.TaskManger;
import lombok.AllArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quartz")
@AllArgsConstructor
public class QuartzController {

    private TaskManger taskManger;
    @PostMapping("/add")
    public R add(@RequestBody Task task){
        try {
            taskManger.addTask(task);
        } catch (SchedulerException | TaskRepositoryException e) {
            throw new BizException(e);
        }
        return R.ok("添加任务成功");
    }

    @PutMapping("/update")
    public R update(@RequestBody Task task){
        try {
            taskManger.updateTask(task);
        }catch (SchedulerException | TaskRepositoryException e) {
            throw new BizException(e.getMessage());
        }
        return R.ok("任务更新成功");
    }

    @PutMapping("/start")
    public R start(@RequestBody Task task){
        try {
            taskManger.startTask(task);
        }catch (SchedulerException | TaskRepositoryException e) {
            throw new BizException(e.getMessage());
        }
        return R.ok("任务启动成功");
    }

    @PutMapping("/pause")
    public R pause(@RequestBody Task task){
        try {
            taskManger.pauseTask(task);
        } catch (SchedulerException | TaskRepositoryException e) {
            throw new BizException(e.getMessage());
        }
        return R.ok("任务暂停成功");
    }

    @DeleteMapping("/delete")
    public R delete(@RequestBody Task task){
        try {
            taskManger.deleteTask(task);
        } catch (SchedulerException | TaskRepositoryException e) {
            throw new BizException(e.getMessage());
        }
        return R.ok("任务删除成功");
    }
}
