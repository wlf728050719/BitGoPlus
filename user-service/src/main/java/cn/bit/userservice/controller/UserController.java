package cn.bit.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bit.pojo.po.UserPO;
import cn.bit.userservice.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/test/{text}")
    public String test(@PathVariable String text) {
        return text + "666";
    }

    @GetMapping("/register/{username}")
    public int register(@PathVariable String username) {
        UserPO user = new UserPO();
        user.setUsername(username).setPassword("654321").setSalt("654321");
        return userService.register(user);
    }
    @GetMapping("/count")
    public long count() {
        return userService.count();
    }
}
