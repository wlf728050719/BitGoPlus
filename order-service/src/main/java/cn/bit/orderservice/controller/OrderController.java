package cn.bit.orderservice.controller;

import cn.bit.orderservice.service.OrderMasterService;
import cn.bit.pojo.po.OrderMasterPO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bit.feign.client.UserClient;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderMasterService orderMasterService;
    private final UserClient userClient;

    @GetMapping("/insert")
    public int insert() {
        OrderMasterPO orderMasterPO = new OrderMasterPO();
        orderMasterPO.setOrderSn("777").setUserId(1L).setTotalAmount(BigDecimal.valueOf(123L))
            .setPayAmount(BigDecimal.valueOf(123L)).setReceiverName("wlf").setReceiverPhone("18086270070");
        return orderMasterService.insertOrderMaster(orderMasterPO);
    }
}
