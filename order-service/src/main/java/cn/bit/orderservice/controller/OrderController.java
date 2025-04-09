package cn.bit.orderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bit.feign.client.UserClient;
import lombok.AllArgsConstructor;

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
