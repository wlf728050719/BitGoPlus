package cn.bit.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单商品项实体类
 * 对应数据库表：order_item_[0-9]
 */
@Data
@Accessors(chain = true)
public class OrderItemPO {
    /** 订单商品项ID */
    private Long itemId;

    /** 关联的订单ID */
    private Long orderId;

    /** 订单编号（冗余存储） */
    private String orderSn;

    /** 商品SPU ID */
    private Long productId;

    /** 商品SPU编码 */
    private String productSn;

    /** 商品名称 */
    private String productName;

    /** 商品品牌 */
    private String productBrand;

    /** 商品分类ID */
    private Long productCategoryId;

    /** 商品SKU ID */
    private Long skuId;

    /** SKU编码 */
    private String skuCode;

    /**
     * 商品规格（JSON格式）
     * 示例：{"颜色":"红色","内存":"128GB"}
     */
    private String specValues;

    /**
     * 商品属性（JSON格式）
     * 示例：{"保修期":"1年","产地":"中国"}
     */
    private String productAttr;

    /** 商品主图URL */
    private String productImage;

    /** 商品单价 */
    private BigDecimal price;

    /** 购买数量 */
    private Integer quantity;

    /** 商品总金额（单价×数量） */
    private BigDecimal totalAmount;

    /** 优惠金额（分摊到该商品的优惠） */
    private BigDecimal discountAmount = BigDecimal.ZERO;

    /** 实付金额（总金额-优惠金额） */
    private BigDecimal realAmount;

    /**
     * 退款状态
     * 0-未退款, 1-退款中, 2-已退款
     */
    private Integer refundStatus = 0;

    /** 退款金额 */
    private BigDecimal refundAmount;

    /** 退款时间 */
    private LocalDateTime refundTime;

    /** 创建时间 */
    private LocalDateTime createTime;
}
