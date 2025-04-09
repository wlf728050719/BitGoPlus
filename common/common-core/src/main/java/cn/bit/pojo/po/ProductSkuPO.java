package cn.bit.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品SKU实体类（Stock Keeping Unit）
 */
@Data
@Accessors(chain = true)
public class ProductSkuPO {
    /** SKU ID（雪花ID） */
    private Long skuId;

    /** 关联的SPU ID */
    private Long spuId;

    /** SKU编码（唯一业务标识） */
    private String skuCode;

    /** 规格值（JSON格式） */
    private String specValues;

    /** 销售价格 */
    private BigDecimal price;

    /** 成本价 */
    private BigDecimal costPrice;

    /** 市场价（划线价） */
    private BigDecimal marketPrice;

    /** 库存数量 */
    private Integer stock;

    /** 库存预警值 */
    private Integer warnStock;

    /** SKU图片 */
    private String image;

    /** 商品重量（kg） */
    private BigDecimal weight;

    /** 商品体积（m³） */
    private BigDecimal volume;

    /** 状态（1-启用，0-禁用） */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 删除标志（0-未删除，1-已删除） */
    private Integer delFlag;
}
