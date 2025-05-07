package cn.bit.core.pojo.dto.product;

import cn.bit.core.jsr303.annotation.ValidFile;
import cn.bit.core.jsr303.enums.FileEnum;
import cn.bit.core.pojo.po.product.ProductSpuPO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * <p>商品Spu基本信息</p>
 * Date:2025/05/07 20:25:49
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class ProductSpuBaseInfo {
    /** SPU ID */
    @Null
    private Long spuId;

    /** SPU编码（唯一业务标识） */
    @NotNull
    private String spuCode;

    /** 产品名称 */
    @NotNull
    private String spuName;

    /** 分类ID */
    @NotNull
    private Long categoryId;

    /** 品牌ID */
    @NotNull
    private Long brandId;

    /** 店铺ID */
    @NotNull
    private Long shopId;

    /** 主图URL */
    @Null
    private String mainImageUrl;

    /** 主图 */
    @ValidFile(value = FileEnum.IMAGE_FILE)
    private MultipartFile mainImage;

    /** 子图URL（JSON数组格式） */
    @Null
    private String subImagesUrl;

    /** 产品描述（富文本） */
    private String description;

    /** 规格模板（JSON格式） */
    @Null
    private String specTemplate;

    /** 总销量 */
    @Null
    private Integer sales;

    /** 状态（1-上架，0-下架） */
    @Null
    private Integer status;

    /** 删除标志（0-未删除，1-已删除） */
    @Null
    private Integer delFlag;

    public ProductSpuPO newProductSpu() {
        ProductSpuPO productSpuPO = new ProductSpuPO();
        productSpuPO.setSpuId(spuId);
        productSpuPO.setSpuCode(spuCode);
        productSpuPO.setSpuName(spuName);
        productSpuPO.setCategoryId(categoryId);
        productSpuPO.setBrandId(brandId);
        productSpuPO.setShopId(shopId);
        productSpuPO.setMainImageUrl(mainImageUrl);
        productSpuPO.setSubImagesUrl(subImagesUrl);
        productSpuPO.setDescription(description);
        productSpuPO.setSpecTemplate(specTemplate);
        productSpuPO.setSales(sales);
        productSpuPO.setStatus(status);
        productSpuPO.setDelFlag(delFlag);
        return productSpuPO;
    }
}
