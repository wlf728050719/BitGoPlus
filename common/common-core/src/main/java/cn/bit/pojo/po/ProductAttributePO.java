package cn.bit.pojo.po;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品属性实体类
 */
@Data
@Accessors(chain = true)
public class ProductAttributePO {
    /** 属性ID */
    private Long attrId;

    /** 关联的SPU ID */
    private Long spuId;

    /** 属性名称 */
    private String attrName;

    /** 属性值 */
    private String attrValue;

    /**
     * 属性类型 1-关键属性，2-销售属性，3-普通属性
     */
    private Integer attrType;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 删除标志（0-未删除，1-已删除） */
    private Integer delFlag;
}
