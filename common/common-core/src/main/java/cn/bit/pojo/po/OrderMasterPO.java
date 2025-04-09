package cn.bit.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 订单主表实体类 对应数据库表：order_master_[0-9]
 */
@Data
@Accessors(chain = true)
public class OrderMasterPO {
    /** 订单ID（雪花ID） */
    private Long orderId;

    /** 订单编号（业务可见单号） */
    private String orderSn;

    /** 用户ID */
    private Long userId;

    /**
     * 订单状态 0-待支付, 1-已支付待发货, 2-已发货, 3-已完成, 4-已取消, 5-已退款
     */
    private Integer orderStatus;

    /** 订单总金额（含运费） */
    private BigDecimal totalAmount;

    /** 实际支付金额（扣除优惠后） */
    private BigDecimal payAmount;

    /** 运费金额 */
    private BigDecimal freightAmount = BigDecimal.ZERO;

    /** 优惠金额（促销、优惠券等） */
    private BigDecimal discountAmount = BigDecimal.ZERO;

    /** 支付方式：1-支付宝, 2-微信, 3-银联 */
    private Integer payType;

    /** 支付时间 */
    private LocalDateTime payTime;

    /** 发货时间 */
    private LocalDateTime deliveryTime;

    /** 收货时间 */
    private LocalDateTime receiveTime;

    /** 订单关闭时间 */
    private LocalDateTime closeTime;

    /** 订单来源：1-PC, 2-APP, 3-小程序, 4-H5 */
    private Integer sourceType;

    /** 物流公司名称 */
    private String deliveryCompany;

    /** 物流单号 */
    private String deliverySn;

    /** 收货人姓名 */
    private String receiverName;

    /** 收货人电话 */
    private String receiverPhone;

    /** 收货地址-省份 */
    private String receiverProvince;

    /** 收货地址-城市 */
    private String receiverCity;

    /** 收货地址-区县 */
    private String receiverRegion;

    /** 收货地址-详细地址 */
    private String receiverDetailAddress;

    /** 订单备注（用户填写） */
    private String note;

    /** 确认收货状态：0-未确认, 1-已确认 */
    private Integer confirmStatus;

    /** 删除状态：0-未删除, 1-已删除 */
    private Integer deleteStatus;

    /** 订单创建时间 */
    private LocalDateTime createTime;

    /** 订单最后更新时间 */
    private LocalDateTime updateTime;
}
