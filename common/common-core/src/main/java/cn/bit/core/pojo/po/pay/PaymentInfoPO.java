package cn.bit.core.pojo.po.pay;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付信息实体类 对应数据库表：payment_info_[0-9]
 */
@Data
@Accessors(chain = true)
public class PaymentInfoPO {
    /** 支付记录ID */
    private Long paymentId;

    /** 关联的订单ID */
    private Long orderId;

    /** 订单编号（冗余存储） */
    private String orderSn;

    /** 支付流水号（系统生成） */
    private String paymentSn;

    /** 支付用户ID */
    private Long userId;

    /** 支付方式：1-支付宝, 2-微信, 3-银联 */
    private Integer paymentType;

    /** 支付金额 */
    private BigDecimal totalAmount;

    /**
     * 支付状态 0-未支付, 1-支付成功, 2-支付失败, 3-已退款
     */
    private Integer paymentStatus;

    /** 支付成功时间 */
    private LocalDateTime paymentTime;

    /** 支付回调时间 */
    private LocalDateTime callbackTime;

    /** 支付回调内容（原始回调数据） */
    private String callbackContent;

    /** 第三方支付交易号 */
    private String transactionId;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最后更新时间 */
    private LocalDateTime updateTime;
}
