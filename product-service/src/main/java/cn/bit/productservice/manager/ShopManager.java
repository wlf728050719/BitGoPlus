package cn.bit.productservice.manager;

import cn.bit.core.pojo.po.product.ShopPO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ShopManager extends IService<ShopPO> {
    Long insertShop(ShopPO shopPO);
    ShopPO getAvailableShop(Long shopId);
    boolean checkAvailableShopId(Long id);
}
