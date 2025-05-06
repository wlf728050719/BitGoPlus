package cn.bit.productservice.manager.impl;

import cn.bit.core.pojo.po.product.ShopPO;
import cn.bit.productservice.manager.ShopManager;
import cn.bit.productservice.mapper.ShopMapper;
import cn.bit.data.snowflake.core.DistributedSnowflakeIdGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShopManagerImpl extends ServiceImpl<ShopMapper, ShopPO> implements ShopManager {
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public Long insertShop(ShopPO shopPO) {
        Long id = idGenerator.nextId();
        shopPO.setShopId(id);
        save(shopPO);
        return id;
    }

    @Override
    public ShopPO getAvailableShop(Long shopId) {
        ShopPO shopPO = getById(shopId);
        return (shopPO != null && shopPO.getDelFlag() == 0) ? shopPO : null;
    }

    @Override
    public boolean checkAvailableShopId(Long id) {
        return getById(id) != null;
    }
}
