package cn.bit.productservice.service;

import cn.bit.pojo.dto.ShopBaseInfo;
import cn.bit.pojo.vo.R;

public interface ProductService {
    R<Boolean> createShop(Long userId, ShopBaseInfo shopBaseInfo);
}
