package cn.bit.pojo.po.product;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品分类实体类
 */
@Data
@Accessors(chain = true)
@TableName(value = "product_category", autoResultMap = true)
public class ProductCategoryDictItem {
    /** 分类ID（雪花ID） */
    @TableId(value = "category_id")
    private Long categoryId;

    /** 分类名称 */
    @TableField("category_name")
    private String categoryName;

    /** 父分类ID（一级分类为null） */
    @TableField("parent_id")
    private Long parentId;

    /** 分类层级（1-一级，2-二级等） */
    @TableField("level")
    private Integer level;

    /** 排序权重（数字越小越靠前） */
    @TableField("sort_order")
    private Integer sortOrder;

    /** 分类图标URL */
    @TableField("icon")
    private String icon;

    /** 分类描述 */
    @TableField("description")
    private String description;

    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标志（0-未删除，1-已删除） */
    @TableField("del_flag")
    private Integer delFlag;
}
