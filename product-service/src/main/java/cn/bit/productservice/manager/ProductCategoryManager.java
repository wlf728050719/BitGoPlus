package cn.bit.productservice.manager;

import cn.bit.pojo.po.product.ProductCategoryDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

public interface ProductCategoryManager extends IService<ProductCategoryDictItem> {
    Set<ProductCategoryDictItem> getProductCategoryDict();
}
