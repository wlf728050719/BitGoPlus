package cn.bit.productservice.controller;

import cn.bit.core.constant.SecurityConstant;
import cn.bit.core.pojo.dto.product.ProductBrandBaseInfo;
import cn.bit.core.pojo.dto.product.ProductSpuBaseInfo;
import cn.bit.security.annotation.Admin;

import cn.bit.core.pojo.dto.security.BitGoUser;

import cn.bit.core.pojo.dto.product.ShopBaseInfo;
import cn.bit.core.pojo.vo.R;
import cn.bit.productservice.service.ProductService;
import cn.bit.security.annotation.Shopkeeper;
import cn.bit.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public R<Boolean> createShop(@ModelAttribute @Valid ShopBaseInfo shopBaseInfo) {
        BitGoUser user = SecurityUtils.getUser();
        return R.ok(productService.createShop(user.getUserBaseInfo().getUserId(), shopBaseInfo), "店铺创建成功");
    }

    @PostMapping("/addBrand")
    public R<Boolean> addBrand(@ModelAttribute @Valid ProductBrandBaseInfo productBrandBaseInfo) {
        return R.ok(productService.addBrand(productBrandBaseInfo), "品牌添加成功");
    }

    @PostMapping("/publishProductSpu")
    @Shopkeeper
    public R<Boolean> publishProductSpu(@ModelAttribute @Valid ProductSpuBaseInfo productSpuBaseInfo) {
        SecurityUtils.checkTenantId(productSpuBaseInfo.getShopId(), SecurityConstant.ROLE_SHOPKEEPER);
        return R.ok(productService.publishProductSpu(productSpuBaseInfo), "商品发布成功");
    }
}
