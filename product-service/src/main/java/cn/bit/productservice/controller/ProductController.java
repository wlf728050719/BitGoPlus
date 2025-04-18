package cn.bit.productservice.controller;

import cn.bit.annotation.Admin;
import cn.bit.pojo.dto.BitGoUser;
import cn.bit.pojo.vo.R;
import cn.bit.util.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/test/{data}")
    @Admin
    public R<String> test(@PathVariable String data) {
        BitGoUser user = (BitGoUser) SecurityUtils.getUser();
        return R.ok(data, "product-service ok,username: " + user.getUsername() + " userId: " + user.getUserBaseInfo().getUserId());
    }
}
