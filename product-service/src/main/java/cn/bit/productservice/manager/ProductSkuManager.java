package cn.bit.productservice.manager;

import cn.bit.core.pojo.po.product.ProductSkuPO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProductSkuManager extends IService<ProductSkuPO> {
    Long insertProductSku(ProductSkuPO productSkuPO);
}
