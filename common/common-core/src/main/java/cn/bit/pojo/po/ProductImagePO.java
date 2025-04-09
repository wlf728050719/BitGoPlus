package cn.bit.pojo.po;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品图片实体类
 */
@Data
@Accessors(chain = true)
public class ProductImagePO {
    /** 图片ID */
    private Long imageId;

    /** 关联的SPU ID */
    private Long spuId;

    /** 关联的SKU ID（null表示SPU通用图片） */
    private Long skuId;

    /** 图片URL */
    private String imageUrl;

    /** 排序权重（数字越小越靠前） */
    private Integer sortOrder;

    /** 是否主图（1-是，0-否） */
    private Integer isMain;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 删除标志（0-未删除，1-已删除） */
    private Integer delFlag;
}
