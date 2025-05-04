package cn.bit.core.pojo.po.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品SKU实体类（Stock Keeping Unit）
 */
@Data
@Accessors(chain = true)
@TableName(value = "product_sku", autoResultMap = true)
public class ProductSkuPO {
    /** SKU ID（雪花ID） */
    @TableId(value = "sku_id")
    private Long skuId;

    /** 关联的SPU ID */
    @TableField("spu_id")
    private Long spuId;

    /** SKU编码（唯一业务标识） */
    @TableField("sku_code")
    private String skuCode;

    /** 规格值（JSON格式） */
    @TableField("spec_values")
    private String specValues;

    /** 销售价格 */
    @TableField("price")
    private BigDecimal price;

    /** 成本价 */
    @TableField("cost_price")
    private BigDecimal costPrice;

    /** 市场价（划线价） */
    @TableField("market_price")
    private BigDecimal marketPrice;

    /** 库存数量 */
    @TableField("stock")
    private Integer stock;

    /** 库存预警值 */
    @TableField("warn_stock")
    private Integer warnStock;

    /** SKU图片 */
    @TableField("image")
    private String image;

    /** 商品重量（kg） */
    @TableField("weight")
    private BigDecimal weight;

    /** 商品体积（m³） */
    @TableField("volume")
    private BigDecimal volume;

    /** 状态（1-启用，0-禁用） */
    @TableField("status")
    private Integer status;

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
