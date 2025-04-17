package cn.bit.productservice.service;

import cn.bit.pojo.po.product.ShopPO;
import cn.bit.pojo.vo.R;

public interface ProductService {
    R<Boolean> createShop(ShopPO shopPO);
}
