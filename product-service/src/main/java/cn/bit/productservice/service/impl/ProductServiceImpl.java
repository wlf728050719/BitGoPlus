package cn.bit.productservice.service.impl;

import cn.bit.pojo.po.product.ShopPO;
import cn.bit.pojo.vo.R;
import cn.bit.productservice.manager.ShopManager;
import cn.bit.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ShopManager shopManager;
    private final ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> createShop(ShopPO shopPO) {
        return null;
    }
}
