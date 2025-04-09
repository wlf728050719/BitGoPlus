package cn.bit.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 订单主表实体类 对应数据库表：order_master_$->{0..9}
 */
@Data
@Accessors(chain = true)
@TableName(value = "order_master", autoResultMap = true)
public class OrderMasterPO {
    /** 订单ID（雪花ID） */
    @TableField("order_id")
    private Long orderId;

    /** 订单编号（业务可见单号） */
    @TableField("order_sn")
    private String orderSn;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;

    /**
     * 订单状态 0-待支付, 1-已支付待发货, 2-已发货, 3-已完成, 4-已取消, 5-已退款
     */
    @TableField("order_status")
    private Integer orderStatus;

    /** 订单总金额（含运费） */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /** 实际支付金额（扣除优惠后） */
    @TableField("pay_amount")
    private BigDecimal payAmount;

    /** 运费金额 */
    @TableField("freight_amount")
    private BigDecimal freightAmount = BigDecimal.ZERO;

    /** 优惠金额（促销、优惠券等） */
    @TableField("discount_amount")
    private BigDecimal discountAmount = BigDecimal.ZERO;

    /** 支付方式：1-支付宝, 2-微信, 3-银联 */
    @TableField("pay_type")
    private Integer payType;

    /** 支付时间 */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /** 发货时间 */
    @TableField("delivery_time")
    private LocalDateTime deliveryTime;

    /** 收货时间 */
    @TableField("receive_time")
    private LocalDateTime receiveTime;

    /** 订单关闭时间 */
    @TableField("close_time")
    private LocalDateTime closeTime;

    /** 订单来源：1-PC, 2-APP, 3-小程序, 4-H5 */
    @TableField("source_type")
    private Integer sourceType;

    /** 物流公司名称 */
    @TableField("delivery_company")
    private String deliveryCompany;

    /** 物流单号 */
    @TableField("delivery_sn")
    private String deliverySn;

    /** 收货人姓名 */
    @TableField("receiver_name")
    private String receiverName;

    /** 收货人电话 */
    @TableField("receiver_phone")
    private String receiverPhone;

    /** 收货地址-省份 */
    @TableField("receiver_province")
    private String receiverProvince;

    /** 收货地址-城市 */
    @TableField("receiver_city")
    private String receiverCity;

    /** 收货地址-区县 */
    @TableField("receiver_region")
    private String receiverRegion;

    /** 收货地址-详细地址 */
    @TableField("receiver_detail_address")
    private String receiverDetailAddress;

    /** 订单备注（用户填写） */
    @TableField("note")
    private String note;

    /** 确认收货状态：0-未确认, 1-已确认 */
    @TableField("confirm_status")
    private Integer confirmStatus;

    /** 删除状态：0-未删除, 1-已删除 */
    @TableField("delete_status")
    private Integer deleteStatus;

    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
