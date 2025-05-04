package cn.bit.productservice.manager;

import cn.bit.core.pojo.po.product.ProductImagePO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProductImageManager extends IService<ProductImagePO> {
    Long insertProductImage(ProductImagePO productImagePO);
}
