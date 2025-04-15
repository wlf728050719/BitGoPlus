package cn.bit.pojo.po.product;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品属性实体类
 */
@Data
@Accessors(chain = true)
@TableName(value = "product_attribute", autoResultMap = true)
public class ProductAttributePO {
    /** 属性ID */
    @TableId(value = "attr_id")
    private Long attrId;

    /** 关联的SPU ID */
    @TableField("spu_id")
    private Long spuId;

    /** 属性名称 */
    @TableField("attr_name")
    private String attrName;

    /** 属性值 */
    @TableField("attr_value")
    private String attrValue;

    /**
     * 属性类型 1-关键属性，2-销售属性，3-普通属性
     */
    @TableField("attr_type")
    private Integer attrType;

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
