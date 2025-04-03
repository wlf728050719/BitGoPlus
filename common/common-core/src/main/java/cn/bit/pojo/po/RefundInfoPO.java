package cn.bit.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退款信息实体类
 * 对应数据库表：refund_info_[0-9]
 */
@Data
@Accessors(chain = true)
public class RefundInfoPO {
    /** 退款记录ID */
    private Long refundId;

    /** 关联的订单ID */
    private Long orderId;

    /** 订单编号（冗余存储） */
    private String orderSn;

    /** 关联的支付记录ID */
    private Long paymentId;

    /** 支付流水号（冗余存储） */
    private String paymentSn;

    /** 退款用户ID */
    private Long userId;

    /** 退款流水号（系统生成） */
    private String refundSn;

    /** 退款金额 */
    private BigDecimal refundAmount;

    /** 退款方式：1-原路退回, 2-线下退款 */
    private Integer refundType;

    /**
     * 退款状态
     * 0-申请中, 1-退款中, 2-退款成功, 3-退款失败
     */
    private Integer refundStatus = 0;

    /** 退款原因 */
    private String refundReason;

    /** 退款完成时间 */
    private LocalDateTime refundTime;

    /** 退款回调时间 */
    private LocalDateTime callbackTime;

    /** 退款回调内容（原始回调数据） */
    private String callbackContent;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最后更新时间 */
    private LocalDateTime updateTime;
}
