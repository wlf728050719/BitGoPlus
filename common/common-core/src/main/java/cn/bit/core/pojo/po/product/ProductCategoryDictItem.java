package cn.bit.core.pojo.po.product;

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
@TableName(value = "dict_product_category", autoResultMap = true)
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
    @TableField("icon_url")
    private String iconUrl;

    /** 分类描述 */
    @TableField("description")
    private String description;

    /** 删除标志（0-未删除，1-已删除） */
    @TableField("del_flag")
    private Integer delFlag;
}
