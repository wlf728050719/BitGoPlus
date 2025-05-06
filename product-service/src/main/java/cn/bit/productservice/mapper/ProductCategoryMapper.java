package cn.bit.productservice.mapper;

import cn.bit.core.pojo.po.product.ProductCategoryDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

public interface ProductCategoryMapper extends BaseMapper<ProductCategoryDictItem> {
    Set<ProductCategoryDictItem> getAvailableProductCategoryDict();
}
