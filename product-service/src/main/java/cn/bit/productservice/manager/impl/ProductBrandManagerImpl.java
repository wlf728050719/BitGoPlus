package cn.bit.productservice.manager.impl;

import cn.bit.core.pojo.po.product.ProductBrandPO;
import cn.bit.data.snowflake.core.DistributedSnowflakeIdGenerator;
import cn.bit.productservice.manager.ProductBrandManager;
import cn.bit.productservice.mapper.ProductBrandMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductBrandManagerImpl extends ServiceImpl<ProductBrandMapper, ProductBrandPO> implements ProductBrandManager {
    private final DistributedSnowflakeIdGenerator idGenerator;
    @Override
    public Long insertProductBrand(ProductBrandPO productBrandPO) {
        Long id = idGenerator.nextId();
        productBrandPO.setBrandId(id);
        save(productBrandPO);
        return id;
    }
}
