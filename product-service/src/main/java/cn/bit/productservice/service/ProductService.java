package cn.bit.productservice.service;

import cn.bit.core.pojo.dto.ProductBrandBaseInfo;
import cn.bit.core.pojo.dto.ShopBaseInfo;

public interface ProductService {
    Boolean createShop(Long userId, ShopBaseInfo shopBaseInfo);
    Boolean addBrand(ProductBrandBaseInfo productBrandBaseInfo);
}
