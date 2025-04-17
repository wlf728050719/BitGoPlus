package cn.bit.productservice.manager.impl;

import cn.bit.pojo.po.product.ProductCategoryDictItem;
import cn.bit.productservice.manager.ProductCategoryManager;
import cn.bit.productservice.mapper.ProductCategoryMapper;
import cn.bit.constant.RedisKey;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductCategoryManagerImpl extends ServiceImpl<ProductCategoryMapper, ProductCategoryDictItem> implements ProductCategoryManager {
    @Override
    @Cacheable(value = RedisKey.NAMESPACE, keyGenerator = "DictCacheGenerator")
    public Set<ProductCategoryDictItem> getProductCategoryDict() {
        return new HashSet<>(this.list());
    }
}
