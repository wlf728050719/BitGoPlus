package cn.bit.userservice.controller;

import cn.bit.pojo.po.UserPO;
import cn.bit.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/test/{text}")
    public String test(@PathVariable String text) {
        return text+"666";
    }

    @GetMapping("/register/{username}")
    public int register(@PathVariable String username) {
        UserPO user = new UserPO();
        user.setUsername("wlf").setPassword(username).setSalt("654321");
        return userService.register(user);
    }
}
