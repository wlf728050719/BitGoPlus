package cn.bit.core.pojo.dto;


import javax.validation.constraints.Null;

public class ProductBrandBaseInfo {
    /** 品牌ID */
    @Null
    private Long brandId;

    /** 品牌名称 */
    private String brandName;

    /** 品牌logo URL */
    @Null
    private String logo;

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
}
