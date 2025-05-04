package cn.bit.productservice.service.impl;

import cn.bit.feign.client.UserClient;
import cn.bit.core.constant.SecurityConstant;
import cn.bit.core.pojo.dto.ShopBaseInfo;
import cn.bit.core.pojo.vo.R;
import cn.bit.productservice.manager.ShopManager;
import cn.bit.productservice.service.ProductService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserClient userClient;
    private final ShopManager shopManager;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public R<Boolean> createShop(Long userId, ShopBaseInfo shopBaseInfo) {
        Long tenantId = shopManager.insertShop(shopBaseInfo.newShopPO());
        userClient.addPermission(userId, tenantId, SecurityConstant.ROLE_SHOPKEEPER);
        return R.ok(true, "店铺创建成功");
    }
}
