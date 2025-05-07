package cn.bit.productservice.manager.impl;

import cn.bit.core.pojo.po.product.ProductCategoryDictItem;
import cn.bit.productservice.manager.ProductCategoryManager;
import cn.bit.productservice.mapper.ProductCategoryMapper;
import cn.bit.core.constant.RedisKey;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 商品类别管理
 * </p>
 * Date:2025/05/05 20:07:57
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class ProductCategoryManagerImpl extends ServiceImpl<ProductCategoryMapper, ProductCategoryDictItem>
    implements ProductCategoryManager {
    private final ProductCategoryMapper productCategoryMapper;
    /** 获取全部有效商品字典项（优先缓存） */
    @Override
    @Cacheable(value = RedisKey.NAMESPACE, keyGenerator = "DictCacheGenerator")
    public Set<ProductCategoryDictItem> getAvailableProductCategoryDict() {
        return productCategoryMapper.selectAvailableProductCategoryDict();
    }

    /** 获取指定id的有效商品分类字典项 */
    @Override
    public ProductCategoryDictItem getAvailableProductCategoryDictItemById(Long categoryId) {
        ProductCategoryDictItem productCategoryDictItem = getById(categoryId);
        return (productCategoryDictItem != null && productCategoryDictItem.getDelFlag() == 0) ? productCategoryDictItem
            : null;
    }

    /** 判断对应商品类别是否存在有效ID */
    @Override
    public boolean checkAvailableProductCategoryDictItemId(Long categoryId) {
        return getAvailableProductCategoryDictItemById(categoryId) != null;
    }
}
