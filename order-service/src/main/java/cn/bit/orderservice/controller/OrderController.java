package cn.bit.orderservice.controller;

import cn.bit.orderservice.service.OrderMasterService;
import cn.bit.pojo.dto.BitGoUser;
import cn.bit.pojo.po.OrderMasterPO;
import cn.bit.pojo.vo.R;
import cn.bit.util.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderMasterService orderMasterService;

    @GetMapping("/test/{data}")
    public R<String> test(@PathVariable String data) {
        BitGoUser user = (BitGoUser) SecurityUtils.getUser();
        return R.ok(data, "order-service ok,username: " + user.getUsername() + " userId: " + user.getUserId());
    }

    @GetMapping("/insert")
    public int insert() {
        OrderMasterPO orderMasterPO = new OrderMasterPO();
        orderMasterPO.setOrderSn("777").setUserId(1L).setTotalAmount(BigDecimal.valueOf(123L))
            .setPayAmount(BigDecimal.valueOf(123L)).setReceiverName("wlf").setReceiverPhone("18086270070");
        return orderMasterService.insertOrderMaster(orderMasterPO);
    }
}
