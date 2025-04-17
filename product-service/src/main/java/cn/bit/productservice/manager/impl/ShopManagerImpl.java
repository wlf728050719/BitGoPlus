package cn.bit.productservice.manager.impl;

import cn.bit.pojo.po.product.ShopPO;
import cn.bit.productservice.manager.ShopManager;
import cn.bit.productservice.mapper.ProductShopMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShopManagerImpl extends ServiceImpl<ProductShopMapper, ShopPO> implements ShopManager {
}
