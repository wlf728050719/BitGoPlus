package cn.bit.productservice.manager;

import cn.bit.core.pojo.po.product.ProductBrandPO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProductBrandManager extends IService<ProductBrandPO> {
    Long insertProductBrand(ProductBrandPO productBrandPO);
    ProductBrandPO getAvailableProductBrandById(Long id);
    boolean checkAvailableProductBrandId(Long id);
}
