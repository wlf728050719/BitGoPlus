package cn.bit.core.pojo.po.product;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品品牌实体类
 */
@Data
@Accessors(chain = true)
@TableName(value = "product_brand", autoResultMap = true)
public class ProductBrandPO {
    /** 品牌ID */
    @TableId(value = "brand_id")
    private Long brandId;

    /** 品牌名称 */
    @TableField("brand_name")
    private String brandName;

    /** 品牌logo URL */
    @TableField("logo")
    private String logo;

    /** 品牌描述 */
    @TableField("description")
    private String description;

    /** 品牌官网 */
    @TableField("website")
    private String website;

    /** 排序权重（数字越小越靠前） */
    @TableField("sort_order")
    private Integer sortOrder;

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
