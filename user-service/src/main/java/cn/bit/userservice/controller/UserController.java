package cn.bit.userservice.controller;

import cn.bit.annotation.Admin;
import cn.bit.pojo.dto.BitGoUser;
import cn.bit.pojo.dto.UserBaseInfo;
import cn.bit.pojo.vo.R;
import cn.bit.userservice.service.UserService;
import cn.bit.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Validated
public class UserController {
    private UserService userService;

    @GetMapping("/test/{data}")
    @Admin
    public R<String> test(@PathVariable String data) {
        BitGoUser user = (BitGoUser) SecurityUtils.getUser();
        return R.ok(data, "user-service ok,username: " + user.getUsername() + "userId: " + user.getUserId());
    }

    @PostMapping("/register")
    public R<Boolean> register(@RequestParam String code, @RequestParam String roleCode,
        @RequestBody @Valid UserBaseInfo userBaseInfo) {
        return userService.register(code, roleCode, userBaseInfo);
    }
}
