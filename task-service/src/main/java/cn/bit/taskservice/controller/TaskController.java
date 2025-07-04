package cn.bit.taskservice.controller;

import cn.bit.security.annotation.Admin;
import cn.bit.core.pojo.dto.security.BitGoUser;
import cn.bit.core.pojo.dto.task.TaskBaseInfo;
import cn.bit.security.util.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bit.core.pojo.vo.R;
import cn.bit.taskservice.core.service.impl.TaskServiceImpl;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
@Validated
public class TaskController {

    private TaskServiceImpl taskServiceImpl;
    @GetMapping("/test/{data}")
    @Admin
    public R<String> test(@PathVariable String data) {
        BitGoUser user = SecurityUtils.getUser();
        return R.ok(data, "task-service ok,username: " + user.getUsername() + " userId: " + user.getUserBaseInfo().getUserId());
    }

    @PostMapping("/add")
    @Admin
    public R<Boolean> add(@RequestBody @Valid TaskBaseInfo taskBaseInfo) {
        taskServiceImpl.addTask(taskBaseInfo);
        return R.ok(true, "添加任务成功");
    }

    @PutMapping("/update")
    @Admin
    public R<Boolean> update(@RequestBody @Valid TaskBaseInfo taskBaseInfo) {
        taskServiceImpl.updateTask(taskBaseInfo);
        return R.ok(true, "任务更新成功");
    }

    @PutMapping("/start")
    @Admin
    public R<Boolean> start(@RequestBody @Valid TaskBaseInfo taskBaseInfo) {
        taskServiceImpl.startTask(taskBaseInfo);
        return R.ok(true, "任务启动成功");
    }

    @PutMapping("/pause")
    @Admin
    public R<Boolean> pause(@RequestBody @Valid TaskBaseInfo taskBaseInfo) {
        taskServiceImpl.pauseTask(taskBaseInfo);
        return R.ok(true, "任务暂停成功");
    }

    @DeleteMapping("/delete")
    @Admin
    public R<Boolean> delete(@RequestBody @Valid TaskBaseInfo taskBaseInfo) {
        taskServiceImpl.deleteTask(taskBaseInfo);
        return R.ok(true, "任务删除成功");
    }
}
