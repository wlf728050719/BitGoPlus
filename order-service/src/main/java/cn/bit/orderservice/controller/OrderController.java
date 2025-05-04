package cn.bit.orderservice.controller;

import cn.bit.security.annotation.Admin;
import cn.bit.orderservice.service.OrderMasterService;
import cn.bit.core.pojo.dto.BitGoUser;
import cn.bit.core.pojo.vo.R;
import cn.bit.security.util.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderMasterService orderMasterService;

    @GetMapping("/test/{data}")
    @Admin
    public R<String> test(@PathVariable String data) {
        BitGoUser user =  SecurityUtils.getUser();
        return R.ok(data, "order-service ok,username: " + user.getUsername() + " userId: " + user.getUserBaseInfo().getUserId());
    }

}
