package cn.bit.userservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/test/{text}")
    public String test(@PathVariable String text) {
        return text+"666";
    }
}
