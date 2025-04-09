package cn.bit.pojo.po;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品分类实体类
 */
@Data
@Accessors(chain = true)
public class ProductCategoryPO {
    /** 分类ID（雪花ID） */
    private Long categoryId;

    /** 分类名称 */
    private String categoryName;

    /** 父分类ID（一级分类为null） */
    private Long parentId;

    /** 分类层级（1-一级，2-二级等） */
    private Integer level;

    /** 排序权重（数字越小越靠前） */
    private Integer sortOrder;

    /** 分类图标URL */
    private String icon;

    /** 分类描述 */
    private String description;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 删除标志（0-未删除，1-已删除） */
    private Integer delFlag;
}
