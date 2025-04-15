package cn.bit.productservice.manager.impl;

import cn.bit.pojo.po.product.ProductSkuPO;
import cn.bit.productservice.manager.ProductSkuManager;
import cn.bit.productservice.mapper.ProductSkuMapper;
import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductSkuManagerImpl extends ServiceImpl<ProductSkuMapper, ProductSkuPO> implements ProductSkuManager {
    private final ProductSkuMapper productSkuMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public Long insertProductSku(ProductSkuPO productSkuPO) {
        Long id = idGenerator.nextId();
        productSkuPO.setSkuId(id);
        productSkuMapper.insert(productSkuPO);
        return id;
    }
}
