package cn.bit.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 商品品牌实体类
 */
@Data
@Accessors(chain = true)
public class ProductBrandPO {
    /** 品牌ID */
    private Long brandId;

    /** 品牌名称 */
    private String brandName;

    /** 品牌logo URL */
    private String logo;

    /** 品牌描述 */
    private String description;

    /** 品牌官网 */
    private String website;

    /** 排序权重（数字越小越靠前） */
    private Integer sortOrder = 0;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 删除标志（0-未删除，1-已删除） */
    private Integer delFlag = 0;
}
