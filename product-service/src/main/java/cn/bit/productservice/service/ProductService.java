package cn.bit.productservice.service;

import cn.bit.core.pojo.dto.ShopBaseInfo;
import cn.bit.core.pojo.vo.R;

public interface ProductService {
    R<Boolean> createShop(Long userId, ShopBaseInfo shopBaseInfo);
}
