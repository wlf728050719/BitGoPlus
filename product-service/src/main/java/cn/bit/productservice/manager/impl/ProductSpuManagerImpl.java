package cn.bit.productservice.manager.impl;

import cn.bit.core.pojo.po.product.ProductSpuPO;
import cn.bit.productservice.manager.ProductSpuManager;
import cn.bit.productservice.mapper.ProductSpuMapper;
import cn.bit.data.snowflake.core.DistributedSnowflakeIdGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductSpuManagerImpl extends ServiceImpl<ProductSpuMapper, ProductSpuPO> implements ProductSpuManager {
    private final ProductSpuMapper productSpuMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public Long insertProductSpu(ProductSpuPO productSpuPO) {
        Long id = idGenerator.nextId();
        productSpuPO.setSpuId(id);
        productSpuMapper.insert(productSpuPO);
        return id;
    }
}
