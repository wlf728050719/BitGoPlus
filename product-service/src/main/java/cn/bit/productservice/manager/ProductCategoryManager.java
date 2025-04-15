package cn.bit.productservice.manager;

import cn.bit.pojo.po.product.ProductCategoryDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ProductCategoryManager extends IService<ProductCategoryDictItem> {
    List<ProductCategoryDictItem> getProductCategoryDict();
}
