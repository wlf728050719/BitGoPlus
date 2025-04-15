package cn.bit.productservice.manager.impl;

import cn.bit.pojo.po.product.ProductImagePO;
import cn.bit.productservice.manager.ProductImageManager;
import cn.bit.productservice.mapper.ProductImageMapper;
import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductImageManagerImpl extends ServiceImpl<ProductImageMapper, ProductImagePO> implements ProductImageManager {
    private final ProductImageMapper productImageMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;
    @Override
    public Long insertProductImage(ProductImagePO productImagePO) {
        Long id = idGenerator.nextId();
        productImagePO.setImageId(id);
        productImageMapper.insert(productImagePO);
        return id;
    }
}
