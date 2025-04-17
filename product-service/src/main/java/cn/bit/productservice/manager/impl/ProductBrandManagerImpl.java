package cn.bit.productservice.manager.impl;

import cn.bit.pojo.po.product.ProductBrandPO;
import cn.bit.productservice.manager.ProductBrandManager;
import cn.bit.productservice.mapper.ProductBrandMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductBrandManagerImpl extends ServiceImpl<ProductBrandMapper, ProductBrandPO> implements ProductBrandManager {
}
