package cn.bit.productservice.service;

import cn.bit.core.pojo.dto.product.ProductBrandBaseInfo;
import cn.bit.core.pojo.dto.product.ProductSpuBaseInfo;
import cn.bit.core.pojo.dto.product.ShopBaseInfo;

public interface ProductService {
    Boolean createShop(Long userId, ShopBaseInfo shopBaseInfo);
    Boolean addBrand(ProductBrandBaseInfo productBrandBaseInfo);
    Boolean publishProductSpu(ProductSpuBaseInfo productSpuBaseInfo);
}
