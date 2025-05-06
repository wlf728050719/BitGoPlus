package cn.bit.productservice.service.impl;

import cn.bit.core.constant.OssFilePath;
import cn.bit.core.pojo.dto.product.ProductBrandBaseInfo;
import cn.bit.core.pojo.dto.product.ProductSpuBaseInfo;
import cn.bit.feign.client.UserClient;
import cn.bit.core.constant.SecurityConstant;
import cn.bit.core.pojo.dto.product.ShopBaseInfo;
import cn.bit.oss.util.OssUtil;
import cn.bit.productservice.manager.ProductBrandManager;
import cn.bit.productservice.manager.ProductCategoryManager;
import cn.bit.productservice.manager.ProductSpuManager;
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
    private final ProductSpuManager productSpuManager;
    private final ProductCategoryManager productCategoryManager;
    private final OssUtil ossUtil;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public Boolean createShop(Long userId, ShopBaseInfo shopBaseInfo) {
        // 上传shopLogo
        String path = ossUtil.uploadFile(shopBaseInfo.getLogoImg(), OssFilePath.SHOP_LOGO_FOLDER);
        try {
            shopBaseInfo.setLogoUrl(path);
            Long tenantId = shopManager.insertShop(shopBaseInfo.newShopPO());
            userClient.addPermission(userId, tenantId, SecurityConstant.ROLE_SHOPKEEPER);
            return true;
        } catch (Exception e) {
            // 数据库操作失败时删除oss中文件
            ossUtil.deleteFile(path);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addBrand(ProductBrandBaseInfo productBrandBaseInfo) {
        // 上传brandLogo
        String path = ossUtil.uploadFile(productBrandBaseInfo.getLogoImg(), OssFilePath.BRAND_LOGO_FOLDER);
        try {
            productBrandBaseInfo.setLogo(ossUtil.generateUrl(path));
            productBrandManager.insertProductBrand(productBrandBaseInfo.newProductBrandPO());
            return true;
        } catch (Exception e) {
            // 数据库操作失败时删除oss中文件
            ossUtil.deleteFile(path);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean publishProductSpu(ProductSpuBaseInfo productSpuBaseInfo) {
        // 校验商品分类ID
        productCategoryManager.checkAvailableProductCategoryDictItemId(productSpuBaseInfo.getCategoryId());
        // 校验商品品牌ID
        productBrandManager.checkAvailableProductBrandId(productSpuBaseInfo.getBrandId());
        // 校验店铺ID
        shopManager.checkAvailableShopId(productSpuBaseInfo.getShopId());
        String path = ossUtil.uploadFile(productSpuBaseInfo.getMainImage(), OssFilePath.PRODUCT_SPU_IMAGE_FOLDER);
        try {
            productSpuBaseInfo.setMainImageUrl(path);
            productSpuManager.insertProductSpu(productSpuBaseInfo.newProductSpu());
            return true;
        } catch (Exception e) {
            // 数据库操作失败时删除oss中文件
            ossUtil.deleteFile(path);
            throw e;
        }
    }
}
