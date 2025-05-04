package cn.bit.productservice.manager;

import cn.bit.core.pojo.po.product.ProductSpuPO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProductSpuManager extends IService<ProductSpuPO> {
    Long insertProductSpu(ProductSpuPO productSpuPO);
}
