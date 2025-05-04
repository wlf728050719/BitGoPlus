package cn.bit.productservice.controller;

import cn.bit.security.annotation.Admin;

import cn.bit.core.pojo.dto.BitGoUser;

import cn.bit.core.pojo.dto.ShopBaseInfo;
import cn.bit.core.pojo.vo.R;
import cn.bit.productservice.service.ProductService;
import cn.bit.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
@Validated
public class ProductController {
    private ProductService productService;

    @GetMapping("/test/{data}")
    @Admin
    public R<String> test(@PathVariable String data) {
        BitGoUser user = SecurityUtils.getUser();
        return R.ok(data,
            "product-service ok,username: " + user.getUsername() + " userId: " + user.getUserBaseInfo().getUserId());
    }

    @PostMapping("/createShop")
    public R<Boolean> createShop(@RequestBody ShopBaseInfo shopBaseInfo) {
        BitGoUser user = SecurityUtils.getUser();
        return productService.createShop(user.getUserBaseInfo().getUserId(), shopBaseInfo);
    }

}
