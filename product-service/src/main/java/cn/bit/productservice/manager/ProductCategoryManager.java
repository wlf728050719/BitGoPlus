package cn.bit.productservice.manager;

import cn.bit.core.pojo.po.product.ProductCategoryDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

public interface ProductCategoryManager extends IService<ProductCategoryDictItem> {
    Set<ProductCategoryDictItem> getAvailableProductCategoryDict();
    ProductCategoryDictItem getAvailableProductCategoryDictItemById(Long categoryId);
    boolean checkAvailableProductCategoryDictItemId(Long categoryId);
}
