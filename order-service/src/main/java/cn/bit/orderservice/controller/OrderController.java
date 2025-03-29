package cn.bit.orderservice.controller;

import cn.bit.common.jsr.annotation.ValidFile;
import cn.bit.common.jsr.enums.FileEnum;
import cn.bit.common.pojo.po.UserPO;
import cn.bit.common.pojo.vo.OrderInfoVO;
import cn.bit.common.pojo.vo.R;
import cn.bit.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/test/{id}")
    public String test(@PathVariable Integer id) {
        System.out.println(id);

        return id.toString();
    }

    @PostMapping("/jsr")
    public String jsr(@RequestBody @Valid UserPO user) {
        return "ok";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") @Valid @ValidFile(fileEnum = FileEnum.IMAGE_FILE) MultipartFile file) throws IOException {
        return file.getOriginalFilename();
    }


    @GetMapping("/info/{id}")
    public R getOrderInfoById(@PathVariable Integer id, @RequestHeader(value = "source",required = false) String source) {
        log.debug("debug");
        log.info("info");
        log.warn("warning");
        System.out.println(source);
        OrderInfoVO orderInfoVO = orderService.getOrderInfoById(id);
        if (orderInfoVO == null) {
            return R.failed("订单不存在");
        }
        else
            return R.ok(orderInfoVO);
    }
}
