package cn.bit.productservice.manager.impl;

import cn.bit.pojo.po.product.ShopPO;
import cn.bit.productservice.manager.ShopManager;
import cn.bit.productservice.mapper.ShopMapper;
import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShopManagerImpl extends ServiceImpl<ShopMapper, ShopPO> implements ShopManager {
    private ShopMapper shopMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;
    @Override
    public Long insertShop(ShopPO shopPO) {
        Long id = idGenerator.nextId();
        shopPO.setShopId(id);
        shopMapper.insert(shopPO);
        return id;
    }
}
