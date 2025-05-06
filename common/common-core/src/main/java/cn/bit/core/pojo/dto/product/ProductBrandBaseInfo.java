package cn.bit.core.pojo.dto.product;


import cn.bit.core.jsr303.annotation.ValidFile;
import cn.bit.core.jsr303.enums.FileEnum;
import cn.bit.core.pojo.po.product.ProductBrandPO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class ProductBrandBaseInfo {
    /** 品牌ID */
    @Null
    private Long brandId;

    /** 品牌名称 */
    @NotNull
    private String brandName;

    /** 品牌logo URL */
    @Null
    private String logo;

    /** 品牌logo图片 */
    @ValidFile(fileEnum = FileEnum.IMAGE_FILE)
    private MultipartFile logoImg;

    /** 品牌描述 */
    private String description;

    /** 品牌官网 */
    private String website;

    /** 排序权重（数字越小越靠前） */
    @Null
    private Integer sortOrder;

    /** 删除标记 */
    @Null
    private Integer delFlag;

    public ProductBrandPO newProductBrandPO() {
        ProductBrandPO productBrandPO = new ProductBrandPO();
        productBrandPO.setBrandId(brandId);
        productBrandPO.setBrandName(brandName);
        productBrandPO.setLogoUrl(logo);
        productBrandPO.setDescription(description);
        productBrandPO.setWebsite(website);
        productBrandPO.setSortOrder(sortOrder);
        productBrandPO.setDelFlag(delFlag);
        return productBrandPO;
    }
}
