package cn.bit.orderservice.controller;

import cn.bit.feign.client.UserClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    UserClient userClient;
    @GetMapping("/test/{text}")
    public String test(@PathVariable String text) {
        return userClient.test(text);
    }
}
