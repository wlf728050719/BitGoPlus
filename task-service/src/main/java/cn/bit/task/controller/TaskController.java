package cn.bit.task.controller;

import cn.bit.pojo.dto.TaskBaseInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bit.pojo.vo.R;
import cn.bit.task.core.service.impl.TaskService;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
@Validated
public class TaskController {

    private TaskService taskService;

    @PostMapping("/add")
    public R<Boolean> add(@RequestBody @Valid TaskBaseInfo taskBaseInfo) {
        taskService.addTask(taskBaseInfo);
        return R.ok(true, "添加任务成功");
    }

    @PutMapping("/update")
    public R<Boolean> update(@RequestBody @Valid TaskBaseInfo taskBaseInfo) {
        taskService.updateTask(taskBaseInfo);
        return R.ok(true, "任务更新成功");
    }

    @PutMapping("/start")
    public R<Boolean> start(@RequestBody @Valid TaskBaseInfo taskBaseInfo) {
        taskService.startTask(taskBaseInfo);
        return R.ok(true, "任务启动成功");
    }

    @PutMapping("/pause")
    public R<Boolean> pause(@RequestBody @Valid TaskBaseInfo taskBaseInfo) {
        taskService.pauseTask(taskBaseInfo);
        return R.ok(true, "任务暂停成功");
    }

    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody @Valid TaskBaseInfo taskBaseInfo) {
        taskService.deleteTask(taskBaseInfo);
        return R.ok(true, "任务删除成功");
    }
}
