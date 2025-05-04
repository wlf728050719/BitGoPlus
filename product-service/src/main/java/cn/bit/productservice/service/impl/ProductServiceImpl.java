package cn.bit.productservice.service.impl;

import cn.bit.core.constant.OssFilePath;
import cn.bit.core.pojo.dto.ProductBrandBaseInfo;
import cn.bit.feign.client.UserClient;
import cn.bit.core.constant.SecurityConstant;
import cn.bit.core.pojo.dto.ShopBaseInfo;
import cn.bit.oss.util.OssUtil;
import cn.bit.productservice.manager.ProductBrandManager;
import cn.bit.productservice.manager.ShopManager;
import cn.bit.productservice.service.ProductService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserClient userClient;
    private final ShopManager shopManager;
    private final ProductBrandManager productBrandManager;
    private final OssUtil ossUtil;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public Boolean createShop(Long userId, ShopBaseInfo shopBaseInfo) {
        Long tenantId = shopManager.insertShop(shopBaseInfo.newShopPO());
        userClient.addPermission(userId, tenantId, SecurityConstant.ROLE_SHOPKEEPER);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addBrand(ProductBrandBaseInfo productBrandBaseInfo) {
        String path = ossUtil.uploadFile(productBrandBaseInfo.getBrandImg(), OssFilePath.BRAND_IMAGE_FOLDER);
        try {
            productBrandBaseInfo.setLogo(ossUtil.generateUrl(path));
            productBrandManager.insertProductBrand(productBrandBaseInfo.newProductBrandPO());
            return true;
        }  catch (Exception e) {
            //数据库操作失败时删除oss中文件
            ossUtil.deleteFile(path);
            throw e;
        }
    }
}
